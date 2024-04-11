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
package com.hmdzl.spspd.items.food.completefood;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.YearBeast;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class YearFood extends CompleteFood {

	{
		//name = "honeymeat";
		image = ItemSpriteSheet.YEAR_FOOD;
		energy = 150;
		hornValue = 3;
		 
	}

	public void doEat() {
			curUser.TRUE_HT = curUser.TRUE_HT + (Random.Int(3, 6));
			curUser.updateHT(true);
            if (Dungeon.dungeondepth == 25){
				curUser.TRUE_HT = curUser.TRUE_HT + (Random.Int(3, 6));
				curUser.updateHT(true);
				int newPos2;
				do {
					newPos2 = Random.Int(Floor.getLength());
				} while (!Floor.passable[newPos2]
						|| Floor.adjacent(newPos2, Dungeon.hero.pos)
						|| Actor.findChar(newPos2) != null);
				YearBeast.spawnAt(newPos2);
			}
	}

	@Override
	public int price() {
		return 400 * quantity;
	}

}