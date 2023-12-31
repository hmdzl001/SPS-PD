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
import com.hmdzl.spspd.items.PuddingCup;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Arrays;

public class LastLevel extends Floor {

	private static final int SIZE = 30;

	{
		color1 = 0x801500;
		color2 = 0xa68521;

		viewDistance = 8;
	}

	private int pedestal;

	@Override
	public String tilesTex() {
		return Assets.TILES_HALLS;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}

	@Override
	public void create() {
		super.create();
		for (int i = 0; i < getLength(); i++) {
			int flags = Terrain.flags[map[i]];
			if ((flags & Terrain.PIT) != 0) {
				passable[i] = avoid[i] = false;
				solid[i] = true;
			}
		}
	}

	@Override
	protected boolean build() {

		Arrays.fill(map, Terrain.CHASM);

		Painter.fill(this, 7, 31, 19, 1, Terrain.WALL);
		Painter.fill(this, 15, 10, 3, 21, Terrain.EMPTY);
		Painter.fill(this, 13, 30, 7, 1, Terrain.EMPTY);
		Painter.fill(this, 14, 29, 5, 1, Terrain.EMPTY);

		Painter.fill(this, 14, 9, 5, 7, Terrain.EMPTY);
		Painter.fill(this, 13, 10, 7, 5, Terrain.EMPTY);

		// Painter.fill( this, 2, 2, SIZE-2, SIZE-2, Terrain.EMPTY );
		// Painter.fill( this, SIZE/2, SIZE/2, 3, 3, Terrain.EMPTY_SP );

		entrance = SIZE * getWidth() + SIZE / 2 + 1;
		map[entrance] = Terrain.ENTRANCE;

		pedestal = (SIZE / 2 + 1) * (getWidth() + 1) - 4 * getWidth();
		map[pedestal] = Terrain.PEDESTAL;
		map[pedestal - 1 - getWidth()] = map[pedestal + 1 - getWidth()] = map[pedestal
				- 1 + getWidth()] = map[pedestal + 1 + getWidth()] = Terrain.STATUE_SP;

		exit = pedestal;

		int pos = pedestal;

		map[pos - getWidth()] = map[pos - 1] = map[pos + 1] = map[pos - 2] = map[pos + 2] = Terrain.WATER;
		pos += getWidth();
		map[pos] = map[pos - 2] = map[pos + 2] = map[pos - 3] = map[pos + 3] = Terrain.WATER;
		pos += getWidth();
		map[pos - 3] = map[pos - 2] = map[pos - 1] = map[pos] = map[pos + 1] = map[pos + 2] = map[pos + 3] = Terrain.WATER;
		pos += getWidth();
		map[pos - 2] = map[pos + 2] = Terrain.WATER;

		feeling = Feeling.NONE;
		viewDistance = 8;

		return true;
	}

	@Override
	protected void decorate() {
		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && Random.Int(10) == 0) {
				map[i] = Terrain.EMPTY_DECO;
			}
		}
	}

	@Override
	protected void createMobs() {
	}

	@Override
	protected void createItems() {
		drop(new PuddingCup(), pedestal);
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_name");
			case Terrain.GRASS:
				return Messages.get(HallsLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(HallsLevel.class, "high_grass_name");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(HallsLevel.class, "bookshelf_desc");
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		super.addVisuals(scene);
		HallsLevel.addVisuals(this, scene);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		for (int i = 0; i < getLength(); i++) {
			int flags = Terrain.flags[map[i]];
			if ((flags & Terrain.PIT) != 0) {
				passable[i] = avoid[i] = false;
				solid[i] = true;
			}
		}
	}
}
