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
import com.hmdzl.spspd.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class GoldDragonEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;
		{
		//name = "GoldDragon egg";
		image = ItemSpriteSheet.GOLD_DRAGONEGG;

		stackable = false;
		}

		public int moves = 5000;
		public int burns = 20;
		public int freezes = 20;
		public int poisons = 20;
		public int lits = 20;
		public int summons = 20;
		public int light = 20;

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {
			if (Dungeon.getMonth()==9 || Random.Int(50) == 0){
				BugDragon pet = new BugDragon();
				eggHatch(pet);
			} else {
				GoldDragon pet = new GoldDragon();
				eggHatch(pet);
			}
		  hero.next();
		
		} else {

			super.execute(hero, action);

		}
		
	}
	@Override
	public int price() {
		return 500 * quantity;
	}

}
