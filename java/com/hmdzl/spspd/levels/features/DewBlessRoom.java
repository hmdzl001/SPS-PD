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
package com.hmdzl.spspd.levels.features;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;

public class DewBlessRoom {

	public static void trample(Level level, int pos, Char ch) {

		if (ch instanceof Hero) {

            Buff.affect(Dungeon.hero, Dewcharge.class,720f);
			GLog.h(Messages.get(DewBlessRoom.class, "order"), Dungeon.pars[Dungeon.depth]);
			CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
			Dungeon.observe();
			Level.set(pos, Terrain.GRASS);
			GameScene.updateMap(pos);
			//Dungeon.dewWater = true;
		} else {
			CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
			Dungeon.observe();
		}

	}
}