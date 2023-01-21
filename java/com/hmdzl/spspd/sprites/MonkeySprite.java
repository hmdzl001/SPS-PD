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
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class MonkeySprite extends MobSprite {
	
	//Frames 1-4 are idle, 5-8 are moving, 9-12 are attack and the last are for death RBVG

	public MonkeySprite() {
		super();

		texture(Assets.KLIKS);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 17, 18, 19, 20);

		run = new Animation(8, true);
		run.frames(frames, 21, 22, 23, 24);

		attack = new Animation(15, false);
		attack.frames(frames, 25, 26, 27, 28);

		zap = attack.clone();
		
		die = new Animation(8, false);
		die.frames(frames, 29, 30, 30, 30);

		play(idle);
	}

	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Blackberry(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					});

			play(zap);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

		}
	}
	
	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}
}
