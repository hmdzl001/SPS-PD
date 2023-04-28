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
package com.hmdzl.spspd.levels.painters.challengeroom;

import com.hmdzl.spspd.actors.mobs.Sentinel;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.Flytrap;
import com.hmdzl.spspd.plants.Phaseshift;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class WisdomRoomPainter extends Painter {

		
	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		Point c = room.center();
		set(level, c.x, c.y, Terrain.PEDESTAL);

		int bushes = Random.Int(3);
		if (bushes == 0) {
			level.plant(new Flytrap.Seed(),room.random());
		} else if (bushes == 1) {
			level.plant(new BlandfruitBush.Seed(), room.random());
		} else if (bushes == 2) {
			level.plant(new Phaseshift.Seed(), room.random());
		}

		Sentinel statue = new Sentinel();
		statue.pos = room.random();
		level.mobs.add(statue);

		room.entrance().set(Room.Door.Type.REGULAR);
	}
}
