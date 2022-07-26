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

	private static final String GOLD = "score";
	private static final String DEEPEST = "maxDepth";
	private static final String REALDEEPEST = "maxDepthReal";
	
	private static final String FINDSEED = "findseed";
	private static final String ASCETIC_SHIELD = "ashield";
	
	private static final String SLAIN = "enemiesSlain";
	private static final String FOOD = "foodEaten";
	private static final String EGG = "eggBreak";
	private static final String ALCHEMY = "potionsCooked";
	private static final String PIRANHAS = "priranhas";
	private static final String WATERS = "waters";
	private static final String ARCHERS = "archers";
	private static final String SKELETONS = "skeletons";
	private static final String ASSASSINS = "assassins";
	private static final String ORCS = "orcs";
	private static final String APIRANHAS = "apiranhas";
	private static final String THIEVES = "thieves";
	private static final String NIGHT = "nightHunt";
	private static final String ANKHS = "ankhsUsed";
	private static final String DURATION = "duration";
	private static final String FLOORMOVES = "floormoves";
	private static final String PREVFLOORMOVES = "prevfloormoves";
	private static final String MOVES = "moves";
	private static final String TIME = "time";
	private static final String AMULET = "amuletObtained";
	private static final String ORB = "orbObtained";
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
	public static int orcsKilled;
	public static int albinoPiranhasKilled;
	public static int goldThievesKilled;
	public static int nightHunt;
	public static int ankhsUsed;
	public static float duration;
	public static int floormoves;
	public static int prevfloormoves;
	public static int moves;
	public static int findseed;
	public static int ashield;
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
		findseed = 0;
		ashield = 0;
		piranhasKilled = 0;
		archersKilled = 0;
		assassinsKilled = 0;
		orcsKilled = 0;
		skeletonsKilled = 0;
		albinoPiranhasKilled = 0;
		goldThievesKilled = 0;
		nightHunt = 0;
		ankhsUsed = 0;

		duration = 0;
		moves = 0;
		floormoves = 0;
		prevfloormoves = 0;
		time = 360;

		qualifiedForNoKilling = false;

		amuletObtained = false;
		orbObtained = false;

	}

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
		bundle.put(ORCS, orcsKilled);
		bundle.put(APIRANHAS, albinoPiranhasKilled);
		bundle.put(THIEVES, goldThievesKilled);
		bundle.put(NIGHT, nightHunt);
		bundle.put(ANKHS, ankhsUsed);
		bundle.put(DURATION, duration);
		bundle.put(FLOORMOVES, floormoves);
		bundle.put(PREVFLOORMOVES, prevfloormoves);
		bundle.put(MOVES, moves);
		bundle.put(TIME, time);
		bundle.put(FINDSEED, findseed);
		bundle.put(ASCETIC_SHIELD, ashield);
		bundle.put(AMULET, amuletObtained);
		bundle.put(ORB, orbObtained);
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

		archersKilled = bundle.getInt(ARCHERS);
		skeletonsKilled = bundle.getInt(SKELETONS);
		assassinsKilled = bundle.getInt(ASSASSINS);
		orcsKilled = bundle.getInt(ORCS);
		albinoPiranhasKilled = bundle.getInt(APIRANHAS);
		goldThievesKilled = bundle.getInt(THIEVES);
		
		nightHunt = bundle.getInt(NIGHT);
		ankhsUsed = bundle.getInt(ANKHS);
		duration = bundle.getFloat(DURATION);
		floormoves = bundle.getInt(FLOORMOVES);
		prevfloormoves = bundle.getInt(PREVFLOORMOVES);
		moves = bundle.getInt(MOVES);
		time = bundle.getInt(TIME);
		findseed = bundle.getInt(FINDSEED);
		ashield = bundle.getInt(ASCETIC_SHIELD);
		amuletObtained = bundle.getBoolean(AMULET);
		orbObtained = bundle.getBoolean(ORB);	
		
	}

}
