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
import com.hmdzl.spspd.change.actors.mobs.LitTower;
import com.hmdzl.spspd.change.effects.Lightning;
import com.watabou.noosa.TextureFilm;

public class OtiluckStoneSprite extends MobSprite {

	private int[] points = new int[2];

	public OtiluckStoneSprite() {
		super();

		texture(Assets.OTILUKESTONE);
		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 3, 0, 0, 3, 3);

		run = new Animation(15, true);
		run.frames(frames, 0, 1, 2, 0);

		attack = new Animation(12, false);
		attack.frames(frames, 0, 1, 4, 4, 4);

		zap = attack.clone();

		die = new Animation(15, false);
		die.frames(frames, 0, 5, 6, 7, 8, 7);

		play(idle);
	}

	@Override
	public void zap(int pos) {

		parent.add( new Lightning( ch.pos, pos,(LitTower) ch));

		turnTo(ch.pos, pos);
		play(zap);
	}
	
}
