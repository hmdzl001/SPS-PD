/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.watabou.utils.Random;

public class BarricadedPainter extends Painter {

	public static void paint( Level level, Room room ) {
		
		final int floor = Terrain.EMPTY_SP;
		
		fill( level, room, Terrain.WALL );
		fill( level, room, 1, floor );

        if (room.width() > room.height()) {
            for (int i=room.left + 2; i < room.right; i += 2) {
                fill( level, i, room.top + 2, 1, room.height() - 3, Terrain.BOOKSHELF );
            }
        } else {
            for (int i=room.top + 2; i < room.bottom; i += 2) {
                fill( level, room.left + 2, i, room.width() - 3, 1, Terrain.BOOKSHELF);
            }
        }
		
		int n = 2 + Random.Int( 0,2 );
		for (int i=0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != floor);
			level.drop( prize( level ), pos ).type = Heap.Type.SKELETON;
		}
		
		room.entrance().set( Room.Door.Type.HIDDEN );
		//level.addItemToSpawn( new PotionOfLiquidFlame() );
	}
	
	private static Item prize( Level level ) {

		Item prize = Generator.random();

        if (prize != null) {
            return prize;
        }
		
		return Generator.random( Random.oneOf(
			Generator.Category.POTION,
			Generator.Category.SCROLL,
			Generator.Category.GOLD,
			Generator.Category.NORNSTONE
		) );
	}
}
