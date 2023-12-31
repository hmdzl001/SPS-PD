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
package com.hmdzl.spspd.levels.painters.hidenroom;

import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.watabou.utils.Random;

public class GlassRoomPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);

		fill( level, room, 1, Terrain.GLASS_WALL);
		fill(level, room, 2, Terrain.EMPTY_SP);

		int cx = (room.left + room.right) / 2;
		int cy = (room.top + room.bottom) / 2;
		int c = cx + cy * Floor.getWidth();
		level.drop(prize(level), c);
		set(level, c, Terrain.PEDESTAL);

		int n = Random.IntRange(2, 3);
		for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY_SP
					&& level.heaps.get(pos) != null);
			level.drop(prize2(level), pos);
		}

		room.entrance().set( Room.Door.Type.HIDDEN );

		level.addItemToSpawn( new DungeonBomb.DoubleBomb() );
	}	
	private static Item prize(Floor level) {
        Item prize = Generator.random(Generator.Category.NORNSTONE);

        if (prize != null) {
            return prize;
        }
        return prize;
    }

	private static Item prize2(Floor level) {
		Item prize = new StoneOre();

		if (prize != null) {
			return prize;
		}
		return prize;
	}
}
