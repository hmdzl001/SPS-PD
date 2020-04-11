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
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class EnchantmentEnergy2 extends Weapon.Enchantment {

    private static ItemSprite.Glowing GRAY = new ItemSprite.Glowing( 0x888888 );

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 13%
		// lvl 1 - 22%
		// lvl 2 - 30%
		FourClover.FourCloverBless fcb = attacker.buff(FourClover.FourCloverBless.class);
		int lvl = Math.max(0, weapon.level);

		//int dmg = damage;
		//defender.damage(Random.Int(dmg/6), this);

		//if (Random.Int(level + 15) >= 15) {
		if(fcb != null && Random.Int(2) == 1){
			defender.damage(Random.Int(damage/6), this);
		}
			Buff.prolong(attacker, DefenceUp.class,5f).level(Math.min(30,lvl+1));
			attacker.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
			return true;
		//} else {
			//return false;
		//}
	}

	@Override
	public Glowing glowing() {
		return GRAY;
	}

}
