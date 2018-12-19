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
package com.hmdzl.spspd.change.items.food.vegetables;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class BlueMilk extends Vegetable {

	{
		//name = "blue milk mushroom";
		image = ItemSpriteSheet.MUSHROOM_BLUEMILK;
		energy = 5;
		hornValue = 1;
		bones = false;
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
				Buff.affect(hero, Haste.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT);
				break;
			case 0: case 2: case 3: case 4: case 5: 
			case 6: case 7: case 8: case 9: case 10:
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, Slow.class, Slow.duration(mob));
					Buff.affect(mob, BerryRegeneration.class).level(mob.HT);
				}
				Buff.affect(hero, Haste.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT);
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
