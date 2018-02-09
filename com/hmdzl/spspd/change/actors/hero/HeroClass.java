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
import com.hmdzl.spspd.change.actors.mobs.Gnoll;
import com.hmdzl.spspd.change.items.BossRush;
import com.hmdzl.spspd.change.items.artifacts.EtherealChains;
import com.hmdzl.spspd.change.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.change.items.misc.Ankhshield;
import com.hmdzl.spspd.change.items.TomeOfMastery;
import com.hmdzl.spspd.change.items.armor.ClothArmor;
import com.hmdzl.spspd.change.items.artifacts.CloakOfShadows;
import com.hmdzl.spspd.change.items.bags.KeyRing;
import com.hmdzl.spspd.change.items.eggs.RandomEgg;
import com.hmdzl.spspd.change.items.food.Food;
import com.hmdzl.spspd.change.items.food.PetFood;
import com.hmdzl.spspd.change.items.journalpages.JournalPage;
import com.hmdzl.spspd.change.items.journalpages.Sokoban1;
import com.hmdzl.spspd.change.items.journalpages.Sokoban2;
import com.hmdzl.spspd.change.items.journalpages.Sokoban3;
import com.hmdzl.spspd.change.items.journalpages.Sokoban4;
import com.hmdzl.spspd.change.items.journalpages.Town;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.items.potions.PotionOfMindVision;
import com.hmdzl.spspd.change.items.potions.PotionOfStrength;
import com.hmdzl.spspd.change.items.quest.GnollClothes;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfIdentify;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.items.wands.WandOfAcid;
import com.hmdzl.spspd.change.items.wands.WandOfAvalanche;
import com.hmdzl.spspd.change.items.wands.WandOfBlood;
import com.hmdzl.spspd.change.items.wands.WandOfCharm;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.change.items.wands.WandOfFlock;
import com.hmdzl.spspd.change.items.wands.WandOfFlow;
import com.hmdzl.spspd.change.items.wands.WandOfFreeze;
import com.hmdzl.spspd.change.items.wands.WandOfLight;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.items.wands.WandOfMagicMissile;
import com.hmdzl.spspd.change.items.wands.WandOfPoison;
import com.hmdzl.spspd.change.items.weapon.melee.Club;
import com.hmdzl.spspd.change.items.weapon.melee.Dagger;
import com.hmdzl.spspd.change.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.change.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.change.items.weapon.melee.Lance;
import com.hmdzl.spspd.change.items.weapon.melee.Rapier;
import com.hmdzl.spspd.change.items.weapon.melee.Scimitar;
import com.hmdzl.spspd.change.items.weapon.melee.ShortSword;
import com.hmdzl.spspd.change.items.weapon.melee.MageBook;
import com.hmdzl.spspd.change.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.Boomerang;
import com.hmdzl.spspd.change.items.weapon.missiles.ErrorT;
import com.hmdzl.spspd.change.items.weapon.missiles.Knive;
import com.hmdzl.spspd.change.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.change.items.armor.ErrorArmor;
import com.hmdzl.spspd.change.items.wands.WandOfError;
import com.hmdzl.spspd.change.items.misc.JumpW;
import com.hmdzl.spspd.change.items.misc.JumpH;
import com.hmdzl.spspd.change.items.misc.JumpM;
import com.hmdzl.spspd.change.items.misc.JumpR;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.messages.Messages;

public enum HeroClass {

