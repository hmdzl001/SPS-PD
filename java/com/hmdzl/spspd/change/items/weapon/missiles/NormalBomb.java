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
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class NormalBomb extends MissileWeapon {
	
	public static final float DURATION = 3f;

	{
		//name = "Normal Bomb";
		image = ItemSpriteSheet.BOMB;

		STR = 13;

		MIN = 2;
		MAX = 6;
	}

	public NormalBomb() {
		this(1);
	}

	public NormalBomb(int number) {
		super();
		quantity = number;
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		Buff.prolong(defender, Paralysis.class, DURATION);
		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(5, 15);
		return this;
	}

	@Override
	public int price() {
		return 6 * quantity;
	}
}
