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
package com.hmdzl.spspd.change.items.scrolls;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.Flare;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.audio.Sample;

public class ScrollOfTerror extends Scroll {

	{
		//name = "Scroll of Terror";
		consumedValue = 5;
		initials = 13;
	}

	@Override
	public void doRead() {

		new Flare(5, 32).color(0xFF0000, true).show(curUser.sprite, 2f);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		int count = 0;
		Mob affected = null;
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Terror.class, Terror.DURATION).object = curUser
						.id();
				Buff.affect(mob, Haste.class, Terror.DURATION*.5f);

				count++;
				affected = mob;
			}
		}

		switch (count) {
		case 0:
			GLog.i(Messages.get(this, "none"));
			break;
		case 1:
			GLog.i(Messages.get(this, "one", affected.name));
			break;
		default:
			GLog.i(Messages.get(this, "many") );
		}
		setKnown();

		readAnimation();
	}

	@Override
	public void empoweredRead() {
		doRead();
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Terror t = mob.buff(Terror.class);
				if (t != null){
					Buff.prolong(mob, Terror.class, Terror.DURATION*1.5f);
					Buff.affect(mob, Paralysis.class, Terror.DURATION*.5f);
					Buff.affect(mob, Haste.class, Terror.DURATION*.5f);
				}
			}
		}
	}	
	
	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
