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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;

public class TentSleep extends FlavourBuff {

    public static final float DURATION = 30f;

	{
		type = buffType.NEUTRAL;
	}
	@Override
	public int icon() {
		return BuffIndicator.MAGIC_SLEEP;
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target)) {
			Buff.detach( target,Tar.class);
			Buff.detach( target,Ooze.class);
			Buff.detach( target,Slow.class);
			Buff.detach( target,Cripple.class);
			Buff.detach( target,Burning.class);
			Buff.detach( target,FrostIce.class);
			Buff.detach( target,Shocked.class);
			Buff.detach( target,LightShootAttack.class);
			Buff.detach( target,ShadowCurse.class);
			target.invisible++;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		if (target.invisible > 0)
			target.invisible--;
		super.detach();
	}


	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	{
		immunities.add(Blob.class);
		immunities.add(Buff.class);
	}	
	
	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
}