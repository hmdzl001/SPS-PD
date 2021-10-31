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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ThiefKingSprite extends MobSprite {

	private static final float DURATION = 2f;
	private Animation cast;
	


	public ThiefKingSprite() {
		super();

		texture(Assets.THIEFKING);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 0);

		run = new Animation(15, false);
		run.frames(frames, 1, 2, 3, 4, 5);

		attack = new Animation(15, false);
		attack.frames(frames, 6, 7, 8);

		cast = new Animation(15, false);
		cast.frames(frames, 9, 10);

		die = new Animation(8, false);
		die.frames(frames, 11, 12, 13, 14);

		play(run.clone());
	}
	
	@Override
	public void link(Char ch) {
		super.link(ch);
		add(State.LEVITATING);
	}

	@Override
	public void die() {
		super.die();
		remove(State.LEVITATING);
	}

	@Override
	public void move(int from, int to) {

		place(to);

		play(run);
		turnTo(from, to);

		isMoving = true;

		if (Level.water[to]) {
			GameScene.ripple(to);
		}
		
		

		ch.onMotionComplete();
	}
	
	
	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {
			Char enemy = Actor.findChar(cell);
				  ((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new EscapeKnive(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
				});
		 	  
		  		
		  		if(Random.Int(10)==0){
		  			Buff.affect(enemy, Slow.class, 2f);
		  		}
		  				  		
		  		if(Random.Int(10)==0){
		  			Buff.affect(enemy, Poison.class).set(Random.Int(7, 9));
		  		}
		  		
			play(cast);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

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
