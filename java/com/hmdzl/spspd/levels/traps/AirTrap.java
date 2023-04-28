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
package com.hmdzl.spspd.levels.traps;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

public class AirTrap {

	public static void trigger( int pos, Char ch ) {
		
		if (ch != null) {
			int damage = Random.NormalIntRange(Dungeon.depth, Dungeon.depth*2);
			//ch.damage(damage,Bleeding.class);
			Buff.affect( ch, Bleeding.class).set(damage);
			Buff.affect( ch, Cripple.class,5f);
			Wound.hit( ch );
			if (ch == Dungeon.hero && !ch.isAlive()) {
				Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
				//GLog.n("You bled to death...");
			}
		} else {
			Wound.hit( pos );
		}
		Dungeon.hero.next();
	}

}
