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
import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class BlueMilk extends Pill {

	{
		//name = "blue milk mushroom";
		image = ItemSpriteSheet.MUSHROOM_BLUEMILK;
		 
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_EAT)) {
			
			if (Dungeon.bossLevel()){
				GLog.w(Messages.get(Food.class,"bosslevel"));
				return;
			}

		}
		
	   if (action.equals(AC_EAT)) {
		
		   switch (Random.Int(10)) {
			case 1:
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, Slow.class, Slow.duration(mob));
				}
				Buff.affect(hero, HasteBuff.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HP/2);
				break;
			case 0: case 2: case 3: case 4: case 5: 
			case 6: case 7: case 8: case 9: case 10:
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, Slow.class, Slow.duration(mob));
					Buff.affect(mob, BerryRegeneration.class).level(mob.HP/2);
				}
				Buff.affect(hero, HasteBuff.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HP/2);
				break;
			}
		}
	   
	   super.execute(hero, action);
	}	
	
	@Override
	public int price() {
		return 5 * quantity;
	}
	
	public BlueMilk() {
		this(1);
	}

	public BlueMilk(int value) {
		this.quantity = value;
	}
}
