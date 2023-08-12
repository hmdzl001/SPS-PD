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
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer2;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.levels.traps.BoundTrap;
import com.hmdzl.spspd.levels.traps.ConfusionTrap;
import com.hmdzl.spspd.levels.traps.ExplosiveTrap;
import com.hmdzl.spspd.levels.traps.FlashingTrap;
import com.hmdzl.spspd.levels.traps.FlockTrap;
import com.hmdzl.spspd.levels.traps.GrippingTrap;
import com.hmdzl.spspd.levels.traps.GuardianTrap;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.levels.traps.ParalyticTrap;
import com.hmdzl.spspd.levels.traps.PitfallTrap;
import com.hmdzl.spspd.levels.traps.PoisonTrap;
import com.hmdzl.spspd.levels.traps.RockfallTrap;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.levels.traps.SummoningTrap;
import com.hmdzl.spspd.levels.traps.TeleportationTrap;
import com.hmdzl.spspd.levels.traps.VenomTrap;
import com.hmdzl.spspd.levels.traps.WarpingTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.DarkBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.DarkBuffTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.EarthBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.EarthBuffTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.FireBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.FireBuffTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.IceBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.IceBuffTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.LightBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.LightBuffTrap;
import com.hmdzl.spspd.levels.traps.bufftrap.ShockBuff2Trap;
import com.hmdzl.spspd.levels.traps.bufftrap.ShockBuffTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.EarthDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.IceDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.ShockDamage2Trap;
import com.hmdzl.spspd.levels.traps.damagetrap.ShockDamageTrap;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Scene;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

public class CavesLevel extends RegularLevel {

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;

