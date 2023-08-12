package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.items.weapon.missiles.arrows.GlassFruit;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by Evan on 13/08/2014.
 */
public class SiOtwoFlower extends Plant {

	{
		image = 18;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			Buff.affect(ch, GlassShield.class).turns(2);
		}

		Dungeon.level.drop(new NutVegetable(), pos).sprite.drop();
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SIOFLOWER;

			plantClass = SiOtwoFlower.class;
			explantClass = ExSiOtwoFlower.class;
			alchemyClass = PotionOfShield.class;
        }
	}
	public static class ExSiOtwoFlower extends Plant {
		{
			image = 18;
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
				Dungeon.level.drop(new GlassFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}
