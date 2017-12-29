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

import java.io.IOException;
import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.scenes.AmuletScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

public class Amulet extends Item {

	private static final String AC_END = "END";

	{
		//name = "Amulet of Yendor";
		image = ItemSpriteSheet.AMULET;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_END);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_END) {

			showAmuletScene(false);

		} else {

		    super.execute(hero, action);

		}
	}

	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {
			showAmuletScene(true);
			if (!Statistics.amuletObtained) {
				Statistics.amuletObtained = true;
				Badges.validateVictory();
			}
			return true;
		} else {
			return false;
		}
	}

	private void showAmuletScene(boolean showText) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = !showText;
			Game.switchScene(AmuletScene.class);
		} catch (IOException e) {
		}
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}
}
