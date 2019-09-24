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
package com.hmdzl.spspd.change.items.eggs;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.GoldThief;
import com.hmdzl.spspd.change.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.change.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.PET;
import com.hmdzl.spspd.change.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.change.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.Spider;

import com.hmdzl.spspd.change.actors.mobs.pets.Velocirooster;
import com.hmdzl.spspd.change.actors.mobs.pets.VioletDragon;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Egg extends Item {

	private static final String TXT_PREVENTING = "This is not the best place to try that.";
	private static final String TXT_NOTREADY = "Something tells you it's not ready yet.";
	private static final String TXT_YOLK = "Ewww. Gross, uncooked egg of a random creature.";
	private static final String TXT_HATCH = "Something hatches!";
	private static final String TXT_SCRATCH = "Something scratches back!";
	private static final String TXT_SLITHERS = "Something squirms inside!";
	private static final String TXT_KICKS = "Something powerful kicks back!";
	private static final String TXT_SLOSH = "Just some sloshing around.";
	private static final String TXT_ZAP = "Ouch! Something zaps you back!.";
	
	public static final float TIME_TO_USE = 1;

	public static final String AC_BREAK = "BREAK";
	public static final String AC_SHAKE = "SHAKE";
	
	public static final int RED_DRAGON = 20;
	public static final int GREEN_DRAGON = 20;
	public static final int BLUE_DRAGON = Dungeon.getMonth()==11 ? 10 : 20;
	public static final int VIOLET_DRAGON = 20;
	public static final int SPIDER = 1000;
	public static final int SCORPION = 2000;
	public static final int VELOCIROOSTER = 5;
	public static final int LIGHT_DRAGON = 20;
	public static final int SHADOW_DRAGON = 20;
	

		{
		//name = "egg";
		image = ItemSpriteSheet.EGG;
		unique = true;
		stackable = false;
		}
		
		public int startMoves = 0;
		public int moves = 0;
		public int burns = 0;
		public int freezes = 0;
		public int poisons = 0;
		public int lits = 0;
		public int summons = 0;
		public int light = 0;
		
		private static final String STARTMOVES = "startMoves";
		private static final String MOVES = "moves";
		private static final String BURNS = "burns";
		private static final String FREEZES = "freezes";
		private static final String POISONS = "poisons";
		private static final String LITS = "lits";
		private static final String SUMMONS = "summons";
		private static final String LIGHT = "light";
		
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(STARTMOVES, startMoves);
			bundle.put(MOVES, moves);
			bundle.put(BURNS, burns);
			bundle.put(FREEZES, freezes);
			bundle.put(POISONS, poisons);
			bundle.put(LITS, lits);
			bundle.put(SUMMONS, summons);
			bundle.put(LIGHT, light);
			
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			startMoves = bundle.getInt(STARTMOVES);
			moves = bundle.getInt(MOVES);
			burns = bundle.getInt(BURNS);
			freezes = bundle.getInt(FREEZES);
			poisons = bundle.getInt(POISONS);
			lits = bundle.getInt(LITS);
			summons = bundle.getInt(SUMMONS);
			light = bundle.getInt(LIGHT);
			
		}
						
		public int checkMoves () {
			return moves;
		}
		public int checkBurns () {
			return burns;
		}
		public int checkFreezes () {
			return freezes;
		}
		public int checkPoisons () {
			return poisons;
		}
		public int checkLits () {
			return lits;
		}
		public int checkSummons () {
			return summons;
		}
		
		public int checkLight () {
			return light;
		}		
		
		@Override
		public boolean doPickUp(Hero hero) {
				
				GLog.w(Messages.get(Egg.class,"warmhome"));
				
				Egg egg = hero.belongings.getItem(Egg.class);
				if (egg!=null){
					GLog.w(Messages.get(Egg.class,"onlyone"));
				}
						 
			 return super.doPickUp(hero);				
		}	
		
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (Dungeon.hero.haspet == false & Dungeon.depth < 26) actions.add(AC_BREAK);
		actions.add(AC_SHAKE);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_BREAK)) {
			
			boolean hatch = false;
			if (checkFreezes()>=BLUE_DRAGON && checkPoisons()>=VIOLET_DRAGON && checkBurns()>=RED_DRAGON && checkLits()>=GREEN_DRAGON 
			          && checkLight()>=SHADOW_DRAGON && checkSummons()>=LIGHT_DRAGON && checkMoves()>=SCORPION){
				if (Dungeon.getMonth()==9 || Random.Int(50) == 0){
				   BugDragon pet = new BugDragon();
				   eggHatch(pet);
					  hatch=true;
				 } else {
				  GoldDragon pet = new GoldDragon();
				  eggHatch(pet);
				  hatch=true;
				 }		
			 } else if (checkLight()>= 20 ){
                  ShadowDragon pet = new ShadowDragon();
                  eggHatch(pet);
				  hatch=true;
                 //spawn ShadowDragon
				
              } else if (checkFreezes()>=5 && checkPoisons()>=5 && checkBurns()>=5 && checkLits()>=5 && checkMoves()>=50){
                  LeryFire pet = new LeryFire();
                  eggHatch(pet);
				  hatch=true;
                 //spawn leryfire
			  } else if (checkFreezes()>=BLUE_DRAGON) {
				  BlueDragon pet = new BlueDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn ice dragon	
			  } else if (checkSummons()>=LIGHT_DRAGON) {
				  LightDragon pet = new LightDragon();
				  eggHatch(pet);
				  hatch=true;				  
			  } else if (checkPoisons()>=VIOLET_DRAGON) {
				  VioletDragon pet = new VioletDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn green dragon
			  } else if (checkLits()>=GREEN_DRAGON) {
				  GreenDragon pet = new GreenDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn lit dragon
			  } else if (checkBurns()>=RED_DRAGON) {
				  RedDragon pet = new RedDragon();
				  eggHatch(pet);
				  hatch=true;
				 //spawn red dragon	
			  } else if (checkBurns()>=VELOCIROOSTER) {
				  Velocirooster pet = new Velocirooster();
				  eggHatch(pet);
				  hatch=true;
				  //spawn velocirooster
			  } else if (checkMoves()>=SCORPION) {
				  Scorpion pet = new Scorpion();
				  eggHatch(pet);
				  hatch=true;
				  //spawn spider
			  } else if (checkMoves()>=SPIDER) {
				  Spider pet = new Spider();
				  eggHatch(pet);
				  hatch=true;
				  //spawn bat
			  }	else if (checkMoves()>=100) {
              	Dungeon.level.drop(new RandomEgg(),hero.pos).sprite.drop();

			}
		           
		  if (!hatch)	{
			  detach(Dungeon.hero.belongings.backpack);		 			 
			  GLog.w(Messages.get(Egg.class,"yolk"));
		  }

			Statistics.eggBreak++;
			Badges.validateEggBreak();
			hero.next();
		
		}
		
		else if (action.equals(AC_SHAKE)) {
						            
			 boolean alive = false;
			  

			  
			  if (checkFreezes()>=BLUE_DRAGON && checkPoisons()>=VIOLET_DRAGON && checkBurns()>=RED_DRAGON && checkLits()>=GREEN_DRAGON 
			          && checkLight()>=SHADOW_DRAGON && checkSummons()>=LIGHT_DRAGON && checkMoves()>=SCORPION) {
				   GLog.w(Messages.get(Egg.class,"zap"));
				   Dungeon.hero.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				   Dungeon.hero.sprite.flash();
				   Dungeon.hero.damage(1, this);	
				   alive = true;

			  } else if (checkSummons()>=SHADOW_DRAGON || checkLight()>=LIGHT_DRAGON || checkFreezes()>=BLUE_DRAGON
			  || checkPoisons()>=VIOLET_DRAGON || checkLits()>=GREEN_DRAGON || checkBurns()>=RED_DRAGON) {
				 GLog.w(Messages.get(Egg.class,"kick"));
				 alive = true;

			  } else if (checkBurns()>=VELOCIROOSTER) {
				  GLog.w(Messages.get(Egg.class,"scratch"));
				  alive = true;
				  //spawn velocirooster
			  } else if (checkMoves()>=SCORPION) {
				  GLog.w(Messages.get(Egg.class,"slithers"));
				  alive = true;
				  //spawn spider
			  } else if (checkMoves()>=SPIDER) {
				  GLog.w(Messages.get(Egg.class,"slithers"));
				  alive = true;
				  //spawn scorpion
			  }
			  
			  if (!alive)	{				  
				  GLog.w(Messages.get(Egg.class,"slosh"));
			  }
			  				
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
	
	
	public void eggHatch (PET pet) {		
		
		  int spawnPos = getSpawnPos();
		  if (spawnPos != -1 && !Dungeon.hero.haspet) {
				
				pet.spawn(1);
				pet.HP = pet.HT;
				pet.pos = spawnPos;
				pet.state = pet.HUNTING;

				GameScene.add(pet);
				Actor.addDelayed(new Pushing(pet, Dungeon.hero.pos, spawnPos), -1f);

				pet.sprite.alpha(0);
				pet.sprite.parent.add(new AlphaTweener(pet.sprite, 1, 0.15f));
				
				detach(Dungeon.hero.belongings.backpack);		 			 
				GLog.w(Messages.get(Egg.class,"hatch"));
				Dungeon.hero.haspet=true;
				
				assignPet(pet);
				
		  } else {
			  
			  Dungeon.hero.spend(Egg.TIME_TO_USE);
			  GLog.w(Messages.get(Egg.class,"notready"));
			  Statistics.eggBreak--;
			  Badges.validateEggBreak();

		  }
	}


	private void assignPet(PET pet){
		
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		   
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;		
	}
		
	@Override
	public int price() {
		return 50 * quantity;
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
		String info = desc();
		info += "\n\n" + Messages.get(this, "moves",moves);
		info += "\n" + Messages.get(this, "burns",burns);
		info += "\n" + Messages.get(this, "freezes",freezes);
		info += "\n" + Messages.get(this, "poisons",poisons);
		info += "\n" + Messages.get(this, "lits",lits);
		info += "\n" + Messages.get(this, "summons",summons);
		info += "\n" + Messages.get(this, "light",light);
		
		return info;	
	}
}
