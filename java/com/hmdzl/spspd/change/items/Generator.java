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
package com.hmdzl.spspd.change.items;

import java.util.HashMap;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.blobs.Freezing;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.armor.normalarmor.BulletArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.CDArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.MachineArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.MultiplelayerArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.PhantomArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.ProtectiveclothingArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.change.items.artifacts.AlienBag;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.items.artifacts.GlassTotem;
import com.hmdzl.spspd.change.items.artifacts.RobotDMT;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.bombs.BuildBomb;
import com.hmdzl.spspd.change.items.bombs.DarkBomb;
import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.items.bombs.EarthBomb;
import com.hmdzl.spspd.change.items.bombs.FireBomb;
import com.hmdzl.spspd.change.items.bombs.FishingBomb;
import com.hmdzl.spspd.change.items.bombs.HugeBomb;
import com.hmdzl.spspd.change.items.bombs.IceBomb;
import com.hmdzl.spspd.change.items.bombs.LightBomb;
import com.hmdzl.spspd.change.items.bombs.MiniBomb;
import com.hmdzl.spspd.change.items.bombs.StormBomb;
import com.hmdzl.spspd.change.items.eggs.BlueDragonEgg;
import com.hmdzl.spspd.change.items.eggs.CocoCatEgg;
import com.hmdzl.spspd.change.items.eggs.EasterEgg;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.eggs.GoldDragonEgg;
import com.hmdzl.spspd.change.items.eggs.GreenDragonEgg;
import com.hmdzl.spspd.change.items.eggs.LeryFireEgg;
import com.hmdzl.spspd.change.items.eggs.LightDragonEgg;
import com.hmdzl.spspd.change.items.eggs.RandomEgg;
import com.hmdzl.spspd.change.items.eggs.RedDragonEgg;
import com.hmdzl.spspd.change.items.eggs.ScorpionEgg;
import com.hmdzl.spspd.change.items.eggs.ShadowDragonEgg;
import com.hmdzl.spspd.change.items.eggs.SpiderEgg;
import com.hmdzl.spspd.change.items.eggs.VelociroosterEgg;
import com.hmdzl.spspd.change.items.eggs.VioletDragonEgg;
import com.hmdzl.spspd.change.items.food.completefood.Chickennugget;
import com.hmdzl.spspd.change.items.food.completefood.Chocolate;
import com.hmdzl.spspd.change.items.food.completefood.Foamedbeverage;
import com.hmdzl.spspd.change.items.food.completefood.FoodFans;
import com.hmdzl.spspd.change.items.food.completefood.Frenchfries;
import com.hmdzl.spspd.change.items.food.completefood.Fruitsalad;
import com.hmdzl.spspd.change.items.food.completefood.Gel;
import com.hmdzl.spspd.change.items.food.completefood.Hamburger;
import com.hmdzl.spspd.change.items.food.completefood.Herbmeat;
import com.hmdzl.spspd.change.items.food.completefood.HoneyGel;
import com.hmdzl.spspd.change.items.food.completefood.HoneyWater;
import com.hmdzl.spspd.change.items.food.completefood.Honeymeat;
import com.hmdzl.spspd.change.items.food.completefood.Honeyrice;
import com.hmdzl.spspd.change.items.food.completefood.Icecream;
import com.hmdzl.spspd.change.items.food.completefood.Kebab;
import com.hmdzl.spspd.change.items.food.completefood.Meatroll;
import com.hmdzl.spspd.change.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.change.items.food.completefood.Porksoup;
import com.hmdzl.spspd.change.items.food.completefood.Ricefood;
import com.hmdzl.spspd.change.items.food.completefood.Vegetablekebab;
import com.hmdzl.spspd.change.items.food.completefood.Vegetableroll;
import com.hmdzl.spspd.change.items.food.completefood.Vegetablesoup;
import com.hmdzl.spspd.change.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.change.items.medicine.Hardpill;
import com.hmdzl.spspd.change.items.medicine.Magicpill;
import com.hmdzl.spspd.change.items.medicine.Musicpill;
import com.hmdzl.spspd.change.items.medicine.Pill;
import com.hmdzl.spspd.change.items.medicine.Powerpill;
import com.hmdzl.spspd.change.items.medicine.Shootpill;
import com.hmdzl.spspd.change.items.medicine.Smashpill;
import com.hmdzl.spspd.change.items.medicine.PixieParasol;
import com.hmdzl.spspd.change.items.medicine.GreenSpore;
import com.hmdzl.spspd.change.items.medicine.GoldenJelly;
import com.hmdzl.spspd.change.items.medicine.Earthstar;
import com.hmdzl.spspd.change.items.medicine.DeathCap;
import com.hmdzl.spspd.change.items.medicine.JackOLantern;
import com.hmdzl.spspd.change.items.medicine.BlueMilk;
import com.hmdzl.spspd.change.items.rings.RingOfEnergy;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfSacrifice;
import com.hmdzl.spspd.change.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.change.items.summon.FairyCard;
import com.hmdzl.spspd.change.items.summon.Mobile;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.change.items.wands.WandOfTCloud;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunB;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunD;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.melee.Club;
import com.hmdzl.spspd.change.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.change.items.weapon.melee.Flute;
import com.hmdzl.spspd.change.items.weapon.melee.Harp;
import com.hmdzl.spspd.change.items.weapon.melee.HolyWater;
import com.hmdzl.spspd.change.items.weapon.melee.Lance;
import com.hmdzl.spspd.change.items.weapon.melee.Mace;
import com.hmdzl.spspd.change.items.weapon.melee.PrayerWheel;
import com.hmdzl.spspd.change.items.weapon.melee.Rapier;
import com.hmdzl.spspd.change.items.weapon.melee.StoneCross;
import com.hmdzl.spspd.change.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.change.items.weapon.melee.Trumpet;
import com.hmdzl.spspd.change.items.weapon.melee.Wardurm;
import com.hmdzl.spspd.change.items.weapon.melee.WoodenStaff;
import com.hmdzl.spspd.change.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.change.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.change.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.change.items.weapon.missiles.EmpBola;
import com.hmdzl.spspd.change.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.change.items.weapon.missiles.HugeShuriken;
import com.hmdzl.spspd.change.items.weapon.missiles.NormalBomb;
import com.hmdzl.spspd.change.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.change.items.weapon.missiles.ShatteredAmmo;
import com.hmdzl.spspd.change.items.weapon.missiles.Skull;
import com.hmdzl.spspd.change.items.weapon.missiles.Smoke;
import com.hmdzl.spspd.change.items.weapon.missiles.Wave;
import com.hmdzl.spspd.change.plants.Rotberry;
import com.hmdzl.spspd.change.items.armor.Armor;
import com.hmdzl.spspd.change.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.MailArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.PlateArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.ScaleArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.change.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.change.items.artifacts.Artifact;
import com.hmdzl.spspd.change.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.change.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.change.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.change.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.change.items.artifacts.EyeOfSkadi;
import com.hmdzl.spspd.change.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.change.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.change.items.bags.Bag;
import com.hmdzl.spspd.change.items.food.fruit.Blackberry;
import com.hmdzl.spspd.change.items.food.fruit.Blueberry;
import com.hmdzl.spspd.change.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.food.fruit.Moonberry;
import com.hmdzl.spspd.change.items.food.staplefood.Pasty;
import com.hmdzl.spspd.change.items.food.staplefood.OverpricedRation;
import com.hmdzl.spspd.change.items.nornstone.BlueNornStone;
import com.hmdzl.spspd.change.items.nornstone.GreenNornStone;
import com.hmdzl.spspd.change.items.nornstone.NornStone;
import com.hmdzl.spspd.change.items.nornstone.OrangeNornStone;
import com.hmdzl.spspd.change.items.nornstone.PurpleNornStone;
import com.hmdzl.spspd.change.items.nornstone.YellowNornStone;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.potions.PotionOfExperience;
import com.hmdzl.spspd.change.items.potions.PotionOfFrost;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.change.items.potions.PotionOfLevitation;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.items.potions.PotionOfMending;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.change.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.change.items.potions.PotionOfPurity;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.change.items.rings.RingOfElements;
import com.hmdzl.spspd.change.items.rings.RingOfEvasion;
import com.hmdzl.spspd.change.items.rings.RingOfForce;
import com.hmdzl.spspd.change.items.rings.RingOfFuror;
import com.hmdzl.spspd.change.items.rings.RingOfHaste;
import com.hmdzl.spspd.change.items.rings.RingOfMagic;
import com.hmdzl.spspd.change.items.rings.RingOfMight;
import com.hmdzl.spspd.change.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.change.items.rings.RingOfTenacity;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfLullaby;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMirrorImage;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTerror;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.wands.WandOfCharm;
import com.hmdzl.spspd.change.items.wands.WandOfBlood;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.change.items.wands.WandOfFlock;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.change.items.wands.WandOfPoison;
import com.hmdzl.spspd.change.items.wands.WandOfFreeze;
import com.hmdzl.spspd.change.items.wands.WandOfAcid;
import com.hmdzl.spspd.change.items.wands.WandOfError;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.melee.BattleAxe;
import com.hmdzl.spspd.change.items.weapon.melee.Dualknive;
import com.hmdzl.spspd.change.items.weapon.melee.MageBook;
import com.hmdzl.spspd.change.items.weapon.melee.Nunchakus;
import com.hmdzl.spspd.change.items.weapon.melee.Dagger;
import com.hmdzl.spspd.change.items.weapon.melee.Glaive;
import com.hmdzl.spspd.change.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.change.items.weapon.melee.AssassinsBlade;
import com.hmdzl.spspd.change.items.weapon.melee.Scimitar;
import com.hmdzl.spspd.change.items.weapon.melee.Handaxe;
import com.hmdzl.spspd.change.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.change.items.weapon.melee.Spear;
import com.hmdzl.spspd.change.items.weapon.melee.special.Spork;
import com.hmdzl.spspd.change.items.weapon.melee.Whip;
import com.hmdzl.spspd.change.items.weapon.melee.WarHammer;
import com.hmdzl.spspd.change.items.weapon.melee.Gsword;
import com.hmdzl.spspd.change.items.weapon.melee.Halberd;
import com.hmdzl.spspd.change.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.change.items.weapon.melee.special.TekkoKagi;
import com.hmdzl.spspd.change.items.weapon.melee.special.WraithBreath;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.PoisonDart;
import com.hmdzl.spspd.change.items.weapon.missiles.IncendiaryDart;
import com.hmdzl.spspd.change.items.weapon.missiles.Tamahawk;
import com.hmdzl.spspd.change.plants.BlandfruitBush;
import com.hmdzl.spspd.change.plants.Blindweed;
import com.hmdzl.spspd.change.plants.Dewcatcher;
import com.hmdzl.spspd.change.plants.Dreamfoil;
import com.hmdzl.spspd.change.plants.Earthroot;
import com.hmdzl.spspd.change.plants.Fadeleaf;
import com.hmdzl.spspd.change.plants.Firebloom;
import com.hmdzl.spspd.change.plants.Flytrap;
import com.hmdzl.spspd.change.plants.Icecap;
import com.hmdzl.spspd.change.plants.Phaseshift;
import com.hmdzl.spspd.change.plants.Seedpod;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.plants.Sorrowmoss;
import com.hmdzl.spspd.change.plants.Starflower;
import com.hmdzl.spspd.change.plants.Stormvine;
import com.hmdzl.spspd.change.plants.Sungrass;
import com.watabou.utils.Random;

