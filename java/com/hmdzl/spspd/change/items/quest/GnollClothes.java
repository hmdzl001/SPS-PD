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
package com.hmdzl.spspd.change.items.quest;

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class GnollClothes extends Item {

	{
		//name = "GnollClothes";
		image = ItemSpriteSheet.GNOLL_ARMOR;

		stackable = true;
		unique = true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	/*@Override
	public String info() {
		return "Many dwarves and some of their larger creations carry these small pieces of metal of unknown purpose. "
				+ "Maybe they are jewelry or maybe some kind of ID. Dwarves are strange folk.";
	}*/

}