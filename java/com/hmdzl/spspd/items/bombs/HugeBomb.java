/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.bombs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class HugeBomb extends Bomb {

	{
		//name = "cluster bomb";
		image = ItemSpriteSheet.HUGE_BOMB;
	}

	@Override
	public void explode(int cell) {
		super.explode(cell);
		boolean terrainAffected = false;
		PathFinder.buildDistanceMap( cell, BArray.not( Floor.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				if (i >= 0 && i < Floor.getLength()) {
					if (Dungeon.visible[i]) {
						CellEmitter.get(i).burst(SmokeParticle.FACTORY, 4);
					}

					if (Floor.flamable[i]) {
						Floor.set(i, Terrain.EMBERS);
						GameScene.updateMap(i);
						terrainAffected = true;
					}

					if ((Dungeon.depth.map[i] == Terrain.WALL || Dungeon.depth.map[i] == Terrain.GLASS_WALL) && Floor.insideMap(i)) {
						Floor.set(i, Terrain.EMPTY);
						GameScene.updateMap(i);
						terrainAffected = true;
					}

					// destroys items / triggers bombs caught in the blast.
					Heap heap = Dungeon.depth.heaps.get(i);
					if (heap != null)
						heap.explode();

					Char ch = Actor.findChar(i);
					if (ch != null) {
						// those not at the center of the blast take damage less
						// consistently.
						int minDamage = ch.HT / 8;
						int maxDamage = ch.HT / 4;

						int dmg = Random.NormalIntRange(minDamage, maxDamage)
								- Math.max(ch.drRoll(), 0);
						if (dmg > 0) {
							ch.damage(dmg, this,1);
						}
					}
				}
			}

			if (terrainAffected) {
				Dungeon.observe();
			}
		}
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

}
