/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.items;

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Gold extends Item {

	private static final String TXT_COLLECT = "Collect gold coins to spend them later in a shop.";
	private static final String TXT_INFO = "A pile of %d gold coins. "
			+ TXT_COLLECT;
	private static final String TXT_INFO_1 = "One gold coin. " + TXT_COLLECT;
	private static final String TXT_VALUE = "%+d";

	{
		//name = "gold";
		image = ItemSpriteSheet.GOLD;
		stackable = true;
	}

	public Gold() {
		this(1);
	}

	public Gold(int value) {
		this.quantity = value;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		return new ArrayList<String>();
	}

	@Override
	public boolean doPickUp(Hero hero) {

		Dungeon.gold += quantity;
		Statistics.goldCollected += quantity;
		Badges.validateGoldCollected();

		if(Dungeon.skins == 4 && Dungeon.hero.heroClass == HeroClass.WARRIOR){
			Dungeon.gold += (int)(quantity*0.2);
			Statistics.goldCollected += quantity;
		}

		MasterThievesArmband.Thievery thievery = hero
				.buff(MasterThievesArmband.Thievery.class);
		GoldTouch goldtouch = hero
				.buff(GoldTouch.class);
		if (thievery != null && goldtouch == null)
			thievery.collect(quantity);

		GameScene.pickUp(this);
		hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, quantity);
		hero.spendAndNext(TIME_TO_PICK_UP);

		Sample.INSTANCE.play(Assets.SND_GOLD, 1, 1, Random.Float(0.9f, 1.1f));

		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public Item random() {
		quantity = Random.Int(30 + Dungeon.depth * 10, 60 + Dungeon.depth * 20);
		return this;
	}

	private static final String VALUE = "value";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(VALUE, quantity);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		quantity = bundle.getInt(VALUE);
	}
}
