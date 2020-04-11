package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.watabou.noosa.TextureFilm;

public class ThiefImpSprite extends MobSprite {
	
	public ThiefImpSprite() {
		super();
		
		texture( Assets.THIEFIMP );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 5, true );
		idle.frames(frames, 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 3, 4, 5, 6, 7);

        run = new Animation( 10, true );
        run.frames( frames, 0, 1, 2, 3 );

        attack = new Animation( 15, false );
        attack.frames( frames, 8, 9, 10, 11, 12 );

//        cast = new Animation( 15, false );
//        cast.frames( frames, 8, 9, 10, 11, 12 );

        zap = new Animation( 10, false );
        zap.frames( frames, 13, 14, 15, 0 );

        die = new Animation( 10, false );
        die.frames( frames, 16, 17, 18, 19, 20, 21 );
		
		play( idle );
	}
}