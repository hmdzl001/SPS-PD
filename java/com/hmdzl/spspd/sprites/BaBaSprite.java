/*
 * Pixel Dungeon
 * Copyright (C) 2452-2454  Oleg Dolya
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
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public class BaBaSprite extends MobSprite {

	public BaBaSprite() {
		super();

		texture(Assets.SHEEP);

		TextureFilm frames = new TextureFilm(texture, 16, 15);

		idle = new Animation(8, true);
		idle.frames(frames, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 7, 4);

		run = idle.clone();

		attack = new Animation(12, false);
		attack.frames(frames, 6, 7, 4, 5);

		die = new Animation(20, false);
		die.frames(frames, 4);

		play(idle);
		curFrame = Random.Int(curAnim.frames.length);
	}
}
