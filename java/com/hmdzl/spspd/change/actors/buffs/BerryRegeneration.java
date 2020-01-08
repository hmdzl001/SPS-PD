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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class BerryRegeneration extends Buff {
	
	private int regenleft = 0;
	
	private static final String REGENLEFT = "regenleft";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(REGENLEFT, regenleft);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		regenleft = bundle.getInt(REGENLEFT);
	}

	public int level() {
		return regenleft;
	}

	public void level(int value) {
		if (regenleft < value) {
			regenleft = value;
		}
	}

	@Override
	public int icon() {
		return BuffIndicator.REGEN;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", regenleft);
	}
	
	@Override
	public boolean act() {
		if (target.isAlive()) {
			if (Dungeon.hero.subClass == HeroSubClass.PASTOR && target.HP < target.HT*1.5){
			  target.HP += 2 * (5+Math.round(regenleft/25));
			} else if (target.HP < target.HT) {
				target.HP += Math.min(5+Math.round(regenleft/25),(target.HT-target.HP));
			}
			
				spend(TICK);
				if (--regenleft <= 0) {
					detach();
				}

			} else {

				detach();

			}

			return true;
		}
	
}
