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

import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class PerfectFood extends CompleteFood {
	
	{
		//name = "perfect food";
		image = ItemSpriteSheet.PERFECT_FOOD;
		energy = 600;
		hornValue = 10;
	}

	public void doEat() {
			curUser.TRUE_HT = curUser.TRUE_HT + (Random.Int(3, 7));
			Buff.affect(curUser, Bless.class,50f);
			Buff.affect(curUser, Light.class,50f);
			//hero.HP = Math.min(hero.HP + hero.TRUE_HT/10, hero.TRUE_HT);
			Buff.affect(curUser, HasteBuff.class,25f);
			Buff.affect(curUser, Levitation.class,25f);
			//hero.updateHT(true);
	}

	@Override
	public int price() {
		return 50 * quantity;
	}
	
}
