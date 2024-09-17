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
package com.hmdzl.spspd.levels.painters;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Alchemy;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.ShoppingCart;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class CookingPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);
		fill(level, room, 2, Terrain.EMPTY_SP);

		Room.Door entrance = room.entrance();

		Point pot = room.center();
		
		set(level, pot.x, pot.y, Terrain.ALCHEMY);

		Alchemy alchemy = new Alchemy();
		alchemy.seed(pot.x + Floor.getWidth() * pot.y, 1);
		level.blobs.put(Alchemy.class, alchemy);

		if (!Dungeon.LimitedDrops.shopcart.dropped()){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new ShoppingCart(), pos);
			Dungeon.LimitedDrops.shopcart.drop();
		}

		int n = Random.IntRange(1, 2);
		int n2 = Random.IntRange(2, 3);
		for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(prize2(level), pos);
		}
		
		for (int i2 = 0; i2 < n2; i2++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(prize(level), pos);
		}

		entrance.set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.dungeondepth));
	}

	private static Item prize(Floor level) {

		Item prize = level.findPrizeItem(Potion.class);
		if (prize == null)
			prize = Generator.random(Generator.Category.FOOD);

		return prize;
	}
	
	private static Item prize2(Floor level) {

		Item prize = level.findPrizeItem(Potion.class);
		if (prize == null)
			prize = Generator.random(Generator.Category.HIGHFOOD);

		return prize;
	}
}
