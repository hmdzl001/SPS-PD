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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HolyStun;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.watabou.noosa.audio.Sample;

public class PotionOfShield extends Potion {

	//private static final float ALPHA = 0.4f;

	private static final int DISTANCE = 2;

	{
		//name = "Potion of shield";
		initials = 2;
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		//if (hero.buff(ShieldArmor.class) == null && hero.buff(MagicArmor.class) == null){
		   Buff.affect(hero, ShieldArmor.class).level(hero.HT/3);
			Buff.affect(hero, MagicArmor.class).level(hero.HT/3);
		//} else Buff.affect(hero, DefenceUp.class,30f).level(50);
		Sample.INSTANCE.play(Assets.SND_MELD);
	}

	/*@Override
	public String desc() {
		return "Drinking this potion will temporarily speed up your actions.";
	}*/

	@Override
	public void shatter(int cell) {

		Char ch = Actor.findChar(cell);
		if (ch != null) {
			Buff.prolong(ch, HolyStun.class,5f); }

	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}

	//public static void melt(Char ch) {
		//if (ch.sprite.parent != null) {
		//	ch.sprite.parent.add(new AlphaTweener(ch.sprite, ALPHA, 0.4f));
		//} else {
		//	ch.sprite.alpha(ALPHA);
		//}
	//}
}
