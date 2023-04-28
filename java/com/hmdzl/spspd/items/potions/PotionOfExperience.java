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

import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.MoonFury;
import com.hmdzl.spspd.actors.hero.Hero;

public class PotionOfExperience extends Potion {

	{
		//name = "Potion of Experience";
		
		initials = 0;

		 
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		//hero.earnExp(Random.Int(hero.maxExp()/2,hero.maxExp() - hero.exp));
		Buff.affect(hero, Bless.class, 180f);
		Buff.affect(hero, HasteBuff.class, 180f);
		Buff.affect(hero,MoonFury.class);
	}

	@Override
	public int price() {
		return isKnown() ? 80 * quantity : super.price();
	}
}
