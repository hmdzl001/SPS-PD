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
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.mobs.Bestiary;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.bombs.HolyHandGrenade;
import com.hmdzl.spspd.change.levels.painters.Painter;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Random;

public class BattleLevel extends Level {

	{
		color1 = 0x6a723d;
		color2 = 0x88924c;

		viewDistance = 8;
	}

	private static final int ROOM_LEFT = getWidth() / 2 - 2;
	private static final int ROOM_RIGHT = getWidth() / 2 + 2;
	private static final int ROOM_TOP = HEIGHT / 2 - 2;
	private static final int ROOM_BOTTOM = HEIGHT / 2 + 2;

		@Override
	public String tilesTex() {
		return Assets.TILES_PRISON;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	protected static final int REGROW_TIMER = 4;
	protected static final int TIME_TO_RESPAWN = 30;

	@Override
	protected boolean build() {

		int topMost = Integer.MAX_VALUE;

		for (int i = 0; i < 8; i++) {
			int left, right, top, bottom;
			if (Random.Int(2) == 0) {
				left = Random.Int(1, ROOM_LEFT - 3);
				right = ROOM_RIGHT + 3;
			} else {
				left = ROOM_LEFT - 3;
				right = Random.Int(ROOM_RIGHT + 3, getWidth() - 1);
			}
			if (Random.Int(2) == 0) {
				top = Random.Int(2, ROOM_TOP - 3);
				bottom = ROOM_BOTTOM + 3;
			} else {
				top = ROOM_LEFT - 3;
				bottom = Random.Int(ROOM_TOP + 3, HEIGHT - 1);
			}

			Painter.fill(this, left, top, right - left + 1, bottom - top + 1,
					Terrain.EMPTY);

			if (top < topMost) {
				topMost = top;
				exit = Random.Int(left, right) + (top - 1) * getWidth();
			}
		}

		map[exit] = Terrain.WALL;

		
		
		Painter.fill(this, ROOM_LEFT, ROOM_TOP + 1, ROOM_RIGHT - ROOM_LEFT + 1,
				ROOM_BOTTOM - ROOM_TOP, Terrain.EMPTY);

		
				entrance = Random.Int(ROOM_LEFT + 1, ROOM_RIGHT - 1)
				+ Random.Int(ROOM_TOP + 1, ROOM_BOTTOM - 1) * getWidth();
		map[entrance] = Terrain.EMPTY;

		return true;
	}

	@Override
	protected void decorate() {

		for (int i = getWidth() + 1; i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.EMPTY) {
				int n = 0;
				if (map[i + 1] == Terrain.WALL) {
					n++;
				}
				if (map[i - 1] == Terrain.WALL) {
					n++;
				}
				if (map[i + getWidth()] == Terrain.WALL) {
					n++;
				}
				if (map[i - getWidth()] == Terrain.WALL) {
					n++;
				}
				if (Random.Int(8) <= n) {
					map[i] = Terrain.EMPTY_DECO;
				}
			}
		}

		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.WALL && Random.Int(8) == 0) {
				map[i] = Terrain.WALL_DECO;
			}
			if (map[i]==Terrain.ENTRANCE){map[i] = Terrain.EMPTY;}			
			if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.20){map[i] = Terrain.HIGH_GRASS;}
			if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.25){map[i] = Terrain.GRASS;}
			if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Float()<.30){map[i] = Terrain.SHRUB;}
		}

	}

	//@Override
	//protected void createMobs() {
	//}

	//@Override
	//public Actor respawner() {
	//	return null;
	//}

	@Override
	protected void createItems() {

		int pos = randomDestination();
	    drop(new HolyHandGrenade(50), pos).type = Heap.Type.CHEST;
	
	}
	


	//@Override
	//public int randomRespawnCell() {
	//	return -1;
	//}


	
	
	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return Messages.get(PrisonLevel.class, "water_name");
		default:
			return super.tileName(tile);
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
			return super.tileDesc(tile);
		}
	}


	@Override
	public void addVisuals(Scene scene) {
		CavesLevel.addVisuals(this, scene);
	}
	@Override
	public int nMobs() {
		return 16;
	}
	
	@Override
	protected void createMobs() {
		int nMobs = nMobs();
		for (int i = 0; i < nMobs; i++) {
			Mob mob = Bestiary.mob(Dungeon.depth);
			do {
				mob.pos = randomRespawnCellMob();
			} while (mob.pos == -1);
			mobs.add(mob);
			Actor.occupyCell(mob);
		}
	}

	@Override
	public Actor respawner() {
		return new Actor() {
			@Override
			protected boolean act() {
				if (mobs.size() < nMobs()) {

					Mob mob = Bestiary.mutable(Dungeon.depth);
					mob.state = mob.WANDERING;
					mob.pos = randomRespawnCellMob();
					if (Dungeon.hero.isAlive() && mob.pos != -1) {
						GameScene.add(mob);
					}
				}
				spend(Dungeon.level.feeling == Feeling.DARK
						|| Statistics.amuletObtained ? TIME_TO_RESPAWN / 2
						: TIME_TO_RESPAWN);
				return true;
			}
		};
	}
}
