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
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class EnchantmentLight extends Weapon.Enchantment {

	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing(0xFFFF44);

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}

	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 20%
		// lvl 1 - 33%
		// lvl 2 - 43%
		FourClover.FourCloverBless fcb = attacker.buff(FourClover.FourCloverBless.class);
		int level = Math.min(20, attacker.HT / 10);
		int maxdmg = level + weapon.level;
		defender.damage((int) (Random.Int(level, maxdmg) * 0.75), this);
		if (fcb != null && Random.Int(2) == 1) {
			defender.damage((int) (Random.Int(level, maxdmg) * 0.50), this);
		}
		if (Random.Int(3) >= 1) {
			Buff.prolong(defender, Blindness.class, 4f);

		}


		//if (Random.Int(level + 15) >= 15) {
		//Buff.prolong(defender, Blindness.class,5f);
		//defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 6);

		//} else {
		return false;
		//}

	}
	@Override
	public Glowing glowing() {
		return YELLOW;
	}
}
