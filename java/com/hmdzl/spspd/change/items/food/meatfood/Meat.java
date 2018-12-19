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
package com.hmdzl.spspd.change.items.food.meatfood;

import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class Meat extends MeatFood {

	{
		//name = "monster meat";
		image = ItemSpriteSheet.MEAT;
		energy = 100;
		hornValue = 1;
		bones = false;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(15)) {
			case 0:
				GLog.w(Messages.get(this,"bad"));
				Buff.affect(hero, Poison.class).set(
						Poison.durationFactor(hero) * hero.HT / 5);
				break;
			}
		}
	}	

	@Override
	public int price() {
		return 2 * quantity;
	}
		
}
