/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.hmdzl.spspd.infos;

import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.BaseArmor;
import com.hmdzl.spspd.items.armor.normalarmor.BulletArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CDArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.items.armor.normalarmor.LifeArmor;
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
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.artifacts.EyeOfSkadi;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.artifacts.Pylon;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.items.food.Honey;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.WaterItem;
import com.hmdzl.spspd.items.food.completefood.AflyFood;
import com.hmdzl.spspd.items.food.completefood.Chickennugget;
import com.hmdzl.spspd.items.food.completefood.Chocolate;
import com.hmdzl.spspd.items.food.completefood.Crystalnucleus;
import com.hmdzl.spspd.items.food.completefood.Foamedbeverage;
import com.hmdzl.spspd.items.food.completefood.FoodFans;
import com.hmdzl.spspd.items.food.completefood.Frenchfries;
import com.hmdzl.spspd.items.food.completefood.Fruitsalad;
import com.hmdzl.spspd.items.food.completefood.Garbage;
import com.hmdzl.spspd.items.food.completefood.Gel;
import com.hmdzl.spspd.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.items.food.completefood.Hamburger;
import com.hmdzl.spspd.items.food.completefood.Herbmeat;
import com.hmdzl.spspd.items.food.completefood.HoneyGel;
import com.hmdzl.spspd.items.food.completefood.HoneyWater;
import com.hmdzl.spspd.items.food.completefood.Honeymeat;
import com.hmdzl.spspd.items.food.completefood.Honeyrice;
import com.hmdzl.spspd.items.food.completefood.Icecream;
import com.hmdzl.spspd.items.food.completefood.Kebab;
import com.hmdzl.spspd.items.food.completefood.Meatroll;
import com.hmdzl.spspd.items.food.completefood.NutCake;
import com.hmdzl.spspd.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.completefood.Porksoup;
import com.hmdzl.spspd.items.food.completefood.Ricefood;
import com.hmdzl.spspd.items.food.completefood.Vegetablekebab;
import com.hmdzl.spspd.items.food.completefood.Vegetableroll;
import com.hmdzl.spspd.items.food.completefood.Vegetablesoup;
import com.hmdzl.spspd.items.food.completefood.ZongZi;
import com.hmdzl.spspd.items.food.fruit.Blackberry;
import com.hmdzl.spspd.items.food.fruit.Blandfruit;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.food.fruit.Cherry;
import com.hmdzl.spspd.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.items.food.fruit.FullMoonberry;
import com.hmdzl.spspd.items.food.fruit.Moonberry;
import com.hmdzl.spspd.items.food.fruit.Strawberry;
import com.hmdzl.spspd.items.food.meatfood.ChargrilledMeat;
import com.hmdzl.spspd.items.food.meatfood.FrozenCarpaccio;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.items.food.staplefood.OverpricedRation;
import com.hmdzl.spspd.items.food.staplefood.Pasty;
import com.hmdzl.spspd.items.food.vegetable.BattleFlower;
import com.hmdzl.spspd.items.food.vegetable.DreamLeaf;
import com.hmdzl.spspd.items.food.vegetable.HealGrass;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.medicine.BlueMilk;
import com.hmdzl.spspd.items.medicine.DeathCap;
import com.hmdzl.spspd.items.medicine.Earthstar;
import com.hmdzl.spspd.items.medicine.GoldenJelly;
import com.hmdzl.spspd.items.medicine.GreenSpore;
import com.hmdzl.spspd.items.medicine.Hardpill;
import com.hmdzl.spspd.items.medicine.JackOLantern;
import com.hmdzl.spspd.items.medicine.Magicpill;
import com.hmdzl.spspd.items.medicine.Musicpill;
import com.hmdzl.spspd.items.medicine.PixieParasol;
import com.hmdzl.spspd.items.medicine.Powerpill;
import com.hmdzl.spspd.items.medicine.RealgarWine;
import com.hmdzl.spspd.items.medicine.Shootpill;
import com.hmdzl.spspd.items.medicine.Smashpill;
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
import com.hmdzl.spspd.items.wands.CannonOfMage;
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
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.guns.Sling;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.AssassinsBlade;
import com.hmdzl.spspd.items.weapon.melee.BattleAxe;
import com.hmdzl.spspd.items.weapon.melee.Club;
import com.hmdzl.spspd.items.weapon.melee.Dagger;
import com.hmdzl.spspd.items.weapon.melee.Dualknive;
import com.hmdzl.spspd.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.items.weapon.melee.Flute;
import com.hmdzl.spspd.items.weapon.melee.Glaive;
import com.hmdzl.spspd.items.weapon.melee.Gsword;
import com.hmdzl.spspd.items.weapon.melee.Halberd;
import com.hmdzl.spspd.items.weapon.melee.Handaxe;
import com.hmdzl.spspd.items.weapon.melee.Harp;
import com.hmdzl.spspd.items.weapon.melee.HolyWater;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.items.weapon.melee.Lance;
import com.hmdzl.spspd.items.weapon.melee.Mace;
import com.hmdzl.spspd.items.weapon.melee.MageBook;
import com.hmdzl.spspd.items.weapon.melee.Nunchakus;
import com.hmdzl.spspd.items.weapon.melee.PrayerWheel;
import com.hmdzl.spspd.items.weapon.melee.Rapier;
import com.hmdzl.spspd.items.weapon.melee.Scimitar;
import com.hmdzl.spspd.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.items.weapon.melee.Spear;
import com.hmdzl.spspd.items.weapon.melee.StoneCross;
import com.hmdzl.spspd.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.items.weapon.melee.Trumpet;
import com.hmdzl.spspd.items.weapon.melee.WarHammer;
import com.hmdzl.spspd.items.weapon.melee.Wardrum;
import com.hmdzl.spspd.items.weapon.melee.Whip;
import com.hmdzl.spspd.items.weapon.melee.WoodenStaff;
import com.hmdzl.spspd.items.weapon.melee.block.GoblinShield;
import com.hmdzl.spspd.items.weapon.melee.block.SpKnuckles;
import com.hmdzl.spspd.items.weapon.melee.block.TenguSword;
import com.hmdzl.spspd.items.weapon.melee.relic.AresSword;
import com.hmdzl.spspd.items.weapon.melee.relic.CromCruachAxe;
import com.hmdzl.spspd.items.weapon.melee.relic.JupitersWraith;
import com.hmdzl.spspd.items.weapon.melee.relic.LokisFlail;
import com.hmdzl.spspd.items.weapon.melee.relic.NeptunusTrident;
import com.hmdzl.spspd.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.items.weapon.melee.special.DemonBlade;
import com.hmdzl.spspd.items.weapon.melee.special.DiamondPickaxe;
import com.hmdzl.spspd.items.weapon.melee.special.DragonBoat;
import com.hmdzl.spspd.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.items.weapon.melee.special.Goei;
import com.hmdzl.spspd.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.items.weapon.melee.special.HookHam;
import com.hmdzl.spspd.items.weapon.melee.special.KeyWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.LinkSword;
import com.hmdzl.spspd.items.weapon.melee.special.Lollipop;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.items.weapon.melee.special.ShadowEater;
import com.hmdzl.spspd.items.weapon.melee.special.Spork;
import com.hmdzl.spspd.items.weapon.melee.special.TekkoKagi;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Tree;
import com.hmdzl.spspd.items.weapon.melee.special.WraithBreath;
import com.hmdzl.spspd.items.weapon.melee.zero.EmptyPotion;
import com.hmdzl.spspd.items.weapon.melee.zero.WoodenHammer;
import com.hmdzl.spspd.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.MiniMoai;
import com.hmdzl.spspd.items.weapon.missiles.TaurcenBow;

