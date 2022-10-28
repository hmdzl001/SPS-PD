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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.DewVial;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class Handcannon extends MeleeWeapon {

	{
		//name = "Handcannon";
		image = ItemSpriteSheet.HANDCANNON;
        defaultAction = AC_ONOFF;
	}
	
	public Boolean turnedOn = false;
	public static final String AC_ONOFF = "ONOFF";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	    actions.add(AC_ONOFF);
		return actions;
	}

	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_ONOFF)) {
			if (turnedOn == false){
			turnedOn=true;
			GLog.i(Messages.get(this, "power_on"));
			hero.next();
			} else {
			turnedOn=false;		
			GLog.i(Messages.get(this, "power_off"));
			hero.next(); 
		    } 
		} else {
			super.execute(hero, action);
		}
	}
	
	public Handcannon() {
		super(4, 0.7f, 2f, 7);
		reinforced = true;
	}
	
	private static final String TURNEDON = "turnedOn";

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

	public void proc( Char attacker, Char defender, int damage) {
		DewVial vial = Dungeon.hero.belongings.getItem(DewVial.class);
		if (vial != null) {
			if (vial.checkVol()> 10 && turnedOn) {
				int hits = 4;
				int dmg;
				for (int i = 1; i <= hits + 1; i++) {
					if (defender != null) {
						vial.sip();
						dmg = Math.max(1, (attacker.damageRoll() - i) * 2);
						defender.damage(dmg, this);
						GLog.h("Vrrrrrr!");
					} else {
						GLog.h("Xxxxxxx!");
					}
				}
			} else if (vial.checkVol()==0 && turnedOn) {
				GLog.n(Messages.get(this, "fuel"));
				turnedOn = false;
			}
		}

	}

	@Override
	public String desc() {
        DewVial vial = Dungeon.hero.belongings.getItem(DewVial.class);
		String info = super.desc();
        info += "\n\n" + vial.checkVol();
        if (turnedOn)
		info += "\n\n" + "ON";
		return info;
	}	
	
}
