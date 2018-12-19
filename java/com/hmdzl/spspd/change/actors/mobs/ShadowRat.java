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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ShadowRatSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class ShadowRat extends Mob {
	

	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = ShadowRatSprite.class;

		HP = HT = 60;
		evadeSkill = 3;
		
		loot = new StoneOre();
		lootChance = 0.2f;
		
		properties.add(Property.ELEMENT);
		properties.add(Property.MINIBOSS);
	}


	@Override
	public void damage(int dmg, Object src) {
		if (src instanceof WandOfLight) {
			destroy();
			sprite.die();
		} else {

			super.damage(dmg, src);
		}
	}	
	
	@Override
	public int attackProc( Char enemy, int damage) {
		damage = super.attackProc(enemy, damage);
		if (Random.Int(3) < 1) {
			Buff.prolong(enemy, AttackDown.class, 10f).level(25);
		} else
		if (Random.Int(3) < 1) {
            Buff.prolong(enemy, Vertigo.class, 5f);
        }
		return super.attackProc(enemy, damage);
	}	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(6, 9);
	}

	@Override
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return 0;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();
	static {
		IMMUNITIES.add( ToxicGas.class );
		IMMUNITIES.add(Poison.class);
		IMMUNITIES.add(Burning.class);
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

	public static ShadowRat spawnAt(int pos) {
		
        ShadowRat b = new ShadowRat();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
    }
		
	

	
}
