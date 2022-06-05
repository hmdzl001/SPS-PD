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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;

public class  HolyWater extends MeleeWeapon {

	{
		//name = "holywater";
		image = ItemSpriteSheet.HOLY_WATER;

		 
	}

	public HolyWater() {
		super(3, 0.8f, 1.2f, 1);
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
	public Item upgrade(boolean enchant) {		
		if (ACU < 1.2f) {
			ACU+=0.05f;
		}
		
		if (ACU > 1.2f && DLY > 1f){
			DLY-=0.05f;
		}
	
		MIN+=1;


		return super.upgrade(enchant);
    }

	@Override
	public int damageRoll(Hero hero) {

		int damage = super.damageRoll(hero);
		 if (charge >= 14) {
			damage = 5*damage;
		 }
		return Math.round(damage);
	}
		
	
	
   @Override
    public void proc(Char attacker, Char defender, int damage) {

	   int DMG = damage;
 
        if (charge >= 14) {
			attacker.HP += Math.min((int)(damage/5),attacker.HT-attacker.HT);
			charge = 0;
		}
	   
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
		
		charge++;
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
	
	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(Weapon.class, "charge",charge,14);
		return info;
	}
}
