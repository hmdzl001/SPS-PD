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
package com.hmdzl.spspd;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.actbuff.NmImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.actors.mobs.npcs.Wandmaker;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.levels.BetweenLevel;
import com.hmdzl.spspd.levels.BossRushLevel;
import com.hmdzl.spspd.levels.CaveChallengeLevel;
import com.hmdzl.spspd.levels.CavesBossLevel;
import com.hmdzl.spspd.levels.CavesLevel;
import com.hmdzl.spspd.levels.ChaosLevel;
import com.hmdzl.spspd.levels.CityBossLevel;
import com.hmdzl.spspd.levels.CityChallengeLevel;
import com.hmdzl.spspd.levels.CityLevel;
import com.hmdzl.spspd.levels.CrabBossLevel;
import com.hmdzl.spspd.levels.DeadEndLevel;
import com.hmdzl.spspd.levels.FieldBossLevel;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.HallsBossLevel;
import com.hmdzl.spspd.levels.HallsLevel;
import com.hmdzl.spspd.levels.IceChallengeLevel;
import com.hmdzl.spspd.levels.InfestBossLevel;
import com.hmdzl.spspd.levels.LastLevel;
import com.hmdzl.spspd.levels.LearnLevel;
import com.hmdzl.spspd.levels.MinesBossLevel;
import com.hmdzl.spspd.levels.NewRoomLevel;
import com.hmdzl.spspd.levels.PotLevel;
import com.hmdzl.spspd.levels.PrisonBossLevel;
import com.hmdzl.spspd.levels.PrisonChallengeLevel;
import com.hmdzl.spspd.levels.PrisonLevel;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.SafeLevel;
import com.hmdzl.spspd.levels.SewerBossLevel;
import com.hmdzl.spspd.levels.SewerChallengeLevel;
import com.hmdzl.spspd.levels.SewerLevel;
import com.hmdzl.spspd.levels.ShadowEaterLevel;
import com.hmdzl.spspd.levels.SkeletonBossLevel;
import com.hmdzl.spspd.levels.SokobanCastle;
import com.hmdzl.spspd.levels.SokobanIntroLevel;
import com.hmdzl.spspd.levels.SokobanPuzzlesLevel;
import com.hmdzl.spspd.levels.SokobanTeleportLevel;
import com.hmdzl.spspd.levels.SpringFestivalLevel;
import com.hmdzl.spspd.levels.TenguDenLevel;
import com.hmdzl.spspd.levels.ThiefBossLevel;
import com.hmdzl.spspd.levels.ThiefCatchLevel;
import com.hmdzl.spspd.levels.TownLevel;
import com.hmdzl.spspd.levels.TriangleCLevel;
import com.hmdzl.spspd.levels.TrianglePLevel;
import com.hmdzl.spspd.levels.TriangleWLevel;
import com.hmdzl.spspd.levels.ZotBossLevel;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.ui.QuickSlotButton;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

public class Dungeon {

	// enum of items which have limited spawns, records how many have spawned
	// could all be their own separate numbers, but this allows iterating, much
	// nicer for bundling/initializing.
	public enum LimitedDrops {
		// limited world drops
		strengthPotions,
		// doesn't use Generator, so we have to enforce one armband drop here
		spork, goei, caveskey,
		// containers
		shopcart;
		public int count = 0;

		// for items which can only be dropped once, should directly access
		// count otherwise.
		public boolean dropped() {
			return count != 0;
		}

		public void drop() {
			count = 1;
		}
		
		public static void reset(){
			for (LimitedDrops lim : values()){
				lim.count = 0;
			}
		}

		public static void store( Bundle bundle ){
			for (LimitedDrops lim : values()){
				bundle.put(lim.name(), lim.count);
			}
		}

		public static void restore( Bundle bundle ){
			for (LimitedDrops lim : values()){
				if (bundle.contains(lim.name())){
					lim.count = bundle.getInt(lim.name());
				} else {
					lim.count = 0;
				}
				
			}
		}
	}

	public static int[] pars;

	//public static boolean gnollspawned = false;
	//public static boolean skeletonspawned = false;
	//public static boolean goldthiefspawned = false;
	//public static boolean triforce = false;
	public static boolean triforceofcourage = false;
	public static boolean triforceofpower = false;
	public static boolean triforceofwisdom = false;
	public static boolean shadowyogkilled = false;
	public static boolean crabkingkilled = false;
	public static boolean banditkingkilled = false;
	public static boolean skeletonkingkilled = false;
	public static boolean gnollkingkilled = false;
	public static boolean tengudenkilled = false;
	public static boolean zotkilled = false;

	public static boolean dewDraw = false;
	public static boolean dewWater = false;

	public static boolean wings = false;
	public static boolean dewNorn = false;

