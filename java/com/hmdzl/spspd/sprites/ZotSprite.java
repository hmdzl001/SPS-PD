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
package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Skull;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ZotSprite extends MobSprite {

	private Animation cast;
	
	public ZotSprite() {
		super();

		texture(Assets.ZOT);

		TextureFilm frames = new TextureFilm(texture, 18, 18);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 1, 0);

		run = new Animation(8, false);
		run.frames(frames, 0, 1, 2);

		attack = new Animation(8, false);
		attack.frames(frames, 0, 2, 2);
		
		cast = new Animation(8, false);
		cast.frames(frames, 2, 3, 4);

		die = new Animation(8, false);
		die.frames(frames, 0, 5, 6, 7, 8, 9, 8);

		play(run.clone());
	}

	@Override
	public void move(int from, int to) {

		place(to);

		play(run);
		turnTo(from, to);

		isMoving = true;

		if (Floor.water[to]) {
			GameScene.ripple(to);
		}

		ch.onMotionComplete();
	}

	@Override
	public void attack(int cell) {
		if (!Floor.adjacent(cell, ch.pos)) {
			//Char enemy = Actor.findChar(cell);
				  ((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Skull(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
				});
		 	  
		  			  		
			play(cast);
			turnTo(ch.pos, cell);
			explode(cell);			
			
		} else {

			super.attack(cell);

		}
	}

	public void explode(int cell) {
		
		Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

		boolean terrainAffected = false;
		for (int n : Floor.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Floor.getLength()) {
				
				if (Floor.flamable[c]) {
					Floor.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				Char ch = Actor.findChar(c);
				if (ch != null && ch==Dungeon.hero) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = c == cell ? Dungeon.dungeondepth + 5 : 1;
					int maxDamage = 10 + Dungeon.dungeondepth;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
		
	}

	
	@Override
	public void onComplete(Animation anim) {
		if (anim == run) {
			isMoving = false;
			idle();
		} else {
			super.onComplete(anim);
		}
	}
}
