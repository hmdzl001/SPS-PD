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

import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Plant;

public class EntrancePainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.REGULAR);
		}

		level.entrance = room.random(1);
		set(level, level.entrance, Terrain.ENTRANCE);

        int dewbless = room.random();
        while (level.map[dewbless] == Terrain.ENTRANCE ) {
            dewbless = room.random();
        }

		Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED);
		level.explant(seed, room.random());


        //if (depth<25 && depth >1 ) {
           // level.map[dewbless] = Terrain.DEW_BLESS;
      //  }
		//if (Dungeon.gold > (2000000/(Math.max(1,20-Dungeon.depth))) && Dungeon.depth < 25){
		//	GoldCollector gc = new GoldCollector();
		//	gc.pos = room.random();
		//	level.mobs.add(gc);
		//	Actor.occupyCell(gc);
		//}

	}

}
