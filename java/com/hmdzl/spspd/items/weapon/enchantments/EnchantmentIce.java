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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ICE_DAMAGE;

public class EnchantmentIce extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x0044FF);

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
		int level = Math.min(20, attacker.HT/10);
		int maxdmg = level + weapon.level;
		defender.damage((int)(Random.Int(level,maxdmg)*0.75), ICE_DAMAGE);
		if(fcb != null && Random.Int(2) == 1){
			defender.damage((int)(Random.Int(level,maxdmg)*0.50), ICE_DAMAGE);
		}
		Buff.prolong(defender, Wet.class, 3f);
		Buff.prolong(defender, Cold.class, 3f);
		if (Random.Int(3) == 1) {
			Buff.affect(defender, Frost.class, Frost.duration(defender) * Random.Float(2f, 4f));
			defender.sprite.emitter().burst(SnowParticle.FACTORY, 5);
		}
			return true;

	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}

}
