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
package com.hmdzl.spspd.items.food.vegetable;

import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class HealGrass extends Vegetable {

	{
		stackable = true;
		//name = "ration of food";
		image = ItemSpriteSheet.HEAL_L;
		energy = 20;
		hornValue = 1;
		 
	}

	public void doEat() {
			//hero.HP = Math.min(hero.HP + hero.HT/5, hero.HT);
			Buff.affect(curUser, BerryRegeneration.class).level(20);
			Buff.affect(curUser,MagicArmor.class).level(curUser.HT/5);

	}

	@Override
	public int price() {
		return 1 * quantity;
	}
}