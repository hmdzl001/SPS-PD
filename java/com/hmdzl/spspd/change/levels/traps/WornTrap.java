/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.change.levels.traps;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.TrapSprite;
import com.hmdzl.spspd.change.utils.GLog;

public class WornTrap extends Trap {

	{
		color = TrapSprite.BLACK;
		shape = TrapSprite.DOTS;
	}

	@Override
	public Trap hide() {
		//this one can't be hidden
		return reveal();
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		CellEmitter.get(pos).burst(Speck.factory(Speck.STEAM), 6);
		GLog.i(Messages.get(this, "nothing"));
	}
}
