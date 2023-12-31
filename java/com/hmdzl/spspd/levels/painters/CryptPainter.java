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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfDead;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Heap.Type;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class CryptPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		Point c = room.center();
		int cx = c.x;
		int cy = c.y;

		Room.Door entrance = room.entrance();

		entrance.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.dungeondepth));

		if (entrance.x == room.left) {
			set(level, new Point(room.right - 1, room.top + 1), Terrain.STATUE);
			set(level, new Point(room.right - 1, room.bottom - 1),
					Terrain.STATUE);
			cx = room.right - 2;
		} else if (entrance.x == room.right) {
			set(level, new Point(room.left + 1, room.top + 1), Terrain.STATUE);
			set(level, new Point(room.left + 1, room.bottom - 1),
					Terrain.STATUE);
			cx = room.left + 2;
		} else if (entrance.y == room.top) {
			set(level, new Point(room.left + 1, room.bottom - 1),
					Terrain.STATUE);
			set(level, new Point(room.right - 1, room.bottom - 1),
					Terrain.STATUE);
			cy = room.bottom - 2;
		} else if (entrance.y == room.bottom) {
			set(level, new Point(room.left + 1, room.top + 1), Terrain.STATUE);
			set(level, new Point(room.right - 1, room.top + 1), Terrain.STATUE);
			cy = room.top + 2;
		}

		level.drop(prize(level), cx + cy * Floor.getWidth()).type = Type.TOMB;

		if (Random.Int(10) > 5) {
			WeatherOfDead light = (WeatherOfDead) level.blobs.get(WeatherOfDead.class);
			if (light == null) {
				light = new WeatherOfDead();
			}
			for (int i = room.top + 1; i < room.bottom; i++) {
				for (int j = room.left + 1; j < room.right; j++) {
					light.seed(j + Floor.getWidth() * i, 1);
				}
			}
			level.blobs.put(WeatherOfDead.class, light);
		}
		
		Heap.Type heapType = Random.Int(2) == 0 ? Heap.Type.CHEST
				: Heap.Type.HEAP;

		int n = Random.IntRange(2, 3);
		for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(new Gold().random(), pos).type = (i == 0
					&& heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
		}		
		if (heapType == Heap.Type.HEAP) {
			for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(new Gold().random(), pos).type = (i == 0
					&& heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
			}
		}
	}

	private static Item prize(Floor level) {

		Item prize = Generator.random(Generator.Category.ARMOR);

		for (int i = 0; i < 3; i++) {
			Item another = Generator.random(Generator.Category.ARMOR);
			if (another.level > prize.level) {
				prize = another;
			}
		}

		return prize;
	}
}
