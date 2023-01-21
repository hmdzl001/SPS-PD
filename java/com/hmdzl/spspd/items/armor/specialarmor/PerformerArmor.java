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
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PerformerArmor extends NormalArmor {

	{
		//name = "phantom armor";
		image = ItemSpriteSheet.ARMOR_PERFORMER;
		STR -= 1;
		MAX = 10;
		MIN = 0;
	}

	public PerformerArmor() {
		super(2,4f,12f,3);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(8) == 0) {
			Buff.affect(attacker,Charm.class,3f).object=defender.id();
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
	}
}