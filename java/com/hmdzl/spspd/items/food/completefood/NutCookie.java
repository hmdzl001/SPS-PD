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

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class NutCookie extends CompleteFood {
	
	{
		//name = "perfect food";
		image = ItemSpriteSheet.NUT_COOKIE;
		energy = 10;
		hornValue = 1;
	}
	
	public NutCookie() {
		this(6);
	}

	public NutCookie(int number) {
		super();
		quantity = number;
	}

	public void doEat() {
			if (Random.Int(10) == 0){
				Buff.affect(curUser,GlassShield.class);
			} else switch (Random.Int(4)){
				case 0: Buff.affect(curUser,MechArmor.class).level(10);
						break;
				case 1: Buff.affect(curUser,EnergyArmor.class).level(10);
					break;
				case 2: Buff.affect(curUser,ShieldArmor.class).level(10);
					break;
				case 3: Buff.affect(curUser,MagicArmor.class).level(10);
					break;
			}
	}

	@Override
	public Item random() {
		quantity = 6;
		return this;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
	
}
