/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
 *
 * Goblins Pixel Dungeon
 * Copyright (C) 2016 Mario Braun
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

import com.watabou.noosa.TextureFilm;
import com.hmdzl.spspd.change.Assets;

public class FlySprite extends MobSprite {
	
	public FlySprite() {
		super();
		
		texture( Assets.SWARM );
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 15, true );
		idle.frames( frames, 16, 17, 18, 19, 20, 21 );
		
		run = new Animation( 15, true );
		run.frames( frames, 16, 17, 18, 19, 20, 21 );
		
		attack = new Animation( 20, false );
		attack.frames( frames, 22, 23, 24, 25 );
		
		die = new Animation( 15, false );
		die.frames( frames, 26, 27, 28, 29, 30 );
		
		play( idle );
	}
	
	@Override
	public int blood() {
		return 0xFF8BA077;
	}
}
