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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.SkillBook;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TenguKey;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.journalpages.Sokoban2;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.items.weapon.missiles.HugeShuriken;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.rockcode.Nshuriken;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.PoisonTrap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.TenguSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Tengu extends Mob {

	private static final int JUMP_DELAY = 5;

	{
		spriteClass = TenguSprite.class;

		HP = HT = 600;
		EXP = 40;
		evadeSkill = 30;
		viewDistance = 5;

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);

		loot =  new MasterThievesArmband().identify();
		lootChance = 0.2f;

		lootOther = new HugeShuriken(20);
		lootChanceOther = 1f; // by default, see die()

	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(23, 35);
	}

	@Override
	public int hitSkill(Char target) {
		return 40;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 5);
	}

	@Override
	public Item SupercreateLoot(){
		return new ManyKnive().identify().dounique();
	}

	@Override
	public void die(Object cause) {
		yell(Messages.get(this,"die"));	
		GameScene.bossSlain();	
	    Badges.validateBossSlain();	
	    Dungeon.level.unseal();
	    Dungeon.level.drop(new SkillBook(), pos).sprite.drop();
		Dungeon.level.drop(new Sokoban2(), pos).sprite.drop();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();		
		Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Dungeon.skins == 7)
			Dungeon.level.drop(new Nshuriken(), Dungeon.hero.pos).sprite.drop();
        super.die(cause);
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
		if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
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

	@Override
	public int attackProc(Char enemy, int damage) {

		if (Level.distance(pos, enemy.pos) == 1 ) {
			Buff.affect(enemy,Silent.class,.5f);
			timeToJump--;
		}

		if (Level.distance(pos, enemy.pos) > 1 && Random.Int(10) > 7){
			Buff.affect(enemy, Locked.class,5f);
			timeToJump++;
		}

		if (Level.distance(pos, enemy.pos) > 1 && Random.Int(10) > 9){
			Buff.affect(enemy, Burning.class).set(4f);
			timeToJump++;
		}

		return damage;
	}

	private void jump() {
		timeToJump = JUMP_DELAY;

		for (int i = 0; i < 3; i++) {
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

	{
		weakness.add(WandOfLight.class);
		weakness.add(EnchantmentLight.class);

		resistances.add(Burning.class);
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		resistances.add(EnchantmentDark.class);
		
	}
}
