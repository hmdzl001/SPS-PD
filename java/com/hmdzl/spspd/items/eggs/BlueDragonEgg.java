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
package com.hmdzl.spspd.items.eggs;

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class BlueDragonEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;

	public static final int LERY_FIRE = 10;
	

		{
		//name = "shadow dragon egg";
		image = ItemSpriteSheet.BLUE_DRAGONEGG;

		stackable = false;
		}

		public int moves = 0;
		public int burns = 0;
		public int freezes = 20;
		public int poisons = 0;
		public int lits = 0;
		public int summons = 0;
		public int light = 0;

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {

			BlueDragon pet = new BlueDragon();
			eggHatch(pet);
		  
		    hero.next();
		
		} else {

			super.execute(hero, action);

		}

	}
		
	@Override
	public int price() {
		return 500 * quantity;
	}

}
