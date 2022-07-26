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

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TriforceOfCourage;
import com.hmdzl.spspd.items.TriforceOfPower;
import com.hmdzl.spspd.items.TriforceOfWisdom;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class WandHolster extends Bag {

	{
		//name = "wand holster";
		image = ItemSpriteSheet.HOLSTER;

		size = 25;
	}

	@Override
	public boolean grab(Item item) {
        return item instanceof Wand
                || item instanceof TriforceOfCourage
                || item instanceof TriforceOfPower
                || item instanceof TriforceOfWisdom
                || item instanceof SpAmmo
                || item instanceof GunWeapon;

	}

	@Override
	public boolean collect(Bag container) {
		if (super.collect(container)) {
			if (owner != null) {
				for (Item item : items) {
					if (item instanceof Wand){
					((Wand) item).charge(owner);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		for (Item item : items) {
			if (item instanceof Wand){
			((Wand) item).stopCharging();
			}
		}
	}

	@Override
	public int price() {
		return 50 * quantity;
	}	
	
    @Override
    public boolean doPickUp( Hero hero ) {

        return hero.belongings.getItem( WandHolster.class ) == null && super.doPickUp( hero ) ;

    }
	
}
