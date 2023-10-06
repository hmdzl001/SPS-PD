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
package com.hmdzl.spspd.items.weapon;

import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.TargetShoot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.items.rings.RingOfFuror;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.enchantments.AresLeech;
import com.hmdzl.spspd.items.weapon.enchantments.CromLuck;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEarth2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEnergy;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentEnergy2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight2;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock2;
import com.hmdzl.spspd.items.weapon.enchantments.JupitersHorror;
import com.hmdzl.spspd.items.weapon.enchantments.LokisPoison;
import com.hmdzl.spspd.items.weapon.enchantments.NeptuneShock;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MeleeThrowWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.reflect.Array.newInstance;

public class Weapon extends KindOfWeapon {

	private static final String TXT_IDENTIFY = "You are now familiar enough with your %s to identify it. It is %s.";
	//private static final String TXT_INCOMPATIBLE = "Interaction of different types of magic has negated the enchantment on this weapon!";
	private static final String TXT_TO_STRING = "%s :%d";
	//private static final int HITS_TO_KNOW    = 5;
	public int STR = 10;
	public float ACU = 1f; // Accuracy modifier
	public float DLY = 1f; // SpeedUp modifier
	public int   RCH = 1;  // Reach modifier
	//public int   DUR = 10;  // durable modifier
	
	public int durable = 100;

	public boolean isdestory = false;

	public Enchantment enchantment;

