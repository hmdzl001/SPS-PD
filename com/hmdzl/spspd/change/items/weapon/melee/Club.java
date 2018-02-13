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
package com.hmdzl.spspd.change.items.weapon.melee;

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.actors.Char;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;

public class Club extends MeleeWeapon {

	{
		//name = "Club";
		image = ItemSpriteSheet.CLUB;
	}

	public Club() {
		super(4, 1f, 1f, 1);
	}
	
	@Override
	public Item upgrade(boolean enchant) {
		
		if (ACU < 1.50f){
		ACU+=0.05f;
		}

		MIN+=1;
		MAX+=1;
		
		return super.upgrade(enchant);
    }
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
	
        if (Random.Int(100) < 15) {
			Buff.affect(defender, Paralysis.class, 3);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
	}
}