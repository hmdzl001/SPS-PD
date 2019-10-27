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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.blobs.WaterOfAwareness;
import com.hmdzl.spspd.change.actors.blobs.WaterOfHealth;
import com.hmdzl.spspd.change.actors.blobs.WaterOfTransmutation;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.plants.BlandfruitBush;
import com.hmdzl.spspd.change.plants.Flytrap;
import com.hmdzl.spspd.change.plants.Phaseshift;
import com.hmdzl.spspd.change.plants.Seedpod;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MagicWellPainter extends Painter {

	private static final Class<?>[] WATERS = { WaterOfAwareness.class,
			WaterOfHealth.class, WaterOfTransmutation.class };
		
	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		Point c = room.center();
		set(level, c.x, c.y, Terrain.WELL);

		@SuppressWarnings("unchecked")
		Class<? extends WellWater> waterClass = (Class<? extends WellWater>) Random.element(WATERS);

		
	    WellWater water = (WellWater) level.blobs.get(waterClass);
		  if (water == null) {
			try {
				water = waterClass.newInstance();
			} catch (Exception e) {
				water = null;
			}
		 }

		int bushes = Random.Int(3);
		if (bushes == 0) {
			level.plant(new Flytrap.Seed(), room.random());
		} else if (bushes == 1) {
			level.plant(new BlandfruitBush.Seed(), room.random());
		} else if (bushes == 2) {
			level.plant(new Phaseshift.Seed(), room.random());
		}
	
		water.seed(c.x + Level.getWidth() * c.y, 1);
		level.blobs.put(waterClass, water);

		room.entrance().set(Room.Door.Type.REGULAR);
	}
}
