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
package com.hmdzl.spspd.items.armor.normalarmor;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.specialarmor.AsceticArmor;
import com.hmdzl.spspd.items.armor.specialarmor.FollowerArmor;
import com.hmdzl.spspd.items.armor.specialarmor.HuntressArmor;
import com.hmdzl.spspd.items.armor.specialarmor.MageArmor;
import com.hmdzl.spspd.items.armor.specialarmor.PerformerArmor;
import com.hmdzl.spspd.items.armor.specialarmor.RogueArmor;
import com.hmdzl.spspd.items.armor.specialarmor.SoldierArmor;
import com.hmdzl.spspd.items.armor.specialarmor.WarriorArmor;
import com.hmdzl.spspd.messages.Messages;

import java.text.DecimalFormat;


public class NormalArmor extends Armor {

	private int tier;
	public NormalArmor(int tier, float dex, float ste, int eng) {
		super();

		this.tier = tier;

		DEX = dex;
		STE = ste;
		ENG = eng;

		STR = typicalSTR();

		MIN = min();
		MAX = max();
	}

	private int min() {
		return 0;
	}

	private int max() {
		return 8*tier - 4;
	}



	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	@Override
	public Item upgrade(boolean hasglyph) {
		MIN +=1;
		MAX +=3;
		return super.upgrade(hasglyph);
	}	
	
	public Item safeUpgrade() {
		return upgrade(glyph != null);
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
			info += "\n\n" + Messages.get(NormalArmor.class, "stats_known", tier, MIN, MAX, STR);
				info += "\n\n" + Messages.get(NormalArmor.class, "stats_known2",new DecimalFormat("#.##").format(DEX), new DecimalFormat("#.##").format(STE), ENG);
          if (Dungeon.hero.STR() > typicalSTR()){
				info += " " + Messages.get(NormalArmor.class, "excess_str", Dungeon.hero.STR() - typicalSTR());
			}
				} else {
			info += "\n\n" + Messages.get(NormalArmor.class, "stats_unknown", tier, min(), max(), typicalSTR());
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;

		if (glyph != null) {
			info += "\n\n" +  Messages.get(NormalArmor.class, "inscribed",glyph.desc());
		}

		if (reinforced) {
			info += "\n\n" +  Messages.get(Item.class, "reinforced");
		}
		
		if (unique) {
			info += "\n\n" +  Messages.get(Item.class, "unique");
		}		

		if (levelKnown && STR() > Dungeon.hero.STR()) {
			info += "\n\n" + Messages.get(NormalArmor.class, "too_heavy");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(NormalArmor.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(NormalArmor.class, "cursed");
		}

		return info;
	}

	@Override
	public int price() {
		int price = 100;
		if (glyph != null) {
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

	public static NormalArmor upgrade(Hero owner) {

		NormalArmor normalArmor = null;

		switch (owner.heroClass) {
		case WARRIOR:
			normalArmor = new WarriorArmor();
			break;
		case ROGUE:
			normalArmor = new RogueArmor();
			break;
		case MAGE:
			normalArmor = new MageArmor();
			break;
		case HUNTRESS:
			normalArmor = new HuntressArmor();
			break;
		case PERFORMER:
			normalArmor = new PerformerArmor();
			break;
		case SOLDIER:
			normalArmor = new SoldierArmor();
			break;
		case FOLLOWER:
			normalArmor = new FollowerArmor();
			break;	
		case ASCETIC:
			normalArmor = new AsceticArmor();
			break;				
		}
		return normalArmor;
	}		
	
	}