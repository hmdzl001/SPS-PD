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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.normalarmor.BulletArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CDArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MachineArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MailArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MultiplelayerArmor;
import com.hmdzl.spspd.items.armor.normalarmor.PhantomArmor;
import com.hmdzl.spspd.items.armor.normalarmor.PlateArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ProtectiveclothingArmor;
import com.hmdzl.spspd.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ScaleArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.artifacts.EyeOfSkadi;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.bombs.Bomb;
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.items.bombs.DarkBomb;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.bombs.EarthBomb;
import com.hmdzl.spspd.items.bombs.FireBomb;
import com.hmdzl.spspd.items.bombs.FishingBomb;
import com.hmdzl.spspd.items.bombs.HugeBomb;
import com.hmdzl.spspd.items.bombs.IceBomb;
import com.hmdzl.spspd.items.bombs.LightBomb;
import com.hmdzl.spspd.items.bombs.StormBomb;
import com.hmdzl.spspd.items.eggs.BlueDragonEgg;
import com.hmdzl.spspd.items.eggs.CocoCatEgg;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.eggs.GoldDragonEgg;
import com.hmdzl.spspd.items.eggs.GreenDragonEgg;
import com.hmdzl.spspd.items.eggs.LeryFireEgg;
import com.hmdzl.spspd.items.eggs.LightDragonEgg;
import com.hmdzl.spspd.items.eggs.RandomAtkEgg;
import com.hmdzl.spspd.items.eggs.RandomColEgg;
import com.hmdzl.spspd.items.eggs.RandomDefEgg;
import com.hmdzl.spspd.items.eggs.RandomEasterEgg;
import com.hmdzl.spspd.items.eggs.RandomEgg;
import com.hmdzl.spspd.items.eggs.RandomEgg1;
import com.hmdzl.spspd.items.eggs.RandomEgg10;
import com.hmdzl.spspd.items.eggs.RandomEgg11;
import com.hmdzl.spspd.items.eggs.RandomEgg12;
import com.hmdzl.spspd.items.eggs.RandomEgg2;
import com.hmdzl.spspd.items.eggs.RandomEgg3;
import com.hmdzl.spspd.items.eggs.RandomEgg4;
import com.hmdzl.spspd.items.eggs.RandomEgg5;
import com.hmdzl.spspd.items.eggs.RandomEgg6;
import com.hmdzl.spspd.items.eggs.RandomEgg7;
import com.hmdzl.spspd.items.eggs.RandomEgg8;
import com.hmdzl.spspd.items.eggs.RandomEgg9;
import com.hmdzl.spspd.items.eggs.RedDragonEgg;
import com.hmdzl.spspd.items.eggs.ScorpionEgg;
import com.hmdzl.spspd.items.eggs.ShadowDragonEgg;
import com.hmdzl.spspd.items.eggs.VioletDragonEgg;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.items.food.completefood.Chickennugget;
import com.hmdzl.spspd.items.food.completefood.Chocolate;
import com.hmdzl.spspd.items.food.completefood.FoodFans;
import com.hmdzl.spspd.items.food.completefood.Frenchfries;
import com.hmdzl.spspd.items.food.completefood.Fruitsalad;
import com.hmdzl.spspd.items.food.completefood.Gel;
import com.hmdzl.spspd.items.food.completefood.Hamburger;
import com.hmdzl.spspd.items.food.completefood.Herbmeat;
import com.hmdzl.spspd.items.food.completefood.HoneyGel;
import com.hmdzl.spspd.items.food.completefood.HoneyWater;
import com.hmdzl.spspd.items.food.completefood.Honeymeat;
import com.hmdzl.spspd.items.food.completefood.Honeyrice;
import com.hmdzl.spspd.items.food.completefood.Icecream;
import com.hmdzl.spspd.items.food.completefood.Kebab;
import com.hmdzl.spspd.items.food.completefood.Meatroll;
import com.hmdzl.spspd.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.items.food.completefood.Porksoup;
import com.hmdzl.spspd.items.food.completefood.Ricefood;
import com.hmdzl.spspd.items.food.completefood.Vegetablekebab;
import com.hmdzl.spspd.items.food.completefood.Vegetableroll;
import com.hmdzl.spspd.items.food.completefood.Vegetablesoup;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.items.food.staplefood.OverpricedRation;
import com.hmdzl.spspd.items.food.staplefood.Pasty;
import com.hmdzl.spspd.items.medicine.BlueMilk;
import com.hmdzl.spspd.items.medicine.DeathCap;
import com.hmdzl.spspd.items.medicine.Earthstar;
import com.hmdzl.spspd.items.medicine.Foamedbeverage;
import com.hmdzl.spspd.items.medicine.GoldenJelly;
import com.hmdzl.spspd.items.medicine.GreenSpore;
import com.hmdzl.spspd.items.medicine.Hardpill;
import com.hmdzl.spspd.items.medicine.JackOLantern;
import com.hmdzl.spspd.items.medicine.Magicpill;
import com.hmdzl.spspd.items.medicine.Musicpill;
import com.hmdzl.spspd.items.medicine.Pill;
import com.hmdzl.spspd.items.medicine.PixieParasol;
import com.hmdzl.spspd.items.medicine.Powerpill;
import com.hmdzl.spspd.items.medicine.Shootpill;
import com.hmdzl.spspd.items.medicine.Smashpill;
import com.hmdzl.spspd.items.misc.JumpA;
import com.hmdzl.spspd.items.misc.JumpF;
import com.hmdzl.spspd.items.misc.JumpH;
import com.hmdzl.spspd.items.misc.JumpM;
import com.hmdzl.spspd.items.misc.JumpP;
import com.hmdzl.spspd.items.misc.JumpR;
import com.hmdzl.spspd.items.misc.JumpS;
import com.hmdzl.spspd.items.misc.JumpW;
import com.hmdzl.spspd.items.nornstone.BlueNornStone;
import com.hmdzl.spspd.items.nornstone.GreenNornStone;
import com.hmdzl.spspd.items.nornstone.NornStone;
import com.hmdzl.spspd.items.nornstone.OrangeNornStone;
import com.hmdzl.spspd.items.nornstone.PurpleNornStone;
import com.hmdzl.spspd.items.nornstone.YellowNornStone;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfExperience;
import com.hmdzl.spspd.items.potions.PotionOfFrost;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.potions.PotionOfMixing;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.items.potions.PotionOfPurity;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.items.rings.RingOfElements;
import com.hmdzl.spspd.items.rings.RingOfEnergy;
import com.hmdzl.spspd.items.rings.RingOfEvasion;
import com.hmdzl.spspd.items.rings.RingOfForce;
import com.hmdzl.spspd.items.rings.RingOfFuror;
import com.hmdzl.spspd.items.rings.RingOfHaste;
import com.hmdzl.spspd.items.rings.RingOfMagic;
import com.hmdzl.spspd.items.rings.RingOfMight;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.rings.RingOfTenacity;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.items.scrolls.ScrollOfLullaby;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfMirrorImage;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.scrolls.ScrollOfSacrifice;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.scrolls.ScrollOfTerror;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfBlood;
import com.hmdzl.spspd.items.wands.WandOfCharm;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.wands.WandOfError;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
import com.hmdzl.spspd.items.wands.WandOfTCloud;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.melee.AssassinsBlade;
import com.hmdzl.spspd.items.weapon.melee.BattleAxe;
import com.hmdzl.spspd.items.weapon.melee.Club;
import com.hmdzl.spspd.items.weapon.melee.CurseBox;
import com.hmdzl.spspd.items.weapon.melee.Dagger;
import com.hmdzl.spspd.items.weapon.melee.Dualknive;
import com.hmdzl.spspd.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.items.weapon.melee.Flute;
import com.hmdzl.spspd.items.weapon.melee.Glaive;
import com.hmdzl.spspd.items.weapon.melee.Gsword;
import com.hmdzl.spspd.items.weapon.melee.Halberd;
import com.hmdzl.spspd.items.weapon.melee.HandLight;
import com.hmdzl.spspd.items.weapon.melee.Handaxe;
import com.hmdzl.spspd.items.weapon.melee.Harp;
import com.hmdzl.spspd.items.weapon.melee.HolyWater;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.items.weapon.melee.Lance;
import com.hmdzl.spspd.items.weapon.melee.Mace;
import com.hmdzl.spspd.items.weapon.melee.MageBook;
import com.hmdzl.spspd.items.weapon.melee.MirrorDoll;
import com.hmdzl.spspd.items.weapon.melee.Nunchakus;
import com.hmdzl.spspd.items.weapon.melee.PrayerWheel;
import com.hmdzl.spspd.items.weapon.melee.Rapier;
import com.hmdzl.spspd.items.weapon.melee.Scimitar;
import com.hmdzl.spspd.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.items.weapon.melee.Spear;
import com.hmdzl.spspd.items.weapon.melee.StoneCross;
import com.hmdzl.spspd.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.items.weapon.melee.TrickSand;
import com.hmdzl.spspd.items.weapon.melee.Trumpet;
import com.hmdzl.spspd.items.weapon.melee.WarHammer;
import com.hmdzl.spspd.items.weapon.melee.Wardrum;
import com.hmdzl.spspd.items.weapon.melee.Whip;
import com.hmdzl.spspd.items.weapon.melee.WindBottle;
import com.hmdzl.spspd.items.weapon.melee.WoodenStaff;
import com.hmdzl.spspd.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.items.weapon.melee.special.Spork;
import com.hmdzl.spspd.items.weapon.melee.special.TekkoKagi;
import com.hmdzl.spspd.items.weapon.melee.special.WraithBreath;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.EmpBola;
import com.hmdzl.spspd.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.items.weapon.missiles.HugeShuriken;
import com.hmdzl.spspd.items.weapon.missiles.IncendiaryDart;
import com.hmdzl.spspd.items.weapon.missiles.PoisonDart;
import com.hmdzl.spspd.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.items.weapon.missiles.ShatteredAmmo;
import com.hmdzl.spspd.items.weapon.missiles.ShitBall;
import com.hmdzl.spspd.items.weapon.missiles.Skull;
import com.hmdzl.spspd.items.weapon.missiles.Smoke;
import com.hmdzl.spspd.items.weapon.missiles.Tamahawk;
import com.hmdzl.spspd.items.weapon.missiles.Wave;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.Blindweed;
import com.hmdzl.spspd.plants.Dewcatcher;
import com.hmdzl.spspd.plants.Dreamfoil;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Fadeleaf;
import com.hmdzl.spspd.plants.Firebloom;
import com.hmdzl.spspd.plants.Flytrap;
import com.hmdzl.spspd.plants.Icecap;
import com.hmdzl.spspd.plants.NutPlant;
import com.hmdzl.spspd.plants.Phaseshift;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.plants.Sorrowmoss;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.plants.Stormvine;
import com.hmdzl.spspd.plants.Sungrass;
import com.watabou.utils.Random;

