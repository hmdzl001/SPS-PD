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
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.SnowParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.watabou.utils.Random;

public class Freezing {

	// Returns true, if this cell is visible
	public static boolean affect(int cell, Fire fire) {

		Char ch = Actor.findChar(cell);
		if (ch != null) {
			if (Level.water[ch.pos]) {
				Buff.prolong(ch, Frost.class,
						Frost.duration(ch) * Random.Float(5f, 7.5f));
			} else {
				Buff.prolong(ch, Frost.class,
						Frost.duration(ch) * Random.Float(1.0f, 1.5f));
			}
		}

		if (fire != null) {
			fire.clear(cell);
		}

		Heap heap = Dungeon.level.heaps.get(cell);
		if (heap != null) {
			heap.freeze();
		}

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(SnowParticle.FACTORY, 0.2f, 6);
			return true;
		} else {
			return false;
		}
	}
}
