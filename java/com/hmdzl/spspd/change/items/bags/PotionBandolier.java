package com.hmdzl.spspd.change.items.bags;

import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.medicine.Pill;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

/**
 * Created by debenhame on 05/02/2015.
 */
public class PotionBandolier extends Bag {

	{
		//name = "potion bandolier";
		image = ItemSpriteSheet.BANDOLIER;

		size = 22;
	}

	@Override
	public boolean grab(Item item) {
		return item instanceof Potion ||
				item instanceof Pill ||
				item instanceof Bomb;
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
