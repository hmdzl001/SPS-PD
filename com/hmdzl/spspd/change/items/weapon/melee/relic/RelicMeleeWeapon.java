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
package com.hmdzl.spspd.change.items.weapon.melee.relic;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.DwarfKingTomb;
import com.hmdzl.spspd.change.actors.mobs.MineSentinel;
import com.hmdzl.spspd.change.actors.mobs.Otiluke;
import com.hmdzl.spspd.change.actors.mobs.Zot;
import com.hmdzl.spspd.change.actors.mobs.ZotPhase;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.artifacts.Artifact.ArtifactBuff;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.Weapon.Enchantment;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class RelicMeleeWeapon extends Weapon {

	private int tier;
	
	private static final float TIME_TO_EQUIP = 1f;
	
	public Buff passiveBuff;
	protected Buff activeBuff;

	// level is used internally to track upgrades to artifacts, size/logic
	// varies per artifact.
	// already inherited from item superclass
	// exp is used to count progress towards levels for some artifacts
	protected int exp = 0;
	// levelCap is the artifact's maximum level
	protected int levelCap = 0;

	// the current artifact charge
	public int charge = 0;
	
	// the maximum charge, varies per artifact, not all artifacts use this.
	public int chargeCap = 0;

	// used by some artifacts to keep track of duration of effects or cooldowns
	// to use.
	protected int cooldown = 0;
	
	public RelicMeleeWeapon(int tier, float acu, float dly, int rch) {
		super();

		this.tier = tier;

		ACU = acu;
		DLY = dly;
		RCH = rch;

		STR = typicalSTR();

		MIN = min();
		MAX = max();
        reinforced = true;
		
	}

	private int min() {
		return tier;
	}

	private int max() {
		return (int) (((tier * tier - tier + 10) / ACU * DLY)/(0.8+0.2*RCH));
	}

	@Override
	public boolean doEquip(Hero hero) {

			activate(hero);
			
			return super.doEquip(hero);

	}

		
	@Override
	public void activate(Hero hero) {
		passiveBuff = passiveBuff();
		passiveBuff.attachTo(hero);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		
		if (super.doUnequip(hero, collect, single)) {

			if (passiveBuff != null){			
			  passiveBuff.detach();
			  passiveBuff = null;
			}

			hero.belongings.weapon = null;
			return true;

		} else {

			return false;

		}
	}

		
	protected WeaponBuff passiveBuff() {
		return null;
	}

	public class WeaponBuff extends Buff {

		public int level() {
			return level;
		}

		public boolean isCursed() {
			return cursed;
		}

	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
			
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}
	
	
	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	@Override
	public Item upgrade(boolean enchant) {

		MIN++;
		MAX += 3;

		if (enchant){
			GLog.i(Messages.get(this,"refuse"));
		}
		return super.upgrade(false);
		
	}

	public Item safeUpgrade() {
		return upgrade(enchantment != null);
	}

	
	@Override
	public Item degrade() {
		STR++;
		MIN--;
		MAX -= tier;
		return super.degrade();
	}

	public int typicalSTR() {
		return 8 + tier * 2;
	}

	@Override
	public String info() {

        String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX, STR);
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known2", ACU, DLY, RCH);
			if (typicalSTR() > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > typicalSTR()){
				info += " " + Messages.get(MeleeWeapon.class, "excess_str", Dungeon.hero.STR() - typicalSTR());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(), max(), typicalSTR());
			if (typicalSTR() > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;
		
		//Messages.get(MeleeWeapon.class, "stats_known", tier, MIN, MAX,STR,ACU,DLY,RCH )
		
		
		switch (imbue) {
		case LIGHT:
			info += " " + Messages.get(Weapon.class, "lighter");
			break;
		case HEAVY:
			info += " " + Messages.get(Weapon.class, "heavier");
			break;
		case NONE:
		}

		if (enchantment != null) {
			info += " " + Messages.get(Weapon.class, "enchanted");
		}
		
		if (reinforced) {
			info += " " + Messages.get(Weapon.class, "reinforced");
		}

		if (charge>=chargeCap) {
			info += " " + Messages.get(RelicMeleeWeapon.class, "fullcharge");
		} else {
			info += " " + Messages.get(RelicMeleeWeapon.class, "charge", charge, chargeCap);
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
		int price = 20 * (1 << (tier - 1));
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

		if (Random.Int(10 + level) == 0) {
			enchant();
		}

		return this;
	}
	
	private static final String CHARGE = "charge";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);		
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);		
	}
}


