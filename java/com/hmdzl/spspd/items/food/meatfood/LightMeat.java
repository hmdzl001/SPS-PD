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
package com.hmdzl.spspd.items.food.meatfood;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class LightMeat extends MeatFood {

	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing(0xFFFF44);
	@Override
	public ItemSprite.Glowing glowing() {
		return YELLOW;
	}
	
	{
		//name = "frozen carpaccio";
		image = ItemSpriteSheet.MEAT;
		energy = 100;
		hornValue = 1;
	}

	@Override
	public int price() {
		return 3 * quantity;
	}

	public void doEat() {
		Dungeon.depth.drop(new SmallMeat(), curUser.pos).sprite.drop();
	}
	
	public static Food cook(MysteryMeat ingredient) {
		LightMeat result = new LightMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
	public static Food cook(Meat ingredient) {
		LightMeat result = new LightMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
}
