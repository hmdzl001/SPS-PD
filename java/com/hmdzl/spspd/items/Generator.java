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
import com.hmdzl.spspd.items.armor.normalarmor.BeginnerRobe;
import com.hmdzl.spspd.items.armor.normalarmor.BulletArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CDArmor;
import com.hmdzl.spspd.items.armor.normalarmor.CeramicsArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MachineArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MailArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MasterRobe;
import com.hmdzl.spspd.items.armor.normalarmor.MultiplelayerArmor;
import com.hmdzl.spspd.items.armor.normalarmor.PhantomArmor;
import com.hmdzl.spspd.items.armor.normalarmor.PlateArmor;
import com.hmdzl.spspd.items.armor.normalarmor.PracticeRobe;
import com.hmdzl.spspd.items.armor.normalarmor.ProtectiveclothingArmor;
import com.hmdzl.spspd.items.armor.normalarmor.RegularRobe;
import com.hmdzl.spspd.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ScaleArmor;
import com.hmdzl.spspd.items.armor.normalarmor.SeniorRobe;
import com.hmdzl.spspd.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.items.armor.normalarmor.SupermeRobe;
import com.hmdzl.spspd.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.items.artifacts.AlchemistsToolkit;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.artifacts.ChaliceOfBlood;
import com.hmdzl.spspd.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.items.artifacts.ClownDeck;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.artifacts.EyeOfSkadi;
import com.hmdzl.spspd.items.artifacts.FlyChains;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.items.artifacts.TalismanOfForesight;
import com.hmdzl.spspd.items.artifacts.TimeOclock;
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
import com.hmdzl.spspd.items.eggs.RedDragonEgg;
import com.hmdzl.spspd.items.eggs.ScorpionEgg;
import com.hmdzl.spspd.items.eggs.ShadowDragonEgg;
import com.hmdzl.spspd.items.eggs.VioletDragonEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomAtkEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomColEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomDefEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomEasterEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg1;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg10;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg11;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg12;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg2;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg3;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg4;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg5;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg6;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg7;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg8;
import com.hmdzl.spspd.items.eggs.randomone.RandomEgg9;
import com.hmdzl.spspd.items.food.Food;
import com.hmdzl.spspd.items.food.completefood.Chickennugget;
import com.hmdzl.spspd.items.food.completefood.Chocolate;
import com.hmdzl.spspd.items.food.completefood.FoodFans;
import com.hmdzl.spspd.items.food.completefood.Frenchfries;
import com.hmdzl.spspd.items.food.completefood.FruitCandy;
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
import com.hmdzl.spspd.items.food.completefood.MixPizza;
import com.hmdzl.spspd.items.food.completefood.NutCookie;
import com.hmdzl.spspd.items.food.completefood.PerfectFood;
import com.hmdzl.spspd.items.food.completefood.Porksoup;
import com.hmdzl.spspd.items.food.completefood.RiceGruel;
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
import com.hmdzl.spspd.items.rings.RingOfKnowledge;
import com.hmdzl.spspd.items.rings.RingOfMagic;
import com.hmdzl.spspd.items.rings.RingOfMight;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.rings.RingOfTenacity;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfDummy;
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
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.scrolls.ScrollOfTerror;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.sellitem.SellMushroom;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfBlood;
import com.hmdzl.spspd.items.wands.WandOfCharm;
import com.hmdzl.spspd.items.wands.WandOfDarkElement;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.wands.WandOfEarthElement;
import com.hmdzl.spspd.items.wands.WandOfEnergyElement;
import com.hmdzl.spspd.items.wands.WandOfError;
import com.hmdzl.spspd.items.wands.WandOfFireElement;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.wands.WandOfIceElement;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.wands.WandOfLightElement;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.items.wands.WandOfShockElement;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
import com.hmdzl.spspd.items.wands.WandOfTCloud;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
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
import com.hmdzl.spspd.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.items.weapon.melee.special.HookHam;
import com.hmdzl.spspd.items.weapon.melee.special.KeyWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Lollipop;
import com.hmdzl.spspd.items.weapon.melee.special.MeleePan;
import com.hmdzl.spspd.items.weapon.melee.special.PaperFan;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.missiles.arrows.Arrows;
import com.hmdzl.spspd.items.weapon.missiles.arrows.BlindFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.CharmFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.FireFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.GlassFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.HealFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.IceFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.MagicHand;
import com.hmdzl.spspd.items.weapon.missiles.arrows.NutFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RiceBall;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RocketMissile;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RootFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.ShockFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.SmokeFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.ToxicFruit;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Brick;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.DragonBoat;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.HugeShuriken;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Javelin;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Kunai;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MiniMoai;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.SmallChakram;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Tamahawk;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Tree;
import com.hmdzl.spspd.items.weapon.missiles.throwing.EmpBola;
import com.hmdzl.spspd.items.weapon.missiles.throwing.EscapeKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.PoisonDart;
import com.hmdzl.spspd.items.weapon.missiles.throwing.ShitBall;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Skull;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Wave;
import com.hmdzl.spspd.items.weapon.ranges.AlloyBowN;
import com.hmdzl.spspd.items.weapon.ranges.AlloyBowR;
import com.hmdzl.spspd.items.weapon.ranges.AlloyBowS;
import com.hmdzl.spspd.items.weapon.ranges.MetalBowN;
import com.hmdzl.spspd.items.weapon.ranges.MetalBowR;
import com.hmdzl.spspd.items.weapon.ranges.MetalBowS;
import com.hmdzl.spspd.items.weapon.ranges.PVCBowN;
import com.hmdzl.spspd.items.weapon.ranges.PVCBowR;
import com.hmdzl.spspd.items.weapon.ranges.PVCBowS;
import com.hmdzl.spspd.items.weapon.ranges.StoneBowN;
import com.hmdzl.spspd.items.weapon.ranges.StoneBowR;
import com.hmdzl.spspd.items.weapon.ranges.StoneBowS;
import com.hmdzl.spspd.items.weapon.ranges.WoodenBowN;
import com.hmdzl.spspd.items.weapon.ranges.WoodenBowR;
import com.hmdzl.spspd.items.weapon.ranges.WoodenBowS;
import com.hmdzl.spspd.items.weapon.rockcode.Alink;
import com.hmdzl.spspd.items.weapon.rockcode.Bmech;
import com.hmdzl.spspd.items.weapon.rockcode.Dpotion;
import com.hmdzl.spspd.items.weapon.rockcode.Gleaf;
import com.hmdzl.spspd.items.weapon.rockcode.Ichain;
import com.hmdzl.spspd.items.weapon.rockcode.Lbox;
import com.hmdzl.spspd.items.weapon.rockcode.Mlaser;
import com.hmdzl.spspd.items.weapon.rockcode.Nshuriken;
import com.hmdzl.spspd.items.weapon.rockcode.Obubble;
import com.hmdzl.spspd.items.weapon.rockcode.RockCode;
import com.hmdzl.spspd.items.weapon.rockcode.Sweb;
import com.hmdzl.spspd.items.weapon.rockcode.Trush;
import com.hmdzl.spspd.items.weapon.rockcode.Zshield;
import com.hmdzl.spspd.plants.BlandfruitBush;
import com.hmdzl.spspd.plants.Blindweed;
import com.hmdzl.spspd.plants.Dewcatcher;
import com.hmdzl.spspd.plants.Dreamfoil;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Fadeleaf;
import com.hmdzl.spspd.plants.Firebloom;
import com.hmdzl.spspd.plants.Freshberry;
import com.hmdzl.spspd.plants.Icecap;
import com.hmdzl.spspd.plants.NutPlant;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.ReNepenth;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.plants.SiOtwoFlower;
import com.hmdzl.spspd.plants.Sorrowmoss;
import com.hmdzl.spspd.plants.StarEater;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.plants.Stormvine;
import com.hmdzl.spspd.plants.Sungrass;
import com.watabou.utils.Random;

