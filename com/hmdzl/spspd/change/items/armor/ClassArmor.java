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
package com.hmdzl.spspd.change.items.armor;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Bundle;

abstract public class ClassArmor extends Armor {

    private static final String AC_SPECIAL = "SPECIAL";
	
	{
		levelKnown = true;
		cursedKnown = true;
		defaultAction = AC_SPECIAL;

		bones = false;
	}

	private int DR;
	
	public ClassArmor() {
		super(7);
	}

	public static ClassArmor upgrade(Hero owner, Armor armor) {

		ClassArmor classArmor = null;

		switch (owner.heroClass) {
		case WARRIOR:
			classArmor = new WarriorArmor();
			break;
		case ROGUE:
			classArmor = new RogueArmor();
			break;
		case MAGE:
			classArmor = new MageArmor();
			break;
		case HUNTRESS:
			classArmor = new HuntressArmor();
			break;
		}

		classArmor.STR = armor.STR;
		classArmor.DR = armor.DR;

		classArmor.inscribe(armor.glyph);

		return classArmor;
	}

	private static final String ARMOR_STR = "STR";
	private static final String ARMOR_DR = "DR";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ARMOR_STR, STR);
		bundle.put(ARMOR_DR, DR);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		STR = bundle.getInt(ARMOR_STR);
		DR = bundle.getInt(ARMOR_DR);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (hero.HP >= ((hero.HT/3)*2) && isEquipped(hero) && !Dungeon.sokobanLevel(Dungeon.depth)) {
			actions.add(AC_SPECIAL);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_SPECIAL)) {

			if (hero.HP < ((hero.HT/3)*2) ){
				GLog.w(Messages.get(this, "low_hp"));
			} else if (!isEquipped(hero)) {
				GLog.w(Messages.get(this, "not_equipped"));
			} else {
				curUser = hero;
				Invisibility.dispel();
				doSpecial();
			}

		} else {
			super.execute(hero, action);
		}
	}

	abstract public void doSpecial();
	
	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 0;
	}
}
