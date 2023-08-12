/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.watabou.utils.Random;

public class GrippingTrap extends Trap {

	{
		color = TrapSprite.GREY;
		shape = TrapSprite.DIAMOND;
	}

	@Override
	public void activate(Char ch) {
		//super.activate(ch);
		Char c = Actor.findChar( pos );

		if (c != null) {
			int damage = Math.max( 0,  (Dungeon.depth) - Random.IntRange( 0, c.drRoll() / 2 ) );
			Buff.affect( c, Bleeding.class ).set( damage );
			Buff.prolong( c, Cripple.class, 15f);
			Buff.prolong( c, Roots.class, 5f);
			Wound.hit( c );
		} else {
			Wound.hit( pos );
		}
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {heap.earthhit();}

	}
}
