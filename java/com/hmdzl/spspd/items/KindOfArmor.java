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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class KindOfArmor extends EquipableItem {

	protected static final float TIME_TO_EQUIP = 1f;

	public boolean magical = false;

	public int MIN = 0;
	public int MAX = 1;

	public int M_MIN = 0;
	public int M_MAX = 1;

	public int tier;

	//public KindOfArmor( int tier ) {
		//this.tier = tier;
	//}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(isEquippedSecond(hero) ? AC_UNEQUIP_SECOND : (isEquipped(hero)? AC_UNEQUIP : AC_EQUIP)  );
		return actions;
	}

	@Override
	public boolean isEquipped(Hero hero) {
		return hero.belongings.armor == this || hero.belongings.armor_two == this;
	}


	public boolean isEquippedSecond(Hero hero) {
		return hero.belongings.armor_two == this;
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
		//	((HeroSprite) hero.sprite).updateArmor();
			
			//hero.remove(buff);
			//buff = null;
			
			return true;

		} else {

			return false;

		}
	}

	@Override
	public boolean doUnequipSecond(Hero hero, boolean collect, boolean single) {
		if (super.doUnequipSecond(hero, collect, single)) {

			hero.belongings.armor_two = null;
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

	public int magicdrRoll(Hero owner) {
		return Random.NormalIntRange(M_MIN, M_MAX);
	}

}
