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
package com.hmdzl.spspd.change.items.bags;

import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.TriforceOfCourage;
import com.hmdzl.spspd.change.items.TriforceOfPower;
import com.hmdzl.spspd.change.items.TriforceOfWisdom;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.change.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;

public class WandHolster extends Bag {

	{
		//name = "wand holster";
		image = ItemSpriteSheet.HOLSTER;

		size = 25;
	}

	@Override
	public boolean grab(Item item) {
		if (item instanceof Wand
				|| item instanceof TriforceOfCourage
				|| item instanceof TriforceOfPower
				|| item instanceof TriforceOfWisdom
				|| item instanceof SpAmmo
				|| item instanceof GunWeapon){
			return true;
		} else {
			return false;
		}

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
