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
package com.hmdzl.spspd.change.items.food.fruit;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Cloudberry extends Fruit {

	{
		//name = "dungeon cloud berry";
		image = ItemSpriteSheet.SEED_CLOUDBERRY;
		energy = 50;
		hornValue = 1;
		 
	}

	private int duration = 10;
	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(10)) {
			case 0: case 1: case 2: case 3: case 4: case 5: 
				Buff.affect(hero, Haste.class, Haste.DURATION);
				break;
			case 6: case 7: case 8: 
				 Buff.affect(hero, Haste.class, Haste.DURATION);
				 if(Dungeon.depth<51){Buff.affect(hero, Levitation.class, duration);
				}
				
				break;
			 case 9: case 10:
				 Buff.affect(hero, Haste.class, Haste.DURATION);
				 if(Dungeon.depth<51){Buff.affect(hero, Levitation.class, duration*2);
				}
				
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT);
				break;
			}
		}
	}	

	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public Cloudberry() {
		this(1);
	}

	public Cloudberry(int value) {
		this.quantity = value;
	}
}
