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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.BeTired;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class BigBattery extends Item {

	{
		//name = "BigBattery";
		image = ItemSpriteSheet.BIG_BATTERY;
		unique = true;
		defaultAction = AC_CHOOSE;
	}


	public final int fullCharge = 20;
	public int charge = 0;	

	private static final String AC_CHOOSE = "CHOOSE";
	
    public static final String AC_USE = "USE";
	public static final String AC_ADD = "ADD";

	private static final String CHARGE = "charge";

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
	public int price() {
		return 30 * quantity;
	}	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		
		actions.add(AC_USE);
		actions.add(AC_ADD);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_USE)) {

		 if (charge < 15) {
				GLog.p(Messages.get(this, "break"));
			} else {	 
		        Buff.prolong(hero,Recharging.class,25f);
				Buff.affect(hero, Arcane.class, 5f);
			    Buff.affect(hero, HighLight.class, 25f);
			    Buff.affect(hero, AttackUp.class, 25f).level(30);
			    Buff.affect(hero, DefenceUp.class, 25f).level(30);
			    Buff.affect(hero, HasteBuff.class, 25f);
			    Buff.affect(hero, Rhythm.class, 25f);
				charge-=15;
				hero.spendAndNext(1f);
			}
        } else
		 if (action.equals(AC_ADD)) {
			 if (charge < 15) {
				 GLog.p(Messages.get(this, "break"));
			 } else {
				 for (Mob mob : Dungeon.depth.mobs) {
					 if (Floor.fieldOfView[mob.pos] && mob.isAlive()) {
						 Buff.affect(mob, BeTired.class).set(30);
					 }
				 }
				 charge-=15;
				 hero.spendAndNext(1f);
			 }

		} else {
			super.execute(hero, action);
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public String desc() {
		String info = super.desc();
		info += "\n\n" + Messages.get(AttackShield.class, "charge",charge,fullCharge);
		return info;
	}	
	
	 @Override
	 public String status() {
			 return Messages.format("%d", charge /10);
	 }		

}
