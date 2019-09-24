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
package com.hmdzl.spspd.change.items.scrolls;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.messages.Messages;

public class ScrollOfMultiUpgrade extends InventoryScroll {

	private static final String TXT_LOOKS_BETTER = "your %s certainly looks better now";

	{
		name = "Scroll of Multi Upgrade";
		inventoryTitle = "Select an item to upgrade";
		mode = WndBag.Mode.UPGRADEABLE;
		consumedValue = 10;
        initials = 6;
		
		 
	}

	@Override
	protected void onItemSelected(Item item) {

		ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
		item.upgrade();

		for(int i=1; i<6; i++){
		  upgrade(curUser);
		}
		GLog.p(TXT_LOOKS_BETTER, item.name());

		Badges.validateItemLevelAquired(item);
	}

	@Override
	public void empoweredRead() {
		//does nothing for now, this should never happen.
	}

	public static void upgrade(Hero hero) {
		hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
	}



	@Override
	public String desc() {
		return "This scroll will upgrade a single item five times, improving its quality. A wand will "
				+ "increase in power and in number of charges; a weapon will inflict more damage "
				+ "or find its mark more frequently; a suit of armor will deflect additional blows; "
				+ "the effect of a ring on its wearer will intensify. Weapons and armor will also "
				+ "require less strength to use, and any curses on the item will be lifted.";
	}
}
