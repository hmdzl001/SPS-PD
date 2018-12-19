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

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Random;

public class ElectriShock extends Blob {

	@Override
	protected void evolve() {

		//boolean[] shockable = Level.shockable;

		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;


		for (int pos = from; pos < to; pos++) {

			int shock;
			boolean shelf = false;
			
			if (cur[pos] > 0) {

				shocking(pos);

				shock = cur[pos] - 1;
				
                if (shock <= 0){   }
			} else {
				shock = 0;
			}
 
            volume += (off[pos] = shock);
		}

	}

	private void shocking(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null ) {
			ch.damage( Math.max( 1, Random.Int( ch.HP / 100, ch.HP / 50 ) ), this );
			Buff.prolong(ch, Paralysis.class,1f);
			
		}
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			heap.lit();
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
		emitter.start(EnergyParticle.FACTORY, 0.03f, 0);
	}
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}

	public void onDeath() {
		Dungeon.fail(Messages.format(ResultDescriptions.GAS));
	}	
}
