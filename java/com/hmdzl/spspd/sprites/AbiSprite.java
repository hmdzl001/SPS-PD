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
import com.hmdzl.spspd.actors.mobs.pets.Abi;
import com.hmdzl.spspd.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class AbiSprite extends MobSprite {

	public AbiSprite() {
		super();

		texture(Assets.ABI);

		TextureFilm frames = new TextureFilm(texture, 15, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0,1,2,1,0,1,3,1);

		run = new Animation(15, true);
		run.frames(frames, 0, 4,5,6,5);

		attack = new Animation(12, false);
		attack.frames(frames, 0,7,8,9);

		zap = new Animation(12, false);
		zap.frames(frames, 0, 10, 11, 12, 13);

		die = new Animation(15, false);
		die.frames(frames, 0, 13,14);

		play(idle);
	}

	@Override
	public void zap(int cell) {

		turnTo(ch.pos, cell);
		play(zap);

		MagicMissile.blueLight(parent, ch.pos, cell, new Callback() {
			@Override
			public void call() {
				((Abi) ch).onZapComplete();
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