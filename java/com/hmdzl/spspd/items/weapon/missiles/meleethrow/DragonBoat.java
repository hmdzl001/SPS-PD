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
package com.hmdzl.spspd.items.weapon.missiles.meleethrow;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class DragonBoat extends MeleeThrowWeapon {

	{
		//name = "Dragon Boat";
		image = ItemSpriteSheet.BOAT;
		 
		usesTargeting = true;
	}

	public DragonBoat() {
		super(1);
		MIN = 5;
		MAX = 10;
	}
	
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

        if (Random.Int(100) < 40) {
			Buff.prolong(defender, Paralysis.class, 3f);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
		if (Random.Int(100) ==1) {
			this.isdestory = true ;
			if (Dungeon.hero.belongings.weapon == this) {
			    Dungeon.hero.belongings.weapon = null;
			}
			Buff.affect(defender, Bleeding.class).set(50);
			GLog.n(Messages.get(KindOfWeapon.class,"destory"));
		}		
	}
    		
}
