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
package com.hmdzl.spspd.change.items.misc;

import java.util.ArrayList;
import java.util.logging.Level;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.buffs.Arcane;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.GlassShield;
import com.hmdzl.spspd.change.actors.buffs.Haste;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Levitation;
import com.hmdzl.spspd.change.actors.buffs.Recharging;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Rhythm2;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.BalanceFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.DemonFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.HumanFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.LifeFaith;
import com.hmdzl.spspd.change.actors.buffs.faithbuff.MechFaith;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;

import com.hmdzl.spspd.change.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndItem;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class DanceLion extends Item {

	{
		//name = "DanceLion";
		image = ItemSpriteSheet.DANCE_LION;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	public final int fullCharge = 100;
	public int charge = 0;	
	
	public static final String AC_CHOOSE = "CHOOSE";

    public static final String AC_SPIN = "SPIN";
	public static final String AC_STAND = "STAND";
	public static final String AC_BACK = "BACK";
	public static final String AC_RUSH = "RUSH";
	public static final String AC_JUMP = "JUMP";
	
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
		if (charge > 40) {
		actions.add(AC_SPIN);
		actions.add(AC_STAND);
		actions.add(AC_BACK);
		actions.add(AC_RUSH);
		actions.add(AC_JUMP);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;

		if (action.equals( AC_CHOOSE )){
			
			GameScene.show(new WndItem(null, this, true));
			
		} else if (action.equals(AC_SPIN)) {
			Buff.affect(curUser, Rhythm.class,20f);
			if(hero.subClass == HeroSubClass.SUPERSTAR){
				Buff.affect(curUser,Rhythm2.class,20f);
			}
			charge -= 40;
			hero.spendAndNext(1f);
		} else if (action.equals(AC_STAND)) {
			Buff.affect(curUser,DefenceUp.class,20f).level(30);
			if(hero.subClass == HeroSubClass.SUPERSTAR){	
				Buff.affect(curUser,ShieldArmor.class).level(Dungeon.hero.lvl*2);
			}
			charge -= 40;
			hero.spendAndNext(1f);
		} else if (action.equals(AC_BACK)) {
			Buff.affect(curUser, Recharging.class,10f);
			if(hero.subClass == HeroSubClass.SUPERSTAR){	
				Buff.affect(curUser,Arcane.class,20f);
			}
			charge -= 40;
			hero.spendAndNext(1f);
		} else if (action.equals(AC_RUSH)) {
			Buff.affect(curUser, AttackUp.class,20f).level(30);
			if(hero.subClass == HeroSubClass.SUPERSTAR){	
				Buff.affect(curUser,Invisibility.class,20f);
			}
			charge -= 40;
			hero.spendAndNext(1f);
		} else if (action.equals(AC_JUMP)) {
			Buff.affect(curUser, Levitation.class,20f);
			if(hero.subClass == HeroSubClass.SUPERSTAR){	
				Buff.affect(curUser,GlassShield.class).turns(2);
			}
			charge -= 40;
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
			 return Messages.format("%d", (int)charge/40);
	 }	
}
