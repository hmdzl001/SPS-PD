package com.hmdzl.spspd.change.plants;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.food.Nut;
import com.hmdzl.spspd.change.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.change.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.change.items.food.fruit.Blackberry;
import com.hmdzl.spspd.change.items.food.fruit.Blandfruit;
import com.hmdzl.spspd.change.items.food.fruit.Blueberry;
import com.hmdzl.spspd.change.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.change.items.food.fruit.Moonberry;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

/**
 * Created by Evan on 13/08/2014.
 */
public class NutPlant extends Plant {

	{
		image = 16;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Dungeon.level.drop(new Nut(), pos).sprite.drop();
		Dungeon.level.drop(new NutVegetable(), pos).sprite.drop();
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_DUNGEONNUT;

			plantClass = NutPlant.class;
			alchemyClass = null;
		}
	}
}
