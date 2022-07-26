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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.npcs.Sheep;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FlockTrap extends Trap {

	{
		color = TrapSprite.WHITE;
		shape = TrapSprite.WAVES;
	}


	@Override
	public void activate(Char ch) {
		super.activate(ch);
		//use an actor as we want to put this on a slight delay so all chars get a chance to act this turn first.
		Actor.add(new Actor() {

			protected boolean act() {
				int cell;
				for (int i : Level.NEIGHBOURS9DIST2) {
					cell = pos + i;
					if (Level.insideMap(cell) && Actor.findChar(cell) == null && !(Level.solid[cell] || Level.pit[cell])) {
						Sheep sheep = new Sheep();
						sheep.lifespan = 2 + Random.Int(Dungeon.depth + 10);
						sheep.pos = cell;
						GameScene.add(sheep);
					}
					CellEmitter.get(cell).burst(Speck.factory(Speck.WOOL), 4);
				}
				Sample.INSTANCE.play(Assets.SND_PUFF);
				Actor.remove(this);
				return true;
			}
		});

		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {heap.darkhit();}
	}

}
