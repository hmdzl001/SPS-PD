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

import  com.hmdzl.spspd.change.messages.Messages;

public class ResultDescriptions {

	// Mobs
	public static final String MOB = Messages.get(ResultDescriptions.class, "mob");
	public static final String UNIQUE = Messages.get(ResultDescriptions.class, "unique");
	public static final String NAMED = Messages.get(ResultDescriptions.class, "named");

	// Items
	public static final String ITEM = Messages.get(ResultDescriptions.class, "item");
	public static final String GLYPH = Messages.get(ResultDescriptions.class, "glyph");

	// Dungeon features
	public static final String TRAP = Messages.get(ResultDescriptions.class, "trap");

	// Debuffs & blobs
	public static final String BURNING = Messages.get(ResultDescriptions.class, "burning");
	public static final String HUNGER = Messages.get(ResultDescriptions.class, "hunger");
	public static final String POISON = Messages.get(ResultDescriptions.class, "poison");
	public static final String GAS = Messages.get(ResultDescriptions.class, "gas");
	public static final String BLEEDING = Messages.get(ResultDescriptions.class, "bleeding");
	public static final String OOZE = Messages.get(ResultDescriptions.class, "ooze");
	public static final String FALL = Messages.get(ResultDescriptions.class, "fall");
	public static final String COUNTDOWN = Messages.get(ResultDescriptions.class, "countdown");

	public static final String WIN = Messages.get(ResultDescriptions.class, "win");
}
