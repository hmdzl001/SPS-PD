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
package com.hmdzl.spspd.items.weapon.ranges;

import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class MetalBowR extends RangeWeapon {

	{
		//name = "GunA";
		image = ItemSpriteSheet.METAL_BOW;
		STR++;
		sname = "R";
		//MIN=10;
		//MAX=10;
	}

	public MetalBowR() {
		super(3,1.25f);
	}

	@Override
	public Item upgrade(boolean enchant) {
		MAX += 4;
		return super.upgrade(enchant);
	}

}
