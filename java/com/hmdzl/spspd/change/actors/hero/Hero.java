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
package com.hmdzl.spspd.change.actors.hero;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.actors.buffs.AflyBless;
import com.hmdzl.spspd.change.actors.buffs.Arcane;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackUp;
import com.hmdzl.spspd.change.actors.buffs.BloodAngry;
import com.hmdzl.spspd.change.actors.buffs.BoxStar;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.DeadRaise;
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.Dry;
import com.hmdzl.spspd.change.actors.buffs.GlassShield;
import com.hmdzl.spspd.change.actors.buffs.GoldTouch;
import com.hmdzl.spspd.change.actors.buffs.HighAttack;
import com.hmdzl.spspd.change.actors.buffs.HighLight;
import com.hmdzl.spspd.change.actors.buffs.HighVoice;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.MirrorShield;
import com.hmdzl.spspd.change.actors.buffs.Muscle;
import com.hmdzl.spspd.change.actors.buffs.NewCombo;
import com.hmdzl.spspd.change.actors.buffs.Notice;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Rhythm2;
import com.hmdzl.spspd.change.actors.buffs.Shocked;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.WarGroove;
import com.hmdzl.spspd.change.actors.mobs.SommonSkeleton;
import com.hmdzl.spspd.change.effects.Lightning;
import com.hmdzl.spspd.change.items.DolyaSlate;
import com.hmdzl.spspd.change.items.KindOfArmor;
import com.hmdzl.spspd.change.items.armor.glyphs.Iceglyph;
import com.hmdzl.spspd.change.items.artifacts.AlienBag;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.items.misc.AttackShield;
import com.hmdzl.spspd.change.items.misc.BShovel;
import com.hmdzl.spspd.change.items.misc.CopyBall;
import com.hmdzl.spspd.change.items.misc.FourClover;
import com.hmdzl.spspd.change.items.misc.GunOfSoldier;
import com.hmdzl.spspd.change.items.misc.JumpP;
import com.hmdzl.spspd.change.items.misc.JumpS;
import com.hmdzl.spspd.change.items.misc.PotionOfMage;
import com.hmdzl.spspd.change.items.misc.Shovel;
import com.hmdzl.spspd.change.items.rings.RingOfMagic;
import com.hmdzl.spspd.change.items.skills.ClassSkill;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.weapon.melee.special.Goei;
import com.hmdzl.spspd.change.items.misc.MissileShield;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.GamesInProgress;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Barkskin;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Combo;
import com.hmdzl.spspd.change.actors.buffs.Dewcharge;
import com.hmdzl.spspd.change.actors.buffs.Drowsy;
import com.hmdzl.spspd.change.actors.buffs.Fury;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Regeneration;
import com.hmdzl.spspd.change.actors.buffs.SnipersMark;
import com.hmdzl.spspd.change.actors.buffs.Strength;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.CheckedCell;
import com.hmdzl.spspd.change.effects.Flare;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Amulet;
import com.hmdzl.spspd.change.items.Ankh;
import com.hmdzl.spspd.change.items.Dewdrop;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Heap.Type;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.KindOfWeapon;
import com.hmdzl.spspd.change.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.keys.GoldenKey;
import com.hmdzl.spspd.change.items.keys.GoldenSkeletonKey;
import com.hmdzl.spspd.change.items.keys.IronKey;
import com.hmdzl.spspd.change.items.keys.Key;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.misc.AutoPotion.AutoHealPotion;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.rings.RingOfElements;
import com.hmdzl.spspd.change.items.rings.RingOfForce;
import com.hmdzl.spspd.change.items.rings.RingOfFuror;
import com.hmdzl.spspd.change.items.rings.RingOfHaste;
import com.hmdzl.spspd.change.items.rings.RingOfMight;
import com.hmdzl.spspd.change.items.rings.RingOfTenacity;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.items.misc.Jumpshoes;
import com.hmdzl.spspd.change.items.misc.JumpW;
import com.hmdzl.spspd.change.items.misc.JumpM;
import com.hmdzl.spspd.change.items.misc.JumpR;
import com.hmdzl.spspd.change.items.misc.JumpH;
import com.hmdzl.spspd.change.items.misc.Ankhshield;
import com.hmdzl.spspd.change.items.OrbOfZot;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.levels.features.AlchemyPot;
import com.hmdzl.spspd.change.levels.features.Chasm;
import com.hmdzl.spspd.change.levels.features.Sign;
import com.hmdzl.spspd.change.plants.Earthroot;
import com.hmdzl.spspd.change.plants.Sungrass;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.scenes.SurfaceScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.ui.AttackIndicator;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.ui.QuickSlotButton;
import com.hmdzl.spspd.change.utils.GLog;
import com.hmdzl.spspd.change.windows.WndAscend;
import com.hmdzl.spspd.change.windows.WndDescend;
import com.hmdzl.spspd.change.windows.WndMessage;
import com.hmdzl.spspd.change.windows.WndResurrect;
import com.hmdzl.spspd.change.windows.WndTradeItem;
import com.hmdzl.spspd.change.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Dungeon.hero;

