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
package com.hmdzl.spspd.levels;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.food.meatfood.SmallMeat;
import com.hmdzl.spspd.items.misc.LuckyBadge;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

public class SafeLevel extends Floor {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
	}


	@Override
	public String tilesTex() {
		return Assets.TILES_PUZZLE;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		
		map = SaveRoomLayouts.SAFE_ROOM_DEFAULT.clone();
	
		decorate();

		buildFlagMaps();
		cleanWalls();
	
		entrance = 23 + WIDTH * 15;
		exit = 0 + WIDTH * 47;


		return true;
	}
	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {

	}




	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(PrisonLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}

	@Override
	protected void createItems() {
        int goldmin=1; int goldmax=10;
		boolean ringDrop = false;
		if (first){
			goldmin=25; goldmax=50;
		}
		 for (int i = 0; i < LENGTH; i++) {				
				if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Int(100)>70){
					if (first && !ringDrop){drop(new LuckyBadge().identify(), i).type = Heap.Type.CHEST; ringDrop=true;}
				    else if (Random.Int(5)==0){drop(new Gold(Random.Int(goldmin, goldmax)), i).type = Heap.Type.CHEST;}
					else if (Random.Int(4)==0){drop(new SmallMeat(), i).type = Heap.Type.M_WEB;}
					else {drop(new YellowDewdrop(), i).type = Heap.Type.E_DUST;}
				}
			}	 
		
		 
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
		
	

}
