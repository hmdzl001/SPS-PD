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
package com.hmdzl.spspd.change.levels;

import com.watabou.utils.Random;

public class SokobanLayouts2 {
	
	//32X32
	private static final int W = Terrain.WALL_DECO;
	private static final int T = Terrain.SHRUB;
	private static final int Z = Terrain.HIGH_GRASS;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;
	
	//private static final int T = Terrain.INACTIVE_TRAP;

	private static final int E = Terrain.EMPTY;
	//private static final int X = Terrain.EXIT;

	//private static final int M = Terrain.WALL_DECO;
	//private static final int P = Terrain.PEDESTAL;
	
	private static final int A = Terrain.WALL;
	private static final int C = Terrain.EMPTY_DECO;
	private static final int X = Terrain.EMPTY_SP;
	private static final int B = Terrain.BOOKSHELF;
	private static final int H = Terrain.SOKOBAN_HEAP;
    private static final int I = Terrain.EMBERS;
    private static final int F = Terrain.FLEECING_TRAP;
    private static final int U = Terrain.STATUE;
    private static final int G = Terrain.TENT;
    private static final int S = Terrain.SECRET_DOOR;
    private static final int R = Terrain.BED;
    private static final int V = Terrain.ALCHEMY;
	
	private static final int J = Terrain.STATUE_SP;
	private static final int K = Terrain.WATER;
	//  M N O P

		
	public static final int[] SOKOBAN_VAULT_LEVEL = {
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	I, 	I, 	I, 	I, 	I, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	I, 	I, 	I, 	I, 	I, 	E, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	X, 	X, 	X, 	X, 	X, 	I, 	I, 	I, 	E, 	E, 	E, 	W, 	W, 	W, 	A, 	B, 	B, 	B, 	B, 	B, 	A, 	W, 	W, 	A, 	A, 	A, 	X, 	X, 	X, 	J, 	Z, 	Z, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	X, 	K, 	K, 	K, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	A, 	A, 	A, 	X, 	X, 	X, 	X, 	R, 	A, 	W, 	W, 	A, 	X, 	X, 	X, 	X, 	X, 	J, 	Z, 	Z, 	A, 	A, 	W, 	W, 	W, 	W, 	W,
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	X, 	K, 	U, 	K, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	A, 	X, 	X, 	X, 	X, 	X, 	X, 	X, 	A, 	W, 	W, 	A, 	X, 	X, 	J, 	X, 	X, 	J, 	Z, 	Z, 	Z, 	A, 	W, 	W, 	W, 	W, 	W,
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	X,  K, 	K, 	K, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	A, 	V, 	X, 	X, 	X, 	X, 	X, 	X, 	A, 	W, 	W, 	A, 	X, 	X, 	J, 	J, 	J, 	J, 	Z, 	Z, 	Z, 	A, 	W, 	W, 	W, 	W, 	W,
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	G, 	E, 	X, 	X, 	X, 	X, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	A, 	A, 	A, 	X, 	X, 	X, 	X, 	X, 	A, 	W, 	W, 	A, 	X, 	X, 	X, 	X, 	X, 	J, 	Z, 	Z, 	Z, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	A, 	A, 	A, 	D, 	A, 	A, 	A, 	W, 	W, 	A, 	X, 	X, 	X, 	X, 	X, 	J, 	D, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E,  X, 	X, 	X, 	X, 	X, 	X, 	X, 	A, 	K, 	K, 	A, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	X, 	X, 	X, 	A, 	K, 	K, 	A, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	X, 	X, 	X, 	J, 	K, 	K, 	A, 	W, 	W, 	W, 	W,
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	K, 	K, 	J, 	K, 	J, 	K, 	J, 	K, 	K, 	K, 	J, 	K, 	J, 	K, 	J, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	X, 	X, 	X, 	A, 	K, 	K, 	A, 	W, 	W, 	W, 	W,
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	J, 	J, 	J, 	K, 	J, 	K, 	J, 	K, 	J, 	J, 	J, 	K, 	J, 	K, 	J, 	K, 	E, 	E, 	E, 	E, 	E, 	A, 	A, 	A, 	D, 	A, 	J, 	J, 	A, 	A, 	D, 	A, 	A, 	W, 	W, 	W, 	W,
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	J, 	K, 	K, 	K, 	J, 	K, 	J, 	K, 	J, 	K, 	K, 	K, 	J, 	K, 	J, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W,
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	J, 	J, 	J, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	K, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C,  C, 	C, 	C, 	C, 	C, 	C, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	C, 	C, 	T, 	T, 	C, 	T, 	T, 	C, 	C, 	T, 	T, 	C, 	C, 	T, 	T, 	T, 	C, 	T, 	C, 	C, 	T, 	C, 	C, 	T, 	T, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	Z, 	Z, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	C, 	T, 	C, 	C, 	T, 	T, 	C, 	T, 	C, 	T, 	C, 	C, 	C, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	C, 	C, 	T, 	C, 	C, 	T, 	T, 	C, 	C, 	T, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	T, 	T, 	T, 	C, 	T, 	C, 	T, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	C, 	C, 	C, 	T, 	C, 	T, 	C, 	C, 	C, 	T, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	T, 	C, 	T, 	C, 	C, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	C, 	T, 	T, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	T, 	T, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	T, 	T, 	T, 	C, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	X, 	Z, 	Z, 	Z, 	Z, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	E, 	E, 	E, 	X, 	G, 	X, 	G, 	X, 	Z, 	Z, 	Z, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	X, 	Z, 	Z, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	E, 	X, 	G, 	X, 	G, 	X, 	Z, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	T, 	T, 	C, 	T, 	T, 	T, 	C, 	C, 	T, 	T, 	C, 	T, 	T, 	T, 	C, 	T, 	T,  T, 	C, 	T, 	C, 	T, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	E, 	X, 	X, 	X, 	X, 	X, 	Z, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	T, 	C, 	C, 	T, 	T, 	T, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	T, 	T, 	C, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	T, 	C, 	C, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	T, 	C, 	C, 	C, 	T, 	T, 	T, 	C, 	T, 	T, 	C, 	C, 	C, 	T, 	C, 	C, 	T, 	T, 	T, 	C, 	C, 	T, 	C, 	C, 	T, 	C, 	T, 	C, 	T, 	T, 	T, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	C, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	X, 	X, 	X, 	X, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 
		W, 	W, 	W, 	W, 	W, 	A, 	D, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	A, 	E, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	A, 	E, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	T, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	D, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	A, 	E, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	Z, 	Z, 	J, 	B, 	B, 	X, 	A, 	W, 	T, 	W, 	W, 	W, 	W, 	A, 	B, 	B, 	B, 	B, 	A, 	X, 	X, 	X, 	X, 	R, 	B, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	T, 	T, 	S, 	K, 	S, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	U, 	Z, 	A, 	R, 	X, 	X, 	A, 	T, 	W, 	W, 	W, 	W, 	W, 	A, 	B, 	X, 	X, 	X, 	A, 	X, 	X, 	X, 	X, 	X, 	B, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	T, 	W, 	A, 	K, 	A, 	W, 	T, 	W, 	W, 	W, 	W, 	T, 	T, 	T, 	Z, 	Z, 	D, 	X, 	X, 	X, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	B, 	X, 	X, 	X, 	D, 	X, 	X, 	X, 	X, 	X, 	B, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	A, 	A, 	L, 	A, 	A, 	W, 	T, 	T, 	T, 	W, 	T, 	W, 	W, 	U, 	Z, 	A, 	X, 	X, 	V, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	B, 	X, 	X, 	X, 	A, 	X, 	X, 	X, 	X, 	X, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	A, 	K, 	K, 	K, 	A, 	W, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	Z, 	Z, 	A, 	X, 	X, 	X, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	B, 	X, 	G, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	A, 	K, 	K, 	K, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	A, 	U, 	E, 	U, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W
	};
	
	public static final int[] DRAGON_CAVE = {
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	H, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	H, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	H, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	H, 	H, 	H, 	H, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	H, 	H, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	H, 	E, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	H, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	S, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	E, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	H, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	E, 	W, 	W, 	E, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	H, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	H, 	E, 	E, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	H, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
		W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 
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
	
}
