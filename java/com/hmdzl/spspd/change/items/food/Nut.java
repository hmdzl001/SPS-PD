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
package com.hmdzl.spspd.change.items.food;

import com.hmdzl.spspd.change.actors.buffs.Barkskin;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Nut extends Food {

	{
		//name = "dungeon nut";
		image = ItemSpriteSheet.SEED_TOASTEDDUNGEONNUT;
		energy = 10;
		//message = "Crunch Crunch.";
		hornValue = 1;
		 
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

		    hero.HP += 1;
			switch (Random.Int(10)) {
			case 0:
				Buff.affect(hero, Barkskin.class).level(hero.HT / 4);
				break;
			}
		}
	}	
	

	@Override
	public int price() {
		return 1 * quantity;
	}
}
