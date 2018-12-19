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

import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.CustomTileVisual;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class WeakFloorPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.CHASM);

		Room.Door door = room.entrance();
		door.set(Room.Door.Type.REGULAR);

		if (door.x == room.left) {
			for (int i = room.top + 1; i < room.bottom; i++) {
				drawInside(level, room, new Point(room.left, i),
						Random.IntRange(1, room.width() - 2), Terrain.EMPTY_SP);
			}
		} else if (door.x == room.right) {
			for (int i = room.top + 1; i < room.bottom; i++) {
				drawInside(level, room, new Point(room.right, i),
						Random.IntRange(1, room.width() - 2), Terrain.EMPTY_SP);
			}
		} else if (door.y == room.top) {
			for (int i = room.left + 1; i < room.right; i++) {
				drawInside(level, room, new Point(i, room.top),
						Random.IntRange(1, room.height() - 2), Terrain.EMPTY_SP);
			}
		} else if (door.y == room.bottom) {
			for (int i = room.left + 1; i < room.right; i++) {
				drawInside(level, room, new Point(i, room.bottom),
						Random.IntRange(1, room.height() - 2), Terrain.EMPTY_SP);
			}
		}
		
		Point well = null;
		if (door.x == room.left) {
			well = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top + 2 : room.bottom - 1 );
		} else if (door.x == room.right) {
			well = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top + 2 : room.bottom - 1 );
		} else if (door.y == room.top) {
			well = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.bottom-1 );
		} else if (door.y == room.bottom) {
			well = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.top+2 );
		}
		set(level, well, Terrain.CHASM);
		CustomTileVisual vis = new HiddenWell();
		vis.pos(well.x, well.y);
		level.customTiles.add(vis);		
	}

	public static class HiddenWell extends CustomTileVisual{

		{
			name = Messages.get(this, "name");

			tx = Assets.WEAK_FLOOR;
			txX = Dungeon.depth/5;
			txY = 0;
		}

		@Override
		public String desc() {
			return Messages.get(this, "desc");
		}
	}	
}
