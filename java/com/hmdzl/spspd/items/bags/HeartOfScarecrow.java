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
package com.hmdzl.spspd.items.bags;


import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.ShadowEaterKey;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class HeartOfScarecrow extends Bag {

	{
		//name = "HeartOfScarecrow";
		image = ItemSpriteSheet.H_O_SCARECROW;

		size = 30;
	}

	
	@Override
	public boolean grab(Item item) {
        return item instanceof MeleeWeapon
                || item instanceof MissileWeapon
                || item instanceof RelicMeleeWeapon
                || item instanceof Armor
                || item instanceof ShadowEaterKey;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	//@Override
	//public boolean doPickUp( Hero hero ) {

	//	return hero.belongings.getItem( HeartOfScarecrow.class ) == null && super.doPickUp( hero ) ;

	//}

}

