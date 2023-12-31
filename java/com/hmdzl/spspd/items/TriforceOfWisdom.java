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
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class TriforceOfWisdom extends Item {

	{
		//name = "Triforce of Wisdom";
		image = ItemSpriteSheet.ATRIFORCE;

		stackable = false;
		unique = true;
	}
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		return actions;
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
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {

			if (Dungeon.depth != null && Dungeon.dungeondepth ==33) {
				Dungeon.triforceofwisdom= true;							
			}

			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void doDrop(Hero hero) {
		
			if (Dungeon.depth != null && Dungeon.dungeondepth ==33) {
				Dungeon.triforceofwisdom= false;							
			}
		
			super.doDrop(hero);
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}
}
