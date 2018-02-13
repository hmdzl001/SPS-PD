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
package com.hmdzl.spspd.change.items.rings;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.mobs.Mob;

public class RingOfEvasion extends Ring {

	{
		//name = "Ring of Evasion";
		initials = 2;
	}

	@Override
	protected RingBuff buff() {
		return new Evasion();
	}

	public class Evasion extends RingBuff {
		public int effectiveLevel;
		private int pos;

		@Override
		public boolean attachTo(Char target) {

			pos = target.pos;
			effectiveLevel = Math.min(0, level);
			return super.attachTo(target);
		}

		@Override
		public boolean act() {

			boolean seen = false;

			for (Mob enemy : Dungeon.level.mobs.toArray(new Mob[0])) {
				if (enemy.focusingHero()) {
					seen = true;
					break;
				}
			}

			if (level < 1) {
				effectiveLevel = level;
			} else if (seen) {
				effectiveLevel = Math.max(effectiveLevel - 1, 0);
			} else {
				effectiveLevel = Math.min(effectiveLevel + 1, level);
			}

			return super.act();
		}
	}
}
