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

import com.hmdzl.spspd.actors.buffs.Barkskin;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class EarthMeat extends MeatFood {

    private static ItemSprite.Glowing BROWN = new ItemSprite.Glowing( 0x996600 );
	@Override
	public ItemSprite.Glowing glowing() {
		return BROWN;
	}
	
	{
		//name = "frozen carpaccio";
		image = ItemSpriteSheet.MEAT;
		energy = 100;
		hornValue = 1;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {
            effect(hero);
		}
	}

	@Override
	public int price() {
		return 3 * quantity;
	}

    public static void effect(Hero hero){

		GLog.i( Messages.get(Meat.class, "hard") );
		Buff.affect( hero, Barkskin.class ).level( hero.lvl*3 );

	}
	
	public static Food cook(MysteryMeat ingredient) {
		EarthMeat result = new EarthMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
	public static Food cook(Meat ingredient) {
		EarthMeat result = new EarthMeat();
		result.quantity = ingredient.quantity();
		return result;
	}
}
