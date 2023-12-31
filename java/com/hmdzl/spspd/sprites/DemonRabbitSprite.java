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
import com.hmdzl.spspd.items.weapon.missiles.arrows.GlassFruit;
import com.hmdzl.spspd.levels.Floor;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class DemonRabbitSprite extends MobSprite {

    private Animation cast;

	public DemonRabbitSprite() {
		super();

		texture(Assets.DEMON_RABBIT);

		TextureFilm frames = new TextureFilm(texture, 12, 15);

		idle = new Animation(6, true);
		idle.frames(frames, 1, 0, 1, 0);

		run = new Animation(15, true);
		run.frames(frames, 2, 3, 2, 3);

		attack = new Animation(12, false);
		attack.frames(frames, 4, 5);

		cast = attack.clone();

		die = new Animation(15, false);
		die.frames(frames, 1, 6, 7);

		play(idle);
	}

    @Override
	public void attack(int cell) {
		if (!Floor.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new GlassFruit(), new Callback() {
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
