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
package com.hmdzl.spspd.items.weapon.enchantments;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;

public class CromLuck extends Weapon.Enchantment {

	private static ItemSprite.Glowing RED = new ItemSprite.Glowing(0x800000);

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		int level = Math.max(0, weapon.level);

		int dmg = damage;
		for (int i = 1; i <= level + 1; i++) {
			dmg = Math.max(dmg, attacker.damageRoll());
		}

		if (dmg > damage) {
			defender.damage(dmg - damage, weapon,3);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Glowing glowing() {
		return RED;
	}
}
