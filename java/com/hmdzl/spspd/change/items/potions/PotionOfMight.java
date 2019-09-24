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
package com.hmdzl.spspd.change.items.potions;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class PotionOfMight extends Potion {

	{
		//name = "Potion of Might";
        initials = 8;
		 
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		
		hero.HT += 15;
		hero.HP += 15;
		
		hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "msg_1"));
	}


	@Override
	public int price() {
		return isKnown() ? 200 * quantity : super.price();
	}
}
