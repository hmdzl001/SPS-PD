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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class TempestBoomerang extends MissileWeapon {
	
	{
		//name = "boomerang";
		image = ItemSpriteSheet.TEMPEST_B;

		STR = 10;

		MIN = 1;
		MAX = 2;
		
		stackable = false;
		unique = true;
		//reinforced = true;

		 
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		return actions;
	}

	@Override
	public Item upgrade(boolean enchant) {
		
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
		Buff.affect(defender, WatchOut.class);
		super.proc(attacker, defender, damage);
	}

	@Override
	public String desc() {
		String info = super.desc();

		//if (spammo != null){
		//	info += "\n" + Messages.get(GunWeapon.class, "ammo_add") + Messages.get(spammo,"name") ;
		//}

		//if(reinforced){
		//	info += "\n\n" + Messages.get(Item.class, "reinforced");
       // }
		
		return info;
	}
}
