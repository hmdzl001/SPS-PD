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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class XiXiBox extends MeleeWeapon {

	{
		//name = "XiXiBox";
		image = ItemSpriteSheet.XIXI_BOX;
		 
		usesTargeting = true;
	}

	public XiXiBox() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 10;
	}
	
	public static int charge = 0;
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
    public void proc(Char attacker, Char defender, int damage) {
		
		if (damage > 7) {
			charge++;
		}


        if (Random.Int(100) < 40) {
			Buff.prolong(defender, Paralysis.class, 4);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
		if (charge > 100 ){

			Dungeon.hero.belongings.weapon = null;
			GLog.n(Messages.get(KindOfWeapon.class,"destory"));
			Dungeon.depth.drop(Generator.random(Generator.Category.OLDWEAPON), defender.pos).sprite.drop();
			Dungeon.depth.drop(Generator.random(Generator.Category.ARMOR), defender.pos).sprite.drop();
			Dungeon.depth.drop(Generator.random(Generator.Category.ARTIFACT), defender.pos).sprite.drop();
			Dungeon.depth.drop(Generator.random(Generator.Category.RING), defender.pos).sprite.drop();

		}		
	}
    		
}
