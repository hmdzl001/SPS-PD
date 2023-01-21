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
package com.hmdzl.spspd.items.eggs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.pets.Chocobo;
import com.hmdzl.spspd.actors.mobs.pets.Datura;
import com.hmdzl.spspd.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RandomEgg9 extends Egg {
	
	public static final float TIME_TO_USE = 1;

		{
		image = ItemSpriteSheet.RANDOWNEGG;

		stackable = false;
		}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {
			rollpet();
			detach(Dungeon.hero.belongings.backpack);
            Statistics.eggBreak++;
            Badges.validateEggBreak();
            hero.next();
		} else {
			super.execute(hero, action);

		}
	}	


	public void rollpet() {
		switch (Random.Int (3)) {
			case 0:
				RibbonRat pet = new RibbonRat();
				eggHatch(pet);
				break;
			case 1:
				Chocobo pet1 = new Chocobo();
				eggHatch(pet1);
				break;
			case 2:
				Datura pet2 = new Datura();
				eggHatch(pet2);
				break;
	
		}


	}
		
	@Override
	public int price() {
		return 500 * quantity;
	}

}
