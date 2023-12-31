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
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Graph;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.List;

public class TenguDenLevel extends RegularLevel {

	{
		color1 = 0x48763c;
		color2 = 0x59994a;
		cleared=true;
	}

	private int stairs = 0;

	@Override
	public String tilesTex() {
		return Assets.TILES_CAVES;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}

	@Override
	protected boolean build() {

		initRooms();

		int distance;
		// if we ever need to try 20 or more times to find a room, better to
		// give up and try again.
		int retry = 0;

		// start with finding an entrance room (will also contain exit)
		// the room must be at least 4x4 and be nearer the top of the map(so
		// that it is less likely something connects to the top)
		do {
			if (retry++ > 20) {
				return false;
			}
			roomEntrance = Random.element(rooms);
		} while (roomEntrance.width() < 4 || roomEntrance.height() < 4
				|| roomEntrance.top == 0 || roomEntrance.top >= 12);

		roomEntrance.type = Type.ENTRANCE;
		roomExit = roomEntrance;

		// now find the rest of the rooms for this boss mini-maze
		Room curRoom = null;
		Room lastRoom = roomEntrance;
		// we make 4 rooms, last iteration is tieing the final room to the start
		for (int i = 0; i <= 4; i++) {
			retry = 0;
			// find a suitable room the first four times
			// suitable room should be empty, have a distance of 2 from the
			// current room, and not touch the entrance.
			if (i < 4) {
				do {
					if (retry++ > 20) {
						return false;
					}
					curRoom = Random.element(rooms);
					Graph.buildDistanceMap(rooms, curRoom);
					distance = lastRoom.distance();
				} while (curRoom.type != Type.NULL || distance != 2
						|| !curRoom.intersect(roomEntrance).isEmpty());

				curRoom.type = Type.STANDARD;

				// otherwise, we're on the last iteration.
			} else {
				// set the current room to the entrance, so we can build a
				// connection to it.
				curRoom = roomEntrance;
			}

			// now build a connection between the current room and the last one.
			Graph.buildDistanceMap(rooms, curRoom);
			List<Room> path = Graph.buildPath(rooms, lastRoom, curRoom);

			Graph.setPrice(path, lastRoom.distance);

			path = Graph.buildPath(rooms, lastRoom, curRoom);

			Room room = lastRoom;
			for (Room next : path) {
				room.connect(next);
				room = next;
			}

			if (i == 4) {

				// we must find a room for his royal highness!
				// look at rooms adjacent to the final found room (likely to be
				// furthest from start)
				ArrayList<Room> candidates = new ArrayList<Room>();
				for (Room r : lastRoom.neigbours) {
					if (r.type == Type.NULL && r.connected.size() == 0
							&& !r.neigbours.contains(roomEntrance)) {
						candidates.add(r);
					}
				}

				// if we have candidates, pick a room and put the king there
				if (candidates.size() > 0) {
					Room kingsRoom = Random.element(candidates);
					kingsRoom.connect(lastRoom);
					kingsRoom.type = Room.Type.TENGU_BOX;
					

					// unacceptable! make a new level...
				} else {
					return false;
				}
			}
			lastRoom = curRoom;
		}

		// the connection structure ensures that (most of the time) there is a
		// nice loop for the player to kite the
		// boss around. What's nice is that there is enough chaos such that the
		// loop is rarely straightforward
		// and boring.

		// fills our connection rooms in with tunnel
		for (Room r : rooms) {
			if (r.type == Type.NULL && r.connected.size() > 0) {
				r.type = Type.TUNNEL;
			}
		}

		// make sure nothing is connecting at the top of the entrance, this
		// would be bad.
		for (Room r : roomEntrance.neigbours) {
			if (r.bottom == roomEntrance.top && r.type != Type.NULL)
				return false;
		}

		paint();

		// TODO: not handling this through a painter is kinda iffy, separate
		// into a painter if you use it again.
		// sticks the exit in the room entrance.
		exit = roomEntrance.top * Floor.getWidth()
				+ (roomEntrance.left + roomEntrance.right) / 2;
		
		
			map[exit] = Terrain.WALL;
			map[entrance] = Terrain.PEDESTAL;
	   

		paintWater();
		paintGrass();

		return true;
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(0.5f, 5);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(0.40f, 4);
	}

	@Override
	protected boolean[] chasm() {
		return Patch.generate(0, 3);
	}

	@Override
	protected void decorate() {
		int start = roomExit.top * getWidth() + roomExit.left + 1;
		int end = start + roomExit.width() - 1;
		for (int i = start; i < end; i++) {
			if (i != exit) {
				map[i] = Terrain.WALL_DECO;
				map[i + getWidth()] = Terrain.WATER;
			} else {
				map[i + getWidth()] = Terrain.EMPTY;
			}
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		SewerLevel.addVisuals(this, scene);
	}

	@Override
	protected void createMobs() {
		if (Dungeon.tengudenkilled){
			return;
		}
		Mob mob = Bestiary.mob(Dungeon.dungeondepth);
		Room room;
		do {
			room = Random.element(rooms);
		} while (room.type != Type.STANDARD);
		mob.pos = room.random();
		mobs.add(mob);
	}

	@Override
	public Actor respawner() {
		return null;
	}

	@Override
	protected void createItems() {

	}
	
	@Override
	public int randomRespawnCell() {
		return -1;
	}
	public void seal() {
		if (entrance != 0) {

			//locked = true;

			set(entrance, Terrain.WATER_TILES);
			GameScene.updateMap(entrance);
			GameScene.ripple(entrance);

			stairs = entrance;
			entrance = 0;
		}
	}

	public void unseal() {
		if (stairs != 0) {

			locked = false;

			entrance = stairs;
			stairs = 0;

			set(entrance, Terrain.ENTRANCE);
			GameScene.updateMap(entrance);

		}
	}

	private static final String STAIRS = "stairs";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(STAIRS, stairs);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		stairs = bundle.getInt(STAIRS);
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
}
