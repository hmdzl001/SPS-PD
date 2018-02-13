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
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.SpellSprite;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Nomnom extends Weapon.Enchantment {

	private static ItemSprite.Glowing RED = new ItemSprite.Glowing(0x660022);

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 8%
		// lvl 1 ~ 9%
		// lvl 2 ~ 10%
		int level = Math.max(0, weapon.level*3);
				
		if (Random.Int(level + 100) >= 75) {

			defender.damage(defender.HP, this);
		
			
			int maxValue = damage * (level + 2) / (level + 3);
			int effValue = Math.min(Random.IntRange(0, maxValue), attacker.HT-attacker.HP);

			if (effValue > 0) {

				attacker.HP += effValue;
				attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
				attacker.sprite.showStatus(CharSprite.POSITIVE,	Integer.toString(effValue));
			}
			
			attacker.buff(Hunger.class).satisfy(maxValue);
			SpellSprite.show(attacker, SpellSprite.FOOD);
		
		
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