import java.util.HashMap;

public class Generator {

	public enum Category {
		MELEEWEAPON( 130,0,Weapon.class),
		OLDWEAPON(0,0,Weapon.class),
		RANGEWEAPON(20,0,Weapon.class),
		SHOOTWEAPON(40,0,Weapon.class),
		GUNWEAPON(0,0,Weapon.class),
		ARMOR(100, 0,Armor.class),
		WEAPON( 0,0,Weapon.class),
		POTION(500,0, Potion.class),
		SCROLL(500,0, Scroll.class),
		WAND(40, 0,Wand.class),
		RING(15,0, Ring.class),
		ARTIFACT(20,0, Artifact.class),
		SEED(5, 0,Plant.Seed.class),
		SEED2(0,0,	Plant.Seed.class),
		SEED3(0,0,	Plant.Seed.class),
		SEED4(0,0,	Plant.Seed.class),
		FOOD(10, 0,Food.class),
		GOLD(300,0, Gold.class),
		BERRY(50, 0,Food.class),
		MUSHROOM(5,0, Pill.class),
		BOMBS(20, 0,Bomb.class),
		NORNSTONE(0,0,NornStone.class),
		EGGS(0, 0,Egg.class),
		HIGHFOOD(0,0,Food.class),
		SUMMONED(1,0,Item.class),
		PILL(5, 0,Pill.class),
		LINKDROP(0, 0,Item.class),
		MUSICWEAPON(0,0,Weapon.class),
		SHOES(0,0,Item.class),
		DEW(0,0,Item.class),
		BASEPET(0,0,Egg.class),
		EASTERWEAPON(0,0,Item.class),
		ROCKCODE(0,0,RockCode.class),
		ARROWS(0,0,Arrows.class);