	WARRIOR( "warrior" , "warrior_name"),
	MAGE( "mage", "mage_name"),
	ROGUE( "rogue","rogue_name" ),
	HUNTRESS( "huntress", "huntress_name");

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
		}

		/*if (Badges.isUnlocked(masteryBadge())) {
			new TomeOfMastery().collect();
		}*/
		
		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {
		if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
			(hero.belongings.armor = new ClothArmor()).identify();

		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
			new Food().identify().collect();
		
		if (Dungeon.isChallenged(Challenges.NO_ARMOR))
			(hero.belongings.armor = new ErrorArmor()).identify();
		
		if (Dungeon.isChallenged(Challenges.BOSSRUSH)){
		//OtilukesJournal jn = new OtilukesJournal(); jn.collect();
			//TestWeapon tw = new TestWeapon(); tw.collect();
		   JournalPage town = new Town(); town.collect();
			//GnollClothes clothes = new GnollClothes(); clothes.collect();
		   /*PotionOfMight PoM = new PotionOfMight();PoM.identify().collect();
			PotionOfMight PoM1 = new PotionOfMight(); PoM1.identify().collect();
			PotionOfMight PoM2 = new PotionOfMight();PoM2.identify().collect();
			PotionOfMight PoM3 = new PotionOfMight(); PoM3.identify().collect();*/

			/*PotionOfStrength PoM4 = new PotionOfStrength();PoM4.identify().collect();
			PotionOfStrength PoM5 = new PotionOfStrength(); PoM5.identify().collect();
			PotionOfStrength PoM6 = new PotionOfStrength();PoM6.identify().collect();
			PotionOfStrength PoM7 = new PotionOfStrength(); PoM7.identify().collect();*/

			//OrbOfZot zot = new OrbOfZot(); zot.collect();
		ScrollOfMagicMapping scroll = new ScrollOfMagicMapping();
		scroll.identify().collect();
			//EtherealChains chains = new EtherealChains(); chains.collect();
			//Wand wand = new WandOfDisintegration(); wand.upgrade(2).collect();
			//Wand wand2 = new WandOfFlock(); wand2.upgrade(2).collect();
			//Wand wand3 = new WandOfFlow (); wand3.upgrade(2).collect();
			//Wand wand4 = new WandOfAcid(); wand4.upgrade(2).collect();
			//Wand wand5 = new WandOfBlood(); wand5.upgrade(2).collect();
			//Wand wand6 = new WandOfFirebolt(); wand6.upgrade(2).collect();
			//Wand wand7 = new WandOfLightning(); wand7.upgrade(2).collect();
			//Wand wand8 = new WandOfAvalanche();wand8.upgrade(2).collect();
			//Wand wand9 = new WandOfCharm();wand9.upgrade(2).collect();
			//Wand wand10 = new WandOfLight();wand10.upgrade(2).collect();
			//Wand wand11 = new WandOfMagicMissile();wand11.upgrade(2).collect();
			//Wand wand12 = new WandOfPoison();wand12.upgrade(2).collect();
            //Wand wand13 = new WandOfFreeze();wand13.upgrade(2).collect();

		BossRush BR = new BossRush(); BR.collect();
		JournalPage sk1 = new Sokoban1(); sk1.collect();
		//JournalPage sk2 = new Sokoban2(); sk2.collect();
		//JournalPage sk3 = new Sokoban3(); sk3.collect();
		//JournalPage sk4 = new Sokoban4(); sk4.collect();
			//Lance lance = new Lance();lance.collect();
			//Scimitar sci = new Scimitar();sci.upgrade(3).collect();
			//FightGloves FG = new FightGloves();FG.collect();
			//Rapier rapier = new Rapier();rapier.collect();
			//Club club = new Club(); club.collect();
			/*BlandfruitBush.Seed Seed1 = new BlandfruitBush.Seed(); Seed1.collect();
			Blindweed.Seed Seed2 = new Blindweed.Seed(); Seed2.collect();*/
			//Dewcatcher.Seed Seed3 = new Dewcatcher.Seed(); Seed3.collect();
			/*Dreamfoil.Seed Seed4 = new Dreamfoil.Seed(); Seed4.collect();
			Earthroot.Seed Seed5 = new Earthroot.Seed(); Seed5.collect();
			Fadeleaf.Seed Seed6 = new Fadeleaf.Seed(); Seed6.collect();
			Firebloom.Seed Seed7 = new Firebloom.Seed(); Seed7.collect();
			Flytrap.Seed Seed8 = new Flytrap.Seed(); Seed8.collect();
			Icecap.Seed Seed9 = new Icecap.Seed(); Seed9.collect();
			Phaseshift.Seed Seed10 = new Phaseshift.Seed(); Seed10.collect();
			Seedpod.Seed Seed11 = new Seedpod.Seed(); Seed11.collect();
			Sorrowmoss.Seed Seed12 = new Sorrowmoss.Seed(); Seed12.collect();
			Starflower.Seed Seed13 = new Starflower.Seed(); Seed13.collect();
			Stormvine.Seed Seed14 = new Stormvine.Seed(); Seed14.collect();
			Sungrass.Seed Seed15 = new Sungrass.Seed(); Seed15.collect();*/
        //AlchemistsToolkit af1 = new AlchemistsToolkit(); af1.collect();
		//CapeOfThorns af2 = new CapeOfThorns(); af2.collect();
		//ChaliceOfBlood af3 = new ChaliceOfBlood(); af3.collect();
		//DriedRose af4 = new DriedRose(); af4.collect();
		//EyeOfSkadi af5 = new EyeOfSkadi(); af5.collect();
		//HornOfPlenty af6 = new HornOfPlenty(); af6.collect();
		//MasterThievesArmband af7 = new MasterThievesArmband(); af7.collect();
		//RingOfDisintegration af8 = new RingOfDisintegration(); af8.collect();
		//SandalsOfNature af9 = new SandalsOfNature(); af9.collect();
		//TimekeepersHourglass af10 = new TimekeepersHourglass(); af10.collect();
		//TalismanOfForesight af11 = new TalismanOfForesight(); af11.collect();
		//UnstableSpellbook af12 = new UnstableSpellbook(); af12.collect();

		Dungeon.gold = 10000;
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
		}
		return null;
	}

	private static void initWarrior(Hero hero) {

		(hero.belongings.weapon = new ShortSword()).identify();
		Knive darts = new Knive(8);
		darts.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();
		
		JumpW JW = new JumpW(); JW.collect();

		RandomEgg egg = new RandomEgg(); egg.collect();
		PetFood pfood = new PetFood(); pfood.collect();
		Ankhshield shield = new Ankhshield(); shield.collect();
		
		if (Random.Int(10) < 2){
		ErrorArmor ea = new ErrorArmor(); 
		ea.identify().collect();
		}
		
		new PotionOfStrength().setKnown();
	}

	private static void initMage(Hero hero) {
		(hero.belongings.weapon = new MageBook()).identify();

		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();

		//WandOfFreeze wand2 = new WandOfFreeze();
		//wand2.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		JumpM JM = new JumpM(); JM.collect();

		RandomEgg egg = new RandomEgg(); egg.collect();
		PetFood pfood = new PetFood(); pfood.collect();
		Ankhshield shield = new Ankhshield(); shield.collect();
		
		if (Random.Int(10) < 2){
		WandOfError woe = new WandOfError(); 
		woe.identify().collect();
		}
		
		new ScrollOfIdentify().setKnown();
	
	}

	private static void initRogue(Hero hero) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate(hero);

		Knive darts = new Knive(10);
		darts.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		JumpR JR = new JumpR(); JR.collect();

		RandomEgg egg = new RandomEgg(); egg.collect();
		PetFood pfood = new PetFood(); pfood.collect();
		Ankhshield shield = new Ankhshield(); shield.collect();
				
		if (Random.Int(10) < 2){
		ErrorW ew = new ErrorW(); 
		ew.identify().collect();
		}

		new ScrollOfMagicMapping().setKnown();		
	}

	private static void initHuntress(Hero hero) {
		(hero.belongings.weapon = new Knuckles()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		JumpH JH = new JumpH(); JH.collect();

		RandomEgg egg = new RandomEgg(); egg.collect();
		PetFood pfood = new PetFood(); pfood.collect();
		Ankhshield shield = new Ankhshield(); shield.collect();

		if (Random.Int(10) < 2){
		ErrorT et = new ErrorT(); 
		et.identify().collect();
		}
		
		new PotionOfMindVision().setKnown();	
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
					Messages.get(HeroClass.class, "rogue_perk6"),
			};
		case HUNTRESS:
			return new String[]{
					Messages.get(HeroClass.class, "huntress_perk1"),
					Messages.get(HeroClass.class, "huntress_perk2"),
					Messages.get(HeroClass.class, "huntress_perk3"),
					Messages.get(HeroClass.class, "huntress_perk4"),
					Messages.get(HeroClass.class, "huntress_perk5"),
			};
		}
		return null;
	}

	private static final String CLASS	= "class";
	
	public void playtest(Hero hero) {
		if (!Dungeon.playtest){
		    //Playtest
		    //TomeOfMastery tome = new TomeOfMastery(); tome.collect();
				
			//hero.HT=hero.HP=999;
			//hero.STR = hero.STR + 20;
			//PlateArmor armor1 = new PlateArmor();
		    //armor1.reinforce().upgrade(140).identify().collect();
		    //PlateArmor armor2 = new PlateArmor();
		    //armor2.upgrade(14).identify().collect();
		    //WarHammer hammer = new WarHammer();
		   //hammer.reinforce().upgrade(115).identify().collect();
		    //Spectacles specs = new Spectacles(); specs.collect();
		    //Whistle whistle = new Whistle(); whistle.collect();
		    //Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
		    //Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
		    //Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
		    //Starflower.Seed seed4 = new Starflower.Seed(); seed4.collect();
			//BlueNornStone stone1 = new BlueNornStone(); stone1.collect();
			//YellowNornStone stone2 = new YellowNornStone(); stone2.collect();
			//PurpleNornStone stone3 = new PurpleNornStone(); stone3.collect();
			//PotionBandolier bag1 = new PotionBandolier(); bag1.collect();
			//ScrollHolder bag2 = new ScrollHolder(); bag2.collect();
			//OrbOfZot zot = new OrbOfZot(); zot.collect();
			//Wand wand = new WandOfDisintegration(); wand.upgrade(50); wand.collect();
		    //Wand wand3 = new WandOfFlock(); wand3.upgrade(15); wand3.collect();
			//Wand wand2 = new WandOfTelekinesis(); wand2.upgrade(15); wand2.collect();
		    //Wand wand3 = new WandOfAcid(); wand3.upgrade(15); wand3.collect();
			//Wand wand4 = new WandOfBlood(); wand4.upgrade(15); wand4.collect();
			//Wand wand2 = new WandOfFirebolt(); wand2.upgrade(15); wand2.collect();
			//Wand wand3 = new WandOfLightning(); wand3.upgrade(15); wand3.collect();
			//WandOfFreeze wand2 = new WandOfFreeze();wand2.identify().collect();
			//Ring ring = new RingOfHaste(); ring.upgrade(25); ring.collect();
			//ConchShell conch = new ConchShell(); conch.collect();
			//AncientCoin coin = new AncientCoin(); coin.collect();
			//TenguKey key = new TenguKey(); key.collect();
			//OtilukesJournal jn = new OtilukesJournal(); jn.collect();
			//JournalPage sk1 = new Sokoban1(); sk1.collect();
			//JournalPage sk2 = new Sokoban2(); sk2.collect();
			//JournalPage sk3 = new Sokoban3(); sk3.collect();
			//JournalPage sk4 = new Sokoban4(); sk4.collect();
			//JournalPage town = new Town(); town.collect();
			//JournalPage cave = new DragonCave(); cave.collect();
			//NeptunusTrident trident = new NeptunusTrident(); trident.enchantNeptune(); trident.upgrade(200); trident.collect();
			//CromCruachAxe axe = new CromCruachAxe(); axe.enchantLuck(); axe.collect();
			//AresSword sword = new AresSword(); sword.enchantAres(); sword.collect();
			//JupitersWraith wraith = new JupitersWraith(); wraith.enchantJupiter(); wraith.collect();
			//LokisFlail flail = new LokisFlail(); flail.enchantLoki(); flail.collect();
			//JournalPage sk5 = new Town(); sk5.collect();
			//Wand wand = new WandOfCharm(); wand.upgrade(15); wand.collect();
			//Bone bone = new Bone(); bone.collect();
			//ConchShell conch = new ConchShell(); conch.collect();
			//AncientCoin coin = new AncientCoin(); coin.collect();
			//TenguKey key = new TenguKey(); key.collect();
			//CavesKey key2 = new CavesKey(); key2.collect();
			//TriForce san = new TriForce(); san.collect();
			//PowerTrial lbook = new PowerTrial(); lbook.collect();
			//CourageTrial dbook = new CourageTrial(); dbook.collect();
			//ReturnBeacon beacon = new ReturnBeacon(); beacon.collect();
			//TriforceOfCourage san = new TriforceOfCourage(); san.collect();	
			//Blueberry berry = new Blueberry(60); berry.collect();
			//PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			//Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
			//ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
			//ActiveMrDestructo2 mrd2 = new ActiveMrDestructo2(); mrd2.collect();
			//RingOfDisintegration ar = new RingOfDisintegration(); ar.collect();
		    //PotionOfFrost pot = new PotionOfFrost(); pot.collect();
		    //SteelHoneypot hpot = new SteelHoneypot(); hpot.collect();
			//Egg egg = new Egg(); egg.collect();
			//EasterEgg egg2 = new EasterEgg(); egg2.collect();
			//ShadowDragonEgg egg3 = new ShadowDragonEgg(); egg3.collect();
			//GoldenSkeletonKey key = new GoldenSkeletonKey(0); key.collect(); 
			//Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
			//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
			//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
			//BlandfruitBush.Seed seed4 = new BlandfruitBush.Seed(); seed4.collect();

			//Handcannon saw = new Handcannon(); saw.enchantBuzz(); saw.collect();
			//PotionBandolier bag1 = new PotionBandolier(); bag1.collect();
			//ScrollHolder bag2 = new ScrollHolder(); bag2.collect();
			//AnkhChain chain = new AnkhChain(); chain.collect();
			//WandHolster holster = new WandHolster(); holster.collect();
			//AutoPotion apot = new AutoPotion(); apot.collect();
			//AdamantArmor aArmor = new AdamantArmor(); aArmor.collect();
			//AdamantWand aWand = new AdamantWand(); aWand.collect();
			//AdamantRing aRing = new AdamantRing(); aRing.collect();
			//AdamantWeapon aWeapon = new AdamantWeapon(); aWeapon.collect();
			//PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
				
		    //PuddingCup cup = new PuddingCup(); cup.collect();
			
            ErrorW ew = new ErrorW(); 
		    ew.identify().collect();
		    ErrorArmor ea = new ErrorArmor(); 
		    ea.identify().collect();
		    WandOfError woe = new WandOfError(); 
		    woe.identify().collect();
			ErrorT et = new ErrorT(); 
		    et.identify().collect();
			
			Dungeon.playtest=true;
			GLog.i("Playtest Activated");
				
		 		/*for(int i=0; i<199; i++){
					Scroll scroll = new ScrollOfMagicalInfusion();
			        scroll.identify().collect();
			        Scroll scroll2 = new ScrollOfUpgrade();
			        scroll2.identify().collect();  
			       
			        Scroll scroll3 = new ScrollOfIdentify();
			        scroll3.identify().collect();  
			        Scroll scroll4 = new ScrollOfRemoveCurse();
			        scroll4.identify().collect(); 
			        Scroll scroll5 = new ScrollOfPsionicBlast();
			        scroll5.identify().collect(); 
			        
			        hero.earnExp(hero.maxExp() - hero.exp);
			    }*/	
				
				/*
				for(int i=1; i<61; i++){
			     PotionOfExperience potion1 = new PotionOfExperience(); potion1.collect();
			     PotionOfInvisibility potion2 = new PotionOfInvisibility(); potion2.collect();
			     PotionOfHealing potion3 = new PotionOfHealing(); potion3.collect();
			     PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			     PotionOfLevitation potion6 = new PotionOfLevitation(); potion6.collect();
			     //PotionOfFrost potion6 = new PotionOfFrost(); potion6.collect();
			     PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
			     // Bomb bomb = new Bomb(); bomb.collect();
			     //DarkGold darkgold = new DarkGold(); darkgold.collect();
			    }
				*/
				
		}
		
				/*

				  Blueberry berry = new Blueberry(10); berry.collect();
				  ClusterBomb cbomb = new ClusterBomb(); cbomb.collect();
				  DizzyBomb dbomb = new DizzyBomb(); dbomb.collect();
				  SmartBomb smbomb = new SmartBomb(); smbomb.collect();
				  SeekingBombItem sbomb = new SeekingBombItem(); sbomb.collect();
				  SeekingClusterBombItem scbomb = new SeekingClusterBombItem(); scbomb.collect();
				  
				  
				//  Bomb bomb = new Bomb(); bomb.collect();
				//DeathCap mush1 = new DeathCap(); mush1.collect();
				//GoldenJelly mush2 = new GoldenJelly(); mush2.collect();
				//BlueMilk mush3 = new BlueMilk(); mush3.collect();
				//JackOLantern mush4 = new JackOLantern(); mush4.collect();
				//Earthstar mush5 = new Earthstar(); mush5.collect();
				//Lichen mush6 = new Lichen(); mush6.collect();
				//PixieParasol mush7 = new PixieParasol(); mush7.collect();
				
				ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
				//OrbOfZot orb = new OrbOfZot(); orb.collect();
				        
				//Phaseshift.Seed seed = new Phaseshift.Seed(); seed.collect();
				//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
				//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
				
								
				//PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
				//CourageTrial dbook = new CourageTrial(); dbook.collect();
				///PowerTrial lbook = new PowerTrial(); lbook.collect();
				//WisdomTrial tbook = new WisdomTrial(); tbook.collect();
				//TriForce san = new TriForce(); san.collect();	
				
		        //SewersKey key1 = new SewersKey(); key1.collect();
		        //PrisonKey key2 = new PrisonKey(); key2.collect();
		        //CavesKey key3 = new CavesKey(); key3.collect();
		        //CityKey key4 = new CityKey(); key4.collect();
		      // HallsKey key5 = new HallsKey(); key5.collect();
		        FullMoonberry berry2 = new FullMoonberry(10); berry2.collect();		
*/
	}
	
	public void storeInBundle(Bundle bundle) {
		bundle.put(CLASS, toString());
	}

	public static HeroClass restoreInBundle(Bundle bundle) {
		String value = bundle.getString(CLASS);
		return value.length() > 0 ? valueOf(value) : ROGUE;
	}


}
