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
package com.hmdzl.spspd.items.wands;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class WandOfTest extends DamageWand {

	public static final String AC_ENERGY = "0";
	public static final String AC_FIRE = "1";
	public static final String AC_ICE = "2";
	public static final String AC_SHOCK = "3";
	public static final String AC_EARTH = "4";
	public static final String AC_LIGHT = "5";
	public static final String AC_DARK = "6";

	public int type = 0;
	private static final String TYPE = "type";

	{
		image = ItemSpriteSheet.TEST_WAND;
		collisionProperties = Ballistica.MAGIC_BOLT;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ENERGY);
		actions.add(AC_FIRE);
		actions.add(AC_ICE);
		actions.add(AC_SHOCK);
		actions.add(AC_EARTH);
		actions.add(AC_LIGHT);
		actions.add(AC_DARK);

		return actions;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TYPE, type);

	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		type = bundle.getInt(TYPE);
	}

	public int min(int lvl){
		return 10+lvl;
	}

	public int max(int lvl){
		return 10+lvl*2;
	}	

	@Override
	protected void onZap( Ballistica bolt ) {
				
		Char ch = Actor.findChar( bolt.collisionPos );
		if (ch != null) {
			switch (type) {
				case 0:
					ch.damage(damageRoll(), DamageType.ENERGY_DAMAGE,2);
					break;
				case 1:
					ch.damage(damageRoll(), DamageType.FIRE_DAMAGE,2);
					break;
				case 2:
					ch.damage(damageRoll(), DamageType.ICE_DAMAGE,2);
					break;
				case 3:
					ch.damage(damageRoll(), DamageType.SHOCK_DAMAGE,2);
					break;
				case 4:
					ch.damage(damageRoll(), DamageType.EARTH_DAMAGE,2);
					break;
				case 5:
					ch.damage(damageRoll(), DamageType.LIGHT_DAMAGE,2);
					break;
				case 6:
					ch.damage(damageRoll(), DamageType.DARK_DAMAGE,2);
					break;

			}
			ch.sprite.burst(0xFF99CCFF, 2);
		}
	}

	public void execute(Hero hero, String action) {
		curUser = hero;
		if (action.equals(AC_ENERGY)) {
            type = 0;
		} else if (action.equals(AC_FIRE)) {
			type = 1;
		} else if (action.equals(AC_ICE)) {
			type = 2;
		} else if (action.equals(AC_SHOCK)) {
			type = 3;
		} else if (action.equals(AC_EARTH)) {
			type = 4;
		} else if (action.equals(AC_LIGHT)) {
			type = 5;
		} else if (action.equals(AC_DARK)) {
			type = 6;
		}  else {
			super.execute(hero, action);
		}
	}

	@Override
	public void updateLevel() {
		maxCharges = 99;
		curCharges = Math.min( curCharges, maxCharges );
	}

	@Override
	protected void wandUsed() {
		curCharges -= chargesPerCast();
		//if (!isIdentified() && usagesToKnow <= 0) {
		//	identify();
		//	GLog.w( Messages.get(Wand.class, "identify", name()) );
		//} else {
		if (curUser.heroClass == HeroClass.MAGE) levelKnown = true;
		updateQuickslot();
		//	}
	    curUser.spendAndNext(1f);

		if (curCharges == 0){
			Dungeon.hero.spp += 100;
			this.detach(curUser.belongings.backpack);
		}

	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(this, "ac_"+ type);
		return info;
	}

	@Override
	protected int initialCharges() {
		return 99;
	}

}
