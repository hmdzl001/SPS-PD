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
import com.hmdzl.spspd.actors.mobs.ForestProtector;
import com.hmdzl.spspd.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class ForestProtectorSprite extends MobSprite {

	public ForestProtectorSprite() {
		super();

		texture(Assets.PLANT_DOCTOR);

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new Animation( 15, true );
		idle.frames(frames, 0, 0, 4, 4 );

		run = new Animation( 10, true );
		run.frames( frames, 0,1,2,3 );

		attack = new Animation( 20, false );
		attack.frames( frames, 4, 4, 4,0 );

		die = new Animation( 20, false );
		die.frames( frames, 0,0,0 );

		play( idle );
	}

	@Override
	public void zap(int pos) {
		turnTo(ch.pos, pos);
		play(zap);

		MagicMissile.earth(parent, ch.pos, pos, new Callback() {
			@Override
			public void call() {
				((ForestProtector) ch).onZapComplete();
			}
		});
		Sample.INSTANCE.play(Assets.SND_ZAP);
		turnTo(ch.pos, pos);
		play(zap);
	}

	@Override
	public void onComplete(Animation anim) {
		if (anim == zap) {
			idle();
		}
		super.onComplete(anim);
	}
	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}
}
