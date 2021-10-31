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

import java.util.Arrays;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.watabou.utils.Random;

public class DeadEndLevel extends Level {

	private static final int SIZE = 5;

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_CAVES;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}

	@Override
	protected boolean build() {

		Arrays.fill(map, Terrain.WALL);

		for (int i = 2; i < SIZE; i++) {
			for (int j = 2; j < SIZE; j++) {
				map[i * getWidth() + j] = Terrain.EMPTY;
			}
		}

		for (int i = 1; i <= SIZE; i++) {
			map[getWidth() + i] = map[getWidth() * SIZE + i] = map[getWidth() * i + 1] = map[getWidth()
					* i + SIZE] = Terrain.WATER;
		}

		entrance = SIZE * getWidth() + SIZE / 2 + 1;
		map[entrance] = Terrain.ENTRANCE;

		map[(SIZE / 2 + 1) * (getWidth() + 1)] = Terrain.SIGN;

		exit = 0;

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
	}
	
	/*@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Suspiciously colored water";
		case Terrain.HIGH_GRASS:
			return "High blooming flowers";
		default:
			return super.tileName(tile);
		}
	}*/

	@Override
	protected void createMobs() {
	}

	@Override
	protected void createItems() {
	}
	
	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.GRASS:
			return Messages.get(CavesLevel.class, "grass_name");
		case Terrain.HIGH_GRASS:
			return Messages.get(CavesLevel.class, "high_grass_name");
		case Terrain.WATER:
			return Messages.get(CavesLevel.class, "water_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.ENTRANCE:
			return Messages.get(CavesLevel.class, "entrance_desc");
		case Terrain.EXIT:
			return Messages.get(CavesLevel.class, "exit_desc");
		case Terrain.HIGH_GRASS:
			return Messages.get(CavesLevel.class, "high_grass_desc");
		case Terrain.WALL_DECO:
			return Messages.get(CavesLevel.class, "wall_deco_desc");
		case Terrain.BOOKSHELF:
			return Messages.get(CavesLevel.class, "bookshelf_desc");
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public int randomRespawnCell() {
		return entrance - getWidth();
	}

}
