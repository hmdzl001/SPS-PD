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

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ShatteredAmmo extends MissileWeapon {

	{
		image = ItemSpriteSheet.AMMO;

		STR = 10;
		MIN = 10;
		MAX = 30;

		  // Finding them in bones would be semi-frequent and
						// disappointing.
	}

	public ShatteredAmmo() {
		this(1);
	}

	public ShatteredAmmo(int number) {
		super();
		quantity = number;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		super.proc(attacker, defender, damage);
		Buff.affect(defender, ArmorBreak.class,10f).level(30);
	}	
	
	@Override
	public Item random() {
		quantity = Random.Int(3, 5);
		return this;
	}

	@Override
	public int price() {
		return quantity * 10;
	}
}
