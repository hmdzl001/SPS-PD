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
package com.hmdzl.spspd.change.items.weapon.enchantments;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Chill;
import com.hmdzl.spspd.change.actors.buffs.Cold;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.buffs.Wet;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.effects.particles.SnowParticle;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class EnchantmentIce2 extends Weapon.Enchantment {

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
		int lvl = Math.max(0, weapon.level);
		Buff.prolong(defender, Wet.class, Math.min(30,lvl+2));
		Buff.prolong(defender, Cold.class, Math.min(30,lvl+2));
		defender.sprite.emitter().burst(SnowParticle.FACTORY, 5);
		
		return true;

	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}

}
