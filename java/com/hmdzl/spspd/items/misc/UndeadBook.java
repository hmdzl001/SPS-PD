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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;

public class UndeadBook extends Item {

    public static final String AC_READ = "READ";
    
	public static final String AC_BLESS = "BLESS";

	{
		//name = "UndeadBook";
		image = ItemSpriteSheet.UNDEAD_BOOK;

		unique = true;
        charge = 0;
		//defaultAction = AC_READ;
	}

	public static int charge;
	private static final String CHARGE = "charge";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (hero.HT > 10) actions.add(AC_READ);
		if (charge < hero.lvl) actions.add(AC_BLESS);
		return actions;
	}
	
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
	public void execute(Hero hero, String action) {

		if (action.equals(AC_READ)) {
			hero.HT -= 5;
			Buff.affect(hero, Dewcharge.class, 100f);
			GLog.p(Messages.get(this, "bless"));
		}
				
		if (action.equals(AC_BLESS)) {
			    charge ++;
                Dungeon.level.drop(new Ankh(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				GLog.p(Messages.get(this, "1up"));
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
	public int price() {
		return 50 * quantity;
	}

}
