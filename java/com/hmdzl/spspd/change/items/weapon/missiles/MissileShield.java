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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.MissileSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MissileShield extends MissileWeapon {

	{
		//name = "MissileShield";
		image = ItemSpriteSheet.WARRIORSHIELD;

		STR = 10;

		MIN = 0;
		MAX = 0;
		
		stackable = false;
		unique = true;

		
		
		bones = false;
	}

    private static final String AC_SHIELD = "SHIELD";
	public final int fullCharge = 10;
	public int charge = 0;
	private static final String CHARGE = "charge";	
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}		
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 10){
		actions.add(AC_SHIELD);
		}
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_SHIELD)) {
			if (charge < 10)
				GLog.i(Messages.get(this, "rest"));
            else {				
			    curUser = hero;
				Buff.prolong(hero, DefenceUp.class, 3f).level(50);
				Buff.prolong(hero, AttackDown.class, 3f).level(25);
		        }
				charge -= 10;
		} else 
			
		super.execute(hero, action);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {
		super.proc(attacker, defender, damage);
		if (attacker instanceof Hero && ((Hero) attacker).rangedWeapon == this) {
			circleBack(defender.pos, (Hero) attacker);
			if (charge > 3) {
				Buff.prolong(defender, Paralysis.class, 2f);
				charge -= 3;
			}
		}
	}

	@Override
	protected void miss(int cell) {
		circleBack(cell, curUser);
	}

	private void circleBack(int from, Hero owner) {
		((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class))
				.reset(from, curUser.pos, curItem, null);
		if (throwEquiped) {
			owner.belongings.weapon = this;
			owner.spend(-TIME_TO_EQUIP);
			Dungeon.quickslot.replaceSimilar(this);
			updateQuickslot();
		} else if (!collect(curUser.belongings.backpack)) {
			Dungeon.level.drop(this, owner.pos).sprite.drop();
		}
	}

	private boolean throwEquiped;

	@Override
	public void cast(Hero user, int dst) {
		throwEquiped = isEquipped(user);
		super.cast(user, dst);
	}

	@Override
	public String desc() {
		String info = super.desc();

		info += "\n\n" + Messages.get(MissileShield.class, "charge",charge,fullCharge);
		return info;
	}
}
