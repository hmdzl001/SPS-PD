package com.hmdzl.spspd.change.plants;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.food.Blandfruit;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

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
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_BLANDFRUIT;

			plantClass = BlandfruitBush.class;
			alchemyClass = null;
		}
	}
}
