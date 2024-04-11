package com.hmdzl.spspd.items.food.fruit;


import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

/**
 * Created by debenhame on 12/08/2014.
 */
public class Blandfruit extends Fruit {

	public Item potionAttrib = null;
	public ItemSprite.Glowing potionGlow = null;

	{
		//name = "Blandfruit";
		stackable = true;
		image = ItemSpriteSheet.BLANDFRUIT;
		energy = 100;
		hornValue = 2; // only applies when blandfruit is cooked

	}

	//@Override
	//public boolean isSimilar(Item item) {
	//	if (item instanceof Blandfruit) {
	//		if (potionAttrib == null) {
     //           return ((Blandfruit) item).potionAttrib == null;
	//		} else if (((Blandfruit) item).potionAttrib != null) {
    //            return ((Blandfruit) item).potionAttrib.getClass() == potionAttrib
     //                   .getClass();
	//		}
	//	}
	//	return false;
	//}

	//@Override
	//public void execute(Hero hero, String action) {
	//	if (action.equals(AC_EAT)) {


	//	} else {
	//		super.execute(hero, action);
	//	}
	//}

	@Override
	public int price() {
		return 20 * quantity;
	}

}
