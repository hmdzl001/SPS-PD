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

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Honeyrice extends CompleteFood {

	{
		//name = "honeyrice";
		image = ItemSpriteSheet.HONEY_RICE;
		energy = 500;
		hornValue = 3;
		 
	}


	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public ItemSprite.Glowing glowing() {
		return YELLOW;
	}

	public void doEat() {
			curUser.TRUE_HT = curUser.TRUE_HT + (Random.Int(3, 6));
            curUser.updateHT(true);
	}

	@Override
	public int price() {
		return 400 * quantity;
	}

}