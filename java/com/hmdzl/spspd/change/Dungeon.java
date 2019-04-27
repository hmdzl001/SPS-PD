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
package com.hmdzl.spspd.change;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import com.hmdzl.spspd.change.actors.buffs.Awareness;
import com.hmdzl.spspd.change.actors.buffs.GlassShield;
import com.hmdzl.spspd.change.actors.buffs.MindVision;
import com.hmdzl.spspd.change.actors.buffs.Rhythm;
import com.hmdzl.spspd.change.actors.buffs.Rhythm2;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.levels.SokobanSPLevel;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Dewcharge;
import com.hmdzl.spspd.change.actors.buffs.Light;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.change.actors.mobs.npcs.Ghost;
import com.hmdzl.spspd.change.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.change.actors.mobs.npcs.Wandmaker;
import com.hmdzl.spspd.change.actors.mobs.npcs.Tinkerer5;
import com.hmdzl.spspd.change.items.Ankh;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.items.wands.Wand;
import com.hmdzl.spspd.change.levels.BattleLevel;
import com.hmdzl.spspd.change.levels.CatacombLevel;
import com.hmdzl.spspd.change.levels.CavesBossLevel;
import com.hmdzl.spspd.change.levels.CavesLevel;
import com.hmdzl.spspd.change.levels.ChasmLevel;
import com.hmdzl.spspd.change.levels.CityBossLevel;
import com.hmdzl.spspd.change.levels.CityLevel;
import com.hmdzl.spspd.change.levels.CrabBossLevel;
import com.hmdzl.spspd.change.levels.DeadEndLevel;
import com.hmdzl.spspd.change.levels.DragonCaveLevel;
import com.hmdzl.spspd.change.levels.FieldLevel;
import com.hmdzl.spspd.change.levels.FieldBossLevel;
import com.hmdzl.spspd.change.levels.FishingLevel;
import com.hmdzl.spspd.change.levels.FortressLevel;
import com.hmdzl.spspd.change.levels.HallsBossLevel;
import com.hmdzl.spspd.change.levels.HallsLevel;
import com.hmdzl.spspd.change.levels.InfestBossLevel;
import com.hmdzl.spspd.change.levels.LastLevel;
import com.hmdzl.spspd.change.levels.LastShopLevel;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.MinesBossLevel;
import com.hmdzl.spspd.change.levels.PrisonBossLevel;
import com.hmdzl.spspd.change.levels.PrisonLevel;
import com.hmdzl.spspd.change.levels.Room;
import com.hmdzl.spspd.change.levels.SafeLevel;
import com.hmdzl.spspd.change.levels.SewerBossLevel;
import com.hmdzl.spspd.change.levels.SewerLevel;
import com.hmdzl.spspd.change.levels.SkeletonBossLevel;
import com.hmdzl.spspd.change.levels.SokobanCastle;
import com.hmdzl.spspd.change.levels.SokobanIntroLevel;
import com.hmdzl.spspd.change.levels.SokobanPuzzlesLevel;
import com.hmdzl.spspd.change.levels.SokobanTeleportLevel;
import com.hmdzl.spspd.change.levels.SokobanVaultLevel;
import com.hmdzl.spspd.change.levels.TenguDenLevel;
import com.hmdzl.spspd.change.levels.ThiefBossLevel;
import com.hmdzl.spspd.change.levels.ThiefCatchLevel;
import com.hmdzl.spspd.change.levels.BossRushLevel;
import com.hmdzl.spspd.change.levels.TownLevel;
import com.hmdzl.spspd.change.levels.VaultLevel;
import com.hmdzl.spspd.change.levels.ZotBossLevel;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.scenes.StartScene;
import com.hmdzl.spspd.change.ui.QuickSlotButton;
import com.hmdzl.spspd.change.utils.BArray;
import com.hmdzl.spspd.change.utils.GLog;

import com.hmdzl.spspd.change.windows.WndAlchemy;
import com.hmdzl.spspd.change.windows.WndResurrect;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

public class Dungeon {

