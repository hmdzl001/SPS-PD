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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RenBArmor extends NormalArmor {
 
	{
		//name = "cat";
		image = ItemSpriteSheet.BUNNY_ARMOR;
		MAX = 0;
		MIN = 0;
	}

	public RenBArmor() {
		super(1,1,1,1);
	}

	public int charge = 100;

	private static final String CHARGE = "charge";	

	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}

	@Override
	public Item upgrade(boolean hasglyph) {
		MIN-=1;
		MAX-=3;
		return super.upgrade(hasglyph);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {
		charge --;

		if (charge < 1 ) {
			Dungeon.hero.belongings.armor = null;
			Dungeon.depth.drop(new EasterEgg(), defender.pos).sprite.drop();
		}

		if (glyph != null) {
			glyph.proc(this, attacker, defender, damage);
		}
    }
}
