/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SacrificeBook extends Item {

	public static final String AC_USE = "USE";
	{
		image = ItemSpriteSheet.SCR_BOOK;
		stackable=true;
		defaultAction = AC_USE;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE)) {
			curUser = hero;
			if (Dungeon.sacrifice == 0) {
				Dungeon.hero.TRUE_HT += 5;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "use_1"));
				Dungeon.hero.updateHT(true);

			} else if (Dungeon.sacrifice > 0) {
				Dungeon.hero.STR++;
				Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "use_2"));
			}
			if (Dungeon.sacrifice > 1) {
				Dungeon.hero.TRUE_HT -= Random.Int(5 * Dungeon.sacrifice, Dungeon.hero.TRUE_HT / 5);
				Dungeon.hero.updateHT(true);
				GLog.w(Messages.get(this, "use_lot"));
			}

			if (Dungeon.hero.HP > Dungeon.hero.TRUE_HT) {
				Dungeon.hero.HP = Dungeon.hero.TRUE_HT;
			}

			Dungeon.sacrifice++;
			detach(curUser.belongings.backpack);
			curUser.spendAndNext(1f);
			curUser.busy();
		} else {
			super.execute(hero, action);

		}
	}
		@Override
		public int price() {
			return 100 * quantity;
		}
	}
	

