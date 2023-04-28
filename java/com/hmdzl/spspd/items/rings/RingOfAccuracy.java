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
package com.hmdzl.spspd.items.rings;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

public class RingOfAccuracy extends Ring {

	{
		//name = "Ring of Accuracy";
		initials = 0;
		sname = "ACU";
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats",level,Math.min(3,(int)(level/10)));
		} else {
			return "???";
		}
	}	
		
	
	@Override
	protected RingBuff buff() {
		return new Accuracy();
	}

	public class Accuracy extends RingBuff {
	}
}
