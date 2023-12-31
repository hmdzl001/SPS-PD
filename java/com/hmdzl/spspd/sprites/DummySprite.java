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
import com.hmdzl.spspd.items.weapon.missiles.throwing.PoisonDart;
import com.hmdzl.spspd.levels.Floor;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class DummySprite extends MobSprite {

private Animation cast;

	public DummySprite() {
		super();

		texture( Assets.SCARECROW );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 3, true );
        idle.frames(frames, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7);

        run = new Animation( 3, true );
        run.frames( frames, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7, 7 );

        attack = new Animation( 12, false );
        attack.frames( frames, 4, 6, 7 );

        cast = attack.clone();
		
        die = new Animation( 20, false );
        die.frames( frames, 4 );

        play( idle );
    }


	@Override
	public void attack(int cell) {
		if (!Floor.adjacent(cell, ch.pos)) {

			((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new PoisonDart(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
					});

			play(cast);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

		}
	}	

}
