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

public class BeTired extends Buff {

    public static int level;

    {
		type = buffType.NEGATIVE;
	}

	private static final String LEVEL = "level";
	protected int left;
	private static final String LEFT = "left";
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEVEL, level);
		bundle.put(LEFT, left);
	}	

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt(LEVEL);
		left = bundle.getInt(LEFT);
	}	

	public boolean act() {

		spend(TICK);
		left -= TICK;
		if (left <= 0)
			detach();
		if(level>15){
			level=0;
			target.damage(target.HT/10,target);
		detach();}
		return true;

	}

	public int set() {
		return left;
	}

	public void set(int value) {
		if (left < value) {
			left = value;
		}
	}
	
	@Override
	public int icon() {
		return BuffIndicator.SILENT;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", left,15-level);
	}

}