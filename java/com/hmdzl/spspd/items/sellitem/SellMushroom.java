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
package com.hmdzl.spspd.items.sellitem;

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.medicine.Pill;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class SellMushroom extends Pill {

	//private static final String AC_END = "END THE GAME";

	{
		//name = "toadstool mushroom";
		image = ItemSpriteSheet.MUSHROOM;

	
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_EAT)) {
			GLog.w(Messages.get(this, "no"));
		}

		super.execute(hero, action);
	}

	@Override
	public int price() {
		return 100 * quantity;
	}
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}
}
