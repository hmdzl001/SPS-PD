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
package com.hmdzl.spspd.levels.painters.challengeroom;

import com.hmdzl.spspd.actors.blobs.weather.WeatherOfRain;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSand;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSnow;
import com.hmdzl.spspd.actors.blobs.weather.WeatherOfSun;
import com.hmdzl.spspd.actors.mobs.Greatmoss;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.plants.NutPlant;
import com.hmdzl.spspd.plants.Seedpod;
import com.watabou.utils.Random;

public class PowerRoomPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);

		room.entrance().set(Room.Door.Type.REGULAR);

		switch (Random.Int(2)) {
			case 0:
				fill(level, room, 1, Terrain.HIGH_GRASS);
				fill(level, room, 2, Terrain.WATER);
				room.entrance().set(Room.Door.Type.REGULAR);
				level.plant(new Seedpod.Seed(), room.random());
				break;
			case 1:
				fill(level, room, 1, Terrain.WATER);
				fill(level, room, 2, Terrain.HIGH_GRASS);
				room.entrance().set(Room.Door.Type.REGULAR);
				level.plant(new NutPlant.Seed(), room.random());
				break;
		}

		Greatmoss statue = new Greatmoss();
		statue.pos = room.random();
		level.mobs.add(statue);

		if (Random.Int(5) == 0) {
			switch (Random.Int(4)) {
				case 0:
					WeatherOfRain rain = (WeatherOfRain) level.blobs.get(WeatherOfRain.class);
					if (rain == null) {
						rain = new WeatherOfRain();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							rain.seed(j + Floor.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfRain.class, rain);
					break;
				case 1:
					WeatherOfSand sand = (WeatherOfSand) level.blobs.get(WeatherOfSand.class);
					if (sand == null) {
						sand = new WeatherOfSand();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							sand.seed(j + Floor.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSand.class, sand);
					break;
				case 2:
					WeatherOfSnow snow = (WeatherOfSnow) level.blobs.get(WeatherOfSnow.class);
					if (snow == null) {
						snow = new WeatherOfSnow();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							snow.seed(j + Floor.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSnow.class, snow);
					break;
				case 3:
					WeatherOfSun sun = (WeatherOfSun) level.blobs.get(WeatherOfSun.class);
					if (sun == null) {
						sun = new WeatherOfSun();
					}
					for (int i = room.top + 1; i < room.bottom; i++) {
						for (int j = room.left + 1; j < room.right; j++) {
							sun.seed(j + Floor.getWidth() * i, 1);
						}
					}
					level.blobs.put(WeatherOfSun.class, sun);
					break;
			}
		}
	}
}
