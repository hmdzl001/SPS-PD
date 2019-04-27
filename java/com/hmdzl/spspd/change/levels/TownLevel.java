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
package com.hmdzl.spspd.change.levels;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Alchemy;
import com.hmdzl.spspd.change.actors.blobs.Alter;
import com.hmdzl.spspd.change.actors.blobs.BedLight;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.actors.mobs.AdultDragonViolet;
import com.hmdzl.spspd.change.actors.mobs.Piranha;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.TestMob;
import com.hmdzl.spspd.change.actors.mobs.TestMob2;
import com.hmdzl.spspd.change.actors.mobs.npcs.ARealMan;
import com.hmdzl.spspd.change.actors.mobs.npcs.Juh9870;
import com.hmdzl.spspd.change.actors.mobs.npcs.Kostis12345;
import com.hmdzl.spspd.change.actors.mobs.npcs.Lynn;
import com.hmdzl.spspd.change.actors.mobs.npcs.OldNewStwist;
import com.hmdzl.spspd.change.actors.mobs.npcs.OtilukeNPC;
import com.hmdzl.spspd.change.actors.mobs.npcs.RENnpc;
import com.hmdzl.spspd.change.actors.mobs.npcs.RainTrainer;
import com.hmdzl.spspd.change.actors.mobs.npcs.SadSaltan;
import com.hmdzl.spspd.change.actors.mobs.npcs.Shopkeeper;
import com.hmdzl.spspd.change.actors.mobs.npcs.Tinkerer4;
import com.hmdzl.spspd.change.actors.mobs.npcs.Tinkerer5;
import com.hmdzl.spspd.change.actors.mobs.npcs.Udawos;
import com.hmdzl.spspd.change.actors.mobs.npcs.TypedScroll;
import com.hmdzl.spspd.change.actors.mobs.npcs.G2159687;
import com.hmdzl.spspd.change.actors.mobs.npcs.ConsideredHamster;
import com.hmdzl.spspd.change.actors.mobs.npcs.NYRDS;
import com.hmdzl.spspd.change.actors.mobs.npcs.Evan;
import com.hmdzl.spspd.change.actors.mobs.npcs.UncleS;
import com.hmdzl.spspd.change.actors.mobs.npcs.Watabou;
import com.hmdzl.spspd.change.actors.mobs.npcs.Bilboldev;
import com.hmdzl.spspd.change.actors.mobs.npcs.HBB;
import com.hmdzl.spspd.change.actors.mobs.npcs.SFB;
import com.hmdzl.spspd.change.actors.mobs.npcs.Jinkeloid;
import com.hmdzl.spspd.change.actors.mobs.npcs.Rustyblade;
import com.hmdzl.spspd.change.actors.mobs.npcs.HeXA;
import com.hmdzl.spspd.change.actors.mobs.npcs.SP931;
import com.hmdzl.spspd.change.actors.mobs.npcs.Lery;
import com.hmdzl.spspd.change.actors.mobs.npcs.Lyn;
import com.hmdzl.spspd.change.actors.mobs.npcs.Coconut;
import com.hmdzl.spspd.change.actors.mobs.npcs.FruitCat;
import com.hmdzl.spspd.change.actors.mobs.npcs.Locastan;
import com.hmdzl.spspd.change.actors.mobs.npcs.Tempest102;
import com.hmdzl.spspd.change.actors.mobs.npcs.Dachhack;
import com.hmdzl.spspd.change.actors.mobs.npcs.MemoryOfSand;
import com.hmdzl.spspd.change.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.change.actors.mobs.npcs.HateSokoban;
import com.hmdzl.spspd.change.actors.mobs.npcs.AliveFish;
import com.hmdzl.spspd.change.actors.mobs.npcs.LaJi;
import com.hmdzl.spspd.change.actors.mobs.npcs.WhiteGhost;
import com.hmdzl.spspd.change.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.Ankh;
import com.hmdzl.spspd.change.items.AdamantArmor;
import com.hmdzl.spspd.change.items.AdamantRing;
import com.hmdzl.spspd.change.items.AdamantWand;
import com.hmdzl.spspd.change.items.AdamantWeapon;
import com.hmdzl.spspd.change.items.food.completefood.PetFood;
import com.hmdzl.spspd.change.items.misc.SkillOfAtk;
import com.hmdzl.spspd.change.items.misc.SkillOfDef;
import com.hmdzl.spspd.change.items.misc.SkillOfMig;
import com.hmdzl.spspd.change.items.quest.DarkGold;


