package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blandfruit;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by Evan on 13/08/2014.
 */
public class BlandfruitBush extends Plant {

	{
		image = 8;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Dungeon.level.drop(new Blandfruit(), pos).sprite.drop();
		switch (Random.Int(4)){
			case 0:
				Dungeon.level.drop(new Blackberry(), pos).sprite.drop();
				break;
			case 1:
				Dungeon.level.drop(new Blueberry(), pos).sprite.drop();
				break;
			case 2:
				Dungeon.level.drop(new Cloudberry(), pos).sprite.drop();
				break;
			case 3:
				Dungeon.level.drop(new Moonberry(), pos).sprite.drop();
				break;
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_BLANDFRUIT;

			plantClass = BlandfruitBush.class;
			explantClass = ExBlandfruitBush.class;
			alchemyClass = null;
		}
	}
	public static class ExBlandfruitBush extends Plant {
		{
			image = 8;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Level.NEIGHBOURS8){
				if (Level.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 2 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.level.drop(new Blandfruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}
