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
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Seedpod extends Plant{

	{
		image = 13;
	}

	@Override
	public void activate(Char ch) {
        super.activate(ch);
  	
		int nSeeds = Random.NormalIntRange(1, 5);

		ArrayList<Integer> candidates = new ArrayList<Integer>();
		for (int i : Floor.NEIGHBOURS8){
			if (Floor.passable[pos+i]){
				candidates.add(pos+i);
			}
		}

		for (int i = 0; i < nSeeds && !candidates.isEmpty(); i++){
			Integer c = Random.element(candidates);
			Dungeon.depth.drop(Generator.random(Generator.Category.SEED), c).sprite.drop(pos);
			candidates.remove(c);
		}

	}


	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SEEDPOD;
	
			plantClass = Seedpod.class;
			explantClass = ExSeedpod.class;
			alchemyClass = PotionOfMixing.class;
		}
	}
	public static class ExSeedpod extends Plant {
		{
			image = 13;
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

			for (int i = 0; i < 3 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.depth.drop(Generator.random(Generator.Category.BERRY), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}


