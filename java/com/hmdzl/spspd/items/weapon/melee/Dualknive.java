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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;


public class Dualknive extends MeleeWeapon {

	{
		//name = "Dual Knive";
		image = ItemSpriteSheet.DKNIVE;
	}

	public Dualknive(){
		super(2, 1f, 1f, 1);
		MIN = 11;
		MAX = 17;
	}

	@Override
	public Item upgrade(boolean enchant) {
		
		if (ACU < 1.2f) {
			ACU+=0.05f;
		}
		
		if (ACU > 1.2f && DLY > 0.8f){
			DLY-=0.05f;
		}
		
		if (DLY < 0.8f && RCH < 2){
			RCH++;
		}
		
		MIN+=1;
        MAX+=1;
		
		return super.upgrade(enchant);
    }

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		int exdmg = Dungeon.hero.damageRoll();

		defender.damage(Random.Int(exdmg/4,exdmg/2), this);
		
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
