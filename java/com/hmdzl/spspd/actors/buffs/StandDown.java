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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.SlowGas;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.ui.BuffIndicator;

public class StandDown extends FlavourBuff {

	private static final float DURATION = 5f;
	
	{
		type = buffType.NEGATIVE;
	}	

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)) {

			target.paralysed++;

			Buff.detach( target, SpeedSlow.class );

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		super.detach();
		if (target.paralysed > 0){
			target.paralysed--;
	        Buff.prolong(target, SpeedSlow.class, 3f);
	 }
	}

	@Override
	public int icon() {
		return BuffIndicator.SHIELDBLOCK;
	}
	
	//@Override
	public void fx(boolean on) {
		if (on) target.sprite.add(CharSprite.State.FROZEN);
		else target.sprite.remove(CharSprite.State.FROZEN);
	}

	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

	{
		immunities.add( SpeedSlow.class );
		immunities.add( SlowGas.class );

	}
	public static float duration( Char ch ) {
		return DURATION;
	}
}
