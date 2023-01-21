
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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.food.meatfood.BugMeat;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

	public class BugSlow extends Buff implements Hero.Doom  {

        int spawnPower = 0;
        @Override
        public boolean act() {

			if (target.isAlive()) {
				Hero hero = (Hero) target;
				spawnPower++;
				if (spawnPower > Random.Int(10) && spawnPower > 3) {
					Buff.affect(target, Slow.class, 2f);
					spawnPower = 0;
				}
				if (target == hero) {
					if (hero.belongings.getItem(BugMeat.class) == null) {
						spawnPower = 0;
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

		private static String SPAWNPOWER = "spawnpower";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put( SPAWNPOWER, spawnPower );
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawnPower = bundle.getInt( SPAWNPOWER );
		}
		@Override
		public void onDeath() {

			Badges.validateDeathFromFire();

			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			//GLog.n(TXT_BURNED_TO_DEATH);
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