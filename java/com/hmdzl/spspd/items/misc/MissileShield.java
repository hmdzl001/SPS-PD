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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
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
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MissileShield extends Item {

	{
		//name = "MissileShield";
		image = ItemSpriteSheet.WARRIORSHIELD;
	
		stackable = false;
		unique = true;
		defaultAction = AC_CAST;
	}

	private static final String AC_CAST = "CAST";
    private static final String AC_SHIELD = "SHIELD";
	public final int fullCharge = 10;
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
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		if (charge > 9){
		actions.add(AC_CAST);
		}
		if (charge > 5){
		actions.add(AC_SHIELD);
		}
		actions.remove( AC_THROW );
		actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_CAST)) {
			curUser = hero;
			if (charge < 10) {
				GLog.i(Messages.get(this, "rest"));
			} else GameScene.selectCell(shooter);
		} else if (action.equals(AC_SHIELD)) {
			curUser = hero;
			if (charge < 5) {
				GLog.i(Messages.get(this, "rest"));
				return;
			} else {
			Buff.prolong(hero, DefenceUp.class, 3f).level(50);
			Buff.affect(hero, ShieldArmor.class).level(hero.lvl);
			charge -= 5;
			}
		}

	}

	@Override
	public String desc() {
		String info = super.desc();
		info += "\n\n" + Messages.get(MissileShield.class, "damage",min(),max());
		info += "\n\n" + Messages.get(MissileShield.class, "charge",charge,fullCharge);
		return info;
	}

	public int level() {
		return Dungeon.hero == null ? 0 : Dungeon.hero.lvl/5;
	}

	@Override
	public int visiblyUpgraded() {
		return level();
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public int min() {
		return 1 + Dungeon.hero.lvl/5;
	}

	public int max() {
		return 1 + Dungeon.hero.lvl/2;
	}
	
	public int damageRoll(Hero owner) {
		int damage = Random.Int(min(), max());
		return Math.round(damage);
	}
	
	private int targetPos;
	
	public MissileShieldAmmo Ammo(){
		return new MissileShieldAmmo();
	}
	
	public class MissileShieldAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.WARRIORSHIELD;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return MissileShield.this.damageRoll(owner);
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
				defender.damage(damage,this);
			}
			Buff.prolong(defender, Paralysis.class, 3);
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			MissileShield.this.targetPos = cell;
			charge-=10;
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
			return Messages.get(MissileShield.class, "prompt");
		}
	};
}

