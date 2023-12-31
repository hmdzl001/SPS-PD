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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.StrBottle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Rotberry extends Plant {

	{
		image = 7;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		GameScene.add(Blob.seed(pos, 100, ToxicGas.class));

		Dungeon.depth.drop(new Seed(), pos).sprite.drop();

		if (ch != null) {
			Buff.prolong(ch, Roots.class,3);
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_ROTBERRY;

			plantClass = Rotberry.class;
			explantClass = ExRotberry.class;
			alchemyClass = StrBottle.class;
		}
	}
	public static class ExRotberry extends Plant {
		{
			image = 7;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			Dungeon.depth.drop(new Rotberry.Seed(), pos).sprite.drop();
			
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Floor.NEIGHBOURS8){
				if (Floor.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 1 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.depth.drop(new Gold(1), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}