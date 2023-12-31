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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.bombs.FishingBomb;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Random;

public class CaveChallengeLevel extends Floor {

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		cleared=true;

		viewDistance = 8;
	}

	private static final int ROOM_LEFT = getWidth() / 2 - 2;
	private static final int ROOM_RIGHT = getWidth() / 2 + 2;
	private static final int ROOM_TOP = HEIGHT / 2 - 2;
	private static final int ROOM_BOTTOM = HEIGHT / 2 + 2;

		@Override
	public String tilesTex() {
		return Assets.TILES_BEACH;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	

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
	   
	   for (int i = 0; i < getLength(); i++) {
					
			if (map[i]==Terrain.EMPTY && Random.Float()<.95){map[i] = Terrain.WATER;}
		}
	   
		boolean[] patch = Patch.generate(0.45f, 6);
		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.WATER && patch[i]) {
				map[i] = Terrain.EMPTY;
			}
		}

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
		}

	}

	//@Override
	//protected void createMobs() {
	//}

	@Override
	protected void createItems() {

		int pos = entrance + 1;
	    drop(new FishingBomb(5), pos).type = Heap.Type.CHEST;
	    //drop(new PotionOfLevitation(), pos);
	
	}
	


	//@Override
	//public int randomRespawnCell() {
	//	return -1;
	//}


	
	
	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Dark cold water.";
		case Terrain.HIGH_GRASS:
			return "Seaweed tangles. ";
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.ENTRANCE:
			return "A ramp leads up to the upper depth.";
		case Terrain.EXIT:
			return "A ramp leads down to the lower depth.";
		case Terrain.WALL_DECO:
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
		CavesLevel.addVisuals(this, scene);
	}
	@Override
	public int nMobs() {
		return 30;
	}
	
	@Override
	protected void createMobs() {
		int nMobs = nMobs();
		for (int i = 0; i < nMobs; i++) {
			Mob mob = Bestiary.mob(Dungeon.dungeondepth);
			do {
				mob.pos = randomRespawnCellFishMob();
			} while (mob.pos == -1);
			mobs.add(mob);
			Actor.occupyCell(mob);
		}
	}

	

	public int randomRespawnCellFishMob() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (map[cell]!=Terrain.WATER);
		return cell;
	}
	
	@Override
	public Actor respawner() {
		return new Actor() {
			@Override
			protected boolean act() {
				if (mobs.size() < nMobs()) {

					Mob mob = Bestiary.mutable(Dungeon.dungeondepth);
					mob.state = mob.WANDERING;
					mob.pos = randomRespawnCellFishMob();
					if (Dungeon.hero.isAlive() && mob.pos != -1) {
						GameScene.add(mob);
					}
				}
				spend(Dungeon.depth.feeling == Feeling.DARK
						|| Statistics.amuletObtained ? TIME_TO_RESPAWN / 2
						: TIME_TO_RESPAWN);
				return true;
			}
		};
	}
}
