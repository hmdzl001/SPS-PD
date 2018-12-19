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
import com.hmdzl.spspd.change.actors.Char;
import com.watabou.noosa.TextureFilm;


public class DustElementSprite extends MobSprite {

	public DustElementSprite() {
		super();

		texture(Assets.ELEMENTAL);

		TextureFilm frames = new TextureFilm(texture, 12, 14);

		idle = new Animation(10, true);
		idle.frames(frames, 42, 43, 44);

		run = new Animation(12, true);
		run.frames(frames, 42, 43, 45);

		attack = new Animation(15, false);
		attack.frames(frames, 46, 47, 48);
		
		zap = attack.clone();

		die = new Animation(15, false);
		die.frames(frames, 49, 50, 51, 52, 53, 54, 55, 54);

		play(idle);
	}


	@Override
	public int blood() {
		return 0x999999;
	}
}
