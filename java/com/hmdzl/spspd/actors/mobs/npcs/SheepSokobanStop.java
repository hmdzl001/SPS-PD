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
package com.hmdzl.spspd.actors.mobs.npcs;

import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.sellitem.SheepFur;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.SheepSprite;
import com.watabou.utils.Random;

public class SheepSokobanStop extends NPC {
	private static final String[] LINE_KEYS = {"Baa!", "Baa?", "Baa.", "Baa..."};
{
spriteClass = SheepSprite.class;
	properties.add(Property.UNKNOW);
}


@Override
protected boolean act() {
	throwItem();
	return super.act();
}

@Override
public void damage(int dmg, Object src, int type) {
}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

@Override
public void add(Buff buff) {
}

/*  -W-1 -W  -W+1
 *  -1    P  +1
 *  W-1   W  W+1
 * 
 */

@Override
public boolean interact() {
	  yell( Messages.get(this, Random.element(LINE_KEYS)));
    return false;
}
	@Override
	public Item SupercreateLoot(){
		return new SheepFur();
	}
}
