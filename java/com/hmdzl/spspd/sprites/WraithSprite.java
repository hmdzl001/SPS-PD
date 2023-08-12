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
import com.hmdzl.spspd.items.weapon.missiles.throwing.Wave;
import com.hmdzl.spspd.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class WraithSprite extends MobSprite {

	private Animation cast;

	public WraithSprite() {
		super();

		texture(Assets.WRAITH);

		TextureFilm frames = new TextureFilm(texture, 14, 15);

		idle = new Animation(5, true);
		idle.frames(frames, 0, 1);

		run = new Animation(10, true);
		run.frames(frames, 0, 1);

		attack = new Animation(15, false);
		attack.frames(frames, 0, 2, 3);

		cast = attack.clone();
		
		die = new Animation(8, false);
		die.frames(frames, 0, 4, 5, 6, 7);

		play(idle);
	}

	@Override
	public int blood() {
		return 0x88000000;
	}
	
	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {
			//Char enemy = Actor.findChar(cell);
				  ((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Wave(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
				});
		 	  
		  			  		
			play(cast);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

		}
	}
}
