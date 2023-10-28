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
package com.hmdzl.spspd.items.weapon.ranges;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.MeleePan;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RangePan extends RangeWeapon {

	{
		//name = "GunA";
		image = ItemSpriteSheet.RICE;
		image2 = ItemSpriteSheet.STEAK;
		//MIN=10;
		//MAX=10;
	}

	public RangePan() {
		super(1,1f);
	}

	@Override
    public void proc(Char attacker, Char defender, int damage) {

        if (Random.Int(100) < 30) {
			Buff.affect(defender, Burning.class).set(5f);
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}		
	}
	
	public static final String AC_CHANGE = "CHANGE";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	    if (isEquipped(hero)) {
	       actions.add(AC_CHANGE);
		}
		return actions;
	}	
	
	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_CHANGE)){
			Weapon weapon = (Weapon) hero.belongings.weapon;
			MeleeWeapon n;
			n = new MeleePan();
			n.level = 0;
			int level = weapon.level;
				if (level > 0) {n.upgrade(level);
			} else if (level < 0) {n.degrade(-level);}
				n.enchantment = weapon.enchantment;
				n.reinforced = weapon.reinforced;
				n.levelKnown = weapon.levelKnown;
				n.cursedKnown = weapon.cursedKnown;
				n.cursed = weapon.cursed;
				hero.belongings.weapon = n;

	    }  else {

			super.execute(hero, action);

		}
	}	

}
