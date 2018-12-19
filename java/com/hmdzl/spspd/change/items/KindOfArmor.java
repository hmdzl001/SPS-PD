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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class KindOfArmor extends EquipableItem {

	protected static final float TIME_TO_EQUIP = 1f;

	public int MIN = 0;
	public int MAX = 1;
	public int tier;

	//public KindOfArmor( int tier ) {
		//this.tier = tier;
	//}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.armor == this;
	}

	//@Override
	public boolean doEquip(Hero hero) {

		detachAll(hero.belongings.backpack);

		if (hero.belongings.armor == null
				|| hero.belongings.armor.doUnequip(hero, true)) {

			hero.belongings.armor = this;
			activate(hero);

			cursedKnown = true;
			if (cursed) {
				equipCursed(hero);
				GLog.n(Messages.get(KindOfArmor.class, "cursed", name()));
			
			}

			((HeroSprite) hero.sprite).updateArmor();
			
			hero.spendAndNext(TIME_TO_EQUIP);
			return true;

		} else {

			collect(hero.belongings.backpack);
			return false;
		}
	}
	
	//@Override
	//public void activate(Char ch) {
		//buff = buff();
		//buff.attachTo(ch);
//	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)) {

			hero.belongings.armor = null;
			((HeroSprite) hero.sprite).updateArmor();
			
			//hero.remove(buff);
			//buff = null;
			
			return true;

		} else {

			return false;

		}
	}

	public void activate(Hero hero) {
	}

	public int drRoll(Hero owner) {
		return Random.NormalIntRange(MIN, MAX);
	}

	public float dexterityFactor(Hero hero) {
		return 1f;
	}

	public float stealthFactor(Hero hero) {
		return 1f;
	}

	public int energyFactor( Hero hero ){
		return 1;
	}

    public void proc(Char attacker, Char defender, int damage) {
	}
}
