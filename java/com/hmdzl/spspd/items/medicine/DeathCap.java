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
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class DeathCap extends Pill {

	{
		//name = "death cap mushroom";
		image = ItemSpriteSheet.MUSHROOM_DEATHCAP;
		 
	}

	public void doEat2() {
		   for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			   Buff.affect(mob, BeOld.class).set(50);
			   Buff.affect(mob, BeCorrupt.class).level(50);
		   }
		   int damage = Math.max(0,(Dungeon.dungeondepth) - Random.IntRange(0, curUser.drRoll()));
		   curUser.damage(Math.max(1,Math.round(curUser.HP/2)), this);
		   Buff.prolong(curUser, Cripple.class, Cripple.DURATION);
	}	

	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public DeathCap() {
		this(1);
	}

	public DeathCap(int value) {
		this.quantity = value;
	}
}
