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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;

public class Shield extends Buff {

	public static float LEVEL = 0.4f;
	
	private int hits = (Dungeon.checkNight() ? Math.max(8, Math.round(Statistics.deepestFloor/5)+9) : Math.max(2, Math.round(Statistics.deepestFloor/5)+3));
	//private int hits = Math.max(2, Math.round(Statistics.deepestFloor/5)+3);
	
	{
		type = buffType.POSITIVE;
	}		
	
	@Override
	public int icon() {
		return BuffIndicator.DISPEL;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}	
	
	@Override
	public void detach() {
		hits--;
		if(hits==0){
		super.detach();
		}
	}
	
}
