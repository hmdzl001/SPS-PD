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

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.noosa.audio.Sample;

public class RewardPaper extends Item {

	{
		//name = "RewardPaper";
		image = ItemSpriteSheet.REWARD_PAPER;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	private static final String AC_CHOOSE = "CHOOSE";

	public static final String AC_RECOVER = "RECOVER";
	public static final String AC_NEED = "NEED";
	public static final String AC_RANKUP = "RANKUP";

	protected WndBag.Mode mode = WndBag.Mode.TRANMSUTABLE;

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);

		actions.add(AC_RECOVER);
		actions.add(AC_NEED);
		actions.add(AC_RANKUP);

		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals(AC_CHOOSE)) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_RECOVER)) {

			GameScene.selectItem(itemSelector, mode, Messages.get(this, "prompt"));

		} else if (action.equals(AC_NEED)) {
			if (Dungeon.hero.spp < 1) {
				GLog.p(Messages.get(this, "need_charge"));
			} else {
				Dungeon.hero.spp -= 1;
				Dungeon.level.drop(Generator.random(), hero.pos).sprite.drop();
				hero.spendAndNext(1f);
			}
		} else if (action.equals(AC_RANKUP)) {
			if (Dungeon.hero.spp < 100) {
				GLog.p(Messages.get(this, "need_charge"));
			} else {
				Dungeon.hero.spp -= 100;
				Dungeon.hero.hitSkill++;
				Dungeon.hero.evadeSkill++;
				Dungeon.hero.magicSkill++;
				hero.spendAndNext(1f);
				GLog.w(Messages.get(PotionOfMixing.class, "skillup"));
			}
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
	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				Hero hero = Dungeon.hero;
				Dungeon.hero.spp += item.level;

				hero.sprite.operate(hero.pos);
				hero.busy();
				hero.spend(1f);
				Sample.INSTANCE.play(Assets.SND_BURNING);
				hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
				item.detach(hero.belongings.backpack);

				GLog.p(Messages.get(RewardPaper.class, "infuse"));


			}
		}
	};
	
}
