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

public class Terrain {

	public static final int CHASM = 0;
	public static final int EMPTY = 1;
	public static final int GRASS = 2;
	public static final int EMPTY_WELL = 3;
	public static final int WALL = 4;
	public static final int DOOR = 5;
	public static final int OPEN_DOOR = 6;
	public static final int ENTRANCE = 7;
	public static final int EXIT = 8;
	public static final int EMBERS = 9;
	public static final int LOCKED_DOOR = 10;
	public static final int PEDESTAL = 11;
	public static final int WALL_DECO = 12;
	public static final int BARRICADE = 13;
	public static final int EMPTY_SP = 14;
	public static final int HIGH_GRASS = 15;
	
	public static final int SECRET_DOOR	    = 16;
	public static final int SECRET_TRAP     = 17;
	public static final int TRAP            = 18;
	public static final int INACTIVE_TRAP   = 19;	
	
	public static final int GROUND_A   = 20;
	public static final int FLOWER_POT = 21;
	public static final int WALL_GROUND = 22;
	public static final int WALL_LIVER = 23;
	public static final int BUY_WALL   = 24;
	
	public static final int EMPTY_DECO = 25;
	public static final int LOCKED_EXIT = 26;
	public static final int UNLOCKED_EXIT = 27;
	public static final int WALL_SP   = 28;
	public static final int SIGN = 29;

	public static final int GLASS_WALL = 31;

	public static final int OLD_HIGH_GRASS = 32;

	public static final int IRON_MAKER = 33;
	
	public static final int WELL = 34;
	public static final int STATUE = 35;
	public static final int STATUE_SP = 36;
	public static final int BROKEN_DOOR = 37;
	public static final int TENT = 38;

	public static final int DEW_BLESS= 39;

	public static final int BED = 40;
	public static final int BOOKSHELF = 41;
	public static final int ALCHEMY = 42;
	public static final int CHASM_FLOOR = 43;
	public static final int CHASM_FLOOR_SP = 44;
	public static final int CHASM_WALL = 45;
	public static final int CHASM_WATER = 46;
	public static final int SHRUB = 47;
	
	public static final int FLEECING_TRAP = 65;
	public static final int WOOL_RUG = 66;
	public static final int SOKOBAN_SHEEP = 67;
	public static final int CORNER_SOKOBAN_SHEEP = 68;
	public static final int SWITCH_SOKOBAN_SHEEP = 69;
	public static final int CHANGE_SHEEP_TRAP = 70;
	public static final int SOKOBAN_ITEM_REVEAL = 71;
	public static final int SOKOBAN_HEAP = 72;
	public static final int BLACK_SOKOBAN_SHEEP = 73;
	public static final int SOKOBAN_PORT_SWITCH = 75;
	public static final int PORT_WELL = 74;

	public static final int WATER_TILES = 48;
	public static final int WATER = 63;

	public static final int PASSABLE = 0x01;
	public static final int LOS_BLOCKING = 0x02;
	public static final int FLAMABLE = 0x04;
	public static final int SECRET = 0x08;
	public static final int SOLID = 0x10;
	public static final int AVOID = 0x20;
	public static final int LIQUID = 0x40;
	public static final int PIT = 0x80;
    public static final int WALLS = 0x200;

	public static final int UNSTITCHABLE = 0x100;
	
	

