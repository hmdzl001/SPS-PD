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
package com.hmdzl.spspd.actors.blobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;

public class Water extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		if (volume > 0) {

			boolean mapUpdated = false;

			for (int i = 0; i < LENGTH; i++) {
				if (off[i] > 0) {
					int c = Dungeon.depth.map[i];
					if (c == Terrain.EMPTY || c == Terrain.EMBERS
							|| c == Terrain.EMPTY_DECO || c == Terrain.OLD_HIGH_GRASS) {

						Floor.set(i, cur[i] > 9 ? Terrain.HIGH_GRASS
								: Terrain.GRASS);
						mapUpdated = true;

					} else if (c == Terrain.GRASS && cur[i] > 9) {

						Floor.set(i, Terrain.HIGH_GRASS);
						mapUpdated = true;

					}

				}
			}

			if (mapUpdated) {
				GameScene.updateMap();
				Dungeon.observe();
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.start(LeafParticle.LEVEL_SPECIFIC, 0.2f, 0);
	}
}
