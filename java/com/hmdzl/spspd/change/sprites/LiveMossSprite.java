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
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.mobs.npcs.MirrorImage;
import com.watabou.noosa.TextureFilm;

public class LiveMossSprite extends MobSprite {

	public LiveMossSprite() {
		super();

		texture( Assets.LIVEMOSS );

		TextureFilm frames = new TextureFilm(texture, 16, 15);

		idle = new Animation( 2, true );
		idle.frames( frames, 0,0,0,1);

		run = new Animation( 10, true );
		run.frames( frames, 0,1,1,1,0,0);

		attack = new Animation( 15, false );
		attack.frames( frames, 1, 2, 2, 1, 1 );

		die = new Animation( 10, false );
		die.frames( frames, 2, 2, 3, 3 );

		play( idle );
	}
}