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
import com.hmdzl.spspd.Dungeon;
import com.watabou.noosa.TextureFilm;

public class OldNewStwistSprite extends MobSprite {

	public boolean gnollmission;

	public OldNewStwistSprite() {
		super();
		
		
		gnollmission = (Dungeon.gnollmission == false);


		final int c = gnollmission ? 8 : 0;

		texture(Assets.OLDNEWSTWIST);

		TextureFilm frames = new TextureFilm(texture, 12, 15);

		idle = new Animation(2, true);
		idle.frames(frames, c + 0, c + 0, c + 0, c + 1);

		run = new Animation(10, true);
		run.frames(frames, c + 2, c + 3, c + 4, c + 5, c + 6);

		attack = new Animation(15, false);
		attack.frames(frames, c + 0);

		die = new Animation(10, false);
		die.frames(frames, c + 0);

		play(idle);
	}
}
