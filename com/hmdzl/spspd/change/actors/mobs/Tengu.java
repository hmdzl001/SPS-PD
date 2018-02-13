/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.levels.traps.PoisonTrap;
import com.hmdzl.spspd.change.levels.traps.SummoningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Badges.Badge;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.OtilukesJournal;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.journalpages.Sokoban2;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.TenguSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Tengu extends Mob {

	private static final int JUMP_DELAY = 5;

	{
		spriteClass = TenguSprite.class;
		baseSpeed = 2f;

		HP = HT = 700;
		EXP = 20;
		defenseSkill = 30;

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 18);
	}

	@Override
	public int attackSkill(Char target) {
		return 20;
	}

	@Override
	public int dr() {
		return 10;
	}

	@Override
	public void die(Object cause) {

		//note
		Badges.Badge badgeToCheck = null;
		switch (Dungeon.hero.heroClass) {
		case WARRIOR:
			badgeToCheck = Badge.MASTERY_WARRIOR;
			break;
		case MAGE:
			badgeToCheck = Badge.MASTERY_MAGE;
			break;
		case ROGUE:
			badgeToCheck = Badge.MASTERY_ROGUE;
			break;
		case HUNTRESS:
			badgeToCheck = Badge.MASTERY_HUNTRESS;
			break;
		}
		
		Dungeon.level.drop(new TomeOfMastery(), pos).sprite.drop();
		
		if (!Dungeon.limitedDrops.journal.dropped()){ 
		  Dungeon.level.drop(new OtilukesJournal(), pos).sprite.drop();
		  Dungeon.limitedDrops.journal.drop();
		}
		
		Dungeon.level.drop(new Sokoban2(), pos).sprite.drop();
       
		
		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
			
		super.die(cause);

		Badges.validateBossSlain();
		Dungeon.tengukilled=true;

		yell(Messages.get(this,"flee"));
		TenguEscape.spawnAt(pos);
					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 && Level.adjacent(pos, enemy.pos)) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;

		for (int i = 0; i < 4; i++) {
			int trapPos;
			do {
				trapPos = Random.Int(Level.getLength());
			} while (!Level.fieldOfView[trapPos] || !Level.passable[trapPos]);

			if (Dungeon.level.map[trapPos] == Terrain.INACTIVE_TRAP) {
				Dungeon.level.setTrap( new PoisonTrap().reveal(), trapPos );
				Level.set(trapPos, Terrain.TRAP);
				GameScene.updateMap(trapPos);
				ScrollOfMagicMapping.discover(trapPos);
			}
		}

		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.fieldOfView[newPos] || !Level.passable[newPos]
				|| Level.adjacent(newPos, enemy.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice", Dungeon.hero.givenName()));
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
