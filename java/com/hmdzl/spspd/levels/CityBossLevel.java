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
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class CityBossLevel extends Floor {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
	}

	private static final int TOP = 2;
	private static final int HALL_WIDTH = 7;
	private static final int HALL_HEIGHT = 15;
	private static final int CHAMBER_HEIGHT = 3;

	private static final int LEFT = (getWidth() - HALL_WIDTH) / 2;
	private static final int CENTER = LEFT + HALL_WIDTH / 2;

	private int arenaDoor;
	private int stairs = -1;
	private boolean enteredArena = false;

	@Override
	public String tilesTex() {
		return Assets.TILES_CITY;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CITY;
	}

	private static final String DOOR = "door";
	private static final String ENTERED = "entered";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DOOR, arenaDoor);
		bundle.put(ENTERED, enteredArena);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		arenaDoor = bundle.getInt(DOOR);
		enteredArena = bundle.getBoolean(ENTERED);
	}

	@Override
	protected boolean build() {

		Painter.fill(this, LEFT, TOP, HALL_WIDTH, HALL_HEIGHT, Terrain.EMPTY);
		Painter.fill(this, CENTER, TOP, 1, HALL_HEIGHT, Terrain.EMPTY_SP);
  
        map[(TOP + 1) * getWidth() + CENTER] = Terrain.WELL;

		map[(TOP + 1) * getWidth() + CENTER - 1] = Terrain.EMPTY_SP;
		map[(TOP + 1) * getWidth() + CENTER + 1] = Terrain.EMPTY_SP;
  
		int y = TOP + 1;
		while (y < TOP + HALL_HEIGHT) {
			map[y * getWidth() + CENTER - 2] = Terrain.STATUE_SP;
			map[y * getWidth() + CENTER + 2] = Terrain.STATUE_SP;
			y += 2;
		}

		int left = pedestal(true);
		int right = pedestal(false);
		map[left] = map[right] = Terrain.PEDESTAL;
		for (int i = left + 1; i < right; i++) {
			map[i] = Terrain.EMPTY_SP;
		}

		exit = (TOP - 1) * getWidth() + CENTER;
		map[exit] = Terrain.LOCKED_EXIT;

		arenaDoor = (TOP + HALL_HEIGHT) * getWidth() + CENTER;
		map[arenaDoor] = Terrain.DOOR;

		Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, HALL_WIDTH,
				CHAMBER_HEIGHT, Terrain.EMPTY);
		Painter.fill(this, LEFT, TOP + HALL_HEIGHT + 1, 1, CHAMBER_HEIGHT,
				Terrain.BOOKSHELF);
		Painter.fill(this, LEFT + HALL_WIDTH - 1, TOP + HALL_HEIGHT + 1, 1,
				CHAMBER_HEIGHT, Terrain.BOOKSHELF);

		entrance = (TOP + HALL_HEIGHT + 2 + Random.Int(CHAMBER_HEIGHT - 1))
				* getWidth() + LEFT + (/* 1 + */Random.Int(HALL_WIDTH - 2));
		map[entrance] = Terrain.ENTRANCE;

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

		int sign = arenaDoor + getWidth() + 1;
		map[sign] = Terrain.SIGN;
	}

	public static int pedestal(boolean left) {
		if (left) {
			return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER - 2;
		} else {
			return (TOP + HALL_HEIGHT / 2) * getWidth() + CENTER + 2;
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

	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

	@Override
	public void press(int cell, Char hero) {

		super.press(cell, hero);

		if (!enteredArena && outsideEntraceRoom(cell) && hero == Dungeon.hero) {

			enteredArena = true;
			seal();

			Mob boss = Bestiary.mob(Dungeon.dungeondepth);
			//Mob tomb = new DwarfKingTomb();
			boss.state = boss.HUNTING;
			int count = 0;
			do {
				boss.pos = Random.Int(getLength());
				//tomb.pos = (TOP + 1) * getWidth() + CENTER;
			} while (!passable[boss.pos] 
					|| !outsideEntraceRoom(boss.pos)
					|| (Dungeon.visible[boss.pos] && count++ < 20));
			GameScene.add(boss);
			//GameScene.add(tomb);

			if (Dungeon.visible[boss.pos]) {
				boss.notice();
				boss.sprite.alpha(0);
				boss.sprite.parent.add(new AlphaTweener(boss.sprite, 1, 0.1f));
			}

			set(arenaDoor, Terrain.WALL);
			GameScene.updateMap(arenaDoor);
			Dungeon.observe();
		}
	}

	public void seal() {
		if (entrance != 0) {

			locked = true;

			set(arenaDoor, Terrain.WALL);
			GameScene.updateMap(arenaDoor);
			Dungeon.observe();

			CellEmitter.get(arenaDoor).start(Speck.factory(Speck.ROCK), 0.07f,
					10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);

			set(entrance, Terrain.WALL_DECO);
			GameScene.updateMap(entrance);
			GameScene.ripple(entrance);			
			
		}
	}

	public void unseal() {
		if (stairs != 0) {
			locked = false;
			CellEmitter.get(arenaDoor).start(Speck.factory(Speck.ROCK), 0.07f,
					10);
			set(arenaDoor, Terrain.EMPTY_DECO);
			GameScene.updateMap(arenaDoor);
			Dungeon.observe();
		    set(entrance, Terrain.ENTRANCE);
			GameScene.updateMap(entrance);

		}
	}

	private boolean outsideEntraceRoom(int cell) {
		return cell / getWidth() < arenaDoor / getWidth();
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			    return Messages.get(CityLevel.class, "water_name");
			case Terrain.HIGH_GRASS:
			    return Messages.get(CityLevel.class, "high_grass_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(CityLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(CityLevel.class, "exit_desc");
			case Terrain.WALL_DECO:
			case Terrain.EMPTY_DECO:
				return Messages.get(CityLevel.class, "deco_desc");
			case Terrain.EMPTY_SP:
				return Messages.get(CityLevel.class, "sp_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(CityLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(CityLevel.class, "bookshelf_desc");
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		CityLevel.addVisuals(this, scene);
	}
}
