
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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ShadowRatSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.depth;
import static com.hmdzl.spspd.Dungeon.observe;
import static com.hmdzl.spspd.Dungeon.shopOnLevel;


public class DarkFallen extends Buff {

		@Override
		public boolean act() {
			int liver = 0;
			for (Mob mob : depth.mobs){
				if (mob instanceof DarkLiver){
					liver++;
				}
			}

			if (liver < 1 && !shopOnLevel()){

				int pos = 0;
				do{
					pos = Random.Int(depth.randomRespawnCellMob());
				} while (!Floor.passable[pos] || Actor.findChar( pos ) != null);
				DarkLiver.spawnAt(pos);
				Sample.INSTANCE.play(Assets.SND_BURNING);
			} else {

			}


			spend(TICK);
			return true;
		}
	public static class DarkLiver extends Mob {

		{
			spriteClass = ShadowRatSprite.class;

			HP = HT = 10 + Statistics.moves;
			evadeSkill = Statistics.moves;

			EXP = 1;

			viewDistance = 3;

			flying = true;
			state = HUNTING;

			properties.add(Property.UNKNOW);

		}

		@Override
		protected boolean canAttack(Char enemy) {
			if (Statistics.time > 1080 || Statistics.time < 361){
				return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
			} else
				return false;
		}


		@Override
	public void move(int step) {
		super.move(step);
		if (Statistics.time > 1080 || Statistics.time < 361){
		}
		Buff.prolong(this, HiddenShadow.class, 3f);
		
	}

		@Override
		protected boolean getCloser(int target) {
			if (Statistics.time < 1080 && Statistics.time > 361 ) {
				return getFurther(target);
			} else {
				return super.getCloser(target);
			}
		}

		
		@Override
		protected boolean act() {
			if (enemy == null || !Floor.adjacent(pos, enemy.pos)) {
				HP = Math.min(HT, HP + 5);
			}
			return super.act();
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			Buff.detach(this, HiddenShadow.class);
			if (Random.Int(5) < 1) {
				Buff.affect(enemy, Terror.class, 2f).object = this.id();
			}
			return super.attackProc(enemy, damage);

		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(Statistics.moves, 4 * Statistics.moves);
		}

		@Override
		public int hitSkill( Char target ) {
			return Statistics.moves+5;
		}

		@Override
		public int drRoll() {
			return Statistics.moves;
		}


		@Override
		public void die(Object cause) {

			for (int i : Floor.NEIGHBOURS9DIST2) {
				int c = pos + i;
				if ( Floor.insideMap(c)) {
					if (depth.discoverable[c]) {
						depth.mapped[c] = true;
					}

					depth.discover(c);
					//GameScene.discoverTile( cell, terr );
					observe();
				}
			}

			super.die(cause);
		}


		public static DarkLiver spawnAt(int pos) {

			DarkLiver b = new DarkLiver();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, 1f);

			return b;

		}


	}
		
	}