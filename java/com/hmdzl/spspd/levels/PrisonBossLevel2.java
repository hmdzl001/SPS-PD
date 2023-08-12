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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.levels.traps.Trap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Graph;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

import java.util.List;

public class PrisonBossLevel2 extends RegularLevel {

	{
		color1 = 0x6a723d;
		color2 = 0x88924c;
	}

	private Room anteroom;
	private int arenaDoor;

	private boolean enteredArena = false;
	private boolean keyDropped = false;

	@Override
	public String tilesTex() {
		return Assets.TILES_PRISON;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	private static final String ARENA = "arena";
	private static final String DOOR = "door";
	private static final String ENTERED = "entered";
	private static final String DROPPED = "droppped";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ARENA, roomExit);
		bundle.put(DOOR, arenaDoor);
		bundle.put(ENTERED, enteredArena);
		bundle.put(DROPPED, keyDropped);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		roomExit = (Room) bundle.get(ARENA);
		arenaDoor = bundle.getInt(DOOR);
		enteredArena = bundle.getBoolean(ENTERED);
		keyDropped = bundle.getBoolean(DROPPED);
	}

	@Override
	protected boolean build() {

		initRooms();

		int distance;
		int retry = 0;

		do {

			if (retry++ > 10) {
				return false;
			}

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
			} while (roomExit == roomEntrance || roomExit.width() < 7
					|| roomExit.height() < 7 || roomExit.top == 0);

			Graph.buildDistanceMap(rooms, roomExit);
			distance = Graph.buildPath(rooms, roomEntrance, roomExit).size();

		} while (distance < 3);

		roomEntrance.type = Type.ENTRANCE;
		roomExit.type = Type.BOSS_EXIT;

		List<Room> path = Graph.buildPath(rooms, roomEntrance, roomExit);
		Graph.setPrice(path, roomEntrance.distance);

		Graph.buildDistanceMap(rooms, roomExit);
		path = Graph.buildPath(rooms, roomEntrance, roomExit);

		anteroom = path.get(path.size() - 2);
		anteroom.type = Type.STANDARD;

		Room room = roomEntrance;
		for (Room next : path) {
			room.connect(next);
			room = next;
		}

		for (Room r : rooms) {
			if (r.type == Type.NULL && r.connected.size() > 0) {
				r.type = Type.PASSAGE;
			}
		}

		paint();

		Room r = (Room) roomExit.connected.keySet().toArray()[0];
		if (roomExit.connected.get(r).y == roomExit.top) {
			return false;
		}

		paintWater();
		paintGrass();

		placeTraps();

		return true;
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(0.45f, 5);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(0.30f, 4);
	}

	@Override
	protected void paintDoors(Room r) {

		for (Room n : r.connected.keySet()) {

			if (r.type == Type.NULL) {
				continue;
			}

			Point door = r.connected.get(n);

			if (r.type == Room.Type.PASSAGE && n.type == Room.Type.PASSAGE) {

				Painter.set(this, door, Terrain.EMPTY);

			} else {

				Painter.set(this, door, Terrain.DOOR);

			}

		}
	}

	@Override
	protected void placeTraps() {

		int nTraps = nTraps();

		for (int i = 0; i < nTraps; i++) {

			int trapPos = Random.Int(getLength());

			if (map[trapPos] == Terrain.EMPTY) {
				map[trapPos] = Terrain.INACTIVE_TRAP;
				Trap t = new SpearTrap().reveal();
				t.active = false;
				setTrap(t, trapPos);		
			}
		}
	}

	@Override
	protected void decorate() {

		for (int i = getWidth() + 1; i < getLength() - getWidth() - 1; i++) {
			if (map[i] == Terrain.EMPTY) {

				float c = 0.15f;
				if (map[i + 1] == Terrain.WALL
						&& map[i + getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i - 1] == Terrain.WALL
						&& map[i + getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i + 1] == Terrain.WALL
						&& map[i - getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i - 1] == Terrain.WALL
						&& map[i - getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}

				if (Random.Float() < c) {
					map[i] = Terrain.EMPTY_DECO;
				}
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			if (map[i] == Terrain.WALL
					&& (map[i + getWidth()] == Terrain.EMPTY || map[i + getWidth()] == Terrain.EMPTY_SP)
					&& Random.Int(4) == 0) {

				map[i] = Terrain.WALL_DECO;
			}
		}

		for (int i = getWidth(); i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.WALL
					&& map[i - getWidth()] == Terrain.WALL
					&& (map[i + getWidth()] == Terrain.EMPTY || map[i + getWidth()] == Terrain.EMPTY_SP)
					&& Random.Int(2) == 0) {

				map[i] = Terrain.WALL_DECO;
			}
		}

		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance) {
				map[pos] = Terrain.SIGN;
				break;
			}
		}

		Point door = roomExit.entrance();
		arenaDoor = door.x + door.y * getWidth();
		Painter.set(this, arenaDoor, Terrain.LOCKED_DOOR);

		Painter.fill(this, roomExit.left + 2, roomExit.top + 2,
				roomExit.width() - 3, roomExit.height() - 3,
				Terrain.INACTIVE_TRAP);
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

		int keyPos = anteroom.random();
		while (!passable[keyPos]) {
			keyPos = anteroom.random();
		}
		drop(new IronKey(Dungeon.depth), keyPos).type = Heap.Type.CHEST;

	}

	@Override
	public void press(int cell, Char ch) {

		super.press(cell, ch);

		if (ch == Dungeon.hero && !enteredArena && roomExit.inside(cell)) {

			enteredArena = true;
			locked = true;

			int pos;
			do {
				pos = roomExit.random();
			} while (pos == cell || Actor.findChar(pos) != null);

			Mob boss = Bestiary.mob(Dungeon.depth);
			boss.state = boss.HUNTING;
			boss.pos = pos;
			GameScene.add(boss);
			boss.notice();

			mobPress(boss);

			set(arenaDoor, Terrain.LOCKED_DOOR);
			GameScene.updateMap(arenaDoor);
			Dungeon.observe();

		}
	}

	@Override
	public Heap drop(Item item, int cell) {

		if (!keyDropped && item instanceof SkeletonKey) {

			keyDropped = true;
			locked = false;

			set(arenaDoor, Terrain.DOOR);
			GameScene.updateMap(arenaDoor);
			Dungeon.observe();
		}

		return super.drop(item, cell);
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		PrisonLevel.addVisuals(this, scene);
	}
}
