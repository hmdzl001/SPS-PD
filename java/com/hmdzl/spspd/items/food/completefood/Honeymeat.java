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

import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Honeymeat extends CompleteFood {

	{
		//name = "honeymeat";
		image = ItemSpriteSheet.HONEY_MEAT;
		energy = 150;
		hornValue = 3;
		 
	}

	public void doEat() {
			curUser.TRUE_HT = curUser.TRUE_HT + (Random.Int(3, 6));
			Buff.affect(curUser,AttackUp.class,50f).level(20);
			curUser.updateHT(true);

	}

	@Override
	public int price() {
		return 400 * quantity;
	}

}