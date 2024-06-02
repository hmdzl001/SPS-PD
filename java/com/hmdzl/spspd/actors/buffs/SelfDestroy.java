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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class SelfDestroy extends Buff {

	{
		type = buffType.NEGATIVE;
	}


    @Override
	public int icon() {
		return BuffIndicator.BLEEDING;
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
				target.damage(2, this);
				if (target == Dungeon.hero && !target.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
					//GLog.n("You bled to death...");
				}
				spend(TICK);
		} else {
			detach();
		}

		return true;
	}
	
	@Override
	public String desc() {
		return Messages.get(this, "desc");
	}	
}
