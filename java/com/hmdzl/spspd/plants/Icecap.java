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
import com.hmdzl.spspd.actors.blobs.Freezing;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.items.food.vegetable.IceMint;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.weapon.missiles.arrows.IceFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Icecap extends Plant {

	{
		image = 1;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		PathFinder.buildDistanceMap(pos, BArray.not(Floor.losBlockLow, null), 1);

		Fire fire = (Fire) Dungeon.depth.blobs.get(Fire.class);

		//GameScene.add(Blob.seed(pos, 5, FrostCloud.class));

		for (int i = 0; i < Floor.getLength(); i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Freezing.affect(i, fire);
			}
		}

		Dungeon.depth.drop(new IceMint(), pos).sprite.drop();

	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_ICECAP;

			plantClass = Icecap.class;
			explantClass = ExIcecap.class;
			alchemyClass = PotionOfFrost.class;
		}
	}
	
	public static class ExIcecap extends Plant {
		{
			image = 20;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			Dungeon.depth.drop(new IceMint(), pos).sprite.drop();

			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Floor.NEIGHBOURS8){
				if (Floor.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 3 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.depth.drop(new IceFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}
}
