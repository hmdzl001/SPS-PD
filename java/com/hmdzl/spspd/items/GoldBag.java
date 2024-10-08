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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

public class GoldBag extends Item {

	public static final String AC_USE = "USE";

	{
		//name = "goldbag";
		image = ItemSpriteSheet.ITEM_BAG;

		stackable = true;

		defaultAction = AC_USE;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE)) {

			hero.spend(1f);
			hero.busy();

			hero.sprite.operate(hero.pos);

			detach(hero.belongings.backpack);
			Dungeon.gold+=10000;

			Emitter emitter = hero.sprite.centerEmitter();
			emitter.start(FlameParticle.FACTORY, 0.2f, 3);

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
		return 10000 * quantity;
	}
}
