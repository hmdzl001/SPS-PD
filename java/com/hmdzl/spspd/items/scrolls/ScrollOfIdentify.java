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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.effects.Identification;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfIdentify extends InventoryScroll {

	{
		//name = "Scroll of Identify";
		//inventoryTitle = "Select an item to identify";
		mode = WndBag.Mode.UNIDENTIFED;
		consumedValue = 10;
		initials = 1;
		 
	}

	@Override
	public void empoweredRead() {
		ArrayList<Item> unIDed = new ArrayList<>();
		
		for( Item i : curUser.belongings){
			if (!i.isIdentified()){
				unIDed.add(i);
			}
		}
		
		if (unIDed.size() > 1) {
			Random.element(unIDed).identify();
			Sample.INSTANCE.play( Assets.SND_TELEPORT );
		}
		
		doRead();
	}	
	
	@Override
	protected void onItemSelected(Item item) {

		curUser.sprite.parent.add(new Identification(curUser.sprite.center()
				.offset(0, -16)));

		item.identify();
		GLog.i(Messages.get(this, "it_is", item));
		readAnimation();
		Badges.validateItemLevelAquired(item);
	}

	
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
