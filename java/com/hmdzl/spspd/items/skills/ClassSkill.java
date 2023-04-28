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
package com.hmdzl.spspd.items.skills;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.SkillRecharge;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

abstract public class ClassSkill extends Item {

    private static final String AC_CHOOSE = "CHOOSE";

    private static final String AC_SPECIAL = "SPECIAL";
	private static final String AC_SPECIAL_TWO = "SPECIAL_TWO";
	private static final String AC_SPECIAL_THREE = "SPECIAL_THREE";
	private static final String AC_SPECIAL_FOUR = "SPECIAL_FOUR";
	 //private static int SKILL_TIME = 1;
	public float colddown = 0f;
	public static int charge = 0;
	protected SkillCharger skillcharger;

	{
		defaultAction = AC_CHOOSE;
		stackable= true;
		unique = true;
	}

	public static ClassSkill upgrade(Hero owner) {

		ClassSkill classSkill = null;

		switch (owner.heroClass) {
		case WARRIOR:
			classSkill = new WarriorSkill();
			break;
		case ROGUE:
			classSkill = new RogueSkill();
			break;
		case MAGE:
			classSkill = new MageSkill();
			break;
		case HUNTRESS:
			classSkill = new HuntressSkill();
			break;
		case PERFORMER:
			classSkill = new PerformerSkill();
			break;
		case SOLDIER:
			classSkill = new SoldierSkill();
			break;
		case FOLLOWER:
			classSkill = new FollowerSkill();
			break;	
		case ASCETIC:
			classSkill = new AsceticSkill();
			break;				
		}
        charge = 0;
		return classSkill;
	}
	
	@Override
	public boolean collect( Bag container ) {
		if (super.collect( container )) {
			if (container.owner != null) {
				charge( container.owner );
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void charge( Char owner ) {
		if (skillcharger == null) skillcharger = new SkillCharger();
		skillcharger.attachTo( owner );
	}

	@Override
	public void onDetach( ) {
		stopCharging();
	}

	public void stopCharging() {
		if (skillcharger != null) {
			skillcharger.detach();
			skillcharger = null;
		}
	}

	private static final String COLDDOWN = "colddown";
	private static final String CHARGE = "charge";
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(COLDDOWN, colddown);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		colddown = bundle.getInt(COLDDOWN);
		charge = bundle.getInt(CHARGE);
	}		

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		if (charge < 1 ||  hero.buff(SkillRecharge.class) != null){
		actions.add(AC_SPECIAL);
		}
		if ((charge < 1 ||  hero.buff(SkillRecharge.class) != null) && Dungeon.hero.lvl > 20) {
			actions.add(AC_SPECIAL_TWO);
		}
		if ((charge < 1 ||  hero.buff(SkillRecharge.class) != null )&& Dungeon.hero.lvl > 30) {
			actions.add(AC_SPECIAL_THREE);
		}
		if ((charge < 1 ||  hero.buff(SkillRecharge.class) != null )&& Dungeon.hero.lvl > 40) {
			actions.add(AC_SPECIAL_FOUR);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals( AC_CHOOSE )){
			
			GameScene.show(new WndItem(null, this, true));
			
		} else if (action.equals(AC_SPECIAL)) {
			curUser = hero;
			Invisibility.dispel();
			doSpecial();
		} else if (action.equals(AC_SPECIAL_TWO)) {
			curUser = hero;
			Invisibility.dispel();
			doSpecial2();
		} else if (action.equals(AC_SPECIAL_THREE)) {
			curUser = hero;
			Invisibility.dispel();
			doSpecial3();
		} else if (action.equals(AC_SPECIAL_FOUR)) {
			curUser = hero;
			Invisibility.dispel();
			doSpecial4();
		} else {
			super.execute(hero, action);
		}
	}

	abstract public void doSpecial();
	
	abstract public void doSpecial2();

	abstract public void doSpecial3();

	abstract public void doSpecial4();
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 0;
	}

	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(ClassSkill.class, "charge",charge);
		return info;
	}

	@Override
	public String status() {
		return Messages.format("%d", charge);
	}
	
	protected class SkillCharger extends Buff {
	
		@Override
		public boolean attachTo( Char target ) {
			super.attachTo( target );
			return true;
		}
		
		@Override
		public boolean act() {
			if (charge > 0)
				gainCharge();
			
			if (colddown >= 20 && charge > 0) {
				colddown=0;
				charge--;
				updateQuickslot();
			}
			spend( TICK );
			
			return true;
		}
		
		private void gainCharge(){
			colddown+= hero.energybase();
			SkillRecharge bonus = target.buff(SkillRecharge.class);
			if (bonus != null ){
				colddown += 20;
			}
		}		
		
	}	
	
}
