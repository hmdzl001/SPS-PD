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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

import java.text.DecimalFormat;

public class MeleeWeapon extends Weapon {

	private int tier;
	

	public MeleeWeapon(int tier, float acu, float dly, int rch /*, int dur*/) {
		super();

		this.tier = tier;

		ACU = acu;
		DLY = dly;
		RCH = rch;
		//DUR = 10;

		STR = typicalSTR();

		MIN = min();
		MAX = max();		
	}

	private int min() {
		return tier + 5;
	}

	private int max() {
		return (int) (((tier * tier - tier + 12) / ACU * DLY )/ (0.8 + 0.2*RCH) );
	}
	
	/*@Override
	public void b(Char attacker, Char defender, int damage) {
	
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
		
	}*/
	
	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	
	public Item upgrade(boolean enchant) {
		MIN++;
		MAX += 1 + tier/2;
		durable += 10;

		return super.upgrade(enchant);
	}


	public Item safeUpgrade() {
		return upgrade(enchantment != null);
	}

    public Item destory() {
        return Dungeon.hero.belongings.weapon = null;
    }

	@Override
	public Item degrade() {
		return super.degrade();
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}

	@Override
	public String info() {
		String name = name();
		
        String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX, STR);
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known2",new DecimalFormat("#.##").format(ACU), new DecimalFormat("#.##").format(DLY), RCH);
            if (Dungeon.hero.STR() > typicalSTR()){
				info += " " + Messages.get(MeleeWeapon.class, "excess_str", Dungeon.hero.STR() - typicalSTR());
			}
		} else {
			info += "\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(), max(), typicalSTR());
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= " " + stats_desc;
		
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

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "cursed");
		}
		
		return info;
	}

	@Override
	public int price() {
		int price = 80 ;
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

	@Override
	public Item random() {
		super.random();

		if (Random.Int(5 + level) == 0) {
			enchant();
		}

		return this;
	}
}
