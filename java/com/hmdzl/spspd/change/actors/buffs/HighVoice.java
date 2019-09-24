/*
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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.rings.RingOfElements.Resistance;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

import javax.microedition.khronos.opengles.GL10;

public class HighVoice extends FlavourBuff {

	public static final float DURATION = 10f;

	@Override
	public int icon() {
		return BuffIndicator.VOICE_UP;
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
			if (target.buff(HighVoice.class) != null && Random.Int(8) == 0) {
				if (target.HP > target.HT*0.75 ){
					Buff.affect(target,Haste.class,5f);
					GLog.p(Messages.get(this,"speed",Dungeon.hero.givenName()));
				} else {
					target.HP += (int)(target.HT/4);
				GLog.p(Messages.get(this,"heal",Dungeon.hero.givenName()));
				}
			}

			spend(TICK);
		} else {

			detach();

		}

		return true;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

}
