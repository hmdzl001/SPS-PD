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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.pets.Haro;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.tweeners.AlphaTweener;

public class HaroEgg extends Egg {
	
	public static final float TIME_TO_USE = 1;
		{
		//name = "Haro egg";
		image = ItemSpriteSheet.HARO_THE_PET;
		unique = true;
		stackable = false;
		}


	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_BREAK)) {

			Haro pet = new Haro();
			eggHatch(pet);
			
		  hero.next();
		
		}
		
		else if (action.equals(AC_SHAKE)) {

				  GLog.w(Messages.get(Egg.class,"kick"));
			  				
		} else {

			super.execute(hero, action);

		}
		
		
		
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
			  
			  Dungeon.hero.spend(LeryFireEgg.TIME_TO_USE);
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
