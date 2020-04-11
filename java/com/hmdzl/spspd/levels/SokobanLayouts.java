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

import com.watabou.utils.Random;

public class SokobanLayouts {
	
	//32X32
	private static final int W = Terrain.WALL;
	private static final int T = Terrain.SHRUB;
	//private static final int Z = Terrain.HIGH_GRASS;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;

	//private static final int T = Terrain.INACTIVE_TRAP;

	private static final int E = Terrain.EMPTY;
	private static final int Z = Terrain.EXIT;

	//private static final int M = Terrain.WALL_DECO;
	//private static final int P = Terrain.PEDESTAL;
	
	private static final int A = Terrain.SOKOBAN_SHEEP;
	private static final int X = Terrain.CORNER_SOKOBAN_SHEEP;
	private static final int C = Terrain.SWITCH_SOKOBAN_SHEEP;
	private static final int B = Terrain.BLACK_SOKOBAN_SHEEP;
	private static final int H = Terrain.SOKOBAN_HEAP;
    private static final int I = Terrain.SOKOBAN_ITEM_REVEAL;
    private static final int F = Terrain.FLEECING_TRAP;
    private static final int U = Terrain.STATUE;
    private static final int G = Terrain.CHANGE_SHEEP_TRAP;
    private static final int S = Terrain.SECRET_DOOR;
    private static final int R = Terrain.PORT_WELL;
    private static final int V = Terrain.SOKOBAN_PORT_SWITCH;

	public static final int[] SOKOBAN_INTRO_LEVEL =	{      
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	A, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	F, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	E, 	W, 	E, 	C, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	U, 	E, 	D, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	G, 	E, 	E, 	E, 	G, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	U, 	A, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	S, 	E, 	E, 	E, 	E, 	D, 	F, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	H, 	W, 	W, 	U, 	U, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	F, 	U, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	F, 	A, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	F, 	U, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	U, 	X, 	U, 	W, 	E, 	E, 	E, 	E, 	W, 	E, 	C, 	E, 	E, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	F, 	U, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	U, 	U, 	F, 	D, 	E, 	E, 	C, 	E, 	W, 	E, 	G, 	E, 	E, 	F, 	D, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	G, 	U, 	F, 	U, 	G, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	U, 	W, 	C, 	G, 	G, 	E, 	W, 	W, 	W, 	W, 	W, 	C, 	U, 	H, 	U, 	C, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	D, 	E, 	E, 	E, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	B, 	E, 	E, 	E, 	D, 	F, 	E, 	E, 	E, 	D, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	U, 	U, 	U, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	R, 	E, 	E, 	W, 	B, 	B, 	E, 	E, 	W, 	E, 	G, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	E, 	H, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	B, 	W, 	E, 	F, 	F, 	A, 	E, 	W, 	W, 	W, 	W, 	W, 	W,
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	U, 	U, 	U, 	E, 	W, 	E, 	G, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	D, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	H, 	F, 	E, 	E, 	W, 	E, 	G, 	E, 	E, 	W, 	E, 	I, 	A, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	C, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	L, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	X, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	I, 	V, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W

	};

