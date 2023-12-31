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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.wands.WandOfBlood;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.FiendSprite;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Fiend extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_SHADOWBOLT_KILLED = "%s's shadow bolt killed you...";	

	private static final float SPAWN_DELAY = 6f;

	{
		spriteClass = FiendSprite.class;
		baseSpeed = 1.5f;
		viewDistance = 4;
		HP = HT = 80+(Dungeon.dungeondepth *Random.NormalIntRange(2, 5));
		evadeSkill = 2;
		
		EXP = 20;
		
		loot = Generator.Category.SCROLL;
		lootChance = 0.15f;
		
		properties.add(Property.DEMONIC);
		properties.add(Property.ELEMENT);

	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.dungeondepth /2, Dungeon.dungeondepth);
	}

	@Override
	public int hitSkill(Char target) {
		return 50;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}
	
	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Floor.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Floor.fieldOfView[pos]
					|| Floor.fieldOfView[enemy.pos];
			if (visible) {
				sprite.zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	private void zap() {
		spend(TIME_TO_ZAP);

		if (hit(this, enemy, true)) {
			if (enemy == Dungeon.hero && Random.Int(5) == 0) {
				Buff.prolong(enemy, STRdown.class,5f);
			}

			int dmg = Random.Int(20, 45);
			enemy.damage(dmg, this);

			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n(Messages.get(this, "kill"));
			}
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}
	
	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static void spawnAroundChance(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null && Random.Float() < 0.75f) {
				spawnAt(cell);
			}
		}
	}
	
	public static Fiend spawnAt(int pos) {
		
        Fiend b = new Fiend();
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	@Override
	public void add(Buff buff) {
		if (buff instanceof ShadowCurse) {
			if (HP < HT) {
				HP+=HT/10;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else if (buff instanceof Amok || buff instanceof Terror) {
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff);
		} else {
			super.add(buff);
		}
	}
	{
		immunities.add(DamageType.DarkDamage.class);
		resistances.add(WandOfBlood.class);
	}
}
