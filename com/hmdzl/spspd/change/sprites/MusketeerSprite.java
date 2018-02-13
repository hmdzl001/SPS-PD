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
package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.items.weapon.missiles.Ammo;
import com.hmdzl.spspd.change.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class MusketeerSprite extends MobSprite {

    private Animation cast;

	public MusketeerSprite() {
		super();

		texture(Assets.MUSKETEER);

		TextureFilm frames = new TextureFilm(texture, 15, 14);

		idle = new Animation(6, true);
		idle.frames(frames, 1, 0, 1, 2);

		run = new Animation(15, true);
		run.frames(frames, 9, 10, 11, 12, 13, 14);

		attack = new Animation(12, false);
		attack.frames(frames, 3, 4, 3);

		cast = attack.clone();

		die = new Animation(15, false);
		die.frames(frames, 1, 5, 6, 7, 8);

		play(idle);
	}

    @Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Ammo(), new Callback() {
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
