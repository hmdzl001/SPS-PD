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
package com.hmdzl.spspd.items.food.meatfood;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class HarmPoop extends MeatFood {

	{
		//name = "mystery meat";
		image = ItemSpriteSheet.MYSTERYMEAT;
		energy = 10;
		hornValue = 0;
	}

	public void doEat() {
		
		Buff.affect(curUser, Poison.class).set(curUser.HT / 10);
		Buff.prolong(curUser, Slow.class, 5f);

	}

	@Override
	public int price() {
		return 2 * quantity;
	}
}