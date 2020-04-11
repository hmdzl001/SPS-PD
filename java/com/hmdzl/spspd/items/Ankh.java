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
package com.hmdzl.spspd.items;

import java.util.ArrayList;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class Ankh extends Item {

	public static final String AC_BLESS = "BLESS";

	{
		//name = "Ankh";
		image = ItemSpriteSheet.ANKH;
		stackable = true;

		// You tell the ankh no, don't revive me, and then it comes back to
		// revive you again in another run.
		// I'm not sure if that's enthusiasm or passive-aggression.
		 
	}

	private Boolean blessed = true;

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		DewVial vial = hero.belongings.getItem(DewVial.class);
		if (vial != null && vial.isFullBless() && !blessed)
			actions.add(AC_BLESS);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_BLESS)) {

			DewVial vial = hero.belongings.getItem(DewVial.class);
			if (vial != null) {
				blessed = true;
				vial.empty();
				GLog.p(Messages.get(this, "bless"));
				hero.spend(1f);
				hero.busy();

				Sample.INSTANCE.play(Assets.SND_DRINK);
				CellEmitter.get(hero.pos).start(Speck.factory(Speck.LIGHT),
						0.2f, 3);
				hero.sprite.operate(hero.pos);
			}
		} else {

			super.execute(hero, action);

		}

	}

	@Override
	public String desc() {
		if (blessed)
			return Messages.get(this, "desc_blessed");
		else
			return super.desc();
	}

	public Boolean isBlessed() {
		return blessed;
	}

	private static final Glowing WHITE = new Glowing(0xFFFFCC);

	@Override
	public Glowing glowing() {
		return isBlessed() ? WHITE : null;
	}

	private static final String BLESSED = "blessed";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(BLESSED, blessed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		blessed = bundle.getBoolean(BLESSED);
	}

	@Override
	public int price() {
		return 50 * quantity;
	}
}
