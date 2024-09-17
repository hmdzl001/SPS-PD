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
import com.hmdzl.spspd.items.TriforceOfPower;
import com.hmdzl.spspd.items.Vialupdater;
import com.hmdzl.spspd.levels.Room.Type;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TrianglePLevel extends RegularLevel {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
		cleared=true;
	}

	//protected static final int REGROW_TIMER = 4;
	
	@Override
	public String tilesTex() {
		return Assets.TILES_FOREST;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(feeling == Feeling.WATER ? 0.65f : 0.45f, 4);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(feeling == Feeling.GRASS ? 0.60f : 0.40f, 3);
	}

	@Override
	protected boolean[] chasm() {
		return Patch.generate(0, 3);
	}

	protected boolean[] glass() {
		return Patch.generate( 0, 4);
	}

	@Override
	protected void createItems() {
		addItemToSpawn(new Vialupdater());
		super.createItems();

	}
	
	@Override
	protected boolean assignRoomType() {

		powerspecial = new ArrayList<Room.Type>(Arrays.asList(Type.POWER_ROOM));


		for (Room r : rooms) {
			if (r.type == Type.NULL && r.connected.size() == 1) {

				if (powerspecial.size() > 0 && r.width() > 3 && r.height() > 3) {
					int n = powerspecial.size();
					r.type = powerspecial.get(Math.min(Random.Int(n), Random.Int(n)));
					Room.useType(r.type);
				} else if (Random.Int(2) == 0) {

					HashSet<Room> neigbours = new HashSet<Room>();
					for (Room n : r.neigbours) {
						if (!r.connected.containsKey(n)
								&& !Room.SPECIALS.contains(n.type)
								) {

							neigbours.add(n);
						}
					}
					if (neigbours.size() > 1) {
						r.connect(Random.element(neigbours));
					}
				}
			}
		}


		int count = 0;
		for (Room r : rooms) {
			if (r.type == Type.NULL) {
				int connections = r.connected.size();
				if (connections == 0) {

				} else if (Random.Int(connections * connections) == 0) {
					r.type = Type.STANDARD;
					count++;
				} else {
					r.type = Type.TUNNEL;
				}
			}
		}

		while (count < 4) {
			Room r = randomRoom(Type.TUNNEL, 1);
			if (r != null) {
				r.type = Type.STANDARD;
				count++;
			}
		}

		for (Room r : rooms) {
			if (r.type == Type.TUNNEL) {
				r.type = Type.PASSAGE;
			}
		}
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

	    //while (true) {
		//	int pos = roomEntrance.random();
		//	if (pos != entrance) {
		//		break;
		//	}
		//}
		
				
   int length = Floor.getLength();
		
		for (int i = 0; i < length; i++) {
			
					
			if (map[i]==Terrain.ENTRANCE){map[i] = Terrain.PEDESTAL;}
			if (map[i]==Terrain.EXIT){map[i] = Terrain.PEDESTAL;
			if (!Dungeon.triforceofpower){drop(new TriforceOfPower(), i);}}
			if (map[i]==Terrain.CHASM){map[i] = Terrain.EMPTY_DECO;}
			

		}
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Suspiciously colored water";
		case Terrain.HIGH_GRASS:
			return "High blooming flowers";
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
			return "Several tiles are missing here.";
		case Terrain.EMPTY_SP:
			return "Thick carpet covers the floor.";
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return "The statue depicts some dwarf standing in a heroic stance.";
		case Terrain.BOOKSHELF:
			return "The rows of books on different disciplines fill the bookshelf.";
		default:
			return super.tileDesc(tile);
		}
	}


}