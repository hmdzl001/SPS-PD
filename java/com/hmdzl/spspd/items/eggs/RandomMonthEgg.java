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
package com.hmdzl.spspd.items.eggs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RandomMonthEgg extends Item {
	
	public static final float TIME_TO_USE = 1;

		{
		image = ItemSpriteSheet.RANDOWNEGG;

		stackable = true;
		}


	public static final String AC_USE = "USE";

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
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
			curUser = hero;
			if (Random.Int(10) == 0){
				Dungeon.level.drop(new RandomEasterEgg(), hero.pos).sprite.drop();
			} else {
				switch (Random.Int(12)) {

					case 0:
						Dungeon.level.drop(new RandomEgg1(), hero.pos).sprite.drop();
						break;
					case 1:
						Dungeon.level.drop(new RandomEgg2(), hero.pos).sprite.drop();
						break;
					case 2:
						Dungeon.level.drop(new RandomEgg3(), hero.pos).sprite.drop();
						break;
					case 3:
						Dungeon.level.drop(new RandomEgg4(), hero.pos).sprite.drop();
						break;
					case 4:
						Dungeon.level.drop(new RandomEgg5(), hero.pos).sprite.drop();
						break;
					case 5:
						Dungeon.level.drop(new RandomEgg6(), hero.pos).sprite.drop();
						break;
					case 6:
						Dungeon.level.drop(new RandomEgg7(), hero.pos).sprite.drop();
						break;
					case 7:
						Dungeon.level.drop(new RandomEgg8(), hero.pos).sprite.drop();
						break;
					case 8:
						Dungeon.level.drop(new RandomEgg9(), hero.pos).sprite.drop();
						break;
					case 9:
						Dungeon.level.drop(new RandomEgg10(), hero.pos).sprite.drop();
						break;
					case 10:
						Dungeon.level.drop(new RandomEgg11(), hero.pos).sprite.drop();
						break;
					case 11:
						Dungeon.level.drop(new RandomEgg12(), hero.pos).sprite.drop();
						break;
				}
			}
			detach(Dungeon.hero.belongings.backpack);
			curUser.spendAndNext(1f);
			curUser.busy();
		} else {

			super.execute(hero, action);

		}


	}
	@Override
	public int price() {
		return 500 * quantity;
	}

}
