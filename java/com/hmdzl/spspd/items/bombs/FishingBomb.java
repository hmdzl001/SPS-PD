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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.PathFinder;

public class FishingBomb extends Bomb {

	{
		//name = "Fishing bomb";
		image = ItemSpriteSheet.FISH_BOMB;
	}

	@Override
	public void explode(int cell) {
		super.explode(cell);

		PathFinder.buildDistanceMap( cell, BArray.not( Floor.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				if (i >= 0 && i < Floor.getLength()) {
					if (Dungeon.visible[i]) {
						CellEmitter.get(i).burst(SmokeParticle.FACTORY, 4);
					}

					Char ch = Actor.findChar(i);
					if (ch != null) {
						if (ch instanceof Mob && !(ch instanceof Hero || ch instanceof NPC)) {

							int count = 20;
							int pos;
							do {
								pos = Dungeon.depth.randomRespawnCellFish();
								if (count-- <= 0) {
									break;
								}
							} while (pos == -1);

							if (pos == -1) {

								GLog.w(Messages.get(this, "no_tp"));

							} else {

								ch.pos = pos;
								ch.sprite.place(ch.pos);
								ch.sprite.visible = Dungeon.visible[pos];
								GLog.i(Messages.get(this, "tp"));

							}

						}
					}
				}
			}
		}
	}


	@Override
	public int price() {
		return 20 * quantity;
	}

	public FishingBomb() {
		this(1);
	}

	public FishingBomb(int value) {
		this.quantity = value;
	}
}
