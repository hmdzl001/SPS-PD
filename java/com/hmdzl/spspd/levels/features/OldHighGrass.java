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
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.levels.Floor;

public class OldHighGrass {

	public static void trample(Floor level, int pos, Char ch) {

		// Barkskin
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN) {	
			Buff.affect(ch, Invisibility.class,4f);
		}	
	
		CellEmitter.get(pos).burst(LeafParticle.LEVEL_SPECIFIC, 4);
		Dungeon.observe();
	}
}
