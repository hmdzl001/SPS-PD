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

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.Sentinel;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.DwarfHammer;
import com.hmdzl.spspd.change.items.Torch;
import com.hmdzl.spspd.change.levels.traps.*;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class HallsLevel extends RegularLevel {

	{
		minRoomSize = 6;

		viewDistance = Math.max(25 - Dungeon.depth, 1);

		color1 = 0x801500;
		color2 = 0xa68521;
	}
		
	@Override
	public void create() {
		addItemToSpawn(new Torch());
		super.create();
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_HALLS;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_HALLS;
	}
	
	@Override
	protected void setPar(){
		Dungeon.pars[Dungeon.depth] = 200+(Dungeon.depth*50)+(secretDoors*20);
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(feeling == Feeling.WATER ? 0.55f : 0.40f, 6);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(feeling == Feeling.GRASS ? 0.55f : 0.30f, 3);
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ BlazingTrap.class, DisintegrationTrap.class, FrostTrap.class, SpearTrap.class, VenomTrap.class, StormTrap.class,
				ExplosiveTrap.class, GrippingTrap.class, LightningTrap.class, OozeTrap.class, WeakeningTrap.class,
				CursingTrap.class, FlockTrap.class, GrimTrap.class, GuardianTrap.class, SummoningTrap.class, TeleportationTrap.class,
				DisarmingTrap.class, DistortionTrap.class};
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 8, 8, 8, 8, 8, 8,
				4, 4, 4, 4, 4,
				2, 2, 2, 2, 2, 2,
				1, 1 };
	}	

	@Override
	protected void decorate() {

		for (int i = getWidth() + 1; i < getLength() - getWidth() - 1; i++) {
			if (map[i] == Terrain.EMPTY) {

				int count = 0;
				for (int j = 0; j < NEIGHBOURS8.length; j++) {
					if ((Terrain.flags[map[i + NEIGHBOURS8[j]]] & Terrain.PASSABLE) > 0) {
						count++;
					}
				}

				if (Random.Int(80) < count) {
					map[i] = Terrain.EMPTY_DECO;
				}

			} else if (map[i] == Terrain.WALL
					&& map[i - 1] != Terrain.WALL_DECO
					&& map[i - getWidth()] != Terrain.WALL_DECO
					&& Random.Int(20) == 0) {

				map[i] = Terrain.WALL_DECO;

			}
		}

		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance) {
				map[pos] = Terrain.SIGN;
				break;
			}
		}
		
         for (int i = 0; i < getLength(); i++) {
			
			if (map[i]==Terrain.EXIT){map[i] = Terrain.PEDESTAL; 
			     sealedlevel=true;
			    if(Dungeon.depth==24){
			    	Sentinel sentinel = new Sentinel();
				    sentinel.pos = i;
				    mobs.add(sentinel);}
			}			
			
		}
         
         setPar();
		
		
	}
	
	@Override
	protected void createItems() {
		if (Dungeon.depth!=25){addItemToSpawn(new DwarfHammer());}
		super.createItems();
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_name");
			case Terrain.GRASS:
				return Messages.get(HallsLevel.class, "grass_name");
			case Terrain.HIGH_GRASS:
				return Messages.get(HallsLevel.class, "high_grass_name");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(HallsLevel.class, "water_desc");
			case Terrain.STATUE:
			case Terrain.STATUE_SP:
				return Messages.get(HallsLevel.class, "statue_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(HallsLevel.class, "bookshelf_desc");
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
			if (level.map[i] == 63) {
				scene.add(new Stream(i));
			}
		}
	}

	private static class Stream extends Group {

		private int pos;

		private float delay;

		public Stream(int pos) {
			super();

			this.pos = pos;

			delay = Random.Float(2);
		}

		@Override
		public void update() {

			if (visible = Dungeon.visible[pos]) {

				super.update();

				if ((delay -= Game.elapsed) <= 0) {

					delay = Random.Float(2);

					PointF p = DungeonTilemap.tileToWorld(pos);
					((FireParticle) recycle(FireParticle.class)).reset(p.x
							+ Random.Float(DungeonTilemap.SIZE),
							p.y + Random.Float(DungeonTilemap.SIZE));
				}
			}
		}

		@Override
		public void draw() {
			GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
			super.draw();
			GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}
	}

	public static class FireParticle extends PixelParticle.Shrinking {

		public FireParticle() {
			super();

			color(0xEE7722);
			lifespan = 1f;

			acc.set(0, +80);
		}

		public void reset(float x, float y) {
			revive();

			this.x = x;
			this.y = y;

			left = lifespan;

			speed.set(0, -40);
			size = 4;
		}

		@Override
		public void update() {
			super.update();
			float p = left / lifespan;
			am = p > 0.8f ? (1 - p) * 5 : 1;
		}
	}
}
