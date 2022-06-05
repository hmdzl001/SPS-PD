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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class TestWeapon extends MeleeWeapon {

	{
		//name = "TestWeapon";
		image = ItemSpriteSheet.ADAMANT_WEAPON;
		 
	}

	public TestWeapon() {
		super(1, 1f, 1f, 1);
		MIN = 10;
		MAX = 10;
	}
	

	@Override
	public Item upgrade(boolean enchant) {
		
		return super.upgrade(enchant);
    }

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		//Buff.affect(attacker,AttackUp.class,5).level(50);
		//Buff.affect(attacker,DefenceUp.class,5).level(50);

		//defender.damage(100,DamageType.ENERGY_DAMAGE);
		//defender.damage(100,DamageType.FIRE_DAMAGE);
		//defender.damage(100,DamageType.ICE_DAMAGE);
		//defender.damage(100,DamageType.SHOCK_DAMAGE);
		//defender.damage(100,DamageType.EARTH_DAMAGE);
		//defender.damage(100,DamageType.LIGHT_DAMAGE);
		//defender.damage(100,DamageType.DARK_DAMAGE);

		/*defender.damage(100,DamageType.EnergyDamage.class);
		defender.damage(100,DamageType.FireDamage.class);
		defender.damage(100,DamageType.IceDamage.class);
		defender.damage(100,DamageType.EarthDamage.class);
		defender.damage(100,DamageType.ShockDamage.class);
		defender.damage(100,DamageType.LightDamage.class);
		defender.damage(100,DamageType.DarkDamage.class);
*/
        damage = 0;

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	}
	
}
