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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.scenes.LoadSaveScene;
import com.hmdzl.spspd.scenes.Pudding_CupScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.io.IOException;
import java.util.ArrayList;

import static com.hmdzl.spspd.Badges.validateLearn;

public class PuddingCup extends Item {

	private static final String AC_SAVE = "SAVE" ;

	{
		//name = "pudding cup";
		image = ItemSpriteSheet.PUDDING_CUP;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_SAVE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_SAVE)) {
			//showPudding_cupScene();
			curUser = hero;
			validateLearn();
			detach(curUser.belongings.backpack);
			try {
				Dungeon.saveAll();
			} catch (IOException e) {
				//
			}
			Dungeon.canSave=true;
			Game.switchScene(LoadSaveScene.class);

		} else {

			super.execute(hero, action);

		}
	}


	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {
			validateLearn();
			showPudding_cupScene();
			return true;
		} else {
			return false;
		}
	}

	private void showPudding_cupScene() {
		try {
			Dungeon.saveAll();
			Game.switchScene(Pudding_CupScene.class);
		} catch (IOException e) {
			ShatteredPixelDungeon.reportException(e);
		}
	}

	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
}
