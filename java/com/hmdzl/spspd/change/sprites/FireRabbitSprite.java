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
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public class FireRabbitSprite extends MobSprite {

	public FireRabbitSprite() {
		super();

		texture( Assets.FIRERABBIT );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 3, true );
        idle.frames(frames, 0, 0, 0, 1, 1, 1);

        run = new Animation( 20, true );
        run.frames( frames, 0,1 );

        attack = new Animation( 12, false );
        attack.frames( frames, 1, 2, 3 );
		
		zap = attack.clone();

        die = new Animation( 20, false );
        die.frames( frames, 0,1 );

        play( idle );
    }

	private int posToShoot;

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