		public Class<?>[] classes;
		public float[] probs;

		public float prob;
		public float prob2;
		public Class<? extends Item> superClass;

		Category(float prob, float prob2, Class<? extends Item> superClass) {
			this.prob = prob;
			this.prob2 = prob2;
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
				ScrollOfMirrorImage.class, ScrollOfRegrowth.class, ScrollOfDummy.class};
		Category.SCROLL.probs = new float[] { 30, 10, 15, 3, 10, 20, 10, 8, 8,
				3, 3, 6, 6, 6 };

		Category.POTION.classes = new Class<?>[] { PotionOfHealing.class,
				PotionOfExperience.class, PotionOfToxicGas.class,
				PotionOfParalyticGas.class, PotionOfLiquidFlame.class,
				PotionOfLevitation.class, PotionOfStrength.class,
				PotionOfMindVision.class, PotionOfPurity.class,
				PotionOfInvisibility.class, PotionOfMight.class,
				PotionOfFrost.class, PotionOfMending.class,
				PotionOfOverHealing.class, PotionOfShield.class,PotionOfMixing.class};
		Category.POTION.probs = new float[] { 10, 5, 15, 10, 15, 10, 4, 20, 12,
				10, 4, 10, 15, 4, 5, 0};

		Category.WAND.classes = new Class<?>[] { WandOfAcid.class,
				WandOfFreeze.class, WandOfFirebolt.class,
				WandOfLight.class, WandOfSwamp.class, WandOfBlood.class,
				WandOfLightning.class, WandOfCharm.class,
				WandOfFlow.class, WandOfFlock.class,
				WandOfMagicMissile.class, WandOfDisintegration.class,
				WandOfMeteorite.class, WandOfError.class, WandOfTCloud.class,
				WandOfFireElement.class,	WandOfIceElement.class,
				WandOfShockElement.class,	WandOfEarthElement.class,
				WandOfLightElement.class,	WandOfDarkElement.class,
				WandOfEnergyElement.class,
		};
		Category.WAND.probs = new float[] { 5, 5, 5, 5, 5, 5, 5, 5, 5,
				5, 5, 5, 5, 0, 5,
		         5,5,5,5,5,5,5};

		Category.RANGEWEAPON.classes = new Class<?>[] {
				EmpBola.class ,EscapeKnive.class,RocketMissile.class, Skull.class,  Wave.class,
				 ShitBall.class,MagicHand.class};
		Category.RANGEWEAPON.probs = new float[] {
				1,1,1,1,1,
				1,1,
		};

		Category.WEAPON.classes = new Class<?>[] {
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Club.class,
				Gsword.class, Halberd.class, WarHammer.class, Lance.class,
				Triangolo.class, Flute.class, Wardrum.class, Trumpet.class, Harp.class,
				WoodenStaff.class, Mace.class, HolyWater.class, PrayerWheel.class, StoneCross.class,
				TrickSand.class, MirrorDoll.class, WindBottle.class, HandLight.class, CurseBox.class,
				Kunai.class, SmallChakram.class,Javelin.class,HugeShuriken.class, Tamahawk.class,
				WoodenBowN.class, WoodenBowS.class, WoodenBowR.class, GunA.class,
				StoneBowN.class, StoneBowS.class, StoneBowR.class, GunB.class,
				MetalBowN.class, MetalBowS.class, MetalBowR.class, GunC.class,
				AlloyBowN.class, AlloyBowS.class, AlloyBowR.class, GunD.class,
				PVCBowN.class, PVCBowS.class, PVCBowR.class, GunE.class

		};
		Category.WEAPON.probs = new float[] {
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,

		};

		Category.MELEEWEAPON.classes = new Class<?>[] {
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Club.class,
				Gsword.class, Halberd.class, WarHammer.class, Lance.class,
				Triangolo.class, Flute.class, Wardrum.class, Trumpet.class, Harp.class,
				WoodenStaff.class, Mace.class, HolyWater.class, PrayerWheel.class, StoneCross.class,
				TrickSand.class, MirrorDoll.class, WindBottle.class, HandLight.class, CurseBox.class,
		         Kunai.class, SmallChakram.class,Javelin.class,HugeShuriken.class, Tamahawk.class};
		Category.MELEEWEAPON.probs = new float[] {
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1,
				1,1,1,1,1
		};
		
