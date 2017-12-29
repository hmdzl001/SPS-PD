package com.hmdzl.spspd.change.plants;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

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
			Buff.affect(ch, Vertigo.class, Vertigo.duration(ch));
		}
	}


	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_STORMVINE;

			plantClass = Stormvine.class;
			alchemyClass = PotionOfLevitation.class;
		}
	}
}
