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
package com.hmdzl.spspd.items.weapon.melee.block;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class TenguSword extends MeleeWeapon {

	{
		//name = "TenguSword";
		image = ItemSpriteSheet.EMPTY_SWORD;
	}

	public TenguSword() {
		super(2, 1.2f, 0.8f, 1);
	}

	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 2;
		return super.upgrade(enchant);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {

		int DMG = damage;

		if (Random.Int(100) < 30) {
			Buff.affect(defender, Bleeding.class).set(Random.Int(2,DMG));
		}		
		
		if (Random.Int(100) < 10 && attacker.buff(ShieldArmor.class) == null) {
		Buff.affect(attacker, ShieldArmor.class).level(DMG);
		}

		if (enchantment != null) {
		enchantment.proc(this, attacker, defender, damage);
		}

	}

}
