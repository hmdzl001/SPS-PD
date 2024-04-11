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

import android.annotation.SuppressLint;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;

public class Ooze extends Buff {
 
    public static final float DURATION = 20f;

	{
		type = buffType.NEGATIVE;
	}	
	
	private float left;
	
	private static final String LEFT = "left";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEFT, left);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		left = bundle.getFloat(LEFT);
	}	
	
	
	@Override
	public int icon() {
		return BuffIndicator.OOZE;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",dispTurns(left));
	}


	public void set(float left) {
		 this.left = left;
	}

	@SuppressLint("SuspiciousIndentation")
	@Override
	public boolean act() {
		if (target.isAlive()) {
			if (Random.Int(6) == 0) {
				target.damage(1, EARTH_DAMAGE);
			} else target.damage(Math.min(500,(int)(target.HT/30)), EARTH_DAMAGE);
            if (!target.isAlive() && target == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n(TXT_HERO_KILLED, toString());
			}
			spend(TICK);
			left -= TICK;
			if (left <= 0){
				detach();
			}
		}
		if (Floor.water[target.pos]) {
			detach();
		}
		return true;
	}
}
