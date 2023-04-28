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
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

public class PotionOfMixing extends Potion {

	{

	    image = ItemSpriteSheet.POTION_CATALYST;
		initials = 15;
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		mixheal(Dungeon.hero);
	}

	public static void mixheal(Hero hero) {

	       hero.hitSkill++;
	       hero.evadeSkill++;
           hero.magicSkill++;
           GLog.w(Messages.get(PotionOfMixing.class, "skillup"));

		hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f, 4);
	}

	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}

	@Override
	public boolean isKnown() {
		return true;
	}
}
