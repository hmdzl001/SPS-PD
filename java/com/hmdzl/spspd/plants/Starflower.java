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
package com.hmdzl.spspd.plants;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MoonFury;
import com.hmdzl.spspd.items.food.vegetable.BattleFlower;
import com.hmdzl.spspd.items.potions.PotionOfExperience;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Starflower extends Plant {

	{
		image = 11;
	}
	
	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Dungeon.level.drop(new BattleFlower(), pos).sprite.drop();
		if (ch != null) {
		  Buff.affect(ch, MoonFury.class);
		}
	}

	public static class Seed extends Plant.Seed{

		{
			image = ItemSpriteSheet.SEED_STARFLOWER;

			plantClass = Starflower.class;
			alchemyClass = PotionOfExperience.class;
		}
	}
}
