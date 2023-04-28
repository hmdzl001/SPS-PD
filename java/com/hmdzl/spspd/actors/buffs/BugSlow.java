
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

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.food.meatfood.BugMeat;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

	public class BugSlow extends Buff {

        int slowDelay = 0;
        @Override
        public boolean act() {

			if (target.isAlive()) {
				Hero hero = (Hero) target;
				slowDelay++;
				if (slowDelay > Random.Int(10) && slowDelay > 3) {
					Buff.affect(target, Slow.class, 2f);
					slowDelay = 0;
				}
				if (target == hero) {
					if (hero.belongings.getItem(BugMeat.class) == null) {
						detach();
					}
				}
			}
			spend(TICK);
            return true;

        }

		public void dispel(){
			detach();
		}

		private static String SLOWDELAY = "slowdelay";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SLOWDELAY, slowDelay);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			slowDelay = bundle.getInt(SLOWDELAY);
		}

		@Override
		public int icon() {
			return BuffIndicator.CRAZY_MIND;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}
	}