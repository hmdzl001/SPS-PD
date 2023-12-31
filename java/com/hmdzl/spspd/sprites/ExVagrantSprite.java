
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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class ExVagrantSprite extends MobSprite {

	public ExVagrantSprite() {
		super();

		texture( Assets.VAGRANT );

		TextureFilm frames = new TextureFilm( texture, 12, 16 );

		idle = new Animation( 2, true );
		idle.frames( frames, 15, 15, 15, 16, 15, 15, 16, 16 );

		run = new Animation( 15, true );
		run.frames( frames, 17, 18, 19, 20, 21, 22 );

		attack = new Animation( 12, false );
		attack.frames( frames, 23, 24, 25 );


		die = new Animation( 8, false );
		die.frames( frames, 26, 27, 28, 29 );

		play( idle );
    }
	
	

	@Override
	public void die() {
		super.die();
		if (Dungeon.visible[ch.pos]) {
			emitter().burst(Speck.factory(Speck.STAR), 6);
		}
	}

}

