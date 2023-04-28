/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the LicensW, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be usefuW,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.levels;

public class LearnRoomLayouts {

	private static final int W = Terrain.WALL;

	private static final int W0 = Terrain.WALL_DECO;
	private static final int W1 = Terrain.WALL_GROUND;
	private static final int W2 = Terrain.WALL_LIVER;
	private static final int W3 = Terrain.BUY_WALL;
	private static final int W4 = Terrain.WALL_SP;

	private static final int S = Terrain.STATUE;
	private static final int S1 = Terrain.STATUE_SP;
	private static final int S2 = Terrain.BROKEN_DOOR;

	private static final int O = Terrain.SIGN;

	private static final int B = Terrain.BED;

	private static final int D = Terrain.DOOR;
	private static final int D1 = Terrain.LOCKED_DOOR;
	private static final int D2 = Terrain.SECRET_DOOR;

	private static final int H = Terrain.WELL;
	private static final int H1 = Terrain.EMPTY_WELL;
	private static final int H2 = Terrain.FLOWER_POT;

	private static final int T = Terrain.TENT;

	private static final int E = Terrain.EMPTY;
	private static final int E1 = Terrain.EMPTY_SP;
	private static final int E2 = Terrain.GROUND_A;
	private static final int E3 = Terrain.EMPTY_DECO;

	private static final int I = Terrain.IRON_MAKER;

	//private static final int P = Terrain.PEDESTAL;

	private static final int J = Terrain.WATER;
	
	private static final int A = Terrain.GLASS_WALL;

	private static final int Y = Terrain.ALCHEMY;

	private static final int G = Terrain.GRASS;
	private static final int G1 = Terrain.HIGH_GRASS;
	private static final int G2 = Terrain.OLD_HIGH_GRASS;

	private static final int C = Terrain.BARRICADE;
	private static final int C1 = Terrain.BOOKSHELF;
	private static final int C2 = Terrain.SHRUB;
	
	private static final int P = Terrain.SECRET_TRAP;
	//32X32


	public static final int[] LEARN_ROOM = {
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, E, E, E, W, E, E, E, E, E, E, W, E, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, E, T, E, D, E, E, E, E, E, E,D1, E, E, E, E, E, E, W, W, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, W, W, W, W, W,
			W, W, E, E, E, W, E, E,E2, E, E, E, W, E, E, E, E, E, E,E1, D, E, E,E2, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, J, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, E, E, E, E, E, E, W, E, E, E, E, E,E2, W, W, E, E, E, E, E,D2, E, E, E, E, E, E, W, E, E, E, D, P, J,E2, E, E, W, W, W, W, W,
			W, W, W, W, W, W, E, E, E, E, E, E, W, E, E, E, E, E, E, W, W, E, E, E, E, E, W, W, W, W, W, W, E, E, E, W, W, W, W, J, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, E, E, E, E, E, E, W, E, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,D1, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, A, A, A, A, A, A, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, A, J, J, J, J, J, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, E, E, E, W, E, A, J, G, G, G, J, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E,E3, E, E, E, W, E, A, J, G,E2, G, J, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E,E3, E, D, W, E, A, J, G, G, G, J, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, E, E, E, E, E, E, D, E,E3,E3,E3,E3,E3, E, W, E, A, J, J, J, J, J, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, E, E, E, E,E3, E, D, W, E, A, A, A, D, A, A, A, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, E, E, E,E3, E, E, E, W, E, E, E, E, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, W, W,G2, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, E, E, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, D, W, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, S, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, E,G1, E, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, G, G, G, E, E, E, W, W, W, W, W,
			W, W, W, W, W, W, W, D, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,E1, W, E, E, E, E, E, E, E, E, E, E, E,C1, G, P, G, E, E,E2, W, W, W, W, W,
			W, W, W, W, W, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, W, W, W, W, W, W, W, W, W, W, W, G, G, G, E, E, E, W, W, W, W, W,
			W, W, W, W, W, E, E, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, E, E, W, W, W, W, W, W, W, W, W, W, W, E, S, E, E, E, E, W, W, W, W, W,
			W, W, W, W, W, E, E,E2, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E,E2, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, E, E, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, E, W, W, W,
			W, W, W, W, E, W, E, W, E, W, W, W, E, W, E, E, W, W, E, E, E, E, E, W, E, W, W, W, W, E, W, W, W, E, W, W, W, E, W, W, W, E, W, E, W, E, W, W,
			W, W, W, W, E, W, E, W, E, E, W, E, E, W, E, W, E, W, W, W, W, E, W, W, E, W, W, W, E, W, E, W, E, W, E, W, E, E, W, W, E, W, W, W, W, W, E, W,
			W, W, W, W, E, E, E, W, E, W, E, W, E, W, E, W, E, W, W, W, E, W, W, W, E, W, W, W, E, W, E, W, E, W, E, W, W, E, W, W, W, E, W, W, W, E, W, W,
			W, W, W, W, E, W, E, W, E, W, W, W, E, W, E, W, E, W, W, E, W, W, W, W, E, W, W, W, E, W, E, W, E, W, E, W, W, E, W, W, W, W, E, W, E, W, W, W,
			W, W, W, W, E, W, E, W, E, W, W, W, E, W, E, E, W, W, E, E, E, E, E, W, E, E, E, W, W, E, W, W, W, E, W, W, E, E, E, W, W, W, W, E, W, W, W, W,
			W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W

	};
}