import com.hmdzl.spspd.change.items.summon.Honeypot;



import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.potions.PotionOfHealing;
import com.hmdzl.spspd.change.items.potions.PotionOfMending;
import com.hmdzl.spspd.change.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.change.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.change.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.change.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.change.items.weapon.melee.special.HookHam;
import com.hmdzl.spspd.change.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.change.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.change.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.change.items.weapon.melee.special.Tree;
import com.hmdzl.spspd.change.items.weapon.missiles.MiniMoai;
import com.hmdzl.spspd.change.items.weapon.missiles.PocketBall;
import com.hmdzl.spspd.change.levels.features.Chasm;
import com.hmdzl.spspd.change.levels.features.Door;
import com.hmdzl.spspd.change.levels.features.HighGrass;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Phaseshift;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.plants.Starflower;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Dungeon.level;

public class TownLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
		special=false;
	}
	
		
    public int mineDepth=0;
    
    public int[] scrollspots;
    public int[] storespots;
    public int[] bombpots;
	public int[] foodpots;
	public int[] sppots;
	public int[] eggpots;
	public int[] gnollpots;
	public int[] skillpots;
	
	private static final String MINEDEPTH = "mineDepth";
	private static final String SCROLLSPOTS = "scrollspots";
	private static final String STORESPOTS = "storespots";
	private static final String BOMBPOTS = "bombpots";
	private static final String FOODPOTS = "foodpots";
	private static final String SPPOTS = "sppots";
	private static final String EGGPOTS = "eggpots";
	private static final String GNOLLPOTS = "gnollpots";
	private static final String SKILLPOTS = "skillpots";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(MINEDEPTH, mineDepth);
		bundle.put(SCROLLSPOTS, scrollspots);
		bundle.put(STORESPOTS, storespots);
		bundle.put(BOMBPOTS, bombpots);
		bundle.put(FOODPOTS, foodpots);
		bundle.put(SPPOTS, sppots);
		bundle.put(EGGPOTS, eggpots);
		bundle.put(GNOLLPOTS, gnollpots);
		bundle.put(SKILLPOTS, skillpots);
	}
		
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		mineDepth = bundle.getInt(MINEDEPTH);
		scrollspots = bundle.getIntArray(SCROLLSPOTS);
		storespots = bundle.getIntArray(STORESPOTS);
		bombpots = bundle.getIntArray(BOMBPOTS);
		foodpots = bundle.getIntArray(FOODPOTS);
		sppots = bundle.getIntArray(SPPOTS);
		eggpots = bundle.getIntArray(EGGPOTS);
		gnollpots = bundle.getIntArray(GNOLLPOTS);
		skillpots = bundle.getIntArray(SKILLPOTS);
	}
	
	private boolean checkOtiluke(){
		boolean check=false;
		for (Mob mob : mobs) {
			if (mob instanceof OtilukeNPC ) {
			check=true;	
			}
		}
		return check;
	}

	public void storeStock (){
		
		
		if(Actor.findChar(13 + WIDTH * 10) == null){
			Mob shopkeeper =  new Shopkeeper();
		    shopkeeper.pos = 13 + WIDTH * 10;
		    mobs.add(shopkeeper);
		}
		if(Actor.findChar(8 + WIDTH * 23) == null){
			Mob shopkeeper2 =  new Shopkeeper();
		    shopkeeper2.pos = 8 + WIDTH * 23;
		    mobs.add(shopkeeper2);
		}
		
		if(Badges.checkOtilukeRescued()&&!checkOtiluke()){
			if(Actor.findChar(32 + WIDTH * 15) == null){
		    Mob otiluke =  new OtilukeNPC();
		    otiluke.pos = 32 + WIDTH * 15;
			mobs.add(otiluke);
			}
			
			map[exit] = Terrain.STATUE;
		}
		
		if(storeRefresh()){
			if (Badges.checkSARRescued()){
			for (int i : sppots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem5 = storeItem5();
					drop(storeitem5, i).type = Heap.Type.FOR_SALE;
				}
			}
			}
			
			if (Badges.checkMOSRescued()){
			for (int i : foodpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem4 = storeItem4();
					drop(storeitem4, i).type = Heap.Type.FOR_SALE;
				}					
			}
			}
			
			if (Badges.checkCoconutRescued()){
			for (int i : bombpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem3 = storeItem3();
					drop(storeitem3, i).type = Heap.Type.FOR_SALE;
				}					
			}
		    }
			
			if (Badges.checkItemRescued()){
			for (int i : scrollspots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem2 = storeItem2();
					drop(storeitem2, i).type = Heap.Type.FOR_SALE;
				}					
			}
			for (int i : storespots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem = storeItem();
					drop(storeitem, i).type = Heap.Type.FOR_SALE;
				}					
			}
			}
			
			if (Badges.checkEggRescued()){
			for (int i : eggpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem6 = storeItem6();
					drop(storeitem6, i).type = Heap.Type.FOR_SALE;
				}					
			}
		    }
			if (Dungeon.gnollmission==true){
			for (int i : gnollpots) {
				Heap heap = heaps.get(i);
					if (heap == null){
						Item storeitem7 = storeItem7();
						drop(storeitem7, i).type = Heap.Type.FOR_SALE;
					}
				}
			}
			if (Badges.checkRainRescued()) {
				for (int i : skillpots) {
					Heap heap = heaps.get(i);
					if (heap == null) {
						Item storeitem8 = storeItem8();
						drop(storeitem8, i).type = Heap.Type.FOR_SALE;
					}
				}
			}
		}	
	}
	
	public Item storeItem (){
		Item prize;
		switch (Random.Int(12)) {
		case 0:
			prize = new PotionOfHealing();
			break;
		case 1:
			prize = Generator.random(Generator.Category.POTION);
			break;
		case 2:
			prize = new PocketBall();
			break;
		case 3:
			prize = Generator.random(Generator.Category.SCROLL);
			break;
		case 4:
			prize = new PotionOfMending();
			break;			
		case 5:
			prize = Generator.random(Generator.Category.BERRY);  
			break;
		case 6:
			prize = new ActiveMrDestructo();
			break;
		case 7:
			prize = new Phaseshift.Seed();
			break;
		case 8:
			prize = new PotionOfOverHealing();
			break;
		default:
			prize = new PetFood();
			break;
		}

		return prize;
 
	}
	
	public Item storeItem2 (){
		Item prize;
		switch (Random.Int(12)) {
		case 0:
			prize = new ScrollOfUpgrade();
			break;
		case 1:
			prize = new ScrollOfMagicalInfusion();
			break;
		case 2:
			prize = new ScrollOfRegrowth();
			break;
		case 3:
			prize = Generator.random(Generator.Category.WAND);
			break;
		case 4:
			prize = Generator.random(Generator.Category.RING);
			break;			
		case 5:
			prize = Generator.random(Generator.Category.WEAPON);  
			break;
		case 6:
			prize = Generator.random(Generator.Category.NORNSTONE);
			break;
		case 7:
			prize = Generator.random(Generator.Category.ARMOR);
			break;
		case 8:
			prize = new RunicBlade();
			break;
		case 9:
			prize = Generator.random(Generator.Category.RANGEWEAPON);
			break;
		default:
			prize = new ScrollOfUpgrade();
			break;
		}

		return prize;
 
	}
	
	public Item storeItem3 (){
		Item prize;
		switch (Random.Int(12)) {
		case 0:
			prize = new Ankh();
			break;
		case 1:
		case 2:
		case 3:
		case 4:		
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:	
            prize = Generator.random(Generator.Category.BOMBS);
            break;					
		case 10:
			prize =  new Honeypot();
			break;			
		default:
			prize = new Ankh();
			break;
		}

		return prize;
 
	}
	
	public Item storeItem4 (){
		Item prize;
		switch (Random.Int(3)) {
		case 0:
			prize = Generator.random(Generator.Category.HIGHFOOD);
			break;
		case 1:
			prize = Generator.random(Generator.Category.FOOD);
			break;
		default:
			prize = Generator.random(Generator.Category.HIGHFOOD);
			break;
		}
		return prize;
	}
	
	public Item storeItem5 (){
		Item prize;
		switch (Random.Int(6)) {
		case 0:
			prize = new AdamantArmor();
			break;
		case 1:
			prize = new AdamantRing();
			break;
		case 2:
			prize = new AdamantWand();
			break;
		case 3:
			prize = new AdamantWeapon();
			break;
		default:
			prize = new DarkGold().quantity(10);
			break;
		}
		return prize;
	}	
	
	public Item storeItem6 (){
		Item prize;
		switch (Random.Int(2)) {
		case 0:
		case 1:
			prize = Generator.random(Generator.Category.EGGS);
			break;
		default:
			prize = new Egg();
			break;
		}
		return prize;
	}

	public Item storeItem7 (){
		Item prize;
		switch (Random.Int(8)) {
			case 0:
				prize = new Pumpkin();
				break;
			case 1:
				prize = new Tree();
				break;
			case 2:
				prize = new FireCracker();
				break;
			case 3:
				prize = new MiniMoai();
				break;
			case 4:
				prize = new TestWeapon();
				break;
			case 5:
				prize = new ToyGun();
				break;
			case 6:
				prize = new HookHam();
				break;
			case 7:
				prize = new Brick();
				break;
			default:
				prize = new PetFood();
				break;
		}
		return prize;
	}

	public Item storeItem8 (){
		Item prize;
		switch (Random.Int(10)) {
			case 0:
				prize = new SkillOfAtk();
				break;
			case 1:
				prize = new SkillOfDef();
				break;
			case 2:
				prize = new SkillOfMig();
				break;
			default:
				prize = new ScrollOfUpgrade();
				break;
		}
		return prize;
	}

	public boolean storeRefresh(){
		boolean check=false;
		if (Statistics.realdeepestFloor>mineDepth || Dungeon.oneDay==true){
			//mineDepth=Statistics.realdeepestFloor;
			check=true;
			Dungeon.oneDay=false;
		}		
		return check;
	}

  @Override
  public void create() {
	  
	   super.create();	
   }	
  
   	
  @Override
	protected void createItems() {
	    
      /*Mob bluecat =  new BlueCat();
      bluecat.pos = 35 + WIDTH * 5;
      mobs.add(bluecat);*/
      
      Mob udawos = new Udawos();
	  udawos.pos = 33 + WIDTH * 34;
	  mobs.add(udawos);
	  
	  Mob typedscroll = new TypedScroll();
	  typedscroll.pos = 9 + WIDTH * 23;
	  mobs.add(typedscroll);
	  
      Mob g2159687 = new G2159687();
	  g2159687.pos = 35 + WIDTH * 3;
	  mobs.add(g2159687);

      Mob consideredhamster = new ConsideredHamster();
	  consideredhamster.pos = 26 + WIDTH * 10;
	  mobs.add(consideredhamster);  

      Mob bilboldev = new Bilboldev();
	  bilboldev.pos = 24 + WIDTH * 10;
	  mobs.add(bilboldev);  
	  
      if(Badges.checkTombRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)){
	  Mob watabou = new Watabou();
	  watabou.pos = 42 + WIDTH * 42;
	  mobs.add(watabou);

		  Mob realman = new ARealMan();
		  realman.pos = 44 + WIDTH * 45;
		  mobs.add(realman);

		  Mob wg = new WhiteGhost();
		  wg.pos = 45 + WIDTH * 44;
		  mobs.add(wg);

	  }

      Mob nyrds = new NYRDS();
	  nyrds.pos = 13 + WIDTH * 11;
	  mobs.add(nyrds);  

      Mob evan = new Evan();
	  evan.pos = 33 + WIDTH * 41;
	  mobs.add(evan);  	  
	  
      Mob hbb = new HBB();
	  hbb.pos = 43 + WIDTH * 15;
	  mobs.add(hbb);

      Mob sfb = new SFB();
	  sfb.pos = 34 + WIDTH * 11;
	  mobs.add(sfb);

      Mob jinkeloid = new Jinkeloid();
	  jinkeloid.pos = 43 + WIDTH * 14;
	  mobs.add(jinkeloid);

	  Mob uncles = new UncleS();
	  uncles.pos = 43 + WIDTH * 19;
	  mobs.add(uncles);

      Mob rustyblade = new Rustyblade();
	  rustyblade.pos = 40 + WIDTH * 15;
	  mobs.add(rustyblade);
     
      Mob sp931 = new SP931();
	  sp931.pos = 40 + WIDTH * 16;
	  mobs.add(sp931);
	  
	  Mob lyn = new Lyn();
	  lyn.pos = 40 + WIDTH * 9;
	  mobs.add(lyn);
	  
	  if (Badges.checkEggRescued()){
	  Mob lery = new Lery();
	  lery.pos = 41 + WIDTH * 9;
	  mobs.add(lery);
	  }
	  
      Mob hexa = new HeXA();
	  hexa.pos = 33 + WIDTH * 30;
	  mobs.add(hexa);	

      if (Badges.checkCoconutRescued()){
	  Mob fruitcat = new FruitCat();
	  fruitcat.pos = 45 + WIDTH * 2;
	  mobs.add(fruitcat);
	  } else {
	  Mob coconut = new Coconut();
	  coconut.pos = 45 + WIDTH * 2;
	  mobs.add(coconut);
	  }
	  
      Mob locastan = new Locastan();
	  locastan.pos = 5 + WIDTH * 5;
	  mobs.add(locastan);
	  
	  Mob dachhack = new Dachhack();
	  dachhack.pos = 37 + WIDTH * 3;
	  mobs.add(dachhack);
	  
	  Mob tempest102 = new Tempest102();
	  tempest102.pos = 33 + WIDTH * 42;
	  mobs.add(tempest102);
	  
	  if (Badges.checkMOSRescued()){
      Mob MOS = new MemoryOfSand();
	  MOS.pos = 26 + WIDTH * 4;
	  mobs.add(MOS);
	 }

	  Mob ONS = new OldNewStwist();
	  ONS.pos = 20 + WIDTH * 3;
	  mobs.add(ONS);
	  
      Mob HS = new HateSokoban();
	  HS.pos = 23 + WIDTH * 10;
	  mobs.add(HS);
	  
	  Mob LJ = new LaJi();
	  LJ.pos = 23 + WIDTH * 10;
	  mobs.add(LJ);
	  
	  if (Badges.checkSARRescued()){
      Mob SAR = new StormAndRain();
	  SAR.pos = 21 + WIDTH * 6;
	  mobs.add(SAR);	
	  }
	  
      if (Dungeon.dewNorn == true) {
	  Mob LYNN = new Lynn();
	  LYNN.pos = 31 + WIDTH * 32;
	  mobs.add(LYNN);  
	  }

	  Mob REN = new RENnpc();
	  REN.pos = 28 + WIDTH * 27;
	  mobs.add(REN);

	  Mob KOSTIS = new Kostis12345();
	  KOSTIS.pos = 20 + WIDTH * 25;
	  mobs.add(KOSTIS);

	  Mob JUH = new Juh9870();
	  JUH.pos = 35 + WIDTH * 6;
	  mobs.add(JUH);

	  Mob SADS = new SadSaltan();
	  SADS.pos = 42 + WIDTH * 38;
	  mobs.add(SADS);

	  Mob tinkerer4 =  new Tinkerer4();
      tinkerer4.pos = 31 + WIDTH * 20;
      mobs.add(tinkerer4);
      
      Mob tinkerer5 =  new Tinkerer5();
      tinkerer5.pos = 14 + WIDTH * 33;
      mobs.add(tinkerer5);
	  
	  AdultDragonViolet mob3 = new AdultDragonViolet();
	  mob3.pos = 5 + WIDTH * 43;
	  mobs.add(mob3);

	  /*SandMob test = new SandMob();
	  test.pos = 14 + WIDTH * 32;
	  mobs.add(test);*/

	  /*Guard test2 = new Guard();
	  test2.pos = 14 + WIDTH * 31;
	  mobs.add(test2);*/

	  /*TrollWarrior test3 = new TrollWarrior();
	  test3.pos = 14 + WIDTH * 30;
	  mobs.add(test3);*/
	  if (Badges.checkRainRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)) {
	  TestMob test4 = new TestMob();
	  test4.pos = 18 + WIDTH * 44;
	  mobs.add(test4);

	  TestMob2 test5 = new TestMob2();
	  test5.pos = 21 + WIDTH * 44;
	  mobs.add(test5);

	  RainTrainer rain = new RainTrainer();
	  rain.pos = 20 + WIDTH * 42;
	  mobs.add(rain);
	  }

	  /*Assassin test5 = new Assassin();
	  test5.pos = 14 + WIDTH * 28;
	  mobs.add(test5);*/
	  
	  if (Badges.checkFishRescued()){
	  Piranha mob4 = new Piranha();
	  mob4.pos = 42 + WIDTH * 37;
	  mobs.add(mob4);
	  Piranha mob5 = new Piranha();
	  mob5.pos = 42 + WIDTH * 36;
	  mobs.add(mob5);
	  Mob alivefish = new AliveFish();
	  alivefish.pos = 42 + WIDTH * 9;
	  mobs.add(alivefish);
      }
	  
	  /*Golem mob6 = new Golem();
	  mob6.pos = 42 + WIDTH * 36;
	  mobs.add(mob6);*/
	  
      scrollspots = new int[11];
      scrollspots[0] =  4 + WIDTH * 27;
      scrollspots[1] =  4 + WIDTH * 26;
      scrollspots[2] =  4 + WIDTH * 25;
      scrollspots[3] =  4 + WIDTH * 24;
      scrollspots[4] =  4 + WIDTH * 23;
      scrollspots[5] =  4 + WIDTH * 22;
      
      scrollspots[6] =  6 + WIDTH * 25;
      scrollspots[7] =  7 + WIDTH * 25;
      scrollspots[8] =  8 + WIDTH * 25;
      scrollspots[9] =  9 + WIDTH * 25;
      scrollspots[10] =  10 + WIDTH * 25;
      
      storespots = new int[9];
      storespots[0] =  7 + WIDTH * 10;
      storespots[1] =  7 + WIDTH * 9;
      storespots[2] =  7 + WIDTH * 8;
      storespots[3] =  7 + WIDTH * 7;
      
      storespots[4] =  12 + WIDTH * 13;
      storespots[5] =  12 + WIDTH * 12;
      storespots[6] =  12 + WIDTH * 11;
      storespots[7] =  12 + WIDTH * 10;
      storespots[8] =  12 + WIDTH * 9;
	  
	  bombpots = new int[8];
      bombpots[0] =  45 + WIDTH * 1;
      bombpots[1] =  45 + WIDTH * 3;
      bombpots[2] =  44 + WIDTH * 1;
      bombpots[3] =  44 + WIDTH * 2;
      
      bombpots[4] =  44 + WIDTH * 3;
      bombpots[5] =  46 + WIDTH * 1;
      bombpots[6] =  46 + WIDTH * 2;
      bombpots[7] =  46 + WIDTH * 3; 
	  
	  foodpots = new int[4];
      foodpots[0] =  24 + WIDTH * 6;
      foodpots[1] =  25 + WIDTH * 6;
      foodpots[2] =  26 + WIDTH * 6;
      foodpots[3] =  27 + WIDTH * 6;
	  
	  sppots = new int[1];
      sppots[0] =  20 + WIDTH * 6;
	  
	  eggpots = new int[1];
      eggpots[0] =  41 + WIDTH * 10;

	  gnollpots = new int[1];
	  gnollpots[0] =  21 + WIDTH * 3;

	  skillpots = new int[2];
	  skillpots[0] =  19 + WIDTH * 44;
	  skillpots[1] =  20 + WIDTH * 44;

      storeStock();
	  if (Dungeon.dewNorn == true) {
		  Alter alter = new Alter();
		  alter.seed(33 + WIDTH * 32, 1);
		  blobs.put(Alter.class, alter);
	  }

	  BedLight bl = new BedLight();
	  bl.seed(42 + WIDTH * 5,1);
	  blobs.put(BedLight.class, bl);

		addChest(39 + WIDTH * 4);
		addChest(40 + WIDTH * 4);
		addChest(41 + WIDTH * 4);
		addChest(42 + WIDTH * 4);
		
		if(Badges.checkTombRescued()){
		addChest(41 + WIDTH * 18);
		addChest(42 + WIDTH * 18);
		}
		
		addChest(7 + WIDTH * 5);
		addChest(8 + WIDTH * 5);
		addChest(9 + WIDTH * 5);
		
		if(Badges.checkFishRescued()){
		addChest(27 + WIDTH * 11);
		addChest(27 + WIDTH * 12);
		}
		
		if(Badges.checkTombRescued()){
		addTomb(41 + WIDTH * 41);
	    }
	}	
  
  private void addChest(int pos) {
		
		Item prize;
		switch (Random.Int(10)) {
		case 0:
			prize = Generator.random(Generator.Category.BERRY);
			break;
		case 1:
			prize = new Starflower.Seed();
			break;
		default:
			prize = new DarkGold();
			break;
		}

		drop(prize, pos).type = Heap.Type.CHEST;
	}

  private void addTomb(int pos) {
		
		Item prize;
		switch (Random.Int(10)) {
		case 0:
			prize = new Phaseshift.Seed();
			break;
		case 1:
			prize = Generator.random(Generator.Category.MUSHROOM);
			break;
		default:
			prize = new DarkGold();
			break;
		}

		drop(prize, pos).type = Heap.Type.TOMB;
	}	
  
	@Override
	public void press(int cell, Char ch) {
		
		if(!special){
			storeStock();
			special=true;
		}
				
		if (pit[cell] && ch == Dungeon.hero) {
			Chasm.heroFall(cell);
			return;
		}

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean trap = false;
		boolean interrupt = false;



		switch (map[cell]) {			
					
        
		case Terrain.HIGH_GRASS:
			HighGrass.trample(this, cell, ch);
			break;

		case Terrain.WELL:
			WellWater.affectCell(cell);
			break;

		case Terrain.ALCHEMY:
		         Alchemy alchemy = new Alchemy();
                level.blobs.put( Alchemy.class, alchemy );
			break;
			
		case Terrain.PEDESTAL:
			if (ch == null ) {
				Alter.transmute(cell);
			}
			break;

		/*case Terrain.BED:
			if(ch == null){
				BedLight.transmute(cell);
			}
			break;*/

		case Terrain.DOOR:
			Door.enter(cell);
			break;
		}

		if (trap){

			if (Dungeon.visible[cell])
				Sample.INSTANCE.play(Assets.SND_TRAP);

			if (ch == Dungeon.hero)
				Dungeon.hero.interrupt();

			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);					
		} 
		
		if (interrupt){

			Dungeon.hero.interrupt();
			GameScene.updateMap(cell);					
		} 

		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(ch);
		}
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_TOWN;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		
		map = TownLayouts.TOWN_LAYOUT.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();
			
		entrance = 25 + WIDTH * 21;
		exit = 5 + WIDTH * 40;


		return true;
	}
	@Override
	protected void decorate() {

	}

	@Override
	protected void createMobs() {

	}
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}

}
