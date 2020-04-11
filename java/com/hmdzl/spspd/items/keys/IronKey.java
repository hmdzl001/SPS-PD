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
package com.hmdzl.spspd.items.keys;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;


public class IronKey extends Key {

	public static int curDepthQuantity = 0;

	{
		//name = "iron key";
		image = ItemSpriteSheet.IRON_KEY;
	}

	public IronKey() {
		this(0);
	}

	public IronKey(int depth) {
		super();
		this.depth = depth;
	}

	@Override
	public boolean collect(Bag bag) {
		boolean result = super.collect(bag);
		if (result && depth == Dungeon.depth && Dungeon.hero != null) {
			Dungeon.hero.belongings.countIronKeys();
		}
		return result;
	}

	@Override
	public void onDetach() {
		if (depth == Dungeon.depth) {
			Dungeon.hero.belongings.countIronKeys();
		}
	}
}
