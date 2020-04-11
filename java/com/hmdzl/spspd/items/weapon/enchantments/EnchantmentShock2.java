/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.effects.Lightning;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class EnchantmentShock2 extends Weapon.Enchantment {

private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing( 0x00FF00 );
	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}

	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 25%
		// lvl 1 - 40%
		// lvl 2 - 50%
		FourClover.FourCloverBless fcb = attacker.buff(FourClover.FourCloverBless.class);
		int level = Math.max(0, weapon.level);

		if (Random.Int(level + 15) >= 15 || (fcb != null && Random.Int(level + 15) >= 10)) {
		Buff.prolong(defender, Shocked.class,2f);
		return true;
		} else {
			return false;
		}
	}
	@Override
	public Glowing glowing() {
		return GREEN;
	}
}