public class Hero extends Char {

	private static final String TXT_LEAVE = "One does not simply leave Pixel Dungeon.";
	private static final String TXT_OVERFILL = "HP Overfilled by %s";

	private static final String TXT_LEVEL_UP = "level up!";
	private static final String TXT_NEW_LEVEL = "Welcome to level %d! Now you are healthier and more focused. "
			+ "It's easier for you to hit enemies and dodge their attacks.";

	public static final String TXT_YOU_NOW_HAVE = "You now have %s";

	private static final String TXT_SOMETHING_ELSE = "There is something else here";
	private static final String TXT_LOCKED_CHEST = "This chest is locked and you don't have matching key";
	private static final String TXT_LOCKED_DOOR = "You don't have a matching key";
	private static final String TXT_NOTICED_SMTH = "You noticed something";

	private static final String TXT_WAIT = "...";
	private static final String TXT_SEARCH = "search";
	//private static final String TXT_JUMP = "jump";
	private static final String TXT_VALUE = "%+d";

	public static final int STARTING_STR = 10;
	public static final int STARTING_MAGIC = 0;


	private static final float TIME_TO_REST = 1f;
	private static final float TIME_TO_SEARCH = 2f;
	//private static final float TIME_TO_JUMP = 2f;

	public HeroClass heroClass = HeroClass.ROGUE;
	public HeroSubClass subClass = HeroSubClass.NONE;

	public int hitSkill = 10;
	public int evadeSkill = 5;
	public int magicSkill = 0;

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

		HP = HT = 30;
		STR = STARTING_STR;
		magicSkill = STARTING_MAGIC;
		
		awareness = 0.1f;

		belongings = new Belongings(this);

