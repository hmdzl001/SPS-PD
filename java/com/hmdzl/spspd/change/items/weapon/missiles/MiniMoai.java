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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.MissileSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MiniMoai extends MissileWeapon {

	{
		//name = "MiniMoai";
		image = ItemSpriteSheet.MOAI;

		STR = 10;

		MIN = 1;
		MAX = 10;

		stackable = false;
		unique = true;

		 
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (!isEquipped(hero)) actions.add(AC_EQUIP);
		return actions;
	}	

	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 1;
		MAX += 1;
		super.upgrade(enchant);

		updateQuickslot();

		return this;
	}

	@Override
	public Item degrade() {

		return super.degrade();
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		if (Random.Int(10)> 7){
		Buff.prolong(defender, Charm.class, 3 ).object = attacker.id();}
		super.proc(attacker, defender, damage);
	}

	@Override
	public String desc() {
		String info = super.desc();

		if(reinforced){
			info += "\n\n" + Messages.get(Item.class, "reinforced");
		}		
		
		return info;
	}
	
	@Override
	public int price() {
		return quantity * 100;
	}	
}
