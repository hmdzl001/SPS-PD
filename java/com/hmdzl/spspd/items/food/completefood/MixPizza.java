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

import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class MixPizza extends CompleteFood {
	
	{
		//name = "perfect food";
		image = ItemSpriteSheet.MIX_PIZZA;
		energy = 50;
		hornValue = 2;
	}
	
	public MixPizza() {
		this(4);
	}

	public MixPizza(int number) {
		super();
		quantity = number;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			Buff.affect(hero, Bless.class,10f);
			Buff.affect(hero, Light.class,10f);
			Buff.affect(hero, HasteBuff.class,10f);
			Buff.affect(hero, Levitation.class,10f);
		}
	}

	@Override
	public Item random() {
		quantity = Random.Int(3,6);
		return this;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
	
}
