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
import com.hmdzl.spspd.actors.buffs.MoonFury;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class FullMoonberry extends Fruit {

	{
		//name = "dungeon full moon berry";
		image = ItemSpriteSheet.FULLMOONBERRY;
		energy = 50;
		hornValue = 1;
		 
	}

	public void doEat() {

				switch (Random.Int(2)) {
				case 0:
					Buff.affect(curUser, MoonFury.class);
					Buff.affect(curUser, FullMoonStrength.class);
					Buff.affect(curUser, Light.class, Light.DURATION);
					break;
				case 1:
					Buff.affect(curUser, MoonFury.class);
					Buff.affect(curUser, FullMoonStrength.class);
					Buff.affect(curUser, Barkskin.class).level(curUser.lvl);
					Buff.affect(curUser, Light.class, Light.DURATION);
					break;
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
