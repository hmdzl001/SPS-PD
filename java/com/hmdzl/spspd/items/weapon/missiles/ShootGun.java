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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ShootGun extends Weapon {
	
	public static final String AC_SHOOT		= "SHOOT";
	public static final String AC_ENDSHOOT		= "ENDSHOOT";
	public static final String AC_RELOAD		= "RELOAD";
	
    public int charge = 0;
	public int fullcharge = 3;
	
	public ShootGun() {
		super();

		STR = 10;

		MIN = 5;
		MAX = 10;

		stackable = false;
		
		unique = true;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		reinforced = true;
	}

	public Item upgrade() {
		MIN += 3;
		MAX += 5;
		return super.upgrade();
	}
	
	
	{
		image = ItemSpriteSheet.SHOOTGUN ;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		
		 
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		//actions.remove(AC_EQUIP);
		actions.add(AC_SHOOT);
		actions.add(AC_ENDSHOOT);
		actions.add(AC_RELOAD);
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
               if (charge<1){
					charge = fullcharge;
					hero.sprite.showStatus(CharSprite.DEFAULT,  Messages.get(this, "reloading"));
					hero.spendAndNext(1.5f);
				} else GameScene.selectCell( shooter );
		} else if (action.equals(AC_ENDSHOOT)) {
               if (charge<1){
					charge = fullcharge;
					hero.sprite.showStatus(CharSprite.DEFAULT,  Messages.get(this, "reloading"));
					hero.spendAndNext(1.5f);
				} else GameScene.selectCell( shooter2 );
		} else if(action.equals(AC_RELOAD)){
            if (charge == fullcharge){
				GLog.n(Messages.get(ToyGun.class,"full"));
			} else {
				float reloadtime = (fullcharge - charge)/2;
				hero.spendAndNext(reloadtime*1f);
				hero.sprite.showStatus(CharSprite.DEFAULT,  Messages.get(this, "reloading"));
				charge = fullcharge;

			}
		}
	}

	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( CHARGE, charge );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt( CHARGE );
	}

	@Override
	public int damageRoll(Hero owner)	{
		return 0;
	}

	public int damageRoll2(Hero owner)	{
		return Random.Int(MAX,MIN);
	}
	
   @Override
    public void proc(Char attacker, Char defender, int damage) {

		int oppositeDefender = defender.pos + (defender.pos - attacker.pos);
		Ballistica trajectory = new Ballistica(defender.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(defender, trajectory, 1);
    }	
	
	@Override
	public String info() {
		String info = desc();


        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }
		info += "\n\n" + Messages.get(this, "damage",MIN,MAX);
		info += "\n\n" + Messages.get(this, "charge",charge,3);

		return info;
	}
	
	@Override
	public String status() {
		if (levelKnown) {
			return charge + "/" + fullcharge;
		} else {
			return null;
		}
	}	

	private int targetPos;

	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	public ShootAmmo Ammo(){
		return new ShootAmmo();
	}
	
	public ShootEndAmmo Ammo2(){
		return new ShootEndAmmo();
	}	
	
	public class ShootAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.AMMO_M;
		}

		public int damageRoll(Hero owner) {
			return ShootGun.this.damageRoll2(owner);
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
            Buff.affect(defender,ArmorBreak.class, 5f).level(30);
			super.proc(attacker, defender, damage);
		}		
		
		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			ShootGun.this.targetPos = cell;
			charge--;
				super.cast(user, dst);
		}
	}
	
	public class ShootEndAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.AMMO_L;
			ACU = 1000;
		}

		public int damageRoll(Hero owner) {
			return ShootGun.this.damageRoll2(owner);
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
            int p = defender.pos;
		    for (int n : Floor.NEIGHBOURS8) {
			Char ch = Actor.findChar(n+p);
			if (ch != null && ch != defender && ch != attacker && ch.isAlive()) {

				int dr = Random.IntRange( 0, 1 );
				int dmg = Random.Int( MIN, MAX );
				int effectiveDamage = Math.max( dmg - dr, 0 );
                Buff.affect(ch,ArmorBreak.class, 5f).level(30);
				ch.damage( effectiveDamage, attacker,2 );
			}
		}
			if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)){
				defender.damage(Math.min(defender.HT - defender.HP,defender.HT/6),this,2);
			} else {
				defender.damage(Math.min(defender.HT - defender.HP,defender.HT/3),this,2);
		    }   
			super.proc(attacker, defender, damage);
		}		
		
		int flurryCount = -1;
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			ShootGun.this.targetPos = cell;
			charge = 0;
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
			return Messages.get(ToyGun.class, "prompt");
		}
	};
	private CellSelector.Listener shooter2 = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Ammo2().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(ToyGun.class, "prompt");
		}
	};	
	
}