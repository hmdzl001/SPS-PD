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
package com.hmdzl.spspd.change.levels;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.actors.mobs.LitTower;
import com.hmdzl.spspd.change.actors.mobs.MineSentinel;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.Otiluke;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanBlack;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Palantir;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.keys.IronKey;
import com.hmdzl.spspd.change.levels.features.Chasm;
import com.hmdzl.spspd.change.levels.features.Door;
import com.hmdzl.spspd.change.levels.features.HighGrass;
import com.hmdzl.spspd.change.levels.traps.ChangeSheepTrap;
import com.hmdzl.spspd.change.levels.traps.FleecingTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.windows.WndOtilukeMessage;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class MinesBossLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;

	}
		
	
	private boolean entered = false;
	private static final String ENTERED = "entered";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ENTERED, entered);
		
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);		
		entered = bundle.getBoolean(ENTERED);
		
	}

  
	
	@Override
	public void press(int cell, Char ch) {
		

		if (ch == Dungeon.hero && !entered) {

			entered = true;
			locked = true;
			GameScene.show(new WndOtilukeMessage());
			
		}
		
		if (pit[cell] && ch == Dungeon.hero) {
			Chasm.heroFall(cell);
			return;
		}

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean trap = false;
		
		switch (map[cell]) {

			case Terrain.FLEECING_TRAP:			
					
			if (ch != null && ch==Dungeon.hero){
				trap = true;
				FleecingTrap.trigger(cell, ch);
			}
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner ){
				trap = true;
				ChangeSheepTrap.trigger(cell, ch);
			}						
			break;
			
				
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
	public void mobPress(Mob mob) {

		int cell = mob.pos;

		if (pit[cell] && !mob.flying) {
			Chasm.mobFall(mob);
			return;
		}

		boolean trap = true;
		boolean fleece = false;
		boolean sheep = false;
		switch (map[cell]) {
			
		case Terrain.FLEECING_TRAP:
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack ){
				fleece=true;
			}
			FleecingTrap.trigger(cell, mob);
			break;

		case Terrain.DOOR:
			Door.enter(cell);

		default:
			trap = false;
		}

		if (trap && !fleece && !sheep) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);
		}
		
		if (trap && fleece) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.WOOL_RUG);
			GameScene.updateMap(cell);
		} 	
		
		if (trap && sheep) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.EMPTY);
			GameScene.updateMap(cell);
		}
	
		
		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(mob);
		}
		
		Dungeon.observe();
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
		
		
		map = MineBossLayouts.MINE_BOSS.clone();
		
		decorate();

		buildFlagMaps();
		cleanWalls();
			
		entrance = 17 + WIDTH * 44;
		exit = 0 ;


		return true;
	}
	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		 for (int i = 0; i < LENGTH; i++) {				
				if (map[i]==Terrain.SOKOBAN_SHEEP){
					MineSentinel npc = new MineSentinel(); 
					mobs.add(npc); npc.pos = i; 
					Actor.occupyCell(npc);
				} else if (map[i]==Terrain.CORNER_SOKOBAN_SHEEP){
					LitTower npc = new LitTower(); 
					mobs.add(npc); npc.pos = i; 
					Actor.occupyCell(npc);
				}
			}
		 
		    Otiluke mob = new Otiluke(); 
			mobs.add(mob); mob.pos = 33 + WIDTH * 10; 
			Actor.occupyCell(mob);
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
		 			
				drop(new IronKey(Dungeon.depth), 30 + WIDTH * 44).type = Heap.Type.CHEST;	
				drop(new Palantir(), 14 + WIDTH * 10);
		 
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}


}