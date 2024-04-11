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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.reward.BoundReward;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class RewardPaper extends Item {

	{
		//name = "RewardPaper";
		image = ItemSpriteSheet.REWARD_PC;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	private static final String AC_CHOOSE = "CHOOSE";

	public static final String AC_DOSP = "DOSP";
	public static final String AC_DOUP = "DOUP";
	public static final String AC_DORE = "DORE";
	public static final String AC_NEED = "NEED";
	public static final String AC_RANKUP = "RANKUP";

	protected WndBag.Mode mode1 = WndBag.Mode.TEST_RECOVER;
	protected WndBag.Mode mode2 = WndBag.Mode.UPGRADEABLE;
	protected WndBag.Mode mode3 = WndBag.Mode.NOTREINFORCED;
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);

		if (Dungeon.hero.spp > 100){

			actions.add(AC_DOUP);
			actions.add(AC_DORE);
		}
		if (Dungeon.hero.spp > 50) {
			actions.add(AC_DOSP);
			actions.add(AC_RANKUP);
		}
		if (Dungeon.gold > 1000) {
			actions.add(AC_NEED);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals(AC_CHOOSE)) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_DOSP)) {

			GameScene.selectItem(itemSelector1, mode1, Messages.get(this, "prompt"));

		} else if (action.equals(AC_DOUP)) {

			GameScene.selectItem(itemSelector2, mode2, Messages.get(this, "prompt"));

		} else if (action.equals(AC_DORE)) {

			GameScene.selectItem(itemSelector3, mode3, Messages.get(this, "prompt"));

		} else if (action.equals(AC_NEED)) {
				Dungeon.gold -= 1000;
				Dungeon.depth.drop(new BoundReward(), hero.pos).sprite.drop();
				hero.spendAndNext(1f);

		} else if (action.equals(AC_RANKUP)) {
				Dungeon.hero.spp -= 50;
				switch (Random.Int(50)){
					case 0 :
					case 45: Dungeon.hero.TRUE_HT++;
						Dungeon.hero.updateHT(true);
						GLog.w(Messages.get(RewardPaper.class, "htup"));
						break;
					case 46 :
						Dungeon.hero.hitSkill++;
						GLog.w(Messages.get(RewardPaper.class, "hitup"));
						break;
					case 47 :
						Dungeon.hero.evadeSkill++;
						GLog.w(Messages.get(RewardPaper.class, "evadeup"));
						break;
					case 48 :
						Dungeon.hero.magicSkill++;
						GLog.w(Messages.get(RewardPaper.class, "magicup"));
						break;
					case 49 :
						Dungeon.hero.TRUE_HT++;
						Dungeon.hero.updateHT(true);
						Dungeon.hero.hitSkill++;
						Dungeon.hero.evadeSkill++;
						Dungeon.hero.magicSkill++;
						GLog.w(Messages.get(PotionOfMixing.class, "skillup"));
						break;
				}
				hero.spendAndNext(1f);


		} else {
			super.execute(hero, action);
		}
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
	public int price() {
		return 30 * quantity;
	}


	protected static WndBag.Listener itemSelector1 = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				Hero hero = Dungeon.hero;
				Dungeon.hero.spp -= 50;
				item.unique();
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(1f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			}
		}
	};
	protected static WndBag.Listener itemSelector2 = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				Hero hero = Dungeon.hero;
				Dungeon.hero.spp -= 100;
                item.reinforce();
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(1f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			}
		}
	};
	protected static WndBag.Listener itemSelector3 = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				Hero hero = Dungeon.hero;
				Dungeon.hero.spp -= 100;
				item.upgrade(10);
				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(1f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
			}
		}
	};
}
