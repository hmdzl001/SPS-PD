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
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class ShoppingCart extends Bag {

	{
		//name = "shopping cart";
		image = ItemSpriteSheet.SHOPCART;

		size = 25;
	}

	
	@Override
	public boolean grab(Item item) {
		return item instanceof Food ;	
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	@Override
	public boolean doPickUp( Hero hero ) {

		return hero.belongings.getItem( ShoppingCart.class ) == null && super.doPickUp( hero ) ;

	}

}

