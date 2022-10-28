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
import com.watabou.noosa.TextureFilm;

public class Hmdzl001Sprite extends MobSprite {

	public Hmdzl001Sprite() {
		super();

		texture( Assets.HMDZL );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 10, true );
        idle.frames(frames, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1,2,2,2,3,3,3,4,4,4,5,5,5,1,1,1,6,6,7,7,7,1,1,0,0,0);

        run = new Animation( 20, true );
        run.frames( frames, 0 );

        attack = new Animation( 12, false );
        attack.frames( frames, 0, 2, 3 );

        die = new Animation( 20, false );
        die.frames( frames, 0 );

        play( idle );
    }


}
