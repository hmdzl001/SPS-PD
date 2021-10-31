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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.GreatCrabSprite;
import com.hmdzl.spspd.utils.GLog;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

public class GreatCrab extends Crab {
		{
			//name = "great crab";
			spriteClass = GreatCrabSprite.class;

			HP = HT = 100;
			evadeSkill = 0; // see damage()
			baseSpeed = 1f;

			EXP = 6;

			state = WANDERING;

			properties.add(Property.BEAST);
			properties.add(Property.MINIBOSS);
		}

		private int moving = 0;

		@Override
		protected boolean getCloser(int target) {
			// this is used so that the crab remains slower, but still detects
			// the player at the expected rate.
			moving++;
			if (moving < 3) {
				return super.getCloser(target);
			} else {
				moving = 0;
				return true;
			}

		}

		@Override
		public void damage(int dmg, Object src) {
			// crab blocks all attacks originating from the hero or enemy
			// characters or traps if it is alerted.
			// All direct damage from these sources is negated, no exceptions.
			// blob/debuff effects go through as normal.
			if ((enemySeen && state != SLEEPING && paralysed == 0)
					&& (src instanceof Wand || src instanceof LightningTrap.Electricity || src instanceof Char)){
				GLog.n( Messages.get(this, "noticed") );
				sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "blocked") );
			} else {
				super.damage(dmg, src);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			Ghost.Quest.process();

			Dungeon.level.drop(new MysteryMeat(), pos);
			Dungeon.level.drop(new MysteryMeat(), pos).sprite.drop();
		}

	}