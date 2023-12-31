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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.weapon.missiles.throwing.PoisonDart;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.GnollTricksterSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

	public class GnollTrickster extends Gnoll {
		{
			//name = "gnolltrickster";
			spriteClass = GnollTricksterSprite.class;

			HP = HT = 60;
			evadeSkill = 5;

			EXP = 5;

			state = WANDERING;

			loot = Generator.random(PoisonDart.class);
			lootChance = 1f;
			
			properties.add(Property.ORC);
			properties.add(Property.MINIBOSS);
		}

		private int combo = 0;

		@Override
		public int hitSkill(Char target) {
			return 16;
		}

		@Override
		protected boolean canAttack(Char enemy) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
				if (buff(Locked.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return !Floor.adjacent( pos, enemy.pos ) && attack.collisionPos == enemy.pos;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			// The gnoll's attacks get more severe the more the player lets it
			// hit them
			combo++;
			int effect = Random.Int(4) + combo;

			if (effect > 2) {

				if (effect >= 6 && enemy.buff(Burning.class) == null) {

					if (Floor.flamable[enemy.pos])
						GameScene.add(Blob.seed(enemy.pos, 4, Fire.class));
					Buff.affect(enemy, Burning.class).set(3f);

				} else
					Buff.affect(enemy, Poison.class).set(
							(effect - 2));

			}
			return damage;
		}

		@Override
		protected boolean getCloser(int target) {
			combo = 0; // if he's moving, he isn't attacking, reset combo.
			if (Floor.adjacent(pos, enemy.pos)) {
				return getFurther(target);
			} else {
				return super.getCloser(target);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);

			Ghost.Quest.process();
		}

		private static final String COMBO = "combo";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(COMBO, combo);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			combo = bundle.getInt(COMBO);
		}

	}