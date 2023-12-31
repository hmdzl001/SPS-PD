/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.levels.traps;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.watabou.noosa.audio.Sample;

public class BoundTrap extends Trap {

	{
		color = TrapSprite.ORANGE;
		shape = TrapSprite.GRILL;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		if (Dungeon.visible[ pos ]) {
			CellEmitter.get(pos).burst(Speck.factory(Speck.STAR), 10);
			Sample.INSTANCE.play(Assets.SND_GOLD);
		}
		Dungeon.depth.drop(Generator.random(), pos).sprite.drop();
	}
}
