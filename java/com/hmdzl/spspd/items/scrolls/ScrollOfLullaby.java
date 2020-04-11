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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Drowsy;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.audio.Sample;

public class ScrollOfLullaby extends Scroll {

	{
		//name = "Scroll of Lullaby";
		consumedValue = 5;
		initials = 2;
	}

	@Override
	public void doRead() {

		curUser.sprite.centerEmitter()
				.start(Speck.factory(Speck.NOTE), 0.3f, 5);
		Sample.INSTANCE.play(Assets.SND_LULLABY);
		Invisibility.dispel();

		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (Level.fieldOfView[mob.pos]) {
				Buff.affect(mob, Drowsy.class);
				Buff.affect(mob, AttackDown.class,10f).level(50);
				Buff.affect(mob, ArmorBreak.class,10f).level(50);
				mob.sprite.centerEmitter().start(Speck.factory(Speck.NOTE),
						0.3f, 5);
			}
		}

		Buff.affect(curUser, Drowsy.class);
		Buff.affect(curUser, AttackDown.class,10f).level(20);
		Buff.affect(curUser, ArmorBreak.class,10f).level(20);

		GLog.i(Messages.get(this, "sooth"));

		setKnown();

		readAnimation();
	}

	@Override
	public void empoweredRead() {
		doRead();
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (Level.fieldOfView[mob.pos]) {
				Buff drowsy = mob.buff(Drowsy.class);
				if (drowsy != null) drowsy.act();
			}
		}
	}	
	
	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}
}
