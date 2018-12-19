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
package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.particles.FlameParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.items.Generator;
import com.watabou.utils.Random;

public class Fire extends Blob {

	@Override
	protected void evolve() {

		boolean[] flamable = Level.flamable;

		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;

		boolean observe = false;

		for (int pos = from; pos < to; pos++) {

			int fire;
			boolean shelf = false;
			
        Blob blob = Dungeon.level.blobs.get( TarGas.class );

        if (blob != null) {

            int par[] = blob.cur;

            for (int i=0; i < LENGTH; i++) {

                if (cur[i] > 0) {
                    blob.volume -= par[i];
                    par[i] = 0;
                }
            }
        }
			
			if (cur[pos] > 0) {

				burn(pos);

				fire = cur[pos] - 1;
				if (fire <= 0 && flamable[pos]) {
					
					if(Dungeon.level.map[pos]==Terrain.BOOKSHELF){
						shelf=true;
					}

					int oldTile = Dungeon.level.map[pos];					
					Level.set(pos, Terrain.EMBERS);
					
					if (shelf && Random.Float()<.10){
						Dungeon.level.drop(Generator.random(Generator.Category.SCROLL), pos);				
						}
						
					observe = true;
					GameScene.updateMap(pos);
					if (Dungeon.visible[pos]) {
						GameScene.discoverTile(pos, oldTile);
					}
				}

			} else {

				if (flamable[pos]
						&& (cur[pos - 1] > 0 || cur[pos + 1] > 0
								|| cur[pos - WIDTH] > 0 || cur[pos + WIDTH] > 0)) {
					fire = 4;
					burn(pos);
				} else {
					fire = 0;
				}

			}

			volume += (off[pos] = fire);

		}

		if (observe) {
			Dungeon.observe();
		}
	}

	private void burn(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null ) {
			Buff.affect(ch, Burning.class).reignite(ch);
		}
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			heap.burn();
		}
		
		if( Dungeon.level.map[pos] == Terrain.SECRET_DOOR) {

            GameScene.discoverTile( pos, Dungeon.level.map[pos] );

            Level.set( pos, Terrain.DOOR);

            GameScene.updateMap( pos );
        }
	}

	@Override
	public void seed(int cell, int amount) {
		if (cur[cell] == 0) {
			volume += amount;
			cur[cell] = amount;
		/*} else {
			volume += amount - cur[cell];
			cur[cell] = amount;*/
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(FlameParticle.FACTORY, 0.1f, 0);
	}
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