import java.util.HashMap;

public class Generator {

	public enum Category {
		WEAPON(150, Weapon.class), MELEEWEAPON( 20,Weapon.class),OLDWEAPON(0,Weapon.class),RANGEWEAPON(20,Weapon.class),GUNWEAPON(0,Weapon.class),ARMOR(100, Armor.class),
		POTION(500, Potion.class), SCROLL(400, Scroll.class), WAND(40, Wand.class), RING(15, Ring.class),
		ARTIFACT(20, Artifact.class), SEED(5, Plant.Seed.class), SEED2(0,	Plant.Seed.class),SEED3(0,	Plant.Seed.class),SEED4(0,	Plant.Seed.class),
		FOOD(10, Food.class), GOLD(500, Gold.class), BERRY(50, Food.class), MUSHROOM(5, Pill.class), BOMBS(20, Bomb.class),
		NORNSTONE(0,NornStone.class), EGGS(0, Egg.class), HIGHFOOD(0,Food.class), SUMMONED(1,Item.class), PILL(5, Pill.class),LINKDROP(0, Item.class),MUSICWEAPON(0,Weapon.class)
		,SHOES(0,Item.class),DEW(0,Item.class),BASEPET(0,Egg.class);

		public Class<?>[] classes;
		public float[] probs;

