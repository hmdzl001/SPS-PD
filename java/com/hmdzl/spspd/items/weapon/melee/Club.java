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
package com.hmdzl.spspd.items.weapon.melee;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class Club extends MeleeWeapon {

	{
		//name = "Club";
		image = ItemSpriteSheet.CLUB;
	}

	public Club() {
		super(4, 1f, 1f, 1);
		MIN = 28;
		MAX = 40;
	}
	
	@Override
	public Item upgrade(boolean enchant) {
		
		if (ACU < 1.50f){
		ACU+=0.05f;
		}

		MIN+=3;
		MAX+=1;
		
		return super.upgrade(enchant);
    }
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
	
        if (Random.Int(100) < 15) {
			Buff.prolong(defender, Paralysis.class, 2);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
		if (durable() && attacker == Dungeon.hero){
			durable --;
            if (durable == 10){
                GLog.n(Messages.get(KindOfWeapon.class,"almost_destory"));
            }
			if (durable == 0){
				Dungeon.hero.belongings.weapon = null;
				GLog.n(Messages.get(KindOfWeapon.class,"destory"));
			}
		}		
	}
}