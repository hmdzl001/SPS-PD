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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DBurning;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;


public class DemonBlade extends MeleeWeapon {

	{
		//name = "D blade";
		image = ItemSpriteSheet.DEMON_BLADE;
	}

	public DemonBlade(){
		super(2, 1f, 1f, 1);
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
		
		MIN+=2;
        MAX+=2;
		
		return super.upgrade(enchant);
    }

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		int DMG = damage;

		defender.damage(Random.Int(DMG/4,DMG/2), this);

		damage = (int)(damage* (1 + 0.1 * Dungeon.hero.magicSkill()));
		
		if (Random.Int(8) == 0) {
			Buff.affect(defender,DBurning.class).reignite(defender);
		}
		
		if (Random.Int(3) == 0) {
			Dungeon.hero.spp++;
		}		
		
	    if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}		

}
