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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.sprites.ZombieSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Zombie extends Mob {
	

	{
		spriteClass = ZombieSprite.class;

		HP = HT = 70+(adj(0)*Random.NormalIntRange(3, 7));
		evadeSkill = 9+adj(1);
		baseSpeed = 2f;
		
		
		EXP = 7;
		maxLvl = 18;
		
	    state = WANDERING;	
		
		loot = PotionOfToxicGas.class;
		lootChance = 0.1f;
		
		properties.add(Property.UNDEAD);
	}

	@Override
	protected float attackDelay() {
		return 2f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10+adj(0), 20+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 15 +adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(3, 8);
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Poison.class).set(
					Random.Int(7, 9) * Poison.durationFactor(enemy));
		}

		return damage;
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	static {
		WEAKNESS.add(Burning.class);
		WEAKNESS.add(WandOfFirebolt.class);
		RESISTANCES.add(ToxicGas.class);
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
