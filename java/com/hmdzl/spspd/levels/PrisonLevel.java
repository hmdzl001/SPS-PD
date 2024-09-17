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
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.mobs.MossySkeleton;
import com.hmdzl.spspd.actors.mobs.npcs.Wandmaker;
import com.hmdzl.spspd.effects.Halo;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.traps.AlarmTrap;
import com.hmdzl.spspd.levels.traps.BoundTrap;
import com.hmdzl.spspd.levels.traps.ConfusionTrap;
import com.hmdzl.spspd.levels.traps.DewTrap;
import com.hmdzl.spspd.levels.traps.FlashingTrap;
import com.hmdzl.spspd.levels.traps.FlockTrap;
import com.hmdzl.spspd.levels.traps.GrippingTrap;
import com.hmdzl.spspd.levels.traps.KnowledgeTrap;
import com.hmdzl.spspd.levels.traps.ParalyticTrap;
import com.hmdzl.spspd.levels.traps.PoisonTrap;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.levels.traps.SummoningTrap;
import com.hmdzl.spspd.levels.traps.TeleportationTrap;
import com.hmdzl.spspd.levels.traps.ToxicTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.DarkDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.EarthDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.IceDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.LightDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.ShockDamageTrap;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class PrisonLevel extends RegularLevel {

	{
		color1 = 0x6a723d;
		color2 = 0x88924c;
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_PRISON;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
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
		return Patch.generate(feeling == Feeling.CHASM ? 0.30f : 0.35f, 4);
	}

	protected boolean[] glass() {
		return Patch.generate( 0.35f, 4);
	}

	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ PoisonTrap.class, SpearTrap.class, ToxicTrap.class,
				AlarmTrap.class, FlashingTrap.class, GrippingTrap.class,
				ParalyticTrap.class, ConfusionTrap.class, FlockTrap.class,
				SummoningTrap.class, TeleportationTrap.class, BoundTrap.class, DewTrap.class,  KnowledgeTrap.class,
				FireDamageTrap.class, IceDamageTrap.class, ShockDamageTrap.class, EarthDamageTrap.class,
				LightDamageTrap.class, DarkDamageTrap.class};
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 4, 4, 4,
				3, 4, 3,
				2, 2, 1,
				2, 1, 5, 3, 1,
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
	protected void setPar(){
		Dungeon.pars[Dungeon.dungeondepth] = 400+(Dungeon.dungeondepth *50)+(secretDoors*20);
	}

	@Override
	protected void createItems() {
		if (Dungeon.hero.heroClass==HeroClass.PERFORMER){addItemToSpawn(new DungeonBomb());}
		
		super.createItems();

		Wandmaker.Quest.spawn(this, roomEntrance);
		spawnSkeleton(this);
	}

	public static void spawnSkeleton(PrisonLevel level) {
		if (Dungeon.dungeondepth == 9){

			MossySkeleton skeleton = new MossySkeleton();
			do {
				skeleton.pos = level.randomRespawnCell();
			} while (skeleton.pos == -1);
			level.mobs.add(skeleton);
			Actor.occupyCell(skeleton);
           
			//Dungeon.skeletonspawned = true;
		}
	}

	
	@Override
	protected void decorate() {

		for (int i = getWidth() + 1; i < getLength() - getWidth() - 1; i++) {
			if (map[i] == Terrain.EMPTY) {

				float c = 0.05f;
				if (map[i + 1] == Terrain.WALL
						&& map[i + getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i - 1] == Terrain.WALL
						&& map[i + getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i + 1] == Terrain.WALL
						&& map[i - getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}
				if (map[i - 1] == Terrain.WALL
						&& map[i - getWidth()] == Terrain.WALL) {
					c += 0.2f;
				}

				if (Random.Float() < c) {
					map[i] = Terrain.EMPTY_DECO;
				}
			}
		}

		for (int i = 0; i < getWidth(); i++) {
			if (map[i] == Terrain.WALL
					&& (map[i + getWidth()] == Terrain.EMPTY || map[i + getWidth()] == Terrain.EMPTY_SP)
					&& Random.Int(6) == 0) {

				map[i] = Terrain.WALL_DECO;
			}
		}

		for (int i = getWidth(); i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.WALL
					&& map[i - getWidth()] == Terrain.WALL
					&& (map[i + getWidth()] == Terrain.EMPTY || map[i + getWidth()] == Terrain.EMPTY_SP)
					&& Random.Int(3) == 0) {

				map[i] = Terrain.WALL_DECO;
			}
		}

		for (int i = getWidth(); i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.WALL && feeling == Feeling.SPECIAL_FLOOR && Floor.insideMap(i)) {

				map[i] = Terrain.GLASS_WALL;
			}
		}


		//while (true) {
		//	int pos = roomEntrance.random();
		//	if (pos != entrance) {
		//		map[pos] = Terrain.SIGN;
		//		break;
		//	}
		//}
		
		setPar();
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName( tile );
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
				return super.tileDesc( tile );
		}
	}

	@Override
	public void addVisuals(Scene scene) {
		super.addVisuals(scene);
		addVisuals(this, scene);
	}

	public static void addVisuals(Floor level, Scene scene) {
		for (int i = 0; i < getLength(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				scene.add(new Torch(i));
			}
		}
	}

	private static class Torch extends Emitter {

		private int pos;

		public Torch(int pos) {
			super();

			this.pos = pos;

			PointF p = DungeonTilemap.tileCenterToWorld(pos);
			pos(p.x - 1, p.y + 3, 2, 0);

			pour(FlameParticle.FACTORY, 0.15f);

			add(new Halo(16, 0xFFFFCC, 0.2f).point(p.x, p.y));
		}

		@Override
		public void update() {
			if (visible = Dungeon.visible[pos]) {
				super.update();
			}
		}
	}
}