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
package com.hmdzl.spspd.change.levels.painters;

import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.Terrain;
import com.watabou.utils.Random;

public class StoragePainter extends Painter {

	public static void paint( Level level, Room room ) {
		
		final int floor = Terrain.EMPTY_SP;
		
		fill( level, room, Terrain.WALL );
		fill( level, room, 1, floor );

        Room.Door entrance = room.entrance();

        if (entrance.x == room.left) {
            fill( level, room.right - 1, room.top + 1, 1, room.height() - 1 , Terrain.EMPTY );
            fill( level, room.right - 2, room.top + 1, 1, room.height() - 1 , Terrain.BOOKSHELF );
        } else if (entrance.x == room.right) {
            fill( level, room.left + 1, room.top + 1, 1, room.height() - 1 , Terrain.EMPTY );
            fill( level, room.left + 2, room.top + 1, 1, room.height() - 1 , Terrain.BOOKSHELF );
        } else if (entrance.y == room.top) {
            fill( level, room.left + 1, room.bottom - 1, room.width() - 1, 1 , Terrain.EMPTY );
            fill( level, room.left + 1, room.bottom - 2, room.width() - 1, 1 , Terrain.BOOKSHELF );
        } else if (entrance.y == room.bottom) {
            fill( level, room.left + 1, room.top + 1, room.width() - 1, 1 , Terrain.EMPTY );
            fill( level, room.left + 1, room.top + 2, room.width() - 1, 1 , Terrain.BOOKSHELF );
        }
		
		int n = 2 + Random.Int( 3 );
		for (int i=0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY);
			level.drop( prize( level ), pos ).type = Heap.Type.SKELETON;
		}

        int pos;
        do {
            pos = room.random();
        } while (level.map[pos] != floor);
        level.drop( prize( level ), pos ).type = Heap.Type.CHEST;

		room.entrance().set( Room.Door.Type.REGULAR );
		level.addItemToSpawn( new PotionOfLiquidFlame() );
	}
	
	private static Item prize( Level level ) {

		Item prize =  Generator.random();

        if (prize != null) {
            return prize;
        }
		
		return Generator.random( Random.oneOf(
			Generator.Category.POTION,
			Generator.Category.SCROLL,
			Generator.Category.GOLD,
			Generator.Category.WAND
		) );
	}
}
