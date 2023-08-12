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
package com.hmdzl.spspd.items.weapon.melee.relic;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MagicImmunity;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;

import java.util.ArrayList;

public class CromCruachAxe extends RelicMeleeWeapon {

	public CromCruachAxe() {
		super(6, 1.2f, 1f, 1);
		// TODO Auto-generated constructor stub
	}

	
	{
		image = ItemSpriteSheet.CROMAXE;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		 
		
  }
		
	public static final String AC_DISPEL = "DISPEL";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_DISPEL);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_DISPEL)) {
			charge = 0;
			Buff.prolong(hero, MagicImmunity.class, 2f*(level/10));	
		} else
			super.execute(hero, action);
	}

	
	public class DispelCounter extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=Math.min(level, 10);
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}
		
		@Override
		public String toString() {
			return "Dispel";
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.NONE;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}
	
	
	
	
	@Override
	protected WeaponBuff passiveBuff() {
		return new DispelCounter();
	}
	
}


