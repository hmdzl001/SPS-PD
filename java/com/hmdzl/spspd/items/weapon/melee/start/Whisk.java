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
package com.hmdzl.spspd.items.weapon.melee.start;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

public class Whisk extends MeleeWeapon {

	{
		//name = "Whisk";
		image = ItemSpriteSheet.WHISK;
		 
	}

	public Whisk() {
		super(3, 1f, 1f, 2);
	}
	
	public static int charge = 0;
	private static final String CHARGE = "charge";
	
	public static int chargeex = 0;
	private static final String CHARGEEX = "chargeex";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
		bundle.put(CHARGEEX, chargeex);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
		chargeex = bundle.getInt(CHARGEEX);
	}		
		
    @Override
    public void proc(Char attacker, Char defender, int damage) {

        charge++;
		
		if (charge > 10) {
	    int oppositeDefender = defender.pos + (defender.pos - attacker.pos);
		Ballistica trajectory = new Ballistica(defender.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(defender, trajectory, 1);
		Buff.prolong(defender, Vertigo.class,3f);
		charge = 0;
		chargeex ++;
		}
		
		if (chargeex > 4){
			Item loot = ((Mob) defender).SupercreateLoot();
			Dungeon.depth.drop(loot, defender.pos).sprite.drop();
			((Mob) defender).firstitem = false;
			chargeex = 0;
		}
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	

	}
    	
	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(Weapon.class, "charge",charge,10);
		
		info += "\n\n" + Messages.get(this, "chargeex",chargeex,5);
		return info;
	}
		
}
