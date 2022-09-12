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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DiceTower extends Item {

	{
		//name = "DiceTower";
		image = ItemSpriteSheet.DICE_TOWER;
		unique = true;
		defaultAction = AC_CHOOSE;
	}


	public final int fullCharge = 100;
	public int charge = 0;	

	private static final String AC_CHOOSE = "CHOOSE";
	
    public static final String AC_ROLL = "ROLL";
	public static final String AC_REROLL = "REROLL";
	public static final String AC_ALLIN = "ALLIN";

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
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		
		actions.add(AC_ROLL);
		actions.add(AC_REROLL);
		actions.add(AC_ALLIN);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_ROLL)) {

		        Dungeon.hero.spp = Random.Int(100);
		        
				hero.spendAndNext(1f);
        } else
		 if (action.equals(AC_REROLL)) {
			 if (charge < 60) {
				 GLog.p(Messages.get(this, "need_charge"));
			 } else {
				 Dungeon.hero.spp = 100;
				 charge -= 60;
				 hero.spendAndNext(1f);
			 }
		 } else
		 if (action.equals(AC_ALLIN)) {
        	Dungeon.hero.spp += Dungeon.gold / 5000;
			Dungeon.gold = 0;
			hero.spendAndNext(1f);
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
			 return Messages.format("%d", charge /60);
	 }		
	@Override
	public int price() {
		return 30 * quantity;
	}
}
