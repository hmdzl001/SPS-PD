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
package com.hmdzl.spspd.levels;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer1;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Scene;
import com.watabou.utils.Graph;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.List;

import static com.hmdzl.spspd.items.Heap.Type.HEAP;

public class BetweenLevel extends RegularLevel {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
	}

	@Override
	public String tilesTex() {
		String result = null;
		 switch (Dungeon.dungeondepth){
			case 1:
				 result = Assets.TILES_SP;
				 break;
			case 6:
				result = Assets.TILES_PRISON;
				break;
			case 11:
				result = Assets.TILES_BEACH;
				break;
			case 16:
				result = Assets.TILES_CITY;
				break;
			case 21:
				result = Assets.TILES_HALLS;
				break;

		}
		return result;
	}

	@Override
	public String waterTex() {
		String result2 = null;
		switch (Dungeon.dungeondepth){
			case 1:
				result2 = Assets.WATER_SEWERS;
				break;
			case 6:
				result2 = Assets.WATER_PRISON;
				break;
			case 11:
				result2 = Assets.WATER_CAVES;
				break;
			case 16:
				result2 = Assets.WATER_CITY;
				break;
			case 21:
				result2 = Assets.WATER_HALLS;
				break;

		}
		return result2;
	}

	@Override
	protected boolean build() {

		//feeling = Feeling.CHASM;
		viewDistance = 6;

		initRooms();

		int distance;
		int retry = 0;
		int minDistance = (int) Math.sqrt(rooms.size());
		do {
			int innerRetry = 0;
			do {
				if (innerRetry++ > 10) {
					return false;
				}
				roomEntrance = Random.element(rooms);
			} while (roomEntrance.width() < 4 || roomEntrance.height() < 4);

			innerRetry = 0;
			do {
				if (innerRetry++ > 10) {
					return false;
				}
				roomExit = Random.element(rooms);
			} while (roomExit == roomEntrance || roomExit.width() < 6
					|| roomExit.height() < 6 || roomExit.top == 0);

			Graph.buildDistanceMap(rooms, roomExit);
			distance = Graph.buildPath(rooms, roomEntrance, roomExit).size();

			if (retry++ > 10) {
				return false;
			}

		} while (distance < minDistance);

		roomEntrance.type = Type.ENTRANCE;
		roomExit.type = Type.EXIT;

		Graph.buildDistanceMap(rooms, roomExit);
		List<Room> path = Graph.buildPath(rooms, roomEntrance, roomExit);

		Graph.setPrice(path, roomEntrance.distance);

		Graph.buildDistanceMap(rooms, roomExit);
		path = Graph.buildPath(rooms, roomEntrance, roomExit);

		Room room = roomEntrance;
		for (Room next : path) {
			room.connect(next);
			room = next;
		}

		Room roomShop = null;
		int shopSquare = 0;
		for (Room r : rooms) {
			if (r.type == Type.NULL && r.connected.size() > 0) {
				r.type = Type.PASSAGE;
				if (r.square() > shopSquare) {
					roomShop = r;
					shopSquare = r.square();
				}
			}
		}

		ArrayList<Room> candidates = new ArrayList<Room>();
		for (Room r : roomShop.neigbours) {
			if (r.type == Type.NULL && r.connected.size() == 0
					&& !r.neigbours.contains(roomEntrance)) {
				candidates.add(r);
			}
		}
		// if we have candidates, pick a room and put the king there
		if (candidates.size() > 0) {

			Room kingsRoom = Random.element(candidates);
			if (kingsRoom == null || kingsRoom.square() < 54) {
				return false;
			} else {
				kingsRoom.type =  Type.TENTROOM;
			}
			kingsRoom.connect(roomShop);
			// unacceptable! make a new level...
		} else {
			return false;
		}

		if (roomShop == null || shopSquare < 54) {
			return false;
		} else {
			roomShop.type =  Room.Type.SHOP;
		}

		paint();

		paintWater();
		paintGrass();

		return true;
	}

	@Override
	protected void decorate() {

		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && Random.Int(10) == 0) {

				map[i] = Terrain.EMPTY_DECO;

			} else if (map[i] == Terrain.WALL && Random.Int(8) == 0) {

				map[i] = Terrain.WALL_DECO;

			} else if (map[i] == Terrain.SECRET_DOOR) {

				map[i] = Terrain.DOOR;

			}
		}

		
			while (true) {
				int pos = roomEntrance.random();
				if (pos != entrance) {
					map[pos] = Terrain.SIGN;
					break;
				}
			}

		for (int i = 0; i < getLength(); i++) {

			if (map[i] == Terrain.EXIT && Dungeon.dungeondepth == 1) {
				map[i] = Terrain.LOCKED_EXIT;
				//sealedlevel = true;
			}
		}

	}

	@Override
	protected void createMobs() {
	}

	@Override
	public Actor respawner() {
		return null;
	}


	@Override
	protected void createItems() {
		if (Dungeon.dungeondepth == 1) {
			addItemToSpawn(new Moonberry());
			addItemToSpawn(new Blueberry());
			addItemToSpawn(new Cloudberry());
			addItemToSpawn(new Blackberry());
			addItemToSpawn(new Mushroom());
		}

		for (Item item : itemsToSpawn) {
			int cell = randomFallRespawnCell();
			if (item instanceof Scroll) {
				while (map[cell] == Terrain.TRAP
						|| map[cell] == Terrain.SECRET_TRAP
						&& traps.get( cell ) instanceof FireDamageTrap) {
					cell = randomDropCell();
				}
			}
			drop(item, cell).type = HEAP;
		}
	}

	@Override
	public int randomRespawnCell() {

		int count = 0;
		int cell = -1;

		while (true) {

			if (++count > 30) {
				return -1;
			}

			Room room = randomRoom(Type.ENTRANCE, 10);
			if (room == null) {
				continue;
			}

			cell = room.random();
			if (!Dungeon.visible[cell] && Actor.findChar(cell) == null
					&& Floor.passable[cell]) {
				return cell;
			}

		}

	}

	//@Override
	protected int randomSpecialDropCell() {
		while (true) {
			Room room = randomNormalRoom(Type.EXIT, 1);
			if (room != null) {
				int pos = room.random();
				if (heaps.get(pos) != null) {
					do {
						pos = room.random();
					} while (heaps.get(pos) != null);
				}
				if (passable[pos]) {
					return pos;
				}
			}
		}
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(CityLevel.class, "water_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(CityLevel.class, "high_grass_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(CityLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(CityLevel.class, "exit_desc");
			case Terrain.WALL_DECO:
			case Terrain.EMPTY_DECO:
				return Messages.get(CityLevel.class, "deco_desc");
			case Terrain.EMPTY_SP:
				return Messages.get(CityLevel.class, "sp_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(CityLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(CityLevel.class, "bookshelf_desc");
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(0.35f, 4);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(0.30f, 3);
	}

	@Override
	protected boolean[] chasm() {
		return Patch.generate(0, 3);
	}

	protected boolean[] glass() {
		return Patch.generate( 0, 4);
	}

	@Override
	public void addVisuals(Scene scene) {
		super.addVisuals(scene);
		CityLevel.addVisuals(this, scene);
	}
}
