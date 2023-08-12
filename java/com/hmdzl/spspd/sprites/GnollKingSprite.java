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
import com.hmdzl.spspd.items.weapon.missiles.throwing.EscapeKnive;
import com.hmdzl.spspd.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class GnollKingSprite extends MobSprite {

	private Animation cast;

	public GnollKingSprite() {
		super();

		texture(Assets.GNOLLKING);

		TextureFilm frames = new TextureFilm(texture, 12, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 1, 0, 0, 1, 1);

		run = new Animation(12, true);
		run.frames(frames, 4, 5, 6, 7);

		attack = new Animation(12, false);
		attack.frames(frames, 2, 3, 0);
		
		cast = attack.clone();

		die = new Animation(12, false);
		die.frames(frames, 8, 9, 10);

		play(idle);
	}
	
	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new EscapeKnive(), new Callback() {
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
