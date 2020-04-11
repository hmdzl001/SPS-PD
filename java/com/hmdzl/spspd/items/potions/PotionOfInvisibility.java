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
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.misc.AutoPotion.AutoHealPotion;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;

public class PotionOfInvisibility extends Potion {

	private static final float ALPHA = 0.4f;

	{
		//name = "Potion of Invisibility";
		initials = 4;
	}
	
	@Override
	public void apply(Hero hero) {
		setKnown();
		Buff.affect(hero, Invisibility.class,  Dungeon.hero.buff(AutoHealPotion.class) != null ? Invisibility.DURATION*2 : Invisibility.DURATION);
		Buff.affect(hero,AttackUp.class, 10f).level(20);
		GLog.i(Messages.get(this, "invisible"));
		Sample.INSTANCE.play(Assets.SND_MELD);
	}
	
	@Override
	public void execute(final Hero hero, String action) {
	   super.execute(hero, action);
	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}

	public static void melt(Char ch) {
		if (ch.sprite.parent != null) {
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, ALPHA, 0.4f));
		} else {
			ch.sprite.alpha(ALPHA);
		}
	}
}
