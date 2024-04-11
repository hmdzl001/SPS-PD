/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class GunOfSoldier extends Item {

	public static final String AC_USE = "USE";

	private static final float TIME_TO_DIG = 1f;

	{
		image = ItemSpriteSheet.SOLDIER_GUN;
		defaultAction = AC_USE;
		unique = true;
		usesTargeting = true;
	}
	
	public final int fullCharge = 225;
	public int charge = 0;
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
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (charge >= 75){
		actions.add(AC_USE);
		}
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {		
      if( action.equals( AC_USE ) ){
		  curUser = hero;
	     if (charge < 75) {
			  GLog.i(Messages.get(GunOfSoldier.class, "break"));
		  } else
		  	GameScene.selectCell( shooter );
		} else {
			super.execute(hero, action);
			
		}
	}

	@Override
	public String status() {
		return Messages.format("%d", charge /75);
	}
	@Override
	public int price() {
		return 30 * quantity;
	}	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(GunOfSoldier.class, "charge",charge,fullCharge);
		return info;	
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	private int targetPos;
	
	public SoldierAmmo Ammo(){
		return new SoldierAmmo();
	}
	
	public class SoldierAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.AMMO_M;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return 0;
		}

		@Override
		protected void onThrow( int cell ) {
			Char enemy = Actor.findChar( cell );
			if (enemy == null || enemy == curUser) {
				parent = null;
				Splash.at( cell, 0xCC99FFFF, 1 );
			} else {
				if (!curUser.shoot( enemy, this )) {
					Splash.at(cell, 0xCC99FFFF, 1);
				}
			}
		}

		@Override
		public void proc(Char attacker, Char defender, int damage) {

            if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)){
				defender.damage(Math.min(defender.HT - defender.HP,defender.HT/6),this);
			} else {
				defender.damage(Math.min(defender.HT - defender.HP,defender.HT/3),this);
			//defender.damage(defender.HT,this);
		   }
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			GunOfSoldier.this.targetPos = cell;
			charge-=75;
			super.cast(user, dst);
		}

	}
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Ammo().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(GunOfSoldier.class, "prompt");
		}
	};
}
