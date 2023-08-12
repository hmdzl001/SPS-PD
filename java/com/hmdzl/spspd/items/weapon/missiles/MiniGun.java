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
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.missiles.throwing.EscapeKnive;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.MissileSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashMap;

public class MiniGun extends Weapon {
	
	public static final String AC_SHOOT		= "SHOOT";
	public static final String AC_RELOAD		= "RELOAD";
	
    public int charge = 0;
	public int fullcharge = 3;
	
	public MiniGun() {
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
	
	public class ShootAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.AMMO;
		}

		public int damageRoll(Hero owner) {
			return MiniGun.this.damageRoll2(owner);
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
			MiniGun.this.targetPos = cell;
			//charge--;
			minicheck( cell);

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

	public void minicheck( int cell ) {
		final HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();
		Item proto = new EscapeKnive();
		for (int n : Level.NEIGHBOURS9DIST2) {
			int c = cell + n;
			final Char target;

			if ( (target = Actor.findChar(c)) != null && target instanceof Mob ) {
				final Char maintarget = Actor.findChar(cell);
					Callback callback = new Callback() {
						@Override
						public void call() {
							if (maintarget != null) {
								miniattack(maintarget);
								if (!maintarget.isAlive()){
									miniattack(target);
								}
							} else {
								miniattack(target);
							}
							//curUser.attack(targets.get(this));
							targets.remove(this);
							if (targets.isEmpty()) {
								curUser.spendAndNext(1f);
							}
						}
					};
					((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class)).reset(curUser.pos,
							maintarget != null ? maintarget.isAlive() ? maintarget.pos : target.pos : target.pos,
							proto, callback);
					targets.put(callback, (Mob)target);
				}

		}
	}

	public void miniattack(Char ch) {
		curUser.attack(ch);
	}
}