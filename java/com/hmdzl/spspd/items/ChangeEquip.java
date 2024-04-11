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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.FloatingText2;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ChangeEquip extends Item {

	{
		//name = "gold";
		image = ItemSpriteSheet.EQUIP_CHANGE;
		stackable = true;
		defaultAction = AC_CHANGE;
	}

	public static final String AC_CHANGE = "CHANGE";


	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		actions.add(AC_CHANGE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_CHANGE)) {
			if (hero.belongings.weapon_two == null && hero.belongings.weapon !=null) {
				hero.belongings.weapon_two = hero.belongings.weapon;
                hero.belongings.weapon = null;
			} else if (hero.belongings.weapon == null && hero.belongings.weapon_two != null){
				hero.belongings.weapon = hero.belongings.weapon_two;
				hero.belongings.weapon_two = null;
				hero.belongings.weapon.activate(hero);
			} else if  (hero.belongings.weapon != null && hero.belongings.weapon_two != null){
				Weapon weapon1 = (Weapon) hero.belongings.weapon_two;
				hero.belongings.weapon_two = hero.belongings.weapon;
				hero.belongings.weapon = weapon1;
				hero.belongings.weapon.activate(hero);
			}

			if (hero.belongings.armor_two == null && hero.belongings.armor !=null) {
				hero.belongings.armor_two = hero.belongings.armor;
				hero.belongings.armor = null;
			} else if (hero.belongings.armor == null && hero.belongings.armor_two != null){
				hero.belongings.armor = hero.belongings.armor_two;
				hero.belongings.armor_two = null;
				hero.belongings.armor.activate(hero);
			} else if  (hero.belongings.armor != null && hero.belongings.armor_two != null){
				Armor armor1 = (Armor) hero.belongings.armor_two;
				hero.belongings.armor_two = hero.belongings.armor;
				hero.belongings.armor = armor1;
				hero.belongings.armor.activate(hero);
			}
			hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "change"));
			//hero.belongings.armor_two = hero.belongings.armor;
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	@Override
	public String info() {
		String name = name();

		String info = desc();



		return info;
	}
}
