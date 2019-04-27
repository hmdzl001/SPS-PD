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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;

import com.hmdzl.spspd.change.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class SoulCollect extends Item {

	{
		//name = "Soul collect";
		image = ItemSpriteSheet.SOUL_COLLECT;
		stackable = false;
	}

	
	public static final String AC_BREAK = "BREAK";
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.depth<26) {
			actions.add(AC_BREAK);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_BREAK)) {

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
