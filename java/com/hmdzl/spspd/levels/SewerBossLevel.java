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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class SewerBossLevel extends Floor {

	{
		color1 = 0x6a723d;
		color2 = 0x88924c;
	}

	//private Room anteroom;
	//private int arenaDoor;

	private int stairs = -1;
	private boolean enteredArena = false;


	@Override
	public String tilesTex() {
		return Assets.TILES_SEWERS;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_SEWERS;
	}

	//private static final String ARENA = "arena";
	//private static final String STAIRS	= "stairs";
	//private static final String DOOR = "door";
	private static final String ENTERED = "entered";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		//bundle.put(DOOR, arenaDoor);
		//bundle.put( STAIRS, stairs );
		bundle.put(ENTERED, enteredArena);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		//arenaDoor = bundle.getInt(DOOR);
		//stairs = bundle.getInt( STAIRS );
		enteredArena = bundle.getBoolean(ENTERED);
	}

	@Override
	protected boolean build() {
		map = MAP_S_START.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();

		entrance = 23 + WIDTH * 15;
		exit = 23 + WIDTH * 37;


		

		return true;

	}

	@Override
	protected void decorate() {
		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && heaps.get(i) == null && Random.Float() < .10) {
				map[i] = Terrain.HIGH_GRASS;
			}
			if (map[i] == Terrain.EMPTY && heaps.get(i) == null && Random.Float() < .10) {
				map[i] = Terrain.OLD_HIGH_GRASS;
			}
		}
	}

	@Override
	protected void createMobs() {
	}

	@Override
	public Actor respawner() {
		return null;
	}

	@Override
	protected void createItems() {
		for (int i = 0; i < LENGTH; i++) {
			if (map[i]==Terrain.GROUND_A && heaps.get(i) == null){
				 drop(Generator.random(), i).type = Heap.Type.CHEST;
			}
		}


	}

	@Override
	public void press(int cell, Char ch) {

		super.press(cell, ch);

		if (!enteredArena && hero == Dungeon.hero && cell != entrance) {

			enteredArena = true;
			seal();

			Mob boss = Bestiary.mob(Dungeon.dungeondepth);
			boss.state = boss.HUNTING;
			boss.pos = 23 + WIDTH * 21;
			GameScene.add(boss);
			boss.notice();

			GameScene.updateMap();
			Dungeon.observe();
			

		}
	}
	
	@Override
	public int randomRespawnCell() {
		return -1;
	}
	
	public void seal() {
		if (entrance != 0) {

			locked = true;

			set(entrance, Terrain.WALL_DECO);
			GameScene.updateMap(entrance);
			GameScene.ripple(entrance);

		}
	}

	public void unseal() {
		if (stairs != 0) {

			locked = false;

			set(entrance, Terrain.ENTRANCE);
			GameScene.updateMap(entrance);

		}
	}	
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(SewerLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(SewerLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(SewerLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		SewerLevel.addVisuals(this, scene);
	}

	private static final int W = Terrain.WALL;
	private static final int D = Terrain.DOOR;
	private static final int B = Terrain.SECRET_DOOR;
	private static final int I = Terrain.GLASS_WALL;
	private static final int O = Terrain.EMPTY; //for readability
	private static final int S = Terrain.SIGN;
	private static final int A = Terrain.WATER;

	private static final int T = Terrain.EMPTY_DECO;

	private static final int E = Terrain.ENTRANCE;
	private static final int X = Terrain.LOCKED_EXIT;
	private static final int F = Terrain.EMPTY_SP;
	private static final int C = Terrain.GROUND_A;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;

	private static final int[] MAP_S_START =
			{
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	W, 	W, 	W, 	O, 	O, 	B, 	T, 	C, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	C, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	A, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	A, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	O, 	T, 	T, 	T, 	O, 	W, 	W, 	W, 	O, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O,  O, 	W, 	O, 	S, 	O, 	O, 	O, 	W, 	W, 	W, 	O, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	C, 	O, 	O, 	D, 	O, 	O, 	O, 	O, 	D, 	O, 	O, 	O,  O, 	D, 	O, 	O, 	E, 	O, 	O, 	D, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O,  O, 	O, 	W, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	O, 	W, 	W, 	O, 	I, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	I, 	I, 	I, 	I, 	B, 	I, 	I, 	I, 	I, 	W, 	O, 	A, 	A, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	W, 	I, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	I, 	W, 	O, 	A, 	A, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	A, 	I, 	O, 	W, 	A, 	A, 	A, 	W, 	O, 	I, 	A, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	A, 	I, 	O, 	A, 	A, 	P, 	A, 	A, 	O, 	I, 	A, 	O, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	B, 	O, 	A, 	B, 	O, 	A, 	P, 	P, 	P, 	A, 	O, 	B, 	A, 	O, 	O, 	O, 	B, 	O, 	W, 	W, 	W, 	W, 	W, 	B, 	F, 	C, 	F, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	A, 	I, 	O, 	A, 	A, 	P, 	A, 	A, 	O, 	I, 	A, 	W, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	A, 	I, 	O, 	W, 	A, 	A,  A, 	W, 	O, 	I, 	A, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	W, 	I, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	I, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	W, 	I, 	I, 	I, 	I, 	B, 	I, 	I, 	I, 	I, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	A, 	A, 	A, 	A, 	A, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	A, 	A, 	W, 	O, 	I, 	O, 	W, 	A, 	A, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	A, 	A, 	W, 	O, 	O, 	O, 	W, 	A, 	A, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	O, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	O, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	W, 	O, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	D, 	O, 	O, 	O, 	D, 	O, 	O, 	O, 	O, 	O, 	O, 	I, 	O, 	C, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	C, 	O, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	O, 	B, 	O, 	O, 	O, 	C, 	O, 	O, 	W, 	W, 	D, 	W, 	W, 	O,  O, 	C, 	O, 	O, 	O, 	W, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	W, 	O, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	F, 	F, 	F, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	D, 	F, 	X, 	F, 	D, 	O, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	F, 	F, 	F, 	W, 	W, 	W, 	W, 	B, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	W, 	W, 	A, 	A, 	A, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	A, 	A, 	A, 	A, 	A, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	O, 	A, 	C, 	A, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	A, 	W, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	W, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	A, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M
			};
}