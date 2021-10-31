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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.CharSprite;

public class PotionOfStrength extends Potion {

	{
		//name = "Potion of Strength";
        initials = 13;
		 
	}

	@Override
	public void apply(Hero hero) {
		setKnown();

		hero.STR++;
        hero.HP += (hero.HT - hero.HP);
		hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "msg_1"));
		Badges.validateStrengthAttained();
	}

	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}
}
