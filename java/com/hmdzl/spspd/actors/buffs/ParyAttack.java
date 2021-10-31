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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class ParyAttack extends Buff {
	
    private int pos;
	private int level = 0;
	private static final String LEVEL = "level";
	private static final String POS = "pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(POS, pos);
		bundle.put(LEVEL, level);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		pos = bundle.getInt(POS);
		level = bundle.getInt(LEVEL);
	}
	
	{
		type = buffType.POSITIVE;
	}	
	
	
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			return super.attachTo(target);
		}	
	
	public boolean act() {

		if (target.isAlive()) {
			
			if (target.pos != pos || Dungeon.gold < level) {
				detach();
			} 
			
			level++;
			if (level > 100)
				Dungeon.gold -=level*10;

			spend(TICK);

		} else {
				detach();
		}

		return true;
	}	

	@Override
	public void detach() {
		super.detach();
	}

	@Override
	public int icon() {
		return BuffIndicator.BLESS;
	}

	public int level() {
		return level;
	}	
	
	public void level(int value) {
		if (level < value) {
			level = value;
		}
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",level/2.5);
	}
}
