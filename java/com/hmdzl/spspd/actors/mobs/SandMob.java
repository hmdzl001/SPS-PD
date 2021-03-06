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

import java.util.HashSet;

import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth2;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SandmobSprite;
import com.watabou.utils.Random;

public class SandMob extends Mob {

	{
		spriteClass = SandmobSprite.class;

		HP = HT = 90+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 5+adj(0);
		baseSpeed = 0.5f;

		EXP = 10;
		maxLvl = 25;

		loot = new WaterItem();
		//loot = new PotionOfMending(); potential nerf
		lootChance = 0.5f; // by default, see die()
		
		properties.add(Property.ELEMENT);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(17, 25+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 10+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}


	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Dry.class, 10f);
		}
			
		if (Random.Int(5) == 0) {
			Buff.prolong(enemy, Slow.class, 10f);
		}			

		return damage;
	}

	@Override
	public void die(Object cause) {
        MiniSand.spawnAround(this.pos);
		super.die(cause);
	}

    public static class MiniSand extends Mob {

		protected static final float SPAWN_DELAY = 1f;
		{
			spriteClass = SandmobSprite.class;

			HP = HT = 45+(adj(0)*Random.NormalIntRange(2, 5));
			evadeSkill = 25;


			EXP = 0;

			state = WANDERING;
		}
		
		int generation = 0;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(15, 20+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 30+adj(0);
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(5) == 0) {
				Buff.prolong(enemy, Dry.class, 10f);
			}
			
			if (Random.Int(5) == 0) {
				Buff.prolong(enemy, Slow.class, 10f);
			}			

			return damage;
		}
		
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static MiniSand spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			MiniSand w = new MiniSand();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);
			return w;
  			
		} else {
			return null;
		}
	}		

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(EnchantmentEarth.class);
			IMMUNITIES.add(EnchantmentEarth2.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}	

}
