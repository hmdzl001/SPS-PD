/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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

public class BloodAngry extends Buff {

    public static final float DURATION = 30f;

	private float left;
	private static final String LEFT	= "left";

	@Override
	public int icon() {
		return BuffIndicator.FURY;
	}

	@Override
	public boolean act() {
		
		if (target.HP > target.HT/3){
			target.HP = Math.max(target.HT/3, target.HP - 1);
		}
		spend(TICK);
		left -= TICK;
		if (left <= 0){
			detach();
		} else
		if (target.HP < target.HT/3){
			target.HP = Math.max(target.HT/3, target.HP + 1);
		}
		spend(TICK);
		left -= TICK;
		if (left <= 0){
			detach();
		}
		return true;
	}
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEFT, left);

	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		left = bundle.getInt(LEFT);
	}
	public void set(float left){
		this.left = left;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}
	
	@Override
	public String desc() {
		return Messages.get(this, "desc", left);
	}
}