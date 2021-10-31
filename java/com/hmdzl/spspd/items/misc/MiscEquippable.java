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
package com.hmdzl.spspd.items.misc;

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindofMisc;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.utils.GLog;

public class MiscEquippable extends KindofMisc {


	private static final float TIME_TO_EQUIP = 1f;
	private static final String TXT_TO_STRING = "%s";
	private static final String TXT_TO_STRING_LVL = "%s%+d";
	protected Buff buff;
	
	public MiscEquippable() {
		super();
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {

		if (hero.belongings.misc1 != null && hero.belongings.misc2 != null && hero.belongings.misc3 != null) {

			GLog.w(Messages.get(Artifact.class, "onlythree"));
			return false;

		} else {

			if (hero.belongings.misc1 == null) {
				hero.belongings.misc1 = this;
			} else if (hero.belongings.misc2 == null){
				hero.belongings.misc2 = this;
			} else {
				hero.belongings.misc3 = this;
			}

			detach(hero.belongings.backpack);

			activate(hero);

			cursedKnown = true;
			if (cursed) {
				equipCursed(hero);
				GLog.n(Messages.get(Artifact.class, "cursed_worn"));
			}

			hero.spendAndNext(TIME_TO_EQUIP);
			return true;

		}

	}

	@Override
	public void activate(Char ch) {
		buff = buff();
		buff.attachTo(ch);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {

			if (hero.belongings.misc1 == this) {
				hero.belongings.misc1 = null;
			} else if (hero.belongings.misc2 == this){
				hero.belongings.misc2 = null;
			} else {
				hero.belongings.misc3 = null;
			}

			hero.remove(buff);
			buff = null;

			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.misc1 == this || hero.belongings.misc2 == this || hero.belongings.misc3 == this ;
	}
	
	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	@Override
	public Item upgrade() {

		super.upgrade();

		if (buff != null) {

			Char owner = buff.target;
			buff.detach();
			if ((buff = buff()) != null) {
				buff.attachTo(owner);
		    }
		}

		return this;
	}

	@Override
	public int visiblyUpgraded() {
		return level;
	}	

	@Override
	public String info() {
		if (cursed && cursedKnown && !isEquipped(Dungeon.hero)) {

			return desc() + "\n\n" + Messages.get(Artifact.class, "curse_known");

		} else {

			return desc();
		}
	}

	@Override
	public String toString() {
		if (levelKnown && level != 0) {
				return Messages.format(TXT_TO_STRING_LVL, name(),
						visiblyUpgraded());
		} else {
				return Messages.format(TXT_TO_STRING, name());
			}
	}	

	@Override
	public String status() {
		return null;
	}

	
	protected MiscBuff buff() {
		return null;
	}
	
	public static int getBonus(Char target, Class<?extends MiscBuff> type){
		int bonus = 0;
		for (MiscBuff buff : target.buffs(type)) {
			bonus += buff.level;
		}
		return bonus;
	}

	public class MiscBuff extends Buff {

		public int level;

		public MiscBuff() {
			level = MiscEquippable.this.level;
		}

		@Override
		public boolean attachTo(Char target) {
		
			return super.attachTo(target);
		}

		@Override
		public boolean act() {		

			spend(TICK);

			return true;
		}
	}
}
