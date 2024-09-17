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
package com.hmdzl.spspd.items.medicine;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class GoldenJelly extends Pill {

	{
		//name = "golden jelly mushroom";
		image = ItemSpriteSheet.MUSHROOM_GOLDENJELLY;
		 
	}

	public void doEat2() {
				for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, GrowSeed.class).set(10f);
				}
				Buff.affect(curUser, Vertigo.class, 10f);
	}	
	
	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public GoldenJelly() {
		this(1);
	}

	public GoldenJelly(int value) {
		this.quantity = value;
	}
}
