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
package com.hmdzl.spspd.change.items;

import java.io.IOException;
import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.items.artifacts.DriedRose;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.AmuletScene;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

public class Palantir extends Item {

	public static final float TIME_TO_USE = 1;

	public static final String AC_PORT = "PORT";

	private int specialLevel = 99;
	private int returnDepth = 1;
	private int returnPos;
	private boolean used = false;

	{
		//name = "palantir";
		image = ItemSpriteSheet.PALANTIR;

		stackable = false;
		unique = true;
	}
	
	private static final String DEPTH = "depth";
	private static final String POS = "pos";
	private static final String USED = "used";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEPTH, returnDepth);
		bundle.put(USED, used);
		if (returnDepth != 55) {
			bundle.put(POS, returnPos);
		}
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		returnDepth = bundle.getInt(DEPTH);
		returnPos = bundle.getInt(POS);
		used = bundle.getBoolean(USED);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if((!used || Dungeon.depth==99) && !Dungeon.level.locked && !hero.petfollow){actions.add(AC_PORT);}		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {		

		if (action == AC_PORT) {
			
			hero.spend(TIME_TO_USE);

			Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
			
			if (buff != null)
				buff.detach();

			for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
				
            if (Dungeon.depth<99 && !Dungeon.bossLevel()){
            	
            	returnDepth = Dungeon.depth;
       			returnPos = hero.pos;
       			used=true;
				InterlevelScene.mode = InterlevelScene.Mode.PALANTIR;
				
			} else {

				checkPetPort();
				removePet();
				
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;	
				
			}               
                
				InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene(InterlevelScene.class);
	
		} else {

			super.execute(hero, action);

		}
	}
	
	public void reset() {
		returnDepth = 55;
	}
	

	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c =  Dungeon.hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	private void checkPetPort(){
		PET pet = checkpet();
		if(pet!=null && checkpetNear()){
		  //GLog.i("I see pet");
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		  Dungeon.hero.petKills=pet.kills;	
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;
		  pet.destroy();
		  Dungeon.hero.petfollow=true;
		} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
			Dungeon.hero.petfollow=true;
		} else {
			Dungeon.hero.petfollow=false;
		}

	}
	private void removePet(){
		if (Dungeon.hero.haspet && !Dungeon.hero.petfollow){
		 for (Mob mob : Dungeon.level.mobs) {
				if(mob instanceof PET) {				 
					Dungeon.hero.haspet=false;
					Dungeon.hero.petCount++;
					mob.destroy();				
				}
			  }
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
}
