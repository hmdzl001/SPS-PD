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
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.ShoppingCart;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class RuinRoomPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_DECO);

		int cx = (room.left + room.right) / 2;
		int cy = (room.top + room.bottom) / 2;
		int c = cx + cy * Floor.getWidth();

		Room.Door door = room.entrance();
		door.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.dungeondepth));

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

		if (!Dungeon.LimitedDrops.shopcart.dropped()){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new ShoppingCart(), pos);
			Dungeon.LimitedDrops.shopcart.drop();
		}


		switch (Random.Int(3)) {

			case 0: case 1: case 2:
				Item i1,i2,i3,i4;
				i1 = prize(level);
				i2 = prize2(level);
				i3 = prize3(level);
				i4 = prize4(level);
				level.drop(i1, c).type = Heap.Type.CHEST;
				level.drop(i2, c + Floor.NEIGHBOURS8[Random.Int(8)]).type = Heap.Type.M_WEB;

				int pos; int pos2;
				do {
					pos = room.random();
				} while (level.heaps.get(pos) != null);
				do {
					pos2 = room.random();
				} while (level.heaps.get(pos) != null  &&  pos2 == pos);


				level.drop(i3, pos).type= Heap.Type.REMAINS;
				level.drop(i4,pos2 ).type= Heap.Type.E_DUST;

				break;
		}

		Room.Door entrance = room.entrance();

		Point pot = null;
		if (entrance.x == room.left) {
			pot = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
			set( level, entrance.x + 1, entrance.y, Terrain.EMPTY_SP );
		} else if (entrance.x == room.right) {
			pot = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
			set( level, entrance.x - 1, entrance.y, Terrain.EMPTY_SP );
		} else if (entrance.y == room.top) {
			pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.bottom-1 );
			set(level, entrance.x, entrance.y + 1, Terrain.EMPTY_SP);
		} else if (entrance.y == room.bottom) {
			pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.top+1 );
			set(level, entrance.x, entrance.y - 1, Terrain.EMPTY_SP);
		}

		set( level, pot, Terrain.IRON_MAKER );

	}

    private static Item prize(Floor level) {
        return Generator.random(Random.oneOf(Generator.Category.BASEPET,
                Generator.Category.RING, Generator.Category.ARTIFACT));
    }

    private static Item prize2(Floor level) {
        return Generator.random(Random.oneOf(Generator.Category.FOOD,
                Generator.Category.HIGHFOOD, Generator.Category.PILL));
    }

    private static Item prize3(Floor level) {
        return Generator.random(Random.oneOf(Generator.Category.MELEEWEAPON,
                Generator.Category.ARMOR, Generator.Category.WAND));
    }

	private static Item prize4(Floor level) {
		return Generator.random(Random.oneOf(Generator.Category.DEW,
				Generator.Category.SEED, Generator.Category.NORNSTONE));
	}

}
