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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith2;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.watabou.utils.Random;

public class BlacksmithPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.TRAP);
		fill(level, room, 2, Terrain.EMPTY_SP);

		
		for (int i = 0; i < 2; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.TRAP);
			level.drop(Generator.random(Random.oneOf(Generator.Category.ARMOR,
					Generator.Category.MELEEWEAPON)), pos);
		}
		

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.UNLOCKED);
			drawInside(level, room, door, 1, Terrain.EMPTY);
		}

		Blacksmith npc = new Blacksmith();
		do {
			npc.pos = room.random(1);
		} while (level.heaps.get(npc.pos) != null);
		level.mobs.add(npc);
		Actor.occupyCell(npc);
		
		
		Blacksmith2 npc2 = new Blacksmith2();
		do {
			npc2.pos = room.random(1);
		} while (level.heaps.get(npc2.pos) != null || Actor.findChar(npc2.pos) != null);
		level.mobs.add(npc2);
		Actor.occupyCell(npc2);

		for(int cell : room.getCells()) {
			if (level.map[cell] == Terrain.TRAP){
				level.setTrap(new FireDamageTrap().reveal(), cell);
			}
		}
	}
}
