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
import com.hmdzl.spspd.items.DewVial;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.Elevator;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KnowledgeBook;
import com.hmdzl.spspd.items.Palantir;
import com.hmdzl.spspd.items.PocketBall;
import com.hmdzl.spspd.items.PowerHand;
import com.hmdzl.spspd.items.SaveYourLife;
import com.hmdzl.spspd.items.SkillBook;
import com.hmdzl.spspd.items.SoulCollect;
import com.hmdzl.spspd.items.StrBottle;
import com.hmdzl.spspd.items.Stylus;
import com.hmdzl.spspd.items.TomeOfMastery;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.TransmutationBall;
import com.hmdzl.spspd.items.TriForce;
import com.hmdzl.spspd.items.UnBlessAnkh;
import com.hmdzl.spspd.items.Weightstone;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.armor.normalarmor.BaseArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.items.armor.normalarmor.StyrofoamArmor;
import com.hmdzl.spspd.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.items.armor.specialarmor.LifeArmor;
import com.hmdzl.spspd.items.armor.specialarmor.PerformerArmor;
import com.hmdzl.spspd.items.armor.specialarmor.TestArmor;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.Artifact;
import com.hmdzl.spspd.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.artifacts.Pylon;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.items.bags.ArrowCollecter;
import com.hmdzl.spspd.items.bags.KeyRing;
import com.hmdzl.spspd.items.bags.PotionBandolier;
import com.hmdzl.spspd.items.bags.ScrollHolder;
import com.hmdzl.spspd.items.bags.SeedPouch;
import com.hmdzl.spspd.items.bags.ShoppingCart;
import com.hmdzl.spspd.items.bags.WandHolster;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.bombs.FireBomb;
import com.hmdzl.spspd.items.bombs.IceBomb;
import com.hmdzl.spspd.items.bombs.StormBomb;
import com.hmdzl.spspd.items.challengelists.CaveChallenge;
import com.hmdzl.spspd.items.challengelists.CityChallenge;
import com.hmdzl.spspd.items.challengelists.CourageChallenge;
import com.hmdzl.spspd.items.challengelists.IceChallenge;
import com.hmdzl.spspd.items.challengelists.PowerChallenge;
import com.hmdzl.spspd.items.challengelists.PrisonChallenge;
import com.hmdzl.spspd.items.challengelists.SewerChallenge;
import com.hmdzl.spspd.items.challengelists.WisdomChallenge;
import com.hmdzl.spspd.items.eggs.AflyEgg;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.eggs.GoldDragonEgg;
import com.hmdzl.spspd.items.eggs.RandomEgg;
import com.hmdzl.spspd.items.eggs.RandomMonthEgg;
import com.hmdzl.spspd.items.food.Honey;
import com.hmdzl.spspd.items.food.completefood.AflyFood;
import com.hmdzl.spspd.items.food.completefood.FruitCandy;
import com.hmdzl.spspd.items.food.completefood.Hamburger;
import com.hmdzl.spspd.items.food.completefood.Meatroll;
import com.hmdzl.spspd.items.food.completefood.MixPizza;
import com.hmdzl.spspd.items.food.completefood.MoonCake;
import com.hmdzl.spspd.items.food.completefood.NutCake;
import com.hmdzl.spspd.items.food.completefood.NutCookie;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.completefood.Porksoup;
import com.hmdzl.spspd.items.food.completefood.RiceGruel;
import com.hmdzl.spspd.items.food.completefood.Vegetablekebab;
import com.hmdzl.spspd.items.food.staplefood.Pasty;
import com.hmdzl.spspd.items.journalpages.EnergyCore;
import com.hmdzl.spspd.items.journalpages.NewHome;
import com.hmdzl.spspd.items.journalpages.SafeSpotPage;
import com.hmdzl.spspd.items.journalpages.Sokoban1;
import com.hmdzl.spspd.items.journalpages.Sokoban2;
import com.hmdzl.spspd.items.journalpages.Sokoban3;
import com.hmdzl.spspd.items.journalpages.Sokoban4;
import com.hmdzl.spspd.items.journalpages.Town;
import com.hmdzl.spspd.items.journalpages.Vault;
import com.hmdzl.spspd.items.medicine.Hardpill;
import com.hmdzl.spspd.items.medicine.Powerpill;
import com.hmdzl.spspd.items.medicine.Smashpill;
import com.hmdzl.spspd.items.misc.Ankhshield;
import com.hmdzl.spspd.items.misc.AttackShield;
import com.hmdzl.spspd.items.misc.AttackShoes;
import com.hmdzl.spspd.items.misc.BShovel;
import com.hmdzl.spspd.items.misc.BigBattery;
import com.hmdzl.spspd.items.misc.CopyBall;
import com.hmdzl.spspd.items.misc.DanceLion;
import com.hmdzl.spspd.items.misc.DemoScroll;
import com.hmdzl.spspd.items.misc.DiceTower;
import com.hmdzl.spspd.items.misc.FaithSign;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.misc.GnollMark;
import com.hmdzl.spspd.items.misc.GrassBook;
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
import com.hmdzl.spspd.items.misc.LeaderFlag;
import com.hmdzl.spspd.items.misc.MKbox;
import com.hmdzl.spspd.items.misc.MechPocket;
import com.hmdzl.spspd.items.misc.MissileShield;
import com.hmdzl.spspd.items.misc.NeedPaper;
import com.hmdzl.spspd.items.misc.NmHealBag;
import com.hmdzl.spspd.items.misc.PPC;
import com.hmdzl.spspd.items.misc.PPC2;
import com.hmdzl.spspd.items.misc.PotionOfMage;
import com.hmdzl.spspd.items.misc.RangeBag;
import com.hmdzl.spspd.items.misc.RewardPaper;
import com.hmdzl.spspd.items.misc.RockManJumpshoes;
import com.hmdzl.spspd.items.misc.SavageHelmet;
import com.hmdzl.spspd.items.misc.SeriousPunch;
import com.hmdzl.spspd.items.misc.Shovel;
import com.hmdzl.spspd.items.misc.UndeadBook;
import com.hmdzl.spspd.items.nornstone.BlueNornStone;
import com.hmdzl.spspd.items.nornstone.GreenNornStone;
import com.hmdzl.spspd.items.nornstone.OrangeNornStone;
import com.hmdzl.spspd.items.nornstone.PurpleNornStone;
import com.hmdzl.spspd.items.nornstone.YellowNornStone;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.potions.PotionOfMight;
import com.hmdzl.spspd.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.items.potions.PotionOfPurity;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.items.potions.PotionOfStrength;
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
import com.hmdzl.spspd.items.scrolls.ScrollOfDummy;
import com.hmdzl.spspd.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.items.scrolls.ScrollOfLullaby;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfMirrorImage;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.scrolls.ScrollOfTerror;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.wands.CannonOfMage;
import com.hmdzl.spspd.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.items.wands.WandOfTest;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.Sling;
import com.hmdzl.spspd.items.weapon.melee.Dagger;
import com.hmdzl.spspd.items.weapon.melee.Glaive;
import com.hmdzl.spspd.items.weapon.melee.HolyWater;
import com.hmdzl.spspd.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.items.weapon.melee.Mace;
import com.hmdzl.spspd.items.weapon.melee.MageBook;
import com.hmdzl.spspd.items.weapon.melee.Rapier;
import com.hmdzl.spspd.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.items.weapon.melee.Spear;
import com.hmdzl.spspd.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.items.weapon.melee.TrickSand;
import com.hmdzl.spspd.items.weapon.melee.Whip;
import com.hmdzl.spspd.items.weapon.melee.WoodenStaff;
import com.hmdzl.spspd.items.weapon.melee.special.DiamondPickaxe;
import com.hmdzl.spspd.items.weapon.melee.special.EleKatana;
import com.hmdzl.spspd.items.weapon.melee.special.HolyMace;
import com.hmdzl.spspd.items.weapon.melee.special.LinkSword;
import com.hmdzl.spspd.items.weapon.melee.special.MeleePan;
import com.hmdzl.spspd.items.weapon.melee.special.NinjaFan;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Whisk;
import com.hmdzl.spspd.items.weapon.missiles.ElfBow;
import com.hmdzl.spspd.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.items.weapon.missiles.MegaCannon;
import com.hmdzl.spspd.items.weapon.missiles.MiniGun;
import com.hmdzl.spspd.items.weapon.missiles.ShootGun;
import com.hmdzl.spspd.items.weapon.missiles.TaurcenBow;
import com.hmdzl.spspd.items.weapon.missiles.arrows.BlindFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.HealFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.NutFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RocketMissile;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RootFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.SmokeFruit;
import com.hmdzl.spspd.items.weapon.missiles.arrows.ToxicFruit;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.PlantPotBlock;
import com.hmdzl.spspd.items.weapon.missiles.buildblock.WallBlock;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Javelin;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MiniMoai;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.Tamahawk;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Boomerang;
import com.hmdzl.spspd.items.weapon.missiles.throwing.EmpBola;
import com.hmdzl.spspd.items.weapon.missiles.throwing.EscapeKnive;
import com.hmdzl.spspd.items.weapon.missiles.throwing.PoisonDart;
import com.hmdzl.spspd.items.weapon.missiles.throwing.Skull;
import com.hmdzl.spspd.items.weapon.ranges.StoneBowN;
import com.hmdzl.spspd.items.weapon.ranges.WoodenBowN;
import com.hmdzl.spspd.items.weapon.spammo.BattleAmmo;
import com.hmdzl.spspd.items.weapon.spammo.GoldAmmo;
import com.hmdzl.spspd.items.weapon.spammo.HeavyAmmo;
import com.hmdzl.spspd.items.weapon.spammo.WoodenAmmo;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Dewcatcher;
import com.hmdzl.spspd.plants.Firebloom;
import com.hmdzl.spspd.plants.Rotberry;
import com.hmdzl.spspd.plants.Seedpod;
import com.hmdzl.spspd.sprites.HeroSkinSprite;
import com.hmdzl.spspd.sprites.HeroSkinSpriteSheet;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" , "warrior_name"),
	MAGE( "mage", "mage_name"),
	ROGUE( "rogue","rogue_name" ),
	HUNTRESS( "huntress", "huntress_name"),
	PERFORMER( "performer", "performer_name"),
	SOLDIER( "soldier", "soldier_name"),
	FOLLOWER( "follower", "follower_name"),
	ASCETIC( "ascetic", "ascetic_name"),
	NEWPLAYER( "newplayer", "newplayer_name");
	

	private String title;
	private String title2;

	HeroClass(String title, String title2) {
		this.title = title;
		this.title2 = title2;
	}


	public void initHero(Hero hero) {

		hero.heroClass = this;

		if (hero.heroClass != HeroClass.NEWPLAYER)
		   initCommon( hero );

		switch (this) {
		case WARRIOR:
			initWarrior( hero );
			break;

		case MAGE:
			initMage( hero );
			break;

		case ROGUE:
			initRogue( hero );
			break;

		case HUNTRESS:
			initHuntress( hero );
			break;

		case PERFORMER:
			initPerformer( hero );
			break;

		case SOLDIER:
			initSoldier( hero );
			break;
			
		case FOLLOWER:
			initFollower( hero );
			break;
			
		case ASCETIC:
			initAscetic( hero );
			break;	

        case NEWPLAYER:
			initNewPlayer( hero );
			break;				
			
		}			
		
		if (Badges.isUnlocked(learnBadge())) {
			new PlantPotBlock(3).identify().collect();
			//new WandOfTest().identify().collect();
		}
		
		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {
		new KeyRing().collect();
		new ArrowCollecter().collect();
		new Pasty().identify().collect();
		new NutCake().identify().collect();
		//new Lollipop().identify().collect();
		//new Chocolate().identify().collect();
		//new HealFruit(99).collect();
		//new MeleePan().identify().collect();
		new DewVial(0,0).identify().collect();
		new RocketMissile(2).collect();
		//new Vialupdater().collect();
		if (Dungeon.skins != 3 && Dungeon.skins != 6) {
			new Ankhshield().collect();
		}
		if (Dungeon.skins == 3) {
			EtherealChains chains = new EtherealChains();
			(hero.belongings.misc3 = chains).identify().upgrade(3);
			hero.belongings.misc3.activate(hero);
			new EmpBola(10).collect();
			new PoisonDart(10).collect();
			new BlindFruit(10).collect();
			new RocketMissile(5).collect();
			new Weightstone().collect();
			new Stylus().collect();
			new PotionOfHealing().identify().collect();
            new ScrollOfMagicMapping().identify().collect();
		}

		if (Dungeon.skins == 0) {
			new RandomEgg().collect();
			new PetFood().collect();
			new PocketBall().collect();
		}
		
		if (Dungeon.skins == 5) {

			Weapon weapon = (Weapon) Generator.random(Generator.Category.MELEEWEAPON);
			Armor armor = new BaseArmor();
			Ring ring = (Ring) Generator.random(Generator.Category.RING);
			Artifact artifact = (Artifact) Generator.random(Generator.Category.ARTIFACT);

			(hero.belongings.weapon = weapon).identify();
			(hero.belongings.armor = armor).identify();
			(hero.belongings.misc1 = artifact).identify();
			hero.belongings.misc1.activate(hero);
			(hero.belongings.misc3 = ring).identify();
			hero.belongings.misc3.activate(hero);

			hero.STR += 10;
            Dungeon.limitedDrops.strengthPotions.count+=10;
			Item shoes = Generator.random(Generator.Category.SHOES);
			shoes.identify().collect();
			new KnowledgeBook().collect();
			new ScrollOfRemoveCurse().identify().collect();

			new TransmutationBall().collect();
			new TransmutationBall().collect();
			new TransmutationBall().collect();
		}		

		if (Dungeon.isChallenged(Challenges.ITEM_PHOBIA)){
			Dungeon.gold += 1000;
		}
		if (Dungeon.isChallenged(Challenges.LISTLESS)){
			new PotionOfMight().collect();
			new PotionOfMight().setKnown();
			new Honey().collect();
		}
		if (Dungeon.isChallenged(Challenges.NIGHTMARE_VIRUS)){
			new UnBlessAnkh().collect();
		}
		if (Dungeon.isChallenged(Challenges.ENERGY_LOST)){
			new Pasty().collect();
		}
		if (Dungeon.isChallenged(Challenges.DEW_REJECTION)){
			new Dewcatcher.Seed().collect();
			new Dewcatcher.Seed().collect();
		}
		if (Dungeon.isChallenged(Challenges.DARKNESS)){
		    new ScrollOfMagicMapping().collect();
		    new ScrollOfMagicMapping().collect();
			new ScrollOfMagicMapping().setKnown();
		}
		if (Dungeon.isChallenged(Challenges.ABRASION)){
			new ScrollOfUpgrade().collect();
			new ScrollOfUpgrade().setKnown();
			new ScrollOfMagicalInfusion().collect();
			new ScrollOfMagicalInfusion().setKnown();
		}
		if (Dungeon.isChallenged(Challenges.ELE_STOME)){
			new ScrollOfPsionicBlast().collect();
			new ScrollOfPsionicBlast().setKnown();
			new PotionOfShield().collect();
			new PotionOfShield().setKnown();
		}

		if (Dungeon.isChallenged(Challenges.TEST_TIME)){
			//Dungeon.dewWater = true;
           // Dungeon.dewDraw = true;
			new Elevator().collect();
			new SkillBook().collect();
			new SafeSpotPage().collect();
		    new Town().collect();
			new SewerChallenge().collect();
			new PrisonChallenge().collect();
			new CaveChallenge().collect();
			new CityChallenge().collect();
			new ScrollHolder().collect();
			new SeedPouch().collect();
			new PotionBandolier().collect();
			new ShoppingCart().collect();
			new WandHolster().collect();
			new Sokoban1().collect();
			new Sokoban2().collect();
			new Sokoban3().collect();
			new Sokoban4().collect();
			new DolyaSlate().collect();
			new EnergyCore().collect();
			new Vault().collect();
			new NewHome().collect();
			new CourageChallenge().collect();
			new PowerChallenge().collect();
			new WisdomChallenge().collect();
			new TriForce().collect();
			new Palantir().collect();

           // new ApostleBox().collect();

			new SoulCollect().collect();
			//new ErrorAmmo(20).collect();
			new PowerHand().collect();
			new TomeOfMastery().collect();
			//new ShadowEater().collect();
			new TestWeapon().identify().collect();

			new EasterEgg().collect();
			//new WandOfFlock().upgrade(5).identify().collect();
			//new WandOfFlock().upgrade(10).identify().collect();
           new AflyEgg().collect();
			new GoldDragonEgg().collect();
			//new VioletDragonEgg().collect();
		//	new ShadowDragonEgg().collect();
		//	new LightDragonEgg().collect();
		//	new GreenDragonEgg().collect();
		//	new RedDragonEgg().collect();
		//	new BlueDragonEgg().collect();
			new PocketBall(10).collect();

			//new KnowledgeBook().collect();

		for(int i=0; i<199; i++){
			//new ScrollOfMagicalInfusion().identify().collect();
			//new ScrollOfTeleportation().identify().collect();
			new ScrollOfIdentify().identify().collect();
            new ScrollOfMagicMapping().identify().collect();
			//new WaterItem().collect();
			new MoonCake().collect();
			new PotionOfMindVision().identify().collect();
			//new PotionOfStrength().identify().collect();
			//new ExpOre().collect();
			//new SkillOfMig().collect();
			//new PotionOfShield().identify().collect();
			new YellowNornStone().collect();
			new BlueNornStone().collect();
			new OrangeNornStone().collect();
			new PurpleNornStone().collect();
			new GreenNornStone().collect();
	   }
			for(int i=0; i<10; i++){
		   // new ExpOre().collect();
		  // new StrBottle().collect();
		   // new TransmutationBall().collect();
				new Seedpod.Seed().collect();
				new Dewcatcher.Seed().collect();
				//new BlandfruitBush.Seed().collect();
				//new Rotberry.Seed().collect();
				new ScrollOfDummy().collect();
				new PotionOfMending().collect();
				new ScrollOfPsionicBlast().collect();
				new Hamburger().collect();
				new RandomMonthEgg().collect();
			}

			new RingOfElements().upgrade(10).identify().collect();
			new RingOfAccuracy().upgrade(10).identify().collect();
			new RingOfMight().upgrade(10).identify().collect();
			new RingOfForce().upgrade(10).identify().collect();
			new RingOfFuror().upgrade(10).identify().collect();
			new RingOfEvasion().upgrade(10).identify().collect();
			new RingOfEnergy().upgrade(10).identify().collect();
			new RingOfMagic().upgrade(10).identify().collect();
			new RingOfHaste().upgrade(10).identify().collect();
			new RingOfSharpshooting().upgrade(10).identify().collect();
			new RingOfTenacity().upgrade(10).identify().collect();

			//new SewerReward().collect();
		new SaveYourLife().collect();
		new IceChallenge().collect();

		//new UnBlessAnkh().collect();

		//new WandOfFlock().upgrade(10).identify().collect();
		//new WandOfBlackMeow().identify().collect();
		new Pumpkin().collect();
		new FourClover().collect();

		new MasterThievesArmband().upgrade(5).collect();
			//new AlienBag().collect();
		Dungeon.gold = 10000;
		//Dungeon.gold = 10000000;
		hero.TRUE_HT=hero.HP=10000;
		Dungeon.hero.updateHT(false);
		//hero.STR = hero.STR + 20;
		Dungeon.depth = 1;

		}
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case PERFORMER:
				return Badges.Badge.MASTERY_PERFORMER;
			case SOLDIER:
				return Badges.Badge.MASTERY_SOLDIER;
			case FOLLOWER:
				return Badges.Badge.MASTERY_FOLLOWER;
			case ASCETIC:
				return Badges.Badge.MASTERY_ASCETIC;
		}

		return null;
	}

	public Badges.Badge learnBadge() {
		return Badges.Badge.LEARN;
	}
	
	private static void initWarrior(Hero hero) {

		if (Dungeon.skins == 1) {
			(hero.belongings.armor = new VestArmor()).identify().upgrade(1);
			RingOfForce force = new RingOfForce();
			(hero.belongings.misc1 = force).identify().upgrade(1);
			hero.belongings.misc1.activate(hero);

			RingOfMight might = new RingOfMight();
			(hero.belongings.misc2 = might).identify().upgrade(1);
			hero.belongings.misc2.activate(hero);

			new AttackShield().collect();
			new JumpW().collect();

		} else if (Dungeon.skins == 2) {
				hero.TRUE_HT+=36;
			    hero.HP+=36;
			    Dungeon.hero.updateHT(false);
				hero.STR -=4;
				hero.hitSkill-=4;
				hero.evadeSkill+=1;
			    hero.magicSkill+=6;
				(hero.belongings.armor = new BaseArmor()).upgrade(6).identify();
				new WandOfFirebolt().upgrade(6).identify().collect();
				new DemoScroll().collect();
				new StrBottle().collect();
				new StrBottle().collect();
				new StrBottle().collect();
				new StrBottle().collect();
				Dungeon.gold+=666;
			new JumpW().collect();

		}  else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Spear()).identify();
			(hero.belongings.armor = new DiscArmor()).identify();
			new MissileShield().collect();
            new SavageHelmet().collect();

            hero.STR += 2;
            Dungeon.limitedDrops.strengthPotions.count+=2;

		} else if (Dungeon.skins == 4) {
            hero.STR += 3;
            Dungeon.limitedDrops.strengthPotions.count+=3;

            (hero.belongings.weapon = new GunB()).identify();
            (hero.belongings.armor = new StyrofoamArmor()).identify();

            new TestWeapon().identify().unique().collect();
			new WandOfTest().identify().collect();
			new TestArmor().identify().unique().collect();

            new RewardPaper().identify().collect();

			new JumpW().collect();

		} else if (Dungeon.skins == 5) {

        } else if (Dungeon.skins == 6) {
                (hero.belongings.armor = new VestArmor()).identify().upgrade(1);
                RingOfForce force = new RingOfForce();
                (hero.belongings.misc1 = force).identify().upgrade(1);
                hero.belongings.misc1.activate(hero);

                RingOfMight might = new RingOfMight();
                (hero.belongings.misc2 = might).identify().upgrade(1);
                hero.belongings.misc2.activate(hero);

                new SeriousPunch().collect();
                new Ankhshield().collect();
                new JumpW().collect();
		} else {
			(hero.belongings.weapon = new ShortSword()).identify();
			(hero.belongings.armor = new WoodenArmor()).identify();
			new MissileShield().collect();
			new Powerpill().collect();
			new Smashpill().collect();
			new Hardpill().collect();
			new JumpW().collect();
	}

		new Porksoup().identify().collect();
			new PotionOfStrength().setKnown();
			new ScrollOfUpgrade().setKnown();

	}

	private static void initMage(Hero hero) {

		if (Dungeon.skins == 1) {
			hero.STR+=4;

			(hero.belongings.weapon = new Whip()).identify().upgrade(2);
			(hero.belongings.armor = new LeatherArmor()).identify().upgrade(1);

			Dungeon.limitedDrops.strengthPotions.count+=4;
			new CannonOfMage().identify().collect();
			new JumpM().collect();
		} else if (Dungeon.skins == 2) {
			hero.TRUE_HT-=10;
			Dungeon.hero.updateHT(false);
			hero.hitSkill-=5;
			hero.evadeSkill+=2;
			hero.magicSkill+=3;
			(hero.belongings.weapon = new Dagger()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			new WandOfFirebolt().upgrade(1).identify().collect();
			new WandOfFreeze().upgrade(1).identify().collect();
			new WandOfLightning().upgrade(1).identify().collect();
			new GnollMark().collect();
			new JumpM().collect();
		}  else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new WandOfFirebolt().identify().collect();
			new WandOfFreeze().identify().collect();
			new GnollMark().collect();
			new PotionOfMage().identify().collect();

		} else if (Dungeon.skins == 4) {

			(hero.belongings.weapon = new ElfBow()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new JumpM().collect();
			new ScrollOfRegrowth().identify().collect();

		} else if (Dungeon.skins == 5) {

        } else if (Dungeon.skins == 6) {
            (hero.belongings.weapon = new ShortSword()).identify();
            (hero.belongings.armor = new ClothArmor()).identify();

            new GnollMark().collect();
            new WandOfLight().identify().collect();
            new Powerpill().collect();
            new Smashpill().collect();
            new Hardpill().collect();
            new JumpW().collect();

		} else {
			(hero.belongings.weapon = new MageBook()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();
			new WandOfMagicMissile().identify().collect();
			new WandOfDisintegration().identify().collect();
			new PotionOfMage().identify().collect();
			new JumpM().collect();

		}
		hero.magicSkill = hero.magicSkill + 3;
		new Meatroll().identify().collect();
		new ScrollOfIdentify().setKnown();
		new PotionOfLiquidFlame().setKnown();
	
	}

	private static void initRogue(Hero hero) {
		if (Dungeon.skins == 1) {
			(hero.belongings.weapon = new LinkSword()).identify();
			hero.belongings.weapon.activate(hero);
			(hero.belongings.armor = new WoodenArmor()).identify();
			hero.STR += 1;
			Dungeon.limitedDrops.strengthPotions.count++;
			EtherealChains ec = new EtherealChains();
			(hero.belongings.misc1 = ec).identify();
			hero.belongings.misc1.activate(hero);
			new JumpR().collect();
		}  else if (Dungeon.skins == 2) {
				hero.TRUE_HT-=20;
			Dungeon.hero.updateHT(false);
				hero.evadeSkill+=3;
			    hero.magicSkill+=3;
				(hero.belongings.weapon = new Dagger()).identify();
				(hero.belongings.armor = new ClothArmor()).identify();
				new UndeadBook().collect();
				new Skull(5).collect();
			new JumpR().collect();
		} else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Glaive()).identify();
			(hero.belongings.armor = new DiscArmor()).identify();

			CloakOfShadows cloak = new CloakOfShadows();
			(hero.belongings.misc1 = cloak).identify();
			hero.belongings.misc1.activate(hero);

			new HorseTotem().identify().collect();

			hero.STR += 4;
			Dungeon.limitedDrops.strengthPotions.count+=4;

		} else if (Dungeon.skins == 4) {
			(hero.belongings.weapon = new Dagger()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			new JumpR().collect();
            new NeedPaper().identify().collect();

		} else if (Dungeon.skins == 5) {

		} else if (Dungeon.skins == 6) {

            (hero.belongings.weapon = new EleKatana()).identify();
            (hero.belongings.armor = new ClothArmor()).identify();

            CloakOfShadows cloak = new CloakOfShadows();
            (hero.belongings.misc1 = cloak).identify();
            hero.belongings.misc1.activate(hero);

			EtherealChains chains = new EtherealChains();
			(hero.belongings.misc3 = chains).identify().upgrade(3);
			hero.belongings.misc3.activate(hero);
			new EmpBola(10).collect();
			new PoisonDart(10).collect();
			new BlindFruit(10).collect();
			new Weightstone().collect();
			new Stylus().collect();
			new PotionOfHealing().identify().collect();
			new ScrollOfMagicMapping().identify().collect();
           new WandOfLightning().upgrade(3).identify().collect();

            hero.STR += 2;
            Dungeon.limitedDrops.strengthPotions.count+=2;
		} else if (Dungeon.skins == 7) {
			(hero.belongings.weapon = new Dagger()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			new JumpR().collect();
			new AflyFood().collect();
			new AflyFood().collect();
			new AflyFood().collect();
			new AflyEgg().collect();
			new AflyEgg().collect();
			new AflyEgg().collect();
			hero.STR += 10;
			Dungeon.limitedDrops.strengthPotions.count+=10;

        }else {

			(hero.belongings.weapon = new Dagger()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			CloakOfShadows cloak = new CloakOfShadows();
			(hero.belongings.misc1 = cloak).identify();
			hero.belongings.misc1.activate(hero);

			new BlindFruit(3).identify().collect();
			new JumpR().collect();
		}

		new RiceGruel().identify().collect();
		new ScrollOfMagicMapping().setKnown();
		new PotionOfInvisibility().setKnown();
	}

	private static void initHuntress(Hero hero) {
		if (Dungeon.skins == 1) {
			hero.STR+=1;
			(hero.belongings.weapon = new Dagger()).identify().upgrade(1);
			(hero.belongings.armor = new RubberArmor()).identify().upgrade(1);
			Dungeon.limitedDrops.strengthPotions.count++;
			TimekeepersHourglass th = new TimekeepersHourglass();
			(hero.belongings.misc1 = th).identify().upgrade(5);
			hero.belongings.misc1.activate(hero);

			new ManyKnive().upgrade(1).identify().collect();

			EscapeKnive knife = new EscapeKnive(5);
			knife.identify().collect();
			new JumpH().collect();
		} else if (Dungeon.skins == 2) {
			hero.TRUE_HT-=10;
			Dungeon.hero.updateHT(false);
			hero.hitSkill+=5;
			hero.evadeSkill+=3;
			(hero.belongings.weapon = new Knuckles()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();
			new TaurcenBow().identify().collect();
			new JumpH().collect();
		} else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Knuckles()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			new TaurcenBow().identify().collect();
            new RangeBag().identify().collect();

		} else if (Dungeon.skins == 4) {

			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			new PPC().identify().collect();
			new JumpH().collect();

		} else if (Dungeon.skins == 5) {

		} else if (Dungeon.skins == 6) {
            (hero.belongings.weapon = new Sling()).identify();
            (hero.belongings.armor = new VestArmor()).identify();

            new ShootGun().identify().collect();
            new JumpS().collect();
            EmpBola empbola = new EmpBola(3);
            empbola.identify().collect();
            hero.hitSkill = hero.hitSkill + 4;
            hero.evadeSkill = hero.evadeSkill + 2;

        }else {
			(hero.belongings.weapon = new Knuckles()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			Boomerang boomerang = new Boomerang(null);
			boomerang.identify().collect();
			EmpBola empbola = new EmpBola(3);
			empbola.identify().collect();
			new JumpH().collect();
		}
		new Vegetablekebab().identify().collect();
		new PotionOfMindVision().setKnown();
		new ScrollOfRemoveCurse().setKnown();
	}

	private static void initPerformer(Hero hero) {

        if (Dungeon.skins == 1) {
            (hero.belongings.weapon = new GunA()).identify().upgrade(2);
            (hero.belongings.armor = new VestArmor()).identify().upgrade(1);
            new GoldAmmo().collect();
            new WoodenAmmo().collect();
            new BattleAmmo().collect();
            AlienBag alienBag = new AlienBag();
            (hero.belongings.misc1 = alienBag).identify();
            hero.belongings.misc1.activate(hero);
            new BShovel().collect();
			new JumpP().collect();
        } else if (Dungeon.skins == 2) {
			hero.TRUE_HT-=10;
			Dungeon.hero.updateHT(false);
			hero.magicSkill+=3;
			hero.evadeSkill+=5;

			hero.STR += 4;
			Dungeon.limitedDrops.strengthPotions.count+=4;

			(hero.belongings.weapon = new HolyWater()).identify();
			(hero.belongings.armor = new BaseArmor()).identify();
			new CopyBall().collect();
            new PotionOfMending().identify().collect();
			new PotionOfHealing().identify().collect();
			new JumpP().collect();
		} else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Triangolo()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			new Shovel().identify().collect();
			new DanceLion().identify().collect();

		} else if (Dungeon.skins == 4) {

			hero.STR += 2;
			Dungeon.limitedDrops.strengthPotions.count+=2;

			(hero.belongings.weapon = new Mace()).identify();
			(hero.belongings.armor = new LeatherArmor()).identify();

			new LeaderFlag().collect();
			new JumpP().collect();

			Dungeon.gold += 1000;

		} else if (Dungeon.skins == 5) {

        } else if (Dungeon.skins == 6) {
            hero.STR += 2;
            Dungeon.limitedDrops.strengthPotions.count+=2;

            (hero.belongings.weapon = new Mace()).identify();
            (hero.belongings.armor = new LeatherArmor()).identify();

            new PPC2().collect();
            new JumpP().collect();

		}   else if (Dungeon.skins == 7) {

            hero.STR += 2;
            Dungeon.limitedDrops.strengthPotions.count+=2;

            (hero.belongings.weapon = new MegaCannon()).identify();
             hero.belongings.weapon.activate(hero);
             (hero.belongings.armor = new PerformerArmor()).identify();

           new Dewcatcher.Seed().collect();
            new Dewcatcher.Seed().collect();
           new RockManJumpshoes().collect();


          // new Alink().collect();
		//	new Bmech().collect();
		//	new Dpotion().collect();
		//	new Gleaf().collect();
		//	new Ichain().collect();
		//	new Lbox().collect();
		//	new Mlaser().collect();
		//	new Nshuriken().collect();
		//	new Obubble().collect();
		//	new Sweb().collect();
		//	new Trush().collect();
		//	new Zshield().collect();

    } else {

            (hero.belongings.weapon = new Triangolo()).identify();
            (hero.belongings.armor = new ClothArmor()).identify();

            new Shovel().identify().collect();
            new ScrollOfLullaby().collect();
			new JumpP().collect();

			new FireBomb().collect();
			new IceBomb().collect();
			new StormBomb().collect();
			new DungeonBomb().collect();
        }

		new NutCookie(6).identify().collect();
		new DungeonBomb().collect();
		new ScrollOfLullaby().setKnown();
		new PotionOfPurity().setKnown();
	}
	
	private static void initSoldier(Hero hero) {
		if (Dungeon.skins == 1) {
			hero.STR += 2;
			(hero.belongings.armor = new LeatherArmor()).identify().upgrade(3);
			Dungeon.limitedDrops.strengthPotions.count += 2;

			new AttackShoes().collect();
			new MKbox().collect();

		}else if (Dungeon.skins == 2) {
				hero.TRUE_HT+=5;
			hero.HP+=5;
			Dungeon.hero.updateHT(false);
				hero.STR += 6;
				hero.magicSkill+=5;
			    hero.hitSkill-=10;
				hero.evadeSkill-=35;
				(hero.belongings.weapon = new GunC()).identify();
				(hero.belongings.armor = new BaseArmor()).identify();
			    Dungeon.limitedDrops.strengthPotions.count += 6;
				new MechPocket().collect();
			    new JumpS().collect();

		} else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new GunA()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new HeavyAmmo().collect();
			new GunOfSoldier().identify().collect();
			new HealBag().identify().collect();

		} else if (Dungeon.skins == 4) {

			(hero.belongings.weapon = new GunA()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new JumpS().collect();
			new NmHealBag().identify().collect();
			new EscapeKnive(10).collect();

		} else if (Dungeon.skins == 5) {

		} else if (Dungeon.skins == 6) {
			hero.TRUE_HT+=5;
			hero.HP+=5;
			Dungeon.hero.updateHT(false);
			hero.STR += 6;
			hero.magicSkill+=5;
			hero.hitSkill-=10;
			hero.evadeSkill-=35;
			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new BaseArmor()).identify();
			Dungeon.limitedDrops.strengthPotions.count += 6;
			new ShootGun().identify().collect();
			new JumpS().collect();
		} else if (Dungeon.skins == 7) {
			//hero.STR += 6;
			//hero.magicSkill+=5;
			//hero.hitSkill-=10;
			//hero.evadeSkill-=35;
			//(hero.belongings.weapon = new TrickSand()).identify().upgrade(100);
			//(hero.belongings.armor = new BaseArmor()).identify();
			//Dungeon.limitedDrops.strengthPotions.count += 6;
			//new ShootGun().identify().collect();
			//new JumpS().collect();

			RobotDMT robotDMT = new RobotDMT();
			(hero.belongings.misc1 = robotDMT).identify();
			hero.belongings.misc1.activate(hero);
		} else {
			(hero.belongings.weapon = new Sling()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			new GunOfSoldier().identify().collect();
			new JumpS().collect();
			EscapeKnive knife = new EscapeKnive(3);
			knife.identify().collect();
		}

		new MixPizza(4).identify().collect();
			new ScrollOfRage().setKnown();
			new PotionOfMending().setKnown();

			hero.hitSkill = hero.hitSkill + 4;
			hero.evadeSkill = hero.evadeSkill + 2;

	}	

	private static void initFollower(Hero hero) {
		if (Dungeon.skins == 1) {
			(hero.belongings.weapon = new DiamondPickaxe()).identify();
			(hero.belongings.armor = new LeatherArmor()).identify();
			hero.STR += 4;
			Dungeon.limitedDrops.strengthPotions.count += 4;
			new JumpF().collect();
		}else if (Dungeon.skins == 2) {
			(hero.belongings.weapon = new Dagger()).upgrade(2).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			Pylon pylon = new Pylon();
			(hero.belongings.misc1 = pylon).identify().upgrade(2);
			hero.belongings.misc1.activate(hero);
			new JumpF().collect();

		} else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Rapier()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new FaithSign().identify().collect();
			Pylon pylon = new Pylon();
			(hero.belongings.misc1 = pylon).identify();
			hero.belongings.misc1.activate(hero);

			hero.STR += 4;
			Dungeon.limitedDrops.strengthPotions.count+=4;

		}  else if (Dungeon.skins == 4) {

			(hero.belongings.weapon = new Knuckles()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			new DiceTower().identify().collect();
			new JumpF().collect();
			Dungeon.gold += 1000;

		} else if (Dungeon.skins == 5) {

		} else if (Dungeon.skins == 6) {
			(hero.belongings.weapon = new TrickSand()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			new Skull(5).collect();
			new FaithSign().identify().collect();
			new JumpA().collect();

		} else {

			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();
			new PotionOfHealing().identify().collect();
			new FaithSign().identify().collect();
			new JumpF().collect();
		}

		new MoonCake().collect();
		new ScrollOfTerror().setKnown();
        new PotionOfHealing().setKnown();

	}		
	
	private static void initAscetic(Hero hero) {

		if (Dungeon.skins == 1) {
			hero.STR+=4;
			(hero.belongings.weapon = new HolyMace()).identify().upgrade(1);
			(hero.belongings.armor = new LeatherArmor()).identify().upgrade(1);
			Dungeon.limitedDrops.strengthPotions.count+=4;
			//new CannonOfMage().identify().collect();
			new JumpA().collect();
		} else if (Dungeon.skins == 2) {
			hero.TRUE_HT+=10;
			Dungeon.hero.updateHT(false);
			hero.hitSkill+=4;
			hero.evadeSkill+=2;
			hero.magicSkill-=3;
			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new LifeArmor()).identify();
			new GrassBook().collect();
			new JumpA().collect();
		}  else if (Dungeon.skins == 3) {

            (hero.belongings.weapon = new Whisk()).identify();
            (hero.belongings.armor = new LeatherArmor()).identify();

            new BigBattery().identify().collect();
            UnstableSpellbook spellbook = new UnstableSpellbook();
            (hero.belongings.misc1 = spellbook).identify();
            hero.belongings.misc1.activate(hero);

            hero.STR += 4;
            Dungeon.limitedDrops.strengthPotions.count+=4;

		} else if (Dungeon.skins == 4) {

			//(hero.belongings.weapon = new ElfBow()).identify();
			//(hero.belongings.armor = new VestArmor()).identify();

			//new JumpM().collect();
			//new ScrollOfRegrowth().identify().collect();

		} else if (Dungeon.skins == 5) {

        } else if (Dungeon.skins == 6) {
           // (hero.belongings.weapon = new ShortSword()).identify();
          //  (hero.belongings.armor = new ClothArmor()).identify();

          //  new GnollMark().collect();
          //  new WandOfLight().identify().collect();
          //  new Powerpill().collect();
          //  new Smashpill().collect();
          //  new Hardpill().collect();
          //  new JumpW().collect();

		} else if (Dungeon.skins == 7) {
			 (hero.belongings.weapon = new NinjaFan()).identify();
			  (hero.belongings.armor = new VestArmor()).identify();

			DriedRose rose = new DriedRose();
			(hero.belongings.misc1 = rose).identify();
			hero.belongings.misc1.activate(hero);

			Pylon pylon = new Pylon();
			(hero.belongings.misc2 = pylon).identify();
			hero.belongings.misc2.activate(hero);

			  new WandOfFirebolt().identify().collect();
			  new JumpW().collect();

		} else {
			(hero.belongings.weapon = new TrickSand()).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			//new WandOfMagicMissile().identify().collect();
			new BigBattery().collect();
			new ActiveMrDestructo().collect();
			//new PotionOfMage().identify().collect();
			new JumpA().collect();

		}
		hero.magicSkill = hero.magicSkill + 3;
		new FruitCandy(3).collect();
		new ScrollOfMirrorImage().setKnown();
		new PotionOfShield().setKnown();
	
	}

	private static void initNewPlayer(Hero hero) {
		hero.TRUE_HT = 50;
		hero.HP = 10;
		new WoodenBowN().identify().collect();

		new StoneBowN().identify().collect();

		new SmokeFruit(99).collect();

		new GunA().identify().collect();

		new Torch().collect();

		new HealFruit(99).collect();
		new ToxicFruit(99).collect();
		new NutFruit(99).collect();
		new ScrollOfUpgrade().collect();
		new ScrollOfUpgrade().collect();

		new ScrollOfIdentify().collect();
		new ScrollOfIdentify().collect();
		//new ScrollOfIdentify().collect();

        new ShortSword().collect();

		new MiniMoai().collect();
		new MiniMoai().collect();

		new Tamahawk().collect();
		new Javelin().collect();

		new ScrollOfMagicalInfusion().collect();
		new ScrollOfMagicalInfusion().collect();
		new ScrollOfMagicalInfusion().collect();

		new Firebloom.Seed().collect();
		new Firebloom.Seed().collect();
		new Firebloom.Seed().collect();

		new Rotberry.Seed().collect();

		new RootFruit().collect();
		new RootFruit().collect();
		new RootFruit().collect();

		new RocketMissile().collect();
		new RocketMissile().collect();
		new RocketMissile().collect();

		new PlantPotBlock().collect();
        new WallBlock().collect();

	}

	public String title() {
		return Messages.get(HeroClass.class, title);
	}

	public String title2() {
		return Messages.get(HeroClass.class, title2);
	}


	public String spritesheet() {
		
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		case PERFORMER:
			return Assets.PERFORMER;	
		case SOLDIER:
			return Assets.SOLDIER;	
		case FOLLOWER:
			return Assets.FOLLOWER;	
		case ASCETIC:
			return Assets.ASCETIC;	
        case NEWPLAYER:
			return Assets.WARRIOR;				
		}
		
		return null;
	}

	public Image skinsheet(int skins) {
		switch (this) {
			case WARRIOR:
				return new HeroSkinSprite(HeroSkinSpriteSheet.WARRIOR + skins );
			case MAGE:
				return new HeroSkinSprite(HeroSkinSpriteSheet.MAGE + skins );
			case ROGUE:
				return new HeroSkinSprite(HeroSkinSpriteSheet.ROGUE + skins );
			case HUNTRESS:
				return new HeroSkinSprite(HeroSkinSpriteSheet.HUNTRESS + skins );
			case PERFORMER:
				return new HeroSkinSprite(HeroSkinSpriteSheet.PERFORMER + skins );
			case SOLDIER:
				return new HeroSkinSprite(HeroSkinSpriteSheet.SOLDIER + skins );
			case FOLLOWER:
				return new HeroSkinSprite(HeroSkinSpriteSheet.FOLLOWER + skins );
			case ASCETIC:
				return new HeroSkinSprite(HeroSkinSpriteSheet.ASCETIC + skins );
			case NEWPLAYER:
				return new HeroSkinSprite(HeroSkinSpriteSheet.WARRIOR + skins );
		}

		return null;
	}

	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return new String[]{
					Messages.get(HeroClass.class, "warrior_desc_item"),
					Messages.get(HeroClass.class, "warrior_desc_loadout"),
					Messages.get(HeroClass.class, "warrior_desc_misc"),
			};
		case MAGE:
			return new String[]{
					Messages.get(HeroClass.class, "mage_desc_item"),
					Messages.get(HeroClass.class, "mage_desc_loadout"),
					Messages.get(HeroClass.class, "mage_desc_misc"),
			};
		case ROGUE:
			return new String[]{
					Messages.get(HeroClass.class, "rogue_desc_item"),
					Messages.get(HeroClass.class, "rogue_desc_loadout"),
					Messages.get(HeroClass.class, "rogue_desc_misc"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_desc_item"),
					Messages.get(HeroClass.class, "huntress_desc_loadout"),
					Messages.get(HeroClass.class, "huntress_desc_misc"),
			};
		case PERFORMER:
			return new String[]{
					Messages.get(HeroClass.class, "performer_desc_item"),
					Messages.get(HeroClass.class, "performer_desc_loadout"),
					Messages.get(HeroClass.class, "performer_desc_misc"),
			};	
		case SOLDIER:
			return new String[]{
					Messages.get(HeroClass.class, "soldier_desc_item"),
					Messages.get(HeroClass.class, "soldier_desc_loadout"),
					Messages.get(HeroClass.class, "soldier_desc_misc"),
			};
		case FOLLOWER:
			return new String[]{
					Messages.get(HeroClass.class, "follower_desc_item"),
					Messages.get(HeroClass.class, "follower_desc_loadout"),
					Messages.get(HeroClass.class, "follower_desc_misc"),
			};	
		case ASCETIC:
			return new String[]{
					Messages.get(HeroClass.class, "ascetic_desc_item"),
					Messages.get(HeroClass.class, "ascetic_desc_loadout"),
					Messages.get(HeroClass.class, "ascetic_desc_misc"),
			};				
		}
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle(Bundle bundle) {
		bundle.put(CLASS, toString());
	}

	public static HeroClass restoreInBundle(Bundle bundle) {
		String value = bundle.getString(CLASS);
		return value.length() > 0 ? valueOf(value) : ROGUE;
	}


}
