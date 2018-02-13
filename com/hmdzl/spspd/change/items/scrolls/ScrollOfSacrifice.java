/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.change.items.scrolls;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class ScrollOfSacrifice extends Scroll {

	{
		//name = "Scroll of Sacrifice";
		consumedValue = 15;
		initials = 15;
	}
	
	@Override
	public void doRead() {

		if(Dungeon.sacrifice==0){Dungeon.hero.HT +=5;
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, "+5 HT");
		}
	    else if(Dungeon.sacrifice > 0){Dungeon.hero.STR++;
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, "+1 STR");
	    }
        if(Dungeon.sacrifice > 1) {Dungeon.hero.HT-= Random.Int(5*Dungeon.sacrifice,Dungeon.hero.HT/5);
			GLog.w("You feel not good...");
        }

        if(Dungeon.hero.HP > Dungeon.hero.HT)
		{Dungeon.hero.HP = Dungeon.hero.HT;}

		setKnown();

		curUser.spendAndNext( TIME_TO_READ );

		Dungeon.sacrifice++;
	}
	
	@Override
	public void empoweredRead() {
		//does nothing for now, this should never happen.
	}	

}