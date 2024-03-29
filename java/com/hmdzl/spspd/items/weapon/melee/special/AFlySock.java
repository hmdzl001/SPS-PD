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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class AFlySock extends MeleeWeapon {

	{
		//name = "";
		image = ItemSpriteSheet.AFLY_SOCK;
		 
		//usesTargeting = true;
	}

	public AFlySock() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 5;
	}
	

	@Override
	public Item upgrade(boolean enchant) {
		
		return super.upgrade(enchant);
    }
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

		switch (Random.Int(4)) {
			case 0:
				Buff.affect(defender,Paralysis.class,3f);
				break;
			case 1:
				Buff.affect(defender,Charm.class,3f).object = attacker.id();
				break;
			case 2:
				Buff.affect(defender,Terror.class,3f).object = attacker.id();;
				break;
			case 3:
				Buff.affect(defender,Amok.class,3f);
				break;
				default:GLog.i("nothing happened");
					break;
		}
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }		
}
