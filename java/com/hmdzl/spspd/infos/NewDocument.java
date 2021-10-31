/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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

package com.hmdzl.spspd.infos;

import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;

import java.util.Collection;
import java.util.LinkedHashMap;

public enum NewDocument {
	
	STORY_GUIDE,
	//ALCHEMY_GUIDE(ItemSpriteSheet.ALCH_PAGE);
	
	NewDocument;

	private LinkedHashMap<String, Boolean> pages = new LinkedHashMap<>();
	
	public Collection<String> pages(){
		return pages.keySet();
	}

	public boolean hasPage( String page ){
		return pages.containsKey(page) && pages.get(page);
	}
	
	public String title(){
		return Messages.get( this, name() + ".title");
	}

	public String pageTitle( String page ){
		return Messages.get( this, name() + "." + page + ".title");
	}

	public String pageBody( String page ){
		return Messages.get( this, name() + "." + page + ".body");
	}


	public static final String GUIDE_INTRO_PAGE = "Intro";
	//public static final String GUIDE_SEARCH_PAGE = "Examining_and_Searching";
	
	static {
		STORY_GUIDE.pages.put(GUIDE_INTRO_PAGE, true);
		STORY_GUIDE.pages.put("tester", true);
		STORY_GUIDE.pages.put("tower", true);
		STORY_GUIDE.pages.put("def", true);
		STORY_GUIDE.pages.put("palace", true);
		STORY_GUIDE.pages.put("ruins", true);
		STORY_GUIDE.pages.put("horn", true);
		STORY_GUIDE.pages.put("tribe", true);
		STORY_GUIDE.pages.put("homeless", true);
		STORY_GUIDE.pages.put("overseas", true);

		//sewers
		//ALCHEMY_GUIDE.pages.put("Potions",              DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Stones",               DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Energy_Food",          DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Bombs",                DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Darts",              DeviceCompat.isDebug());

		//prison
		//ALCHEMY_GUIDE.pages.put("Exotic_Potions",       DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Exotic_Scrolls",       DeviceCompat.isDebug());

		//caves
		//ALCHEMY_GUIDE.pages.put("Catalysts",            DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Brews_Elixirs",        DeviceCompat.isDebug());
		//ALCHEMY_GUIDE.pages.put("Spells",               DeviceCompat.isDebug());
	}
}
