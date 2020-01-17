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

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.Ankh;
import com.hmdzl.spspd.change.items.ArmorKit;
import com.hmdzl.spspd.change.items.DolyaSlate;
import com.hmdzl.spspd.change.items.Elevator;
import com.hmdzl.spspd.change.items.ExpOre;
import com.hmdzl.spspd.change.items.Palantir;
import com.hmdzl.spspd.change.items.PowerHand;
import com.hmdzl.spspd.change.items.SaveYourLife;
import com.hmdzl.spspd.change.items.SoulCollect;
import com.hmdzl.spspd.change.items.Stylus;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.TriForce;
import com.hmdzl.spspd.change.items.Weightstone;
import com.hmdzl.spspd.change.items.armor.normalarmor.BaseArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.LeatherArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.MailArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.change.items.artifacts.AlienBag;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.items.artifacts.Pylon;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.bags.PotionBandolier;
import com.hmdzl.spspd.change.items.bags.SeedPouch;
import com.hmdzl.spspd.change.items.bags.WandHolster;
import com.hmdzl.spspd.change.items.bombs.FireBomb;
import com.hmdzl.spspd.change.items.bombs.IceBomb;
import com.hmdzl.spspd.change.items.bombs.StormBomb;
import com.hmdzl.spspd.change.items.challengelists.CaveChallenge;
import com.hmdzl.spspd.change.items.challengelists.ChallengeList;
import com.hmdzl.spspd.change.items.challengelists.CityChallenge;
import com.hmdzl.spspd.change.items.challengelists.CourageChallenge;
import com.hmdzl.spspd.change.items.challengelists.PowerChallenge;
import com.hmdzl.spspd.change.items.challengelists.PrisonChallenge;
import com.hmdzl.spspd.change.items.challengelists.SewerChallenge;
import com.hmdzl.spspd.change.items.challengelists.WisdomChallenge;
import com.hmdzl.spspd.change.items.journalpages.Vault;
import com.hmdzl.spspd.change.items.medicine.Hardpill;
import com.hmdzl.spspd.change.items.medicine.Powerpill;
import com.hmdzl.spspd.change.items.medicine.Smashpill;
import com.hmdzl.spspd.change.items.misc.AttackShield;
import com.hmdzl.spspd.change.items.misc.AttackShoes;
import com.hmdzl.spspd.change.items.misc.BShovel;
import com.hmdzl.spspd.change.items.misc.CopyBall;
import com.hmdzl.spspd.change.items.misc.DanceLion;
import com.hmdzl.spspd.change.items.misc.DemoScroll;
import com.hmdzl.spspd.change.items.misc.FaithSign;
import com.hmdzl.spspd.change.items.misc.GnollMark;
import com.hmdzl.spspd.change.items.misc.HealBag;
import com.hmdzl.spspd.change.items.misc.HorseTotem;
import com.hmdzl.spspd.change.items.misc.JumpF;
import com.hmdzl.spspd.change.items.misc.Jumpshoes;
import com.hmdzl.spspd.change.items.misc.MKbox;
import com.hmdzl.spspd.change.items.misc.MechPocket;
import com.hmdzl.spspd.change.items.misc.RangeBag;
import com.hmdzl.spspd.change.items.misc.SavageHelmet;
import com.hmdzl.spspd.change.items.misc.UndeadBook;
import com.hmdzl.spspd.change.items.nornstone.BlueNornStone;
import com.hmdzl.spspd.change.items.nornstone.GreenNornStone;
import com.hmdzl.spspd.change.items.nornstone.OrangeNornStone;
import com.hmdzl.spspd.change.items.nornstone.PurpleNornStone;
import com.hmdzl.spspd.change.items.nornstone.YellowNornStone;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.change.items.rings.RingOfElements;
import com.hmdzl.spspd.change.items.rings.RingOfEnergy;
import com.hmdzl.spspd.change.items.rings.RingOfEvasion;
import com.hmdzl.spspd.change.items.rings.RingOfForce;
import com.hmdzl.spspd.change.items.rings.RingOfFuror;
import com.hmdzl.spspd.change.items.rings.RingOfHaste;
import com.hmdzl.spspd.change.items.rings.RingOfMagic;
import com.hmdzl.spspd.change.items.rings.RingOfMight;
import com.hmdzl.spspd.change.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.change.items.rings.RingOfTenacity;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTerror;
import com.hmdzl.spspd.change.items.summon.FairyCard;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.change.items.bags.ScrollHolder;
import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.items.food.Honey;
import com.hmdzl.spspd.change.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.change.items.food.staplefood.Pasty;
import com.hmdzl.spspd.change.items.journalpages.SafeSpotPage;
import com.hmdzl.spspd.change.items.journalpages.Sokoban2;
import com.hmdzl.spspd.change.items.journalpages.Sokoban3;
import com.hmdzl.spspd.change.items.journalpages.Sokoban4;
import com.hmdzl.spspd.change.items.misc.Ankhshield;
import com.hmdzl.spspd.change.items.armor.normalarmor.ClothArmor;
import com.hmdzl.spspd.change.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.change.items.bags.KeyRing;
import com.hmdzl.spspd.change.items.eggs.RandomEgg;
import com.hmdzl.spspd.change.items.food.completefood.PetFood;
import com.hmdzl.spspd.change.items.journalpages.JournalPage;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.journalpages.Town;
import com.hmdzl.spspd.change.items.misc.GunOfSoldier;
import com.hmdzl.spspd.change.items.misc.JumpP;
import com.hmdzl.spspd.change.items.misc.JumpS;
import com.hmdzl.spspd.change.items.misc.PotionOfMage;
import com.hmdzl.spspd.change.items.misc.Shovel;
import com.hmdzl.spspd.change.items.potions.PotionOfExperience;
import com.hmdzl.spspd.change.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.items.potions.PotionOfMending;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.change.items.potions.PotionOfPurity;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.reward.SewerReward;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfLullaby;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.wands.CannonOfMage;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.change.items.wands.WandOfFlock;
import com.hmdzl.spspd.change.items.wands.WandOfFreeze;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.change.items.weapon.guns.GunA;
import com.hmdzl.spspd.change.items.weapon.guns.GunC;
import com.hmdzl.spspd.change.items.weapon.guns.GunE;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.items.weapon.melee.Dagger;
import com.hmdzl.spspd.change.items.weapon.melee.Glaive;
import com.hmdzl.spspd.change.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.change.items.weapon.melee.Rapier;
import com.hmdzl.spspd.change.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.change.items.weapon.melee.MageBook;
import com.hmdzl.spspd.change.items.weapon.melee.Spear;
import com.hmdzl.spspd.change.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.change.items.weapon.melee.Whip;
import com.hmdzl.spspd.change.items.weapon.melee.WoodenStaff;
import com.hmdzl.spspd.change.items.weapon.melee.special.DiamondPickaxe;
import com.hmdzl.spspd.change.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.change.items.weapon.melee.special.LinkSword;
import com.hmdzl.spspd.change.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.zero.EmptyPotion;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.EmpBola;
import com.hmdzl.spspd.change.items.weapon.missiles.ErrorAmmo;
import com.hmdzl.spspd.change.items.misc.JumpW;
import com.hmdzl.spspd.change.items.misc.JumpH;
import com.hmdzl.spspd.change.items.misc.JumpM;
import com.hmdzl.spspd.change.items.misc.JumpR;
import com.hmdzl.spspd.change.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.change.items.misc.MissileShield;
import com.hmdzl.spspd.change.items.weapon.missiles.ManyKnive;
import com.hmdzl.spspd.change.items.weapon.missiles.PocketBall;
import com.hmdzl.spspd.change.items.weapon.missiles.PoisonDart;
import com.hmdzl.spspd.change.items.weapon.missiles.Skull;
import com.hmdzl.spspd.change.items.weapon.missiles.Smoke;
import com.hmdzl.spspd.change.items.weapon.missiles.TaurcenBow;
import com.hmdzl.spspd.change.items.weapon.spammo.BattleAmmo;
import com.hmdzl.spspd.change.items.weapon.spammo.GoldAmmo;
import com.hmdzl.spspd.change.items.weapon.spammo.HeavyAmmo;
import com.hmdzl.spspd.change.items.weapon.spammo.WoodenAmmo;
import com.hmdzl.spspd.change.plants.Dewcatcher;
import com.hmdzl.spspd.change.plants.Seedpod;
import com.watabou.utils.Bundle;
import com.hmdzl.spspd.change.messages.Messages;

