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
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.HolyStun;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class Lollipop extends MeleeWeapon {

	{
		//name = "Lollipop";
		image = ItemSpriteSheet.LOLLIPOP;
		 
		usesTargeting = true;
	}

	public Lollipop() {
		super(1, 1f, 1f, 1);
		MIN = 50;
		MAX = 50;
	}
	
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

        if (Random.Int(100) < 40) {
			Buff.affect(defender, Tar.class);
		}

        if (Random.Int(100) < 60) {
			Buff.affect(defender, Charm.class,5f).object = attacker.id();
		}		
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}	
		if (Random.Int(50) == 1 ){
            Buff.prolong(attacker, HolyStun.class, 5f);
			Buff.prolong(attacker, STRdown.class, 20f);
            attacker.HT -= Math.min(15,attacker.HT-1);
			Dungeon.hero.belongings.weapon = null;
			GLog.n(Messages.get(KindOfWeapon.class,"destory"));
		}		
	}
    		
}
