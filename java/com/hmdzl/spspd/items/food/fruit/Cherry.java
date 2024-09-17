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
package com.hmdzl.spspd.items.food.fruit;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Cherry extends Fruit {

	{
		//name = "Cherry";
		image = ItemSpriteSheet.CHERRY;
		energy = 30;
		hornValue = 1;
		 
	}

	public void doEat() {
		Dungeon.depth.drop(new DungeonBomb(), curUser.pos).sprite.drop();
	}	

	@Override
	public int price() {
		return 5 * quantity;
	}
	
}
