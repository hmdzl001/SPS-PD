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
import com.hmdzl.spspd.actors.mobs.Greatmoss;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.NutPlant;
import com.hmdzl.spspd.plants.Seedpod;
import com.watabou.utils.Random;

public class TreasuryPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.GRASS);

		room.entrance().set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.depth));

		level.plant(new Seedpod.Seed(), room.random());
		level.plant(new BlandfruitBush.Seed(), room.random());
        level.plant(new NutPlant.Seed(), room.random());
		
		int lashers = ((room.right-room.left-1)*(room.bottom-room.top-1))/10;

		for (int i = 1; i <= lashers; i++){
			int pos;
			do {
				pos = room.random();
			} while (!validPlantPos(level, pos));
			placePlant(level, pos, new Greatmoss());
		}
	}

	private static boolean validPlantPos(Level level, int pos){
		if (level.map[pos] != Terrain.GRASS){
			return false;
		}

		for (int i : Level.NEIGHBOURS9){
			if (level.findMob(pos+i) != null){
				return false;
			}
		}

		return true;
	}

	private static void placePlant(Level level, int pos, Mob plant){
		plant.pos = pos;
		level.mobs.add( plant );

		for(int i : Level.NEIGHBOURS8) {
			if (level.map[pos + i] == Terrain.GRASS){
				set(level, pos + i, Terrain.HIGH_GRASS);
			}
		}
	}		
	
}
