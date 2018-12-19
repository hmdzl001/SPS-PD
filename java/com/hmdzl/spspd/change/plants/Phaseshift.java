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
package com.hmdzl.spspd.change.plants;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.WaterOfTransmutation;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class Phaseshift extends Plant {

	{
		image = 14;
	}

	@Override
	public void activate(Char ch) {
		if (ch==null){
		 if (WellWater.affectCellPlant(pos)){
			super.activate(null);	
		    }
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_PHASEPITCHER;

			plantClass = Phaseshift.class;
			alchemyClass = PotionOfMight.class;				
		}
		
		@Override
		public Plant couch(int pos) {
			GameScene.add(Blob.seed(pos, 1, WaterOfTransmutation.class));	
		    return super.couch(pos);		    
		}
	}
	
		
	public static boolean checkWater(){
		
	WellWater water = (WellWater) Dungeon.level.blobs.get(WaterOfTransmutation.class);
	  if (water == null) {
		return false;
		} else if (water != null && water.volume==0) {
	    return false;
		} else {
		return true;
		}
	 } 
}
