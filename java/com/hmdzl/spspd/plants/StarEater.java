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
package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.UpgradeEatBall;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class StarEater extends Plant {

	{
		image = 15;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Dungeon.depth.drop(new UpgradeEatBall(), pos).sprite.drop();
		switch (Random.Int(4)){
			case 0:
				Dungeon.depth.drop(new Blackberry(), pos).sprite.drop();
				break;
			case 1:
				Dungeon.depth.drop(new Blueberry(), pos).sprite.drop();
				break;
			case 2:
				Dungeon.depth.drop(new Cloudberry(), pos).sprite.drop();
				break;
			case 3:
				Dungeon.depth.drop(new Moonberry(), pos).sprite.drop();
				break;
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_FLYTRAP;

			plantClass = StarEater.class;
			explantClass = ExStarEater.class;
			alchemyClass = PotionOfOverHealing.class;				
		}

	}
	public static class ExStarEater extends Plant {
		{
			image = 15;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Floor.NEIGHBOURS8){
				if (Floor.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 2 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.depth.drop(new UpgradeEatBall(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}
