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
package com.hmdzl.spspd.items.weapon.missiles.throwing;

import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ErrorAmmo extends TossWeapon {

	{
		//name = "error ammo";
		image = ItemSpriteSheet.ERROR_AMMO;

		STR = 0;
		MIN = 10000;
		MAX = 10000;

		  // Finding them in bones would be semi-frequent and
						// disappointing.
	}

	public ErrorAmmo() {
		this(1);
	}

	public ErrorAmmo(int number) {
		super();
		quantity = number;
	}

	@Override
	public Item random() {
		quantity = Random.Int(5, 8);
		return this;
	}
	@Override
	public int price() {
		return quantity * 0;
	}
}
