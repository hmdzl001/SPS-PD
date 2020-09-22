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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.PoisonGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.hmdzl.spspd.sprites.MobBambooSprite;
import com.hmdzl.spspd.sprites.SeniorSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class BambooMob extends Mob {

	{
		
		HP = HT = 40;
		evadeSkill = 0;

		EXP = 1;

		loot = new NutVegetable();
		lootChance = 0.4f;
		
		spriteClass = MobBambooSprite.class;
		
		properties.add(Property.PLANT);
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
			return Random.NormalIntRange(1, 3);
		}		
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(this,DefenceUp.class,3f).level(20);
		}
		return super.attackProc(enemy, damage);
	}
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage);
		if (dmg > 0) {
			enemy.damage(dmg, this);
		}

		return super.defenseProc(enemy, damage);
	}	

		@Override
		protected boolean getCloser(int target) {
			return true;
		}

		@Override
		protected boolean getFurther(int target) {
			return true;
		}
		
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<>();


	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	static {
		WEAKNESS.add(ToxicGas.class);
		WEAKNESS.add(Ooze.class);
		WEAKNESS.add(PoisonGas.class);

		IMMUNITIES.add(Roots.class);

		WEAKNESS.add(Burning.class);
		WEAKNESS.add(Wand.class);
		WEAKNESS.add(EnchantmentFire.class);
		WEAKNESS.add(Poison.class);

	}
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> weakness() {
		return WEAKNESS;
	}
}
