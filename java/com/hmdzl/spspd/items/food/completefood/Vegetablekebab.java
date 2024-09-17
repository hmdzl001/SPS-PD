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
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Vegetablekebab extends CompleteFood {

	{
		//name = "vegetablekebab";
		image = ItemSpriteSheet.KEBAB;
		energy = 150;
		hornValue = 2;
		 
	}
	private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing(0x22CC44);
	@Override
	public ItemSprite.Glowing glowing() {
		return GREEN;
	}

	@Override
	public int price() {
		return 2 * quantity;
	}

	public void doEat() {
			Buff.affect(curUser,MagicArmor.class).level(curUser.HT/2);
			Buff.affect(curUser,AttackUp.class,50f).level(20);
	}
}