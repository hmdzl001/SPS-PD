/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Shocked2 extends Buff implements Hero.Doom{

    //private static final String TXT_DISARMED = "Sudden shock have made you drop your %s on the ground!";

	
	{
		type = buffType.NEGATIVE;
	}

    private int pos;
	private float left;
	private static final String LEFT = "left";
	private static final String POS = "pos";
	public static final float DURATION = 5f;
	private boolean first = false;

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POS, pos);
		bundle.put(LEFT, left);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pos = bundle.getInt(POS);
		left = bundle.getInt(LEFT);
	}
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			first = true;
			return super.attachTo(target);
		}	
	
	public boolean act() {

		if (target.isAlive()) {
			if (target.pos != pos) {
				//pos = 0;
				//if (target.pos != -1)  pos = target.pos;
				target.damage(Math.min(1000,target.HT/20),this,2);
				Buff.affect(target,Roots.class,5f);
				detach();
			} 
	
		} else {
			detach();
		}
		
		spend(TICK);
		left -= TICK;		
		
		if (left <= 0 ) {
			detach();
		}		

		return true;
	}	
	
	@Override
	public int icon() {
		return BuffIndicator.SHOCK_2;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",left);
	}

	public void set(float duration) {
		this.left = duration;
	}

    public float level() { return left; }

	public void level(int value) {
		if (left < value) {
			left = value;
		}
	}

	public static float duration(Char ch) {
		return DURATION;
	}	

	public void onDeath() {

		Badges.validateDeathFromFire();
		//Dungeon.fail(Messages.format(ResultDescriptions.LOSE));

	}	

}
