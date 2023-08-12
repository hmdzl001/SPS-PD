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

import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.particles.ShaftParticle;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;

public class TorchLight extends Blob {

	@Override
	protected void evolve() {

		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;

		for (int pos = from; pos < to; pos++) {
			if (cur[pos] > 0) {
				off[pos] = cur[pos];
				volume += off[pos];

			} else {
				off[pos] = 0;
			}
		}

	}


	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(ShaftParticle.FACTORY, 1.0f, 0);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
