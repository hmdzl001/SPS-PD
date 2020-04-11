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
import com.hmdzl.spspd.actors.mobs.Fiend;
import com.hmdzl.spspd.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class FiendSprite extends MobSprite {

	public FiendSprite() {
		super();

		texture(Assets.SPECTRALRAT);
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 5, true );
		idle.frames(frames, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 4, 10, 11, 10, 4, 1, 1 );

        run = new Animation( 5, true );
        run.frames( frames, 0, 1, 2, 3 );

        attack = new Animation( 15, false );
        attack.frames( frames, 10, 11, 12, 13, 14, 15 );

        zap = attack.clone();

        die = new Animation( 10, false );
        die.frames( frames, 16, 17, 18, 19, 19, 19 );
		
		play( idle );		
	}
	
	@Override
	public void zap(int cell) {

		turnTo(ch.pos, cell);
		play(zap);

		MagicMissile.shadow(parent, ch.pos, cell, new Callback() {
			@Override
			public void call() {
				((Fiend) ch).onZapComplete();
			}
		});
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public void onComplete(Animation anim) {
		if (anim == zap) {
			idle();
		}
		super.onComplete(anim);
	}
}