	// enum of items which have limited spawns, records how many have spawned
	// could all be their own separate numbers, but this allows iterating, much
	// nicer for bundling/initializing.
	public static enum limitedDrops {
		// limited world drops
		strengthPotions, upgradeScrolls,

		//Norn Stones
		nornstones,

		// doesn't use Generator, so we have to enforce one armband drop here
		spork, sewerkey, prisonkey, caveskey, citykey, hallskey, ringofwealth, vaultpage, town,
		conchshell, ancientcoin, tengukey, bone, journal, safespotpage, dragoncave, treasuremap, goei,

		// containers
		dewVial, seedBag, scrollBag, potionBag, wandBag, shopcart, heartScarecrow, challengebook;

		public int count = 0;

		// for items which can only be dropped once, should directly access
		// count otherwise.
		public boolean dropped() {
			return count != 0;
		}

		public void drop() {
			count = 1;
		}
	}
	
	public static int[] pars;
	
	public static boolean earlygrass = false;
	public static boolean gnollspawned = false;
	public static boolean skeletonspawned = false;
	public static boolean goldthiefspawned = false;
	public static boolean triforce = false;
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
	public static boolean canSave = false;
	public static boolean gnollmission = false;
	public static boolean oneDay = false;
	public static boolean error = false;
	//public static boolean secondQuest = false;

	public static int challenges;
	
	public static int ratChests = 0;
	public static int sacrifice = 0;
	public static boolean sporkAvail = false;
	public static boolean challengebookdrop = false;
	public static boolean goeidrop = false;

	public static Hero hero;
	public static Level level;

	public static QuickSlot quickslot = new QuickSlot();

	public static int depth = 1;
	public static int gold = 0;

	public static String resultDescription;

	public static HashSet<Integer> chapters;

	// Hero's field of view
	public static boolean[] visible = new boolean[Level.getLength()];

	public static SparseArray<ArrayList<Item>> droppedItems;

	public static int version;

	public static void init() {

	    version = Game.versionCode;
		challenges = ShatteredPixelDungeon.challenges();

		//Generator.initArtifacts();

		Actor.clear();
		Actor.resetNextID();

		PathFinder.setMapSize(Level.getWidth(), Level.HEIGHT);

		Scroll.initLabels();
		Potion.initColors();
		Ring.initGems();

		Statistics.reset();
		Journal.reset();

		quickslot.reset();
		QuickSlotButton.reset();

		depth = 0;
		gold = 0;
		
		droppedItems = new SparseArray<ArrayList<Item>>();

		for (limitedDrops a : limitedDrops.values())
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

		StartScene.curClass.initHero(hero);
		
		earlygrass = false;
		gnollspawned = false;
		skeletonspawned = false;
		goldthiefspawned = false;
		triforce = false;
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
        ratChests = 0;
		sacrifice = 0 ;
		sporkAvail = false;
		challengebookdrop = false;
		goeidrop = false;
		dewDraw = false;
		dewWater = false;
		wings = false;
		dewNorn = false;
		canSave = false;
		gnollmission = false;
		oneDay =false;
		error = false;
	    
		pars = new int[100];
		
	}

	public static boolean isChallenged(int mask) {
		return (challenges & mask) != 0;
	}

