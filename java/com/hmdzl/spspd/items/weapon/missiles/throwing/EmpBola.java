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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class EmpBola extends TossWeapon {

	{
		//name = "bola";
		image = ItemSpriteSheet.BOLA;

		STR = 10;

		MIN = 5;
		MAX = 10;
	}

	public EmpBola() {
		this(1);
	}

	public EmpBola(int number) {
		super();
		quantity = number;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		super.proc(attacker, defender, damage);
		Buff.prolong(defender, Cripple.class, Cripple.DURATION);
		Buff.affect(defender, Shocked.class).level(5);
		Buff.detach(defender, EnergyArmor.class);
		if(defender.properties().contains(Char.Property.MECH)){
			defender.damage(defender.HT/3,this);
		}
	}

	@Override
	public Item random() {
		quantity = Random.Int(2, 5);
		return this;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
}
