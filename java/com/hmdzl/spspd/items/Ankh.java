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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class Ankh extends Item {

	public static final String AC_BLESS = "BLESS";

	{
		//name = "Ankh";
		image = ItemSpriteSheet.ANKH;
        stackable = true;

		// You tell the ankh no, don't revive me, and then it comes back to
		// revive you again in another run.
		// I'm not sure if that's enthusiasm or passive-aggression.
		 
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	@Override
	public String desc() {
			return Messages.get(this, "desc_blessed");
	}

	private static final Glowing WHITE = new Glowing(0xFFFFCC);

	@Override
	public Glowing glowing() {
		return  WHITE;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}
}
