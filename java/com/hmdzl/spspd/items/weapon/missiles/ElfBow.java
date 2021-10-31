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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.special.DemonBlade;
import com.hmdzl.spspd.items.weapon.spammo.SpAmmo;
import com.hmdzl.spspd.scenes.CellSelector;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndBag;
import com.hmdzl.spspd.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

import java.util.ArrayList;

public class ElfBow extends Weapon {

	public static final String AC_SHOOT	 = "SHOOT";
	public static final String AC_DRINK	 = "DRINK";

	{
		//name = "ElfBow";
		image = ItemSpriteSheet.ELF_BOW;

		STR = 10;

		MIN = 2;
		MAX = 6;
		
		stackable = false;
		unique = true;
		
		defaultAction = AC_SHOOT;
		usesTargeting = true;
		reinforced = true;
	}

	public static int charge = 0;
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
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public Item upgrade() {
		return upgrade(false);
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
	
		actions.add(AC_SHOOT);
	    
        actions.add(AC_DRINK);		
 
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_SHOOT)) {
			if (!isEquipped( hero ))
				GLog.i( Messages.get(ToyGun.class, "need_to_equip") );
			else GameScene.selectCell( shooter );
		} 
	    if (action.equals(AC_DRINK)) {
			if (!isEquipped( hero )){
				GLog.i( Messages.get(ToyGun.class, "need_to_equip") );
			} else {
            hero.spp += 10;
			charge++;
			if (charge > 10) {
				Dungeon.level.drop(new DemonBlade().upgrade(3), Dungeon.hero.pos).sprite.drop();
				hero.belongings.weapon = null;
			    updateQuickslot();
			}
			}
		} 
	}

	
	@Override
	public Item upgrade(boolean enchant) {
		
		MIN += 1;
		MAX += 3;
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

		damage = (int)(damage*(1 + 0.05*bonus ) * (1 + 0.1 * Dungeon.hero.magicSkill()));


		return Math.round(damage);
	}	
	
	
	@Override
	public String info() {
		String info = desc();

        if (reinforced) {
            info += "\n" + Messages.get(Item.class, "reinforced");
        }
		info += "\n\n" + Messages.get(this, "damage",MIN,MAX);

		return info;
	}
	

	private int targetPos;
	
	public ElfBowAmmo Ammo(){
		return new ElfBowAmmo();
	}
	
	public class ElfBowAmmo extends MissileWeapon {
		
		{
			image = ItemSpriteSheet.POSION_DART;
			enchantment = ElfBow.this.enchantment;
		}

		public int damageRoll(Hero owner) {
			return ElfBow.this.damageRoll(owner);
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

			if (enchantment != null) {
				enchantment.proc(ElfBow.this, attacker, defender, damage);
			}

			if (damage > defender.HP){
				Dungeon.hero.spp++;
			}

			super.proc(attacker, defender, damage);
		}
		
		@Override
		public void cast(final Hero user, final int dst) {
			final int cell = throwPos( user, dst );
			ElfBow.this.targetPos = cell;
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
}