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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

public class ReturnBeacon extends Item {

	
	private static final String TXT_INFO = "Return beacon is an intricate magic device, that allows you to return to a place you have already been.";

	public static final float TIME_TO_USE = 1;

	//public static final String AC_SET = "SET";
	public static final String AC_RETURN = "RETURN";

	//private int returnDepth = -1;
	//private int returnPos;

	{
		name = "return beacon";
		image = ItemSpriteSheet.BEACON;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_RETURN);	
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

	     if (action == AC_RETURN) {

			
				Buff buff = Dungeon.hero
						.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				InterlevelScene.mode = InterlevelScene.Mode.RETURNSAVE;
				InterlevelScene.returnDepth = 1;
				InterlevelScene.returnPos = 1;
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


	@Override
	public String info() {
		return TXT_INFO;
	}
}
