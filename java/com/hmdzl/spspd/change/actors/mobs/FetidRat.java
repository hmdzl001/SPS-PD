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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.StenchGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.FetidRatSprite;

import com.watabou.utils.Random;
	
	public class FetidRat extends Rat {

		{
			//name = "fetidrat";
			spriteClass = FetidRatSprite.class;

			HP = HT = 45;
			evadeSkill = 5;

			EXP = 4;

			state = WANDERING;

			properties.add(Property.BEAST);
			properties.add(Property.MINIBOSS);
		}

		@Override
		public int hitSkill(Char target) {
			return 12;
		}

		@Override
		public int drRoll() {
			return 2;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(3) == 0) {
				Buff.affect(enemy, Ooze.class);
			}

			return damage;
		}

		@Override
		public int defenseProc(Char enemy, int damage) {

			GameScene.add(Blob.seed(pos, 20, StenchGas.class));

			return super.defenseProc(enemy, damage);
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			Ghost.Quest.process();
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(StenchGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}