	public static final int[] flags = new int[256];
	static {
		flags[CHASM] = AVOID | PIT | UNSTITCHABLE;
		flags[EMPTY] = PASSABLE;
		flags[GRASS] = PASSABLE | FLAMABLE;
		flags[EMPTY_WELL] = PASSABLE;
		flags[WATER] = PASSABLE | LIQUID | UNSTITCHABLE ;
		flags[WALL] = LOS_BLOCKING | SOLID | UNSTITCHABLE | WALLS;
		flags[WALL_SP] = flags[WALL];
		flags[GLASS_WALL] = SOLID | UNSTITCHABLE ;
		flags[DOOR] = PASSABLE | LOS_BLOCKING | FLAMABLE | SOLID | UNSTITCHABLE | WALLS ;
		flags[OPEN_DOOR] = PASSABLE | FLAMABLE | UNSTITCHABLE;
		flags[ENTRANCE] = PASSABLE/* | SOLID */;
		flags[EXIT] = PASSABLE;
		flags[EMBERS] = PASSABLE;
		flags[LOCKED_DOOR] = LOS_BLOCKING | SOLID | UNSTITCHABLE | WALLS;
		flags[PEDESTAL] = PASSABLE | UNSTITCHABLE;
		flags[TENT] = PASSABLE ;
		flags[BED] = PASSABLE;
		flags[WALL_DECO] = flags[WALL];
		//flags[BUY_WALL] = flags[WALL];

		flags[BARRICADE] = FLAMABLE | SOLID;
		flags[EMPTY_SP] = flags[EMPTY] | UNSTITCHABLE;

		flags[GROUND_A] = flags[EMPTY] | UNSTITCHABLE;
		flags[FLOWER_POT] = flags[EMPTY] | UNSTITCHABLE;
		flags[WALL_LIVER] = flags[WALL];
		flags[WALL_GROUND] = flags[WALL];
		flags[BUY_WALL] = flags[WALL];
		
		

		flags[HIGH_GRASS] = PASSABLE | LOS_BLOCKING | FLAMABLE;
		
		flags[OLD_HIGH_GRASS] = PASSABLE | LOS_BLOCKING | FLAMABLE;
		
		flags[SECRET_DOOR]  = flags[WALL]  | SECRET	  | UNSTITCHABLE;
		flags[SECRET_TRAP]  = flags[EMPTY] | SECRET;
		flags[TRAP]         = AVOID;
		flags[INACTIVE_TRAP]= flags[EMPTY];

		flags[DEW_BLESS] = AVOID;
        flags[IRON_MAKER] = AVOID;

		flags[EMPTY_DECO] = flags[EMPTY];
		flags[LOCKED_EXIT] = SOLID;
		flags[UNLOCKED_EXIT] = PASSABLE;
		flags[SIGN] = PASSABLE | FLAMABLE;
		flags[WELL] = AVOID;
		flags[STATUE] = SOLID;
		flags[STATUE_SP] = flags[STATUE] | UNSTITCHABLE;
		flags[BROKEN_DOOR] = flags[STATUE] | UNSTITCHABLE;
		flags[BOOKSHELF] = flags[BARRICADE] | LOS_BLOCKING | UNSTITCHABLE  | WALLS;
		flags[ALCHEMY] = AVOID;
		flags[SHRUB] =  FLAMABLE | LOS_BLOCKING | SOLID ;

        flags[CHASM_WALL] = flags[CHASM];
		flags[CHASM_FLOOR] = flags[CHASM];
		flags[CHASM_FLOOR_SP] = flags[CHASM];
		flags[CHASM_WATER] = flags[CHASM];

		flags[FLEECING_TRAP] = AVOID ;
		flags[WOOL_RUG] = PASSABLE;
		flags[SOKOBAN_SHEEP] = PASSABLE;
		flags[CORNER_SOKOBAN_SHEEP] = PASSABLE;
		flags[SWITCH_SOKOBAN_SHEEP] = PASSABLE;
		flags[CHANGE_SHEEP_TRAP] = PASSABLE;
		flags[SOKOBAN_ITEM_REVEAL] = PASSABLE;
		flags[SOKOBAN_HEAP] = PASSABLE;
		flags[BLACK_SOKOBAN_SHEEP] = PASSABLE;
		flags[SOKOBAN_PORT_SWITCH] = PASSABLE;
		flags[PORT_WELL] = PASSABLE;

		
		for (int i = WATER_TILES; i < WATER_TILES + 16; i++) {
			flags[i] = flags[WATER];
		}
	}

    public static int discover(int terr) {
		switch (terr) {
		case SECRET_DOOR:
			return DOOR;
		case SECRET_TRAP:
			return TRAP;			
		default:
			return terr;
		}
	}
	
	
}
