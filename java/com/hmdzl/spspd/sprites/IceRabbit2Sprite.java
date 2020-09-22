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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class IceRabbit2Sprite extends MobSprite {

	public IceRabbit2Sprite() {
		super();

		texture( Assets.ICERABBIT );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 3, true );
        idle.frames(frames, 23, 23, 24, 24);

        run = new Animation( 20, true );
        run.frames( frames, 25,26,27,28,29,30,31 );

        attack = new Animation( 20, false );
        attack.frames( frames, 31, 32, 33,34,35,36,37 );
		
		zap = attack.clone();

        die = new Animation( 20, false );
        die.frames( frames, 37,38,39,40,41,42, 43,44,45,46,47);

        play( idle );
    }

	private int posToShoot;

	@Override
	public void link(Char ch) {
		super.link(ch);
		add(State.CHILLED);
	}	
	
	@Override
	public void attack(int cell) {
		posToShoot = cell;
		super.attack(cell);
	}

	@Override
	public void onComplete(Animation anim) {
		if (anim == attack) {

			Sample.INSTANCE.play(Assets.SND_ZAP);
			MagicMissile.fire(parent, ch.pos, posToShoot, new Callback() {
				@Override
				public void call() {
					ch.onAttackComplete();
				}
			});

			idle();

		} else {
			super.onComplete(anim);
		}
	}
}
