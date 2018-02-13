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
import com.hmdzl.spspd.change.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.levels.Level;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;

public class LerySprite extends MobSprite {

	public LerySprite() {
		super();

		texture( Assets.ELEMENTAL );

		TextureFilm frames = new TextureFilm( texture, 12, 14 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 21, 22, 23 );
		
		run = new Animation( 12, true );
		run.frames( frames, 21, 22, 24 );
		
		attack = new Animation( 15, false );
		attack.frames( frames, 25, 26, 27 );
		
		zap = attack.clone();
		
		die = new Animation( 15, false );
		die.frames( frames, 28, 29, 30, 31, 32, 33, 34, 33 );
		
		play( idle );
    }

	@Override
	public void link( Char ch ) {
		super.link( ch );
		add( CharSprite.State.BURNING );
	}	
	@Override
	public void die() {
		super.die();
		remove( CharSprite.State.BURNING );
	}

	@Override
	public void zap(int cell) {

		turnTo(ch.pos, cell);
		play(zap);

		MagicMissile.fire(parent, ch.pos, cell, new Callback() {
			@Override
			public void call() {
				((LeryFire) ch).onZapComplete();
			}
		});
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
	@Override
	public int blood() {
		return 0xFFFF7D13;
	}

	
}
