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
package com.hmdzl.spspd.items.eggs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.pets.BlueDragon;
import com.hmdzl.spspd.actors.mobs.pets.BlueGirl;
import com.hmdzl.spspd.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.actors.mobs.pets.GreenDragon;
import com.hmdzl.spspd.actors.mobs.pets.LeryFire;
import com.hmdzl.spspd.actors.mobs.pets.LightDragon;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.actors.mobs.pets.RedDragon;
import com.hmdzl.spspd.actors.mobs.pets.Scorpion;
import com.hmdzl.spspd.actors.mobs.pets.ShadowDragon;
import com.hmdzl.spspd.actors.mobs.pets.VioletDragon;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.sellitem.VIPcard;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Egg extends Item {
	
	public static final float TIME_TO_USE = 1;

	public static final String AC_BREAK = "BREAK";

		{
		//name = "egg";
		image = ItemSpriteSheet.EGG;

		stackable = false;
		}

		public int moves = 0;
		public int burns = 0;
		public int freezes = 0;
		public int poisons = 0;
		public int lits = 0;
		public int darks = 0;
		public int lights = 0;

		private static final String MOVES = "moves";
		private static final String BURNS = "burns";
		private static final String FREEZES = "freezes";
		private static final String POISONS = "poisons";
		private static final String LITS = "lits";
		private static final String DARKS = "darks";
		private static final String LIGHTS = "lights";
		
		
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(MOVES, moves);
			bundle.put(BURNS, burns);
			bundle.put(FREEZES, freezes);
			bundle.put(POISONS, poisons);
			bundle.put(LITS, lits);
			bundle.put(DARKS, darks);
			bundle.put(LIGHTS, lights);
			
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			moves = bundle.getInt(MOVES);
			burns = bundle.getInt(BURNS);
			freezes = bundle.getInt(FREEZES);
			poisons = bundle.getInt(POISONS);
			lits = bundle.getInt(LITS);
			darks = bundle.getInt(DARKS);
			lights = bundle.getInt(LIGHTS);
			
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
		public int checkDarks() {
			return darks;
		}
		public int checkLight () {
			return lights;
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
		if (Dungeon.hero.haspet == false ||  Dungeon.depth == 50) actions.add(AC_BREAK);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_BREAK)) {
			if (Random.Int(10) == 0) {
				Dungeon.level.drop(new VIPcard(), hero.pos).sprite.drop();
			}
			boolean hatch = false;
			if (checkFreezes()>=20 && checkPoisons()>=20 && checkBurns()>=20 && checkLits()>=20
			          && checkLight()>=20 && checkDarks()>=20 && checkMoves()>=2000) {
				if (Dungeon.getMonth() == 9 || Random.Int(50) == 0) {
					BugDragon pet = new BugDragon();
					eggHatch(pet);
					hatch = true;
				} else {
					GoldDragon pet = new GoldDragon();
					eggHatch(pet);
					hatch = true;
				}
			} else if (checkPoisons()>=30 && checkLight()>=66 && checkLight()<=122){
				BlueGirl pet = new BlueGirl();
				eggHatch(pet);
				hatch=true;
				//spawn leryfire	 }
			} else if (checkFreezes()>=5 && checkPoisons()>=5 && checkBurns()>=5 && checkLits()>=5 && checkMoves()>=50){
					LeryFire pet = new LeryFire();
					eggHatch(pet);
					hatch=true;
					//spawn leryfire	 }
			 } else if (checkLight()>= 20 ){
                  ShadowDragon pet = new ShadowDragon();
                  eggHatch(pet);
				  hatch=true;
                 //spawn ShadowDragon

			  } else if (checkFreezes()>=20) {
				  BlueDragon pet = new BlueDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn ice dragon	
			  } else if (checkDarks()>=20) {
				  LightDragon pet = new LightDragon();
				  eggHatch(pet);
				  hatch=true;				  
			  } else if (checkPoisons()>=20) {
				  VioletDragon pet = new VioletDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn green dragon
			  } else if (checkLits()>=20) {
				  GreenDragon pet = new GreenDragon();
				  eggHatch(pet);
				  hatch=true;
				  //spawn shockhit dragon
			  } else if (checkBurns()>=20) {
				  RedDragon pet = new RedDragon();
				  eggHatch(pet);
				  hatch=true;
				 //spawn red dragon
			  } else if (checkMoves()>=2000) {
				  Scorpion pet = new Scorpion();
				  eggHatch(pet);
				  hatch=true;
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
				
				pet.spawn();
				pet.HP = pet.HT;
				pet.pos = spawnPos;
				pet.state = pet.HUNTING;

				GameScene.add(pet);
				Actor.addDelayed(new Pushing(pet, Dungeon.hero.pos, spawnPos), -1f);

				pet.sprite.alpha(0);
				pet.sprite.parent.add(new AlphaTweener(pet.sprite, 1, 0.15f));
				
				detach(Dungeon.hero.belongings.backpack);		 			 
				GLog.w(Messages.get(Egg.class,"hatch"));
			  assignPet(pet);

		  } else if (Dungeon.depth == 50) {

			  pet.spawn();
			  pet.HP = pet.HT;
			  pet.pos = spawnPos;
			  pet.state = pet.HUNTING;

			  GameScene.add(pet);
			  Actor.addDelayed(new Pushing(pet, Dungeon.hero.pos, spawnPos), -1f);

			  pet.sprite.alpha(0);
			  pet.sprite.parent.add(new AlphaTweener(pet.sprite, 1, 0.15f));

			  detach(Dungeon.hero.belongings.backpack);
			  GLog.w(Messages.get(Egg.class,"hatch"));

		  } else {
			  
			  Dungeon.hero.spend(Egg.TIME_TO_USE);
			  GLog.w(Messages.get(Egg.class,"notready"));
			  Statistics.eggBreak--;
			  Badges.validateEggBreak();

		  }
	}
	private void assignPet(PET pet){
		  Dungeon.hero.haspet=true;
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petCooldown=pet.cooldown;		
	}

	public void rollpet() {

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
		info += "\n" + Messages.get(this, "darks", darks);
		info += "\n" + Messages.get(this, "lights",lights);
		
		return info;	
	}
}
