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
package com.hmdzl.spspd.levels;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Alchemy;
import com.hmdzl.spspd.actors.blobs.Alter;
import com.hmdzl.spspd.actors.blobs.WellWater;
import com.hmdzl.spspd.actors.mobs.AdultDragonViolet;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.Piranha;
import com.hmdzl.spspd.actors.mobs.TestMob;
import com.hmdzl.spspd.actors.mobs.TestMob2;
import com.hmdzl.spspd.actors.mobs.npcs.AFly;
import com.hmdzl.spspd.actors.mobs.npcs.ARealMan;
import com.hmdzl.spspd.actors.mobs.npcs.ATV9;
import com.hmdzl.spspd.actors.mobs.npcs.AliveFish;
import com.hmdzl.spspd.actors.mobs.npcs.Apostle;
import com.hmdzl.spspd.actors.mobs.npcs.AshWolf;
import com.hmdzl.spspd.actors.mobs.npcs.Bilboldev;
import com.hmdzl.spspd.actors.mobs.npcs.BlackMeow;
import com.hmdzl.spspd.actors.mobs.npcs.BoneStar;
import com.hmdzl.spspd.actors.mobs.npcs.CatSheep;
import com.hmdzl.spspd.actors.mobs.npcs.Coconut;
import com.hmdzl.spspd.actors.mobs.npcs.ConsideredHamster;
import com.hmdzl.spspd.actors.mobs.npcs.Dachhack;
import com.hmdzl.spspd.actors.mobs.npcs.DreamPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.Evan;
import com.hmdzl.spspd.actors.mobs.npcs.FruitCat;
import com.hmdzl.spspd.actors.mobs.npcs.G2159687;
import com.hmdzl.spspd.actors.mobs.npcs.GoblinPlayer;
import com.hmdzl.spspd.actors.mobs.npcs.HBB;
import com.hmdzl.spspd.actors.mobs.npcs.HateSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.HeXA;
import com.hmdzl.spspd.actors.mobs.npcs.HoneyPoooot;
import com.hmdzl.spspd.actors.mobs.npcs.Ice13;
import com.hmdzl.spspd.actors.mobs.npcs.Jinkeloid;
import com.hmdzl.spspd.actors.mobs.npcs.Juh9870;
import com.hmdzl.spspd.actors.mobs.npcs.Kostis12345;
import com.hmdzl.spspd.actors.mobs.npcs.LaJi;
import com.hmdzl.spspd.actors.mobs.npcs.Lery;
import com.hmdzl.spspd.actors.mobs.npcs.Locastan;
import com.hmdzl.spspd.actors.mobs.npcs.Lyn;
import com.hmdzl.spspd.actors.mobs.npcs.Lynn;
import com.hmdzl.spspd.actors.mobs.npcs.MemoryOfSand;
import com.hmdzl.spspd.actors.mobs.npcs.Millilitre;
import com.hmdzl.spspd.actors.mobs.npcs.NYRDS;
import com.hmdzl.spspd.actors.mobs.npcs.NutPainter;
import com.hmdzl.spspd.actors.mobs.npcs.OldNewStwist;
import com.hmdzl.spspd.actors.mobs.npcs.Omicronrg9;
import com.hmdzl.spspd.actors.mobs.npcs.OtilukeNPC;
import com.hmdzl.spspd.actors.mobs.npcs.RENnpc;
import com.hmdzl.spspd.actors.mobs.npcs.RainTrainer;
import com.hmdzl.spspd.actors.mobs.npcs.Ravenwolf;
import com.hmdzl.spspd.actors.mobs.npcs.Rustyblade;
import com.hmdzl.spspd.actors.mobs.npcs.SFB;
import com.hmdzl.spspd.actors.mobs.npcs.SP931;
import com.hmdzl.spspd.actors.mobs.npcs.SadSaltan;
import com.hmdzl.spspd.actors.mobs.npcs.SaidbySun;
import com.hmdzl.spspd.actors.mobs.npcs.Shopkeeper;
import com.hmdzl.spspd.actors.mobs.npcs.Shower;
import com.hmdzl.spspd.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.actors.mobs.npcs.Tempest102;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer4;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer5;
import com.hmdzl.spspd.actors.mobs.npcs.TypedScroll;
import com.hmdzl.spspd.actors.mobs.npcs.Udawos;
import com.hmdzl.spspd.actors.mobs.npcs.UncleS;
import com.hmdzl.spspd.actors.mobs.npcs.Watabou;
import com.hmdzl.spspd.actors.mobs.npcs.WhiteGhost;
import com.hmdzl.spspd.actors.mobs.npcs.XixiZero;
import com.hmdzl.spspd.items.AdamantArmor;
import com.hmdzl.spspd.items.AdamantRing;
import com.hmdzl.spspd.items.AdamantWand;
import com.hmdzl.spspd.items.AdamantWeapon;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PocketBall;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.eggs.Egg;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.items.misc.SkillOfAtk;
import com.hmdzl.spspd.items.misc.SkillOfDef;
import com.hmdzl.spspd.items.misc.SkillOfMig;
import com.hmdzl.spspd.items.quest.DarkGold;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.weapon.guns.ToyGun;
import com.hmdzl.spspd.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.items.weapon.melee.special.DragonBoat;
import com.hmdzl.spspd.items.weapon.melee.special.FireCracker;
import com.hmdzl.spspd.items.weapon.melee.special.HookHam;
import com.hmdzl.spspd.items.weapon.melee.special.KeyWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Lollipop;
import com.hmdzl.spspd.items.weapon.melee.special.Pumpkin;
import com.hmdzl.spspd.items.weapon.melee.special.RunicBlade;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.items.weapon.melee.special.TestWeapon;
import com.hmdzl.spspd.items.weapon.melee.special.Tree;
import com.hmdzl.spspd.items.weapon.missiles.MiniMoai;
import com.hmdzl.spspd.items.weapon.missiles.MoneyPack;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.levels.features.HighGrass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Phaseshift;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.level;

