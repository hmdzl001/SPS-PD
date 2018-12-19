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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.effects.Splash;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Bleeding extends Buff {

	{
		type = buffType.NEGATIVE;
	}

	protected int level;

	private static final String LEVEL = "level";

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

	public void set(int level) {
		this.level = level;
	};

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

			if ((level = Random.Int(level / 3, level/2)) > 0) {

				target.damage(level, this);
				if (target.sprite.visible) {
					Splash.at(target.sprite.center(), -PointF.PI / 2,
							PointF.PI / 6, target.sprite.blood(),
							Math.min(10 * level / target.HT, 10));
				}

				if (target == Dungeon.hero && !target.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
					//GLog.n("You bled to death...");
				}

				spend(TICK);
			} else {
				detach();
			}

		} else {

			detach();

		}

		return true;
	}
	
	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", level);
	}	
}
