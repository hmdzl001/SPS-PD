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
package com.hmdzl.spspd.items.bags;

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.bombs.Bomb;


import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.OrbOfZot;



import com.hmdzl.spspd.items.challengelists.ChallengeList;
import com.hmdzl.spspd.items.journalpages.JournalPage;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.summon.CallCoconut;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class ScrollHolder extends Bag {

	{
		//name = "scroll holder";
		image = ItemSpriteSheet.HOLDER;

		size = 25;
	}

	@Override
	public boolean grab(Item item) {
        return item instanceof Scroll
                || item instanceof ActiveMrDestructo
                || item instanceof CallCoconut
                || item instanceof Mobile
                || item instanceof FairyCard
                || item instanceof OrbOfZot
                || item instanceof JournalPage
                || item instanceof ChallengeList;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	@Override
	public boolean doPickUp( Hero hero ) {

		return hero.belongings.getItem( ScrollHolder.class ) == null && super.doPickUp( hero ) ;

	}
}
