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
import com.hmdzl.spspd.change.effects.particles.PurpleParticle;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndBag;
import com.watabou.noosa.audio.Sample;

public class GreaterStylus extends Item {

	private static final String TXT_SELECT_ARMOR = "Select an armor to inscribe on";
	private static final String TXT_INSCRIBED = "you inscribed your %s with the stylus";

	private static final float TIME_TO_INSCRIBE = 2;

	private static final String AC_INSCRIBE = "INSCRIBE";

	{
		name = "greater arcane stylus";
		image = ItemSpriteSheet.STYLUS;

		stackable = true;

		 
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_INSCRIBE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_INSCRIBE) {

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.ARMOR,
					TXT_SELECT_ARMOR);

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

	private void inscribe(Armor armor) {

		detach(curUser.belongings.backpack);

		GLog.w(TXT_INSCRIBED, armor.name());

		armor.hasglyph();

		curUser.sprite.operate(curUser.pos);
		curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
		Sample.INSTANCE.play(Assets.SND_BURNING);

		curUser.spend(TIME_TO_INSCRIBE);
		curUser.busy();
	}

	@Override
	public int price() {
		return 30 * quantity;
	}

	@Override
	public String info() {
		return "This arcane stylus is made of some dark, very hard stone. Using it you can inscribe "
				+ "a magical glyph on your armor, but you have no power over choosing what glyph it will be, "
				+ "the stylus will decide it for you.";
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				GreaterStylus.this.inscribe((Armor) item);
			}
		}
	};
}
