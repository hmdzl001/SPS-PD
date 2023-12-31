package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.sprites.ItemSpriteSheet;

/**
 * Created by dachhack on 10/15/2015.
 */
public class NoomlinCrown extends Artifact {

	{
		//name = "AlienBag";
		image = ItemSpriteSheet.CROWN;
		//level = 1;
		levelCap = 1;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new crown();
	}

	public class crown extends ArtifactBuff {

	}

	//@Override
	//public boolean isIdentified() {
	//	return true;
	//}

	//@Override
//	public boolean isUpgradable() {
	//	return false;
	//}

	@Override
	public int price() {
		return 100 * quantity;
	}

}
