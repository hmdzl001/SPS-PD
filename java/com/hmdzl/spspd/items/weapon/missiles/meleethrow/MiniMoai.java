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
package com.hmdzl.spspd.items.weapon.missiles.meleethrow;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class MiniMoai extends MeleeThrowWeapon {

	{//name = "MiniMoai";
		image = ItemSpriteSheet.MOAI;
		MIN=10;
		MAX=10;
	}

	public MiniMoai() {
		super(1);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		if (Random.Int(10)> 7){
		Buff.prolong(defender, Charm.class, 3 ).object = attacker.id();}
		super.proc(attacker, defender, damage);
	}

	@Override
	public int price() {
		return quantity * 100;
	}
}