public class TownLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
		special=false;
	}
	   
    public int[] scrollspots;
    public int[] storespots;
    public int[] bombpots;
	public int[] foodpots;
	public int[] sppots;
	public int[] eggpots;
	public int[] gnollpots;
	public int[] skillpots;
	public int[] pillpots;
	
	private static final String SCROLLSPOTS = "scrollspots";
	private static final String STORESPOTS = "storespots";
	private static final String BOMBPOTS = "bombpots";
	private static final String FOODPOTS = "foodpots";
	private static final String SPPOTS = "sppots";
	private static final String EGGPOTS = "eggpots";
	private static final String GNOLLPOTS = "gnollpots";
	private static final String SKILLPOTS = "skillpots";
	private static final String PILLPOTS = "pillpots";
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SCROLLSPOTS, scrollspots);
		bundle.put(STORESPOTS, storespots);
		bundle.put(BOMBPOTS, bombpots);
		bundle.put(FOODPOTS, foodpots);
		bundle.put(SPPOTS, sppots);
		bundle.put(EGGPOTS, eggpots);
		bundle.put(GNOLLPOTS, gnollpots);
		bundle.put(SKILLPOTS, skillpots);
		bundle.put(PILLPOTS, pillpots);
	}
		
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		scrollspots = bundle.getIntArray(SCROLLSPOTS);
		storespots = bundle.getIntArray(STORESPOTS);
		bombpots = bundle.getIntArray(BOMBPOTS);
		foodpots = bundle.getIntArray(FOODPOTS);
		sppots = bundle.getIntArray(SPPOTS);
		eggpots = bundle.getIntArray(EGGPOTS);
		gnollpots = bundle.getIntArray(GNOLLPOTS);
		skillpots = bundle.getIntArray(SKILLPOTS);
		pillpots = bundle.getIntArray(PILLPOTS);
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
		
		
			if (Badges.checkSARRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
			for (int i : sppots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem5 = storeItem5();
					drop(storeitem5, i).type = Heap.Type.FOR_SALE;
				}
			}
			}
			
			if (Badges.checkMOSRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
			for (int i : foodpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem4 = storeItem4();
					drop(storeitem4, i).type = Heap.Type.FOR_SALE;
				}					
			}
			}
			
			if (Badges.checkCoconutRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
			for (int i : bombpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem3 = storeItem3();
					drop(storeitem3, i).type = Heap.Type.FOR_SALE;
				}					
			}
		    }
			
			if (Badges.checkItemRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
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
			
			if (Badges.checkEggRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
			for (int i : eggpots) {
				Heap heap = heaps.get(i);
				if (heap == null){
					Item storeitem6 = storeItem6();
					drop(storeitem6, i).type = Heap.Type.FOR_SALE;
				}					
			}
		    }
			if (Dungeon.gnollmission==true|| Dungeon.isChallenged(Challenges.TEST_TIME)){
			for (int i : gnollpots) {
				Heap heap = heaps.get(i);
					if (heap == null){
						Item storeitem7 = storeItem7();
						drop(storeitem7, i).type = Heap.Type.FOR_SALE;
					}
				}
			}
			if (Badges.checkRainRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)) {
				for (int i : skillpots) {
					Heap heap = heaps.get(i);
					if (heap == null) {
						Item storeitem8 = storeItem8();
						drop(storeitem8, i).type = Heap.Type.FOR_SALE;
					}
				}
			}
			if (Badges.checkUncleRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)) {
				for (int i : pillpots) {
					Heap heap = heaps.get(i);
					if (heap == null) {
						Item storeitem9 = storeItem9();
						drop(storeitem9, i).type = Heap.Type.FOR_SALE;
					}
				}
			}
			
	}
	
	public Item storeItem (){
		Item prize;
		switch (Random.Int(10)) {
		case 0:
			prize = Generator.random(Generator.Category.MUSHROOM);
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
			prize = Generator.random(Generator.Category.SEED);
			break;			
		case 5:
			prize = Generator.random(Generator.Category.BERRY);  
			break;
		case 6:
			prize = Generator.random(Generator.Category.SUMMONED);
			break;
		case 7:
			prize = new PetFood();
			break;
		case 8:
			prize = new NutVegetable();
			break;
		default:
			prize = new Nut();
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
			prize = Generator.random(Generator.Category.RANGEWEAPON);
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
		case 10:
            prize = Generator.random(Generator.Category.BOMBS);
            break;
         case 11:
			prize =  new ScrollOfRegrowth();
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
		switch (Random.Int(13)) {
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
			case 8:
				prize = new Lollipop();
				break;
			case 9:
				prize = new SJRBMusic();
				break;
			case 10:
				prize = new MoneyPack(5);
				break;
			case 11:
				prize = new KeyWeapon();
				break;
			case 12:
				prize = new DragonBoat();
				break;
			default:
				prize = new PetFood();
				break;
		}
		return prize;
	}

	public Item storeItem8 (){
		Item prize;
		switch (Random.Int(6)) {
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
				prize = Generator.random(Generator.Category.MUSICWEAPON);
				break;
		}
		return prize;
	}

	public Item storeItem9 (){
		Item prize;
		prize = Generator.random(Generator.Category.PILL);
		return prize;
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

	  Mob xixizero = new XixiZero();
	  xixizero.pos = 25 + WIDTH * 11;
	  mobs.add(xixizero);

	  if(Badges.checkTombRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)){
	  Mob watabou = new Watabou();
	  watabou.pos = 42 + WIDTH * 42;
	  mobs.add(watabou);

		  Mob wg = new WhiteGhost();
		  wg.pos = 45 + WIDTH * 44;
		  mobs.add(wg);

	  }

	  Mob mill = new Millilitre();
	  mill.pos = 45 + WIDTH * 42;
	  mobs.add(mill);

      Mob nyrds = new NYRDS();
	  nyrds.pos = 13 + WIDTH * 11;
	  mobs.add(nyrds);  

      Mob hbb = new HBB();
	  hbb.pos = 43 + WIDTH * 15;
	  mobs.add(hbb);

      Mob sfb = new SFB();
	  sfb.pos = 34 + WIDTH * 11;
	  mobs.add(sfb);

	  Mob omi = new Omicronrg9();
	  omi.pos = 36 + WIDTH * 11;
	  mobs.add(omi);

      Mob honey = new HoneyPoooot();
	  honey.pos = 40 + WIDTH * 15;
	  mobs.add(honey);

	  Mob jinkeloid = new Jinkeloid();
	  jinkeloid.pos = 43 + WIDTH * 14;
	  mobs.add(jinkeloid);

	  Mob atv9 = new ATV9();
	  atv9.pos = 43 + WIDTH * 17;
	  mobs.add(atv9);

	  if (Badges.checkUncleRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)) {
		  Mob uncles = new UncleS();
		  uncles.pos = 43 + WIDTH * 22;
		  mobs.add(uncles);

		  Mob realman = new ARealMan();
		  realman.pos = 44 + WIDTH * 45;
		  mobs.add(realman);

		  Mob lyn = new Lyn();
		  lyn.pos = 40 + WIDTH * 22;
		  mobs.add(lyn);

		  Mob saidbysun = new SaidbySun();
		  saidbysun.pos = 42 + WIDTH * 45;
          mobs.add(saidbysun);
	  }
     
      Mob sp931 = new SP931();
	  sp931.pos = 40 + WIDTH * 16;
	  mobs.add(sp931);

	  Mob dp = new DreamPlayer();
	  dp.pos = 43 + WIDTH * 24;
	  mobs.add(dp);

	  if (Badges.checkEggRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
		  Mob lery = new Lery();
		  lery.pos = 41 + WIDTH * 9;
		  mobs.add(lery);

		  Mob BM = new BlackMeow();
		  BM.pos = 38 + WIDTH * 38;
		  mobs.add(BM);

		  Mob catS = new CatSheep();
		  catS.pos = 37 + WIDTH * 37;
		  mobs.add(catS);

	  }
	  Mob evan = new Evan();
	  evan.pos = 27 + WIDTH * 31;
	  mobs.add(evan);

	  Mob ice13 = new Ice13();
	  ice13.pos = 29 + WIDTH * 32;
	  mobs.add(ice13);

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

	  Mob goblin = new GoblinPlayer();
	  goblin.pos = 2 + WIDTH * 5;
	  mobs.add(goblin);
	  
	  Mob dachhack = new Dachhack();
	  dachhack.pos = 37 + WIDTH * 3;
	  mobs.add(dachhack);

	  if (Badges.checkMOSRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)){
      Mob MOS = new MemoryOfSand();
	  MOS.pos = 26 + WIDTH * 4;
	  mobs.add(MOS);

          Mob Afly = new AFly();
          Afly.pos = 27 + WIDTH * 17;
          mobs.add(Afly);

		 Piranha mob6 = new Piranha();
		  mob6.pos = 19 + WIDTH * 9;
		  mobs.add(mob6);

		  Mob bonestar = new BoneStar();
		  bonestar.pos = 21 + WIDTH * 9;
		  mobs.add(bonestar);

	 }

	  Mob ONS = new OldNewStwist();
	  ONS.pos = 20 + WIDTH * 3;
	  mobs.add(ONS);
	  
      Mob HS = new HateSokoban();
	  HS.pos = 23 + WIDTH * 10;
	  mobs.add(HS);
	  
	  Mob LJ = new LaJi();
	  LJ.pos = 23 + WIDTH * 12;
	  mobs.add(LJ);
	  
	  if (Badges.checkSARRescued()|| Dungeon.isChallenged(Challenges.TEST_TIME)){
      Mob SAR = new StormAndRain();
	  SAR.pos = 21 + WIDTH * 6;
	  mobs.add(SAR);

	  Mob RW = new Ravenwolf();
	   RW.pos = 23 + WIDTH * 6;
	   mobs.add(RW);
	  }
	  
      if (Dungeon.dewNorn == true || Dungeon.isChallenged(Challenges.TEST_TIME)) {
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

	  Mob apos = new Apostle();
	  apos.pos = 20 + WIDTH * 21;
	  mobs.add(apos);

	  Mob painter = new NutPainter();
	  painter.pos = 16 + WIDTH * 21;
	  mobs.add(painter);

	  Mob JUH = new Juh9870();
	  JUH.pos = 35 + WIDTH * 6;
	  mobs.add(JUH);

	  Mob SADS = new SadSaltan();
	  SADS.pos = 42 + WIDTH * 38;
	  mobs.add(SADS);

	  Mob tinkerer4 =  new Tinkerer4();
      tinkerer4.pos = 31 + WIDTH * 20;
      mobs.add(tinkerer4);

	  Mob shower =  new Shower();
	  shower.pos = 30 + WIDTH * 19;
	  mobs.add(shower);
      
      Mob tinkerer5 =  new Tinkerer5();
      tinkerer5.pos = 14 + WIDTH * 33;
      mobs.add(tinkerer5);
	  
	  AdultDragonViolet mob3 = new AdultDragonViolet();
	  mob3.pos = 5 + WIDTH * 43;
	  mobs.add(mob3);

	 //MonsterBox test = new MonsterBox();
	 // test.pos = 14 + WIDTH * 32;
	  //mobs.add(test);

	  //FishProtector test2 = new FishProtector();
	  //test2.pos = 14 + WIDTH * 31;
	  //mobs.add(test2);

	  //GraveProtector test3 = new GraveProtector();
	 // test3.pos = 14 + WIDTH * 30;
	//  mobs.add(test3);

	  if (Badges.checkRainRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)) {
	  TestMob test4 = new TestMob();
	  test4.pos = 18 + WIDTH * 44;
	  mobs.add(test4);

	  RainTrainer rain = new RainTrainer();
	  rain.pos = 17 + WIDTH * 42;
	  mobs.add(rain);

		  Mob rustyblade = new Rustyblade();
		  rustyblade.pos = 22 + WIDTH * 42;
		  mobs.add(rustyblade);

		  Mob tempest102 = new Tempest102();
		  tempest102.pos = 33 + WIDTH * 42;
		  mobs.add(tempest102);
	  }

	  TestMob2 test5 = new TestMob2();
	  test5.pos = 21 + WIDTH * 44;
	  mobs.add(test5);

	  /*Assassin test5 = new Assassin();
	  test5.pos = 14 + WIDTH * 28;
	  mobs.add(test5);*/
	  
	  if (Badges.checkFishRescued() || Dungeon.isChallenged(Challenges.TEST_TIME)){
	  Piranha mob4 = new Piranha();
	  mob4.pos = 42 + WIDTH * 37;
	  mobs.add(mob4);
	  Piranha mob5 = new Piranha();
	  mob5.pos = 42 + WIDTH * 36;
	  mobs.add(mob5);
	  Mob alivefish = new AliveFish();
	  alivefish.pos = 42 + WIDTH * 9;
	  mobs.add(alivefish);
		  AshWolf ashwolf = new AshWolf();
		  ashwolf.pos = 40 + WIDTH * 36;
		  mobs.add(ashwolf);

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

	  skillpots = new int[3];
	  skillpots[0] =  33 + WIDTH * 44;
	  skillpots[1] =  34 + WIDTH * 44;
	  skillpots[2] =  35 + WIDTH * 44;

	  pillpots = new int[1];
	  pillpots[0] =  43 + WIDTH * 23;

      storeStock();
	  if (Dungeon.dewNorn == true) {
		  Alter alter = new Alter();
		  alter.seed(33 + WIDTH * 32, 1);
		  blobs.put(Alter.class, alter);
	  }

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
		//return Assets.WATER_HONEY;
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
