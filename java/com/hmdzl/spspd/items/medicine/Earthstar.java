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
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Earthstar extends Pill {

	{
		//name = "earthstar mushroom";
		image = ItemSpriteSheet.MUSHROOM_EARTHSTAR;
		 
	}

	public void doEat2() {
		   for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			   //mob.HP = Math.max(1, (int)(mob.HP/2));
			   Buff.affect(mob, Bleeding.class).set(Random.Int(mob.HP/8,mob.HP/4));

		   }
		   curUser.damage(Math.max(1,Math.round(curUser.HP/4)), this,1);
		   Buff.prolong(curUser, Blindness.class, Random.Int(5, 7));
	}	

	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public Earthstar() {
		this(1);
	}

	public Earthstar(int value) {
		this.quantity = value;
	}
}
