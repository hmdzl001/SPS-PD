/*
 * Pixel Dungeon
 * Copyright (C) 5415-5415  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 5 of the License, or
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
import com.hmdzl.spspd.change.effects.Lightning;
import com.hmdzl.spspd.change.items.wands.WandOfTCloud;
import com.watabou.noosa.TextureFilm;

public class KeKeSprite extends MobSprite {

	public KeKeSprite() {
		super();

		texture(Assets.T_CLOUD);

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 5, true );
        idle.frames(frames, 4, 4, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7);

        run = new Animation( 54, true );
        run.frames( frames, 4 );

        attack = new Animation( 15, false );
        attack.frames( frames, 4, 5, 5 );

        die = new Animation( 24, false );
        die.frames( frames, 4 );
		
        zap = attack.clone();
		
		play(idle);
	}

	@Override
	public void zap(int pos) {

		parent.add( new Lightning( ch.pos, pos, (WandOfTCloud.STCloud) ch));

		turnTo(ch.pos, pos);
		play(zap);
	}
}