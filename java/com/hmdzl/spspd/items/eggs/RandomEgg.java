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

import java.util.ArrayList;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.Hero;

import com.hmdzl.spspd.actors.mobs.pets.ButterflyPet;
import com.hmdzl.spspd.actors.mobs.pets.Chocobo;
import com.hmdzl.spspd.actors.mobs.pets.Datura;
import com.hmdzl.spspd.actors.mobs.pets.DogPet;
import com.hmdzl.spspd.actors.mobs.pets.Fly;
import com.hmdzl.spspd.actors.mobs.pets.GentleCrab;
import com.hmdzl.spspd.actors.mobs.pets.Kodora;
import com.hmdzl.spspd.actors.mobs.pets.Monkey;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.actors.mobs.pets.PigPet;
import com.hmdzl.spspd.actors.mobs.pets.RibbonRat;
import com.hmdzl.spspd.actors.mobs.pets.Snake;
import com.hmdzl.spspd.actors.mobs.pets.Stone;

import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class RandomEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;

		{
		image = ItemSpriteSheet.RANDOWNEGG;

		stackable = false;
		}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {
			/*if (Random.Int(120)<20){
			Snake pet = new Snake();
			eggHatch(pet);
			} else if (Random.Int(100)< 20) {
                Fly pet = new Fly();
                eggHatch(pet);
			} else if (Random.Int(80)<20) {
				RibbonRat pet = new RibbonRat();
				eggHatch(pet);
			} else if (Random.Int(60)<20) {
				GentleCrab pet = new GentleCrab();
				eggHatch(pet);
            } else if (Random.Int(40)<20) {
                Stone pet = new Stone();
                eggHatch(pet);
            } else {
                Monkey pet = new Monkey();
                eggHatch(pet);
            }*/

			switch (Random.Int (12)) {
				case 0:
				DogPet pet = new DogPet();
					eggHatch(pet);
					break;
				case 1:
					ButterflyPet pet1 = new ButterflyPet();
					eggHatch(pet1);
					break;
				case 2:
					Chocobo pet2 = new Chocobo();
					eggHatch(pet2);
					break;
				case 3:
					Kodora pet3 = new Kodora();
					eggHatch(pet3);
					break;
				case 4:
					Snake pet4 = new Snake();
					eggHatch(pet4);
					break;
				case 5:
					Fly pet5 = new Fly();
					eggHatch(pet5);
					break;
				case 6:
					RibbonRat pet6 = new RibbonRat();
					eggHatch(pet6);
					break;
				case 7:
					GentleCrab pet7 = new GentleCrab();
					eggHatch(pet7);
					break;
				case 8:
					Stone pet8 = new Stone();
					eggHatch(pet8);
					break;
				case 9:
					Monkey pet9 = new Monkey();
					eggHatch(pet9);
					break;
				case 10:
					PigPet pet10 = new PigPet();
					eggHatch(pet10);
					break;
				case 11:
					Datura pet11 = new Datura();
					eggHatch(pet11);
					break;
															
			}


            Statistics.eggBreak++;
            Badges.validateEggBreak();
            hero.next();
		}
		
		else if (action.equals(AC_SHAKE)) {

			GLog.w(Messages.get(Egg.class,"kick"));
			  				
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
			  
			  Dungeon.hero.spend(CocoCatEgg.TIME_TO_USE);
			  GLog.w(Messages.get(Egg.class,"notready"));

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
		return 500 * quantity;
	}

}
