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
package com.hmdzl.spspd.items.food.fruit;

import com.hmdzl.spspd.actors.buffs.Barkskin;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FullMoonStrength;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Strength;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class FullMoonberry extends Fruit {

	{
		//name = "dungeon full moon berry";
		image = ItemSpriteSheet.SEED_FULLMOONBERRY;
		energy = 50;
		hornValue = 1;
		 
	}
		
	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

				switch (Random.Int(2)) {
				case 0:
					Buff.affect(hero, Strength.class);
					Buff.affect(hero, FullMoonStrength.class);
					Buff.affect(hero, Light.class, Light.DURATION);
					break;
				case 1:
					Buff.affect(hero, Strength.class);
					Buff.affect(hero, FullMoonStrength.class);
					Buff.affect(hero, Barkskin.class).level(hero.HT*2);
					Buff.affect(hero, Light.class, Light.DURATION);
					break;
				}
			}
	}	


	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public FullMoonberry() {
		this(1);
	}

	public FullMoonberry(int value) {
		this.quantity = value;
	}
}
