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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.misc.BigBattery;
import com.hmdzl.spspd.windows.WndBag;

public class ScrollOfUpgrade extends InventoryScroll {

	{
		//name = "Scroll of Upgrade";
		//inventoryTitle = "Select an item to upgrade";
		mode = WndBag.Mode.UPGRADEABLE;
		consumedValue = 15;
		initials = 14;
	}

	@Override
	protected void onItemSelected(Item item) {

		//ScrollOfRemoveCurse.uncurse(Dungeon.hero, item);
		item.upgrade();
		upgrade(curUser);
		//GLog.p(Messages.get(this, "looks_better", item.name()));
		//
		ScrollOfUpgrade sup = Dungeon.hero.belongings.getItem(ScrollOfUpgrade.class);
		if( sup!=null && ShatteredPixelDungeon.allin()) {
			if (!((Scroll) curItem).ownedByBook ) {
				int lvl = item.level;
				int maxlvl = item.reinforced ? item.level + sup.quantity() : 15;

				int uptime = sup.quantity;
				if (lvl < maxlvl && uptime > maxlvl - lvl) {
					for (int i = 0; i < maxlvl - lvl; i++) {
						item.upgrade();
						upgrade(curUser);
						//for (Item sup : Dungeon.hero.belongings.backpack) {
						//	 if (sup instanceof ScrollOfUpgrade) {
						sup.detach(Dungeon.hero.belongings.backpack);
						//	 }
						// }
					}
				} else {
					for (int i = 0; i < uptime; i++) {
						item.upgrade();
						upgrade(curUser);
						//for (Item sup : Dungeon.hero.belongings.backpack) {
						//	 if (sup instanceof ScrollOfUpgrade) {
						sup.detach(Dungeon.hero.belongings.backpack);
						//	 }
						// }
					}
				}
			} else {
				//do nothing
			}

		}


		readAnimation();
		Badges.validateItemLevelAquired(item);
	}

	public static void upgrade(Hero hero) {
		hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
	}
	
	@Override
	public void empoweredRead() {
		///DewVial.uncurse(hero, hero.belongings.weapon, hero.belongings.armor, hero.belongings.misc1, hero.belongings.misc2, hero.belongings.misc3);

	}	

	@Override
	public int price() {
		return 100 * quantity;
	}
}
