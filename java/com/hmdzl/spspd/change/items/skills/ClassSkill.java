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
package com.hmdzl.spspd.change.items.skills;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Bundle;

abstract public class ClassSkill extends Item {

    private static final String AC_SPECIAL = "SPECIAL";
	
	{
		defaultAction = AC_SPECIAL;
		unique = true;
		bones = false;
	}

	public static ClassSkill upgrade(Hero owner) {

		ClassSkill classSkill = null;

		switch (owner.heroClass) {
		case WARRIOR:
			classSkill = new WarriorSkill();
			break;
		case ROGUE:
			classSkill = new RogueSkill();
			break;
		case MAGE:
			classSkill = new MageSkill();
			break;
		case HUNTRESS:
			classSkill = new HuntressSkill();
			break;
		case PERFORMER:
			classSkill = new PerformerSkill();
			break;
		case SOLDIER:
			classSkill = new SoldierSkill();
			break;
		}

		return classSkill;
	}


	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (hero.HP >= ((hero.HT/3)*2) && !Dungeon.sokobanLevel(Dungeon.depth)) {
			actions.add(AC_SPECIAL);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_SPECIAL)) {

			if (hero.HP < ((hero.HT/3)*2) ){
				GLog.w(Messages.get(this, "low_hp"));
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
		return false;
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
