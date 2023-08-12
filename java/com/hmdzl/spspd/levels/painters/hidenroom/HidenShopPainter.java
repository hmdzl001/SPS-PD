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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.HoneyPoooot;
import com.hmdzl.spspd.actors.mobs.npcs.Ice13;
import com.hmdzl.spspd.actors.mobs.npcs.SaidbySun;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HidenShopPainter extends Painter {

	private static int pasWidth;
	private static int pasHeight;

	private static ArrayList<Item> itemsToSpawn;
	private static ArrayList<Item> itemsToSpawn2;

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		pasWidth = room.width() - 2;
		pasHeight = room.height() - 2;
		int per = pasWidth * 2 + pasHeight * 2;

		if (itemsToSpawn == null  ||  itemsToSpawn2 == null)
			generateItems();

		int pos = xy2p(room, room.entrance()) + (per - itemsToSpawn.size()) / 2;
		for (Item item : itemsToSpawn) {

			Point xy = p2xy(room, (pos + per) % per);
			int cell = xy.x + xy.y * Level.getWidth();

			if (level.heaps.get(cell) != null) {
				do {
					cell = room.random();
				} while (level.heaps.get(cell) != null);
			}
			level.drop(item, cell).type = Heap.Type.FOR_LIFE;
			pos++;
		}

		for (Item item : itemsToSpawn2) {

			Point xy = p2xy(room, (pos + per) % per);
			int cell = xy.x + xy.y * Level.getWidth();

			if (level.heaps.get(cell) != null) {
				do {
					cell = room.random();
				} while (level.heaps.get(cell) != null);
			}
			level.drop(item, cell).type = Heap.Type.FOR_SALE;
			pos++;
		}

		placeShopkeeper(level, room);

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.HIDDEN);
		}

		itemsToSpawn = null;
	}

	private static void generateItems() {

		itemsToSpawn = new ArrayList<Item>();
		itemsToSpawn.add(Generator.random(Generator.Category.MELEEWEAPON).identify().uncurse().upgrade(Dungeon.depth));
		itemsToSpawn.add(Generator.random(Generator.Category.ARMOR).identify().uncurse().upgrade(Dungeon.depth));
		itemsToSpawn.add(Generator.random(Generator.Category.WAND).identify().uncurse().upgrade(Dungeon.depth));
		itemsToSpawn.add(Generator.random(Generator.Category.RING).identify().uncurse().upgrade(Dungeon.depth));
		itemsToSpawn.add(Generator.random(Generator.Category.ARTIFACT));
		itemsToSpawn.add(new Ankh());

		itemsToSpawn2 = new ArrayList<Item>();
		itemsToSpawn2.add(Generator.random(Generator.Category.POTION));
		itemsToSpawn2.add(Generator.random(Generator.Category.SCROLL));
		itemsToSpawn2.add(Generator.random(Generator.Category.LINKDROP));
		itemsToSpawn2.add(Generator.random(Generator.Category.MELEEWEAPON).uncurse().upgrade(Dungeon.depth));
		itemsToSpawn2.add(Generator.random(Generator.Category.ARMOR).uncurse().upgrade(Dungeon.depth));
		itemsToSpawn2.add(Generator.random(Generator.Category.WAND).uncurse().upgrade(Dungeon.depth));
		itemsToSpawn2.add(Generator.random(Generator.Category.RING).uncurse().upgrade(Dungeon.depth));
		itemsToSpawn2.add(Generator.random(Generator.Category.ARTIFACT));

		// this is a hard limit, level gen allows for at most an 8x5 room, can't
		// fit more than 39 items + 1 shopkeeper.
		if (itemsToSpawn.size() + itemsToSpawn2.size() > 39)
			throw new RuntimeException(
					"Shop attempted to carry more than 39 items!");

	}


	public static int spaceNeeded() {
		if (itemsToSpawn == null)
			generateItems();

		// plus one for the shopkeeper
		return itemsToSpawn.size() + 1;
	}

	private static void placeShopkeeper(Level level, Room room) {

		int pos;
		do {
			pos = room.random();
		} while (level.heaps.get(pos) != null);


		switch (Random.Int(3)) {
			case 0:
				Mob shoper0 = new Ice13();
				shoper0.pos = pos;
				level.mobs.add(shoper0);
				for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
					int p = shoper0.pos + Level.NEIGHBOURS9[i];
					if (level.map[p] == Terrain.EMPTY_SP) {
						level.map[p] = Terrain.PEDESTAL;
					}
				}
				break;
			case 1:
				Mob shoper1 = new HoneyPoooot();
				shoper1.pos = pos;
				level.mobs.add(shoper1);
				for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
					int p = shoper1.pos + Level.NEIGHBOURS9[i];
					if (level.map[p] == Terrain.EMPTY_SP) {
						level.map[p] = Terrain.PEDESTAL;
					}
				}
				break;
			case 2:
				Mob shoper2 = new SaidbySun();
				shoper2.pos = pos;
				level.mobs.add(shoper2);
				for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
					int p = shoper2.pos + Level.NEIGHBOURS9[i];
					if (level.map[p] == Terrain.EMPTY_SP) {
						level.map[p] = Terrain.PEDESTAL;
					}
				}
				break;
		}

		

		
	}

	private static int xy2p(Room room, Point xy) {
		if (xy.y == room.top) {

			return (xy.x - room.left - 1);

		} else if (xy.x == room.right) {

			return (xy.y - room.top - 1) + pasWidth;

		} else if (xy.y == room.bottom) {

			return (room.right - xy.x - 1) + pasWidth + pasHeight;

		} else {

			if (xy.y == room.top + 1) {
				return 0;
			} else {
				return (room.bottom - xy.y - 1) + pasWidth * 2 + pasHeight;
			}

		}
	}

	private static Point p2xy(Room room, int p) {
		if (p < pasWidth) {

			return new Point(room.left + 1 + p, room.top + 1);

		} else if (p < pasWidth + pasHeight) {

			return new Point(room.right - 1, room.top + 1 + (p - pasWidth));

		} else if (p < pasWidth * 2 + pasHeight) {

			return new Point(room.right - 1 - (p - (pasWidth + pasHeight)),
					room.bottom - 1);

		} else {

			return new Point(room.left + 1, room.bottom - 1
					- (p - (pasWidth * 2 + pasHeight)));

		}
	}
}
