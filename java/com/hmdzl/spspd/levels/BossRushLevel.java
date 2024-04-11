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
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BossRushLevel extends Floor {

	{
		color1 = 0x801500;
		color2 = 0xa68521;
		
		viewDistance = 8;
	}

	private static final int ROOM_LEFT = WIDTH / 2 - 1;
	private static final int ROOM_RIGHT = WIDTH / 2 + 1;
	private static final int ROOM_TOP = HEIGHT / 2 - 1;
	private static final int ROOM_BOTTOM = HEIGHT / 2 + 1;

	private int stairs = -1;

	@Override
	public String tilesTex() {
		return Assets.TILES_SEAL;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CITY;
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
	protected boolean build() {

		for (int i = 0; i < 5; i++) {

			int top = Random.IntRange(2, ROOM_TOP - 1);
			int bottom = Random.IntRange(ROOM_BOTTOM + 1, 22);
			Painter.fill(this, 2 + i * 4, top, 4, bottom - top + 1,
					Terrain.EMPTY);

			if (i == 2) {
				exit = (i * 4 + 3) + (top - 1) * getWidth();
			}

			for (int j = 0; j < 4; j++) {
				if (Random.Int(2) == 0) {
					int y = Random.IntRange(top + 1, bottom - 1);
					map[i * 4 + j + y * getWidth()] = Terrain.WALL_DECO;
				}
			}
		}

		map[exit] = Terrain.LOCKED_EXIT;

		Painter.fill(this, ROOM_LEFT - 1, ROOM_TOP - 1, ROOM_RIGHT - ROOM_LEFT
				+ 3, ROOM_BOTTOM - ROOM_TOP + 3, Terrain.HIGH_GRASS);
		
		Painter.fill(this, ROOM_LEFT, ROOM_TOP, ROOM_RIGHT - ROOM_LEFT + 1,
				ROOM_BOTTOM - ROOM_TOP + 1, Terrain.EMPTY);

		entrance = Random.Int(ROOM_LEFT + 1, ROOM_RIGHT - 1)
				+ Random.Int(ROOM_TOP + 1, ROOM_BOTTOM - 1) * getWidth();
		map[entrance] = Terrain.PEDESTAL;

		boolean[] patch = Patch.generate(0.45f, 6);
		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && patch[i]) {
				map[i] = Terrain.WATER;
			}
		}

		return true;
	}

	@Override
	protected void decorate() {

		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && Random.Int(10) == 0) {
				map[i] = Terrain.EMPTY_DECO;
				if (map[i]==Terrain.ENTRANCE){map[i] = Terrain.PEDESTAL;}
			}
		}			
		
	}

	@Override
	protected void createMobs() {
		Mob mob = Bestiary.mob(Dungeon.dungeondepth);
		mob.pos =  randomRespawnCellMob();
		mobs.add(mob);
		Actor.occupyCell(mob);
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

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return Messages.get(BossRushLevel.class, "water_name");
		case Terrain.GRASS:
			return Messages.get(BossRushLevel.class, "grass_name");
		case Terrain.HIGH_GRASS:
			return Messages.get(BossRushLevel.class, "high_grass_name");
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return Messages.get(BossRushLevel.class, "statue_sp_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return Messages.get(BossRushLevel.class, "water_desc");
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return Messages.get(BossRushLevel.class, "statue_sp_desc");
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		HallsLevel.addVisuals(this, scene);
	}
}