	public static boolean gnollmission = false;
	public static boolean picktype = false;
	//public static boolean secondQuest = false;


	public static int sacrifice = 0;
	public static int saferoom = 0;
	public static int oldslot;
	public static boolean sporkAvail = false;

	//public static boolean challengebookdrop = false;
	//public static boolean goeidrop = false;
	
	public static int challenges;

	public static int giftunlocked;

	public static Hero hero;
	public static Floor depth;

	public static QuickSlot quickslot = new QuickSlot();

	public static int dungeondepth;
	public static int gold;

	public static String resultDescription;

	public static HashSet<Integer> chapters;

	// Hero's field of view
	public static boolean[] visible = new boolean[Floor.getLength()];

	public static SparseArray<ArrayList<Item>> droppedItems;

	public static int version;

	public static void init() {

		version = Game.versionCode;
		challenges = ShatteredPixelDungeon.challenges();
		giftunlocked = ShatteredPixelDungeon.unlocks();

		Actor.clear();
		Actor.resetNextID();

		PathFinder.setMapSize(Floor.getWidth(), Floor.HEIGHT);

		Scroll.initLabels();
		Potion.initColors();
		Ring.initGems();

		Statistics.reset();
		Journal.reset();

		quickslot.reset();
		QuickSlotButton.reset();

		dungeondepth = 0;
		gold = 0;

		droppedItems = new SparseArray<ArrayList<Item>>();

		for (LimitedDrops a : LimitedDrops.values())
			a.count = 0;

		chapters = new HashSet<Integer>();

		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();

		Room.shuffleTypes();

		//Generator.initArtifacts();
		
		Generator.reset();
		//Generator.initArtifacts();
		hero = new Hero();
		hero.live();

		Badges.reset();

		//gnollspawned = false;
		//skeletonspawned = false;
		//goldthiefspawned = false;
		//triforce = false;
		triforceofcourage = false;
		triforceofpower = false;
		triforceofwisdom = false;
		shadowyogkilled = false;
		crabkingkilled = false;
		banditkingkilled = false;
		gnollkingkilled = false;
		tengudenkilled = false;
		skeletonkingkilled = false;
		zotkilled = false;

		sacrifice = 0;
		saferoom = 0;
		oldslot = 0;
		sporkAvail = false;
		//challengebookdrop = false;
		//goeidrop = false;
		dewDraw = false;
		dewWater = false;
		wings = false;
		dewNorn = false;

		gnollmission = false;
		picktype = false;

		pars = new int[100];
		
		GamesInProgress.selectedClass.initHero(hero);

	}

	public static void initlearn() {

		version = Game.versionCode;
		challenges = ShatteredPixelDungeon.challenges();
		giftunlocked = ShatteredPixelDungeon.unlocks();

		//Generator.initArtifacts();

		Actor.clear();
		Actor.resetNextID();

		PathFinder.setMapSize(Floor.getWidth(), Floor.HEIGHT);

		Scroll.initLabels();
		Potion.initColors();
		Ring.initGems();

		Statistics.reset();
		Journal.reset();

		quickslot.reset();
		QuickSlotButton.reset();

		dungeondepth = 0;
		gold = 0;

		droppedItems = new SparseArray<ArrayList<Item>>();

		for (LimitedDrops a : LimitedDrops.values())
			a.count = 0;

		chapters = new HashSet<Integer>();

		Ghost.Quest.reset();
		Wandmaker.Quest.reset();
		Blacksmith.Quest.reset();
		Imp.Quest.reset();

		Room.shuffleTypes();

		//Generator.initArtifacts();
		hero = new Hero();
		hero.live();

		Badges.reset();

		HeroClass.NEWPLAYER.initHero(hero);

		//gnollspawned = false;
		//skeletonspawned = false;
		//goldthiefspawned = false;
		//triforce = false;
		triforceofcourage = false;
		triforceofpower = false;
		triforceofwisdom = false;
		shadowyogkilled = false;
		crabkingkilled = false;
		banditkingkilled = false;
		gnollkingkilled = false;
		tengudenkilled = false;
		skeletonkingkilled = false;
		zotkilled = false;

		sacrifice = 0;
		saferoom = 0;
		oldslot = 0;
		sporkAvail = false;
		//challengebookdrop = false;
		//goeidrop = false;
		dewDraw = false;
		dewWater = false;
		wings = false;
		dewNorn = false;

		gnollmission = false;
		picktype = false;

		pars = new int[100];

	}

	public static boolean isChallenged(int mask) {
		return (challenges & mask) != 0;
	}

	public static boolean isGift(int mask) {
		return (giftunlocked & mask) != 0;
	}

