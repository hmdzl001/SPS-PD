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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class CountDown extends Buff {
	
	private int ticks = 0;

	private static final String TICKS = "ticks";
	
	{
		type = buffType.NEGATIVE;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TICKS, ticks);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		ticks = bundle.getInt(TICKS);
	}
	
	
	@Override
	public int icon() {
		return BuffIndicator.COUNTDOWN;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", ticks);
	}	
	
	@Override
	public boolean act() {
		if (target.isAlive()) {
			ticks++;
			if (ticks>5){
				target.sprite.emitter().burst(ShadowParticle.CURSE, 6);
				target.damage(Math.round(target.HT / 4), this);
				detach();
			}
		}
		
		if (!target.isAlive() && target == Dungeon.hero) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n(TXT_HERO_KILLED, toString());
		}
			
		spend(TICK);	
		
		return true;
	}
}
