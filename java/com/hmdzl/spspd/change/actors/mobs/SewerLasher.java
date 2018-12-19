/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.GrowSeed;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.SewerLasherSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class SewerLasher extends Mob {

protected static final float SPAWN_DELAY = 2f;

	{
		spriteClass = SewerLasherSprite.class;

		HP = HT = 60;
		evadeSkill = 0;

		EXP = 1;

		loot = Generator.Category.SEED;
		lootChance = 0.2f;

		state = HUNTING;

		properties.add(Property.PLANT);
		properties.add(Property.MINIBOSS);
		//properties.add(Property.IMMOVABLE);
	}

	@Override
	protected boolean act() {
		if (enemy == null || !Level.adjacent(pos, enemy.pos)) {
			HP = Math.min(HT, HP + 3);
		}
		return super.act();
	}

	@Override
	public void damage(int dmg, Object src) {
		if (src instanceof Burning) {
			destroy();
			sprite.die();
		} else {

			super.damage(dmg, src);
		}
	}

	@Override
	public int attackProc( Char enemy, int damage) {
		damage = super.attackProc(enemy, damage);
		if (Random.Int(5) < 1) {
			Buff.affect(enemy, Cripple.class, 2f);
		} else
		if (Random.Int(4) < 1) {
			Buff.affect(enemy, GrowSeed.class).reignite(enemy);
		} else
		if (Random.Int(3) < 1) {
			Buff.affect(enemy, Bleeding.class).set(damage);}

		return super.attackProc(enemy, damage);

	}
	@Override
	protected boolean getCloser(int target) {
		return true;
	}

	@Override
	protected boolean getFurther(int target) {
		return true;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4, 12);
	}

	@Override
	public int hitSkill( Char target ) {
		return 15;
	}

	@Override
	public int drRoll() {
		return 8;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( ToxicGas.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS8) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static void spawnAroundChance(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null && Random.Float() < 0.75f) {
				spawnAt(cell);
			}
		}
	}

	public static SewerLasher spawnAt(int pos) {
		
        SewerLasher b = new SewerLasher();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
		

}
