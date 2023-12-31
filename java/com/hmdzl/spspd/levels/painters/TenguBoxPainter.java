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
package com.hmdzl.spspd.levels.painters;

import com.hmdzl.spspd.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.ReNepenth;
import com.hmdzl.spspd.plants.Starflower;
import com.watabou.utils.Random;

public class TenguBoxPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.HIDDEN);
		int door = entrance.x + entrance.y * Floor.getWidth();
		
		//Dungeon.ratChests=0;

		for (int i = room.left + 1; i < room.right; i++) {
			addChest(level, (room.top + 1) * Floor.getWidth() + i, door);
			addChest(level, (room.bottom - 1) * Floor.getWidth() + i, door);
		}

		for (int i = room.top + 2; i < room.bottom - 1; i++) {
			addChest(level, i * Floor.getWidth() + room.left + 1, door);
			addChest(level, i * Floor.getWidth() + room.right - 1, door);
		}

		//while (true) {
		//	Heap chest = level.heaps.get(room.random());
		//	if (chest != null) {
		//		chest.type = Heap.Type.MIMIC;
		//		break;
		//	}
	//	}

		StormAndRain king = new StormAndRain();
		king.pos = room.random(1);
		level.mobs.add(king);
	}

	private static void addChest(Floor level, int pos, int door) {

		if (pos == door - 1 || pos == door + 1 || pos == door - Floor.getWidth()
				|| pos == door + Floor.getWidth()) {
			return;
		}

		Item prize;
		switch (Random.Int(8)) {
		case 0:
			prize = new Egg();
			break;
		case 1:
			prize = new ReNepenth.Seed();
			break;
		case 2:
			prize = Generator.random(Generator.Category.BERRY);
			break;
		case 3:
			prize =  new Starflower.Seed();
			break;
		case 5:
			prize =  new ActiveMrDestructo();
			break;
		case 6:
			prize =  Generator.random(Generator.Category.BOMBS);
			break;
		default:
			prize = new Gold(Random.IntRange(1, 5));
			break;
		}

		level.drop(prize, pos).type = Heap.Type.CHEST;
		//Dungeon.ratChests++;
	}
}