public class Generator {

	public static enum Category {
		WEAPON(150, Weapon.class), MELEEWEAPON( 20,Weapon.class),OLDWEAPON(0,Weapon.class),RANGEWEAPON(20,Weapon.class),GUNWEAPON(0,Weapon.class),ARMOR(100, Armor.class),
		POTION(500, Potion.class), SCROLL(400, Scroll.class), WAND(40, Wand.class), RING(15, Ring.class),
		ARTIFACT(20, Artifact.class), SEED(5, Plant.Seed.class), SEED2(0,	Plant.Seed.class),SEED3(0,	Plant.Seed.class),
		FOOD(10, Food.class), GOLD(500, Gold.class), BERRY(50, Food.class), MUSHROOM(50, Food.class), BOMBS(20, Bomb.class),
		NORNSTONE(0,NornStone.class), EGGS(0, Egg.class), HIGHFOOD(0,Food.class), SUMMONED(1,Item.class), PILL(1, Pill.class),LINKDROP(0, Item.class),MUSICWEAPON(0,Weapon.class);

		public Class<?>[] classes;
		public float[] probs;

		public float prob;
		public Class<? extends Item> superClass;

		private Category(float prob, Class<? extends Item> superClass) {
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
	};

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
				0, 3, 6, 0, 0 };