public enum HeroClass {

	WARRIOR( "warrior" , "warrior_name"),
	MAGE( "mage", "mage_name"),
	ROGUE( "rogue","rogue_name" ),
	HUNTRESS( "huntress", "huntress_name"),
	PERFORMER( "performer", "performer_name"),
	SOLDIER( "soldier", "soldier_name"),
	FOLLOWER( "follower", "follower_name");
	

	private String title;
	private String title2;

	private HeroClass(String title, String title2) {
		this.title = title;
		this.title2 = title2;
	}


	public void initHero(Hero hero) {

		hero.heroClass = this;

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
			
		}			
		
		/*if (Badges.isUnlocked(masteryBadge())) {
			new TomeOfMastery().collect();
		}*/
		
		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {
		new KeyRing().collect();
		new NormalRation().identify().collect();

		if (Dungeon.skins != 3) {
			new Ankhshield().collect();
		}
		if (Dungeon.skins == 3) {
			EtherealChains chains = new EtherealChains();
			(hero.belongings.misc3 = chains).identify();
			hero.belongings.misc3.activate(hero);
			new EmpBola(7).collect();
			new PoisonDart(7).collect();
			new Smoke(7).collect();
			new Weightstone().collect();
			new Stylus().collect();
			new PotionOfHealing().identify().collect();
		}

		if (Dungeon.skins == 0) {
			new RandomEgg().collect();
			new PetFood().collect();
			new PocketBall().collect();
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
			new Ankh().collect();
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
		if (Dungeon.isChallenged(Challenges.TEST_TIME)){
			new Elevator().collect();
			new ArmorKit().collect();
			new SafeSpotPage().collect();
		    new Town().collect();
			new SewerChallenge().collect();
			new PrisonChallenge().collect();
			new CaveChallenge().collect();
			new CityChallenge().collect();
			new ScrollHolder().collect();
			new SeedPouch().collect();
			new PotionBandolier().collect();
			new WandHolster().collect();
			new Sokoban1().collect();
			new Sokoban2().collect();
			new Sokoban3().collect();
			new Sokoban4().collect();
			new DolyaSlate().collect();
			new Vault().collect();
			new CourageChallenge().collect();
			new PowerChallenge().collect();
			new WisdomChallenge().collect();
			new TriForce().collect();
			new Palantir().collect();
			new SoulCollect();
			new ErrorAmmo(20).collect();
			new PowerHand().collect();
			new TomeOfMastery().collect();
		for(int i=0; i<199; i++){
			new ScrollOfMagicalInfusion().identify().collect();
			new ScrollOfUpgrade().identify().collect();
			new ScrollOfIdentify().identify().collect();
            new ScrollOfMagicMapping().identify().collect();
			new ExpOre().collect();
			new Pasty().collect();
			new PotionOfMindVision().identify().collect();
			new PotionOfStrength().identify().collect();
			new YellowNornStone().collect();
			new BlueNornStone().collect();
			new OrangeNornStone().collect();
			new PurpleNornStone().collect();
			new GreenNornStone().collect();
	   }
			for(int i=0; i<10; i++){
				new Seedpod.Seed().collect();
				new ScrollOfRegrowth().collect();
			}
			new WandOfFlock().upgrade(10).identify().collect();
            new SewerReward().collect();
		new SaveYourLife().collect();
		new FireCracker().collect();

		Dungeon.gold = 10000;
		//Dungeon.gold = 10000000;
		hero.HT=hero.HP=10000;
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
		}

		return null;
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
				hero.HT+=36;
				hero.STR -=4;
				hero.hitSkill-=4;
				hero.evadeSkill+=1;
			    hero.magicSkill+=6;
				(hero.belongings.armor = new BaseArmor()).upgrade(6).identify();
				new WandOfFirebolt().upgrade(6).identify().collect();
				new DemoScroll().collect();
				new PotionOfStrength().collect();
				new PotionOfStrength().collect();
				new PotionOfStrength().collect();
				new PotionOfStrength().collect();
				Dungeon.gold+=666;
			new JumpW().collect();

		}  else if (Dungeon.skins == 3) {

			(hero.belongings.weapon = new Spear()).identify();
			(hero.belongings.armor = new DiscArmor()).identify();
			new MissileShield().collect();
            new SavageHelmet().collect();

            hero.STR += 2;
            Dungeon.limitedDrops.strengthPotions.count+=2;

		} else {
			(hero.belongings.weapon = new ShortSword()).identify();
			(hero.belongings.armor = new WoodenArmor()).identify();
			new MissileShield().collect();
			new Powerpill().collect();
			new Smashpill().collect();
			new Hardpill().collect();
			new JumpW().collect();
	}

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
			hero.HT-=10;
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

		} else {
			(hero.belongings.weapon = new MageBook()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();
			new WandOfMagicMissile().identify().collect();
			new WandOfDisintegration().identify().collect();
			new PotionOfMage().identify().collect();
			new JumpM().collect();

		}
		hero.magicSkill = hero.magicSkill + 3;
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
				hero.HT-=20;
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

		} else {

			(hero.belongings.weapon = new Dagger()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			CloakOfShadows cloak = new CloakOfShadows();
			(hero.belongings.misc1 = cloak).identify();
			hero.belongings.misc1.activate(hero);

			new Smoke(3).identify().collect();
			new JumpR().collect();
		}

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
			(hero.belongings.misc1 = th).identify();
			hero.belongings.misc1.activate(hero);

			new ManyKnive().upgrade(1).identify().collect();

			EscapeKnive knife = new EscapeKnive(5);
			knife.identify().collect();
			new JumpH().collect();
		} else if (Dungeon.skins == 2) {
			hero.HT-=10;
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

		} else {
			(hero.belongings.weapon = new Knuckles()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();

			Boomerang boomerang = new Boomerang(null);
			boomerang.identify().collect();
			EmpBola empbola = new EmpBola(3);
			empbola.identify().collect();
			new JumpH().collect();
		}
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
			hero.HT-=10;
			hero.magicSkill+=3;
			hero.evadeSkill+=5;
			(hero.belongings.weapon = new EmptyPotion()).identify();
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
				hero.HT+=5;
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

		} else {

			(hero.belongings.weapon = new Sling()).identify();
			(hero.belongings.armor = new VestArmor()).identify();

			new GunOfSoldier().identify().collect();

		    new JumpS().collect();
			EscapeKnive knife = new EscapeKnive(3);
			knife.identify().collect();
		}

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
			Dungeon.limitedDrops.strengthPotions.count += 6;
			new JumpF().collect();
		}else if (Dungeon.skins == 2) {
			(hero.belongings.weapon = new Dagger()).upgrade(2).identify();
			(hero.belongings.armor = new VestArmor()).identify();
			Pylon pylon = new Pylon();
			(hero.belongings.misc1 = pylon).identify();
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

		}  else {

			(hero.belongings.weapon = new WoodenStaff()).identify();
			(hero.belongings.armor = new ClothArmor()).identify();
			new PotionOfHealing().identify().collect();
			new FaithSign().identify().collect();
			new JumpF().collect();
		}

		new ScrollOfTerror().setKnown();
        new PotionOfHealing().setKnown();

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
