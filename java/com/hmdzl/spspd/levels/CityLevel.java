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
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.GoldThief;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.traps.BoundTrap;
import com.hmdzl.spspd.levels.traps.CursingTrap;
import com.hmdzl.spspd.levels.traps.DisarmingTrap;
import com.hmdzl.spspd.levels.traps.ExplosiveTrap;
import com.hmdzl.spspd.levels.traps.GrippingTrap;
import com.hmdzl.spspd.levels.traps.GuardianTrap;
import com.hmdzl.spspd.levels.traps.PitfallTrap;
import com.hmdzl.spspd.levels.traps.RockfallTrap;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.levels.traps.SummoningTrap;
import com.hmdzl.spspd.levels.traps.TeleportationTrap;
import com.hmdzl.spspd.levels.traps.WarpingTrap;
import com.hmdzl.spspd.levels.traps.WeakeningTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.DarkDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.EarthDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.IceDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.LightDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.ShockDamage2Trap;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class CityLevel extends RegularLevel {

	{
		color1 = 0x4b6636;
		color2 = 0xf2f2f2;
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_CITY;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CITY;
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
	protected void setPar(){
		Dungeon.pars[Dungeon.depth] = 250+(Dungeon.depth*50)+(secretDoors*20);
	}

	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ SpearTrap.class, ExplosiveTrap.class, GrippingTrap.class,
				RockfallTrap.class, WeakeningTrap.class, BoundTrap.class, Dewcharge.class,
				CursingTrap.class, GuardianTrap.class, PitfallTrap.class,
				SummoningTrap.class, TeleportationTrap.class, DisarmingTrap.class, WarpingTrap.class,
				FireDamage2Trap.class, IceDamage2Trap.class, ShockDamage2Trap.class, EarthDamage2Trap.class,
				LightDamage2Trap.class, DarkDamage2Trap.class};
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 6, 4, 3,
				6, 3, 4, 2,
				4, 4, 4,
				4, 1, 2, 3,
				3, 3, 3, 3,
				3, 3};
	}	
	
	@Override
	protected boolean assignRoomType() {
		super.assignRoomType();

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

		for (int i = getWidth(); i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.WALL && feeling == Feeling.SPECIAL_FLOOR && Level.insideMap(i)) {

				map[i] = Terrain.GLASS_WALL;
			}
		}


		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance) {
				map[pos] = Terrain.SIGN;
				break;
			}
		}
		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance) {
				map[pos] = Terrain.DEW_BLESS;
				break;
			}
		}
			
		setPar();
	}

	@Override
	protected void createItems() {
		if (Dungeon.hero.heroClass==HeroClass.PERFORMER && Random.Int(1) == 0){addItemToSpawn(new DungeonBomb());}
		super.createItems();

		Imp.Quest.spawn(this);
		spawnGoldThief(this);
	}

    @Override
    protected void createMobs() {
        int nMobs = nMobs();
        for (int i = 0; i < nMobs; i++) {
            Mob mob = Bestiary.mob(Dungeon.depth);
            do {
                mob.pos = randomRespawnCell();
                mob.originalgen=true;
                Buff.affect(mob,ShieldArmor.class).level(Dungeon.depth*10);
                Buff.affect(mob,MagicArmor.class).level(Dungeon.depth*10);
            } while (mob.pos == -1);
            mobs.add(mob);
            Actor.occupyCell(mob);
        }
    }

	public static void spawnGoldThief(CityLevel level) {
		if (Dungeon.depth == 19 && !Dungeon.goldthiefspawned){

			GoldThief thief = new GoldThief();
			do {
				thief.pos = level.randomRespawnCell();
			} while (thief.pos == -1);
			level.mobs.add(thief);
			Actor.occupyCell(thief);
           
			Dungeon.goldthiefspawned = true;
		}
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
		super.addVisuals(scene);
		addVisuals(this, scene);
	}

	public static void addVisuals(Level level, Scene scene) {
		for (int i = 0; i < getLength(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				scene.add(new Smoke(i));
			}
		}
	}

	private static class Smoke extends Emitter {

		private int pos;

		private static final Emitter.Factory factory = new Factory() {

			@Override
			public void emit(Emitter emitter, int index, float x, float y) {
				SmokeParticle p = (SmokeParticle) emitter
						.recycle(SmokeParticle.class);
				p.reset(x, y);
			}
		};

		public Smoke(int pos) {
			super();

			this.pos = pos;

			PointF p = DungeonTilemap.tileCenterToWorld(pos);
			pos(p.x - 4, p.y - 2, 4, 0);

			pour(factory, 0.2f);
		}

		@Override
		public void update() {
			if (visible = Dungeon.visible[pos]) {
				super.update();
			}
		}
	}

	public static final class SmokeParticle extends PixelParticle {

		public SmokeParticle() {
			super();

			color(0x000000);
			speed.set(Random.Float(8), -Random.Float(8));
		}

		public void reset(float x, float y) {
			revive();

			this.x = x;
			this.y = y;

			left = lifespan = 2f;
		}

		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.8f ? 1 - p : p * 0.25f;
			size(8 - p * 4);
		}
	}
}