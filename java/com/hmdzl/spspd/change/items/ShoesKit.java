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
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public class ShoesKit extends Item {

	private static final String TXT_SELECT_SHOES = "Select an shoes to upgrade";
	private static final String TXT_UPGRADED = "you applied the armor kit to upgrade your %s";

	private static final float TIME_TO_UPGRADE = 2;

	private static final String AC_APPLY = "APPLY";

	{
		//name = "armor kit";
		image = ItemSpriteSheet.SHOESKIT;

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

			//curUser = hero;
			//GameScene.selectItem(itemSelector, WndBag.Mode.SHOES,
					//TXT_SELECT_SHOES);

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

	/*private void upgrade(Shoes shoes) {

		detach(curUser.belongings.backpack);

		curUser.sprite.centerEmitter().start(Speck.factory(Speck.KIT), 0.05f,
				10);
		curUser.spend(TIME_TO_UPGRADE);
		curUser.busy();

		GLog.w(TXT_UPGRADED, shoes.name());

		switch (owner.heroClass) {
		case WARRIOR:
			classShoes = new JumpW();
			break;
		case ROGUE:
			classShoes = new JumpR();
			break;
		case MAGE:
			classShoes = new JumpM();
			break;
		case HUNTRESS:
			classShoes = new JumpH();
			break;
		}

		shoes.detach(curUser.belongings.backpack);
		classShoes.collect(curUser.belongings.backpack);
		

		curUser.sprite.operate(curUser.pos);
		Sample.INSTANCE.play(Assets.SND_EVOKE);
	}*/

	/*private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				ShoesKit.this.upgrade((Shoes) item);
			}
		}
	};*/
	
	@Override
	public int price() {
		return 200 * quantity;
	}
}
