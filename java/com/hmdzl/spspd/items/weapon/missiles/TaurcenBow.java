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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TaurcenBow extends Weapon {

	public static final String AC_SHOOT	 = "SHOOT";
	public static final String AC_BREAK	= "BREAK";
    public static final String AC_FIRE	= "FIRE";
	public static final String AC_ICE	= "ICE";
	public static final String AC_POSION	= "POSION";
	public static final String AC_ELE	= "ELE";

	{
		//name = "TaurcenBow";
		image = ItemSpriteSheet.BOW;

		STR = 10;

		MIN = 4;
		MAX = 8;
		
		stackable = false;
		
		unique = true;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		reinforced = true;
	}

	public enum Arrow {
		NONE, FIRE, ICE, POSION, ELE
	}

	public Arrow arrow = Arrow.NONE;
	
	public static int charge = 0;
	private static final String CHARGE = "charge";
	private static final String ARROW = "arrow";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( ARROW, arrow );
		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		arrow = bundle.getEnum( ARROW, Arrow.class );
		charge = bundle.getInt(CHARGE);
	}	

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}	
	
	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		actions.add(AC_SHOOT);
        actions.add(AC_BREAK);
		actions.add(AC_FIRE);
		actions.add(AC_ICE);
		actions.add(AC_POSION);
        actions.add(AC_ELE);
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
		GameScene.selectCell( shooter );
		}
		if (action.equals(AC_BREAK)) {
			curUser = hero;
			this.arrow = TaurcenBow.Arrow.NONE;
		}
		if (action.equals(AC_FIRE)) {
			curUser = hero;
			this.arrow = TaurcenBow.Arrow.FIRE;
		}
		if (action.equals(AC_ICE)) {
			curUser = hero;
			this.arrow = TaurcenBow.Arrow.ICE;
		}
		if (action.equals(AC_POSION)) {
			curUser = hero;
			this.arrow = TaurcenBow.Arrow.POSION;
		}
		if (action.equals(AC_ELE)) {
			curUser = hero;
			this.arrow = TaurcenBow.Arrow.ELE;
		}
	}

	
	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 3;
		MAX += 5;
		super.upgrade(enchant);
		updateQuickslot();
		return this;
	}	
	
 	@Override
	public Item degrade() {
		return super.degrade();
	}
	
	public int damageRoll(Hero owner) {
		int damage = Random.Int(MIN, MAX);

		float bonus = 0;
		for (Buff buff : owner.buffs(RingOfSharpshooting.Aim.class)) {
				bonus += ((RingOfSharpshooting.Aim) buff).level;
		}
		if (Dungeon.hero.buff(TargetShoot.class)!= null)
			bonus += 10;
		if (Dungeon.hero.buff(MechArmor.class)!= null)
			bonus += 10;
		damage = (int)(damage*(1 + 0.05*bonus));
		return Math.round(damage);
	}	
	
	
	@Override
	public String info() {
		String info = desc();

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }
		info += "\n\n" + Messages.get(this, "damage",MIN,MAX);
		info += "\n\n" + Messages.get(this, "charge",charge,8);

		return info;
	}


	@Override
	public String status() {
		if (levelKnown) {
			//return charge + "/" + "8";
			return charge + "/8";
		} else {
			return null;
		}
	}

	private int targetPos;
	
	public TaurcenBowArrow Arrow(){
		return new TaurcenBowArrow();
	}
	
	public class TaurcenBowArrow extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.POSION_DART;
			enchantment = TaurcenBow.this.enchantment;
		}



		public int damageRoll(Hero owner) {
			return TaurcenBow.this.damageRoll(owner);
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
			int DMG = damage;
			if (TaurcenBow.charge > 7 ) {
				if (arrow == Arrow.NONE) {
					if (defender.isAlive()) Buff.affect(defender, ArmorBreak.class,5f).level(40);
					defender.damage(DMG,this);
					TaurcenBow.charge = 0;
				}
				if (arrow == Arrow.FIRE) {
					if (defender.isAlive())Buff.affect(defender, Burning.class).reignite(defender);
					defender.damage(DMG/2,this);
					TaurcenBow.charge = 0;
				}
				if (arrow == Arrow.ICE) {
					defender.damage(DMG/2,this);
					if (defender.isAlive()) {
						Buff.prolong(defender, Wet.class, 5f);
						Buff.prolong(defender, Slow.class, 5f);
					}
					TaurcenBow.charge = 0;
				}
				if (arrow == Arrow.POSION) {
					defender.damage(DMG/4,this);
					if (defender.isAlive())Buff.affect(defender, Ooze.class);
					TaurcenBow.charge = 0;
				}
				if (arrow == Arrow.ELE) {
					if (defender.isAlive())Buff.affect(defender, Shocked.class,3f);
					Buff.affect(attacker, AttackUp.class,10f).level(30);
					defender.damage(DMG/3,this);
					TaurcenBow.charge = 0;
				}

				if (Random.Int(8) ==  0) {
					if (arrow == Arrow.NONE) {
						if (defender.isAlive())
							Buff.affect(defender, ArmorBreak.class, 5f).level(40);
						defender.damage(DMG, this);
					}
					if (arrow == Arrow.FIRE) {
						if (defender.isAlive())
							Buff.affect(defender, Burning.class).reignite(defender);
						defender.damage(DMG / 2, this);

					}
					if (arrow == Arrow.ICE) {
						defender.damage(DMG / 2, this);
						if (defender.isAlive()) {
							Buff.prolong(defender, Wet.class, 5f);
							Buff.prolong(defender, Slow.class, 5f);
						}

					}
					if (arrow == Arrow.POSION) {
						defender.damage(DMG / 4, this);
						if (defender.isAlive()) Buff.affect(defender, Ooze.class);

					}
					if (arrow == Arrow.ELE) {
						if (defender.isAlive()) Buff.affect(defender, Shocked.class, 3f);
						Buff.affect(attacker, AttackUp.class, 10f).level(30);
						defender.damage(DMG / 3, this);

					}
				}
			}

			if (enchantment != null) {
				enchantment.proc(TaurcenBow.this, attacker, defender, damage);
			}
			TaurcenBow.charge++;
			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			TaurcenBow.this.targetPos = cell;
				super.cast(user, dst);
		}
	}
	
	private CellSelector.Listener shooter = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {
				Arrow().cast(curUser, target);
			}
		}
		@Override
		public String prompt() {
			return Messages.get(ToyGun.class, "prompt");
		}
	};
}