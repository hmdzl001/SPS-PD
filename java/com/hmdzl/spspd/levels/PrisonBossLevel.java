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
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Scene;
import com.watabou.utils.Bundle;

import static com.hmdzl.spspd.Dungeon.hero;

public class PrisonBossLevel extends Floor {

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
		return Assets.TILES_PRISON;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
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
		map = MAP_P_START.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();

		entrance = 23 + WIDTH * 15;
		exit = 23 + WIDTH * 37;
		

		return true;

	}

	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
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
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc(tile);
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		PrisonLevel.addVisuals(this, scene);
	}

	private static final int W = Terrain.WALL;
	private static final int D = Terrain.DOOR;
	private static final int I = Terrain.HIGH_GRASS;
	private static final int O = Terrain.EMPTY; //for readability
	private static final int S = Terrain.SIGN;
	private static final int A = Terrain.WATER;

	private static final int T = Terrain.INACTIVE_TRAP;

	private static final int E = Terrain.ENTRANCE;
	private static final int X = Terrain.LOCKED_EXIT;

	private static final int M = Terrain.WALL_DECO;
	private static final int P = Terrain.PEDESTAL;

	private static final int[] MAP_P_START =
			{
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	I,  O, 	W, 	O, 	S, 	O, 	T, 	O, 	W, 	O, 	O, 	I, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W,  T, 	D, 	O, 	O, 	E, 	O, 	O, 	D, 	O, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O,  I, 	O, 	W, 	O, 	I, 	O, 	A, 	O, 	W, 	T, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	O, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	A, 	O, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	O, 	O, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	O, 	W, 	O, 	I, 	O, 	O, 	O, 	I, 	O, 	W, 	W, 	O, 	A, 	A, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	I, 	A, 	O, 	W, 	O, 	W, 	W, 	T, 	W, 	W, 	O, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	O, 	A, 	O, 	D, 	O, 	O, 	T, 	P, 	T, 	O, 	O, 	D, 	T, 	O, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	O, 	W, 	O, 	O, 	T, 	O, 	T, 	O, 	O, 	W, 	T, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	A, 	O, 	W, 	O, 	W, 	W, 	T, 	W, 	W, 	O, 	W, 	O, 	W, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	A, 	O, 	W, 	O, 	I, 	O, 	O,  O, 	I, 	O, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	D, 	W, 	W, 	D, 	W, 	W, 	D, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	O, 	W, 	O, 	W, 	O, 	A, 	O, 	W, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	A, 	W, 	O, 	W, 	A, 	W, 	A, 	W, 	W, 	W, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	O, 	O, 	T, 	D, 	O, 	A, 	O, 	D, 	O, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	W, 	O, 	W, 	O, 	W, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	W, 	O, 	O, 	O, 	W, 	O, 	A, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	D, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	W, 	O, 	W, 	W, 	O, 	W, 	W, 	O, 	W, 	T, 	W, 	T, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	T, 	O, 	O, 	O, 	T, 	W, 	W, 	O, 	W, 	W, 	O, 	W, 	T, 	W, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	O, 	D, 	O, 	O, 	O, 	D, 	O, 	W, 	O, 	W, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	O, 	O, 	W, 	O, 	W, 	W, 	O, 	W, 	W, 	O,  W, 	O, 	W, 	A, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	W, 	A, 	W, 	W, 	O, 	W, 	T, 	T, 	T, 	W, 	T, 	O, 	A, 	W, 	W, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	O, 	I, 	I, 	I, 	O, 	O, 	W, 	T, 	X, 	T, 	W, 	O, 	O, 	W, 	O, 	O, 	O, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	T, 	T, 	T, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	W, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M,
					M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M, 	M
			};
}