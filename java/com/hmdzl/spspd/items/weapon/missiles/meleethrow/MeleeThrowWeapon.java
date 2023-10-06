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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.messages.Messages;

import java.util.ArrayList;

public class MeleeThrowWeapon extends MissileWeapon {

	{
		//name = "incendiary dart";
		stackable = false;
		levelKnown = false;
	}

	private int tier;

	public MeleeThrowWeapon(int tier) {
		super();

		this.tier = tier;

		STR = typicalSTR();

		MIN = min();
		MAX = max();		
	}	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	
		actions.add(AC_EQUIP);
		//actions.add(AC_UNEQUIP);
		
		return actions;
	}


	@Override
	public boolean isUpgradable() {
		return true;
	}

	//@Override
	//public boolean isIdentified() {
	//	return false;
	//}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	private int min() {
		return tier + 4;
	}

	private int max() {
		return tier * tier - tier + 10;
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}	

	@Override
	public Item upgrade(boolean enchant) {
		MIN += 2;
		MAX += 1 + tier ;
		super.upgrade(enchant);

		updateQuickslot();
		return this;
	}

	@Override
	public Item degrade() {
		MIN -= 2;
		MAX -= 1 + tier;
		return super.degrade();
	}
	@Override
	public String info() {
		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX, STR);
			if (Dungeon.hero.STR() > typicalSTR()) {
				info += " " + Messages.get(MeleeWeapon.class, "excess_str", Dungeon.hero.STR() - typicalSTR());
			}
		} else {
			info += "\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(), max(), typicalSTR());
		}

		//String stats_desc = Messages.get(this, "stats_desc");

		//if (!stats_desc.equals("")) info += "  " + stats_desc;
		//Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX,STR,ACU,DLY,RCH )

		if (enchantment != null) {
			info += "\n" + Messages.get(MeleeWeapon.class, "enchanted", enchantment.desc());
		}

		if (reinforced) {
			info += "\n" + Messages.get(Item.class, "reinforced");
		}
		
		if (unique) {
			info += "\n\n" +  Messages.get(Item.class, "unique");
		}	

		if (levelKnown && STR() > Dungeon.hero.STR()) {
			info += "\n" + Messages.get(MeleeWeapon.class, "too_heavy");
		}

		if (cursed && isEquipped(Dungeon.hero)) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed");
		}
		return info;
	}

	@Override
	public int price() {
		int price = 50 ;
		if (enchantment != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}
}