	public static Level newFieldLevel(){

		Dungeon.level = null;
		Actor.clear();
		depth = 27;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new FieldLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	public static Level newBattleLevel(){

		Dungeon.level = null;
		Actor.clear();
		depth = 28;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new BattleLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	public static Level newFishLevel(){

		Dungeon.level = null;
		Actor.clear();
		depth = 29;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new FishingLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	public static Level newVaultLevel(){

		Dungeon.level = null;
		Actor.clear();
		depth = 30;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new VaultLevel();

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	
	public static Level newCatacombLevel(){

		
		Dungeon.level = null;
		Actor.clear();
		depth = 31;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new CatacombLevel();
	
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	
public static Level newFortressLevel(){
		
		Dungeon.level = null;
		Actor.clear();
		depth = 32;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new FortressLevel();
	
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}

public static Level newChasmLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 33;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new ChasmLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newInfestLevel(){

	Dungeon.level = null;
	Actor.clear();
    depth = 35;
    if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new InfestBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();
	if (Statistics.deepestFloor>24){Statistics.deepestFloor = depth;}

	return level;
}



public static Level newTenguHideoutLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 36;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new TenguDenLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newSkeletonBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 37;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new SkeletonBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newCrabBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 38;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new CrabBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newThiefBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 40;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new ThiefBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newFieldBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 43;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new FieldBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newMineBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	//depth = 67;
	depth++;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new MinesBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newBossRushLevel(){

		
		Dungeon.level = null;
		Actor.clear();
		depth = 71;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		Arrays.fill(visible, false);

		Level level;
		level = new BossRushLevel();
	
		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();

		return level;
	}
	
public static Level newZotBossLevel(){

	Dungeon.level = null;
	Actor.clear();
	depth = 99;
	if (depth > Statistics.realdeepestFloor) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	level = new ZotBossLevel();

	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newJournalLevel(int page, Boolean first){

	Dungeon.level = null;
	Actor.clear();
	
    depth = 50+page;
	
	if (page==6){
		depth = 66;
	}
	
	if (page==7){
		depth = 67;
	}
	
	if (depth > Statistics.realdeepestFloor && depth < 68) {
		Statistics.realdeepestFloor = depth;}
	
	Arrays.fill(visible, false);

	Level level;
	switch(page){
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
		level = new SokobanSPLevel();
		break;
	case 7:
		level = new MinesBossLevel();
		break;
	default:
		level = Dungeon.newLevel();
	}

	level.first = first;
	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

public static Level newChallengeLevel(int list, Boolean first){

	Dungeon.level = null;
	Actor.clear();
	
    depth = 27+list;
	if (list==0){
		depth = 27;
	}
	if (list==1){
		depth = 28;
	}
	if (list==2){
		depth = 29;
	}
	if (list==3){
		depth = 30;
	}
	if (list==4){
		depth = 31;
	}
	if (list==5){
		depth = 32;
	}
	if (list==6){
		depth = 33;
	}
	if (depth > Statistics.realdeepestFloor && depth < 34) {
		Statistics.realdeepestFloor = depth;}

	Arrays.fill(visible, false);

	Level level;
	switch(list){
	case 0:
	    level = new FieldLevel();
	    break;
	case 1:
		level = new BattleLevel();
		break;
	case 2:
		level = new FishingLevel();
		break;
	case 3:
		level = new VaultLevel();
		break;
	case 4:
		level = new CatacombLevel();
		break;
	case 5:
		level = new FortressLevel();
		break;
	case 6:
		level = new ChasmLevel();
		break;
	default:
		level = Dungeon.newLevel();
	}

	level.first = first;
	level.create();

	Statistics.qualifiedForNoKilling = !bossLevel();

	return level;
}

	
	public static Level newLevel() {

		Dungeon.level = null;
		Actor.clear();

		depth++;
		if (depth > Statistics.realdeepestFloor) {
			Statistics.realdeepestFloor = depth;}
		
		if (depth > Statistics.deepestFloor && depth < 27) {
			Statistics.deepestFloor = depth;

			if (Statistics.qualifiedForNoKilling) {
				Statistics.completedWithNoKilling = true;
			} else {
				Statistics.completedWithNoKilling = false;
			}
		}
		
		if (depth==6){
			Statistics.sewerKills=Statistics.enemiesSlain;
		}
		if (depth==10){
			Statistics.prisonKills=Statistics.enemiesSlain-Statistics.sewerKills;
		}

		Arrays.fill(visible, false);

		Level level;
		switch (depth) {
		case 1:
			//level = new PrisonBossLevel();
			//level = new SewerLevel();
			//hero.HT=999;
			//hero.HP=hero.HT;
			//break;
		case 2:
			//level = new HallsLevel();
			//hero.HT=999;
			//hero.HP=hero.HT;
			//break;
		case 3:
		case 4:
			//level = new CavesLevel();
			level = new SewerLevel();
			//hero.HT=999;
			//hero.HP=hero.HT;
			break;
		case 5:
			level = new SewerBossLevel();
			break;
		case 6:
			//level = new HallsLevel();
			//hero.HT=999;
			//hero.HP=hero.HT;
			//break;
		case 7:
		case 8:
		case 9:
			level = new PrisonLevel();
			break;
		case 10:
			level = new PrisonBossLevel();
			break;
		case 11:
		case 12:
		case 13:
		case 14:
			level = new CavesLevel();
			break;
		case 15:
			level = new CavesBossLevel();
			break;
		case 16:
		case 17:
		case 18:
		case 19:
			level = new CityLevel();
			break;
		case 20:
			level = new CityBossLevel();
			break;
		case 21:
			level = new LastShopLevel();
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
		default:
			level = new DeadEndLevel();
			if (depth<27){Statistics.deepestFloor--;}
		}

		level.create();

		Statistics.qualifiedForNoKilling = !bossLevel();
		if (depth<26 && depth!=21 && !Dungeon.bossLevel(depth) && (Dungeon.dewDraw || Dungeon.dewWater)){
			Buff.prolong(Dungeon.hero, Dewcharge.class, Dewcharge.DURATION+(Math.max(Statistics.prevfloormoves,1)));
		    //GLog.p("You feel the dungeon charge with dew!");
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

	public static void resetLevel() {

		Actor.clear();

		Arrays.fill(visible, false);

		level.reset();
		switchLevel(level, level.entrance);
	}

	public static boolean shopOnLevel() {
		return depth==1 || depth == 6 || depth == 11 || depth == 16;
	}

	public static boolean bossLevel() {
		return bossLevel(depth);
	}

	public static boolean bossLevel(int depth) {
		return depth == 5 || depth == 10 || depth == 15 || depth == 20
				|| depth == 25 ||  depth ==  36 ||  depth ==  41 || depth == 71 ;
	}

	public static boolean notClearableLevel(int depth) {
		return depth == 1 || depth ==2 ||depth == 5 || depth == 10 || depth == 15 || depth == 20 || depth == 21
				|| depth == 25 || depth>25;
	}

	public static boolean townCheck(int depth) {
		return depth > 54 && depth < 66;
	}	
	
	public static boolean growLevel(int depth) {
		return depth == 27 || depth == 28 || depth == 32 || depth == 30 || depth == 55;
	}

	public static boolean sokobanLevel(int depth) {
		return  depth == 51 || depth == 52 || depth == 53 || depth == 54;
	}
	
	//public static boolean dropLevel(int depth) {
		//return depth == 40;
	//}


	@SuppressWarnings("deprecation")
	public static void switchLevel(final Level level, int pos) {

		Dungeon.level = level;
		DriedRose.restoreGhostHero( level, pos );
		Actor.init();

		Actor respawner = level.respawner();
		if (respawner != null) {
			Actor.add(level.respawner());
		}
		
		Actor regrower = level.regrower();
		if (regrower != null && growLevel(depth)) {
			Actor.add(level.regrower());
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
		if (respawnerPet != null) {
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
		int depth = Dungeon.depth + 1;
		ArrayList<Item> dropped = Dungeon.droppedItems
				.get(depth);
		if (dropped == null) {
			Dungeon.droppedItems.put(depth, dropped = new ArrayList<Item>());
		}
		dropped.add(item);
	}

	public static boolean posNeeded() {
		int[] quota = { 4, 2, 9, 4, 14, 6, 19, 8, 24, 10 };
		return chance(quota, limitedDrops.strengthPotions.count);
	}

	public static boolean souNeeded() {
		int[] quota = { 5, 3, 10, 6, 15, 9, 20, 12, 25, 15 };
		return chance(quota, limitedDrops.upgradeScrolls.count);
	}

	private static boolean chance(int[] quota, int number) {

		for (int i = 0; i < quota.length; i += 2) {
			int qDepth = quota[i];
			if (depth <= qDepth) {
				int qNumber = quota[i + 1];
				return Random.Float() < (float) (qNumber - number)
						/ (qDepth - depth + 1);
			}
		}

		return false;
	}

	private static final String RG_GAME_FILE	= "rogue.dat";
	private static final String RG_DEPTH_FILE	= "rogue%d.dat";

	private static final String WR_GAME_FILE = "warrior.dat";
	private static final String WR_DEPTH_FILE = "warrior%d.dat";

	private static final String MG_GAME_FILE = "mage.dat";
	private static final String MG_DEPTH_FILE = "mage%d.dat";

	private static final String RN_GAME_FILE	= "huntress.dat";
	private static final String RN_DEPTH_FILE	= "huntress%d.dat";
	
	private static final String PE_GAME_FILE	= "performer.dat";
	private static final String PE_DEPTH_FILE	= "performer%d.dat";
		
	private static final String SO_GAME_FILE	= "soldier.dat";
	private static final String SO_DEPTH_FILE	= "soldier%d.dat";
	
	private static final String VERSION = "version";
	private static final String CHALLENGES = "challenges";
	private static final String HERO = "hero";
	private static final String GOLD = "gold";
	private static final String DEPTH = "depth";
	private static final String DROPPED = "dropped%d";
	private static final String LEVEL = "level";
	private static final String LIMDROPS = "limiteddrops";
	private static final String DV = "dewVial";
	private static final String WT = "transmutation";
	private static final String CHAPTERS = "chapters";
	private static final String QUESTS = "quests";
	private static final String BADGES = "badges";
	
	private static final String SACRIFICE = "sacrifice";
	private static final String RATCHESTS = "ratChests";
	private static final String EARLYGRASS = "earlygrass";
	private static final String GNOLLSPAWN = "gnollspawned";
	private static final String SKELETONSPAWN = "skeletonspawned";
	private static final String THIEFSPAWN = "goldthiefspawned";
	private static final String STRI = "triforce";
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
	private static final String CBDROP = "challengebookdrop";
	private static final String GOEIDROP = "goeidrop";
	private static final String DEWDRAW = "dewDraw";
	private static final String DEWWATER = "dewWater";
	private static final String DEWNORN = "dewNorn";
	private static final String CANSAVE = "canSave";
	private static final String GNOLLMISSION = "gnollmission";
	private static final String ONEDAY = "oneDay";
    private static final String ERROR = "error";
	private static final String WINGS = "wings";
	private static final String PARS = "pars";
	
	//private static final String SECONDQUEST = "secondQuest";
	
	// TODO: to support pre-0.2.3 saves, remove when needed
	private static final String POS = "potionsOfStrength";
	private static final String SOU = "scrollsOfEnhancement";
	private static final String AS = "arcaneStyli";

	public static String gameFile(HeroClass cl) {
		switch (cl) {
		case WARRIOR:
			return WR_GAME_FILE;
		case MAGE:
			return MG_GAME_FILE;
		//case ROUGE:
			//return RG_GAME_FILE;
		case HUNTRESS:
			return RN_GAME_FILE;		
		case PERFORMER:
			return PE_GAME_FILE;
		case SOLDIER:
			return SO_GAME_FILE;			
		default:
			return RG_GAME_FILE;
		}
	}

	private static String depthFile(HeroClass cl) {
		switch (cl) {
		case WARRIOR:
			return WR_DEPTH_FILE;
		case MAGE:
			return MG_DEPTH_FILE;
		//case ROUGE:
           // return RG_DEPTH_FILE;
		case HUNTRESS:
			return RN_DEPTH_FILE;
		case PERFORMER:
			return PE_DEPTH_FILE;
		case SOLDIER:
			return SO_DEPTH_FILE;			
		default:
			return RG_DEPTH_FILE;
		}
	}

	public static void saveGame(String fileName) throws IOException {
		try {
			Bundle bundle = new Bundle();

			version = Game.versionCode;
			bundle.put(VERSION, Game.versionCode);
			bundle.put(CHALLENGES, challenges);
			bundle.put(HERO, hero);
			bundle.put(GOLD, gold);
			bundle.put(DEPTH, depth);
			
			//bundle.put(SECONDQUEST, secondQuest);
			bundle.put(SACRIFICE, sacrifice);
			bundle.put(RATCHESTS, ratChests);
			bundle.put(EARLYGRASS, earlygrass);
			bundle.put(GNOLLSPAWN, gnollspawned);
			bundle.put(SKELETONSPAWN, skeletonspawned);
			bundle.put(THIEFSPAWN, goldthiefspawned);
			bundle.put(STRI, triforce);
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
			bundle.put(CBDROP, challengebookdrop);
			bundle.put(GOEIDROP, goeidrop);
			bundle.put(DEWDRAW, dewDraw);
			bundle.put(DEWWATER, dewWater);
			bundle.put(WINGS, wings);
			bundle.put(DEWNORN, dewNorn);
			bundle.put(CANSAVE, canSave);
			bundle.put(GNOLLMISSION, gnollmission);
			bundle.put(ONEDAY, oneDay);
			bundle.put(ERROR, error);
			bundle.put(PARS, pars);
	
			for (int d : droppedItems.keyArray()) {
				bundle.put(String.format(DROPPED, d), droppedItems.get(d));
			}

			quickslot.storePlaceholders(bundle);

			int[] dropValues = new int[limitedDrops.values().length];
			for (limitedDrops value : limitedDrops.values())
				dropValues[value.ordinal()] = value.count;
			bundle.put(LIMDROPS, dropValues);

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

			OutputStream output = Game.instance.openFileOutput(fileName,
					Game.MODE_PRIVATE);
			Bundle.write(bundle, output);
			output.close();

		} catch (IOException e) {

			GamesInProgress.setUnknown(hero.heroClass);
		}
	}

	public static void saveLevel() throws IOException {
		Bundle bundle = new Bundle();
		bundle.put(LEVEL, level);

		OutputStream output = Game.instance.openFileOutput(
				Messages.format(depthFile(hero.heroClass), depth),
				Game.MODE_PRIVATE);
		Bundle.write(bundle, output);
		output.close();
	}

	public static void saveAll() throws IOException {
		if (hero.isAlive()) {

			Actor.fixTime();
			saveGame(gameFile(hero.heroClass));
			saveLevel();

			GamesInProgress.set(hero.heroClass, depth, hero.lvl,
					challenges != 0);

		} else if (WndResurrect.instance != null) {

			WndResurrect.instance.hide();
			Hero.reallyDie(WndResurrect.causeOfDeath);

		}
	}

	public static void loadGame(HeroClass cl) throws IOException {
		loadGame(gameFile(cl), true);
	}

	public static void loadGame(String fileName) throws IOException {
		loadGame(fileName, false);
	}

	public static void loadGame(String fileName, boolean fullLoad)
			throws IOException {
        try{
		Bundle bundle = gameBundle(fileName);

		version = bundle.getInt(VERSION);

		Generator.reset();

		Actor.restoreNextID(bundle);

		quickslot.reset();
		QuickSlotButton.reset();

		Dungeon.challenges = bundle.getInt(CHALLENGES);

		Dungeon.level = null;
		Dungeon.depth = -1;

		if (fullLoad) {
			PathFinder.setMapSize(Level.getWidth(), Level.HEIGHT);
		}

		Scroll.restore(bundle);
		Potion.restore(bundle);
		Ring.restore(bundle);

		quickslot.restorePlaceholders(bundle);

		if (fullLoad) {
			// TODO: adjust this when dropping support for pre-0.2.3 saves
			if (bundle.contains(LIMDROPS)) {
				int[] dropValues = bundle.getIntArray(LIMDROPS);
				for (limitedDrops value : limitedDrops.values())
					value.count = value.ordinal() < dropValues.length ? dropValues[value
							.ordinal()] : 0;
			} else {
				for (limitedDrops value : limitedDrops.values())
					value.count = 0;
				limitedDrops.strengthPotions.count = bundle.getInt(POS);
				limitedDrops.upgradeScrolls.count = bundle.getInt(SOU);

			}
			// for pre-0.2.4 saves
			if (bundle.getBoolean(DV))
				limitedDrops.dewVial.drop();

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

			Room.restoreRoomsFromBundle(bundle);
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
		depth = bundle.getInt(DEPTH);
		
		sacrifice = bundle.getInt(SACRIFICE);
		ratChests = bundle.getInt(RATCHESTS);
		earlygrass = bundle.getBoolean(EARLYGRASS);
		gnollspawned = bundle.getBoolean(GNOLLSPAWN);
		skeletonspawned = bundle.getBoolean(SKELETONSPAWN);
		goldthiefspawned = bundle.getBoolean(THIEFSPAWN);
		triforce = bundle.getBoolean(STRI);
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
		challengebookdrop = bundle.getBoolean(CBDROP);
		goeidrop = bundle.getBoolean(GOEIDROP);
		dewDraw = bundle.getBoolean(DEWDRAW);
		dewWater = bundle.getBoolean(DEWWATER);
		wings = bundle.getBoolean(WINGS);
		dewNorn = bundle.getBoolean(DEWNORN);
		canSave = bundle.getBoolean(CANSAVE);
		gnollmission = bundle.getBoolean(GNOLLMISSION);
		oneDay = bundle.getBoolean(ONEDAY);
		error = bundle.getBoolean(ERROR);
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
		catch (IOException ex) {
			GLog.i("Save File corrupt...\n\nthe gremlins have won this round!");
		}		
	}

	public static Level loadLevel(HeroClass cl) throws IOException {

		Dungeon.level = null;
		Actor.clear();

		InputStream input = Game.instance.openFileInput(Messages.format(
				depthFile(cl), depth));
		Bundle bundle = Bundle.read(input);
		input.close();

		return (Level) bundle.get("level");
	}

	public static void deleteGame(HeroClass cl, boolean deleteLevels) {

		Game.instance.deleteFile(gameFile(cl));

		if (deleteLevels) {
			int depth = 1;
			while (Game.instance.deleteFile(Messages.format(depthFile(cl), depth))) {
				depth++;
			}
			for(int i=1; i<200; i++){
	            Game.instance.deleteFile(Messages.format(depthFile(cl), i));
	        }
		}

		GamesInProgress.delete(cl);
	}

	public static Bundle gameBundle(String fileName) throws IOException {

		InputStream input = Game.instance.openFileInput(fileName);
		Bundle bundle = Bundle.read(input);
		input.close();

		return bundle;
	}

	public static void preview(GamesInProgress.Info info, Bundle bundle) {
		info.depth = bundle.getInt(DEPTH);
		info.challenges = (bundle.getInt(CHALLENGES) != 0);
		if (info.depth == -1) {
			info.depth = bundle.getInt("maxDepth"); // FIXME
		}
		Hero.preview(info, bundle.getBundle(HERO));
	}

	public static void fail( String desc ) {
		resultDescription = desc;
		if (hero.belongings.getItem(Ankh.class) == null) {
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

		if (level == null) {
			return;
		}

	    if (level.darkness()){
			level.visited = visible;
			level.updateFieldOfView(hero);
			System.arraycopy(Level.fieldOfView, 0, visible, 0, visible.length);
			BArray.or(level.visited, visible, level.visited);
		} else {

		level.updateFieldOfView(hero);
		System.arraycopy(Level.fieldOfView, 0, visible, 0, visible.length);

		BArray.or(level.visited, visible, level.visited);
		}

		GameScene.afterObserve();
	}

	private static boolean[] passable = new boolean[Level.getLength()];
	

	public static int findPath(Char ch, int from, int to, boolean pass[],
			boolean[] visible) {

		if (Level.adjacent(from, to)) {
			return Actor.findChar(to) == null && (pass[to] || Level.avoid[to]) ? to
					: -1;
		}

		if (ch.flying || ch.buff(Amok.class) != null) {
			BArray.or(pass, Level.avoid, passable);
		} else {
			System.arraycopy(pass, 0, passable, 0, Level.getLength());
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
			BArray.or(pass, Level.avoid, passable);
		} else {
			System.arraycopy(pass, 0, passable, 0, Level.getLength());
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
	   int hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	   return (hour > 19 || hour < 7);
    }
    
    public static int getMonth(){
 	   int month=Calendar.getInstance().get(Calendar.MONTH);
 	   return month;
     }

}