		Category.OLDWEAPON.classes = new Class<?>[] {
				Dagger.class, Knuckles.class,  ShortSword.class, MageBook.class,
				Handaxe.class, Spear.class, Dualknive.class, FightGloves.class,
				Nunchakus.class, Scimitar.class,Whip.class, Rapier.class,
				AssassinsBlade.class,BattleAxe.class,Glaive.class,Club.class,
				Gsword.class, Halberd.class, WarHammer.class, Lance.class};
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
				ClothArmor.class, WoodenArmor.class, VestArmor.class, BeginnerRobe.class,
				LeatherArmor.class, CeramicsArmor.class, RubberArmor.class, PracticeRobe.class,
				DiscArmor.class, StoneArmor.class, CDArmor.class, RegularRobe.class,
				MailArmor.class, MultiplelayerArmor.class, StyrofoamArmor.class, SeniorRobe.class,
				ScaleArmor.class, BulletArmor.class, ProtectiveclothingArmor.class, MasterRobe.class,
				PlateArmor.class, MachineArmor.class, PhantomArmor.class, SupermeRobe.class};
		Category.ARMOR.probs = new float[] {
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1
		};

		Category.SHOOTWEAPON.classes = new Class<?>[] {
				WoodenBowN.class, WoodenBowS.class, WoodenBowR.class, GunA.class,
				StoneBowN.class, StoneBowS.class, StoneBowR.class, GunB.class,
				MetalBowN.class, MetalBowS.class, MetalBowR.class, GunC.class,
				AlloyBowN.class, AlloyBowS.class, AlloyBowR.class, GunD.class,
				PVCBowN.class, PVCBowS.class, PVCBowR.class, GunE.class};
		Category.SHOOTWEAPON.probs = new float[] {
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
				1, 1, 1,1,
		};

		Category.ARROWS.classes = new Class<?>[] {
				BlindFruit.class, CharmFruit.class, CharmFruit.class,
				FireFruit.class, GlassFruit.class, HealFruit.class,
				IceFruit.class, MagicHand.class, NutFruit.class,
				RocketMissile.class, RootFruit.class, ShockFruit.class,
				SmokeFruit.class, ToxicFruit.class, RiceBall.class};
		Category.ARROWS.probs = new float[] {
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
				1, 1, 1,
		};


		Category.FOOD.classes = new Class<?>[] { NormalRation.class, Pasty.class, OverpricedRation.class};
		Category.FOOD.probs = new float[] { 8, 2, 5 };

