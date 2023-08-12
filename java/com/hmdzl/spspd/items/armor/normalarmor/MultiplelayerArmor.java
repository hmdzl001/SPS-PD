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
package com.hmdzl.spspd.items.armor.normalarmor;

import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class MultiplelayerArmor extends NormalArmor {

	{
		//name = "Multiplelayer MagicPlantArmor";
		image = ItemSpriteSheet.MUL_ARMOR;
		STR += 1;
		MAX = 36;
		MIN = 8;
	}

	public MultiplelayerArmor() {
		super(4,0.4f,-0.5f,3);
	}

	@Override
	public Item upgrade(boolean hasglyph) {
		MIN += 1;
		MAX += 1;
		return super.upgrade(hasglyph);
	}
}
