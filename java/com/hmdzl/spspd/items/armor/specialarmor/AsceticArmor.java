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
package com.hmdzl.spspd.items.armor.specialarmor;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class AsceticArmor extends NormalArmor {

	{
		//name = "phantom armor";
		image = ItemSpriteSheet.ARMOR_ASCETIC;
		STR -= 1;
		MAX = 15;
		MIN = 0;
	}

	public AsceticArmor() {
		super(3,3.5f,11f,4);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(8) == 0) {
			Buff.affect(defender,HasteBuff.class,10f);
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
	}
}