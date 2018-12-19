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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.rings.RingOfElements.Resistance;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class GlassShield extends Buff {

	{
		type = buffType.POSITIVE;
	}

	private int turns = 0;

	@Override
	public int icon() {
		return BuffIndicator.GLASS_SHIELD;
	}

	@Override
	public void detach() {
		turns--;
		if(turns<=0){
			super.detach();
		}
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc",turns());
	}

	private static final String TURNS = "turns";

	public int turns() {
		return turns;
	}

	public void turns(int value) {
		if (turns< value) {
			turns = value;
		}
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TURNS, turns);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		turns = bundle.getInt(TURNS);
	}

}