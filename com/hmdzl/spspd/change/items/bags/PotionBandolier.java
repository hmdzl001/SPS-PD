package com.hmdzl.spspd.change.items.bags;

import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.messages.Messages;

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
		return item instanceof Potion ;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

}
