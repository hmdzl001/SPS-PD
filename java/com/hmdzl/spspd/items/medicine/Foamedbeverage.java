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

import com.hmdzl.spspd.actors.buffs.BerryRegeneration;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.EarthImbue;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.ToxicImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Foamedbeverage extends Pill {

	{
		//name = "foamedbeverage";
		image = ItemSpriteSheet.FOAMED;
		 
	}


	public Foamedbeverage(int number) {
		super();
		quantity = number;
	}
	@Override
	public int price() {
		return 5 * quantity;
	}

	@Override
	public Item random() {
		return this;
	}


	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_EAT)){
			Buff.detach(hero, Poison.class);
			Buff.detach(hero, Cripple.class);
			Buff.detach(hero, STRdown.class);
			Buff.detach(hero, Bleeding.class);
			Buff.affect(hero, Bless.class, 30f);
            Buff.affect(hero, BerryRegeneration.class).level(hero.HT/4);
            switch (Random.Int(4)) {
				case 0:
					Buff.affect(hero,FireImbue.class).set(30f);
					break;
				case 1:
					Buff.affect(hero,FrostImbue.class,30f);
					break;
				case 2:
					Buff.affect(hero,ToxicImbue.class).set(30f);
					break;
				case 3:
					Buff.affect(hero,EarthImbue.class,30f);
					break;
				//case 4:
					//Buff.affect(hero,FireImbue.class).set(30f);
					//break;
				//case 5:
				//	Buff.affect(hero,FireImbue.class).set(30f);
				//	break;
			}
		}
}

}