		Category.POTION.classes = new Class<?>[] { PotionOfHealing.class,
				PotionOfExperience.class, PotionOfToxicGas.class,
				PotionOfParalyticGas.class, PotionOfLiquidFlame.class,
				PotionOfLevitation.class, PotionOfStrength.class,
				PotionOfMindVision.class, PotionOfPurity.class,
				PotionOfInvisibility.class, PotionOfMight.class,
				PotionOfFrost.class, PotionOfMending.class,
				PotionOfOverHealing.class, Egg.class};
		Category.POTION.probs = new float[] { 10, 4, 15, 10, 15, 10, 0, 20, 12,
				10, 0, 10, 45, 4, 10};

		Category.WAND.classes = new Class<?>[] { WandOfAcid.class,
				WandOfFreeze.class, WandOfFirebolt.class,
				WandOfLight.class, WandOfPoison.class, WandOfBlood.class,
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
				Triangolo.class, Flute.class, Wardurm.class, Trumpet.class, Harp.class,
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
				Skull.class, RiceBall.class, Wave.class, ShatteredAmmo.class, HugeShuriken.class, NormalBomb.class,
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
				Triangolo.class, Flute.class, Wardurm.class, Trumpet.class, Harp.class,
				WoodenStaff.class, Mace.class, HolyWater.class, PrayerWheel.class, StoneCross.class};
		Category.MELEEWEAPON.probs = new float[] {
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
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
				Triangolo.class, Flute.class, Wardurm.class, Trumpet.class, Harp.class};
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
				Firebloom.Seed.class, Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class, Sungrass.Seed.class,
				Earthroot.Seed.class, Fadeleaf.Seed.class, Rotberry.Seed.class, BlandfruitBush.Seed.class, Dreamfoil.Seed.class,
				Stormvine.Seed.class, /*Nut.class, Vegetable.class,Blackberry.class, Blueberry.class, Cloudberry.class,
				Moonberry.class,*/ Starflower.Seed.class, Phaseshift.Seed.class, Flytrap.Seed.class, Dewcatcher.Seed.class, Seedpod.Seed.class};
		
		Category.SEED.probs = new float[] { 12, 12, 12, 12, 12,
				                            12, 12, 0, 4, 12,
				                            12,/* 48, 36, 20, 4, 16,
				                            4,*/ 3, 3, 4, 8, 2};
		
		
		Category.SEED2.classes = new Class<?>[] { Firebloom.Seed.class,
				Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Stormvine.Seed.class, Starflower.Seed.class};
		
		Category.SEED2.probs = new float[] { 8, 4, 6, 4, 3, 1 };

		Category.SEED3.classes = new Class<?>[] {Sungrass.Seed.class, Earthroot.Seed.class, BlandfruitBush.Seed.class, Dreamfoil.Seed.class,
				Starflower.Seed.class, Dewcatcher.Seed.class, Seedpod.Seed.class};

		Category.SEED3.probs = new float[] { 8, 4, 3, 4, 3, 1 , 1 };

		
		Category.BERRY.classes = new Class<?>[] {Blackberry.class, Blueberry.class, Cloudberry.class, Moonberry.class};
		Category.BERRY.probs = new float[] {6,3,2,1};	
		
		Category.MUSHROOM.classes = new Class<?>[] {BlueMilk.class, DeathCap.class, Earthstar.class, JackOLantern.class, PixieParasol.class, GoldenJelly.class, GreenSpore.class};
		Category.MUSHROOM.probs = new float[] {4,3,3,3,3,3,2};
		
		Category.NORNSTONE.classes = new Class<?>[] {BlueNornStone.class, GreenNornStone.class, OrangeNornStone.class, PurpleNornStone.class, YellowNornStone.class};
		Category.NORNSTONE.probs = new float[] {2,2,2,2,2};

		Category.EGGS.classes = new Class<?>[] { BlueDragonEgg.class, CocoCatEgg.class, EasterEgg.class,Egg.class,
                LightDragonEgg.class, GreenDragonEgg.class, LeryFireEgg.class, RedDragonEgg.class, ScorpionEgg.class,
                ShadowDragonEgg.class, SpiderEgg.class, VelociroosterEgg.class, VioletDragonEgg.class,
				GoldDragonEgg.class, RandomEgg.class };
		Category.EGGS.probs = new float[] {1,0,1,1,1,1,1,1,1,1,1,1,1,1,1};

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
				HugeBomb.class,MiniBomb.class,
				FireBomb.class,IceBomb.class,EarthBomb.class,StormBomb.class,
				LightBomb.class,DarkBomb.class,FishingBomb.class
		};
		Category.BOMBS.probs = new float[] { 0,3,
				0,1,
				1,1,1,1,
				1,1,1};
		Category.SUMMONED.classes = new Class<?>[] {ActiveMrDestructo.class,Mobile.class,FairyCard.class

		};
		Category.SUMMONED.probs = new float[] { 1,1,1};

		Category.PILL.classes = new Class<?>[] {Hardpill.class, Magicpill.class,Musicpill.class,
				Powerpill.class, Shootpill.class, Smashpill.class
		};
		Category.PILL.probs = new float[] { 1,1,1,1,1,1};

		Category.LINKDROP.classes = new Class<?>[] {
				BuildBomb.class,DungeonBomb.class,
				HugeBomb.class,MiniBomb.class, FireBomb.class,IceBomb.class,EarthBomb.class,StormBomb.class,
				LightBomb.class,DarkBomb.class,FishingBomb.class,
				EmpBola.class ,EscapeKnive.class,PoisonDart.class,Smoke.class,IncendiaryDart.class,Tamahawk.class,
				Skull.class, RiceBall.class, Wave.class, ShatteredAmmo.class, HugeShuriken.class, NormalBomb.class,
		};
		Category.LINKDROP.probs = new float[] { 3,1,
		1,1,1,1,1,1,
		1,1,1,
		2,2,2,2,2,2,
		2,2,2,2,2,2};
	}

	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put(cat, cat.prob);
		}
	}

	public static Item random() {
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

			return ((Item) cl.newInstance()).random();

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
