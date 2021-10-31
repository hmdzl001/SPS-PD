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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Random;

public class DelayProtect extends Buff {

	{
		type = buffType.NEUTRAL;
	}


	@Override
	public int icon() {
		return BuffIndicator.DROWSY;
	}

	@Override
	public boolean attachTo(Char target) {
		if (!target.immunities().contains(GlassShield.class)
				&& super.attachTo(target)) {
			if (cooldown() == 0)
				spend(Random.Int(1, 2));
			return true;
		}
		return false;
	}

	@Override
	public boolean act() {
		Buff.affect(target, GlassShield.class);

		detach();
		return true;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns(cooldown()+1));
	}
}