	public static final int[] SOKOBAN_CASTLE =	{      
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	I, 	T, 	I, 	T, 	I, 	T, 	I, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	S, 	W, 	W, 	W, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	R, 	U, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	H, 	W, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	U, 	U, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	F, 	F, 	W, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	W, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	T, 	T, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	G, 	E, 	W, 	F, 	G, 	C, 	F, 	E, 	E, 	F, 	E, 	B, 	W, 	F, 	W, 	F, 	W, 	F, 	F, 	H, 	F, 	H, 	F, 	F, 	F, 	F, 	W, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	H, 	E, 	S, 	F, 	F, 	X, 	F, 	E, 	C, 	U, 	E, 	E, 	W, 	E, 	W, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	S, 	E, 	X, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	F, 	F, 	X, 	F, 	G, 	C, 	U, 	E, 	E, 	W, 	E, 	W, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	F, 	F, 	W, 	H, 	F, 	E, 	F, 	G, 	H, 	U, 	E, 	E, 	W, 	E, 	W, 	E, 	W, 	E, 	X, 	X, 	X, 	X, 	X, 	E, 	E, 	E, 	W, 	F, 	F, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	E, 	B, 	E, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	S, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	F, 	F, 	E, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	H, 	F, 	U, 	F, 	H, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	F, 	U, 	F, 	U, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	U, 	U, 	E, 	U, 	U, 	W, 	W, 	W, 	W, 	W, 	U, 	E, 	H, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	X, 	G, 	X, 	E, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	E, 	G, 	E, 	G, 	E, 	W, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	C, 	G, 	C, 	E, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	C, 	G, 	E, 	G, 	C, 	W, 	W, 	H, 	H, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	E, 	E, 	X, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	U, 	U, 	U, 	C, 	L, 	E, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	U, 	E, 	U, 	U, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	H, 	F, 	G, 	E, 	W, 	E, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	U, 	U, 	U, 	B, 	W, 	E, 	W, 	W, 	W, 	G, 	G, 	I, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	E, 	G, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	X, 	E, 	C, 	W, 	W, 	G, 	W, 	G,  W, 	W, 	C, 	A, 	G, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	U, 	U, 	F, 	D, 	E, 	W, 	W, 	W, 	E, 	E, 	C, 	E, 	G, 	X, 	C, 	X, 	G, 	E, 	E, 	C, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	I, 	C, 	E, 	F, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	E, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	G, 	X, 	E, 	E, 	W, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	G, 	X, 	C, 	X, 	G, 	E, 	E, 	E, 	E, 	E, 	X, 	E, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	U, 	U, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	U, 	U, 	E, 	U, 	U, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	U, 	U, 	E, 	E, 	W, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	G, 	X, 	G, 	I, 	S, 	S, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	E, 	T, 	W, 	W, 	E, 	W, 	R, 	L, 	F, 	F, 	X, 	E, 	W, 	E, 	W, 	F, 	F, 	H, 	F, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	U, 	F, 	E, 	F, 	U, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	H, 	U, 	G, 	G, 	D, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	E, 	E, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	F, 	U, 	E, 	C, 	W, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	U, 	E, 	X, 	E, 	U, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	E, 	W, 	E, 	X, 	X, 	E, 	E, 	E, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	A, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	E, 	W, 	E, 	W, 	W, 	H, 	S, 	I, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	G, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	E, 	F, 	X, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	U, 	E, 	U, 	S, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	U, 	F, 	U, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	G, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	I, 	H, 	C, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	R, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	W, 	E, 	B, 	E, 	W, 	W, 	W, 	W, 	X, 	X, 	X, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	E, 	X, 	E, 	E, 	E, 	E, 	B, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	E, 	U, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	X, 	X, 	X, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	F, 	U, 	F, 	U, 	W, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	I, 	H, 	I, 	W, 	W, 	W, 	E, 	E, 	C, 	G, 	F, 	F, 	H, 	I, 	W, 	F, 	W, 	F, 	W, 	W, 	W, 	W, 	E, 	H, 	V, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	T, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	W, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	T, 	W, 	W, 
		W, 	T, 	E, 	W, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	F, 	F, 	W, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	H, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	H, 	S, 	T, 	W, 	W, 
		W, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	T, 	W, 	W, 
		W, 	W, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	T, 	W, 	W,
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W

	};
	
	public static final int[] SOKOBAN_TELEPORT_LEVEL =	{      
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	R, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	H, 	H, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	X, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	F, 	F, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	B, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	R, 	S, 	E, 	L, 	B, 	W, 	F, 	R, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	R, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	I, 	F, 	R, 	W, 	W, 	W, 	W, 	F, 	H, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	F, 	F, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	R, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	R, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	H, 	W, 	W, 	W, 	W, 	E, 	R, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	R, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	G, 	G, 	G, 	F, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	X, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	V, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	E, 	E, 	E, 	I, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	C, 	W, 	E, 	V, 	X, 	E, 	W, 	W, 	E, 	X, 	V, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	R, 	W, 	W, 	W, 	E, 	R, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	R, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	W, 	W, 	E, 	E, 	E, 	R, 	W, 	W, 	R, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	X, 	W, 	W, 	W, 	E, 	E, 	I, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	C, 	G, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	C, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	H, 	E, 	W, 	W, 	W, 	W, 	S, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	E, 	R, 	W, 	W, 	R, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	C, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	R, 	E, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	V, 	X, 	E, 	W, 	W, 	E, 	X, 	V, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	A, 	R, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	I, 	E, 	E, 	E, 	W, 	W, 	E, 	E, 	E, 	H, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	C, 	R, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	E, 	E, 	E, 	E, 	E, 	W, 	E, 	E, 	W, 	W, 	E, 	X, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	E, 	U, 	E, 	E, 	E, 	L, 	E, 	H, 	W, 	W, 	G, 	G, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	U, 	I, 	U, 	I, 	E, 	E, 	W, 	E, 	E, 	W, 	W, 	E, 	E, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	E, 	F, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	R, 	H, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	E, 	E, 	E, 	U, 	E, 	U, 	E, 	E, 	U, 	E, 	U, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	U, 	E, 	U, 	E, 	E, 	U, 	E, 	U, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	U, 	U, 	E, 	U, 	F, 	F, 	U, 	E, 	U, 	U, 	U, 	E, 	E, 	E, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	G, 	U, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	F, 	U, 	G, 	E, 	E, 	E, 	U, 	I, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	F, 	F, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	G, 	F, 	E, 	E, 	X, 	E, 	X, 	X, 	E, 	X, 	E, 	E, 	F, 	G, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	G, 	U, 	F, 	V, 	E, 	V, 	E, 	E, 	V, 	E, 	V, 	F, 	U, 	G, 	E, 	E, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	F, 	F, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	E, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	U, 	E, 	E, 	E, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	E, 	E, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	I, 	U, 	E, 	E, 	E, 	E, 	E, 	X, 	E, 	E, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	I, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	H, 	E, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	V, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	U, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	E, 	X, 	X, 	R, 	C, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	R, 	S, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	U, 	E, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	G, 	G, 	G, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	H, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W


	};