		viewDistance = 6;
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_CAVES;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_CAVES;
	}

	@Override
	protected boolean[] water() {
		return Patch.generate(feeling == Feeling.WATER ? 0.60f : 0.45f, 6);
	}

	@Override
	protected boolean[] grass() {
		return Patch.generate(feeling == Feeling.GRASS ? 0.55f : 0.35f, 3);
	}

	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{ VenomTrap.class, ExplosiveTrap.class, FlashingTrap.class,
				            GrippingTrap.class, ParalyticTrap.class,RockfallTrap.class,
						    ConfusionTrap.class, FlockTrap.class, GuardianTrap.class, SummoningTrap.class,
				            TeleportationTrap.class, WarpingTrap.class, BoundTrap.class,
				FireBuff2Trap.class, IceBuff2Trap.class, ShockBuff2Trap.class, EarthBuff2Trap.class,
				LightBuff2Trap.class, DarkBuff2Trap.class};
	}

	@Override
	protected float[] trapChances() {
		return new float[]{ 4, 4, 4,
				      2, 3, 4,
				1, 1, 2, 2,
				1, 1, 3,
				3, 3, 3, 3,
				3, 3};
	}	
	
	@Override
	protected void setPar(){
		Dungeon.pars[Dungeon.depth] = 300+(Dungeon.depth*50)+(secretDoors*20);
	}

	@Override
	protected void createItems() {
		
		if (Dungeon.depth == 12){
			Tinkerer2 npc = new Tinkerer2();
			do {
				npc.pos = randomRespawnCell();
			} while (npc.pos == -1 || heaps.get(npc.pos) != null);
			mobs.add(npc);
			Actor.occupyCell(npc);
		}
		
		if (Dungeon.depth == 11){
			addItemToSpawn(new Mushroom());
		}

		if (Dungeon.hero.heroClass==HeroClass.PERFORMER && Random.Int(3) == 0){addItemToSpawn(new DungeonBomb());}
		super.createItems();
	}

	@Override
	protected void createMobs() {
		int nMobs = nMobs();
		for (int i = 0; i < nMobs; i++) {
			Mob mob = Bestiary.mob(Dungeon.depth);
			do {
				mob.pos = randomRespawnCell();
				mob.originalgen=true;
				Buff.affect(mob,ShieldArmor.class).level(Dungeon.depth*5);
				Buff.affect(mob,MagicArmor.class).level(Dungeon.depth*5);
			} while (mob.pos == -1);
			mobs.add(mob);
			Actor.occupyCell(mob);
		}
	}


	@Override
	protected boolean assignRoomType() {
		super.assignRoomType();

        return Blacksmith.Quest.spawn(rooms) || Dungeon.depth != 14;
    }

	@Override
	protected void decorate() {

		for (Room room : rooms) {
			if (room.type != Room.Type.STANDARD) {
				continue;
			}

			if (room.width() <= 3 || room.height() <= 3) {
				continue;
			}

			int s = room.square();

			if (Random.Int(s) > 8) {
				int corner = (room.left + 1) + (room.top + 1) * getWidth();
				if (map[corner - 1] == Terrain.WALL
						&& map[corner - getWidth()] == Terrain.WALL) {
					map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int(s) > 8) {
				int corner = (room.right - 1) + (room.top + 1) * getWidth();
				if (map[corner + 1] == Terrain.WALL
						&& map[corner - getWidth()] == Terrain.WALL) {
					map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int(s) > 8) {
				int corner = (room.left + 1) + (room.bottom - 1) * getWidth();
				if (map[corner - 1] == Terrain.WALL
						&& map[corner + getWidth()] == Terrain.WALL) {
					map[corner] = Terrain.WALL;
				}
			}

			if (Random.Int(s) > 8) {
				int corner = (room.right - 1) + (room.bottom - 1) * getWidth();
				if (map[corner + 1] == Terrain.WALL
						&& map[corner + getWidth()] == Terrain.WALL) {
					map[corner] = Terrain.WALL;
				}
			}

			for (Room n : room.connected.keySet()) {
				if ((n.type == Room.Type.STANDARD || n.type == Room.Type.TUNNEL)
						&& Random.Int(3) == 0) {
					Painter.set(this, room.connected.get(n), Terrain.EMPTY_DECO);
				}
			}
		}

		for (int i = getWidth() + 1; i < getLength() - getWidth(); i++) {
			if (map[i] == Terrain.EMPTY) {
				int n = 0;
				if (map[i + 1] == Terrain.WALL) {
					n++;
				}
				if (map[i - 1] == Terrain.WALL) {
					n++;
				}
				if (map[i + getWidth()] == Terrain.WALL) {
					n++;
				}
				if (map[i - getWidth()] == Terrain.WALL) {
					n++;
				}
				if (Random.Int(6) <= n) {
					map[i] = Terrain.EMPTY_DECO;
				}
			}
		}

		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.WALL && Random.Int(8) == 0) {
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

		if (Dungeon.bossLevel(Dungeon.depth + 1)) {
			return;
		}

		for (Room r : rooms) {
			if (r.type == Type.STANDARD) {
				for (Room n : r.neigbours) {
					if (n.type == Type.STANDARD && !r.connected.containsKey(n)) {
						Rect w = r.intersect(n);
						if (w.left == w.right && w.bottom - w.top >= 5) {

							w.top += 2;
							w.bottom -= 1;

							w.right++;

							Painter.fill(this, w.left, w.top, 1, w.height(),
									Terrain.CHASM);

						} else if (w.top == w.bottom && w.right - w.left >= 5) {

							w.left += 2;
							w.right -= 1;

							w.bottom++;

							Painter.fill(this, w.left, w.top, w.width(), 1,
									Terrain.CHASM);
						}
					}
				}
			}
		}
		
		
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.GRASS:
			return Messages.get(CavesLevel.class, "grass_name");
		case Terrain.HIGH_GRASS:
			return Messages.get(CavesLevel.class, "high_grass_name");
		case Terrain.WATER:
			return Messages.get(CavesLevel.class, "water_name");
		default:
			return super.tileName(tile);
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.ENTRANCE:
			return Messages.get(CavesLevel.class, "entrance_desc");
		case Terrain.EXIT:
			return Messages.get(CavesLevel.class, "exit_desc");
		case Terrain.HIGH_GRASS:
			return Messages.get(CavesLevel.class, "high_grass_desc");
		case Terrain.WALL_DECO:
			return Messages.get(CavesLevel.class, "wall_deco_desc");
		case Terrain.BOOKSHELF:
			return Messages.get(CavesLevel.class, "bookshelf_desc");
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
				scene.add(new Vein(i));
			}
		}
	}

	private static class Vein extends Group {

		private int pos;

		private float delay;

		public Vein(int pos) {
			super();

			this.pos = pos;

			delay = Random.Float(2);
		}

		@Override
		public void update() {

			if (visible = Dungeon.visible[pos]) {

				super.update();

				if ((delay -= Game.elapsed) <= 0) {

					delay = Random.Float();

					PointF p = DungeonTilemap.tileToWorld(pos);
					((Sparkle) recycle(Sparkle.class)).reset(
							p.x + Random.Float(DungeonTilemap.SIZE), p.y
									+ Random.Float(DungeonTilemap.SIZE));
				}
			}
		}
	}

	public static final class Sparkle extends PixelParticle {

		public void reset(float x, float y) {
			revive();

			this.x = x;
			this.y = y;

			left = lifespan = 0.5f;
		}

		@Override
		public void update() {
			super.update();

			float p = left / lifespan;
			size((am = p < 0.5f ? p * 2 : (1 - p) * 2) * 2);
		}
	}
}