import java.util.Collection;
import java.util.LinkedHashMap;

public enum NewCatalog {
	
	WEAPONS,
	ARMOR,
	WANDS,
	RINGS,
	ARTIFACTS,
	FOODS,
	PILLS;
	
	private LinkedHashMap<Class<? extends Item>,Boolean> seen = new LinkedHashMap<>();
	
	public Collection<Class<? extends Item>> items(){
		return seen.keySet();
	}

	static {
		WEAPONS.seen.put( ShortSword.class , true);
		WEAPONS.seen.put( Handaxe.class , true);
		WEAPONS.seen.put( Scimitar.class , true);
		WEAPONS.seen.put( BattleAxe.class , true);
		WEAPONS.seen.put( Gsword.class , true);
		WEAPONS.seen.put( WoodenHammer.class , true);
		WEAPONS.seen.put( MageBook.class , true);
		WEAPONS.seen.put( FightGloves.class , true);
		WEAPONS.seen.put( Nunchakus.class , true);
		WEAPONS.seen.put( Club.class , true);
		WEAPONS.seen.put( WarHammer.class , true);
		WEAPONS.seen.put( Dagger.class , true);
		WEAPONS.seen.put( Dualknive.class , true);
		WEAPONS.seen.put( Rapier.class , true);
		WEAPONS.seen.put( AssassinsBlade.class , true);
		WEAPONS.seen.put( Lance.class , true);
		WEAPONS.seen.put( Knuckles.class , true);
		WEAPONS.seen.put( Spear.class , true);
		WEAPONS.seen.put( Whip.class , true);
		WEAPONS.seen.put( Glaive.class , true);
		WEAPONS.seen.put( Halberd.class , true);
		WEAPONS.seen.put( EmptyPotion.class , true);
		WEAPONS.seen.put( Triangolo.class , true);
		WEAPONS.seen.put( Flute.class , true);
		WEAPONS.seen.put( Wardrum.class , true);
		WEAPONS.seen.put( Trumpet.class , true);
		WEAPONS.seen.put( Harp.class , true);
		WEAPONS.seen.put( Sling.class , true);
		WEAPONS.seen.put( GunA.class , true);
		WEAPONS.seen.put( GunB.class , true);
		WEAPONS.seen.put( GunC.class , true);
		WEAPONS.seen.put( GunD.class , true);
		WEAPONS.seen.put( GunE.class , true);
		WEAPONS.seen.put( WoodenStaff.class , true);
		WEAPONS.seen.put( Mace.class , true);
		WEAPONS.seen.put( HolyWater.class , true);
		WEAPONS.seen.put( PrayerWheel.class , true);
		WEAPONS.seen.put( StoneCross.class , true);
		WEAPONS.seen.put( AresSword.class , true);
		WEAPONS.seen.put( CromCruachAxe.class , true);
		WEAPONS.seen.put( JupitersWraith.class , true);
		WEAPONS.seen.put( LokisFlail.class , true);
		WEAPONS.seen.put( NeptunusTrident.class , true);
		WEAPONS.seen.put( DemonBlade.class , true);
		WEAPONS.seen.put( DiamondPickaxe.class , true);
		WEAPONS.seen.put( LinkSword.class , true);
		WEAPONS.seen.put( Boomerang.class , true);
		WEAPONS.seen.put( ManyKnive.class , true);
		WEAPONS.seen.put( TaurcenBow.class , true);
		WEAPONS.seen.put( TekkoKagi.class , true);
		WEAPONS.seen.put( WraithBreath.class , true);
		WEAPONS.seen.put( Spork.class , true);
		WEAPONS.seen.put( Goei.class , true);
		WEAPONS.seen.put( Handcannon.class , true);
		WEAPONS.seen.put( RunicBlade.class , true);
		WEAPONS.seen.put( ErrorW.class , true);
		WEAPONS.seen.put( ShadowEater.class , true);
		WEAPONS.seen.put( Brick.class , true);
		WEAPONS.seen.put( DragonBoat.class , true);
		WEAPONS.seen.put( FireCracker.class , true);
		WEAPONS.seen.put( HookHam.class , true);
		WEAPONS.seen.put( KeyWeapon.class , true);
		WEAPONS.seen.put( Lollipop.class , true);
		WEAPONS.seen.put( Pumpkin.class , true);
		WEAPONS.seen.put( SJRBMusic.class , true);
		WEAPONS.seen.put( TestWeapon.class , true);
		WEAPONS.seen.put( Tree.class , true);
		WEAPONS.seen.put( ToyGun.class , true);
		WEAPONS.seen.put( MiniMoai.class , true);
		WEAPONS.seen.put( GoblinShield.class , true);
		WEAPONS.seen.put( SpKnuckles.class , true);
		WEAPONS.seen.put( TenguSword.class , true);

		ARMOR.seen.put( BaseArmor.class, true);
		ARMOR.seen.put( VestArmor.class, true);
		ARMOR.seen.put( ClothArmor.class, true);
		ARMOR.seen.put( WoodenArmor.class, true);
		ARMOR.seen.put( RubberArmor.class, true);
		ARMOR.seen.put( LeatherArmor.class, true);
		ARMOR.seen.put( CeramicsArmor.class, true);
		ARMOR.seen.put( CDArmor.class, true);
		ARMOR.seen.put( DiscArmor.class, true);
		ARMOR.seen.put( StoneArmor.class, true);
		ARMOR.seen.put( StyrofoamArmor.class, true);
		ARMOR.seen.put( MailArmor.class, true);
		ARMOR.seen.put( MultiplelayerArmor.class, true);
		ARMOR.seen.put( ProtectiveclothingArmor.class, true);
		ARMOR.seen.put( ScaleArmor.class, true);
		ARMOR.seen.put( BulletArmor.class, true);
		ARMOR.seen.put( PhantomArmor.class, true);
		ARMOR.seen.put( PlateArmor.class, true);
		ARMOR.seen.put( MachineArmor.class, true);
		ARMOR.seen.put( LifeArmor.class, true);
		ARMOR.seen.put( ErrorArmor.class, true);

		WANDS.seen.put( WandOfMagicMissile.class, true);
		WANDS.seen.put( WandOfDisintegration.class,           true);
		WANDS.seen.put( WandOfLightning.class,              true);
		WANDS.seen.put( WandOfTCloud.class,         true);
		WANDS.seen.put( WandOfFirebolt.class,              true);
		WANDS.seen.put( WandOfMeteorite.class,                  true);
		WANDS.seen.put( WandOfFreeze.class,              true);
		WANDS.seen.put( WandOfFlow.class,          true);
		WANDS.seen.put( WandOfAcid.class,                  true);
		WANDS.seen.put( WandOfSwamp.class,         true);
		WANDS.seen.put( WandOfLight.class,              true);
		WANDS.seen.put( WandOfCharm.class,            true);
		WANDS.seen.put( WandOfFlock.class,             true);
		WANDS.seen.put( WandOfBlood.class,               true);
		WANDS.seen.put( WandOfError.class,               true);
		WANDS.seen.put( CannonOfMage.class,               true);
	
		RINGS.seen.put( RingOfAccuracy.class, true);
		RINGS.seen.put( RingOfEnergy.class,   true);
		RINGS.seen.put( RingOfElements.class,               true);
		RINGS.seen.put( RingOfEvasion.class,                true);
		RINGS.seen.put( RingOfForce.class,                  true);
		RINGS.seen.put( RingOfFuror.class,                  true);
		RINGS.seen.put( RingOfHaste.class,                  true);
		RINGS.seen.put( RingOfMight.class,                  true);
		RINGS.seen.put( RingOfSharpshooting.class,          true);
		RINGS.seen.put( RingOfTenacity.class,               true);
		RINGS.seen.put( RingOfMagic.class,                 true);

		ARTIFACTS.seen.put( AlchemistsToolkit.class,      true);
		ARTIFACTS.seen.put( CapeOfThorns.class,             true);
		ARTIFACTS.seen.put( ChaliceOfBlood.class,           true);
		ARTIFACTS.seen.put( CloakOfShadows.class,          true);
		ARTIFACTS.seen.put( DriedRose.class,                true);
		ARTIFACTS.seen.put( EtherealChains.class,          true);
		ARTIFACTS.seen.put( HornOfPlenty.class,            true);
		ARTIFACTS.seen.put( GlassTotem.class,             true);
		ARTIFACTS.seen.put( MasterThievesArmband.class,     true);
		ARTIFACTS.seen.put( SandalsOfNature.class,          true);
		ARTIFACTS.seen.put( TalismanOfForesight.class,      true);
		ARTIFACTS.seen.put( TimekeepersHourglass.class,     true);
		ARTIFACTS.seen.put( UnstableSpellbook.class,        true);
		ARTIFACTS.seen.put( AlienBag.class, true);
		ARTIFACTS.seen.put( EyeOfSkadi.class, true);
		ARTIFACTS.seen.put( RobotDMT.class, true);
		ARTIFACTS.seen.put( Pylon.class, true);

		FOODS.seen.put( Honey.class, true);
		FOODS.seen.put( Nut.class, true);
		FOODS.seen.put( WaterItem.class, true);
		FOODS.seen.put( OverpricedRation.class, true);
		FOODS.seen.put( NormalRation.class, true);
		FOODS.seen.put( Pasty.class, true);
		FOODS.seen.put( BattleFlower.class, true);
		FOODS.seen.put( DreamLeaf.class, true);
		FOODS.seen.put( HealGrass.class, true);
		FOODS.seen.put( NutVegetable.class, true);
		FOODS.seen.put( Blackberry.class, true);
		FOODS.seen.put( Blueberry.class, true);
		FOODS.seen.put( Cloudberry.class, true);
		FOODS.seen.put( Moonberry.class, true);
		FOODS.seen.put( FullMoonberry.class, true);
		FOODS.seen.put( Blandfruit.class, true);
		FOODS.seen.put( Strawberry.class, true);
		FOODS.seen.put( Cherry.class, true);
		FOODS.seen.put( Meat.class, true);
		FOODS.seen.put( MysteryMeat.class, true);
		FOODS.seen.put( ChargrilledMeat.class, true);
		FOODS.seen.put( FrozenCarpaccio.class, true);

		FOODS.seen.put( AflyFood.class, true);
		FOODS.seen.put( Chickennugget.class, true);
		FOODS.seen.put( Chocolate.class, true);
		FOODS.seen.put( Crystalnucleus.class, true);
		FOODS.seen.put( Foamedbeverage.class, true);
		FOODS.seen.put( FoodFans.class, true);
		FOODS.seen.put( Frenchfries.class, true);
		FOODS.seen.put( Fruitsalad.class, true);
		FOODS.seen.put( Garbage.class, true);
		FOODS.seen.put( Gel.class, true);
		FOODS.seen.put( GoldenNut.class, true);
		FOODS.seen.put( Hamburger.class, true);
		FOODS.seen.put( Herbmeat.class, true);
		FOODS.seen.put( HoneyGel.class, true);
		FOODS.seen.put( Honeymeat.class, true);
		FOODS.seen.put( Honeyrice.class, true);
		FOODS.seen.put( HoneyWater.class, true);
		FOODS.seen.put( Icecream.class, true);
		FOODS.seen.put( Kebab.class, true);
		FOODS.seen.put( Meatroll.class, true);
		FOODS.seen.put( NutCake.class, true);
		FOODS.seen.put( PerfectFood.class, true);
		FOODS.seen.put( PetFood.class, true);
		FOODS.seen.put( Porksoup.class, true);
		FOODS.seen.put( Ricefood.class, true);
		FOODS.seen.put( Vegetablekebab.class, true);
		FOODS.seen.put( Vegetableroll.class, true);
		FOODS.seen.put( Vegetablesoup.class, true);
		FOODS.seen.put( ZongZi.class, true);

		PILLS.seen.put( Powerpill.class, true);
		PILLS.seen.put( Magicpill.class, true);
		PILLS.seen.put( Shootpill.class, true);
		PILLS.seen.put( Smashpill.class, true);
		PILLS.seen.put( Musicpill.class, true);
		PILLS.seen.put( Hardpill.class, true);
		PILLS.seen.put( BlueMilk.class, true);
		PILLS.seen.put( DeathCap.class, true);
		PILLS.seen.put( Earthstar.class, true);
		PILLS.seen.put( GoldenJelly.class, true);
		PILLS.seen.put( GreenSpore.class, true);
		PILLS.seen.put( JackOLantern.class, true);
		PILLS.seen.put( PixieParasol.class, true);
		PILLS.seen.put( RealgarWine.class, true);

	}

}