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
package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Dewdrop;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.medicine.GreenSpore;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Dewcatcher extends Plant {

	{
		image = 12;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		explodeDew(pos);
		//if (Random.Int(2)==0)
		//{}
		    
		
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_DEWCATCHER;

			plantClass = Dewcatcher.class;
			explantClass = ExDewcatcher.class;
			alchemyClass = PotionOfHealing.class;
		}
	}
	
	public static class ExDewcatcher extends Plant {
		{
			image = 12;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Level.NEIGHBOURS8){
				if (Level.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 3 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.level.drop(new GreenSpore(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
    public void explodeDew(int cell) {
		
		 for (int n : Level.NEIGHBOURS8) {
			 int c = cell + n;
			 if (c >= 0 && c < Level.getLength() && Level.passable[c]) {
				 
				if (Random.Int(10)==1){Dungeon.level.drop(new VioletDewdrop(), c).sprite.drop();}		
			    else if (Random.Int(5)==1){Dungeon.level.drop(new RedDewdrop(), c).sprite.drop();}
				else if (Random.Int(3)==1){Dungeon.level.drop(new YellowDewdrop(), c).sprite.drop();}
				else {Dungeon.level.drop(new Dewdrop(), c).sprite.drop();}
			}
		  }	
		
	}
		
	
}
