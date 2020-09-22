package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

/**
 * Created by Evan on 13/08/2014.
 */
public class NutPlant extends Plant {

	{
		image = 17;
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
			alchemyClass = PotionOfShield.class;;
		}
	}
}
