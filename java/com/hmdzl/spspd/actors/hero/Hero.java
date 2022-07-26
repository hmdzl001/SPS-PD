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
package com.hmdzl.spspd.actors.hero;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.GamesInProgress;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.NmGas;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Arcane;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Barkskin;
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.BeTired;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.BloodAngry;
import com.hmdzl.spspd.actors.buffs.BoxStar;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Combo;
import com.hmdzl.spspd.actors.buffs.DBurning;
import com.hmdzl.spspd.actors.buffs.DamageUp;
import com.hmdzl.spspd.actors.buffs.DeadRaise;
import com.hmdzl.spspd.actors.buffs.DelayProtect;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.Drowsy;
import com.hmdzl.spspd.actors.buffs.Dry;
import com.hmdzl.spspd.actors.buffs.Fury;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.GoldTouch;
import com.hmdzl.spspd.actors.buffs.HTimprove;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HighAttack;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.buffs.HighVoice;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.MirrorShield;
import com.hmdzl.spspd.actors.buffs.Muscle;
import com.hmdzl.spspd.actors.buffs.NewCombo;
import com.hmdzl.spspd.actors.buffs.Notice;
import com.hmdzl.spspd.actors.buffs.OnePunch;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.ParyAttack;
import com.hmdzl.spspd.actors.buffs.Regeneration;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.SnipersMark;
import com.hmdzl.spspd.actors.buffs.Strength;
import com.hmdzl.spspd.actors.buffs.SuperArcane;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.WarGroove;
import com.hmdzl.spspd.actors.buffs.faithbuff.BalanceFaith;
import com.hmdzl.spspd.actors.buffs.faithbuff.DemonFaith;
import com.hmdzl.spspd.actors.buffs.faithbuff.HumanFaith;
import com.hmdzl.spspd.actors.buffs.faithbuff.LifeFaith;
import com.hmdzl.spspd.actors.buffs.faithbuff.MechFaith;
import com.hmdzl.spspd.actors.buffs.mindbuff.AmokMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.CrazyMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.HopeMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.KeepMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.LoseMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.TerrorMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.WeakMind;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.SommonSkeleton;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.CheckedCell;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Amulet;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.DewVial;
import com.hmdzl.spspd.items.Dewdrop;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Heap.Type;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.KindofMisc;
import com.hmdzl.spspd.items.OrbOfZot;
import com.hmdzl.spspd.items.armor.glyphs.Iceglyph;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.artifacts.Pylon;
import com.hmdzl.spspd.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.keys.GoldenKey;
import com.hmdzl.spspd.items.keys.GoldenSkeletonKey;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.keys.Key;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.Ankhshield;
import com.hmdzl.spspd.items.misc.AttackShield;
import com.hmdzl.spspd.items.misc.AutoPotion.AutoHealPotion;
import com.hmdzl.spspd.items.misc.BShovel;
import com.hmdzl.spspd.items.misc.BigBattery;
import com.hmdzl.spspd.items.misc.CopyBall;
import com.hmdzl.spspd.items.misc.DanceLion;
import com.hmdzl.spspd.items.misc.DiceTower;
import com.hmdzl.spspd.items.misc.FishBone;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.misc.GhostGirlRose;
import com.hmdzl.spspd.items.misc.GunOfSoldier;
import com.hmdzl.spspd.items.misc.HealBag;
import com.hmdzl.spspd.items.misc.HorseTotem;
import com.hmdzl.spspd.items.misc.JumpA;
import com.hmdzl.spspd.items.misc.JumpF;
import com.hmdzl.spspd.items.misc.JumpH;
import com.hmdzl.spspd.items.misc.JumpM;
import com.hmdzl.spspd.items.misc.JumpP;
import com.hmdzl.spspd.items.misc.JumpR;
import com.hmdzl.spspd.items.misc.JumpS;
import com.hmdzl.spspd.items.misc.JumpW;
import com.hmdzl.spspd.items.misc.Jumpshoes;
import com.hmdzl.spspd.items.misc.LeaderFlag;
import com.hmdzl.spspd.items.misc.MissileShield;
import com.hmdzl.spspd.items.misc.PotionOfMage;
import com.hmdzl.spspd.items.misc.RangeBag;
import com.hmdzl.spspd.items.misc.SavageHelmet;
import com.hmdzl.spspd.items.misc.SeriousPunch;
import com.hmdzl.spspd.items.misc.Shovel;
import com.hmdzl.spspd.items.misc.UndeadBook;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.items.rings.RingOfForce;
import com.hmdzl.spspd.items.rings.RingOfFuror;
import com.hmdzl.spspd.items.rings.RingOfHaste;
import com.hmdzl.spspd.items.rings.RingOfMagic;
import com.hmdzl.spspd.items.rings.RingOfMight;
import com.hmdzl.spspd.items.rings.RingOfTenacity;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.AlchemyPot;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.IronMaker;
import com.hmdzl.spspd.levels.features.Sign;
import com.hmdzl.spspd.levels.features.TentRoom;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Sungrass;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.scenes.SurfaceScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.ui.AttackIndicator;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndAscend;
import com.hmdzl.spspd.windows.WndDescend;
import com.hmdzl.spspd.windows.WndLifeTradeItem;
import com.hmdzl.spspd.windows.WndMessage;
import com.hmdzl.spspd.windows.WndResurrect;
import com.hmdzl.spspd.windows.WndTradeItem;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collections;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;
import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public class Hero extends Char {

	private static final String TXT_VALUE = "%+d";

	public static final int STARTING_STR = 10;
	public static final int STARTING_MAGIC = 0;


	private static final float TIME_TO_REST = 1f;
	private static final float TIME_TO_SEARCH = 2f;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;

	public int hitSkill = 10;
	public int evadeSkill = 5;
	public int magicSkill = 0;
	public int spp = 0;

	public boolean ready = false;
	
	public boolean haspet = false;
	public boolean petfollow = false;
	public int petType = 0;
	public int petLevel = 0;
	public int petHP = 0;
	public int petExperience = 0;
	public int petCooldown = 0;

	private boolean damageInterrupt = true;
	public HeroAction curAction = null;
	public HeroAction lastAction = null;

	private Char enemy;

	private Item theKey;
	private Item theSkeletonKey;

	public boolean restoreHealth = false;

	public MissileWeapon rangedWeapon = null;
	public Belongings belongings;

	public int STR;
	public boolean weakened = false;

	public float awareness;

	public int lvl = 1;
	public int exp = 0;

	private ArrayList<Mob> visibleEnemies;

	public Hero() {
		super();
		name = Messages.get(this, "name");

		TRUE_HT = HT = 30;
		HP = HT;
		STR = STARTING_STR;
		magicSkill = STARTING_MAGIC;
		
		awareness = 0.1f;

		belongings = new Belongings(this);

		visibleEnemies = new ArrayList<Mob>();
	}


	public void updateHT( boolean boostHP ){
		int curHT = HT;
		int EX_HT = 0;
		if (hero.buff(RingOfMight.Might.class) != null || hero.buff(HTimprove.class) != null ) {
            for (Buff buff : buffs(RingOfMight.Might.class)) {
                EX_HT += (int) (Math.min((TRUE_HT * RingOfMight.strengthBonus(this) / 15), TRUE_HT * 2));
            }
            for (Buff buff : buffs(HTimprove.class)) {
                EX_HT += (int) (TRUE_HT * 0.2);
            }
		} else EX_HT = 0;

		HT = TRUE_HT + EX_HT;
		//float multiplier = RingOfMight.HTMultiplier(this);
		//HT = Math.round(multiplier * HT);
		
		//if (buff(ElixirOfMight.HTBoost.class) != null){
			//HT += buff(ElixirOfMight.HTBoost.class).boost();
		//}
	
		if (boostHP){
			HP += Math.max(HT - curHT, 0);
		}
		HP = Math.min(HP, HT);
	}
	
	
	public int STR() {
		int STR = this.STR;

		for (Buff buff : buffs(RingOfMight.Might.class)) {
			STR += (int)(RingOfMight.strengthBonus(this)/5);
		}

		if (buff(Muscle.class)!= null)
			STR += 2 ;

		if (buff(AflyBless.class)!= null)
			STR += 1 ;

		return weakened ? STR - 3 : STR;
	}
	
	public int magicSkill() {
		int magicSkill = this.magicSkill;

		for (Buff buff : buffs(RingOfMagic.Magic.class)) {
			magicSkill += Math.min(30,((RingOfMagic.Magic) buff).level);
		}	
		if (subClass == HeroSubClass.BATTLEMAGE)
			magicSkill += 5;
		if (subClass == HeroSubClass.SUPERSTAR)
			magicSkill += 4;
	    if (subClass == HeroSubClass.HACKER)
			magicSkill += 5;		
		if (buff(Arcane.class)!= null)
			magicSkill += 10;
        if (buff(LoseMind.class)!= null)
            magicSkill -= 5;
		SuperArcane sarcane = buff(SuperArcane.class);
		if (sarcane!= null)
			magicSkill += Math.max(5,sarcane.level());

		return magicSkill;
	}	

	private static final String HIT_SKILL = "hitSkill";
	private static final String EVADE_SKILL = "evadeSkill";
	private static final String MAGIC = "magicSkill";
	private static final String SPP = "spp";
	private static final String STRENGTH = "STR";
	private static final String LEVEL = "lvl";
	private static final String EXPERIENCE = "exp";
	private static final String HASPET = "haspet";
	private static final String PETFOLLOW = "petfollow";
	private static final String PETTYPE = "petType";
	private static final String PETLEVEL = "petLevel";
	private static final String PETHP = "petHP";
	private static final String PETEXP = "petExperience";
	private static final String PETCOOLDOWN = "petCooldown";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		heroClass.storeInBundle(bundle);
		subClass.storeInBundle(bundle);

		bundle.put(HIT_SKILL, hitSkill);
		bundle.put(EVADE_SKILL, evadeSkill);
		bundle.put(MAGIC, magicSkill);
		bundle.put(SPP, spp);

		bundle.put(STRENGTH, STR);

		bundle.put(LEVEL, lvl);
		bundle.put(EXPERIENCE, exp);
		bundle.put(HASPET, haspet);
		bundle.put(PETFOLLOW, petfollow);
		bundle.put(PETTYPE, petType);
		bundle.put(PETLEVEL, petLevel);
		bundle.put(PETHP, petHP);
		bundle.put(PETEXP, petExperience);
		bundle.put(PETCOOLDOWN, petCooldown);

		belongings.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		heroClass = HeroClass.restoreInBundle(bundle);
		subClass = HeroSubClass.restoreInBundle(bundle);

		hitSkill = bundle.getInt(HIT_SKILL);
		evadeSkill = bundle.getInt(EVADE_SKILL);
		magicSkill = bundle.getInt(MAGIC);
		spp = bundle.getInt(SPP);

		STR = bundle.getInt(STRENGTH);
		updateAwareness();

		lvl = bundle.getInt(LEVEL);
		exp = bundle.getInt(EXPERIENCE);
		haspet = bundle.getBoolean(HASPET);
		petfollow = bundle.getBoolean(PETFOLLOW);
		petType = bundle.getInt(PETTYPE);
		petLevel = bundle.getInt(PETLEVEL);
		petHP = bundle.getInt(PETHP);
		petExperience = bundle.getInt(PETEXP);
		petCooldown = bundle.getInt(PETCOOLDOWN);
		
		belongings.restoreFromBundle(bundle);
	}

	public static void preview(GamesInProgress.Info info, Bundle bundle) {
		info.level = bundle.getInt(LEVEL);
	}

	public String className() {
		return subClass == null || subClass == HeroSubClass.NONE ? heroClass
				.title() : subClass.title();
	}

	public String givenName() {
		return name.equals(Messages.get(this, "name")) ? className() : name;
	}

	public void live() {
		Buff.affect(this, Regeneration.class);
		Buff.affect(this, Hunger.class);
		
	   // if (Dungeon.skins == 4 ) {
			//if (heroClass == HeroClass.SOLDIER) {
			//	Buff.affect(this, NmImbue.class);
			//}
		//}
	}
	
	public int useskin() {
		return Dungeon.skins;
	}

	public boolean shoot(Char enemy, MissileWeapon wep) {

		rangedWeapon = wep;
		boolean result = attack(enemy);
		Invisibility.dispel();
		rangedWeapon = null;

		return result;
	}

	@Override
	public int hitSkill(Char target) {
		float accuracy = 1;

		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {
			return (int) (hitSkill * accuracy * wep.acuracyFactor(this));
		} else {
			return (int) (hitSkill * accuracy);
		}
	}

	@Override
	public int evadeSkill(Char enemy) {
		float evasion = 1;
		if (paralysed > 0) {
			evasion /= 2;
		}
		KindOfArmor arm =  belongings.armor;
		if (arm != null) {
			return (int) (evadeSkill * evasion * arm.dexterityFactor(this));
		} else {
			return (int) (evadeSkill * evasion);
		}
	}

	@Override
	public int drRoll() {
		KindOfArmor arm = belongings.armor;
		int dr;
		//int bonus = 0;
		//for (Buff buff : buffs(RingOfForce.Force.class)) {
			//bonus += ((RingOfForce.Force) buff).level;
		//}
		Barkskin barkskin = buff(Barkskin.class);
		if (arm != null) {
			dr = arm.drRoll(this);
		} else {
			//int str = 6;
			//dr = bonus == 0 ? str > 1 ? Random.NormalIntRange(1, str) : 1
					//: bonus > 0 ? str > 0 ? Random.NormalIntRange(str / 2
					//+ bonus, (int) (str * 0.5f * bonus) + str * 2) : 1
					//: 0;
			dr = 0;
		}

		//if (bonus > 0){ dr *= Math.min(3f,(1f + (bonus/15)*1f));}

		if (dr < 0)
			dr = 0;
		if (barkskin != null) {
			dr += barkskin.level();
		}
		if (Dungeon.hero.heroClass == HeroClass.SOLDIER && (Dungeon.skins == 2 || Dungeon.skins == 6)) {
			dr += Dungeon.hero.lvl;
		}
		return dr;
	}

	@Override
	public int damageRoll() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		int dmg;
		int bonus = 0;
		for (Buff buff : buffs(RingOfForce.Force.class)) {
			bonus += ((RingOfForce.Force) buff).level;
		}


		if (wep != null) {
			dmg = wep.damageRoll(this);
		} else {
			int str = 6;
			dmg = 
			bonus == 0 ? 
			str > 1 ? 
			Random.NormalIntRange(1, str) 
			: 1
			: bonus > 0 ? 
			str > 0 ? 
			Random.NormalIntRange(str / 2 + bonus, (int) (str * 0.5f * bonus) + str * 2) 
			: 1
			: 0;
		}


		if (wep == null && Dungeon.hero.heroClass==HeroClass.SOLDIER && Dungeon.skins == 2) {
             dmg+= Dungeon.hero.lvl* (Dungeon.hero.STR-10)* Dungeon.hero.HP/Dungeon.hero.HT;
		}

		if (bonus > 0){ dmg *= Math.min(3f,(1f + (bonus*1.00/10)*1f));}

		if (dmg < 0)
			dmg = 0;

		if (buff(Fury.class) != null){ dmg *= 1.30f; }
		
		OnePunch op = buff(OnePunch.class);
		if (op != null){ 
		    dmg *= 1f+op.level()*0.10f;
            Buff.detach(this,OnePunch.class);		
		}

		if (Dungeon.skins == 4 && Dungeon.hero.heroClass == HeroClass.FOLLOWER){
			 if (Dungeon.hero.spp < 40){
				 dmg *= 0.5f;
			 } else if (Dungeon.hero.spp < 70){
				 dmg *= 1f * (3+ Dungeon.hero.spp)/100;
			 } else if (Dungeon.hero.spp < 95){
				 dmg *= 1f *( 1 + ((Dungeon.hero.spp-70)*4/100));
			 } else {
				dmg *= 1f * ( 2 + (Dungeon.hero.spp-95));
			}
		}
		
		if (buff(Strength.class) != null){ dmg *= 3f; Buff.detach(this, Strength.class);}

		HighAttack hatk = buff(HighAttack.class);
		if (buff(HighAttack.class) != null){
			dmg *= hatk.level();
			Buff.detach(this, HighAttack.class);
		}
		
        ParyAttack paryatk = buff(ParyAttack.class);
		if (buff(ParyAttack.class) != null){
			dmg *= (1+paryatk.level()*0.4);
		}
		

        if (buff(WarGroove.class) != null){ dmg *= 1.5f; Buff.detach(this, WarGroove.class);}
		
		if (buff(Dry.class) != null){ dmg *= 0.80f; }

		if (buff(BloodAngry.class) != null){ dmg *= 1.50f; }

		if (buff(Rhythm2.class) != null){ dmg *= 1.20f; }
		
		if (buff(CrazyMind.class) != null && Random.Int(10) == 0){ dmg = 0; }

        if (buff(CrazyMind.class) != null){ dmg *= 1.20f; }
        if (buff(LoseMind.class) != null){ dmg *= 1.20f; }
        if (buff(AmokMind.class) != null){ dmg *= 1.20f; }
        if (buff(WeakMind.class) != null){ dmg *= 1.20f; }
        if (buff(TerrorMind.class) != null){ dmg *= 1.20f; }

		/*AttackUp atkup = buff(AttackUp.class);
		if (atkup != null) {
			dmg *=(1f+atkup.level()*0.01f);
		}

		AttackDown atkdown = buff(AttackDown.class);
		if (atkdown != null) {
			dmg *=(1f-atkdown.level()*0.01f);
		}*/

		Hunger hunger = buff(Hunger.class);
		if (hunger != null && hunger.isStarving()) { dmg *= 0.8f;}
		if (hunger != null && hunger.isHungry()) { dmg *= 0.9f;}
		if (hunger != null && hunger.isOverfed()) { dmg *= 1.2f; }
		
		return dmg;
		
	}
	

	@Override
	public float speed() {

		float speed = super.speed();

		int hasteLevel = 0;
		for (Buff buff : buffs(RingOfHaste.Haste.class)) {
			hasteLevel += ((RingOfHaste.Haste) buff).level;
		}

		
		if (hasteLevel != 0) {
			if (hasteLevel < 30){
			    speed *= (1+(hasteLevel*1.00/10));
				}
            else speed *=4;
	    }

	    if (buff(FishBone.FishFriend.class) !=null && Level.water[pos]){
            speed*=2;
		}

		if (hero.heroClass == HeroClass.HUNTRESS && Dungeon.skins == 2) {
				speed += 0.5f;

		}

		if (hero.subClass == HeroSubClass.MONK ) {
			speed += 0.25f;

		}
			return ((HeroSprite) sprite).sprint(subClass == HeroSubClass.FREERUNNER && !isStarving()) ? invisible > 0 ? 2f * speed
					: 1.5f * speed : speed;


	}
	
    public boolean canAttack(Char enemy){
		if (enemy == null || pos == enemy.pos)
			return false;

		//can always attack adjacent enemies
		if (Level.adjacent(pos, enemy.pos))
			return true;

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

		int emptyarange = (int)(1+bonus/10);
		KindOfWeapon wep = hero.belongings.weapon;

		if (wep != null && Level.distance( pos, enemy.pos ) <= wep.reachFactor(this)){

			boolean[] passable = BArray.not(Level.solid, null);
			for (Mob m : Dungeon.level.mobs)
				passable[m.pos] = false;

			PathFinder.buildDistanceMap(enemy.pos, passable, wep.reachFactor(this));

			return PathFinder.distance[pos] <= wep.reachFactor(this);

		} else if (wep == null && Level.distance( pos, enemy.pos ) <= emptyarange){
				
			boolean[] passable = BArray.not(Level.solid, null);
			for (Mob m : Dungeon.level.mobs)
				passable[m.pos] = false;

			PathFinder.buildDistanceMap(enemy.pos, passable, emptyarange);

			return PathFinder.distance[pos] <= emptyarange;

		} else{
			return false;
		}
	}
	

	public float attackDelay() {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;
		if (wep != null) {

			return wep.speedFactor(this);

		} else {
			// Normally putting furor speed on unarmed attacks would be
			// unnecessary
			// But there's going to be that one guy who gets a furor+force ring
			// combo
			// This is for that one guy, you shall get your fists of fury!
			int bonus = 0;
			for (Buff buff : buffs(RingOfFuror.Furor.class)) {
				bonus += ((RingOfFuror.Furor) buff).level;
			}
			return (float) ( 1 / Math.min( 4, 1 + bonus * 1.00 / 10) );
		}
	}

	@Override
	public void spend(float time) {
		justMoved = false;
		TimekeepersHourglass.timeFreeze freeze = buff(TimekeepersHourglass.timeFreeze.class);
		if (freeze != null) {
			freeze.processTime(time);
			return;
		} else if (Statistics.time < 1440 ) {
			Statistics.time += time;
			Dungeon.observe();
		} else if ( Statistics.time > 1440 ) {
			Statistics.time += time;
			Statistics.time -= 1440;
			//Dungeon.observe();
		} else {
			Statistics.time = 0;
			//Dungeon.observe();
		}
		
		super.spend(time);
	}

    public void spendAndNext(float time) {
		busy();
		spend(time);
		next();
	}

	@Override
	public boolean act() {

		super.act();

		Statistics.moves++;

		Light light = buff(Light.class);
		DewVial.DewLight dlight = buff(DewVial.DewLight.class);
		if (buff(HighLight.class) != null){
			viewDistance = 8;
			Dungeon.observe();
		} else if ((Statistics.time > 360 && Statistics.time <601 ) || (Statistics.time > 840 && Statistics.time < 1081 )) {
			viewDistance = 6;
			Dungeon.observe();
		} else if (Statistics.time < 841 && Statistics.time > 600) {
			viewDistance = 8;
			Dungeon.observe();
		} else if (Statistics.time > 1080  && light == null && dlight == null) {
			viewDistance = 4;
			Dungeon.observe();
		} else if (Statistics.time < 361 && light == null && dlight == null) {
			viewDistance = 2;
			Dungeon.observe();
		} else {viewDistance = 5;
			Dungeon.observe();}

		
		if(Dungeon.dewDraw || Dungeon.dewWater){ Dungeon.level.currentmoves++;}
	
		if (paralysed > 0) {

			curAction = null;

			spendAndNext(TICK);
			return false;
		}

		if(Dungeon.hero.heroClass == HeroClass.PERFORMER & Dungeon.skins == 4){
			if (Statistics.time == 1 ) {
				LeaderFlag lflag = belongings.getItem(LeaderFlag.class);
				int people = Dungeon.hero.spp;
				Dungeon.gold = Math.max(0, Dungeon.gold - people);
				lflag.charge = 1440;
			}
		}

		Egg egg = belongings.getItem(Egg.class);
		if (egg!=null){
			egg.moves++;
		}
		
		DolyaSlate journal = belongings.getItem(DolyaSlate.class);
		if (journal!=null && (Dungeon.depth < 26) 
				&& (journal.level>1 || journal.rooms[0]) 
				&& journal.charge<journal.fullCharge){
			journal.charge++;
		}
		
		Jumpshoes jump = belongings.getItem(Jumpshoes.class);
		if (jump!=null && jump.charge<jump.fullCharge) {jump.charge++;}
		
		JumpW jumpw = belongings.getItem(JumpW.class);
		if (jumpw!=null && jumpw.charge<jumpw.fullCharge) {jumpw.charge++;}
		
		JumpM jumpm = belongings.getItem(JumpM.class);
		if (jumpm!=null && jumpm.charge<jumpm.fullCharge) {jumpm.charge++;}
				
		JumpR jumpr = belongings.getItem(JumpR.class);
		if (jumpr!=null && jumpr.charge<jumpr.fullCharge) {jumpr.charge++;}
		
		JumpH jumph = belongings.getItem(JumpH.class);
		if (jumph!=null && jumph.charge<jumph.fullCharge) {jumph.charge++;}

		JumpP jumpp = belongings.getItem(JumpP.class);
		if (jumpp!=null && jumpp.charge<jumpp.fullCharge) {jumpp.charge++;}

		JumpS jumps = belongings.getItem(JumpS.class);
		if (jumps!=null && jumps.charge<jumps.fullCharge) {jumps.charge++;}

		JumpF jumpf = belongings.getItem(JumpF.class);
		if (jumpf!=null && jumpf.charge<jumpf.fullCharge) {jumpf.charge++;}
		
		JumpA jumpa = belongings.getItem(JumpA.class);
		if (jumpa!=null && jumpa.charge<jumpa.fullCharge) {jumpa.charge++;}
		
		Shovel shovel = belongings.getItem(Shovel.class);
		if (shovel!=null && shovel.charge<shovel.fullCharge) {shovel.charge++;}

		BShovel bshovel = belongings.getItem(BShovel.class);
		if (bshovel!=null && bshovel.charge<bshovel.fullCharge) {bshovel.charge++;}

		Ankhshield shield = belongings.getItem(Ankhshield.class);
		if (shield!=null && shield.charge<shield.fullCharge) {shield.charge++;}

         MissileShield missileshield = belongings.getItem(MissileShield.class);
		if (missileshield!=null && missileshield.charge<missileshield.fullCharge) {missileshield.charge++;}

		PotionOfMage pom = belongings.getItem(PotionOfMage.class);
		if (pom!=null && pom.charge<pom.fullCharge) {pom.charge++;}

		GunOfSoldier gos = belongings.getItem(GunOfSoldier.class);
		if (gos!=null && gos.charge<gos.fullCharge) {gos.charge++;}

        OrbOfZot ofz = belongings.getItem(OrbOfZot.class);
		if (ofz!=null && ofz.charge<ofz.fullCharge) {ofz.charge++;}

		DanceLion lion = belongings.getItem(DanceLion.class);
		if (lion!=null && lion.charge<lion.fullCharge) {lion.charge++;}
		
		HealBag healbag = belongings.getItem(HealBag.class);
		if (healbag!=null && healbag.charge<healbag.fullCharge) {healbag.charge++;}		

		if (buff(DeadRaise.class) != null && Random.Int(30) == 0) {
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				int p = pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				SommonSkeleton.spawnAt(Random.element(spawnPoints));
				Sample.INSTANCE.play(Assets.SND_CURSED);
			}
		}
		

		/*
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null){
			heap.dewcollect();
		}
		*/

		checkVisibleMobs();

		if (curAction == null) {

			if (restoreHealth) {
				spend(TIME_TO_REST);
				next();
				return false;
			}
			
			ready();
			return false;

		} else {

			restoreHealth = false;

			ready = false;
			
			if (curAction instanceof HeroAction.Move) {

				return actMove((HeroAction.Move) curAction);

			} else if (curAction instanceof HeroAction.Interact) {

				return actInteract((HeroAction.Interact) curAction);
				
			} else if (curAction instanceof HeroAction.InteractPet) {

				return actInteractPet((HeroAction.InteractPet) curAction);

			} else if (curAction instanceof HeroAction.Buy) {

				return actBuy((HeroAction.Buy) curAction);

			} else if (curAction instanceof HeroAction.LifeBuy) {

				return actLifeBuy((HeroAction.LifeBuy) curAction); 
				
			}else if (curAction instanceof HeroAction.PickUp) {

				return actPickUp((HeroAction.PickUp) curAction);

			} else if (curAction instanceof HeroAction.OpenChest) {

				return actOpenChest((HeroAction.OpenChest) curAction);

			} else if (curAction instanceof HeroAction.Unlock) {

				return actUnlock((HeroAction.Unlock) curAction);

			} else if (curAction instanceof HeroAction.Descend) {

				return actDescend((HeroAction.Descend) curAction);

			} else if (curAction instanceof HeroAction.Ascend) {

				return actAscend((HeroAction.Ascend) curAction);

			} else if (curAction instanceof HeroAction.Attack) {

				return actAttack((HeroAction.Attack) curAction);

			/*} else 	if (curAction instanceof HeroAction.Cook) {

				return actCook( (HeroAction.Cook)curAction );*/
				
			}
		}

		return false;
	}

	public void busy() {
		ready = false;
	}

	private void ready() {
		sprite.idle();
		curAction = null;
		damageInterrupt = true;
		ready = true;

		AttackIndicator.updateState();

		GameScene.ready();
	}

	public void interrupt() {
		if (isAlive() && curAction != null
				&& curAction instanceof HeroAction.Move && curAction.dst != pos) {
			lastAction = curAction;
		}
		curAction = null;
	}

	public void resume() {
		curAction = lastAction;
		lastAction = null;
		damageInterrupt = false;
		act();
	}

	
    public boolean justMoved = false;
	
	private boolean actMove(HeroAction.Move action) {

		if (getCloser(action.dst)) {
            justMoved = true;
			return true;

		} else {
			if (Dungeon.level.map[pos] == Terrain.SIGN && pos != Dungeon.level.pitSign) {
				Sign.read(pos);
			} else if (Dungeon.level.map[pos] == Terrain.SIGN && pos == Dungeon.level.pitSign){
				Sign.readPit(pos);
			} else if (Dungeon.level.map[pos] == Terrain.ALCHEMY){
				AlchemyPot.cook(pos);
			} else if (Dungeon.level.map[pos] == Terrain.TENT){
				TentRoom.rest(pos);
			} else if (Dungeon.level.map[pos] == Terrain.IRON_MAKER){
		      	IronMaker.make(pos);
			}
			ready();

			return false;
		}
	}
	
	private boolean actInteract(HeroAction.Interact action) {

		NPC npc = action.npc;

		if (Level.adjacent(pos, npc.pos)) {

			ready();
			sprite.turnTo(pos, npc.pos);
			//npc.interact();
			//return false;
			return npc.interact();

		} else {

			if (Level.fieldOfView[npc.pos] && getCloser(npc.pos)) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}

	private boolean actInteractPet(HeroAction.InteractPet action) {

		PET pet = action.pet;

		if (Level.adjacent(pos, pet.pos)) {

			ready();
			sprite.turnTo(pos, pet.pos);
			//pet.interact();
			//return false;
			return pet.interact();

		} else {

			if (Level.fieldOfView[pet.pos] && getCloser(pet.pos)) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}
	
	private boolean actBuy(HeroAction.Buy action) {
		int dst = action.dst;
		if (pos == dst || Level.adjacent(pos, dst)) {

			ready();

			Heap heap = Dungeon.level.heaps.get(dst);
			if (heap != null && heap.type == Type.FOR_SALE && heap.size() == 1) {
				GameScene.show(new WndTradeItem(heap, true));
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private boolean actLifeBuy(HeroAction.LifeBuy action) {
		int dst = action.dst;
		if (pos == dst || Level.adjacent(pos, dst)) {

			ready();

			Heap heap = Dungeon.level.heaps.get(dst);
			if (heap != null && heap.type == Type.FOR_LIFE && heap.size() == 1) {
				GameScene.show(new WndLifeTradeItem(heap, true));
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}	

	/*private boolean actAlchemy( HeroAction.Alchemy action ) {
		int dst = action.dst;
		if (Dungeon.level.distance(dst, pos) <= 1) {

			ready();
			GameScene.show(new WndAlchemy());
			return false;

		} else if (getCloser( dst )) {

			return true;

		} else {
			ready();
			return false;
		}
	}*/

	private boolean actPickUp(HeroAction.PickUp action) {
		int dst = action.dst;
		if (pos == dst) {

			Heap heap = Dungeon.level.heaps.get(pos);
			if (heap != null) {
				Item item = heap.pickUp();
				if (item.doPickUp(this)) {

					if (item instanceof Dewdrop
							|| item instanceof TimekeepersHourglass.sandBag
							|| item instanceof DriedRose.Petal) {
						// Do Nothing
					} else {

						boolean important = ((item instanceof ScrollOfUpgrade || item instanceof ScrollOfMagicalInfusion) && ((Scroll) item)
								.isKnown())
								|| ((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion) item)
										.isKnown());
						if (important) {
							GLog.p(Messages.get(this, "you_now_have", item.name()));
						} else {
							GLog.i(Messages.get(this, "you_now_have", item.name()));
						}

						// Alright, if anyone complains about not knowing the
						// vial doesn't revive
						// after this... I'm done, I'm just done.
					}

					curAction = null;
				} else {
					Dungeon.level.drop(item, pos).sprite.drop();
					ready();
				}
			} else {
				ready();
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actOpenChest(HeroAction.OpenChest action) {
		int dst = action.dst;
		if (Level.adjacent(pos, dst) || pos == dst) {

			Heap heap = Dungeon.level.heaps.get(dst);
			if (heap != null
					&& (heap.type != Type.HEAP && heap.type != Type.FOR_SALE  && heap.type != Type.FOR_LIFE)) {

				theKey = null;
				theSkeletonKey = null;

				if (heap.type == Type.LOCKED_CHEST
						|| heap.type == Type.CRYSTAL_CHEST 
						//|| heap.type == Type.MONSTERBOX
						) {

					theKey = belongings.getKey(GoldenKey.class, Dungeon.depth);
					theSkeletonKey = belongings.getKey(GoldenSkeletonKey.class, 0);

					if (theKey == null && theSkeletonKey == null) {
						GLog.w( Messages.get(this, "locked_chest"));
						ready();
						return false;
					}
				}

				switch (heap.type) {
				case TOMB:
					Sample.INSTANCE.play(Assets.SND_TOMB);
					Camera.main.shake(1, 0.5f);
					break;
				case SKELETON:
				case REMAINS:
				case E_DUST:
					break;
				default:
					Sample.INSTANCE.play(Assets.SND_UNLOCK);
				}

				spend(Key.TIME_TO_UNLOCK);
				sprite.operate(dst);

			} else {
				ready();
			}

			return false;

		} else if (getCloser(dst)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actUnlock(HeroAction.Unlock action) {
		int doorCell = action.dst;
		if (Level.adjacent(pos, doorCell)) {

			theKey = null;
			int door = Dungeon.level.map[doorCell];

			if (door == Terrain.LOCKED_DOOR) {

				theKey = belongings.getKey(IronKey.class, Dungeon.depth);

			} else if (door == Terrain.LOCKED_EXIT) {

				theKey = belongings.getKey(SkeletonKey.class, Dungeon.depth);

			}

			if (theKey != null) {

				spend(Key.TIME_TO_UNLOCK);
				sprite.operate(doorCell);

				Sample.INSTANCE.play(Assets.SND_UNLOCK);

			} else {
				GLog.w( Messages.get(Hero.class, "locked_door"));
				ready();
			}

			return false;

		} else if (getCloser(doorCell)) {

			return true;

		} else {
			ready();
			return false;
		}
	}
	
	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}

	private boolean actDescend(HeroAction.Descend action) {
		int stairs = action.dst;
		
		if (!Dungeon.level.forcedone && ( Dungeon.dewDraw || Dungeon.dewWater )
		        && (Dungeon.level.checkdew()>0 
				|| hero.buff(Dewcharge.class) != null))
		{
			GameScene.show(new WndDescend());
			ready();
			return false;
		}
		
		if (!Dungeon.level.forcedone && 
		    ( Dungeon.dewDraw || Dungeon.dewWater ) &&
		    !Dungeon.level.cleared && 
			!Dungeon.notClearableLevel(Dungeon.depth))
		{
			GameScene.show(new WndDescend());
			ready();
			return false;
		}
		
		
		//if (pos == stairs && pos == Dungeon.level.exit && !Dungeon.level.sealedlevel){
		if (pos == stairs && pos == Dungeon.level.exit){
			
			curAction = null;
			
			if(Dungeon.dewDraw || Dungeon.dewWater) {

				for (int i = 0; i < Level.LENGTH; i++) {

					if (!Dungeon.level.cleared && (Dungeon.dewDraw || Dungeon.dewWater) && !Dungeon.notClearableLevel(Dungeon.depth)) {
						Dungeon.level.cleared = true;
						Statistics.prevfloormoves = 0;
					}
				}
			}
			
			PET pet = checkpet();
			if(pet!=null && checkpetNear()){
			  hero.petType=pet.type;
			  hero.petLevel=pet.level;
			  hero.petHP=pet.HP;
			  hero.petExperience=pet.experience;
			  hero.petCooldown=pet.cooldown;
			  pet.destroy();
			  petfollow=true;
			} else petfollow = hero.haspet && hero.petfollow;
	
			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null) buff.detach();

			InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
			Game.switchScene(InterlevelScene.class);

			return false;
			
		} else if (getCloser(stairs)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actAscend(HeroAction.Ascend action) {
		int stairs = action.dst;
		if (pos == stairs && pos == Dungeon.level.entrance) {
			
			if (Dungeon.depth == 1) {

				if (belongings.getItem(Amulet.class) == null) {
					GameScene.show(new WndMessage(Messages.get(this, "leave")));
					ready();
							
				} else if (Dungeon.level.forcedone){
					Dungeon.win(Messages.format(ResultDescriptions.WIN));
					Dungeon.deleteGame(hero.heroClass, true);
					Game.switchScene(SurfaceScene.class);
				} else {
					GameScene.show(new WndAscend());
					ready();
				}
				
			} else if (Dungeon.depth == 34) {
				curAction = null;

				Hunger hunger = buff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
					hunger.satisfy(-Hunger.STARVING / 10);
				}
				
				PET pet = checkpet();
				if(pet!=null && checkpetNear()){
				  hero.petType=pet.type;
				  hero.petLevel=pet.level;
				  hero.petHP=pet.HP;
				  hero.petExperience=pet.experience;
				  hero.petCooldown=pet.cooldown;
				  pet.destroy();
				  petfollow=true;
				} else petfollow = hero.haspet && hero.petfollow;
				
				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				//for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					//if (mob instanceof DriedRose.GhostHero)
						//mob.destroy();
                
				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene(InterlevelScene.class);
				
			
		    } else if (Dungeon.depth == 41) {
			curAction = null;

			Hunger hunger = buff(Hunger.class);
			if (hunger != null && !hunger.isStarving()) {
				hunger.satisfy(-Hunger.STARVING / 10);
			}

			PET pet = checkpet();
			if(pet!=null && checkpetNear()){
			  hero.petType=pet.type;
			  hero.petLevel=pet.level;
			  hero.petHP=pet.HP;
			  hero.petExperience=pet.experience;
			  hero.petCooldown=pet.cooldown;
			  pet.destroy();
			  petfollow=true;
			} else petfollow = hero.haspet && hero.petfollow;
			
			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null)
				buff.detach();

			//for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				//if (mob instanceof DriedRose.GhostHero)
					//mob.destroy();
            
			InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
			Game.switchScene(InterlevelScene.class);
			
		   } else if (Dungeon.depth > 26 ){
				ready();
			} else if (Dungeon.depth == 55 || Dungeon.depth == 99){
				ready();
			} else if (Dungeon.depth > 55 && Dungeon.level.locked){
				ready();
			} else {

				curAction = null;

				Hunger hunger = buff(Hunger.class);
				if (hunger != null && !hunger.isStarving()) {
					hunger.satisfy(-Hunger.STARVING / 10);
				}

				PET pet = checkpet();
				if(pet!=null && checkpetNear()){
				  hero.petType=pet.type;
				  hero.petLevel=pet.level;
				  hero.petHP=pet.HP;
				  hero.petExperience=pet.experience;
				  hero.petCooldown=pet.cooldown;
				  pet.destroy();
				  petfollow=true;
				} else petfollow = hero.haspet && hero.petfollow;
				
				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				//for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					//if (mob instanceof DriedRose.GhostHero)
						//mob.destroy();
                
				InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
				Game.switchScene(InterlevelScene.class);
			}

			return false;

		} else if (getCloser(stairs)) {

			return true;

		} else {
			ready();
			return false;
		}
	}

	private boolean actAttack(HeroAction.Attack action) {

		enemy = action.target;

		if (enemy.isAlive() && canAttack( enemy ) && !isCharmedBy( enemy ) && (buff(Disarm.class) == null)) {

			Invisibility.dispel();
			spend( attackDelay());
			sprite.attack( enemy.pos );

			return false;

		} else  {

			if (Level.fieldOfView[enemy.pos] && getCloser( enemy.pos )) {

				return true;

			} else {
				ready();
				return false;
			}

		}
	}
	
	public Char enemy(){
		return enemy;
	}	

	public void rest(boolean fullRest) {
		//search(true);
		spendAndNext(TIME_TO_REST);
		if (!fullRest) {
			sprite.showStatus(CharSprite.DEFAULT, Messages.get(this, "wait"));
		}
		restoreHealth = fullRest;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		KindOfWeapon wep = rangedWeapon != null ? rangedWeapon
				: belongings.weapon;

		if (wep != null){
			wep.proc(this, enemy, damage);
		}

		AttackShield atkshield = belongings.getItem(AttackShield.class);
		if (atkshield!=null && atkshield.charge<atkshield.fullCharge) {atkshield.charge++;}
		
		SeriousPunch spunch = belongings.getItem(SeriousPunch.class);
		if (spunch!=null) {spunch.charge++;}
		
		CopyBall copyball = belongings.getItem(CopyBall.class);
		if (copyball!=null && copyball.charge<copyball.fullCharge) {copyball.charge++;}
		
		BigBattery bigbattery = belongings.getItem(BigBattery.class);
		if (bigbattery!=null && bigbattery.charge<bigbattery.fullCharge) {bigbattery.charge++;}

		DiceTower diceTower = belongings.getItem(DiceTower.class);
		if (diceTower!=null && diceTower.charge<diceTower.fullCharge) {diceTower.charge++;}

		switch (heroClass) {
			case WARRIOR:
				if (Dungeon.skins==2) {
					Buff.affect(enemy, DBurning.class).set(4);
				}
				break;
			case PERFORMER:
				if (Dungeon.skins == 4) {
				int people = Dungeon.hero.spp - Dungeon.hero.lvl;
				if (people > 0 )
					damage = damage + people;
			}
				break;
			case FOLLOWER:
				if (Dungeon.skins==4) {
					Dungeon.hero.spp = Random.Int(100);

                if (Dungeon.hero.spp == 13) {
                    Buff.affect(enemy, DBurning.class).set(4);
                }
				if (enemy.HP <= damage && Dungeon.hero.spp > 95) {
					Dungeon.level.drop(Generator.random(), enemy.pos).sprite.drop();
				}
			}
			case ASCETIC:
				enemy.damage(hero.magicSkill(),ENERGY_DAMAGE);
				break;
		}
		switch (subClass) {
		case GLADIATOR:
			if (wep instanceof MeleeWeapon || wep == null) {
				damage += Buff.affect(this, Combo.class).hit(enemy, damage);
			}
			break;
		case BATTLEMAGE:
			break;
		case SNIPER:
			if (rangedWeapon != null) {
				Buff.prolong(this, SnipersMark.class, attackDelay() * 1.75f).object = enemy
						.id();
			}
			break;
		case SUPERSTAR:
				Buff.prolong(this,Rhythm.class,3f);
				break;
		case JOKER:
			if (wep instanceof MeleeWeapon || wep == null) {
				switch (Random.Int (3)){
					case 0:
				int oppositeDefender = enemy.pos + (enemy.pos - pos);
				Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
				WandOfFlow.throwChar(enemy, trajectory, 1);
				break;
					case 1:
						Buff.prolong(enemy,Silent.class,5f);
						Buff.prolong(enemy,Locked.class,5f);
						break;
					case 2:
						Buff.affect(enemy,BeTired.class).set(40);
					    Buff.affect(enemy,BeOld.class).set(10f);
						break;
					default:
							break;

				}
			}
				break;
			case PASTOR:
				if (Random.Int (6) == 0) {
					Buff.prolong(enemy,Charm.class,6f).object = id();
				}
				break;
			case ARTISAN:
				if (enemy.HP <= damage && Random.Int(15) == 0) {
				Dungeon.level.drop(Generator.random(), enemy.pos).sprite.drop();
				}
				break;
			case MONK:
			if (enemy.buff(BeTired.class) == null)
				Buff.affect(enemy,BeTired.class).set(20);
				break;	
			case HACKER:
				enemy.damage((int)(hero.magicSkill()*damage/10),ENERGY_DAMAGE);
				Buff.affect(this,MagicArmor.class).level(hero.magicSkill());
				break;						
		default:
		}
		if (buff(Shocked.class)!=null){
			Buff.detach(this,Shocked.class);
			Buff.affect(this, Disarm.class,5f);
			damage(this.HP/10,SHOCK_DAMAGE);
		}
		if (buff(GoldTouch.class)!=null){
			if (rangedWeapon == null && Dungeon.gold  < (1000000/(Math.max(1,20-Statistics.deepestFloor)))) {
				int earngold = Math.min(1000 * hero.lvl, damage);
				Dungeon.gold += earngold;

				hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, earngold);
			}
		}

		if (buff(HighVoice.class)!=null &&  Random.Int(5) == 0){
			Buff.affect(this, AttackUp.class,8f).level(30);
			GLog.p(Messages.get(HighVoice.class,"atkup",Dungeon.hero.givenName()));
		}

		if (buff(MechFaith.class)!= null && ((enemy.properties().contains(Property.BEAST))
				|| (enemy.properties().contains(Property.PLANT))
			|| (enemy.properties().contains(Property.FISHER))
				|| (enemy.properties().contains(Property.ELEMENT))) ){
			damage=(int)(damage*1.5);
		}

		if (buff(LifeFaith.class)!= null && ((enemy.properties().contains(Property.MECH))
				|| (enemy.properties().contains(Property.ALIEN))
				|| (enemy.properties().contains(Property.DWARF))
				|| (enemy.properties().contains(Property.GOBLIN))) ){
			damage=(int)(damage*1.5);
		}

		if (buff(DemonFaith.class)!= null && ((enemy.properties().contains(Property.HUMAN))
				|| (enemy.properties().contains(Property.ORC))
				|| (enemy.properties().contains(Property.ELF))
				|| (enemy.properties().contains(Property.TROLL)))){
			damage=(int)(damage*1.5);
		}

		if (buff(HumanFaith.class)!= null && ((enemy.properties().contains(Property.DRAGON))
				|| (enemy.properties().contains(Property.DEMONIC))
				|| (enemy.properties().contains(Property.UNKNOW))
				|| (enemy.properties().contains(Property.UNDEAD))) ){
			damage=(int)(damage*1.5);
		}

		if (buff(BalanceFaith.class)!= null && ((enemy.properties().contains(Property.BOSS))
				|| (enemy.properties().contains(Property.MINIBOSS))) ){
			damage=(int)(damage*1.5);
		}

		HorseTotem horseTotem = belongings.getItem(HorseTotem.class);
		HorseTotem.HorseTotemBless totembuff = buff(HorseTotem.HorseTotemBless.class);
		if (totembuff != null) {
			int x = Random.Int(1,damage/3);
			damage +=x;
			Buff.prolong(this, HasteBuff.class,4f);
		} else if (horseTotem!=null && Random.Int(5)==0) {
			int x = Random.Int(1,damage/3);
			damage +=x;
			Buff.prolong(this, HasteBuff.class,4f);
		}

		RangeBag.RangeBagBless rangeBless = buff(RangeBag.RangeBagBless.class);
		if (rangeBless != null){
           if (enemy.HP <= damage && Random.Int(6) == 0) {
               Dungeon.level.drop(Generator.random(Generator.Category.LINKDROP), enemy.pos).sprite.drop();
           }
        }

		if (enemy instanceof Mob && !(enemy instanceof NPC)) {
			((Mob) enemy).aggro(this);
		}

		return damage;		

	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		KindOfArmor arm = belongings.armor;

		UndeadBook ub = belongings.getItem(UndeadBook.class);
		if (ub!=null) {ub.charge++;}

		if (buff(HighVoice.class)!=null &&  Random.Int(5) == 0){
			Buff.affect(this, GlassShield.class).turns(2);
			GLog.p(Messages.get(HighVoice.class,"save",Dungeon.hero.givenName()));
		}

		CapeOfThorns.Thorns thorns = buff(CapeOfThorns.Thorns.class);
		if (thorns != null) {
			damage = thorns.proc(damage, enemy);
		}

		MirrorShield mirror = buff(MirrorShield.class);
		if (mirror != null) {
			damage = mirror.proc(damage, enemy);
		}		
		
		BoxStar star = buff(BoxStar.class);
		if (star != null) {
			damage = star.proc(damage, enemy);
		}			
		
		Earthroot.Armor earmor = buff(Earthroot.Armor.class);
		if (earmor != null) {
			damage = earmor.absorb(damage);
		}

		Sungrass.Health health = buff(Sungrass.Health.class);
		if (health != null) {
			health.absorb(damage);
		}

		SavageHelmet helmet = belongings.getItem(SavageHelmet.class);
		SavageHelmet.SavageHelmetBless helmetbuff = buff(SavageHelmet.SavageHelmetBless.class);
		if (helmetbuff != null) {
			helmetbuff.absorb(damage);
		} else if (helmet!=null && Random.Int(5)==0) {
			int x = Random.Int(1,damage/2);
			damage -=x;
			Buff.affect(this, DamageUp.class).level(x);
		}

		if (arm != null) {
			arm.proc(enemy, this, damage);
		}

		switch (heroClass) {
			case ROGUE:
			    if (Dungeon.skins == 4){
				int x = (int)(Dungeon.gold*0.3);
				Dungeon.gold -= x;
				Dungeon.hero.spp +=x;
				if ( x > 0);
				damage = (int)(damage*0.7);}
			case PERFORMER:
				if (Dungeon.skins == 2){
					if 	 (Level.water[this.pos]) {
						Buff.prolong(this, Invisibility.class,3f);
					}
				}
		}
		switch (subClass) {
			case LEADER:
				switch (Random.Int (10)){
					case 0:
						int oppositeDefender = enemy.pos + (enemy.pos - pos);
						Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
						WandOfFlow.throwChar(enemy, trajectory, 1);
						break;
					case 1:
						Buff.prolong(enemy,Silent.class,5f);
						break;
					case 2:
						Buff.prolong(enemy,Disarm.class,5f);
						break;
					case 3:
						Buff.prolong(enemy,Terror.class,5f);
						break;
					case 4:
						Buff.prolong(enemy,ArmorBreak.class,5f).level(35);
						break;
					case 5:
						Buff.prolong(enemy,Locked.class,5f);
						break;

					default:
						break;

				}
		}
		return damage;
	}

	@Override
	public void damage(int dmg, Object src) {
		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		if (!(src instanceof Hunger || src instanceof Iceglyph.DeferedDamage)
				&& damageInterrupt){
			//interrupt();
			restoreHealth = false;
			}

		if (!(src instanceof Hunger || src instanceof Iceglyph.DeferedDamage || src instanceof NmGas)
				&& damageInterrupt){
			interrupt();

		}

		if (this.buff(Drowsy.class) != null) {
			Buff.detach(this, Drowsy.class);
			GLog.w(Messages.get(this, "pain_resist"));
		}

		if (enemy != null) {
			if (buff(LifeFaith.class) != null) {
				if ((enemy.properties().contains(Property.BEAST))
						|| (enemy.properties().contains(Property.PLANT))
					|| (enemy.properties().contains(Property.FISHER))
						|| (enemy.properties().contains(Property.ELEMENT))) {
					dmg = (int) Math.ceil(dmg * 0.75);
				}
			}

			if (buff(MechFaith.class) != null) {
				if ((enemy.properties().contains(Property.MECH))
						|| (enemy.properties().contains(Property.ALIEN))
						|| (enemy.properties().contains(Property.DWARF))
						|| (enemy.properties().contains(Property.GOBLIN))) {
					dmg = (int) Math.ceil(dmg * 0.75);
				}
			}

			if (buff(HumanFaith.class) != null) {
				if ((enemy.properties().contains(Property.HUMAN))
						|| (enemy.properties().contains(Property.ORC))
						|| (enemy.properties().contains(Property.ELF))
						|| (enemy.properties().contains(Property.TROLL))) {
					dmg = (int) Math.ceil(dmg * 0.75);
				}
			}

			if (buff(DemonFaith.class) != null) {
				if ((enemy.properties().contains(Property.DRAGON))
						|| (enemy.properties().contains(Property.DEMONIC))
						|| (enemy.properties().contains(Property.UNKNOW))
						|| (enemy.properties().contains(Property.UNDEAD))) {
					dmg = (int) Math.ceil(dmg * 0.75);
				}
			}

			if (buff(BalanceFaith.class) != null) {
				if ((enemy.properties().contains(Property.BOSS))
						|| (enemy.properties().contains(Property.MINIBOSS))) {
					dmg = (int) Math.ceil(dmg * 0.75);
				}
			}
		}

		int friend = 0;
		for (Buff buff : buffs(FishBone.FishFriend.class)) {
			friend += 1;
		}
		if (friend != 0 ) {
			if (hasProp(enemy, Property.FISHER))
				dmg = 0;
		}

		int tenacity = 0;
		for (Buff buff : buffs(RingOfTenacity.Tenacity.class)) {
			tenacity += ((RingOfTenacity.Tenacity) buff).level;
		}
		
		if (tenacity != 0) // (HT - HP)/HT = heroes current % missing health.
			dmg = (int) Math.ceil(dmg * Math.max(0.60, (1- 1.00*tenacity/75)));
			
        if (buff(Fury.class) != null){dmg = (int) Math.ceil(dmg * 0.75);}
		if (buff(BloodAngry.class) != null){dmg = (int) Math.ceil(dmg * 0.80);}
		if (buff(Rhythm2.class) != null){dmg = (int) Math.ceil(dmg * 0.90);}
        if (buff(WeakMind.class) != null){dmg = (int) Math.ceil(dmg * 1.30);}

		if (subClass == HeroSubClass.LEADER){dmg = (int) Math.ceil(dmg * 0.80);}

		if (heroClass == HeroClass.WARRIOR) {
			if ((dmg > HP) && ( 3*HP > 2*HT )) {
				dmg = Math.max(1,HT / 2);
			}
		}

		if (heroClass == HeroClass.PERFORMER && Dungeon.skins == 4) {
        	int people = Dungeon.hero.spp - Dungeon.hero.lvl;
        	if (people > 0 )
        	dmg = (int) Math.ceil(dmg * (1+ (people*0.01)));
        }

		//if (buff(Hot.class) != null){dmg = (int) Math.ceil(dmg * 1.20);}

		/*DefenceUp drup = buff(DefenceUp.class);
		if (buff(DefenceUp.class) != null) {
			dmg = (int) Math.ceil(dmg *(-drup.level()*0.01+1));
		}*/

		/*ArmorBreak ab = buff(ArmorBreak.class);
		if (buff(ArmorBreak.class) != null){
			dmg= (int) Math.ceil(dmg *(ab.level()*0.01+1));
		}*/



		super.damage(dmg, src);
		
	
		if (subClass == HeroSubClass.BERSERKER && 0 < HP
				&& HP <= HT * Fury.LEVEL) {
			Buff.affect(this, Fury.class);
		}

		if (heroClass == HeroClass.HUNTRESS && Dungeon.skins == 4) {
			Dungeon.hero.spp++;
			if(Dungeon.hero.spp>100 && Random.Int(4)==0){
				GLog.i(Messages.get(this, "test"));
			    switch (Random.Int(8)){
					case 0:
						if (buff(HopeMind.class) != null);
						else {Buff.affect(this,HopeMind.class);
							break;}
					case 1:
						if (buff(KeepMind.class) != null);
						else {Buff.affect(this,KeepMind.class);
							break;}
					case 2:
                        if (buff(AmokMind.class) != null);
                        else {Buff.affect(this,AmokMind.class);
                        break;}

                    case 3:
                        if (buff(CrazyMind.class) != null);
                        else {Buff.affect(this,CrazyMind.class);
                        break;}
					case 4:
						if (buff(WeakMind.class) != null);
						else {Buff.affect(this,WeakMind.class);
							break;}

                    case 5:
                        if (buff(LoseMind.class) != null);
                        else {Buff.affect(this,LoseMind.class);
                        break;}
                    case 6:
                        if (buff(TerrorMind.class) != null);
                        else {Buff.affect(this,TerrorMind.class);
                        break;}
                    case 7:
                        Buff.affect(this,Bless.class,20f);
                        break;
                }
                Dungeon.hero.spp = 0;
            }
		}

		if(heroClass == HeroClass.ASCETIC){
		    if (hero.HP > hero.HT/2)  Statistics.ashield++;
		    if (Statistics.ashield > 5 && hero.HP < hero.HT/2){
				Statistics.ashield = 0;
		        Buff.affect(hero,DelayProtect.class);
            }
        }

		if (this.buff(AutoHealPotion.class) != null && ((float) HP / HT)<.1) {
			PotionOfHealing pot = hero.belongings.getItem(PotionOfHealing.class);
			if (pot != null) {
				pot.detach(hero.belongings.backpack,1);
				/*
				if(!(Dungeon.hero.belongings.getItem(PotionOfHealing.class).quantity() > 0)){
					pot.detachAll(Dungeon.hero.belongings.backpack);
				}
				*/
				GLog.w(Messages.get(this, "auto_potion"));
				pot.apply(this);				
			}	
			else if (pot==null){
				GLog.w(Messages.get(this, "auto_potion_no"));
			}
			
		}
		
	}

	private void checkVisibleMobs() {
		ArrayList<Mob> visible = new ArrayList<Mob>();

		boolean newMob = false;
		Mob closest = null;

		for (Mob m : Dungeon.level.mobs) {
			if (Level.fieldOfView[m.pos] && m.hostile) {
				visible.add(m);
				if (!visibleEnemies.contains(m)) {
					newMob = true;
				}
				if (closest == null){
									closest = m;
								} else if (distance(closest) > distance(m)) {
									closest = m;
								}
			}
		}
			
			if (closest != null && (QuickSlotButton.lastTarget == null ||
												!QuickSlotButton.lastTarget.isAlive() ||
												!Dungeon.visible[QuickSlotButton.lastTarget.pos])){
								QuickSlotButton.target(closest);
		}

		if (newMob) {
			interrupt();
			restoreHealth = false;
		}

		visibleEnemies = visible;
	}

	public int visibleEnemies() {
		return visibleEnemies.size();
	}

	public Mob visibleEnemy(int index) {
		return visibleEnemies.get(index % visibleEnemies.size());
	}

	private boolean getCloser(final int target) {

		if (rooted) {
			Camera.main.shake(1, 1f);
			return false;
		}

		int step = -1;

		if (Level.adjacent(pos, target)) {

			if (Actor.findChar(target) == null) {
				if (Level.pit[target] && !flying && !Chasm.jumpConfirmed) {
					if (!Level.solid[target]) {
						Chasm.heroJump(this);
						interrupt();
					}
					return false;
				}			
				if (Level.passable[target] || Level.avoid[target]) {
					step = target;
				}
			}

		} else {

			int len = Level.getLength();
			boolean[] p = Level.passable;
			boolean[] v = Dungeon.level.visited;
			boolean[] m = Dungeon.level.mapped;
			boolean[] passable = new boolean[len];
			for (int i = 0; i < len; i++) {
				passable[i] = p[i] && (v[i] || m[i]);
			}

			step = Dungeon.findPath(this, pos, target, passable,
					Level.fieldOfView);
		}

		if (step != -1) {

			int oldPos = pos;
			move(step);
			sprite.move(oldPos, pos);
			spend(1 / speed());

			return true;

		} else {

			return false;

		}

	}

	public boolean handle(int cell) {

		if (cell == -1) {
			return false;
		}

		Char ch;
		Heap heap;
		if (Level.fieldOfView[cell]
				&& (ch = Actor.findChar(cell)) instanceof Mob) {

			if (ch instanceof NPC) {
				curAction = new HeroAction.Interact((NPC) ch);
			} else if (ch instanceof PET) {
					curAction = new HeroAction.InteractPet((PET) ch);
			} else {
				curAction = new HeroAction.Attack(ch);
			}
		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR
				|| Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {

			curAction = new HeroAction.Unlock(cell);
		} else if ((heap = Dungeon.level.heaps.get(cell)) != null) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp(cell);
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? new HeroAction.Buy(
						cell) : new HeroAction.PickUp(cell);
				break;
		    case FOR_LIFE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? new HeroAction.LifeBuy(
						cell) : new HeroAction.PickUp(cell);
				break;				
				
			default:
				curAction = new HeroAction.OpenChest(cell);
			}



		} else if (cell == Dungeon.level.exit && (Dungeon.depth < 26)) {

			curAction = new HeroAction.Descend(cell);

		} else if (cell == Dungeon.level.entrance) {

			curAction = new HeroAction.Ascend(cell);

		} else {

			curAction = new HeroAction.Move(cell);
			lastAction = null;

		}

		return act();
	}

	public void earnExp(int exp) {

		this.exp += exp;
        GhostGirlRose.GhostGirlBless ggb = buff(GhostGirlRose.GhostGirlBless.class);
		if (ggb != null){
			this.exp += 2;
		}
		
		float percent = exp/(float)maxExp();

		EtherealChains.chainsRecharge chains = buff(EtherealChains.chainsRecharge.class);
		if (chains != null) chains.gainExp(percent);
		
		Pylon.beaconRecharge pylon = buff(Pylon.beaconRecharge.class);
		if (pylon != null) pylon.gainExp(percent);		

		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			lvl++;

			if (Dungeon.isChallenged(Challenges.LISTLESS)){
				TRUE_HT += 2;
				HP += 1;
				hitSkill++;
				evadeSkill++;
			} else if (lvl < 12) {
			TRUE_HT += 4;
			HP += 4;
			hitSkill++;
			evadeSkill++;
			} else {
			TRUE_HT += 5;
			HP += 5;
			hitSkill++;
			evadeSkill++;}
			FourClover.FourCloverBless fcb = buff(FourClover.FourCloverBless.class);
			if (fcb != null){
				TRUE_HT+=5;
				magicSkill++;
				Dungeon.gold+=1000;
				hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, 1000);
				
			}
			if (heroClass == HeroClass.SOLDIER){
				TRUE_HT+=3;
			}

			if (heroClass == HeroClass.WARRIOR && Dungeon.skins == 4){
				Hero hero = Dungeon.hero;
				KindOfWeapon weapon = hero.belongings.weapon;
				KindOfArmor armor = hero.belongings.armor;
				KindofMisc misc1 = hero.belongings.misc1;
				KindofMisc misc2 = hero.belongings.misc2;
				KindofMisc misc3 = hero.belongings.misc3;
				if (weapon != null ) {
					Dungeon.hero.spp +=Math.max(1, weapon.level+1);
					hero.belongings.weapon = null;
					weapon.updateQuickslot();
				} else if ( armor != null ) {
					Dungeon.hero.spp += Math.max(1,armor.level+1);
					hero.belongings.armor = null;
				} else if ( misc1 != null && misc1.unique == false  ) {
					Dungeon.hero.spp += Math.max(1,misc1.level+1);
					hero.belongings.misc1 = null;
				} else if ( misc2 != null && misc2.unique == false  ) {
					Dungeon.hero.spp += Math.max(1,misc2.level+1);
					hero.belongings.misc2 = null;
				} else if ( misc3 != null && misc3.unique == false  ) {
					Dungeon.hero.spp += Math.max(1,misc3.level+1);
					hero.belongings.misc3 = null;
				} else {
					Dungeon.gold = 0;
				}

			}

			if (heroClass == HeroClass.PERFORMER && (Dungeon.skins == 4 || Dungeon.skins == 6)){
					Dungeon.hero.spp += lvl;
			}


			if (buff(HopeMind.class) != null){
				TRUE_HT+=1;
			}
			if (lvl < 10) {
				updateAwareness();
			}

			levelUp = true;
		}

		if (levelUp) {

			GLog.p(Messages.get(this, "new_level"), lvl );
			sprite.showStatus(CharSprite.POSITIVE, Messages.get(Hero.class, "level_up") );
			Sample.INSTANCE.play(Assets.SND_LEVELUP);

			Badges.validateLevelReached();

			int value = HT - HP;
			if (value > 0) {
				HP += value;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}

			hero.updateHT(true);
			buff(Hunger.class).satisfy(10);
			if(hero.heroClass == HeroClass.PERFORMER){
				Buff.prolong(hero,Rhythm.class,100);
				Buff.affect(hero,GlassShield.class).turns(1);
			}			
			if(hero.subClass == HeroSubClass.SUPERSTAR){
				Buff.prolong(hero,Rhythm2.class,100);
			}
		}
	}

	public int maxExp() {
		return 7 + lvl * 8;
	}

	void updateAwareness() {
		awareness = (float) (1 - Math.pow((heroClass == HeroClass.ROGUE ? 0.85
				: 0.90), (1 + Math.min(lvl, 9)) * 0.5));
	}

	public boolean isStarving() {
		return buff(Hunger.class) != null
				&& buff(Hunger.class).isStarving();
	}
	
	public boolean isHungry() {
		return buff(Hunger.class) != null
				&& buff(Hunger.class).isHungry();
	}	
	
	public boolean isOverfed() {
		return buff(Hunger.class) != null
				&& buff(Hunger.class).isOverfed();
	}	
	
	public boolean isBeOld() {
		return buff(BeOld.class) != null;
	}	
	
	public boolean isBeCorrupt() {
		return buff(BeCorrupt.class) != null;
	}		

	@Override
	public void add(Buff buff) {

		if (buff(TimekeepersHourglass.timeStasis.class) != null)
			return;

		super.add(buff);

		if (sprite != null) {
			String msg = buff.heroMessage();
			if (msg != null){
				GLog.w(msg);
			}

			if (buff instanceof RingOfMight.Might) {
				//if (((RingOfMight.Might) buff).level > 0) {
			//		HT += ((RingOfMight.Might) buff).level * 8;
			//	}
				Dungeon.hero.updateHT(false);
			} else
				if (buff instanceof Paralysis || buff instanceof Vertigo) {
				interrupt();
			}

		}
		BuffIndicator.refreshHero();
	}

	@Override
	public void remove(Buff buff) {
		super.remove(buff);
		if (buff instanceof RingOfMight.Might) {
			//if (((RingOfMight.Might) buff).level > 0) {
				//HT -= ((RingOfMight.Might) buff).level * 8;
				//hero.damage(1, this);
				//if (!hero.isAlive()) {
				//	Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
			//}
			Dungeon.hero.updateHT(false);
			//}
		}

		BuffIndicator.refreshHero();
	}

	@Override
	public int stealth() {
		int stealth = super.stealth();
		int shadow = 1;
		KindOfArmor arm =  belongings.armor;
		if (arm != null) {
			stealth += shadow * arm.stealthFactor(this);
		} else {
			stealth += shadow;
		}
		if (hero.subClass == HeroSubClass.AGENT){
			stealth += 5;
		}
		if (hero.heroClass == HeroClass.ROGUE && Dungeon.skins == 2){
			stealth += 8;
		}
		return stealth;
	}

	@Override
	public int energybase() {
		int energybase = super.energybase();
		int energy = 1;
		KindOfArmor arm =  belongings.armor;
		if (arm != null) {
			energybase += energy * arm.energyFactor(this);
		} else {
			energybase += energy;
		}
		return energybase;
	}	

	@Override
	public void die(Object cause) {

		curAction = null;

		Ankh ankh = null;

		// look for ankhs in player inventory, prioritize ones which are
		// blessed.
		for (Item item : belongings) {
			if (item instanceof Ankh) {
				if (ankh == null || ((Ankh) item).isBlessed()) {
					ankh = (Ankh) item;
				}
			}
		}

		if (ankh != null && ankh.isBlessed() && this.HT > 0) {

			this.HP = HT;
			
			Buff.detach(this, Paralysis.class);
			spend(-cooldown());		
			
			new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
			CellEmitter.get(this.pos)
					.start(Speck.factory(Speck.LIGHT), 0.2f, 3);

			ankh.detach(belongings.backpack);

			Sample.INSTANCE.play(Assets.SND_TELEPORT);
			GLog.w(Messages.get(this, "revive"));
			Statistics.ankhsUsed++;
			return;
		}

		Actor.fixTime();
		super.die(cause);

		if (ankh == null) {
			reallyDie(cause);
		} else {
			
			//ankh.detach(belongings.backpack);
			Dungeon.deleteGame(hero.heroClass, false);
			GameScene.show(new WndResurrect(ankh, cause));

		}
	}

	public static void reallyDie(Object cause) {

		int length = Level.getLength();
		int[] map = Dungeon.level.map;
		boolean[] visited = Dungeon.level.visited;
		boolean[] discoverable = Level.discoverable;

		for (int i = 0; i < length; i++) {

			int terr = map[i];

			if (discoverable[i]) {

				visited[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {
					//Level.set(i, Terrain.discover(terr));
					//GameScene.updateMap(i);
					Dungeon.level.discover( i );
				}
			}
		}

		Dungeon.observe();

		hero.belongings.identify();

		int pos = hero.pos;

		ArrayList<Integer> passable = new ArrayList<Integer>();
		for (Integer ofs : Level.NEIGHBOURS8) {
			int cell = pos + ofs;
			if ((Level.passable[cell] || Level.avoid[cell])
					&& Dungeon.level.heaps.get(cell) == null) {
				passable.add(cell);
			}
		}
		Collections.shuffle(passable);

		ArrayList<Item> items = new ArrayList<Item>(
				hero.belongings.backpack.items);
		for (Integer cell : passable) {
			if (items.isEmpty()) {
				break;
			}

			Item item = Random.element(items);
			Dungeon.level.drop(item, cell).sprite.drop(pos);
			items.remove(item);
		}

		GameScene.gameOver();
		
		if (cause instanceof Hero.Doom) {
			((Hero.Doom) cause).onDeath();
		}

		Dungeon.deleteGame(hero.heroClass, true);
	}

	@Override
	public void move(int step) {
		super.move(step);

		if (!flying) {
			if (Level.water[pos]) {
				Sample.INSTANCE.play(Assets.SND_WATER, 1, 1,
						Random.Float(0.8f, 1.25f));
			} else {
				Sample.INSTANCE.play(Assets.SND_STEP);
			}
			Dungeon.level.press(pos, this);
		}
	}

	@Override
	public void onMotionComplete() {
		Dungeon.observe();
		search(false);

		super.onMotionComplete();
	}
	

	@Override
	public void onAttackComplete() {

		AttackIndicator.target(enemy);

		//attack(enemy);

		boolean hit = attack( enemy );

		if (buff(AttackShield.LongBuff.class)!=null && belongings.weapon == null){
			if (hit) {
				Buff.affect( this, NewCombo.class ).hit();
			} else {
				NewCombo newcombo = buff(NewCombo.class);
				if (newcombo != null) newcombo.miss();
			}
		}
		curAction = null;

		Invisibility.dispel();

		super.onAttackComplete();
	}

	@Override
	public void onOperateComplete() {

		if (curAction instanceof HeroAction.Unlock) {

			if (theKey != null) {
				theKey.detach(belongings.backpack);
				theKey = null;
			}

			int doorCell = ((HeroAction.Unlock) curAction).dst;
			int door = Dungeon.level.map[doorCell];

			Level.set(doorCell, door == Terrain.LOCKED_DOOR ? Terrain.DOOR
					: Terrain.UNLOCKED_EXIT);
			GameScene.updateMap(doorCell);

		} else if (curAction instanceof HeroAction.OpenChest) {

			if (theKey != null) {
				theKey.detach(belongings.backpack);
				theKey = null;
			} else if (theKey == null && theSkeletonKey != null) {
				theSkeletonKey.detach(belongings.backpack);
				theSkeletonKey = null;
			}
			
			Heap heap = Dungeon.level.heaps
					.get(((HeroAction.OpenChest) curAction).dst);
			if (heap.type == Type.SKELETON || heap.type == Type.REMAINS) {
				Sample.INSTANCE.play(Assets.SND_BONES);
			}
			heap.open(this);
		}
		curAction = null;

		super.onOperateComplete();
	}

	public boolean search(boolean intentional) {

		boolean smthFound = false;

		int positive = 0;
		int negative = 0;
		Light light = buff(Light.class);
		if (light != null){
			positive = 1;
		}
		int distance = 1 + positive + negative;


		
		float level = intentional ? (2 * awareness - awareness * awareness)
				: awareness;
		if (distance <= 0) {
			level /= 2 - distance;
			distance = 1;
		}

		int cx = pos % Level.getWidth();
		int cy = pos / Level.getWidth();
		int ax = cx - distance;
		if (ax < 0) {
			ax = 0;
		}
		int bx = cx + distance;
		if (bx >= Level.getWidth()) {
			bx = Level.getWidth() - 1;
		}
		int ay = cy - distance;
		if (ay < 0) {
			ay = 0;
		}
		int by = cy + distance;
		if (by >= Level.HEIGHT) {
			by = Level.HEIGHT - 1;
		}

		TalismanOfForesight.Foresight foresight = buff(TalismanOfForesight.Foresight.class);
		boolean notice = buff(Notice.class) != null;
		// cursed talisman of foresight makes unintentionally finding things
		// impossible.
		if (foresight != null && foresight.isCursed()) {
			level = -1;
		}

		for (int y = ay; y <= by; y++) {
			for (int x = ax, p = ax + y * Level.getWidth(); x <= bx; x++, p++) {

				if (Dungeon.visible[p]) {

					if (intentional) {
						sprite.parent.addToBack(new CheckedCell(p));
					}

					if (Level.secret[p]
							&& (intentional || Random.Float() < level || notice)) {

						int oldValue = Dungeon.level.map[p];

						GameScene.discoverTile(p, oldValue);

						//Level.set(p, Terrain.discover(oldValue));

						//GameScene.updateMap(p);
						
						Dungeon.level.discover( p );

						ScrollOfMagicMapping.discover(p);

						smthFound = true;

						if (foresight != null && !foresight.isCursed())
							foresight.charge();
					}
				}
			}
		}

		if (intentional) {
			sprite.showStatus(CharSprite.DEFAULT, Messages.get(this, "search"));
			sprite.operate(pos);
			if (foresight != null && foresight.isCursed()) {
				GLog.n(Messages.get(this, "search_distracted"));
				spendAndNext(TIME_TO_SEARCH * 3);
			} else {
				spendAndNext(TIME_TO_SEARCH);
			}

		}

		if (smthFound) {
			GLog.w(Messages.get(this, "noticed_smth"));
			Sample.INSTANCE.play(Assets.SND_SECRET);
			interrupt();
		}

		return smthFound;
	}
	

	public void resurrect(int resetLevel) {

		HP = HT;
		Dungeon.gold = 0;
		exp = 0;

		belongings.resurrect(resetLevel);

		live();

	}

	/*@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.RingResistance r = buff(RingOfElements.RingResistance.class);
		return r == null ? super.resistances() : r.resistances();
	}
	
	@Override
	public HashSet<Class<?>> weakness() {
		RingOfElements.RingResistance r = buff(RingOfElements.RingResistance.class);
		return r == null ? super.weakness() : r.weakness();
	}

	@Override
	public HashSet<Class<?>> immunities() {
		HashSet<Class<?>> immunities = new HashSet<Class<?>>();
		for (Buff buff : buffs()) {
			for (Class<?> immunity : buff.immunities)
				immunities.add(immunity);
		}
		return immunities;
	}*/

	@Override
	public void next() {
		super.next();
	}

	public interface Doom {
		void onDeath();
	}
}
