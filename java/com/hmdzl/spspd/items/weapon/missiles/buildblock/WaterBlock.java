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
package com.hmdzl.spspd.items.weapon.missiles.buildblock;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class WaterBlock extends BuildBlock {

	{
		//name = "WaterBlock";
		image = ItemSpriteSheet.WATER_BLOCK;
	}

	public WaterBlock() {
		this(1);
	}

	public WaterBlock(int number) {
		super();
		quantity = number;
	}

	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if ((enemy == null) && !(Dungeon.depth.map[cell] == Terrain.WELL ||
				Dungeon.depth.map[cell] == Terrain.EMPTY_WELL ||
				Dungeon.depth.map[cell] == Terrain.ENTRANCE ||
				Dungeon.depth.map[cell] == Terrain.EXIT ||
				Dungeon.depth.map[cell] == Terrain.ALCHEMY ||
				Dungeon.depth.map[cell] == Terrain.IRON_MAKER)){
			Floor.set(cell, Terrain.WATER);
			GameScene.updateMap(cell);
		}
		else
			super.onThrow(cell);
	}

	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return 0;
	}
}