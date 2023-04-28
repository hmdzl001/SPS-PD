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
package com.hmdzl.spspd.items.food.completefood;

import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.Notice;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class FruitCandy extends CompleteFood {
	
	{
		//name = "perfect food";
		image = ItemSpriteSheet.FRUIT_CANDY;
		energy = 20;
		hornValue = 1;
	}
	
	public FruitCandy() {
		this(2);
	}

	public FruitCandy(int number) {
		super();
		quantity = number;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			switch (Random.Int(3)){
				case 0:
					Buff.affect(hero, HasteBuff.class,20f);
					Buff.affect(hero, Levitation.class, 20f);
					break;
				case 1:
					Buff.affect(hero, Notice.class, 30f);
					break;
				case 2:
					Buff.affect(hero, BerryRegeneration.class).level(Math.max(hero.HT/10,10));
					break;
			}
			
		}
	}

	@Override
	public Item random() {
		quantity = Random.Int(2, 4);
		return this;
	}


	@Override
	public int price() {
		return 10 * quantity;
	}
	
}
