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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Ammo extends MissileWeapon {

	{
		//name = "throwing knive";
		image = ItemSpriteSheet.AMMO;

		STR = 10;
		MIN = 1;
		MAX = 10;

		bones = false; // Finding them in bones would be semi-frequent and
						// disappointing.
	}

	public Ammo() {
		this(1);
	}

	public Ammo(int number) {
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
		return quantity * 2;
	}
}
