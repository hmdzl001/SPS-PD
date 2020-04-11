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

import com.watabou.utils.Bundle;

//move target 300 + d3*lvl

public class Statistics {

	public static int goldCollected;
	public static int deepestFloor;
	public static int realdeepestFloor;
	public static int enemiesSlain;
	public static int foodEaten;
	public static int eggBreak;
	public static int potionsCooked;
	public static int piranhasKilled;
	public static int archersKilled;
	public static int skeletonsKilled;
	public static int assassinsKilled;
	public static int albinoPiranhasKilled;
	public static int goldThievesKilled;
	public static int shadowYogsKilled;
	public static int nightHunt;
	public static int ankhsUsed;
	public static int ballsCooked;
	public static int waters;
	public static int sewerKills;
	public static int prisonKills;
	public static int petDies;

	public static float duration;
	public static int floormoves;
	public static int prevfloormoves;
	public static int moves;
	public static float time;
	
	
	public static boolean qualifiedForNoKilling = false;
	public static boolean completedWithNoKilling = false;
	

	public static boolean amuletObtained = false;
	public static boolean orbObtained = false;


	public static void reset() {

		goldCollected = 0;
		deepestFloor = 0;
		realdeepestFloor = 0;
		enemiesSlain = 0;
		foodEaten = 0;
		eggBreak = 0;
		potionsCooked = 0;
		
		piranhasKilled = 0;
		archersKilled = 0;
		assassinsKilled = 0;
		skeletonsKilled = 0;
		albinoPiranhasKilled = 0;
		goldThievesKilled = 0;
		shadowYogsKilled = 0;
		nightHunt = 0;
		ankhsUsed = 0;
		ballsCooked = 0;
		waters = 0;
		sewerKills = 0;
		prisonKills = 0;
		petDies = 0;

		duration = 0;
		moves = 0;
		floormoves = 0;
		prevfloormoves = 0;
		time = 360;

		qualifiedForNoKilling = false;

		amuletObtained = false;
		orbObtained = false;

	}

	private static final String GOLD = "score";
	private static final String DEEPEST = "maxDepth";
	private static final String REALDEEPEST = "maxDepthReal";
	private static final String SLAIN = "enemiesSlain";
	private static final String FOOD = "foodEaten";
	private static final String EGG = "eggBreak";
	private static final String ALCHEMY = "potionsCooked";
	private static final String PIRANHAS = "priranhas";
	private static final String WATERS = "waters";
	
	private static final String ARCHERS = "archers";
	private static final String SKELETONS = "skeletons";
	private static final String ASSASSINS = "assassins";
	private static final String APIRANHAS = "apiranhas";
	private static final String THIEVES = "thieves";
	private static final String SYOGS = "syogs";
	private static final String BALLS = "balls";
	private static final String PRISONKILLS = "prisonKills";
	private static final String SEWERKILLS = "sewerKills";
	private static final String PETDIES = "petDies";
	
	private static final String NIGHT = "nightHunt";
	private static final String ANKHS = "ankhsUsed";
	private static final String DURATION = "duration";
	private static final String FLOORMOVES = "floormoves";
	private static final String PREVFLOORMOVES = "prevfloormoves";
	private static final String MOVES = "moves";
	private static final String TIME = "time";
	private static final String AMULET = "amuletObtained";
	private static final String ORB = "orbObtained";

	public static void storeInBundle(Bundle bundle) {
		bundle.put(GOLD, goldCollected);
		bundle.put(DEEPEST, deepestFloor);
		bundle.put(REALDEEPEST, realdeepestFloor);
		bundle.put(SLAIN, enemiesSlain);
		bundle.put(FOOD, foodEaten);
		bundle.put(EGG, eggBreak);
		bundle.put(ALCHEMY, potionsCooked);
		bundle.put(PIRANHAS, piranhasKilled);
		bundle.put(ARCHERS, archersKilled);
		bundle.put(SKELETONS, skeletonsKilled);
		bundle.put(ASSASSINS, assassinsKilled);
		bundle.put(APIRANHAS, albinoPiranhasKilled);
		bundle.put(THIEVES, goldThievesKilled);
		bundle.put(SYOGS, shadowYogsKilled);
		bundle.put(BALLS, ballsCooked);
		bundle.put(NIGHT, nightHunt);
		bundle.put(ANKHS, ankhsUsed);
		bundle.put(DURATION, duration);
		bundle.put(FLOORMOVES, floormoves);
		bundle.put(PREVFLOORMOVES, prevfloormoves);
		bundle.put(MOVES, moves);
		bundle.put(TIME, time);
		bundle.put(AMULET, amuletObtained);
		bundle.put(ORB, orbObtained);
		bundle.put(WATERS, waters);
		bundle.put(SEWERKILLS, sewerKills);
		bundle.put(PRISONKILLS, prisonKills);
		bundle.put(PETDIES, petDies);
	}

	public static void restoreFromBundle(Bundle bundle) {
		goldCollected = bundle.getInt(GOLD);
		deepestFloor = bundle.getInt(DEEPEST);
		realdeepestFloor = bundle.getInt(REALDEEPEST);
		enemiesSlain = bundle.getInt(SLAIN);
		foodEaten = bundle.getInt(FOOD);
		eggBreak = bundle.getInt(EGG);
		potionsCooked = bundle.getInt(ALCHEMY);
		piranhasKilled = bundle.getInt(PIRANHAS);
		waters = bundle.getInt(WATERS);
		sewerKills = bundle.getInt(SEWERKILLS);
		prisonKills = bundle.getInt(PRISONKILLS);
		petDies = bundle.getInt(PETDIES);
		
		archersKilled = bundle.getInt(ARCHERS);
		skeletonsKilled = bundle.getInt(SKELETONS);
		assassinsKilled = bundle.getInt(ASSASSINS);
		albinoPiranhasKilled = bundle.getInt(APIRANHAS);
		goldThievesKilled = bundle.getInt(THIEVES);
		shadowYogsKilled = bundle.getInt(SYOGS);
		ballsCooked = bundle.getInt(BALLS);
		
		nightHunt = bundle.getInt(NIGHT);
		ankhsUsed = bundle.getInt(ANKHS);
		duration = bundle.getFloat(DURATION);
		floormoves = bundle.getInt(FLOORMOVES);
		prevfloormoves = bundle.getInt(PREVFLOORMOVES);
		moves = bundle.getInt(MOVES);
		time = bundle.getInt(TIME);
		amuletObtained = bundle.getBoolean(AMULET);
		orbObtained = bundle.getBoolean(ORB);	
		
	}

}
