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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

import java.util.ArrayList;

public class SoulCollect extends Item {

	{
		//name = "Soul collect";
		image = ItemSpriteSheet.POCKETBALL_FULL;
		stackable = false;
	}

	
	public static final String AC_BREAK = "BREAK";
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.dungeondepth <26) {
			actions.add(AC_BREAK);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_BREAK)) {
			curUser = hero;
		   GLog.w(Messages.get(this, "win"));
		   //Statistics.archersKilled += 51;
		   //Statistics.skeletonsKilled += 51;
		   //Statistics.piranhasKilled += 51;
		   //Statistics.goldThievesKilled += 51;
			Badges.validateOtilukeRescued();
			curUser.sprite.operate(curUser.pos);
			detach(hero.belongings.backpack);
			curUser.spendAndNext(1f);
			curUser.busy();

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
}
