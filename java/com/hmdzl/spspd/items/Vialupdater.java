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
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class Vialupdater extends Item {

	{
		//name = "Vialupdater";
		image = ItemSpriteSheet.VIAL;

		stackable = false;
		unique = true;
	}

	public static final String AC_USE = "USE";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
        actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_USE)) {
			detach(curUser.belongings.backpack);
		   curUser = hero;
			//Dungeon.dewNorn=true;
            Dungeon.dewWater=true;
			//Dungeon.dewDraw=true;
		   Dungeon.wings=true;
		   curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f,10);

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
