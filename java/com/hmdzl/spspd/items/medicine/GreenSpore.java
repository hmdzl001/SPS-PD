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
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class GreenSpore extends Pill {
	{
		//name = "Green Sporre";
		image = ItemSpriteSheet.MUSHROOM_LICHEN;
		 
	}
	
	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {
            if (!Dungeon.dewWater && !Dungeon.dewDraw){
				GLog.w(Messages.get(this,"not_time"));
				return;
			}
			else Buff.affect(hero, Dewcharge.class,100f);
		}
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

}