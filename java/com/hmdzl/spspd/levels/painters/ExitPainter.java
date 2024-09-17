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
package com.hmdzl.spspd.levels.painters;

import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.ExProtect;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.mobs.BanditKing;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Plant;

import static com.hmdzl.spspd.Dungeon.shopOnLevel;

public class ExitPainter extends Painter {

	public static void paint(Floor level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.REGULAR);
		}

		//if ((Dungeon.hero.lvl > 14 + Dungeon.depth) && Dungeon.depth < 25 &&  !Dungeon.isChallenged(Challenges.TEST_TIME)){
		//	LevelChecker lc = new LevelChecker();
		//	lc.pos = room.random();
		//	level.mobs.add(lc);
		//	//Actor.occupyCell(lc);
		//}


		if(!shopOnLevel()) {
			Mob mob = Bestiary.exmob(Dungeon.dungeondepth);
		    mob.pos = room.random();
		   // mob.state = mob.HUNTING;
		    mob.originalgen=true;
		    Buff.affect(mob,ExProtect.class);
		    Buff.affect(mob,ShieldArmor.class).level(Dungeon.dungeondepth *5);
		    Buff.affect(mob,MagicArmor.class).level(Dungeon.dungeondepth *5);
			level.mobs.add(mob);
			//Actor.occupyCell(mob);
		}

		if(Dungeon.dungeondepth == 11) {
			Mob mob = new BanditKing();
			mob.pos = room.random();
			// mob.state = mob.HUNTING;
			mob.originalgen=false;
			Buff.affect(mob,ExProtect.class);
			Buff.affect(mob,ShieldArmor.class).level(Dungeon.dungeondepth *5);
			Buff.affect(mob,MagicArmor.class).level(Dungeon.dungeondepth *5);
			level.mobs.add(mob);
			//Actor.occupyCell(mob);
		}

		Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED);
		level.explant(seed, room.random());

		level.exit = room.random(1);
		if (Dungeon.isChallenged(Challenges.TEST_TIME)) {
			set(level, level.exit, Terrain.STATUE);
		} else {
			set(level, level.exit, Terrain.EXIT);
		}
		//int tent;
	//	do{tent = room.random();}
		//while (level.map[tent] == Terrain.EXIT || level.map[tent] == Terrain.STATUE );
		//level.map[tent] = Terrain.TENT;

		//level.tent=tent;
	}
}
