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
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;

public class ExProtect extends Buff {

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)){
		if (target instanceof Mob) {
			((Mob) target).state = ((Mob) target).SLEEPING;
		}
		target.paralysed++;
		return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		if (target.paralysed > 0)
			target.paralysed--;
		super.detach();
	}

	@Override
	public int icon() {
		return BuffIndicator.MAGIC_SLEEP;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}
}