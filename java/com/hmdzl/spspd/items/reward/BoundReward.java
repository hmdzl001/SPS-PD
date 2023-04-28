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
package com.hmdzl.spspd.items.reward;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BoundReward extends Item {

	public static final String AC_WEAPON = "WEAPON";
	public static final String AC_FOOD = "FOOD";
	public static final String AC_POTION = "POTION";

	public static final float TIME_TO_USE = 1;

	{
		//name = "reward";
		image = ItemSpriteSheet.ITEM_BAG;

		stackable = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_WEAPON);
		actions.add(AC_FOOD);
		actions.add(AC_POTION);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_WEAPON)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);

			Item item1 = Generator.random(Random.oneOf(Generator.Category.MELEEWEAPON,
					Generator.Category.ARMOR, Generator.Category.RING,
					Generator.Category.ARTIFACT, Generator.Category.WAND));
			Dungeon.level.drop(item1, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
			detach(hero.belongings.backpack);

		} else if (action.equals(AC_FOOD)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);

			Item item21 = Generator.random(Random.oneOf(Generator.Category.HIGHFOOD,
					Generator.Category.FOOD, Generator.Category.SEED,
					Generator.Category.SEED4,Generator.Category.GOLD));
			Item item22 = Generator.random(Random.oneOf(Generator.Category.HIGHFOOD,
					Generator.Category.FOOD, Generator.Category.SEED,
					Generator.Category.SEED4,Generator.Category.GOLD));
			Item item23 = Generator.random(Random.oneOf(Generator.Category.HIGHFOOD,
					Generator.Category.FOOD, Generator.Category.SEED,
					Generator.Category.SEED4,Generator.Category.GOLD));
			Dungeon.level.drop(item21, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(item22, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(item23, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

			detach(hero.belongings.backpack);

		} else if (action.equals(AC_POTION)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);


			Item item31 = Generator.random(Random.oneOf(Generator.Category.POTION,
					Generator.Category.PILL, Generator.Category.MUSHROOM,
					Generator.Category.SCROLL, Generator.Category.BOMBS));
			Item item32 = Generator.random(Random.oneOf(Generator.Category.HIGHFOOD,
					Generator.Category.FOOD, Generator.Category.SEED,
					Generator.Category.SEED4, Generator.Category.BOMBS));
			Dungeon.level.drop(item31, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Dungeon.level.drop(item32, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

			detach(hero.belongings.backpack);

		} else {
			super.execute(hero, action);

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
	public int price() {
		return 100 * quantity;
	}
}