		public float prob;
		public Class<? extends Item> superClass;

		Category(float prob, Class<? extends Item> superClass) {
			this.prob = prob;
			this.superClass = superClass;
		}

		public static int order(Item item) {
			for (int i = 0; i < values().length; i++) {
				if (values()[i].superClass.isInstance(item)) {
					return i;
				}
			}

			return item instanceof Bag ? Integer.MAX_VALUE
					: Integer.MAX_VALUE - 1;
		}
	}

    private static HashMap<Category, Float> categoryProbs = new HashMap<Generator.Category, Float>();
	
	//private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{  1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

	static {

		Category.GOLD.classes = new Class<?>[] { Gold.class };
		Category.GOLD.probs = new float[] { 1 };

		Category.SCROLL.classes = new Class<?>[] { ScrollOfIdentify.class,
				ScrollOfTeleportation.class, ScrollOfRemoveCurse.class,
				ScrollOfUpgrade.class, ScrollOfRecharging.class,
				ScrollOfMagicMapping.class, ScrollOfRage.class,
				ScrollOfTerror.class, ScrollOfLullaby.class,
				ScrollOfMagicalInfusion.class, ScrollOfPsionicBlast.class,
				ScrollOfMirrorImage.class, ScrollOfRegrowth.class, ScrollOfSacrifice.class};
		Category.SCROLL.probs = new float[] { 30, 10, 15, 0, 10, 20, 10, 8, 8,
				0, 3, 6, 6, 0 };

		Category.POTION.classes = new Class<?>[] { PotionOfHealing.class,
				PotionOfExperience.class, PotionOfToxicGas.class,
				PotionOfParalyticGas.class, PotionOfLiquidFlame.class,
				PotionOfLevitation.class, PotionOfStrength.class,
				PotionOfMindVision.class, PotionOfPurity.class,
				PotionOfInvisibility.class, PotionOfMight.class,
				PotionOfFrost.class, PotionOfMending.class,
				PotionOfOverHealing.class, PotionOfShield.class,PotionOfMixing.class};
		Category.POTION.probs = new float[] { 10, 5, 15, 10, 15, 10, 0, 20, 12,
				10, 0, 10, 15, 4, 5, 0};

		Category.WAND.classes = new Class<?>[] { WandOfAcid.class,
				WandOfFreeze.class, WandOfFirebolt.class,
				WandOfLight.class, WandOfSwamp.class, WandOfBlood.class,
				WandOfLightning.class, WandOfCharm.class,
				WandOfFlow.class, WandOfFlock.class,
				WandOfMagicMissile.class, WandOfDisintegration.class,
				WandOfMeteorite.class, WandOfError.class, WandOfTCloud.class};
		Category.WAND.probs = new float[] { 5, 5, 5, 5, 5, 5, 5, 5, 5,
				5, 5, 5, 5, 0, 5  };

		Category.WEAPON.classes = new Class<?>[] { ErrorW.class,Pumpkin.class,TekkoKagi.class,
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class,WraithBreath.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class,Spork.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Handcannon.class,Club.class,
                Gsword.class, Halberd.class, WarHammer.class, RunicBlade.class, Lance.class,
				Triangolo.class, Flute.class, Wardrum.class, Trumpet.class, Harp.class,
				WoodenStaff.class, Mace.class, HolyWater.class, PrayerWheel.class, StoneCross.class
		};
		Category.WEAPON.probs = new float[] {
				0,0,0,
				1,1,1,1,
				1,1,1,0,1,
				1,1,1,0,1,
				1,1,1,0,1,
				1,1,1,0,1,
				1,1,1,1,1,
				1,1,1,1,1,
		};

		Category.RANGEWEAPON.classes = new Class<?>[] {
				EmpBola.class ,EscapeKnive.class,PoisonDart.class,Smoke.class,IncendiaryDart.class,Tamahawk.class,
				Skull.class, RiceBall.class, Wave.class, ShatteredAmmo.class, HugeShuriken.class, ShitBall.class,
				Boomerang.class};
		Category.RANGEWEAPON.probs = new float[] {
				1,1,1,1,1,1,
                1,1,1,1,1,1,
				0
		};

		Category.MELEEWEAPON.classes = new Class<?>[] {
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Club.class,
				Gsword.class, Halberd.class, WarHammer.class, Lance.class,
				Triangolo.class, Flute.class, Wardrum.class, Trumpet.class, Harp.class,
				WoodenStaff.class, Mace.class, HolyWater.class, PrayerWheel.class, StoneCross.class,
				TrickSand.class, MirrorDoll.class, WindBottle.class, HandLight.class, CurseBox.class};
		Category.MELEEWEAPON.probs = new float[] {
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
		};
		
		Category.OLDWEAPON.classes = new Class<?>[] {
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Club.class,
				Gsword.class, Halberd.class, WarHammer.class, Lance.class,};
		Category.OLDWEAPON.probs = new float[] {
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
		};

		Category.GUNWEAPON.classes = new Class<?>[] {
				GunA.class, GunB.class, GunC.class, GunD.class, GunE.class};
		Category.GUNWEAPON.probs = new float[] {
				1,1,1,1,1,
		};

		Category.MUSICWEAPON.classes = new Class<?>[] {
				Triangolo.class, Flute.class, Wardrum.class, Trumpet.class, Harp.class};
		Category.MUSICWEAPON.probs = new float[] {
				1,1,1,1,1,
		};

		Category.ARMOR.classes = new Class<?>[] {
				ClothArmor.class, WoodenArmor.class, VestArmor.class,
				LeatherArmor.class, CeramicsArmor.class, RubberArmor.class,
				DiscArmor.class, StoneArmor.class, CDArmor.class,
				MailArmor.class, MultiplelayerArmor.class, StyrofoamArmor.class,
				ScaleArmor.class, BulletArmor.class, ProtectiveclothingArmor.class,
				PlateArmor.class, MachineArmor.class, PhantomArmor.class,
				ErrorArmor.class };
		Category.ARMOR.probs = new float[] {
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				0 };

		Category.FOOD.classes = new Class<?>[] { NormalRation.class, Pasty.class, OverpricedRation.class};
		Category.FOOD.probs = new float[] { 8, 2, 5 };

		Category.RING.classes = new Class<?>[] { RingOfAccuracy.class,
				RingOfEvasion.class, RingOfElements.class, RingOfForce.class,
				RingOfFuror.class, RingOfHaste.class, RingOfMagic.class,
				RingOfMight.class, RingOfSharpshooting.class,
				RingOfTenacity.class, RingOfEnergy.class };
		Category.RING.probs = new float[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

		Category.ARTIFACT.classes = new Class<?>[] { CapeOfThorns.class,
				ChaliceOfBlood.class, CloakOfShadows.class, HornOfPlenty.class,
				MasterThievesArmband.class, SandalsOfNature.class,
				TalismanOfForesight.class, TimekeepersHourglass.class,
				UnstableSpellbook.class, AlchemistsToolkit.class, RobotDMT.class,
				EyeOfSkadi.class, EtherealChains.class,
				DriedRose.class, GlassTotem.class, AlienBag.class
		};
		Category.ARTIFACT.probs =  new float[]{  1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

		Category.SEED.classes = new Class<?>[] { 
				Firebloom.Seed.class, Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Sungrass.Seed.class, Earthroot.Seed.class, Fadeleaf.Seed.class, Rotberry.Seed.class,
				BlandfruitBush.Seed.class, Dreamfoil.Seed.class, Stormvine.Seed.class, NutPlant.Seed.class,
				Starflower.Seed.class, Phaseshift.Seed.class, Flytrap.Seed.class, Dewcatcher.Seed.class,
				Seedpod.Seed.class};
		
		Category.SEED.probs = new float[] { 12, 12, 12, 12,
				                             12, 12, 12, 0,
				                              4, 12, 12, 12,
				                               3, 3, 4, 8,
				                              2};
		
		
		Category.SEED2.classes = new Class<?>[] { Firebloom.Seed.class,
				Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Stormvine.Seed.class, Starflower.Seed.class};
		
		Category.SEED2.probs = new float[] { 8, 4, 6, 4, 3, 1 };

		Category.SEED3.classes = new Class<?>[] {Sungrass.Seed.class, Earthroot.Seed.class, BlandfruitBush.Seed.class, Dreamfoil.Seed.class,
				Starflower.Seed.class, Dewcatcher.Seed.class, Seedpod.Seed.class};

		Category.SEED3.probs = new float[] { 8, 4, 2, 4, 3, 1 , 1 };

		Category.SEED4.classes = new Class<?>[] {Sungrass.Seed.class, Flytrap.Seed.class, Dreamfoil.Seed.class,
				Starflower.Seed.class, Phaseshift.Seed.class, NutPlant.Seed.class,BlandfruitBush.Seed.class , Seedpod.Seed.class};

		Category.SEED4.probs = new float[] { 4,  1, 4, 2, 1 , 3,1,1 };
		
		
		Category.BERRY.classes = new Class<?>[] {Blackberry.class, Blueberry.class, Cloudberry.class, Moonberry.class};
		Category.BERRY.probs = new float[] {6,2,2,2};
		
		Category.MUSHROOM.classes = new Class<?>[] {BlueMilk.class, DeathCap.class, Earthstar.class, JackOLantern.class, PixieParasol.class, GoldenJelly.class, GreenSpore.class};
		Category.MUSHROOM.probs = new float[] {4,3,3,3,3,3,2};
		
		Category.NORNSTONE.classes = new Class<?>[] {BlueNornStone.class, GreenNornStone.class, OrangeNornStone.class, PurpleNornStone.class, YellowNornStone.class};
		Category.NORNSTONE.probs = new float[] {2,2,2,2,2};

		Category.EGGS.classes = new Class<?>[] { BlueDragonEgg.class, CocoCatEgg.class, EasterEgg.class,Egg.class,
                LightDragonEgg.class, GreenDragonEgg.class, LeryFireEgg.class, RedDragonEgg.class, ScorpionEgg.class,
                ShadowDragonEgg.class, VioletDragonEgg.class,
				GoldDragonEgg.class, RandomEgg.class };
		Category.EGGS.probs = new float[] {1,0,1,1,1,1,1,1,1,1,1,1,1};

		Category.HIGHFOOD.classes = new Class<?>[] { Chickennugget.class,Foamedbeverage.class,Fruitsalad.class,
				Hamburger.class,Herbmeat.class,Honeymeat.class,Honeyrice.class,
				Icecream.class,Kebab.class,PerfectFood.class,Porksoup.class,
				Ricefood.class,Vegetablekebab.class,Vegetablesoup.class,
				Meatroll.class,Vegetableroll.class,HoneyGel.class,Gel.class,HoneyWater.class,
				Chocolate.class, FoodFans.class,Frenchfries.class};
		Category.HIGHFOOD.probs = new float[] { 1,1,1,1,
		1,1,1,1,
		1,1,1,1,
		1,1,1,
		1,1,1,1,1,
		1,1,1};
		Category.BOMBS.classes = new Class<?>[] { BuildBomb.class,DungeonBomb.class,
				HugeBomb.class,
				FireBomb.class,IceBomb.class,EarthBomb.class,StormBomb.class,
				LightBomb.class,DarkBomb.class,FishingBomb.class
		};
		Category.BOMBS.probs = new float[] { 0,3,
				0,
				1,1,1,1,
				1,1,1};
		Category.SUMMONED.classes = new Class<?>[] {ActiveMrDestructo.class,Mobile.class,FairyCard.class,Honeypot.class

		};
		Category.SUMMONED.probs = new float[] { 1,1,1,1};

		Category.PILL.classes = new Class<?>[] {Hardpill.class, Magicpill.class,Musicpill.class,
				Powerpill.class, Shootpill.class, Smashpill.class
		};
		Category.PILL.probs = new float[] { 1,1,1,1,1,1};

		Category.LINKDROP.classes = new Class<?>[] {
				BuildBomb.class,DungeonBomb.class,
				HugeBomb.class, FireBomb.class,IceBomb.class,EarthBomb.class,StormBomb.class,
				LightBomb.class,DarkBomb.class,FishingBomb.class,
				EmpBola.class ,EscapeKnive.class,PoisonDart.class,Smoke.class,IncendiaryDart.class,Tamahawk.class,
				Skull.class, RiceBall.class, Wave.class, ShatteredAmmo.class, HugeShuriken.class, ShitBall.class
		};
		Category.LINKDROP.probs = new float[] { 3,1,
		1,1,1,1,1,
		1,1,1,
		2,2,2,2,2,2,
		2,2,2,2,2,2};
	
		Category.SHOES.classes = new Class<?>[] {
				JumpW.class,JumpM.class,
				JumpR.class,JumpH.class, JumpP.class,
				JumpS.class,JumpF.class, JumpA.class
		};
		Category.SHOES.probs = new float[] { 1,1,1,1,1,1,1,1};

		Category.DEW.classes = new Class<?>[] {
				Dewdrop.class,YellowDewdrop.class,RedDewdrop.class,VioletDewdrop.class,Dewcatcher.Seed.class
		};
		Category.DEW.probs = new float[] { 4,8,5,3,1};

		Category.BASEPET.classes = new Class<?>[] { RandomAtkEgg.class, RandomDefEgg.class, RandomColEgg.class,RandomEasterEgg.class,
				RandomEgg1.class, RandomEgg2.class, RandomEgg3.class, RandomEgg4.class, RandomEgg5.class,RandomEgg6.class,
				RandomEgg7.class, RandomEgg8.class, RandomEgg9.class, RandomEgg10.class,RandomEgg11.class,RandomEgg12.class};
		Category.BASEPET.probs = new float[] {1,1,1,1,
				1,1,1,1,1,1,
				1,1,1,1,1,1};
		
	}

	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put(cat, cat.prob);
		}
	}

