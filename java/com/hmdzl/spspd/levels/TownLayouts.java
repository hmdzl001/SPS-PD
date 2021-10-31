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

public class TownLayouts {
	
	//32X32
	private static final int W = Terrain.WALL;
	private static final int T = Terrain.SHRUB;
	private static final int Z = Terrain.HIGH_GRASS;
	private static final int D = Terrain.DOOR;
	private static final int L = Terrain.LOCKED_DOOR;

	private static final int E = Terrain.EMPTY;
	private static final int X = Terrain.EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;
	private static final int F = Terrain.EMPTY_DECO;
	private static final int O = Terrain.EMPTY_SP;
	private static final int A = Terrain.WELL;
	private static final int B = Terrain.BOOKSHELF;

    private static final int U = Terrain.STATUE;
    private static final int S = Terrain.SECRET_DOOR;
	private static final int R = Terrain.WATER;
	private static final int Y = Terrain.ALCHEMY;
    private static final int G = Terrain.STATUE_SP;
	private static final int C = Terrain.TENT;
	private static final int H = Terrain.BED;
	private static final int I = Terrain.EMBERS;
	private static final int J = Terrain.GLASS_WALL;
	
	//private static final int V = Terrain.TRAP_AIR;
	
	
	public static final int[] TOWN_LAYOUT =	{     
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	O, 	O, 	O, 	M,
		M, 	M, 	M, 	M, 	E, 	M, 	T, 	M, 	M, 	M, 	M, 	M, 	E, 	E, 	M, 	M, 	M, 	M, 	M, 	R, 	R, 	R, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M, 	M, 	O, 	E, 	O, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	W, 	S, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	T, 	C, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	J, 	O, 	O, 	G, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	M,
		M, 	M, 	O, 	E, 	E, 	E, 	L, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	T, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	W, 	O, 	O, 	G, 	G, 	G, 	O, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	D, 	M,
		M, 	M, 	O, 	E, 	T, 	E, 	J, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	T, 	W, 	W, 	S, 	W, 	W, 	O, 	G, 	G, 	G, 	W, 	E, 	E, 	D, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	O, 	O, 	O, 	H, 	W, 	M, 	M, 	E, 	M,
		M, 	M, 	O, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	L, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	E, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	J, 	W, 	W, 	M, 	M, 	E, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	G, 	G, 	G, 	O, 	W, 	E, 	E, 	W, 	W, 	J, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	R, 	R, 	R, 	R, 	R, 	R, 	M, 	E, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	G, 	O, 	O, 	O, 	W, 	E, 	E, 	W, 	R, 	J, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	J, 	R, 	R, 	R, 	R, 	R, 	R, 	M, 	E, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	G, 	O, 	O, 	O, 	J, 	E, 	E, 	W, 	R, 	J, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	D, 	W, 	W, 	R, 	R, 	R, 	R, 	R, 	U, 	T, 	E, 	M,
		M, 	M, 	M, 	M, 	E, 	E, 	W, 	W, 	W, 	D, 	W, 	W, 	O, 	O, 	O, 	W, 	E, 	E, 	W, 	W, 	J, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	J, 	E, 	E, 	E, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	W, 	E, 	E, 	E, 	W, 	W, 	Y, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	W, 	O, 	H, 	O, 	H, 	W, 	W, 	D, 	W, 	W,  W, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E,  W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	J, 	O, 	P, 	P, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	D, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	J, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	W, 	O, 	O, 	G, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	O, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	O, 	R, 	U, 	R, 	O, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	W, 	O, 	O, 	G, 	G, 	G, 	G, 	G, 	W, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	O, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	J, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	O, 	O, 	O, 	O, 	O, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	J, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M,
		M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	O, 	U, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	O, 	O, 	O, 	W, 	B, 	B, 	B, 	W, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	O, 	U, 	W, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	W, 	W, 	D, 	W, 	W, 	W, 	M, 	M, 	M, 
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	M, 	M, 	M, 
		M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	M, 	M, 	M, 
		M, 	M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	B, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	D, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	P, 	O, 	B, 	W, 	E, 	E, 	E, 	E, 	O, 	O, 	O, 	O, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	B, 	W, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	M, 	M, 	M, 
		M, 	M, 	W, 	I, 	F, 	F, 	F, 	W, 	T, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	W, 	F, 	F, 	I, 	F, 	T, 	T, 	T, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	B, 	B, 	B, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	O, 	R, 	R, 	R, 	M, 	M, 	M,
		M, 	M, 	W, 	F, 	F, 	F, 	F, 	T, 	T, 	T, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	W, 	W, 	W, 	W, 	W, 	E, 	E, 	E, 	E, 	E, 	E, 	O, 	O, 	O, 	O, 	M, 	M, 	M, 
		M, 	M, 	W, 	F, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
		M, 	M, 	W, 	F, 	F, 	U, 	F, 	F, 	W, 	F, 	F, 	W, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	M, 	M, 	M,
		M, 	M, 	W, 	I, 	F, 	F, 	F, 	F, 	W, 	F, 	F, 	W, 	M, 	M, 	M, 	M, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	U, 	E, 	U, 	E, 	E, 	Z, 	Z, 	Z, 	Z, 	Z, 	Z, 	W, 	W, 	W, 	M,
		M, 	M, 	W, 	F, 	F, 	F, 	F, 	I, 	F, 	F, 	F, 	S, 	T, 	T, 	T, 	T, 	M, 	I, 	I, 	I, 	I, 	I, 	I, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	E, 	G, 	O, 	O, 	O, 	G, 	E, 	Z, 	Z, 	Z, 	Z, 	W, 	S, 	W, 	Z, 	W, 	M,
		M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	W, 	F, 	F, 	W, 	M, 	M, 	M, 	M, 	M, 	O, 	I, 	I, 	I, 	I, 	O, 	E, 	E, 	E, 	M, 	M, 	E, 	E, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	E, 	Z, 	Z, 	Z, 	Z, 	S, 	Z, 	Z, 	Z, 	W, 	M,
		M, 	M, 	W, 	F, 	F, 	F, 	F, 	F, 	W, 	F, 	I, 	W, 	M, 	M, 	M, 	M, 	M, 	O, 	I, 	I, 	I, 	I, 	O, 	E, 	E, 	M, 	M, 	M, 	M, 	M, 	E, 	E, 	W, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	Z, 	O, 	O, 	W, 	M,
		M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M, 	M, 	O, 	O, 	O, 	O, 	O, 	O, 	E, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	M, 	M, 	M, 	M, 	W, 	Z, 	Z, 	O, 	O, 	W, 	M,
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	M,
		M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M 

		
	};
	
}
