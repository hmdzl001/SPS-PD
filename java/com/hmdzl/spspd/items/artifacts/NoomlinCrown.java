package com.hmdzl.spspd.items.artifacts;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.EnergyArmor;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndIronMaker;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

/**
 * Created by dachhack on 10/15/2015.
 */
public class NoomlinCrown extends Artifact {

	{
		//name = "AlienBag";
		image = ItemSpriteSheet.CROWN;
		level = levelCap = 1;

	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new crown();
	}

	public class crown extends ArtifactBuff {

	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public int price() {
		return 100 * quantity;
	}

}