	public static final int[] SOKOBAN_PUZZLE_LEVEL =	{ 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	R, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	E, 	E, 	E, 	E, 	U, 	S, 	R, 	W, 	W, 	W, 	E, 	A, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	C, 	U, 	H, 	U, 	C, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	H, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	G, 	U, 	F, 	U, 	G, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	H, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	S, 	E, 	G, 	G, 	U, 	F, 	U, 	G, 	G, 	V, 	S, 	E, 	E, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	E, 	E, 	E, 	E, 	U, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	U, 	W, 	W, 	C, 	I, 	U, 	F, 	U, 	I, 	C, 	W, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	V, 	W, 	W, 	G, 	G, 	U, 	F, 	U, 	G, 	G, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	C, 	E, 	U, 	F, 	U, 	E, 	C, 	W, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	S, 	R, 	G, 	G, 	U, 	F, 	U, 	G, 	G, 	V, 	S, 	E, 	E, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	A, 	E, 	A, 	E, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	I, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	G, 	C, 	U, 	C, 	G, 	U, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	H, 	U, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	W, 	B, 	E, 	E, 	E, 	B, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	W, 	G, 	G, 	E, 	G, 	G, 	W, 	E, 	E, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	R, 	W, 	W, 	W, 	E, 	E, 	U, 	E, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	S, 	E, 	E, 	G, 	E, 	E, 	S, 	E, 	G, 	F, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	H, 	E, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	W, 	E, 	F, 	F, 	F, 	E, 	W, 	E, 	E, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	E, 	E, 	U, 	E, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	W, 	E, 	F, 	H, 	F, 	E, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	R, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	C, 	G, 	C, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	X, 	G, 	X, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	C, 	C, 	C, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	I, 	E, 	G, 	G, 	G, 	G, 	G, 	E, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	H, 	H, 	E, 	L, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	U, 	U, 	U, 	U, 	U, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	H, 	E, 	W, 	F, 	U, 	U, 	U, 	U, 	W, 	T, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	U, 	F, 	E, 	E, 	E, 	F, 	U, 	G, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	A, 	X, 	B, 	I, 	E, 	W, 	T, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	U, 	E, 	F, 	F, 	F, 	E, 	U, 	G, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	T, 	W, 	W, 	W, 	U, 	A, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	U, 	E, 	U, 	F, 	U, 	E, 	U, 	G, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	T, 	W, 	W, 	W, 	T, 	T, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	U, 	E, 	U, 	F, 	U, 	E, 	U, 	G, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	T, 	F, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	G, 	U, 	F, 	U, 	H, 	U, 	F, 	U, 	G, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	A, 	F, 	F, 	E, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	T, 	F, 	R, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	U, 	U, 	U, 	U, 	U, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	T, 	F, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	F, 	S, 	E, 	B, 	E, 	B, 	E, 	B, 	E, 	S, 	F, 	H, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	T, 	T, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	L, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	T, 	T, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	G, 	V, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	T, 	T, 	T, 	T, 	T, 	T, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	B, 	G, 	G, 	G, 	G, 	G, 	F, 	F, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	C, 	G, 	C, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	U, 	G, 	U, 	U, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	R, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	C, 	E, 	C, 	C, 	W, 	W, 	W, 	W, 	W, 	X, 	E, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	G, 	E, 	G, 	U, 	W, 	W, 	W, 	W, 	W, 	F, 	E, 	E, 	W, 	W, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	B, 	C, 	E, 	C, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	U, 	E, 	U, 	U, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	G, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	B, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	L, 	E, 	B, 	E, 	F, 	H, 	D, 	C, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W,
      };	

	
}
