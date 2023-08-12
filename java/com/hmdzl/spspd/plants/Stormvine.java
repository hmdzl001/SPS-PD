package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.items.weapon.missiles.arrows.ShockFruit;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by Evan on 23/10/2014.
 */
public class Stormvine extends Plant {

	{
		image = 9;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			Buff.affect(ch, Vertigo.class, 10f);
			Buff.affect(ch, Shocked.class).level( 10);
		}
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			heap.shockhit();
		}
	}


	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_STORMVINE;

			plantClass = Stormvine.class;
			explantClass = ExStormvine.class;
			alchemyClass = PotionOfLevitation.class;
		}
	}
	
	public static class ExStormvine extends Plant {
		{
			image = 9;
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

			for (int i = 0; i < 3 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.level.drop(new ShockFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
}
