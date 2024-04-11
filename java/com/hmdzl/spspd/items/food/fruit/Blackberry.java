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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Blackberry extends Fruit {

	{
		//name = "dungeon black berry";
		image = ItemSpriteSheet.BLACKBERRY;
		energy = 50;
		hornValue = 1;
		 
	}

	public void doEat() {
			switch (Random.Int(10)) {
			case 0:
			case 1:
				Buff.affect(curUser, MindVision.class, MindVision.DURATION);
				Dungeon.observe();
				Buff.affect(curUser, BerryRegeneration.class).level(Math.max(curUser.HT/8,20));
				break;
			 case 2: case 3: case 4: case 5:
			case 6: case 7: case 8: case 9:
				Buff.affect(curUser, BerryRegeneration.class).level(Math.max(curUser.HT/10,15));
				break;
			}

	}	

	@Override
	public int price() {
		return 10 * quantity;
	}
	
	public Blackberry() {
		this(1);
	}

	public Blackberry(int value) {
		this.quantity = value;
	}
}
