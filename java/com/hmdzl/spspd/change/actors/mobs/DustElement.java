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

import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.sprites.DustElementSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class DustElement extends Mob {

	{
		spriteClass = DustElementSprite.class;

		HP = HT = 35+(Dungeon.depth*Random.NormalIntRange(1, 3));
		evadeSkill = 4+(Math.round((Dungeon.depth)/2));

		EXP = 2;
		maxLvl = 8;

		loot = Generator.random(Generator.Category.SEED);
		lootChance = 0.5f;
		
		properties.add(Property.ELEMENT);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(2, 5+(Dungeon.depth));
	}

	@Override
	public int hitSkill(Char target) {
		return 11+(Dungeon.depth);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.prolong(enemy, Blindness.class, Random.Int(3, 10));
			GLog.w(Messages.get(this,"blind"));
			Dungeon.observe();
		}

		return damage;
	}

	@Override
	public int drRoll() {
		return 2;
	}
	
}
