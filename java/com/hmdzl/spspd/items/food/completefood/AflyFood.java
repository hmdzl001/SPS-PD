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

import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class AflyFood extends CompleteFood {

	{
		//name = "AflyFood";
		image = ItemSpriteSheet.AFLY_FOOD;
		energy = 200;
		hornValue = 3;
		 
	}

	@Override
	public int price() {
		return 2 * quantity;
	}

	public void doEat() {
			Buff.affect(curUser, AflyBless.class,150f);
			curUser.belongings.observeS();
	}

}