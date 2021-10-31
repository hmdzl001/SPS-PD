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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.BrownBatSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class BrownBat extends Mob {

	{
		spriteClass = BrownBatSprite.class;

		HP = HT = 20;
		evadeSkill = 1;
		baseSpeed = 2f;

		EXP = 1;
		maxLvl = 6;

		flying = true;

		loot = new Meat();
		lootChance = 0.5f; // by default, see die()
		
		lootOther = Generator.Category.SEED;
		lootChanceOther = 0.05f; // by default, see die()
		
		properties.add(Property.BEAST);
		
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 4);
	}

	@Override
	public int hitSkill(Char target) {
		return 5+Dungeon.depth;
	}


	@Override
	public int drRoll() {
		return 1;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);

		if (Random.Int(5) == 0) {
			  for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				  if (Random.Int(2) == 0 && enemy!=null){mob.beckon(enemy.pos);}
			      }
			GLog.w(Messages.get(this,"die"));
	    }

		
	}
	
}
