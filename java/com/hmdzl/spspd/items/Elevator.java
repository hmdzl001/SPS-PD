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
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class Elevator extends Item {

	public static final String AC_UP = "UP";

    public static final String AC_DOWN = "DOWN";


	{
		//name = "Elevator";
		image = ItemSpriteSheet.ELEVATOR;

		stackable = true;

	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.dungeondepth < 26 && Dungeon.dungeondepth > 1 ){
		actions.add(AC_UP);
		actions.add(AC_DOWN);}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_UP)) {
			PocketBallFull.removePet(hero);
			InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
			Game.switchScene(InterlevelScene.class);
		} else if (action.equals(AC_DOWN)) {
			PocketBallFull.removePet(hero);
			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene(InterlevelScene.class);
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
