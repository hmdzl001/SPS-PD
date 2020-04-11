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

public class Challenges {

	public static final int ITEM_PHOBIA = 1;
	public static final int LISTLESS = 2;
	public static final int NIGHTMARE_VIRUS = 4;
	public static final int ENERGY_LOST = 8;
	public static final int DEW_REJECTION = 16;
	public static final int DARKNESS = 32;
	public static final int ABRASION = 64;
	public static final int TEST_TIME = 128;

	public static final String[] NAME_IDS = {
			"item_phobia",
			"listless",
			"nightmare_virus",
			"energy_lost",
			"dew_rejection",
			"darkness",
			"abrasion",
		    "test_time"
		};

	public static final int[] MASKS = { 
	        ITEM_PHOBIA, LISTLESS, NIGHTMARE_VIRUS,
			ENERGY_LOST, DEW_REJECTION, DARKNESS, ABRASION, TEST_TIME};

}