		Category.RING.classes = new Class<?>[] { RingOfAccuracy.class,
				RingOfEvasion.class, RingOfElements.class, RingOfForce.class,
				RingOfFuror.class, RingOfHaste.class, RingOfMagic.class,
				RingOfMight.class, RingOfSharpshooting.class,
				RingOfTenacity.class, RingOfEnergy.class, RingOfKnowledge.class };
		Category.RING.probs = new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1 };

		Category.ARTIFACT.classes = new Class<?>[] { CapeOfThorns.class,
				ChaliceOfBlood.class, CloakOfShadows.class, HornOfPlenty.class,
				MasterThievesArmband.class, SandalsOfNature.class,
				TalismanOfForesight.class, TimekeepersHourglass.class,
				UnstableSpellbook.class, AlchemistsToolkit.class, RobotDMT.class,
				EyeOfSkadi.class, EtherealChains.class,
				DriedRose.class, GlassTotem.class, AlienBag.class, ClownDeck.class,
				FlyChains.class, TimeOclock.class
		};
		Category.ARTIFACT.probs =  new float[]{  1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,0, 0};

		Category.SEED.classes = new Class<?>[] { 
				Firebloom.Seed.class, Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Sungrass.Seed.class, Earthroot.Seed.class, Fadeleaf.Seed.class, Rotberry.Seed.class,
				BlandfruitBush.Seed.class, Dreamfoil.Seed.class, Stormvine.Seed.class, NutPlant.Seed.class,
				Starflower.Seed.class, ReNepenth.Seed.class, StarEater.Seed.class, Dewcatcher.Seed.class,
				Seedpod.Seed.class,Freshberry.Seed.class,SiOtwoFlower.Seed.class};
		
		Category.SEED.probs = new float[] { 12, 12, 12, 12,
				                             12, 12, 12, 0,
				                              4, 12, 12, 12,
				                               3, 3, 4, 8,
				                              2,4,3};
		
		
		Category.SEED2.classes = new Class<?>[] { Firebloom.Seed.class,
				Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Stormvine.Seed.class, Starflower.Seed.class};
		
		Category.SEED2.probs = new float[] { 8, 4, 6, 4, 3, 1 };

		Category.SEED3.classes = new Class<?>[] {Sungrass.Seed.class, Earthroot.Seed.class, BlandfruitBush.Seed.class, Dreamfoil.Seed.class,
				Starflower.Seed.class, Dewcatcher.Seed.class, Seedpod.Seed.class ,SiOtwoFlower.Seed.class};

		Category.SEED3.probs = new float[] { 8, 4, 2, 4, 3, 1 ,1,1 };

		Category.SEED4.classes = new Class<?>[] {Sungrass.Seed.class, StarEater.Seed.class, Dreamfoil.Seed.class,
				Starflower.Seed.class, ReNepenth.Seed.class, NutPlant.Seed.class,BlandfruitBush.Seed.class , Seedpod.Seed.class,
				Freshberry.Seed.class,SiOtwoFlower.Seed.class};

		Category.SEED4.probs = new float[] { 4,  1, 4,
				2, 1 , 3,1,1,
				2,1 };
		
		
		Category.BERRY.classes = new Class<?>[] {Blackberry.class, Blueberry.class, Cloudberry.class, Moonberry.class};
		Category.BERRY.probs = new float[] {6,2,2,2};
		
		Category.MUSHROOM.classes = new Class<?>[] {BlueMilk.class, DeathCap.class, Earthstar.class, JackOLantern.class, PixieParasol.class, GoldenJelly.class, GreenSpore.class,SellMushroom.class};
		Category.MUSHROOM.probs = new float[] {4,3,3,3,3,3,2,1};
		
		Category.NORNSTONE.classes = new Class<?>[] {BlueNornStone.class, GreenNornStone.class, OrangeNornStone.class, PurpleNornStone.class, YellowNornStone.class};
		Category.NORNSTONE.probs = new float[] {3,3,3,3,3};

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
				Chocolate.class, FoodFans.class,Frenchfries.class,
				FruitCandy.class, NutCookie.class,RiceGruel.class,MixPizza.class};
		Category.HIGHFOOD.probs = new float[] { 1,1,1,1,
		1,1,1,1,
		1,1,1,1,
		1,1,1,
		1,1,1,1,1,
		1,1,1,
		1,1,1,1};
		Category.BOMBS.classes = new Class<?>[] { BuildBomb.class,DungeonBomb.class,
				HugeBomb.class,RocketMissile.class,
				FireBomb.class,IceBomb.class,EarthBomb.class,StormBomb.class,
				LightBomb.class,DarkBomb.class,FishingBomb.class
		};
		Category.BOMBS.probs = new float[] { 0,3,
				0,1,
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
				LightBomb.class,DarkBomb.class,FishingBomb.class,RocketMissile.class,
				EmpBola.class ,EscapeKnive.class,PoisonDart.class,
				Skull.class, Wave.class, ShitBall.class
		};
		Category.LINKDROP.probs = new float[] { 3,1,
		1,1,1,1,1,
		1,1,1,1,
		2,2,2,
		2,2,2};
	
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

		Category.EASTERWEAPON.classes = new Class<?>[] {Pumpkin.class,Tree.class,MiniMoai.class,TestWeapon.class,ToyGun.class,
				HookHam.class,Brick.class,Lollipop.class,FireCracker.class,SJRBMusic.class,RocketMissile.class,
				KeyWeapon.class,DragonBoat.class,PaperFan.class, MeleePan.class};
		Category.EASTERWEAPON.probs = new float[] {1,1,1,1,1,
				1,1,1,1,1,1,
				1,1,1,1};

		Category.ROCKCODE.classes = new Class<?>[] {Alink.class,Bmech.class,Dpotion.class,Gleaf.class,Ichain.class,
				Lbox.class,Mlaser.class,Nshuriken.class,Obubble.class,Sweb.class,
				Trush.class,Zshield.class};
		Category.ROCKCODE.probs = new float[] {1,1,1,1,1,
				1,1,1,1,1,
				1,1};
		
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
			case MELEEWEAPON:
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
				+ Dungeon.LimitedDrops.strengthPotions.count;

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
				+ Dungeon.LimitedDrops.strengthPotions.count;

		return randomWeapon(curStr);
	}

	public static Weapon randomWeapon(int targetStr) {

		try {
			Category cat = Category.MELEEWEAPON;

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
