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
package com.hmdzl.spspd.items.journalpages;

import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class SafeSpotPage extends JournalPage {

	{
		//name = "safe spot";
		image = ItemSpriteSheet.JOURNAL_PAGE;
        room=0;
		
		stackable = false;
	}

	@Override
	public String info() {
		switch(Statistics.roomType){
			case 0:
				return Messages.get(this, "grassroom_desc");
			case 1:
				return Messages.get(this, "forestroom_desc");
			case 2: default:
				return Messages.get(this, "cityroom_desc");
		}
	}

}
