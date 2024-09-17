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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class HandLight extends MeleeWeapon {


	{
		//name = "HandLight";
		image = ItemSpriteSheet.HAND_LIGHT;
	}

	public HandLight() {
		super(4, 1f, 1f, 2);
		MIN = 29;
		MAX = 38;
	}

	public Item upgrade(boolean enchant) {
		
		MIN+=1;
        MAX+=1;
		super.upgrade(enchant);
		return this;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		if(defender.buff(ShieldArmor.class) != null) {
			defender.damage(damage,attacker,1);
		} else if(defender.buff(MagicArmor.class) != null){
			defender.damage(damage,attacker,2);
		} else if (defender.buff(EnergyArmor.class) != null){
			defender.damage(damage,attacker,2);
		}
		if (defender.buff(Silent.class) != null) {
			defender.damage((int) (damage * 0.5),attacker,2);
		} else {
			Buff.affect(defender, Silent.class, 3f);
		}
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);
		}
	}

}