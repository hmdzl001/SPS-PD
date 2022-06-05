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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class GoldenNut extends CompleteFood {

	{
		//name = "golden dungeon nut";
		image = ItemSpriteSheet.SEED_GOLDENDUNGEONNUT;
		energy = 100;
		hornValue = 2;
	}

	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_EAT)) {

			switch (Random.Int(2)) {
			case 0:
				hero.TRUE_HT+=40;
				hero.STR+=1;
				hero.hitSkill++;
				hero.evadeSkill++;
				hero.magicSkill++;
				hero.sprite.showStatus(CharSprite.POSITIVE, "+1 all, +40 ht");
                hero.updateHT(true);
				Badges.validateStrengthAttained();
				break;
			case 1:
				hero.TRUE_HT+=10;
				hero.STR+=2;
				hero.sprite.showStatus(CharSprite.POSITIVE, "+2 str, +10 ht");
				hero.updateHT(true);
				Badges.validateStrengthAttained();
				break;
			}
		}
	}	

}


