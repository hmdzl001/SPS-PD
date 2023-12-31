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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class TownReturnBeacon extends Item {

	
	private static final String TXT_INFO = "Return beacon is an intricate magic device, that allows you to return to a place you have already been.";
	private static final String TXT_CREATURES = "Psychic aura of neighbouring creatures doesn't allow you to use the lloyd's beacon at this moment.";

	public static final float TIME_TO_USE = 1;

	//public static final String AC_SET = "SET";
	public static final String AC_RETURN = "RETURN";
	public static final String AC_RETURNTOWN = "RETURNTOWN";
	public static final String FAIL = "Strong magic aura of this place prevents you from using the beacon!";

	private int returnDepth = -1;
	private int returnPos;

	{
		name = "dolyahaven return beacon";
		image = ItemSpriteSheet.BEACON;

		unique = true;
	}
	
	private static final String DEPTH = "depth";
	private static final String POS = "pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEPTH, returnDepth);
		if (returnDepth != -1) {
			bundle.put(POS, returnPos);
		}
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		returnDepth = bundle.getInt(DEPTH);
		returnPos = bundle.getInt(POS);
	}
	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.dungeondepth ==55 && returnDepth>55 && !Badges.checkOtilukeRescued()){
		actions.add(AC_RETURN);
		}
		if(Dungeon.dungeondepth >55){
		   actions.add(AC_RETURNTOWN);	
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_RETURNTOWN)) {
		
		   if (Dungeon.bossLevel() || Dungeon.depth.locked) {
		     	hero.spend(TIME_TO_USE);
			    GLog.w(FAIL);
			    return;
		    }

		  for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			   if (Actor.findChar(hero.pos + Floor.NEIGHBOURS8[i]) != null) {
				GLog.w(TXT_CREATURES);
				return;
			   }
		   }
		
		}
		
	     if (action.equals(AC_RETURNTOWN)) {
	    	 
	    	 hero.spend(TIME_TO_USE);
	    	 
	    	    returnDepth = Dungeon.dungeondepth;
				returnPos = hero.pos;

				Buff buff = Dungeon.hero
						.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = 55;
				InterlevelScene.returnPos = 1925;
				Game.switchScene(InterlevelScene.class);
				
	     } else if (action.equals(AC_RETURN)) {
	    	 
	    	 hero.spend(TIME_TO_USE);
	    	  
	    	 Buff buff = Dungeon.hero
						.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene(InterlevelScene.class);
				
		} else {

			super.execute(hero, action);

		}
	}

	

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	@Override
	public String info() {
		return TXT_INFO;
	}
}
