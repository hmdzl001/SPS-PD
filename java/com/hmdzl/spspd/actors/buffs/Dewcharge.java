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

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Dewcharge extends Buff {

	public static final float DURATION = 300f;
	
	private int dewleft = 0;
	
	private static final String DEWLEFT = "dewleft";

	public boolean isDewing() {
		return dewleft > 2;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEWLEFT, dewleft);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		dewleft = bundle.getInt(DEWLEFT);
	}	
	
	@Override
	public int icon() {
		if (dewleft < 2) {
            return BuffIndicator.NONE;
		} else {
			return BuffIndicator.DEWCHARGE;
		}

	}
	
	@Override
	public boolean act() {
		if (target.isAlive()) {

			spend(TICK);
			boolean statusUpdated = false;
			if (dewleft > 2) {
			dewleft = dewleft-1;
			} 
			
			if (dewleft < 2){
				statusUpdated = true;
			}
			
			if (statusUpdated) {
				BuffIndicator.refreshHero();
			}
			
		} else {

			detach();

		}

		return true;
	}	
	
	public int level() {
		return dewleft;
	}

	public void level(int value) {
		if (dewleft < value) {
			dewleft = value;
		}
	}	

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dewleft-2);
	}
	
}
