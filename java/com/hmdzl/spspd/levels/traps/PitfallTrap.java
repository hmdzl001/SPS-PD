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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.WindParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.TrapSprite;

public class PitfallTrap extends Trap {

	{
		color = TrapSprite.RED;
		shape = TrapSprite.DIAMOND;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Heap heap = Dungeon.depth.heaps.get( pos );

		if (heap != null){
			for (Item item : heap.items){
				Dungeon.dropToChasm(item);
			}
			heap.sprite.kill();
			GameScene.discard(heap);
			Dungeon.depth.heaps.remove( pos );
		}

		//Char ch = Actor.findChar( pos );

		if (ch == Dungeon.hero){
			Chasm.heroFall( pos );
		} else if (ch != null){
			Chasm.mobFall((Mob)ch);
		}
	}

	@Override
	protected void disarm() {
		super.disarm();

		//if making a pit here wouldn't block any paths, make a pit tile instead of a disarmed trap tile.
		if (!(Dungeon.depth.solid[pos - Floor.WIDTH] && Dungeon.depth.solid[pos + Floor.WIDTH])
				&& !(Dungeon.depth.solid[pos - 1]&& Dungeon.depth.solid[pos + 1])){

			int c = Dungeon.depth.map[pos - Floor.WIDTH];

			if (c == Terrain.WALL || c == Terrain.WALL_DECO) {
				Floor.set(pos, Terrain.CHASM_WALL);
			} else {
				Floor.set( pos, Terrain.CHASM_FLOOR );
			}

			sprite.parent.add(new WindParticle.Wind(pos));
			sprite.kill();
			GameScene.updateMap( pos );
		}
	}
}
