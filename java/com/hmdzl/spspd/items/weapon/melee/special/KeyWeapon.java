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
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class KeyWeapon extends MeleeWeapon {

	{
		//name = "keyweapon";
		image = ItemSpriteSheet.OLD_KEY;
		 
	}

	public KeyWeapon() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 10;
	}
	
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {
		int exdmg = Dungeon.hero.damageRoll();
        if (Random.Int(100) < 40) {
			Buff.prolong(defender, Paralysis.class, 2);
		}

		if (Random.Int(100) < 40) {
		defender.damage(Random.Int(exdmg/2,exdmg/4*3), this,3);
		}
		
	    if (Random.Int(100) < 40) {
			Buff.affect(defender, Charm.class,5f).object = attacker.id();
			Buff.affect(defender, Terror.class,5f).object = attacker.id();
			Buff.affect(defender, Amok.class,5f);
		}				
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	

	}
    		
}
