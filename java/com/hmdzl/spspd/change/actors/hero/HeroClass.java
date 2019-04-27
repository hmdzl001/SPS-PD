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
import com.hmdzl.spspd.change.items.ChallengeBook;
import com.hmdzl.spspd.change.items.Elevator;
import com.hmdzl.spspd.change.items.bombs.EarthBomb;
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
import com.hmdzl.spspd.change.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.change.items.summon.CallCoconut;
import com.hmdzl.spspd.change.items.summon.Honeypot;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.armor.normalarmor.VestArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.change.items.bags.ScrollHolder;
import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.items.food.completefood.Hardpill;
import com.hmdzl.spspd.change.items.food.completefood.Honey;
import com.hmdzl.spspd.change.items.food.completefood.Powerpill;
import com.hmdzl.spspd.change.items.food.completefood.Smashpill;
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
import com.hmdzl.spspd.change.items.misc.FourClover;
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
import com.hmdzl.spspd.change.items.summon.Mobile;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.wands.WandOfFlock;
import com.hmdzl.spspd.change.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.change.items.wands.WandOfTCloud;
import com.hmdzl.spspd.change.items.weapon.guns.Sling;
import com.hmdzl.spspd.change.items.weapon.melee.Dagger;
import com.hmdzl.spspd.change.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.change.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.change.items.weapon.melee.MageBook;
import com.hmdzl.spspd.change.items.weapon.melee.Triangolo;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.EmpBola;
import com.hmdzl.spspd.change.items.weapon.missiles.ErrorAmmo;
import com.hmdzl.spspd.change.items.misc.JumpW;
import com.hmdzl.spspd.change.items.misc.JumpH;
import com.hmdzl.spspd.change.items.misc.JumpM;
import com.hmdzl.spspd.change.items.misc.JumpR;
import com.hmdzl.spspd.change.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.change.items.weapon.missiles.MissileShield;
import com.hmdzl.spspd.change.items.weapon.missiles.PocketBall;
import com.hmdzl.spspd.change.items.weapon.missiles.Smoke;
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
	SOLDIER( "soldier", "soldier_name");

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
		}			
		
		/*if (Badges.isUnlocked(masteryBadge())) {
			new TomeOfMastery().collect();
		}*/
		
		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {

		new NormalRation().identify().collect();
		new PocketBall().collect();
		new KeyRing().collect();
		new RandomEgg().collect();
		new PetFood().collect();
		new Ankhshield().collect();
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
			//hero.STR = hero.STR + 10;
			//JupitersWraith JW = new JupitersWraith(); JW.identify().collect();
		//DolyaSlate jn = new DolyaSlate(); jn.collect();
			//Torch torch = new Torch();torch.collect();
         //  new Sling().identify().collect();
           //new HeavyAmmo().identify().collect();
           //new HeavyAmmo().identify().collect();
		    //TestWeapon tw = new TestWeapon(); tw.collect();
			Elevator elevator = new Elevator(); elevator.collect();
			//ArmorKit kit = new ArmorKit();kit.collect();
			JournalPage saveroom = new SafeSpotPage(); saveroom.collect();
		    JournalPage town = new Town(); town.collect();
			//ChallengeBook cbook = new ChallengeBook(); cbook.collect();
			//ChallengeList cl1 = new SewerChallenge(); cl1.collect();
			//ChallengeList cl2 = new PrisonChallenge(); cl2.collect();
			//ChallengeList cl3 = new CaveChallenge(); cl3.collect();
			//ChallengeList cl4 = new CityChallenge(); cl4.collect();
			ScrollHolder sh = new ScrollHolder(); sh.collect();
			JournalPage sk1 = new Sokoban1(); sk1.collect();
			JournalPage sk2 = new Sokoban2(); sk2.collect();
			JournalPage sk3 = new Sokoban3(); sk3.collect();
			JournalPage sk4 = new Sokoban4(); sk4.collect();
			//JournalPage sk5 = new Vault(); sk5.collect();
			//ChallengeList cl5 = new CourageChallenge(); cl5.collect();
			//ChallengeList cl6 = new PowerChallenge(); cl6.collect();
			//ChallengeList cl7 = new WisdomChallenge(); cl7.collect();
			//new MachineArmor().identify().collect();
			//VestArmor va = new VestArmor();va.identify().collect();
			//new SoulCollect().collect();
			//new FourClover().collect();
			//new ToyGun().collect();
			//new Goei().collect();
			//new Palantir().collect();
			//RingOfEnergy ros = new RingOfEnergy();
			//ros.upgrade(10).collect();
			ErrorAmmo ammo = new ErrorAmmo(20);
			ammo.identify().collect();
			//new RingOfHaste().collect();
			//new RingOfTenacity().collect();
			//new RingOfForce().collect();
			TomeOfMastery TOM = new TomeOfMastery();TOM.collect();
			//GnollClothes clothes = new GnollClothes(); clothes.collect();
		   /*PotionOfStrength PoM4 = new PotionOfStrength();PoM4.identify().collect();
			PotionOfStrength PoM5 = new PotionOfStrength(); PoM5.identify().collect();
			PotionOfStrength PoM6 = new PotionOfStrength();PoM6.identify().collect();
			PotionOfStrength PoM7 = new PotionOfStrength(); PoM7.identify().collect();*/

			//OrbOfZot zot = new OrbOfZot(); zot.collect();

			//AresSword aresSword = new AresSword();aresSword.identify().collect();
		for(int i=0; i<199; i++){
			Scroll scroll2 = new ScrollOfMagicalInfusion();
			scroll2.identify().collect();
			Scroll scroll3 = new ScrollOfUpgrade();
			scroll3.identify().collect();
			Scroll scroll4 = new ScrollOfIdentify();
			scroll4.identify().collect();
			PotionOfExperience PoE = new PotionOfExperience();PoE.identify().collect();
			ScrollOfMagicMapping scroll = new ScrollOfMagicMapping();scroll.identify().collect();
			new EarthBomb().collect();
			new Pasty().collect();
			new Honeypot().collect();
			new Mobile().collect();
			new CallCoconut().collect();
			new ActiveMrDestructo().collect();
			//new PotionOfMending().identify().collect();
			//new Bomb().collect();
			//PotionOfStrength PoM = new PotionOfStrength();PoM.identify().collect();
			//new ToastedNut().collect();
	   }
			for(int i=0; i<10; i++){
				//PocketBall pball = new PocketBall();pball.collect();
				Seedpod.Seed Seed11 = new Seedpod.Seed(); Seed11.collect();
				//Flytrap.Seed Seed3 = new Flytrap.Seed(); Seed3.collect();
				//Honeypot hpot = new Honeypot(); hpot.collect();
				//WaterItem water = new WaterItem(); water.collect();
				//StoneOre ore = new StoneOre();ore.collect();
				//Bomb bomb = new Bomb(); bomb.collect();
				//RandomEgg egg = new RandomEgg(); egg.collect();
				//StoneOre ore = new StoneOre(); ore.collect();
				//PotionOfMight PoM = new PotionOfMight();PoM.identify().collect();

				//PotionOfHealing PoH = new PotionOfHealing();PoH.identify().collect();
				//PotionOfExperience PoE = new PotionOfExperience();PoE.identify().collect();
				//PotionOfLiquidFlame PoF = new PotionOfLiquidFlame();PoF.identify().collect();
				//PotionOfLevitation PoL = new PotionOfLevitation();PoL.identify().collect();
				//PotionOfToxicGas PoT = new PotionOfToxicGas();PoT.identify().collect();
				//PotionOfMindVision PoM = new PotionOfMindVision();PoM.identify().collect();
				//PotionOfParalyticGas PoPa = new PotionOfParalyticGas();PoPa.identify().collect();
				//PotionOfPurity PoP = new PotionOfPurity();PoP.identify().collect();
				//PotionOfInvisibility PoI = new PotionOfInvisibility();PoI.identify().collect();
				//PotionOfFrost PoIce = new PotionOfFrost();PoIce.identify().collect();
				//PotionOfMending PoMend = new PotionOfMending();PoMend.identify().collect();
				//ScrollOfMirrorImage somi = new ScrollOfMirrorImage(); somi.identify().collect();

			}
			//RobotDMT dnt = new RobotDMT(); dnt.collect();
			//RobotDMT dnt2 = new RobotDMT(); dnt2.upgrade(8).collect();
			//EtherealChains chains = new EtherealChains(); chains.upgrade(5).collect();
			//Wand wand = new WandOfDisintegration(); wand.upgrade(2).collect();
			Wand wand2 = new WandOfFlock(); wand2.upgrade(10).collect();
			//Wand wand3 = new WandOfFlow(); wand3.upgrade(10).collect();
			//Wand wand4 = new WandOfAcid(); wand4.upgrade(2).collect();
			//Wand wand5 = new WandOfBlood(); wand5.upgrade(2).collect();
			//Wand wand6 = new WandOfFirebolt(); wand6.upgrade(2).collect();
			//Wand wand7 = new WandOfLightning(); wand7.upgrade(2).collect();
		    //Wand wand8 = new WandOfMeteorite();wand8.upgrade(2).collect();
			//Wand wand9 = new WandOfCharm();wand9.upgrade(2).collect();
			//Wand wand10 = new WandOfLight();wand10.upgrade(2).collect();
			//Wand wand11 = new WandOfMagicMissile();wand11.upgrade(2).collect();
			//Wand wand12 = new WandOfPoison();wand12.upgrade(2).collect();
            Wand wand13 = new WandOfTCloud();wand13.upgrade(10).collect();
            new SewerReward().collect();
			//new PrisonReward().collect();
			//new CaveReward().collect();
			//new CityReward().collect();
		//BossRush BR = new BossRush(); BR.collect();
			//Lance lance = new Lance();lance.collect();
			//Scimitar sci = new Scimitar();sci.upgrade(3).collect();
			//FightGloves FG = new FightGloves();FG.collect();
			//Rapier rapier = new Rapier();rapier.collect();
			//Club club = new Club(); club.collect();
			/*BlandfruitBush.Seed Seed1 = new BlandfruitBush.Seed(); Seed1.collect();
			Blindweed.Seed Seed2 = new Blindweed.Seed(); Seed2.collect();*/
			/*Dreamfoil.Seed Seed4 = new Dreamfoil.Seed(); Seed4.collect();
			Earthroot.Seed Seed5 = new Earthroot.Seed(); Seed5.collect();
			Fadeleaf.Seed Seed6 = new Fadeleaf.Seed(); Seed6.collect();
			Firebloom.Seed Seed7 = new Firebloom.Seed(); Seed7.collect();
			Flytrap.Seed Seed8 = new Flytrap.Seed(); Seed8.collect();
			Icecap.Seed Seed9 = new Icecap.Seed(); Seed9.collect();
			Phaseshift.Seed Seed10 = new Phaseshift.Seed(); Seed10.collect();
			Sorrowmoss.Seed Seed12 = new Sorrowmoss.Seed(); Seed12.collect();
			Starflower.Seed Seed13 = new Starflower.Seed(); Seed13.collect();
			Stormvine.Seed Seed14 = new Stormvine.Seed(); Seed14.collect();
			Sungrass.Seed Seed15 = new Sungrass.Seed(); Seed15.collect();*/
      // AlchemistsToolkit af1 = new AlchemistsToolkit();af1.upgrade(0).collect();
		//CapeOfThorns af2 = new CapeOfThorns(); af2.upgrade(10).collect();
		//ChaliceOfBlood af3 = new ChaliceOfBlood(); af3.upgrade(10).collect();
		//DriedRose af4 = new DriedRose(); af4.upgrade(10).collect();

		//EyeOfSkadi af5 = new EyeOfSkadi(); af5.upgrade(10).collect();
		//HornOfPlenty af6 = new HornOfPlenty(); af6.upgrade(10).collect();
		//MasterThievesArmband af7 = new MasterThievesArmband(); af7.upgrade(10).collect();
		//GlassTotem af8 = new GlassTotem(); af8.collect();
		//SandalsOfNature af9 = new SandalsOfNature(); af9.upgrade(10).collect();
		//TimekeepersHourglass af10 = new TimekeepersHourglass(); af10.upgrade(5).collect();
		//TalismanOfForesight af11 = new TalismanOfForesight(); af11.upgrade(10).collect();
		//CloakOfShadows af13 = new CloakOfShadows(); af13.upgrade(10).collect();
		//UnstableSpellbook af12 = new UnstableSpellbook(); af12.upgrade(10).collect();
			new RingOfForce().collect();
			new RingOfTenacity().collect();
			new RingOfHaste().collect();
			new RingOfFuror().collect();
			new RingOfMight().collect();
			new RingOfMagic().collect();
			new RingOfElements().collect();
			new RingOfEnergy().collect();
			new RingOfEvasion().collect();
			new RingOfAccuracy().collect();
			new RingOfSharpshooting().collect();
		Dungeon.gold = 10000;
		//Dungeon.gold = 10000000;
		//hero.HT=hero.HP=10000;
		//hero.STR = hero.STR + 20;
		Dungeon.depth = 14;
			//Statistics.archersKilled = 101;
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
		}

		return null;
	}


	private static void initWarrior(Hero hero) {

		(hero.belongings.weapon = new ShortSword()).identify();
		(hero.belongings.armor = new WoodenArmor()).identify();
		MissileShield missileShield = new MissileShield(); missileShield.collect();
		JumpW JW = new JumpW(); JW.collect();

		new Powerpill().collect();
		new Smashpill().collect();
		new Hardpill().collect();
	/*if (Random.Int(10) < 1){
		ErrorArmor ea = new ErrorArmor(); 
		ea.identify().collect();
		}*/
		new PotionOfStrength().setKnown();
		new ScrollOfUpgrade().setKnown();
	}

	private static void initMage(Hero hero) {
		(hero.belongings.weapon = new MageBook()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();

		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		WandOfDisintegration wand2 = new WandOfDisintegration();
		wand2.identify().collect();

		PotionOfMage pom = new PotionOfMage();
		pom.identify().collect();


		JumpM JM = new JumpM(); JM.collect();
		/*if (Random.Int(10) < 1){
		WandOfError woe = new WandOfError(); 
		woe.identify().collect();
		}*/

		hero.magicSkill = hero.magicSkill + 3;

		new ScrollOfIdentify().setKnown();
		new PotionOfLiquidFlame().setKnown();
	
	}

	private static void initRogue(Hero hero) {
		(hero.belongings.weapon = new Dagger()).identify();
		(hero.belongings.armor = new VestArmor()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate(hero);

		Smoke smoke = new Smoke(3);
		smoke.identify().collect();

		JumpR JR = new JumpR(); JR.collect();

				
		/*if (Random.Int(10) < 1){
		ErrorW ew = new ErrorW(); 
		ew.identify().collect();
		}*/
		new ScrollOfMagicMapping().setKnown();
		new PotionOfInvisibility().setKnown();
	}

	private static void initHuntress(Hero hero) {
		(hero.belongings.weapon = new Knuckles()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();

		Boomerang boomerang = new Boomerang(null);
		boomerang.identify().collect();

		JumpH JH = new JumpH(); JH.collect();

		EmpBola empbola = new EmpBola(3);
		empbola.identify().collect();

		/*if (Random.Int(10) < 1){
			ErrorAmmo ammo = new ErrorAmmo(3);
			ammo.identify().collect();
		}*/
		new PotionOfMindVision().setKnown();
		new ScrollOfRemoveCurse().setKnown();
	}

	private static void initPerformer(Hero hero) {
		(hero.belongings.weapon = new Triangolo()).identify();
		(hero.belongings.armor = new ClothArmor()).identify();

		Shovel shovel = new Shovel();
		shovel.identify().collect();

		JumpP JP = new JumpP(); JP.collect();

		new ScrollOfLullaby().collect();
		new DungeonBomb().collect();

		new ScrollOfLullaby().setKnown();
		new PotionOfPurity().setKnown();
	}
	
	private static void initSoldier(Hero hero) {
		(hero.belongings.weapon = new Sling()).identify();
		(hero.belongings.armor = new VestArmor()).identify();

		GunOfSoldier gun_s = new GunOfSoldier();
		gun_s.identify().collect();

		JumpS JS = new JumpS(); JS.collect();

		EscapeKnive knife = new EscapeKnive(3);
		knife.identify().collect();

		new ScrollOfRage().setKnown();
		new PotionOfMending().setKnown();

		hero.hitSkill = hero.hitSkill + 4;
		hero.evadeSkill = hero.evadeSkill + 2;
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
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case WARRIOR:
			return new String[]{
					Messages.get(HeroClass.class, "warrior_perk1"),
					Messages.get(HeroClass.class, "warrior_perk2"),
					Messages.get(HeroClass.class, "warrior_perk3"),
					Messages.get(HeroClass.class, "warrior_perk4"),
					Messages.get(HeroClass.class, "warrior_perk5"),
			};
		case MAGE:
			return new String[]{
					Messages.get(HeroClass.class, "mage_perk1"),
					Messages.get(HeroClass.class, "mage_perk2"),
					Messages.get(HeroClass.class, "mage_perk3"),
					Messages.get(HeroClass.class, "mage_perk4"),
					Messages.get(HeroClass.class, "mage_perk5"),
			};
		case ROGUE:
			return new String[]{
					Messages.get(HeroClass.class, "rogue_perk1"),
					Messages.get(HeroClass.class, "rogue_perk2"),
					Messages.get(HeroClass.class, "rogue_perk3"),
					Messages.get(HeroClass.class, "rogue_perk4"),
					Messages.get(HeroClass.class, "rogue_perk5"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_perk1"),
					Messages.get(HeroClass.class, "huntress_perk2"),
					Messages.get(HeroClass.class, "huntress_perk3"),
					Messages.get(HeroClass.class, "huntress_perk4"),
					Messages.get(HeroClass.class, "huntress_perk5"),
			};
		case PERFORMER:
			return new String[]{
					Messages.get(HeroClass.class, "performer_perk1"),
					Messages.get(HeroClass.class, "performer_perk2"),
					Messages.get(HeroClass.class, "performer_perk3"),
					Messages.get(HeroClass.class, "performer_perk4"),
					Messages.get(HeroClass.class, "performer_perk5"),
			};	
		case SOLDIER:
			return new String[]{
					Messages.get(HeroClass.class, "soldier_perk1"),
					Messages.get(HeroClass.class, "soldier_perk2"),
					Messages.get(HeroClass.class, "soldier_perk3"),
					Messages.get(HeroClass.class, "soldier_perk4"),
					Messages.get(HeroClass.class, "soldier_perk5"),
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