	public static Floor newFieldLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 27;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new SewerChallengeLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newBattleLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 28;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new PrisonChallengeLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newFishLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 29;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new CaveChallengeLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newVLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 30;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new CityChallengeLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newCatacombLevel() {


		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 31;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new TriangleCLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newFortressLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 32;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new TrianglePLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newChasmLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 33;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new TriangleWLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newInfestLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 35;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new InfestBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();
		if (Statistics.realdeepestFloor > 24) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		return level;
	}


	public static Floor newTenguHideoutLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 36;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new TenguDenLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newSkeletonBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 37;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new SkeletonBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newCrabBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 38;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new CrabBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newThiefBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 40;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new ThiefBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newFieldBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 43;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new FieldBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newPotLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 45;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new PotLevel();

		level.create();

		return level;
	}

	public static Floor newShadowEaterLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 47;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new ShadowEaterLevel();

		level.create();

		return level;
	}

	public static Floor newMineBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		//depth = 67;
		dungeondepth++;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new MinesBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newBossRushLevel() {


		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 71;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new BossRushLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newChaosLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 85;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new ChaosLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newZotBossLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth = 99;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		level = new ZotBossLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newJournalLevel(int page, Boolean first) {

		Dungeon.depth = null;
		Actor.clear();

		dungeondepth = 50 + page;

		if (page == 6) {
			dungeondepth = 66;
		}

		if (page == 7) {
			dungeondepth = 67;
		}

		if (page == 8) {
			dungeondepth = 68;
		}

		if (dungeondepth > Statistics.realdeepestFloor && dungeondepth < 68) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		switch (page) {
			case 0:
				level = new SafeLevel();
				break;
			case 1:
				level = new SokobanIntroLevel();
				break;
			case 2:
				level = new SokobanCastle();
				break;
			case 3:
				level = new SokobanTeleportLevel();
				break;
			case 4:
				level = new SokobanPuzzlesLevel();
				break;
			case 5:
				level = new TownLevel();
				break;
			case 6:
				level = new SpringFestivalLevel();
				break;
			case 7:
				level = new MinesBossLevel();
				break;
			case 8:
				level = new NewRoomLevel();
				break;
			default:
				level = Dungeon.newLevel();
		}

		Floor.first = first;
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

	public static Floor newChallengeLevel(int list, Boolean first) {

		Dungeon.depth = null;
		Actor.clear();

		dungeondepth = 26 + list;
		if (list == 0) {
			dungeondepth = 90;
		}
		if (list == 1) {
			dungeondepth = 27;
		}
		if (list == 2) {
			dungeondepth = 28;
		}
		if (list == 3) {
			dungeondepth = 29;
		}
		if (list == 4) {
			dungeondepth = 30;
		}
		if (list == 5) {
			dungeondepth = 31;
		}
		if (list == 6) {
			dungeondepth = 32;
		}
		if (list == 7) {
			dungeondepth = 33;
		}

		if (dungeondepth > Statistics.realdeepestFloor && dungeondepth < 34) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		Arrays.fill(visible, false);

		Floor level;
		switch (list) {
			case 0:
				level = new IceChallengeLevel();
				break;
			case 1:
				level = new SewerChallengeLevel();
				break;
			case 2:
				level = new PrisonChallengeLevel();
				break;
			case 3:
				level = new CaveChallengeLevel();
				break;
			case 4:
				level = new CityChallengeLevel();
				break;
			case 5:
				level = new TriangleCLevel();
				break;
			case 6:
				level = new TrianglePLevel();
				break;
			case 7:
				level = new TriangleWLevel();
				break;
			default:
				level = Dungeon.newLevel();
		}

		Floor.first = first;
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}


	public static Floor newLevel() {

		Dungeon.depth = null;
		Actor.clear();

		dungeondepth++;
		if (dungeondepth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = dungeondepth;
		}

		if (dungeondepth > Statistics.deepestFloor && dungeondepth < 27) {
			Statistics.deepestFloor = dungeondepth;

			Statistics.completedWithNoKilling = Statistics.qualifiedForNoKilling;
		}

		Arrays.fill(visible, false);

		Floor level;
		switch (dungeondepth) {
			case 1:
				//level = new PrisonBossLevel();
				//level = new SewerLevel();
				//hero.TRUE_HT=999;
				//hero.HP=hero.TRUE_HT;
				level = new BetweenLevel();
				break;
			case 2:
				//level = new HallsLevel();
				//hero.TRUE_HT=999;
				//hero.HP=hero.TRUE_HT;
				//break;
			case 3:
			case 4:
				//level = new CavesLevel();
				level = new SewerLevel();
				//hero.TRUE_HT=999;
				//hero.HP=hero.TRUE_HT;
				break;
			case 5:
				level = new SewerBossLevel();
				break;
			case 6:
				//level = new HallsLevel();
				//hero.TRUE_HT=999;
				//hero.HP=hero.TRUE_HT;
				//break;
				level = new BetweenLevel();
				break;
			case 7:
			case 8:
			case 9:
				level = new PrisonLevel();
				break;
			case 10:
				level = new PrisonBossLevel();
				break;
			case 11:
				level = new BetweenLevel();
				break;
			case 12:
			case 13:
			case 14:
				level = new CavesLevel();
				break;
			case 15:
				level = new CavesBossLevel();
				break;
			case 16:
				level = new BetweenLevel();
				break;
			case 17:
			case 18:
			case 19:
				level = new CityLevel();
				break;
			case 20:
				level = new CityBossLevel();
				break;
			case 21:
				level = new BetweenLevel();
				break;
			case 22:
			case 23:
			case 24:
				level = new HallsLevel();
				break;
			case 25:
				level = new HallsBossLevel();
				break;
			case 26:
				level = new LastLevel();
				break;
			case 41:
				level = new ThiefCatchLevel();
				break;
			case 67:
				level = new MinesBossLevel();
				break;
			case 71:
				level = new BossRushLevel();
				break;
			case 85:
				level = new ChaosLevel();
			default:
				level = new DeadEndLevel();
				if (dungeondepth < 27) {
					Statistics.deepestFloor--;
				}
		}

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();
		//if (depth<25 && !Dungeon.bossLevel(depth) && (Dungeon.dewDraw || Dungeon.dewWater)){

		//GLog.p("You feel the dungeon charge with dew!");
		//}
		NmImbue nm = Dungeon.hero.buff(NmImbue.class);
		if (Dungeon.hero.heroClass == HeroClass.SOLDIER && Dungeon.hero.skins == 4 && nm == null) {
			Buff.affect(Dungeon.hero, NmImbue.class);
		}
		
		/*if(Dungeon.hero.heroClass == HeroClass.PERFORMER){
			//Buff.prolong(Dungeon.hero,Rhythm.class,50);
			Buff.affect(Dungeon.hero,GlassShield.class).turns(3);
		}
		if(Dungeon.hero.heroClass == HeroClass.PERFORMER && Dungeon.hero.subClass == HeroSubClass.SUPERSTAR){
			Buff.affect(Dungeon.hero,Rhythm.class,50);
			Buff.prolong(Dungeon.hero,Rhythm2.class,50);
		}*/

		return level;
	}

	public static Floor newLearnLevel() {

		Dungeon.depth = null;
		Actor.clear();
		dungeondepth++;
		Arrays.fill(visible, false);
		Floor level;
		level = new LearnLevel();
		level.create();
		return level;
	}

	public static void resetLevel() {

		Actor.clear();

		Arrays.fill(visible, false);

		depth.reset();
		switchLevel(depth, depth.entrance);
	}

	public static boolean shopOnLevel() {
		return dungeondepth == 1 || dungeondepth == 6 || dungeondepth == 11 || dungeondepth == 16 || dungeondepth == 21 || dungeondepth == 50 || dungeondepth == 55;
	}

	public static boolean bossLevel() {
		return bossLevel(dungeondepth);
	}

	public static boolean bossLevel(int depth) {
		return depth == 5 || depth == 10 || depth == 15 || depth == 20
				|| depth == 25 || depth == 36 || depth == 41 || depth == 71;
	}

	public static boolean notClearableLevel(int depth) {
		return depth == 1 || depth == 5 || depth == 10 || depth == 15 || depth == 20
				|| depth == 25 || depth > 25;
	}

	//public static boolean townCheck(int depth) {
	//return depth > 54 && depth < 66;
	//}	

	public static boolean growLevel(int depth) {
		return depth == 27 || depth == 28 || depth == 30 || depth == 55;
	}

	public static boolean waterLevel(int depth) {
		return depth == 29;
	}

	public static boolean sokobanLevel(int depth) {
		return depth == 51 || depth == 52 || depth == 53 || depth == 54;
	}

	//public static boolean dropLevel(int depth) {
	//return depth == 40;
	//}


	@SuppressWarnings("deprecation")
	public static void switchLevel(final Floor level, int pos) {

	    PathFinder.setMapSize(Floor.getWidth(), Floor.HEIGHT);
	
		Dungeon.depth = level;
		//DriedRose.restoreGhostHero( level, pos );
		Actor.init();

		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add(level.respawner());
		}

		Actor regrower = level.regrower();
		if (regrower != null && growLevel(dungeondepth)) {
			Actor.add(level.regrower());
		}

		Actor waterer = level.waterer();
		if (waterer != null && waterLevel(dungeondepth)) {
			Actor.add(level.waterer());
		}		
		
		/*Actor floordropper = level.floordropper();
		if (floordropper != null && dropLevel(depth)) {
			Actor.add(level.floordropper());
		}*/

		hero.pos = pos != -1 ? pos : level.exit;

		Light light = hero.buff(Light.class);
		hero.viewDistance = light == null ? level.viewDistance : Math.max(
				Light.DISTANCE, level.viewDistance);

		Actor respawnerPet = level.respawnerPet();
		if (respawnerPet != null && Dungeon.dungeondepth != 50) {
			Actor.add(level.respawnerPet());
		}

		observe();
		
		try {
			saveAll();
		} catch (IOException e) {
			/*
			 * This only catches IO errors. Yes, this means things can go wrong,
			 * and they can go wrong catastrophically. But when they do the user
			 * will get a nice 'report this issue' dialogue, and I can fix the
			 * bug.
			 */
		}
	}

	public static void dropToChasm(Item item) {
		int depth = Dungeon.dungeondepth > 26 ? Dungeon.dungeondepth : Dungeon.dungeondepth + 1;
		ArrayList<Item> dropped = Dungeon.droppedItems
				.get(depth);
		if (dropped == null) {
			Dungeon.droppedItems.put(depth, dropped = new ArrayList<Item>());
		}
		dropped.add(item);
	}

	public static boolean posNeeded() {
		int[] quota = {2, 1, 4, 2, 7, 3, 9, 4, 12, 5, 14, 6, 17, 7, 19, 8, 22, 9, 24, 10};
		return chance(quota, LimitedDrops.strengthPotions.count);
	}

	private static boolean chance(int[] quota, int number) {

		for (int i = 0; i < quota.length; i += 2) {
			int qDepth = quota[i];
			if (dungeondepth <= qDepth) {
				int qNumber = quota[i + 1];
				return Random.Float() < (float) (qNumber - number)
						/ (qDepth - dungeondepth + 1);
			}
		}

		return false;
	}

	private static final String VERSION = "version";
	private static final String CHALLENGES = "challenges";
	private static final String HERO = "hero";
	private static final String GOLD = "gold";
	//private static final String SAFEROOM = "saferoom";
	private static final String DEPTH = "depth";
	private static final String DROPPED = "dropped%d";
	private static final String Depth = "level";
	private static final String LIMDROPS = "limiteddrops";

	private static final String CHAPTERS = "chapters";
	private static final String QUESTS = "quests";
	private static final String BADGES = "badges";

	private static final String SACRIFICE = "sacrifice";

	private static final String OLDSLOT = "oldslot";

	private static final String STRID = "triforceofcourage";
	private static final String STRIL = "triforceofpower";
	private static final String STRIT = "triforceofwisdom";
	private static final String SYOGKILL = "shadowyogkilled";
	private static final String CRABKILL = "crabkingkilled";
	private static final String TENGUDENKILL = "tengudenkilled";
	private static final String SKELETONKILL = "skeletonkingkilled";
	private static final String GNOLLKILL = "gnollkingkilled";
	private static final String BANDITKILL = "banditkingkilled";
	private static final String ZOTKILL = "zotkilled";
	private static final String SPORK = "sporkAvail";
	private static final String DEWDRAW = "dewDraw";
	private static final String DEWWATER = "dewWater";
	private static final String DEWNORN = "dewNorn";
	private static final String GNOLLMISSION = "gnollmission";
	private static final String PICKTYPE = "error";
	private static final String WINGS = "wings";
	private static final String PARS = "pars";

	//private static final String SECONDQUEST = "secondQuest";

	public static void saveGame(int save) throws IOException {
		try {
			Bundle bundle = new Bundle();

			version = Game.versionCode;
			bundle.put(VERSION, Game.versionCode);

			bundle.put(CHALLENGES, challenges);
			bundle.put(HERO, hero);
			bundle.put(GOLD, gold);
			bundle.put(DEPTH, dungeondepth);

			bundle.put(SACRIFICE, sacrifice);

			bundle.put(OLDSLOT, oldslot);

			bundle.put(STRID, triforceofcourage);
			bundle.put(STRIL, triforceofpower);
			bundle.put(STRIT, triforceofwisdom);
			bundle.put(SYOGKILL, shadowyogkilled);
			bundle.put(CRABKILL, crabkingkilled);
			bundle.put(TENGUDENKILL, tengudenkilled);
			bundle.put(BANDITKILL, banditkingkilled);
			bundle.put(SKELETONKILL, skeletonkingkilled);
			bundle.put(GNOLLKILL, gnollkingkilled);
			bundle.put(ZOTKILL, zotkilled);
			bundle.put(SPORK, sporkAvail);

			bundle.put(DEWDRAW, dewDraw);
			bundle.put(DEWWATER, dewWater);
			bundle.put(WINGS, wings);
			bundle.put(DEWNORN, dewNorn);

			bundle.put(GNOLLMISSION, gnollmission);
			bundle.put(PICKTYPE, picktype);
			bundle.put(PARS, pars);

			Bundle limDrops = new Bundle();
			LimitedDrops.store( limDrops );
			bundle.put ( LIMDROPS, limDrops );

			quickslot.storePlaceholders(bundle);

				for (int d : droppedItems.keyArray()) {
				bundle.put(Messages.format(DROPPED, d), droppedItems.get(d));
			}
					
			
			int count = 0;
			int ids[] = new int[chapters.size()];
			for (Integer id : chapters) {
				ids[count++] = id;
			}
			bundle.put(CHAPTERS, ids);

			Bundle quests = new Bundle();
			Ghost.Quest.storeInBundle(quests);
			Wandmaker.Quest.storeInBundle(quests);
			Blacksmith.Quest.storeInBundle(quests);
			Imp.Quest.storeInBundle(quests);
			bundle.put(QUESTS, quests);

			Room.storeRoomsInBundle(bundle);

			Statistics.storeInBundle(bundle);
			Journal.storeInBundle(bundle);
			//Generator.storeInBundle(bundle);

			Scroll.save(bundle);
			Potion.save(bundle);
			Ring.save(bundle);

			Actor.storeNextID(bundle);

			Bundle badges = new Bundle();
			Badges.saveLocal(badges);
			bundle.put(BADGES, badges);

			FileUtils.bundleToFile( GamesInProgress.gameFile(save), bundle);

		} catch (IOException e) {
			GamesInProgress.setUnknown(save);
			ShatteredPixelDungeon.reportException(e);
		}
	}

	public static void saveLevel( int save ) throws IOException {
		Bundle bundle = new Bundle();
		bundle.put(Depth, depth);
		FileUtils.bundleToFile(GamesInProgress.depthFile(save, dungeondepth), bundle);

	}

	public static void saveAll() throws IOException {
		if (hero != null && hero.isAlive()) {
			if (hero.heroClass == HeroClass.NEWPLAYER) {
				Actor.fixTime();
			} else {
				Actor.fixTime();
				saveGame(GamesInProgress.curSlot);
				saveLevel(GamesInProgress.curSlot);
				GamesInProgress.set(GamesInProgress.curSlot, dungeondepth, challenges, hero , oldslot);
			}
		}
	}

	public static void saveNew() {
		int slot = GamesInProgress.curSlot;
		ArrayList<GamesInProgress.Info> games = GamesInProgress.checkAll();
		int slot1 = games.size() + 1;

		try {
			Bundle bundle = FileUtils.bundleFromFile(GamesInProgress.gameFile(slot));
			FileUtils.bundleToFile(GamesInProgress.gameFile(slot1), bundle);
			GLog.b("saveslot" + ":" + slot1);
		} catch (Exception e) {

		}

		for (int i = 1; i < 100; i++) {
			//Dungeon.depth = null;
			//Actor.clear();
			//Bundle bundle1 = FileUtils.bundleFromFile( GamesInProgress.depthFile( slot, depth)) ;
			//Floor level = loadLevel(slot,depth);
			try {
				Bundle bundle1 = FileUtils.bundleFromFile(GamesInProgress.depthFile(slot, i));
				Floor level = (Floor) bundle1.get(Depth);
				if (level != null) {
					Bundle bundle2 = new Bundle();
					bundle2.put(Depth, level);
					FileUtils.bundleToFile(GamesInProgress.depthFile(slot1, i), bundle2);
					GLog.b("savedepth" + ":" + i);
				}
			} catch (Exception e) {

			}
		}
		GamesInProgress.set(slot1, dungeondepth, challenges, hero, 0);
	}

	public static void saveNewSlot(int slot1) {
		//if (slot1 == 0) {return;}

		int slot = GamesInProgress.curSlot;

		Dungeon.deleteGame(slot1, true);
		//FileUtils.deleteDir(GamesInProgress.gameFolder(slot1));

		try {
			Bundle bundle = FileUtils.bundleFromFile(GamesInProgress.gameFile(slot));
			FileUtils.bundleToFile(GamesInProgress.gameFile(slot1), bundle);
			GLog.b("saveslot" + ":" + slot1);
		} catch (Exception e) {

		}

		for (int i = 1; i < 100; i++) {
			try {
				Bundle bundle1 = FileUtils.bundleFromFile(GamesInProgress.depthFile(slot, i));
				Floor level = (Floor) bundle1.get(Depth);
				if (level != null) {
					Bundle bundle2 = new Bundle();
					bundle2.put(Depth, level);
					FileUtils.bundleToFile(GamesInProgress.depthFile(slot1, i), bundle2);
					GLog.b("savedepth" + ":" + i);
				}
			} catch (Exception e) {

			}
		}
		GamesInProgress.set(slot1, dungeondepth, challenges, hero, slot);
	}

	public static void autosaveNew() {
		int slot0 = GamesInProgress.curSlot;
		int slot1 = 0;

		Dungeon.oldslot = slot0;


		Dungeon.deleteGame(0, true);
		//FileUtils.deleteDir(GamesInProgress.gameFolder(slot1));

		try {
			Bundle bundle = FileUtils.bundleFromFile(GamesInProgress.gameFile(slot0));
			FileUtils.bundleToFile(GamesInProgress.gameFile(slot1), bundle);
			GLog.b("saveslot" + ":" + slot1);
		} catch (Exception e) {

		}

		for (int i = 1; i < 100; i++) {
			//Dungeon.depth = null;
			//Actor.clear();
			//Bundle bundle1 = FileUtils.bundleFromFile( GamesInProgress.depthFile( slot, depth)) ;
			//Floor level = loadLevel(slot,depth);
			try {
				Bundle bundle1 = FileUtils.bundleFromFile(GamesInProgress.depthFile(slot0, i));
				Floor level = (Floor) bundle1.get(Depth);
				if (level != null) {
					Bundle bundle2 = new Bundle();
					bundle2.put(Depth, level);
					FileUtils.bundleToFile(GamesInProgress.depthFile(slot1, i), bundle2);
					GLog.b("savedepth" + ":" + i);
				}
			} catch (Exception e) {

			}
		}
		GamesInProgress.set(slot1, dungeondepth, challenges, hero, slot0);
	}

	public static void loadGame(int save) throws IOException {
		loadGame(save, true);
	}


	public static void loadGame(int save, boolean fullLoad) throws IOException {

		Bundle bundle = FileUtils.bundleFromFile(GamesInProgress.gameFile(save));

		version = bundle.getInt(VERSION);

		//Generator.reset();

		Actor.restoreNextID(bundle);

		quickslot.reset();
		QuickSlotButton.reset();

		Dungeon.challenges = bundle.getInt(CHALLENGES);

		Dungeon.depth = null;
		Dungeon.dungeondepth = -1;

		Scroll.restore(bundle);
		Potion.restore(bundle);
		Ring.restore(bundle);

		quickslot.restorePlaceholders(bundle);
		if (fullLoad) {
			
			LimitedDrops.restore(bundle.getBundle(LIMDROPS));
            
			chapters = new HashSet<Integer>();
			int ids[] = bundle.getIntArray(CHAPTERS);
			if (ids != null) {
				for (int id : ids) {
					chapters.add(id);
				}
			}

			Bundle quests = bundle.getBundle(QUESTS);
			if (!quests.isNull()) {
				Ghost.Quest.restoreFromBundle(quests);
				Wandmaker.Quest.restoreFromBundle(quests);
				Blacksmith.Quest.restoreFromBundle(quests);
				Imp.Quest.restoreFromBundle(quests);
			} else {
				Ghost.Quest.reset();
				Wandmaker.Quest.reset();
				Blacksmith.Quest.reset();
				Imp.Quest.reset();
			}
		}


		Bundle badges = bundle.getBundle(BADGES);
		if (!badges.isNull()) {
			Badges.loadLocal(badges);
		} else {
			Badges.reset();
		}

		hero = null;
		hero = (Hero) bundle.get(HERO);

		gold = bundle.getInt(GOLD);
		dungeondepth = bundle.getInt(DEPTH);

		sacrifice = bundle.getInt(SACRIFICE);

		oldslot = bundle.getInt(OLDSLOT);

		//gnollspawned = bundle.getBoolean(GNOLLSPAWN);
		//skeletonspawned = bundle.getBoolean(SKELETONSPAWN);
		//goldthiefspawned = bundle.getBoolean(THIEFSPAWN);
		//triforce = bundle.getBoolean(STRI);
		triforceofcourage = bundle.getBoolean(STRID);
		triforceofpower = bundle.getBoolean(STRIL);
		triforceofwisdom = bundle.getBoolean(STRIT);
		shadowyogkilled = bundle.getBoolean(SYOGKILL);
		crabkingkilled = bundle.getBoolean(CRABKILL);
		tengudenkilled = bundle.getBoolean(TENGUDENKILL);
		banditkingkilled = bundle.getBoolean(BANDITKILL);
		skeletonkingkilled = bundle.getBoolean(SKELETONKILL);
		gnollkingkilled = bundle.getBoolean(GNOLLKILL);
		zotkilled = bundle.getBoolean(ZOTKILL);
		sporkAvail = bundle.getBoolean(SPORK);
		dewDraw = bundle.getBoolean(DEWDRAW);
		dewWater = bundle.getBoolean(DEWWATER);
		wings = bundle.getBoolean(WINGS);
		dewNorn = bundle.getBoolean(DEWNORN);

		gnollmission = bundle.getBoolean(GNOLLMISSION);
		picktype = bundle.getBoolean(PICKTYPE);
		pars = bundle.getIntArray(PARS);

		Statistics.restoreFromBundle(bundle);
		Journal.restoreFromBundle(bundle);
		//Generator.restoreFromBundle(bundle);

		droppedItems = new SparseArray<ArrayList<Item>>();
		for (int i = 2; i <= Statistics.realdeepestFloor + 1; i++) {
			ArrayList<Item> dropped = new ArrayList<Item>();
			for (Bundlable b : bundle.getCollection(String.format(DROPPED, i))) {
				dropped.add((Item) b);
			}
			if (!dropped.isEmpty()) {
				droppedItems.put(i, dropped);
			}
		}

}

	public static Floor loadLevel(int save ) throws IOException {
		
		Dungeon.depth = null;
		Actor.clear();

		Bundle bundle = FileUtils.bundleFromFile( GamesInProgress.depthFile( save, dungeondepth)) ;
		
		Floor level = (Floor)bundle.get(Depth);
		
		if (level == null){
			throw new IOException();
		} else {
			return level;
		}

	}

	public static void deleteGame( int save, boolean deleteLevels) {

	    //if (Dungeon.hero.heroClass == HeroClass.NEWPLAYER) {
		//	FileUtils.deleteFile(GamesInProgress.gameFile(save));
		//}

	    FileUtils.deleteFile(GamesInProgress.gameFile(save));

		if (deleteLevels) {
			FileUtils.deleteDir(GamesInProgress.gameFolder(save));
		}

		GamesInProgress.delete( save );

	}
	
	public static void preview( GamesInProgress.Info info, Bundle bundle ) {
		info.depth = bundle.getInt( DEPTH );
		info.version = bundle.getInt( VERSION );
		info.challenges = bundle.getInt( CHALLENGES );
		Hero.preview( info, bundle.getBundle( HERO ) );
		Statistics.preview( info, bundle );
	}	
	
	public static void fail(String desc) {
		resultDescription = desc;
		if (hero.belongings.getItem(Ankh.class) == null && hero.heroClass != HeroClass.NEWPLAYER) {
			Rankings.INSTANCE.submit(false);
		}
	}

	public static void win( String desc) {

		hero.belongings.identify();

		if (challenges != 0) {
			Badges.validateChampion();
		}

		resultDescription = desc;
		Rankings.INSTANCE.submit( true );
	}


	public static void observe() {

		if (depth == null) {
			return;
		}

	    if (depth.darkness()){
			depth.visited = visible;
			depth.updateFieldOfView(hero);
			System.arraycopy(Floor.fieldOfView, 0, visible, 0, visible.length);
			BArray.or(depth.visited, visible, depth.visited);
		} else {

		depth.updateFieldOfView(hero);
		System.arraycopy(Floor.fieldOfView, 0, visible, 0, visible.length);

		BArray.or(depth.visited, visible, depth.visited);
		}

		GameScene.afterObserve();
	}

	private static boolean[] passable = new boolean[Floor.getLength()];
	

	public static int findPath(Char ch, int from, int to, boolean pass[],
							   boolean[] visible) {

		if (Floor.adjacent(from, to)) {
			return Actor.findChar(to) == null && (pass[to] || Floor.avoid[to]) ? to
					: -1;
		}

		if (ch.flying || ch.buff(Amok.class) != null) {
			BArray.or(pass, Floor.avoid, passable);
		} else {
			System.arraycopy(pass, 0, passable, 0, Floor.getLength());
		}

		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char) actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}

		return PathFinder.getStep(from, to, passable);

	}

	public static int flee(Char ch, int cur, int from, boolean pass[],
			boolean[] visible) {

		if (ch.flying) {
			BArray.or(pass, Floor.avoid, passable);
		} else {
			System.arraycopy(pass, 0, passable, 0, Floor.getLength());
		}

		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				int pos = ((Char) actor).pos;
				if (visible[pos]) {
					passable[pos] = false;
				}
			}
		}
		passable[cur] = true;

		return PathFinder.getStepBack(cur, from, passable);

	}
	
    public static boolean checkNight(){
	   int hour= Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	   return (hour > 19 || hour < 7);
    }
    
    public static int getMonth(){
 	   int month=Calendar.getInstance().get(Calendar.MONTH)+1;
 	   return month;
     }

}
