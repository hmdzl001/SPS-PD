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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Floor;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class NPC extends Mob {

	public int FRIEND;

    {
		HP = HT = 1;
		EXP = 0;

		hostile = false;
		state = PASSIVE;
		
	}

	private static final String TAG_FRIEND = "friend";


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TAG_FRIEND, FRIEND);

	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		FRIEND = bundle.getInt(TAG_FRIEND);
	}

	protected void throwItem() {
		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null  && heap.type != Heap.Type.FOR_SALE  && heap.type != Heap.Type.FOR_LIFE) {
			int n;
			do {
				n = pos + Floor.NEIGHBOURS8[Random.Int(8)];
			} while (!Floor.passable[n] && !Floor.avoid[n]);
			Dungeon.depth.drop(heap.pickUp(), n).sprite.drop(pos);
		}
	}

	protected void pushother() {
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		for (int n : Floor.NEIGHBOURS8) {
		int cell = this.pos + n;
		if ((Floor.passable[cell] || Floor.avoid[cell])) {
			candidates.add(cell);
		    }
	    }
		for (Mob other : Dungeon.depth.mobs) {
			if (other.pos == this.pos) {
				if (other != this) {
					if (candidates.size() > 0) {
						int newPos = Random.element(candidates);
						//Actor.addDelayed(new Pushing(other, other.pos, newPos), -1);
						other.pos = newPos;
						other.sprite.move(pos, newPos);
					}
				}
			}
		}
	}

	@Override
	public void beckon(int cell) {
	}

	abstract public boolean interact();

}