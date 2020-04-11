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
package com.hmdzl.spspd.items;

import java.util.ArrayList;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.utils.Random;

public class UpgradeBlobViolet extends Item {

	private static final float TIME_TO_INSCRIBE = 2;

	private static final String AC_INSCRIBE = "INSCRIBE";
	
	private static final int upgrades = 5;

	{
		//name = "violet upgrade goo";
		image = ItemSpriteSheet.UPGRADEGOO_VIOLET;

		stackable = true;

		 
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_INSCRIBE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_INSCRIBE) {

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEABLE,
					Messages.get(ScrollOfUpgrade.class,"prompt"));

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	private void upgrade(Item item) {
if (!(Dungeon.hero.heroClass == HeroClass.FOLLOWER ) || (Dungeon.hero.heroClass == HeroClass.FOLLOWER && Random.Int(10)>=1 ))
		detach(curUser.belongings.backpack);

		GLog.w(Messages.get(ScrollOfUpgrade.class,"looks_better", item.name()));

		if (item.reinforced){		
			item.upgrade(upgrades);
			} else {
			item.upgrade(Math.min(upgrades, 15-item.level));
			}
		
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);
		Badges.validateItemLevelAquired(item);
		
		curUser.spend(TIME_TO_INSCRIBE);
		curUser.busy();
		
	}

	@Override
	public int price() {
		return 30 * quantity;
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				UpgradeBlobViolet.this.upgrade(item);
			}
		}
	};
}
