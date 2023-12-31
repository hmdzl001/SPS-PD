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
package com.hmdzl.spspd.items.weapon.missiles.arrows;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DamageUp;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.sprites.ItemSprite;

public class Arrows extends MissileWeapon {

	private int dmgmin;
	private int dmgmax;

	public Arrows(int dmgmin, int dmgmax) {
		super();

		this.dmgmin = dmgmin;
		this.dmgmax = dmgmax;

		MIN = bow!= null ? min()+ bow.MIN : min();
		MAX = bow!= null ? max() + 2*bow.MAX : max();

		ACU = bow!= null ? 10 : 1;
		DLY = bow!= null ? 1 : 0.2f;
	}

	private int min() {
		return dmgmin;
	}

	private int max() {
		return dmgmax;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public int price() {
		return quantity * 2;
	}

	public void proc(Char attacker, Char defender, int damage) {
		if (bow!= null) {
			Buff.affect(attacker, DamageUp.class).level(damage/2);
			//if (bow.enchantment != null) {
			//	bow.enchantment.proc(this, attacker, defender, damage);
			//}
		}

		super.proc(attacker, defender, damage);
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return GRAY;
	}
	private static ItemSprite.Glowing GRAY = new ItemSprite.Glowing( 0x888888 );

}
