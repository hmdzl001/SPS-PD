package com.hmdzl.spspd.items.bags;

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bombs.Bomb;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.medicine.Pill;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

/**
 * Created by debenhame on 05/02/2015.
 */
public class PotionBandolier extends Bag {

	{
		//name = "potion bandolier";
		image = ItemSpriteSheet.BANDOLIER;

		size = 25;
	}

	@Override
	public boolean grab(Item item) {
        return item instanceof Potion ||
                item instanceof Pill ||
                item instanceof Bomb ||
                item instanceof Egg;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	@Override
	public boolean doPickUp( Hero hero ) {

		return hero.belongings.getItem( PotionBandolier.class ) == null && super.doPickUp( hero ) ;

	}

}
