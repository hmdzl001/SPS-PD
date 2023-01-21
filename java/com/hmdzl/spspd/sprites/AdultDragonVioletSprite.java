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
import com.hmdzl.spspd.actors.mobs.AdultDragonViolet;
import com.hmdzl.spspd.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class AdultDragonVioletSprite extends MobSprite {

	private static final float DURATION = 2f;
	private Animation cast;

	public AdultDragonVioletSprite() {
		super();

		texture(Assets.DRAGON);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(10, true);
		idle.frames(frames, 0, 1, 0, 1, 0, 1, 0);

		run = new Animation(10, false);
		run.frames(frames, 0, 2, 1);

		attack = new Animation(15, false);
		attack.frames(frames, 0, 2, 3, 4, 5);
		
		cast = attack.clone();

		die = new Animation(10, false);
		die.frames(frames, 0, 5, 6, 7, 8, 9, 9, 9);

		play(run.clone());
	}

	

	@Override
	public void zap(int cell) {

		turnTo(ch.pos, cell);
		play(zap);

		MagicMissile.poison(parent, ch.pos, cell, new Callback() {
			@Override
			public void call() {
				((AdultDragonViolet) ch).onZapComplete();
			}
		});
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}
