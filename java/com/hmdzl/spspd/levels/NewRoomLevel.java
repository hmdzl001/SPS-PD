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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.WellWater;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.levels.features.HighGrass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class NewRoomLevel extends Floor {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
	}
	
	
	public HashSet<Item> heapstogen;
	
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
	}
	
	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		
		      super.restoreFromBundle(bundle);

	}

  @Override
  public void create() {
	   super.create();	
   }
	
	@Override
	public void press(int cell, Char ch) {

		if (pit[cell] && ch == Dungeon.hero) {
			Chasm.heroFall(cell);
			return;
		}

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean trap = false;
		
		switch (map[cell]) {

		case Terrain.HIGH_GRASS:
			HighGrass.trample(this, cell, ch);
			break;

		case Terrain.WELL:
			WellWater.affectCell(cell);
			break;

		/*case Terrain.ALCHEMY:
			if (ch == null) {
				Alchemy.transmute(cell);
			}
			break;*/

		case Terrain.DOOR:
			Door.enter(cell);
			break;
		}

		if (trap){

			if (Dungeon.visible[cell])
				Sample.INSTANCE.play(Assets.SND_TRAP);

			if (ch == Dungeon.hero)
				Dungeon.hero.interrupt();

			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);					
		} 

		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(ch);
		}
	}

	@Override
	public String tilesTex() {
		switch (Statistics.roomType){
			case 0 :
				return Assets.TILES_PUZZLE;
			case 1 :
				return Assets.TILES_FOREST;
//			//default://return Assets.TILES_PUZZLE;
		}
		return null;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		switch (Random.Int(2)) {
			case 0:
				Statistics.roomType = 0;
			    map = SaveRoomLayouts.SAFE_ROOM_DEFAULT.clone();
			    break;
			case 1:
				Statistics.roomType = 1;
				map = SaveRoomLayouts.ROOM_OF_FOREST.clone();
				break;
		}
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
		/*
		    SokobanSentinel mob = new SokobanSentinel();
			mob.pos = 38 + WIDTH * 21;
			mobs.add(mob);
			Actor.occupyCell(mob);
			
			SokobanSentinel mob2 = new SokobanSentinel();
			mob2.pos = 25 + WIDTH * 36;
			mobs.add(mob2);
			Actor.occupyCell(mob2);		
			*/	
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
        //int goldmin=1; int goldmax=10;
	//	boolean ringDrop = false;
		//if (first){
	//		goldmin=25; goldmax=50;
	//	}
		// for (int i = 0; i < LENGTH; i++) {
		//		if (map[i]==Terrain.EMPTY && heaps.get(i) == null && Random.Int(100)>70){
		//			if (first && !ringDrop){drop(new LuckyBadge().identify(), i).type = Heap.Type.CHEST; ringDrop=true;}
		//		    else if (Random.Int(5)==0){drop(new Gold(Random.Int(goldmin, goldmax)), i).type = Heap.Type.CHEST;}
		//			else {drop(new YellowDewdrop(), i).type = Heap.Type.E_DUST;}
		//		}
		//	}
		
		 
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
		
	

}
