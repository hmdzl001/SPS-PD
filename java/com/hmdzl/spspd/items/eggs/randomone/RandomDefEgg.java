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
package com.hmdzl.spspd.items.eggs.randomone;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.pets.Chocobo;
import com.hmdzl.spspd.actors.mobs.pets.DogPet;
import com.hmdzl.spspd.actors.mobs.pets.Fly;
import com.hmdzl.spspd.actors.mobs.pets.Spider;
import com.hmdzl.spspd.actors.mobs.pets.Stone;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class RandomDefEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;

		{
		image = ItemSpriteSheet.RANDOM_PET_EGG;

		stackable = false;
		}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {
			rollpet();
            Statistics.eggBreak++;
            Badges.validateEggBreak();
            hero.next();
		} else {
			super.execute(hero, action);

		}
	}	


	public void rollpet() {
		switch (Random.Int (5)) {
			case 0:
				DogPet pet = new DogPet();
					eggHatch(pet);
				break;
			case 1:
				Chocobo pet2 = new Chocobo();
					eggHatch(pet2);
				break;
			case 2:
				Fly pet5 = new Fly();
					eggHatch(pet5);
				break;
			case 3:
				Stone pet8 = new Stone();
					eggHatch(pet8);
				break;
			case 4:
				Spider pet4 = new Spider();
					eggHatch(pet4);
				break;
		}


	}
		
	@Override
	public int price() {
		return 500 * quantity;
	}

}
