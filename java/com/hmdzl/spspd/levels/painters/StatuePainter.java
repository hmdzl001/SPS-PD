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
import com.hmdzl.spspd.actors.mobs.ArmorStatue;
import com.hmdzl.spspd.actors.mobs.Statue;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class StatuePainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		Point c = room.center();
		int cx = c.x;
		int cy = c.y;

		Room.Door door = room.entrance();

		door.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.dungeondepth));

		if (door.x == room.left) {

			fill(level, room.right - 1, room.top + 1, 1, room.height() - 1,
					Terrain.STATUE);
			cx = room.right - 2;

		} else if (door.x == room.right) {

			fill(level, room.left + 1, room.top + 1, 1, room.height() - 1,
					Terrain.STATUE);
			cx = room.left + 2;

		} else if (door.y == room.top) {

			fill(level, room.left + 1, room.bottom - 1, room.width() - 1, 1,
					Terrain.STATUE);
			cy = room.bottom - 2;

		} else if (door.y == room.bottom) {

			fill(level, room.left + 1, room.top + 1, room.width() - 1, 1,
					Terrain.STATUE);
			cy = room.top + 2;

		}

		int n = Random.IntRange(2, 3);
		for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(prize(level), pos);
		}

		Statue statue = new Statue();
		statue.pos = cx + cy * Floor.getWidth();
		level.mobs.add(statue);
		//Actor.occupyCell(statue);

		ArmorStatue astatue = new ArmorStatue();
		astatue.pos = c.x + c.y * Floor.getWidth();
		level.mobs.add(astatue);
		//Actor.occupyCell(astatue);
	}
	
	private static Item prize(Floor level) {
		return  Generator.random(Random.oneOf(Generator.Category.ARMOR,
						Generator.Category.MELEEWEAPON));
	}
}
