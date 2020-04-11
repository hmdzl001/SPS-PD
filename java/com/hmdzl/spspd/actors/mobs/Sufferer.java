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

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;

import com.hmdzl.spspd.sprites.SuffererSprite;
import com.watabou.utils.Random;

public class Sufferer extends Mob {

	{
		spriteClass = SuffererSprite.class;

		HP = HT = 180+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 16+adj(1);

		EXP = 16;
		maxLvl = 35;
		
		loot = Generator.Category.SCROLL;
		lootChance = 0.35f;
		
		properties.add(Property.DEMONIC);
		properties.add(Property.HUMAN);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12+adj(0), 40+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return 34+adj(1);
	}


	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = damage;
		if (dmg > 50 && buff(GlassShield.class) == null) {
			Buff.affect(this,GlassShield.class).turns(4);
		}

		return super.defenseProc(enemy, damage);
	}	
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Sleep.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
