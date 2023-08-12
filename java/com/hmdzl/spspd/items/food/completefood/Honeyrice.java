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
package com.hmdzl.spspd.items.food.completefood;

import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Honeyrice extends CompleteFood {

	{
		//name = "honeyrice";
		image = ItemSpriteSheet.H_RICE;
		energy = 500;
		hornValue = 3;
		 
	}


	private static ItemSprite.Glowing YELLOW = new ItemSprite.Glowing( 0xFFFF44 );

	@Override
	public ItemSprite.Glowing glowing() {
		return YELLOW;
	}

	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {
			hero.TRUE_HT = hero.TRUE_HT + (Random.Int(6, 12));
			//hero.HP = hero.HP+Math.min(((hero.TRUE_HT-hero.HP)/2), hero.TRUE_HT-hero.HP);
			//Buff.detach(hero, Poison.class);
			//Buff.detach(hero, Cripple.class);
			//Buff.detach(hero, STRdown.class);
			//Buff.detach(hero, Bleeding.class);
            hero.updateHT(true);
			//hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 4);

		}
	}

	@Override
	public int price() {
		return 400 * quantity;
	}

}