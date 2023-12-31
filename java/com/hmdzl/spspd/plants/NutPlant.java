package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.fruit.Cherry;
import com.hmdzl.spspd.items.food.fruit.Strawberry;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.weapon.missiles.arrows.NutFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

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
		if(Random.Int(5)==1){
			if(Random.Int(2)==1){
				Dungeon.depth.drop(new Strawberry(), pos).sprite.drop();
			} Dungeon.depth.drop(new Cherry(), pos).sprite.drop();
		} else Dungeon.depth.drop(new Nut(), pos).sprite.drop();


		Dungeon.depth.drop(new NutVegetable(), pos).sprite.drop();
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_DUNGEONNUT;

			plantClass = NutPlant.class;
			explantClass = ExNutPlant.class;
			alchemyClass = PotionOfMending.class;
        }
	}
	public static class ExNutPlant extends Plant {
		{
			image = 17;
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
				Dungeon.depth.drop(new NutFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}
