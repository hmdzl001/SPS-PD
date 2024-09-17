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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;

public class ScrollOfMagicalInfusion extends InventoryScroll {

	{
		//name = "Scroll of Magical Infusion";
		//inventoryTitle = "Select an item to infuse";
		mode = WndBag.Mode.ENCHANTABLE;
		consumedValue = 15;
		initials = 3;

		 
	}

	@Override
	protected void onItemSelected(Item item) {

		ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
		item.identify();
		if (item instanceof Weapon)
			((Weapon) item).upgrade(true);
		else
			((Armor) item).upgrade(true);

		GLog.p(Messages.get(this, "infuse", item.name()));

		ScrollOfMagicalInfusion smup = Dungeon.hero.belongings.getItem(ScrollOfMagicalInfusion.class);
		if (smup != null && ShatteredPixelDungeon.allin()) {
			if (!((Scroll) curItem).ownedByBook) {
				int lvl = item.level;
				int maxlvl = item.level + smup.quantity();
				if (lvl < maxlvl)
					for (int i = 0; i < maxlvl - lvl; i++) {
						item.upgrade();
						smup.detach(Dungeon.hero.belongings.backpack);
					}
			} else {
				//do nothing
			}
		}


		Badges.validateItemLevelAquired(item);



		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		readAnimation();
	}

	@Override
	public void empoweredRead() {
		//does nothing for now, this should never happen.
	}

	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}
}
