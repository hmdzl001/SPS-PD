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
package com.hmdzl.spspd.actors.blobs.effectblobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.particles.SnowParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;

public class FrostCloud extends Blob {

	@Override
	protected void evolve() {

		int from = WIDTH + 1;
		int to = Floor.getLength() - WIDTH - 1;

		for (int pos = from; pos < to; pos++) {

			int ice;
			if (cur[pos] > 0) {
				ice(pos);
				ice = cur[pos] - 1;
				if (ice <= 0){ }
			} else {
				ice = 0;
			}
			
			volume += (off[pos] = ice);

		}

	}

	private void ice(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null && !ch.isImmune(this.getClass())) {
			//if (ch.buff(Frost.class) != null) {
			Buff.affect(ch, FrostIce.class).level(10);
			//}

			Heap heap = Dungeon.depth.heaps.get(pos);
			if (heap != null) heap.icehit();
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
		emitter.start(SnowParticle.FACTORY, 0.05f, 0);
	}
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
