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
package com.hmdzl.spspd.items.medicine;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.ToxicImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class RealgarWine extends Pill {

	{
		//name = "RealgarWine";
		image = ItemSpriteSheet.WINE;
		 
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			Buff.affect(hero, FireImbue.class).set(FireImbue.DURATION);
			Buff.affect(hero, ToxicImbue.class).set(ToxicImbue.DURATION);
			hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f, 4);
		}
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

}