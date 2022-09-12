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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.OnePunch;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class SeriousPunch extends Item {

	{
		//name = "SeriousPunch";
		image = ItemSpriteSheet.REDHAND;
	
		stackable = false;
		unique = true;
		defaultAction = AC_CAST;
		 
	}

	private static final String AC_CAST = "CAST";
	public int charge = 0;
	private static final String CHARGE = "charge";	

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(AC_CAST);
		actions.remove( AC_THROW );
		actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		super.execute(hero, action);
        if (action.equals(AC_CAST)) {
			Buff.affect(hero, OnePunch.class).level(charge);
			charge = 0;
        }
	}

	@Override
	public String status() {
		return Messages.format("%d", charge);
	}

	@Override
	public String desc() {
		String info = super.desc();
		info += "\n\n" + Messages.get(SeriousPunch.class, "charge",charge);
		return info;
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
	public int price() {
		return 30 * quantity;
	}

}

