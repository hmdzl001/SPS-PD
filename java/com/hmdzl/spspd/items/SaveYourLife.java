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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.scenes.SaveScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.io.IOException;
import java.util.ArrayList;

public class SaveYourLife extends Item {

	private static final String AC_SAVE = "SAVE" ;

	{
		//name = "pudding cup"、





		image = ItemSpriteSheet.TPTRAP;

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
			curUser = hero;
			try {
				Dungeon.saveAll();
				Game.switchScene(SaveScene.class);
			} catch (IOException e) {
				//
			}
		} else {

			super.execute(hero, action);

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