	//@Override
	public void proc(Char attacker, Char defender, int damage) {


		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);	
		}

		/*if (!levelKnown) {
			if (--hitsToKnow <= 0) {
				levelKnown = true;
				GLog.i( Messages.get(Weapon.class, "identify", name(), toString()) );
				Badges.validateItemLevelAquired( this );
			}
		}*/
	}

	private static final String ENCHANTMENT = "enchantment";

    public int STR()
    {
        if(Dungeon.hero != null  && (this instanceof MeleeWeapon || this instanceof RelicMeleeWeapon ) &&
				Dungeon.hero.belongings.weapon == this && STR > 2 &&
				(Dungeon.hero.heroClass == HeroClass.ROGUE || Dungeon.hero.subClass == HeroSubClass.ARTISAN))
          return STR - 2;
        else return STR;
    }

	public boolean durable(){
		return Dungeon.isChallenged(Challenges.ABRASION);
	}
	
    private static final String DURABLE= "durable";	
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ENCHANTMENT, enchantment);
		bundle.put(DURABLE, durable);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		enchantment = (Enchantment) bundle.get(ENCHANTMENT);
		durable = bundle.getInt(DURABLE);
	}

	@Override
	public float acuracyFactor(Hero hero) {

		int encumbrance = STR() - hero.STR();

		float ACU = this.ACU;

		if (this instanceof MissileWeapon) {
			switch (hero.heroClass) {
			//case WARRIOR:
				//encumbrance += 3;
				//break;
			//case HUNTRESS:
                //encumbrance -= 2;
             //   break;
                default:
			}
			int bonus = 0;
			for (Buff buff : hero.buffs(RingOfSharpshooting.Aim.class)) {
				bonus += ((RingOfSharpshooting.Aim) buff).level;
			}
			ACU *= (float) (Math.pow(1.1, bonus));
		}

		return encumbrance > 0 ? (float) (ACU / Math.pow(1.5, encumbrance))
				: ACU;
	}


	@Override
	public float speedFactor(Hero hero) {

		int encumrance = STR() - hero.STR();
		if (this instanceof MissileWeapon
				&& hero.heroClass == HeroClass.HUNTRESS) {
			encumrance -= 2;
		}

		float DLY = this.DLY;
		int bonus = 0;
		for (Buff buff : hero.buffs(RingOfFuror.Furor.class)) {
			bonus += ((RingOfFuror.Furor) buff).level;
		}

		DLY = (float)( DLY / Math.min( 4, 1 + bonus * 1.00 / 10) );

		return (encumrance > 0 ? (float) (DLY * Math.pow(1.2, encumrance))
				: DLY);
	}

	@Override
	public int reachFactor(Hero hero) {
		//return RCH;
		
	    int RCH = this.RCH;
		
		int bonus = 0;
		for (Buff buff : hero.buffs(RingOfAccuracy.Accuracy.class)) {
			bonus += Math.min(((RingOfAccuracy.Accuracy) buff).level,30);
		}
        if (Dungeon.hero.subClass == HeroSubClass.JOKER){
			bonus += 10;
		}
		if (hero.buff(MechArmor.class) != null){
			bonus += 10;
		}
		RCH += bonus/10;
        return RCH;
	}
	
	@Override
	public int damageRoll(Hero hero) {

		int damage = super.damageRoll(hero);

		int exStr = hero.STR() - STR();
		if (exStr > 0) {
			damage += exStr;
		}

		if (this instanceof MissileWeapon) {
			if (Dungeon.hero.buff(TargetShoot.class)!= null)
				 damage = (int)(damage*1.5f);
			
			float bonus = 0;
			for (Buff buff : hero.buffs(RingOfSharpshooting.Aim.class)) {
				bonus += Math.min(((RingOfSharpshooting.Aim) buff).level,30);
			}
			if (Random.Int(10) < 3  &&  bonus > 0 ) {
			damage = (int)(damage * ( 1.5 + 0.25 * bonus));
			hero.sprite.emitter().burst(Speck.factory(Speck.STAR),8);
		    }
			
			if (this instanceof MeleeThrowWeapon && hero.damagetwice){
				damage = damage*2;
			}
			
		}

		return Math.round(damage);
	}

	public Item upgrade(boolean enchant) {
		
		if (enchant){
		   if (enchantment != null) {
				enchantAdv();
		   } else {
				enchant();
		   }
		}

		return super.upgrade();
	}

	@Override
	public String toString() {
		return levelKnown ? Messages.format(TXT_TO_STRING, super.toString(), STR())
				: super.toString();
	}

	@Override
	public String name() {
		return enchantment == null ? super.name() : enchantment.name(super.name());
	}

	@Override
	public Item random() {
		if (Random.Float() < 0.4) {
			int n = 1;
			if (Random.Int(3) == 0) {
				n++;
				if (Random.Int(3) == 0) {
					n++;
				}
			}
			if (Random.Int(2) == 0) {
				upgrade(n);
			} else {
				degrade(n);
				cursed = true;
			}
		}
		return this;
	}

	public Weapon enchant(Enchantment ench) {
		enchantment = ench;
		return this;
	}

	
	public Weapon enchant() {

		Class<? extends Enchantment> oldEnchantment = enchantment != null ? enchantment.getClass() : null;
		Enchantment ench = Enchantment.random();
		while (ench.getClass() == oldEnchantment) {
			ench = Enchantment.random();
		}
		
		return enchant(ench);
	}
	
	public Weapon enchantAdv() {

		Class<? extends Enchantment> oldEnchantment = enchantment != null ? enchantment.getClass() : null;
		Enchantment ench = Enchantment.randomAdv();
		while (ench.getClass() == oldEnchantment) {
			ench = Enchantment.randomAdv();
		}
		
		return enchant(ench);
	}
	
	public Weapon enchantLow() {

		Class<? extends Enchantment> oldEnchantment = enchantment != null ? enchantment.getClass() : null;
		Enchantment ench = Enchantment.randomLow();
		while (ench.getClass() == oldEnchantment) {
			ench = Enchantment.randomLow();
		}
		
		return enchant(ench);
	}
	
	public Weapon enchantLuck() {

		Enchantment ench = Enchantment.Luck();
		return enchant(ench);
	}
	
	public Weapon enchantNeptune() {

		Enchantment ench = Enchantment.Neptune();
		return enchant(ench);
	}
	
	public Weapon enchantAres() {

		Enchantment ench = Enchantment.Ares();
		return enchant(ench);
	}
	

	public Weapon enchantJupiter() {

		Enchantment ench = Enchantment.Jupiter();
		return enchant(ench);
	}
	public Weapon enchantLoki() {

		Enchantment ench = Enchantment.Loki();
		return enchant(ench);
	}
	
	public boolean isEnchanted() {
		return enchantment != null;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return enchantment != null ? enchantment.glowing() : null;
	}

	public static abstract class Enchantment implements Bundlable {

		private static final Class<?>[] enchants = new Class<?>[] { EnchantmentFire.class,
				EnchantmentEarth.class, EnchantmentDark.class, EnchantmentEnergy.class,
				EnchantmentIce.class, EnchantmentShock.class, EnchantmentLight.class,
				EnchantmentFire2.class,
				EnchantmentEarth2.class, EnchantmentDark2.class, EnchantmentEnergy2.class,
				EnchantmentIce2.class, EnchantmentShock2.class, EnchantmentLight2.class,
				};
				
		private static final Class<?>[] randomA = new Class<?>[] { EnchantmentFire.class,
				EnchantmentEarth.class, EnchantmentDark.class, EnchantmentEnergy.class,
				EnchantmentIce.class, EnchantmentShock.class, EnchantmentLight.class,
				EnchantmentFire2.class,
				EnchantmentEarth2.class, EnchantmentDark2.class, EnchantmentEnergy2.class,
				EnchantmentIce2.class, EnchantmentShock2.class, EnchantmentLight2.class,
				};
		private static final float[] chances = new float[] { 5, 5, 5, 5, 5,5,
				5, 5, 5, 5, 5, 5,
				5, 5};
		
		private static final float[] chancesLow = new float[] { 3, 3, 3, 3, 3, 3,
				3, 3, 3, 3, 3, 3,
			3, 3};
		
		private static final float[] chancesAdv = new float[] { 2, 2, 2, 2, 2,2,
				2, 2, 2, 2, 2, 2,
			2, 2 };

		private static final Class<?>[] relicenchants = new Class<?>[] {  NeptuneShock.class,
			CromLuck.class, AresLeech.class, JupitersHorror.class, LokisPoison.class};

		private static final float[] chancesAres = new float[] { 0, 0, 1, 0, 0 };
		private static final float[] chancesNeptune = new float[] { 1, 0, 0, 0, 0 };
		private static final float[] chancesLuck = new float[] { 0, 1, 0, 0, 0 };
		private static final float[] chancesJupiter = new float[] { 0, 0, 0, 1, 0 };
		private static final float[] chancesLoki = new float[] { 0, 0, 0, 0, 1 };
		
		public abstract boolean proc(Weapon weapon, Char attacker,
				Char defender, int damage);
		

		public abstract boolean proc(RelicMeleeWeapon weapon, Char attacker,
				Char defender, int damage);

		public String name() {
				return name( Messages.get(this, "glyph") );		
		}						
				
		public String name( String weaponName ) {
			return Messages.get(this, "name", weaponName);
		}

		public String desc() {
			return Messages.get(this, "desc");
		}


		@Override
		public void restoreFromBundle(Bundle bundle) {
		}

		@Override
		public void storeInBundle(Bundle bundle) {
		}

		public ItemSprite.Glowing glowing() {
			return ItemSprite.Glowing.WHITE;
		}

		@SuppressWarnings("unchecked")
		public static Enchantment Chooserandom( Class<? extends Enchantment> ... toIgnore ) {

			return randomA( toIgnore );
			
		}

		@SuppressWarnings("unchecked")
		public static Enchantment randomA( Class<? extends Enchantment> ... toIgnore ){
			ArrayList<Class<?>> ench = new ArrayList<>(Arrays.asList(randomA));
			ench.removeAll(Arrays.asList(toIgnore));
			if (ench.isEmpty()) {
				return random();
			} else {
				return (Enchantment)newInstance(Random.element(ench));
			}
		}		
		
		@SuppressWarnings("unchecked")
		public static Enchantment random() {
			try {
				return ((Class<Enchantment>) enchants[Random.chances(chances)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		@SuppressWarnings("unchecked")
		public static Enchantment randomAdv() {
			try {
				return ((Class<Enchantment>) enchants[Random.chances(chancesAdv)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		@SuppressWarnings("unchecked")
		public static Enchantment randomLow() {
			try {
				return ((Class<Enchantment>) enchants[Random.chances(chancesLow)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Enchantment Luck() {
			try {
				return ((Class<Enchantment>) relicenchants[Random.chances(chancesLuck)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		public static Enchantment Ares() {
			try {
				return ((Class<Enchantment>) relicenchants[Random.chances(chancesAres)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Enchantment Jupiter() {
			try {
				return ((Class<Enchantment>) relicenchants[Random.chances(chancesJupiter)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Enchantment Loki() {
			try {
				return ((Class<Enchantment>) relicenchants[Random.chances(chancesLoki)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
		
		@SuppressWarnings("unchecked")
		public static Enchantment Neptune() {
			try {
				return ((Class<Enchantment>) relicenchants[Random.chances(chancesNeptune)])
						.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
	}
}
