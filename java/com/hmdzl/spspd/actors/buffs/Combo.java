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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.utils.GLog;

public class Combo extends Buff {

	private static String TXT_COMBO = "%d hit combo!";

	public int count = 0;

	@Override
	public int icon() {
		return BuffIndicator.COMBO;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	public int hit(Char enemy, int damage) {

		count++;

		if (count >= 3) {

			Badges.validateMasteryCombo(count);

			GLog.p( Messages.get(this, "combo", count));
			postpone(1.41f - Math.min(0.8f,count / 20f));
			return (int) (damage * (count - 2) / 10f);

		} else {

			postpone(1.1f);
			return 0;

		}
	}

	@Override
	public boolean act() {
		detach();
		return true;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc") +
				(count <= 2 ?
						Messages.get(this, "notenough") :
						Messages.get(this, "bonusdmg", ((count - 2) * 20f)));
	}	
	
}
