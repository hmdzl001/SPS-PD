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

import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Porksoup extends CompleteFood {

	{
		//name = "porksoup";
		image = ItemSpriteSheet.MEATSOUP;
		energy = 200;
		hornValue = 3;
		 
	}
	private static ItemSprite.Glowing BROWN = new ItemSprite.Glowing(0xCC6600);

	@Override
	public ItemSprite.Glowing glowing() {
		return BROWN;
	}
	@Override
	public int price() {
		return 3 * quantity;
	}

	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_EAT)){
			Buff.detach(hero, Poison.class);
			Buff.detach(hero, Cripple.class);
			Buff.detach(hero, STRdown.class);
			Buff.detach(hero, Bleeding.class);
			Buff.affect(hero,MagicArmor.class).level(hero.HT/4);
			Buff.affect(hero,AttackUp.class,50f).level(20);
			
		}
	}

}