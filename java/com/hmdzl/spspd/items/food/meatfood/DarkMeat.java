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

import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class DarkMeat extends MeatFood {
	
	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
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
		GLog.i( Messages.get(Meat.class, "better") );
			if (curUser.HP < curUser.HT) {
				curUser.HP = Math.min( curUser.HP + curUser.HT / 4, curUser.HT );
				curUser.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			}
	
	}
	
	public static Food cook(MysteryMeat ingredient) {
		DarkMeat result = new DarkMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
	public static Food cook(Meat ingredient) {
		DarkMeat result = new DarkMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
}
