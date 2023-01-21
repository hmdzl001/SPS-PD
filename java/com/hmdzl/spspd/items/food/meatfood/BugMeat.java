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
package com.hmdzl.spspd.items.food.meatfood;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.BugSlow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class BugMeat extends MeatFood {

	{
		//name = "monster meat";
		image = ItemSpriteSheet.FUNNY_FOOD;
		energy = 50;
		hornValue = 1;
		stackable = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	//@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {
			if (hero.buff(BugSlow.class) == null){
				Buff.affect(hero,BugSlow.class);
		 	}
			return true;
		} else {
			return false;
		}
	}



	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {
		
			hero.TRUE_HT = Math.max(hero.TRUE_HT-1,1);
			hero.updateHT(true);
		
		}
	}	

	@Override
	public int price() {
		return 350 * quantity;
	}
		
}
