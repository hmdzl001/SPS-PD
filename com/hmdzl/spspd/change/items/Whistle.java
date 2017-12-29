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

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Bunny;
import com.hmdzl.spspd.change.actors.mobs.pets.CocoCat;
import com.hmdzl.spspd.change.actors.mobs.pets.Fairy;
import com.hmdzl.spspd.change.actors.mobs.pets.Fly;
import com.hmdzl.spspd.change.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.change.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.change.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Snake;
import com.hmdzl.spspd.change.actors.mobs.pets.Spider;
import com.hmdzl.spspd.change.actors.mobs.pets.Stone;
import com.hmdzl.spspd.change.actors.mobs.pets.SugarplumFairy;
import com.hmdzl.spspd.change.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.change.actors.mobs.pets.VioletDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.bee;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Whistle extends Item {

	
	public static final float TIME_TO_USE = 1;

	public static final String AC_CALL = "CALL";
	
		{
		//name = "whistle";
		image = ItemSpriteSheet.WHISTLE;
		unique = true;
		stackable = false;
		}
	
				
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_CALL);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		
		if (action == AC_CALL) {	
			petCall();
			GLog.w(Messages.get(this,"tweet"));
									  				
		} else {

			super.execute(hero, action);

		}
			
	}	
	
	public int getSpawnPos(){
		int newPos = -1;
		int pos = Dungeon.hero.pos;
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS8) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
			
		return newPos;
	}
	
	
	public boolean petCall () {	
		
	      boolean callResult = false;		
		  int spawnPos = getSpawnPos();
		  if (spawnPos != -1 && Dungeon.hero.haspet) {
				
			  int petpos = -1;
				int heropos = Dungeon.hero.pos;
				if (Actor.findChar(heropos) != null) {
					//GLog.i("Check Pet 2");
					ArrayList<Integer> candidates = new ArrayList<Integer>();
					boolean[] passable = Level.passable;

					for (int n : Level.NEIGHBOURS8) {
						int c = heropos + n;
						if (passable[c] && Actor.findChar(c) == null) {
							candidates.add(c);
						}
					}

					petpos = candidates.size() > 0 ? Random.element(candidates) : -1;
				}

				if (petpos != -1 && Dungeon.hero.haspet) {
					
					 PET petCheck = checkpet();
					  if(petCheck!=null){
						  
						  petCheck.destroy();
					      petCheck.sprite.killAndErase();
					  }  
										
				   if (Dungeon.hero.petType==1){
						 Spider pet = new Spider();
						  spawnPet(pet,petpos,heropos);					 
						}
				   if (Dungeon.hero.petType==2){
					  bee pet = new bee();
					  spawnPet(pet,petpos,heropos);					 
					}
				   if (Dungeon.hero.petType==3){
					      Velocirooster pet = new Velocirooster();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==4){
						  RedDragon pet = new RedDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==5){
					   GreenDragon pet = new GreenDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==6){
						  VioletDragon pet = new VioletDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==7){
					   BlueDragon pet = new BlueDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==8){
					   Scorpion pet = new Scorpion();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==9){
					   Bunny pet = new Bunny();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==10){
					   Fairy pet = new Fairy();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==11){
					   SugarplumFairy pet = new SugarplumFairy();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==12){
					   ShadowDragon pet = new ShadowDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
					if (Dungeon.hero.petType==13){
					   CocoCat pet = new CocoCat();
						  spawnPet(pet,petpos,heropos);					 
				   }				
				   if (Dungeon.hero.petType==14){
					   LeryFire pet = new LeryFire();
						  spawnPet(pet,petpos,heropos);					 
				   }	
				   if (Dungeon.hero.petType==15){
					   GoldDragon pet = new GoldDragon();
						  spawnPet(pet,petpos,heropos);					 
				   }
				   if (Dungeon.hero.petType==16){
					   Snake pet = new Snake();
						  spawnPet(pet,petpos,heropos);					 
				   }	
				   if (Dungeon.hero.petType==17){
					   Fly pet = new Fly();
						  spawnPet(pet,petpos,heropos);					 
				   }	
				   if (Dungeon.hero.petType==18){
					   Stone pet = new Stone();
						  spawnPet(pet,petpos,heropos);					 
				   }	
				   if (Dungeon.hero.petType==19){
					   Monkey pet = new Monkey();
						  spawnPet(pet,petpos,heropos);					 
				   }					   
				   callResult = true;
				   GLog.w(Messages.get(this,"arrive"));
				  
				   Dungeon.hero.spend(Whistle.TIME_TO_USE);
				}
				
		  } else {
			  
			  Dungeon.hero.spend(Whistle.TIME_TO_USE);

		  }
		  
		  return callResult;
	}
	
	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
	public void spawnPet(PET pet, Integer petpos, Integer heropos){
		  pet.spawn(Dungeon.hero.petLevel);
		  pet.HP = Dungeon.hero.petHP;
		  pet.pos = petpos;
		  pet.state = pet.HUNTING;
		  pet.kills = Dungeon.hero.petKills;
		  pet.experience = Dungeon.hero.petExperience;
		  pet.cooldown = Dungeon.hero.petCooldown;

			GameScene.add(pet);
			Actor.addDelayed(new Pushing(pet, heropos, petpos), -1f);
	}
	
		
	@Override
	public int price() {
		return 500 * quantity;
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