		visibleEnemies = new ArrayList<Mob>();
	}

	public int STR() {
		int STR = this.STR;

		for (Buff buff : buffs(RingOfMight.Might.class)) {
			STR += (int)(((RingOfMight.Might) buff).level/5);
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
		if (buff(Arcane.class)!= null)
			magicSkill += 10;

		return magicSkill;
	}	

	private static final String ATTACK = "hitSkill";
	private static final String DEFENSE = "evadeSkill";
	private static final String MAGIC = "magicSkill";
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

		bundle.put(ATTACK, hitSkill);
		bundle.put(DEFENSE, evadeSkill);
		bundle.put(MAGIC, magicSkill);

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

		hitSkill = bundle.getInt(ATTACK);
		evadeSkill = bundle.getInt(DEFENSE);
		magicSkill = bundle.getInt(MAGIC);

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
	}

	public int tier() {
		return belongings.armor == null ? 0 : /*belongings.armor.tier;*/ 7 - Dungeon.skins;
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
		if (Dungeon.hero.heroClass == HeroClass.SOLDIER && Dungeon.skins == 2) {
			dr += Dungeon.hero.lvl;
		}
		return (int)dr;
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
			dmg = bonus == 0 ? str > 1 ? Random.NormalIntRange(1, str) : 1
					: bonus > 0 ? str > 0 ? Random.NormalIntRange(str / 2
							+ bonus, (int) (str * 0.5f * bonus) + str * 2) : 1
							: 0;
		}
		if (bonus > 0){ dmg *= Math.min(3f,(1f + (bonus*1.00/10)*1f));}
		
		if (dmg < 0)
			dmg = 0;
		
		if (buff(Fury.class) != null){ dmg *= 1.30f; }
		
		if (buff(Strength.class) != null){ dmg *= 3f; Buff.detach(this, Strength.class);}

		HighAttack hatk = buff(HighAttack.class);
		if (buff(HighAttack.class) != null){
			dmg *= hatk.level();
			Buff.detach(this, HighAttack.class);
		}

        if (buff(WarGroove.class) != null){ dmg *= 1.5f; Buff.detach(this, WarGroove.class);}
		
		if (buff(Dry.class) != null){ dmg *= 0.80f; }

		if (buff(BloodAngry.class) != null){ dmg *= 1.50f; }

		if (buff(Rhythm2.class) != null){ dmg *= 1.20f; }
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
		
		return (int) dmg;
		
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

		if (hero.heroClass == HeroClass.HUNTRESS && Dungeon.skins == 2) {
				speed += 0.5f;

		}

		return ((HeroSprite) sprite).sprint(subClass == HeroSubClass.FREERUNNER && !isStarving()) ? invisible > 0 ? 2.5f * speed
					: 1.5f * speed : speed;


	}
	
    public boolean canAttack(Char enemy){
		if (enemy == null || pos == enemy.pos)
			return false;

		//can always attack adjacent enemies
		if (Dungeon.level.adjacent(pos, enemy.pos))
			return true;

		KindOfWeapon wep = hero.belongings.weapon;

		if (wep != null && Dungeon.level.distance( pos, enemy.pos ) <= wep.reachFactor(this)){

			boolean[] passable = BArray.not(Level.solid, null);
			for (Mob m : Dungeon.level.mobs)
				passable[m.pos] = false;

			PathFinder.buildDistanceMap(enemy.pos, passable, wep.reachFactor(this));

			return PathFinder.distance[pos] <= wep.reachFactor(this);

		} else {
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
	};

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
		if (buff(HighLight.class) != null){
			viewDistance = 8;
			Dungeon.observe();
		} else if ((Statistics.time > 360 && Statistics.time <601 ) || (Statistics.time > 840 && Statistics.time < 1081 )) {
			viewDistance = 6;
			Dungeon.observe();
		} else if (Statistics.time < 841 && Statistics.time > 600) {
			viewDistance = 8;
			Dungeon.observe();
		} else if (Statistics.time > 1080  && light == null) {
			viewDistance = 4;
			Dungeon.observe();
		} else if (Statistics.time < 361 && light == null) {
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
	
		Egg egg = belongings.getItem(Egg.class);
		if (egg!=null){
			egg.moves++;
		}
		
		DolyaSlate journal = belongings.getItem(DolyaSlate.class);
		if (journal!=null && (Dungeon.depth < 26) 
				&& (journal.level>1 || journal.rooms[0]) 
				&& journal.charge<journal.fullCharge){
			journal.charge++;
			if (journal.charge>=journal.fullCharge){
				GLog.p(Messages.get(Hero.class, "otiluke"));
			}
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

			} else if (curAction instanceof HeroAction.PickUp) {

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
					&& (heap.type != Type.HEAP && heap.type != Type.FOR_SALE)) {

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
				GLog.w( Messages.get(this, "locked_door"));
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
		
		
		if (pos == stairs && pos == Dungeon.level.exit && !Dungeon.level.sealedlevel){

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
			} else if (hero.haspet && hero.petfollow) {
				petfollow=true;
			} else {
				petfollow=false;
			}
	
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
				} else if (hero.haspet && hero.petfollow) {
					petfollow=true;
				} else {
					petfollow=false;
				}
				
				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					if (mob instanceof DriedRose.GhostHero)
						mob.destroy();
                
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
			} else if (hero.haspet && hero.petfollow) {
				petfollow=true;
			} else {
				petfollow=false;
			}
			
			Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
			if (buff != null)
				buff.detach();

			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				if (mob instanceof DriedRose.GhostHero)
					mob.destroy();
            
			InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
			Game.switchScene(InterlevelScene.class);
			
		   } else if (Dungeon.depth > 26 && !Dungeon.townCheck(Dungeon.depth)){
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
				} else if (hero.haspet && hero.petfollow) {
					petfollow=true;
				} else {
					petfollow=false;
				}
				
				Buff buff = buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					if (mob instanceof DriedRose.GhostHero)
						mob.destroy();
                
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
		
		CopyBall copyball = belongings.getItem(CopyBall.class);
		if (copyball!=null && copyball.charge<copyball.fullCharge) {copyball.charge++;}

		switch (heroClass) {
			case WARRIOR:
				if (Dungeon.skins==2) {
					Buff.affect(enemy, Burning.class).reignite(enemy);
				}
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
		case JOKER:
			if ((wep instanceof MeleeWeapon || wep == null) && Random.Int(3)==1) {
				switch (Random.Int (4)){
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
						Buff.prolong(enemy,Locked.class,5f);
						break;

						default:
							break;

				}
			}
				break;
		default:
		}
		if (buff(Shocked.class)!=null){
			Buff.detach(this,Shocked.class);
			Buff.affect(this, Disarm.class,5f);
			damage(this.HP/10,this);
			ArrayList<Lightning.Arc> arcs = new ArrayList<>();
			arcs.add(new Lightning.Arc(pos - Level.WIDTH, pos + Level.WIDTH));
			arcs.add(new Lightning.Arc(pos - 1, pos + 1));
			sprite.parent.add( new Lightning( arcs, null ) );
		}
		if (buff(GoldTouch.class)!=null){
			Dungeon.gold+= damage;
			hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, damage);
		}
		return damage;		

	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		KindOfArmor arm = belongings.armor;

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

		if (arm != null) {
			arm.proc(enemy, this, damage);
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
			interrupt();
			restoreHealth = false;
			}	

		if (this.buff(Drowsy.class) != null) {
			Buff.detach(this, Drowsy.class);
			GLog.w(Messages.get(this, "pain_resist"));
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

		if (subClass == HeroSubClass.LEADER){dmg = (int) Math.ceil(dmg * 0.80);}

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

		} else if ((heap = Dungeon.level.heaps.get(cell)) != null) {

			switch (heap.type) {
			case HEAP:
				curAction = new HeroAction.PickUp(cell);
				break;
			case FOR_SALE:
				curAction = heap.size() == 1 && heap.peek().price() > 0 ? new HeroAction.Buy(
						cell) : new HeroAction.PickUp(cell);
				break;
			default:
				curAction = new HeroAction.OpenChest(cell);
			}

		} else if (Dungeon.level.map[cell] == Terrain.LOCKED_DOOR
				|| Dungeon.level.map[cell] == Terrain.LOCKED_EXIT) {

			curAction = new HeroAction.Unlock(cell);

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

		float percent = exp/(float)maxExp();

		EtherealChains.chainsRecharge chains = buff(EtherealChains.chainsRecharge.class);
		if (chains != null) chains.gainExp(percent);
		
		AlienBag.bagRecharge bags = buff(AlienBag.bagRecharge.class);
		if (bags != null) bags.gainExp(percent);

		boolean levelUp = false;
		while (this.exp >= maxExp()) {
			this.exp -= maxExp();
			lvl++;

			if (Dungeon.isChallenged(Challenges.LISTLESS)){
				HT += 2;
				HP += 1;
				hitSkill++;
				evadeSkill++;
			} else if (lvl < 12) {
			HT += 4;
			HP += 4;
			hitSkill++;
			evadeSkill++;
			} else {
			HT += 5;
			HP += 5;
			hitSkill++;
			evadeSkill++;}
			FourClover.FourCloverBless fcb = buff(FourClover.FourCloverBless.class);
			if (fcb != null){
				HT+=5;
				magicSkill++;
				Dungeon.gold+=1000;
				hero.sprite.showStatus(CharSprite.NEUTRAL, TXT_VALUE, 1000);
				
			}
			if (heroClass == HeroClass.SOLDIER){
				HT+=3;
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
			if (HP > HT && (HT > 10000 || HP>50000)) {
				hero.damage(hero.HT,this);
				if (!hero.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.CHEAT));
				}
			}

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
				if (((RingOfMight.Might) buff).level > 0) {
					HT += ((RingOfMight.Might) buff).level * 10;
				}
			} else if (buff instanceof Paralysis || buff instanceof Vertigo) {
				interrupt();
			}

		}
		BuffIndicator.refreshHero();
	}

	@Override
	public void remove(Buff buff) {
		super.remove(buff);
		if (buff instanceof RingOfMight.Might) {
			if (((RingOfMight.Might) buff).level > 0) {
				int dmg = ((RingOfMight.Might) buff).level * 10;
				HT -= ((RingOfMight.Might) buff).level * 10;
				hero.damage(dmg ,this);
				if (!hero.isAlive()) {
					Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
						//GLog.n("The Chalice sucks your life essence dry...");
				}

			}
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
		/*if (armor != null  && !armor.cursed){

			this.HP = HT/2;

			Buff.detach(this, Paralysis.class);
			spend(-cooldown());

			new Flare(8, 32).color(0xFFFF66, true).show(sprite, 2f);
			CellEmitter.get(this.pos)
					.start(Speck.factory(Speck.LIGHT), 0.2f, 3);
			Sample.INSTANCE.play(Assets.SND_TELEPORT);
			GLog.w(Messages.get(this, "armor_revive"));
			return;
		}*/

		if (ankh != null && ankh.isBlessed()) {
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

        //Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
		//GLog.n( Messages.get(this, "dead") );
		
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

	@Override
	public HashSet<Class<?>> resistances() {
		RingOfElements.Resistance r = buff(RingOfElements.Resistance.class);
		return r == null ? super.resistances() : r.resistances();
	}

	@Override
	public HashSet<Class<?>> immunities() {
		HashSet<Class<?>> immunities = new HashSet<Class<?>>();
		for (Buff buff : buffs()) {
			for (Class<?> immunity : buff.immunities)
				immunities.add(immunity);
		}
		return immunities;
	}

	@Override
	public void next() {
		super.next();
	}

	public static interface Doom {
		public void onDeath();
	}
}
