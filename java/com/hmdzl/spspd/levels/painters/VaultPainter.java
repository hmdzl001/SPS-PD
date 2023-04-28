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
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap.Type;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.keys.GoldenKey;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Random;

public class VaultPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);
		fill( level, room, 2, Terrain.EMPTY );

		int cx = (room.left + room.right) / 2;
		int cy = (room.top + room.bottom) / 2;
		int c = cx + cy * Level.getWidth();

		switch (Random.Int(3)) {

		case 0: case 1: case 2:
			Item i1,i2,i3;
			do {
				i1 = prizeUncursed(level);
				i2 = prizeUncursed(level);
			} while (i1.getClass() == i2.getClass());
			level.drop(i1, c).type = Type.CRYSTAL_CHEST;
			level.drop(i2, c + Level.NEIGHBOURS8[Random.Int(8)]).type = Type.CRYSTAL_CHEST;
			level.addItemToSpawn(new GoldenKey(Dungeon.depth));
			
			do {
				i3 = prizeUncursed(level);
			} while ((i1.getClass() == i3.getClass()) && (i1.getClass() == i2.getClass()));			
			   int pos;
			do {
				pos = room.random();
			} while (level.map[pos] == Terrain.EMPTY_SP
					|| level.heaps.get(pos) != null);
			level.drop(i3, pos).type= Type.CRYSTAL_CHEST;
					
			break;

		/*
			level.drop(prizeUncursed(level), c);
			set(level, c, Terrain.PEDESTAL);
			break;*/
		}

		room.entrance().set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.depth));
	}

	private static Item prize(Level level) {
		return Generator.random(Random.oneOf(Generator.Category.WAND,
				Generator.Category.RING, Generator.Category.ARTIFACT));
	}
	
	
	private static Item prizeUncursed(Level level) {
				
	   Item item = Generator.random(Random.oneOf(Generator.Category.WAND, Generator.Category.RING, Generator.Category.ARTIFACT));
			
	   if (item != null && item.cursed && item.isUpgradable()) {	
			item.cursed = false;
			if(item.level<0){item.upgrade(-item.level);} //upgrade to even	
		}	
	   
	   return item;
	}
	
}
