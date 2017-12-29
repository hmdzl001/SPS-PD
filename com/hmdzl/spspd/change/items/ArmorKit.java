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
package com.hmdzl.spspd.change.items;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.ClassArmor;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public class ArmorKit extends Item {

	private static final float TIME_TO_UPGRADE = 2;

	private static final String AC_APPLY = "APPLY";

	{
		//name = "armor kit";
		image = ItemSpriteSheet.KIT;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_APPLY);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_APPLY) {

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.ARMOR,
					Messages.get(this,"prompt"));

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

	private void upgrade(Armor armor) {

		detach(curUser.belongings.backpack);

		curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f,
				10);
		curUser.spend(TIME_TO_UPGRADE);
		curUser.busy();

		GLog.w(Messages.get(this,"upgraded", armor.name()));

		ClassArmor classArmor = ClassArmor.upgrade(curUser, armor);
		if (curUser.belongings.armor == armor) {

			curUser.belongings.armor = classArmor;
			((HeroSprite) curUser.sprite).updateArmor();

		} else {

			armor.detach(curUser.belongings.backpack);
			classArmor.collect(curUser.belongings.backpack);

		}

		curUser.sprite.operate(curUser.pos);
		Sample.INSTANCE.play(Assets.SND_EVOKE);
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				ArmorKit.this.upgrade((Armor) item);
			}
		}
	};
	
	@Override
	public int price() {
		return 200 * quantity;
	}
}
