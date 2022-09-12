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

import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Mirror2 extends SellItem {

	//private static final String AC_END = "END THE GAME";

	{
		//name = "toadstool mushroom";
		image = ItemSpriteSheet.MIRROR_2;

	
	}

	/*
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_END);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_END) {

			showAmuletScene(false);

		} else {

			super.execute(hero, action);

		}
	}
  

*/

	@Override
	public int price() {
		return 112 * quantity;
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
