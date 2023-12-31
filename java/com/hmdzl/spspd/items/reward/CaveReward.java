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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class CaveReward extends Item {

	public static final String AC_USE = "USE";

	public static final float TIME_TO_USE = 1;
	private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing(0xFFFFFF);

	@Override
	public ItemSprite.Glowing glowing() {
		return WHITE;
	}
	{
		//name = "reward";
		image = ItemSpriteSheet.ITEM_BAG;

		stackable = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);

			Moonberry berry1 = new Moonberry(10);
			Dungeon.depth.drop(berry1, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Cloudberry berry2 = new Cloudberry(10);
			Dungeon.depth.drop(berry2, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Blueberry berry3 = new Blueberry(10);
			Dungeon.depth.drop(berry3, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			Blackberry berry4 = new Blackberry(10);
			Dungeon.depth.drop(berry4, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			
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
		return 10 * quantity;
	}
}
