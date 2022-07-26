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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;

public class DarkGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;
		for (int pos = from; pos < to; pos++) {
			int dark;
			if (cur[pos] > 0) {
				dark(pos);
				dark = cur[pos] - 1;
				if (dark <= 0){ }
			} else {
				dark = 0;
			}

			volume += (off[pos] = dark);

		}

	}

	private void dark(int pos) {
		Char ch = Actor.findChar( pos );
		if (ch != null && !ch.isImmune(this.getClass())) {
			Buff.prolong(ch, Blindness.class, 3);
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) heap.darkhit();
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(Speck.factory(Speck.DARKNESS), 0.6f);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}