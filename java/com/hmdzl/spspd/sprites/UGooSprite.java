/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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

public class UGooSprite extends MobSprite {

	
	public UGooSprite() {
		super();
		
		texture( Assets.UGOO );
		
		TextureFilm frames = new TextureFilm( texture, 20, 16 );

		idle = new Animation( 10, true );
		idle.frames( frames, 0, 1, 1, 2, 1, 1 );

		run = new Animation( 10, true );
		run.frames( frames, 0, 1, 2, 1 );

		attack = new Animation( 10, false );
		attack.frames( frames, 3, 4, 5 );

		die = new Animation( 10, false );
		die.frames( frames, 6, 7, 8, 9 );

		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFF000000;
	}
	
	public static class IceSpawnSprite extends MobSprite {

        public IceSpawnSprite() {
            super();
            texture( Assets.UGOO );

            TextureFilm frames = new TextureFilm( texture, 20, 16 );

			idle = new Animation( 10, true );
			idle.frames( frames, 10, 11, 11, 12, 11, 11 );

			run = new Animation( 10, true );
			run.frames( frames, 10, 11, 12, 11 );

			attack = new Animation( 10, false );
			attack.frames( frames, 13, 14, 15 );

			die = new Animation( 10, false );
			die.frames( frames, 16, 17, 18, 19 );

            play( idle );
        }
    }

	public static class EarthSpawnSprite extends MobSprite {

        public EarthSpawnSprite() {
            super();
            texture( Assets.UGOO );

            TextureFilm frames = new TextureFilm( texture, 20, 16 );

			idle = new Animation( 10, true );
			idle.frames( frames, 20, 21, 21, 22, 21, 21 );

			run = new Animation( 10, true );
			run.frames( frames, 20, 21, 22, 21 );

			attack = new Animation( 10, false );
			attack.frames( frames, 23, 24, 25 );

			die = new Animation( 10, false );
			die.frames( frames, 26, 27, 28, 29 );

            play( idle );
        }
    }

	public static class FireSpawnSprite extends MobSprite {

        public FireSpawnSprite() {
            super();
            texture( Assets.UGOO );

            TextureFilm frames = new TextureFilm( texture, 20, 16 );

			idle = new Animation( 10, true );
			idle.frames( frames, 30, 31, 31, 32, 31, 31 );

			run = new Animation( 10, true );
			run.frames( frames, 30, 31, 32, 31 );

			attack = new Animation( 10, false );
			attack.frames( frames, 33, 34, 35 );

			die = new Animation( 10, false );
			die.frames( frames, 36, 37, 38, 39 );

            play( idle );
        }
    }

	public static class ShockSpawnSprite extends MobSprite {
        public ShockSpawnSprite() {
            super();
            texture( Assets.UGOO );

            TextureFilm frames = new TextureFilm( texture, 20, 16 );

			idle = new Animation( 10, true );
			idle.frames( frames, 40, 41, 41, 42, 41, 41 );

			run = new Animation( 10, true );
			run.frames( frames, 40, 41, 42, 41 );

			attack = new Animation( 10, false );
			attack.frames( frames, 43, 44, 45 );

			die = new Animation( 10, false );
			die.frames( frames, 46, 47, 48, 49 );

            play( idle );
        }
    }
}
