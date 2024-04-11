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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Greaterpill extends Pill {

	{
		//name = "shootpill";
		image = ItemSpriteSheet.GREAT_PILL;
		sname = "HT+";
		 
	}

	public void doEat2() {
		Buff.affect(curUser, BerryRegeneration.class).level(Math.max(curUser.HT/2,30));
		curUser.HP = curUser.HP+Math.min(curUser.HT, (int)(curUser.HT*2-curUser.HP));
		Buff.detach(curUser, Poison.class);
		Buff.detach(curUser, Cripple.class);
		Buff.detach(curUser, STRdown.class);
		Buff.detach(curUser, Bleeding.class);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f, 4);

	}

	@Override
	public int price() {
		return 50 * quantity;
	}

}