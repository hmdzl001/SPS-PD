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
package com.hmdzl.spspd.items.food.meatfood;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class MysteryMeat extends MeatFood {

	{
		//name = "mystery meat";
		image = ItemSpriteSheet.MYSTERYMEAT;
		energy = 80;
		hornValue = 1;
	}

	public void doEat() {
			switch (Random.Int(5)) {
			case 0:
				GLog.w(Messages.get(this,"hot"));
				Buff.affect(curUser, Burning.class).set(5f);
				break;
			case 1:
				GLog.w(Messages.get(this,"legs"));
				Buff.prolong(curUser, Roots.class, Paralysis.duration(curUser));
				break;
			case 2:
				GLog.w(Messages.get(this,"bad"));
				Buff.affect(curUser, Poison.class).set(curUser.HT / 5);
				break;
			case 3:
				GLog.w(Messages.get(this,"stuffed"));
				Buff.prolong(curUser, Slow.class, 10f);
				break;
			}

	}

	@Override
	public int price() {
		return 2 * quantity;
	}
}
