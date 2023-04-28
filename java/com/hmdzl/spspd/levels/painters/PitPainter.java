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

import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap.Type;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Fadeleaf;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class PitPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.ONEWAY);

		Point well = null;
		if (entrance.x == room.left) {
			well = new Point(room.right - 1, Random.Int(2) == 0 ? room.top + 1
					: room.bottom - 1);
		} else if (entrance.x == room.right) {
			well = new Point(room.left + 1, Random.Int(2) == 0 ? room.top + 1
					: room.bottom - 1);
		} else if (entrance.y == room.top) {
			well = new Point(Random.Int(2) == 0 ? room.left + 1
					: room.right - 1, room.bottom - 1);
		} else if (entrance.y == room.bottom) {
			well = new Point(Random.Int(2) == 0 ? room.left + 1
					: room.right - 1, room.top + 1);
		}
		set(level, well, Terrain.EMPTY_WELL);

		int remains = room.random();
		while (level.map[remains] == Terrain.EMPTY_WELL) {
			remains = room.random();
		}
		
		int sign = room.random();
		while (level.map[sign] == Terrain.EMPTY_WELL || sign == remains) {
			sign = room.random();
		}

		level.map[sign] = Terrain.SIGN;
		level.pitSign=sign;
		
		level.drop(new ScrollOfTeleportation(), remains).type = Type.SKELETON;
		int loot = Random.Int(3);
		if (loot == 0) {
			level.drop(Generator.random(Generator.Category.RING), remains);
		} else if (loot == 1) {
			level.drop(Generator.random(Generator.Category.ARTIFACT), remains);
		} else {
			level.drop(Generator.random(Random.oneOf(Generator.Category.MELEEWEAPON,
					Generator.Category.ARMOR)), remains);
		}
		level.drop(new Ankh(), remains);
		level.drop(new Fadeleaf.Seed(), remains);
		level.drop(new Fadeleaf.Seed(), remains);
		int n = Random.IntRange(1, 2);
		for (int i = 0; i < n; i++) {
			level.drop(prize(level), remains);
		}
	}

	private static Item prize(Level level) {

		if (Random.Int(2) != 0) {
			Item prize = level.findPrizeItem();
			if (prize != null)
				return prize;
		}

		return Generator.random(Random.oneOf(Generator.Category.POTION,
				Generator.Category.SCROLL, Generator.Category.FOOD,
				Generator.Category.GOLD));
	}
}
