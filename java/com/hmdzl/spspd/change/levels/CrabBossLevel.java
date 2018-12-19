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
package com.hmdzl.spspd.change.levels;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Bones;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.mobs.CrabKing;
import com.hmdzl.spspd.change.actors.mobs.HermitCrab;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.Shell;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.levels.painters.Painter;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class CrabBossLevel extends Level {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
		cleared=true;
	}

	private static final int TOP = 2;
	private static final int HALL_WIDTH = 13;
	private static final int HALL_HEIGHT = 15;
	private static final int CHAMBER_HEIGHT = 3;

	private static final int LEFT = (getWidth() - HALL_WIDTH) / 2;
	private static final int CENTER = LEFT + HALL_WIDTH / 2;

	private int arenaDoor;
	private boolean enteredArena = false;
	private boolean keyDropped = false;

	@Override
	public String tilesTex() {
		return Assets.TILES_BEACH;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	private static final String DOOR = "door";
	private static final String ENTERED = "entered";
	private static final String DROPPED = "droppped";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DOOR, arenaDoor);
		bundle.put(ENTERED, enteredArena);
		bundle.put(DROPPED, keyDropped);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		arenaDoor = bundle.getInt(DOOR);
		enteredArena = bundle.getBoolean(ENTERED);
		keyDropped = bundle.getBoolean(DROPPED);
	}

	@Override
	protected boolean build() {

		Painter.fill(this, LEFT, TOP, HALL_WIDTH, HALL_HEIGHT, Terrain.WATER);
		Painter.fill(this, CENTER, TOP, 1, HALL_HEIGHT, Terrain.WATER);

		int y = TOP + 1;
		while (y < TOP + HALL_HEIGHT) {
			map[y * getWidth() + CENTER - 2] = Terrain.STATUE;
			map[y * getWidth() + CENTER + 2] = Terrain.STATUE;
			y += 2;
		}
		
		exit = (TOP - 1) * getWidth() + CENTER;
		map[exit] = Terrain.LOCKED_EXIT;

		arenaDoor = (TOP + HALL_HEIGHT) * getWidth() + CENTER;
		map[arenaDoor] = Terrain.DOOR;

		Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, HALL_WIDTH,
				CHAMBER_HEIGHT, Terrain.EMPTY);
		Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT,
				Terrain.WATER);
		Painter.fill(this, LEFT + HALL_WIDTH - 1, TOP + HALL_HEIGHT + 1, 1,
				CHAMBER_HEIGHT, Terrain.WATER);

		entrance = (TOP + HALL_HEIGHT + 2 + Random.Int(CHAMBER_HEIGHT - 1))
				* getWidth() + LEFT + (/* 1 + */Random.Int(HALL_WIDTH - 2));
		map[entrance] = Terrain.PEDESTAL;

		map[exit] = Terrain.WALL;

		return true;
	}

	@Override
	protected void decorate() {

		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && Random.Int(10) == 0) {
				map[i] = Terrain.EMPTY_DECO;
			} else if (map[i] == Terrain.WALL && Random.Int(8) == 0) {
				map[i] = Terrain.WALL_DECO;
			}
		}

		//int sign = arenaDoor + WIDTH + 1;
		//map[sign] = Terrain.SIGN;
	}

	public static int pedestal(boolean left) {
		if (left) {
			return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER - 2;
		} else {
			return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER + 2;
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
		Item item = Bones.get();
		if (item != null) {
			int pos;
			do {
				pos = Random.IntRange(LEFT + 1, LEFT + HALL_WIDTH - 2)
						+ Random.IntRange(TOP + HALL_HEIGHT + 1, TOP
								+ HALL_HEIGHT + CHAMBER_HEIGHT) * getWidth();
			} while (pos == entrance || map[pos] == Terrain.SIGN);
			drop(item, pos).type = Heap.Type.REMAINS;
		}
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public void press(int cell, Char hero) {

		super.press(cell, hero);

		if (!enteredArena && outsideEntraceRoom(cell) && hero == Dungeon.hero) {

			enteredArena = true;
			//locked = true;

			Mob boss = new CrabKing();
			Mob shell = new Shell();
			Mob crab1 = new HermitCrab();
			Mob crab2 = new HermitCrab();
			Mob crab3 = new HermitCrab();
			Mob crab4 = new HermitCrab();
			boss.state = boss.HUNTING;
			crab1.state = crab1.HUNTING;
			crab2.state = crab2.HUNTING;
			crab3.state = crab3.HUNTING;
			crab4.state = crab4.HUNTING;
			int count = 0;
			do {
				boss.pos = Random.Int(getLength());
				shell.pos = (TOP + 1) * getWidth() + CENTER;
				crab1.pos = (TOP + 1) * getWidth() + CENTER+1;
				crab2.pos = (TOP + 1) * getWidth() + CENTER-1;
				crab3.pos = (TOP + 2) * getWidth() + CENTER;
				crab4.pos = (TOP + 0) * getWidth() + CENTER;
			} while (!passable[boss.pos] 
					|| !outsideEntraceRoom(boss.pos)
					|| (Dungeon.visible[boss.pos] && count++ < 20));
			
			GameScene.add(boss);
			GameScene.add(shell);
			
			GameScene.add(crab1);
			GameScene.add(crab2);
			GameScene.add(crab3);
			GameScene.add(crab4);

			if (Dungeon.visible[boss.pos]) {
				boss.notice();
				boss.sprite.alpha(0);
				boss.sprite.parent.add(new AlphaTweener(boss.sprite, 1, 0.1f));
			}

			//set(arenaDoor, Terrain.WALL);
			//GameScene.updateMap(arenaDoor);
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

	private boolean outsideEntraceRoom(int cell) {
		return cell / getWidth() < arenaDoor / getWidth();
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Crystal clear pools.";
		case Terrain.HIGH_GRASS:
			return "Seaweed Tangles";
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.EMPTY_DECO:
			return "Small crabs and shell fish litter the sandy floor.";
		case Terrain.EMPTY_SP:
			return "Thick carpet covers the floor.";
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return "A large sea shell is propped up in the sand.";
		case Terrain.BOOKSHELF:
			return "Mostly beach reads.";
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		CityLevel.addVisuals(this, scene);
	}
}
