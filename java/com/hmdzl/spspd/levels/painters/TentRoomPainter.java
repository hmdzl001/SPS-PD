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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ExProtect;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Plant;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class TentRoomPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMBERS);
		fill( level, room, 2, Terrain.EMPTY );

		set(level, new Point(room.right - 2, room.top + 2), Terrain.FLOWER_POT);
		set(level, new Point(room.right - 2, room.bottom - 2), Terrain.ALCHEMY);
		set(level, new Point(room.left + 2, room.top + 2), Terrain.IRON_MAKER);
		set(level, new Point(room.left + 2, room.bottom - 2), Terrain.TENT);

		Point c = room.center();
		set(level, c.x, c.y, Terrain.STATUE_SP);

		Point p = new Point(room.right - 2, room.top + 2);
		Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED);
		level.explant(seed, (room.right - 2) + (room.top + 2) * Floor.getWidth());

		room.entrance().set(Room.Door.Type.REGULAR);
		//level.addItemToSpawn(new IronKey(Dungeon.depth));

		Mob mob1 = Dungeon.shopOnLevel() ? Bestiary.exmob( 55 ) :  Bestiary.exmob( 85 );
		Mob mob2 = Dungeon.shopOnLevel() ? Bestiary.exmob( 55 ) :  Bestiary.exmob( 85 );

		if (!Dungeon.shopOnLevel()) {
			Buff.affect(mob1, ExProtect.class);
			Buff.affect(mob2, ExProtect.class);
		}

		int i;
		int j;
		do {
			i = room.random();
			j = room.random();
		} while (level.map[i] != Terrain.EMBERS || level.map[j] != Terrain.EMBERS || i == j );

		mob1.pos = i;
		level.mobs.add(mob1);
		//Actor.occupyCell(mob1);

		mob2.pos = j;
		level.mobs.add(mob2);
		//Actor.occupyCell(mob2);
	}

	private static Item prize(Floor level) {
		return Generator.random(Random.oneOf(Generator.Category.WAND,
				Generator.Category.RING, Generator.Category.ARTIFACT));
	}
}
