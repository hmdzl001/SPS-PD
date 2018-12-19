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
import com.watabou.noosa.TextureFilm;

public class SkeletonKingSprite extends MobSprite {

	public SkeletonKingSprite() {
		super();

		texture(Assets.SKELETONKING);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(5, true);
		idle.frames(frames, 0, 1, 2);

		run = new Animation(6, true);
		run.frames(frames, 3, 4);

		attack = new Animation(5, false);
		attack.frames(frames, 5, 6, 7);

		die = new Animation(5, false);
		die.frames(frames, 8, 9, 10, 11, 12, 13);

		play(idle);
	}

}
