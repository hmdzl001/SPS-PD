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
package com.hmdzl.spspd.change.items.weapon.melee.special;

import java.util.ArrayList;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.MineSentinel;
import com.hmdzl.spspd.change.actors.mobs.Otiluke;
import com.hmdzl.spspd.change.actors.mobs.Zot;
import com.hmdzl.spspd.change.actors.mobs.ZotPhase;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Handcannon extends MeleeWeapon {

	{
		//name = "Handcannon";
		image = ItemSpriteSheet.HANDCANNON;
		
	}
	
	public Boolean turnedOn = false;
	public static final String AC_ON = "ON";
	public static final String AC_OFF = "OFF";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if(turnedOn){actions.add(AC_OFF);}else{actions.add(AC_ON);}
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_ON)) {
			turnedOn=true;
			GLog.i("The handcannon is overload!"); 
			hero.next();

		} else if (action.equals(AC_OFF)) {
			turnedOn=false;		
			GLog.i("The handcannon become normal.");
			hero.next();
		} else {
			super.execute(hero, action);
		}
	}
	

	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	@Override
	public Item upgrade(boolean enchant) {
		
		return super.upgrade(false);		
	}

	public Item safeUpgrade() {
		return upgrade(enchantment != null);
	}
	
	public Handcannon() {
		super(4, 0.7f, 2f, 7);
	}
	
	private static final String TURNEDON = "OVERLOADOn";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TURNEDON, turnedOn);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		turnedOn = bundle.getBoolean(TURNEDON);
	}
	
}
