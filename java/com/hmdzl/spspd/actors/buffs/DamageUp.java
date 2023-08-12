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

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class DamageUp extends FlavourBuff {

	private int level = 0;
	private static final String LEVEL = "level";
	protected float left;
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEVEL, level);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt(LEVEL);
	}

    {
		type = buffType.POSITIVE;
	}

	public boolean act() {
		spend(TICK);
			return true;
	}
	@Override
	public int icon() {
		return BuffIndicator.ATTACK_UP;
	}

	public int level() {
		return level;
	}

	public void level(int value) {
		if (level < value) {
			level = value;
		}
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",level());
	}

}
