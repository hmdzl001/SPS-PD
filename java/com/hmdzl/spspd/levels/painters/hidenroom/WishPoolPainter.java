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
package com.hmdzl.spspd.levels.painters.hidenroom;

import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Point;

public class WishPoolPainter extends Painter {

	public static void paint(Floor level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );


		Point c = room.center();
		Room.Door door = room.entrance();
        int pos = c.x + c.y * Floor.getWidth();
		set( level, pos, Terrain.STATUE);

		placeHMDZL(level, room);

        int n = 8;
        for (int i = 0; i < n; i++) {
            int p;
            do {
                p = room.random();
            } while (level.map[p] != Terrain.WATER
                    || level.heaps.get(p) != null);
            level.drop(prize(level), p);
        }
        for (int i = 0; i < 1; i++) {
            int p2;
            do {
                p2 = room.random();
            } while (level.map[p2] != Terrain.EMPTY_SP
                    || level.heaps.get(p2) != null);
            level.drop(prize2(level), p2);
        }

		door.set( Room.Door.Type.HIDDEN );
	}

    private static Item prize(Floor level) {
        return  Generator.random(Generator.Category.DEW);
    }

    private static Item prize2(Floor level) {
        return  Generator.random();
    }


    private static void placeHMDZL(Floor level, Room room) {
        Point center = room.center();
        int pos = room.center().x + room.center().y * Floor.getWidth();

        Mob hmdzl = Bestiary.exmob( 55 ) ;
        hmdzl.pos = pos;
        level.mobs.add(hmdzl);

        for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
            int p = hmdzl.pos + Floor.NEIGHBOURS8[i];
            if ( level.map[p] != Terrain.WALL_DECO &&
                    level.map[p] != Terrain.WALL &&
                    level.map[p] != Terrain.DOOR &&
                    level.map[p] != Terrain.SECRET_DOOR &&
                    level.map[p] != Terrain.GLASS_WALL) {
                level.map[p] = Terrain.WATER;
            }
        }
        PathFinder.buildDistanceMap( pos, BArray.not( Floor.solid, null ), 2 );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE) {
                if (level.map[i] != Terrain.WALL_DECO &&
                        level.map[i] != Terrain.WALL &&
                        level.map[i] != Terrain.DOOR &&
                        level.map[i] != Terrain.SECRET_DOOR &&
                        level.map[i] != Terrain.GLASS_WALL && Floor.insideMap(i)) {
                    level.map[i] = Terrain.EMPTY_SP;
                }
            }
        }
    }
}
