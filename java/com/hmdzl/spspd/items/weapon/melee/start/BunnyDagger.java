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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class BunnyDagger extends MeleeWeapon {
	{
		
		image = ItemSpriteSheet.BUNNY_DAGGER;
		usesTargeting = true;
	}

	public BunnyDagger() {
		super(1, 1.2f, 1f, 1);
		MIN = 5;
		MAX = 10;
		unique = true;
		reinforced = true;
		cursed = true;
	}

	@Override
	public Item uncurse(){
		//cursed=false;
		return this;
	}		
	
	@Override
	public Item upgrade(boolean enchant) {
		MIN += 1;
		MAX += 1;
		return super.upgrade(enchant);
	}

	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
        int exdmg = Dungeon.hero.damageRoll();
		defender.damage(Random.Int(exdmg/2,exdmg), this);

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	
	}

}
