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
import com.hmdzl.spspd.change.items.Rice;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.nornstone.NornStone;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.items.TriforceOfCourage;
import com.hmdzl.spspd.change.items.TriforceOfPower;
import com.hmdzl.spspd.change.items.TriforceOfWisdom;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;

public class SeedPouch extends Bag {

	{
		//name = "seed pouch";
		image = ItemSpriteSheet.POUCH;

		size = 22;
	}

	@Override
	public boolean grab(Item item) {
		if (item instanceof Plant.Seed
			|| item instanceof Rice
			|| item instanceof TriforceOfCourage
			|| item instanceof TriforceOfPower
			|| item instanceof TriforceOfWisdom
			|| item instanceof StoneOre
			|| item instanceof NornStone){
		return true;
		} else {
		return false;
		}
	}
	
	@Override
	public int price() {

	    return 50 * quantity;
	}

	@Override
	public boolean doPickUp( Hero hero ) {

		return hero.belongings.getItem( SeedPouch.class ) == null && super.doPickUp( hero ) ;

	}
}
