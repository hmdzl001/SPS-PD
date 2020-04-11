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
import com.hmdzl.spspd.actors.blobs.Foliage;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.items.bags.ShoppingCart;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.Seedpod;
import com.watabou.utils.Random;

public class GardenPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.HIGH_GRASS);
		fill(level, room, 2, Terrain.WATER);

		room.entrance().set(Room.Door.Type.REGULAR);

		//if (Dungeon.isChallenged(Challenges.NO_FOOD)) {
			//if (Random.Int(2) == 0) {
				//level.plant(new Seedpod.Seed(), room.random());
			//}
		//} else {
			int bushes = Random.Int(3);
			if (bushes == 0) {
				level.plant(new Seedpod.Seed(), room.random());
			} else if (bushes == 1) {
				level.plant(new BlandfruitBush.Seed(), room.random());
			} else if (Random.Int(5) == 0) {
				level.plant(new Seedpod.Seed(), room.random());
				level.plant(new BlandfruitBush.Seed(), room.random());
			}
		//}
		
		
		if (!Dungeon.limitedDrops.shopcart.dropped()){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new ShoppingCart(), pos);
			Dungeon.limitedDrops.shopcart.drop();
		}
				
		if (Random.Int(100)==0 && (Dungeon.getMonth()==4 || Dungeon.getMonth()==5)){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new EasterEgg(), pos);
		}
		
		if (Dungeon.depth==32 && Random.Float() < 0.75f){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new Honeypot(), pos);	
			
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new Honeypot(), pos);
			
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new Honeypot(), pos);
		}
		
		if (Dungeon.depth==32 && Random.Float() < 0.75f){
			int pos;
			do {pos = room.random();}
			while (level.heaps.get(pos) != null);
			level.drop(new Ankh(), pos);		
		}

		Foliage light = (Foliage) level.blobs.get(Foliage.class);
		if (light == null) {
			light = new Foliage();
		}
		for (int i = room.top + 1; i < room.bottom; i++) {
			for (int j = room.left + 1; j < room.right; j++) {
				light.seed(j + Level.getWidth() * i, 1);
			}
		}
		level.blobs.put(Foliage.class, light);
	}
}