	public static Item random() {
		return random(Random.chances(categoryProbs));
	}

	public static Item chestrandom() {
		return random(Random.chances(categoryProbs));
	}

	public static Item random(Category cat) {
		try {

			categoryProbs.put(cat, categoryProbs.get(cat) / 2);

			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				return randomArtifact();
			case RING:	
				return randomRing();
			default:
				return ((Item) cat.classes[Random.chances(cat.probs)]
						.newInstance()).random();
			}

		} catch (Exception e) {

			return null;

		}
	}

	public static Item random(Class<? extends Item> cl) {
		try {

			return cl.newInstance().random();

		} catch (Exception e) {

			return null;

		}
	}

	public static Armor randomArmor() {
		int curStr = Hero.STARTING_STR
				+ Dungeon.limitedDrops.strengthPotions.count;

		return randomArmor(curStr);
	}

	public static Armor randomArmor(int targetStr) {

		Category cat = Category.ARMOR;

		try {
			Armor a1 = (Armor) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Armor a2 = (Armor) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			a1.random();
			a2.random();

			return Math.abs(targetStr - a1.STR) < Math.abs(targetStr - a2.STR) ? a1
					: a2;
		} catch (Exception e) {
			return null;
		}
	}

	public static Weapon randomWeapon() {
		int curStr = Hero.STARTING_STR
				+ Dungeon.limitedDrops.strengthPotions.count;

		return randomWeapon(curStr);
	}

	public static Weapon randomWeapon(int targetStr) {

		try {
			Category cat = Category.WEAPON;

			Weapon w1 = (Weapon) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Weapon w2 = (Weapon) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			w1.random();
			w2.random();

			return Math.abs(targetStr - w1.STR) < Math.abs(targetStr - w2.STR) ? w1
					: w2;
		} catch (Exception e) {
			return null;
		}
	}

	// enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;

			Artifact t1 = (Artifact) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Artifact t2 = (Artifact) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			t1.random();
			t2.random();
			
			return Random.Int(6) > 2 ? t1 : t2;			

		} catch (Exception e) {
			//Log.e("PD", Log.getStackTraceString(e));
			return null;
		}
	}
	
	public static Ring randomRing() {

		try {
			Category cat = Category.RING;

			Ring r1 = (Ring) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Ring r2 = (Ring) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			r1.random();
			r2.random();
			
			return Random.Int(6) > 2 ? r1 : r2;			

		} catch (Exception e) {
			//Log.e("PD", Log.getStackTraceString(e));
			return null;
		}
	}	


	/*public static boolean removeArtifact(Artifact artifact) {
		if (spawnedArtifacts.contains(artifact.getClass().getSimpleName()))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact.getClass())) {
				if (cat.probs[i] > 0) {
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact.getClass().getSimpleName());
					return true;
				} else
					return false;
			}

		return false;
	}*/

	// resets artifact probabilities, for new dungeons
	/*public static void initArtifacts() {
		spawnedArtifacts.clear();
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS;
		//checks for dried rose quest completion, adds the rose in accordingly.
		if (Ghost.Quest.processed) Category.ARTIFACT.probs[12] = 1;
	}*/

	//private static ArrayList<String> spawnedArtifacts = new ArrayList<String>();

	//private static final String ARTIFACTS = "artifacts";

	// used to store information on which artifacts have been spawned.
/*	public static void storeInBundle(Bundle bundle) {
		bundle.put(ARTIFACTS,
				spawnedArtifacts.toArray(new String[spawnedArtifacts.size()]));
	}*/

	/*public static void restoreFromBundle(Bundle bundle) {
		initArtifacts();

		if (bundle.contains(ARTIFACTS)) {
			Category cat = Category.ARTIFACT;

			for (String artifact : spawnedArtifacts)
				for (int i = 0; i < cat.classes.length; i++)
					if ((cat.classes[i].getSimpleName().equals(artifact)) && (cat.probs[i] > 0)) {
						cat.probs[i] = 0;
						spawnedArtifacts.add(artifact);
					}
		}
	}*/
}
