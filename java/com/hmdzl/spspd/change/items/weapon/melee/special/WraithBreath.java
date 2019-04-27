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
package com.hmdzl.spspd.change.items.weapon.melee.special;

import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.watabou.utils.Random;

public class WraithBreath extends MeleeWeapon {

	{
		//name = "Wraith Breath";
		image = ItemSpriteSheet.WRAITHBREATH;
	}

	public WraithBreath() {
		super(2, 0.75f, 1f, 4);
	}

	@Override
	public Item upgrade(boolean enchant) {
		MAX+= 2;
		MIN+= 2;
		return super.upgrade(enchant);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(100) < 50) {
			Buff.affect(defender, Vertigo.class, Vertigo.duration(defender));
			Buff.affect(defender, Terror.class, Terror.DURATION).object = attacker.id();
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}
}