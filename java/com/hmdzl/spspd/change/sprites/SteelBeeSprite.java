/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

public class SteelBeeSprite extends MobSprite {

	public SteelBeeSprite() {
		super();

		texture(Assets.BEE);
		
		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(12, true);
		idle.frames(frames, 16, 17, 17, 16, 18, 18);

		run = new Animation(15, true);
		run.frames(frames, 16, 17, 17, 16, 18, 18);

		attack = new Animation(20, false);
		attack.frames(frames, 19, 20, 21, 22);

		die = new Animation(20, false);
		die.frames(frames, 23, 24, 25, 26);

	
		play(idle);
	}

	@Override
	public int blood() {
		return 0xffd500;
	}
}
