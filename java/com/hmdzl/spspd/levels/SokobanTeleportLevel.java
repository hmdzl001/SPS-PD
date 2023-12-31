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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.WellWater;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.SokobanSentinel;
import com.hmdzl.spspd.actors.mobs.npcs.Sheep;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanBlack;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.misc.AutoPotion;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.items.wands.WandOfFlock;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.levels.features.HighGrass;
import com.hmdzl.spspd.levels.traps.ActivatePortalTrap;
import com.hmdzl.spspd.levels.traps.ChangeSheepTrap;
import com.hmdzl.spspd.levels.traps.FleecingTrap;
import com.hmdzl.spspd.levels.traps.HeapGenTrap;
import com.hmdzl.spspd.levels.traps.SokobanPortalTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.ReNepenth;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Collection;
import java.util.HashSet;

public class SokobanTeleportLevel extends Floor {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
	}
	
	
	public HashSet<Item> heapstogen;
	public int[] heapgenspots;
	public int[] teleportspots;
	public int[] portswitchspots;
	public int[] teleportassign;
	public int[] destinationspots;
	public int[] destinationassign;
	public int prizeNo;
	
	private static final String HEAPSTOGEN = "heapstogen";
	private static final String HEAPGENSPOTS = "heapgenspots";
	private static final String TELEPORTSPOTS = "teleportspots";
	private static final String PORTSWITCHSPOTS = "portswitchspots";
	private static final String DESTINATIONSPOTS = "destinationspots";
	private static final String TELEPORTASSIGN = "teleportassign";
	private static final String DESTINATIONASSIGN= "destinationassign";
	private static final String PRIZENO = "prizeNo";
	
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(HEAPSTOGEN, heapstogen);
		bundle.put(HEAPGENSPOTS, heapgenspots);
		bundle.put(TELEPORTSPOTS, teleportspots);
		bundle.put(PORTSWITCHSPOTS, portswitchspots);
		bundle.put(DESTINATIONSPOTS, destinationspots);
		bundle.put(DESTINATIONASSIGN, destinationassign);
		bundle.put(TELEPORTASSIGN, teleportassign);
		bundle.put(PRIZENO, prizeNo);
	}


	@Override
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.SOKOBAN_SHEEP:
			case Terrain.SWITCH_SOKOBAN_SHEEP:
			case Terrain.CORNER_SOKOBAN_SHEEP:
			case Terrain.BLACK_SOKOBAN_SHEEP:
				return Messages.get(Floor.class, "floor_name");
			case Terrain.WATER:
				return Messages.get(Floor.class, "water_name");
			case Terrain.WOOL_RUG:
				return Messages.get(Floor.class, "wool_rug_name");
			case Terrain.FLEECING_TRAP:
				return Messages.get(Floor.class, "fleecing_trap_name");
			case Terrain.CHANGE_SHEEP_TRAP:
				return Messages.get(Floor.class, "change_sheep_trap_name");
			case Terrain.SOKOBAN_ITEM_REVEAL:
				return Messages.get(Floor.class, "sokoban_item_reveal_name");
			case Terrain.SOKOBAN_PORT_SWITCH:
				return Messages.get(Floor.class, "sokoban_port_switch_name");
			case Terrain.PORT_WELL:
				return Messages.get(Floor.class, "port_well_name");
			default:
				return super.tileName(tile);
		}
	}


	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.SOKOBAN_SHEEP:
			case Terrain.SWITCH_SOKOBAN_SHEEP:
			case Terrain.CORNER_SOKOBAN_SHEEP:
			case Terrain.BLACK_SOKOBAN_SHEEP:
				return Messages.get(Floor.class, "default_desc");
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			case Terrain.WOOL_RUG:
				return Messages.get(Floor.class, "wool_rug_desc");
			case Terrain.FLEECING_TRAP:
				return Messages.get(Floor.class, "fleecing_trap_desc");
			case Terrain.CHANGE_SHEEP_TRAP:
				return Messages.get(Floor.class, "change_sheep_trap_desc");
			case Terrain.SOKOBAN_ITEM_REVEAL:
				return Messages.get(Floor.class, "sokoban_item_reveal_desc");
			case Terrain.SOKOBAN_PORT_SWITCH:
				return Messages.get(Floor.class, "sokoban_port_switch_desc");
			case Terrain.PORT_WELL:
				return Messages.get(Floor.class, "port_well_desc");
			default:
				return super.tileDesc(tile);
		}
	}

	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		
		      super.restoreFromBundle(bundle);
		      
		      heapgenspots = bundle.getIntArray(HEAPGENSPOTS);
		      teleportspots = bundle.getIntArray(TELEPORTSPOTS);
		      portswitchspots = bundle.getIntArray(PORTSWITCHSPOTS);
		      destinationspots = bundle.getIntArray(DESTINATIONSPOTS);
		      destinationassign = bundle.getIntArray(DESTINATIONASSIGN);
		      teleportassign = bundle.getIntArray(TELEPORTASSIGN);
		      prizeNo = bundle.getInt(PRIZENO);
		      
		      heapstogen = new HashSet<Item>();
		      
		      Collection <Bundlable> collectionheap = bundle.getCollection(HEAPSTOGEN);
				for (Bundlable i : collectionheap) {
					Item item = (Item) i;
					if (item != null) {
						heapstogen.add(item);
					}
				}
	}

  @Override
  public void create() {
	   heapstogen = new HashSet<Item>();
	   heapgenspots = new int[30];
	   teleportspots = new int[30];
	   portswitchspots = new int[30];
	   destinationspots = new int[30];
	   destinationassign = new int[30];
	   teleportassign = new int[30];
	   super.create();	
   }	
  
  public void addItemToGen(Item item, int arraypos, int pos) {
		if (item != null) {
			heapstogen.add(item);
			heapgenspots[arraypos]=pos;
		}
	}
  
  
	public Item genPrizeItem() {
		return genPrizeItem(null);
	}
	
	
	public Item genPrizeItem(Class<? extends Item> match) {
		
		boolean keysLeft = false;
		
		if (heapstogen.size() == 0)
			return null;

		for (Item item : heapstogen) {
			if (match.isInstance(item)) {
				heapstogen.remove(item);
				keysLeft=true;
				return item;
			}
		}
		
		if (match == null || !keysLeft) {
			Item item = Random.element(heapstogen);
			heapstogen.remove(item);
			return item;
		}

		return null;
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

			case Terrain.FLEECING_TRAP:			
					
			if (ch != null && ch==Dungeon.hero){
				trap = true;
				FleecingTrap.trigger(cell, ch);
			}
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner || ch instanceof Sheep || ch instanceof WandOfFlock.MagicSheep){
				trap = true;
				ChangeSheepTrap.trigger(cell, ch);
			}						
			break;
			
		case Terrain.SOKOBAN_PORT_SWITCH:
			trap=false;
			ActivatePortalTrap.trigger(cell, ch);
				
				/*	
				int arraypos = -1; //position in array of teleport switch
				int portpos = -1; //position on map of teleporter
				int portarraypos = -1; //position in array of teleporter
				int destpos = -1; //destination position assigned to switch
				
				for(int i = 0; i < portswitchspots.length; i++) {
				  if(portswitchspots[i] == cell) {
				     arraypos = i;
				     //GLog.i("Pos1 %s", arraypos);
				     break;
				  }
				}
				
				portpos = teleportassign[arraypos];
				destpos = destinationassign[arraypos];
				
				// Stepping on switch deactivates the portal 
				destpos = -1;
				
				//GLog.i("ass2 %s", portpos);
				//GLog.i("dest3 %s", destpos);
				
				for(int i = 0; i < teleportspots.length; i++) {
					  if(teleportspots[i] == portpos) {
						     portarraypos = i;
						     //GLog.i("Pos4 %s", portarraypos);
						     break;
						  }
				}
				
				if (map[portpos] == Terrain.PORT_WELL){
					destinationspots[portarraypos]=destpos;	
					GLog.i("Portal Deactivated!");
				}
				
				*/
								
			break;

			
        case Terrain.PORT_WELL:
			
			if (ch != null && ch==Dungeon.hero){
				
				int portarray=-1;
				int destinationspot=cell;
				
				for(int i = 0; i < teleportspots.length; i++) {
					  if(teleportspots[i] == cell) {
						     portarray = i;
						     break;
						  }
				}
				
				//GLog.i("hero %s", portarray);
				
				if(portarray != -1) {
					//GLog.i("portar %s", portarray);
					destinationspot=destinationspots[portarray];
					//GLog.i("destspot %s", destinationspot);
					if (destinationspot>0){
					SokobanPortalTrap.trigger(cell, ch, destinationspot);
					}
				}				
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
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep || mob instanceof WandOfFlock.MagicSheep){
				fleece=true;
			}
			FleecingTrap.trigger(cell, mob);
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof Sheep || mob instanceof WandOfFlock.MagicSheep){
				trap=true;
				ChangeSheepTrap.trigger(cell, mob);
			}						
			break;
			
		case Terrain.SOKOBAN_ITEM_REVEAL:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep || mob instanceof WandOfFlock.MagicSheep){
				HeapGenTrap.trigger(cell, mob);
				drop(genPrizeItem(IronKey.class),heapgenspots[prizeNo]);
				prizeNo++;
				sheep=true;
				trap=true;
			}						
			break;
			
		case Terrain.SOKOBAN_PORT_SWITCH:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep || mob instanceof WandOfFlock.MagicSheep){
				ActivatePortalTrap.trigger(cell, mob);
				
				/*
				public int[] teleportspots; location of teleports
				public int[] portswitchspots; location of switches
				public int[] teleportassign; assignment of teleports to switches
				public int[] destinationspots; current assignment of destination spots to teleports
				public int[] destinationassign; assignemnt of destination spots to switches
				*/
				
				int arraypos = -1; //position in array of teleport switch
				int portpos = -1; //position on map of teleporter
				int portarray = -1; //position in array of teleporter
				int destpos = -1; //destination position assigned to switch
				
				for(int i = 0; i < portswitchspots.length; i++) {
				  if(portswitchspots[i] == cell) {
				     arraypos = i;
				     //GLog.i("Pos1 %s", arraypos);
				     break;
				  }
				}
				
				portpos = teleportassign[arraypos];
				destpos = destinationassign[arraypos];
				
				//GLog.i("ass2 %s", portpos);
				//GLog.i("dest3 %s", destpos);
				
				for(int i = 0; i < teleportspots.length; i++) {
					  if(teleportspots[i] == portpos) {
						     portarray = i;
						     //GLog.i("Pos4 %s", portarray);
						     break;
						  }
				}
				
				if (map[portpos] == Terrain.PORT_WELL){
					destinationspots[portarray]=destpos;	
					GLog.i("Click!");
				}
				
				sheep=true;
			}						
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
		
		map = SokobanLayouts.SOKOBAN_TELEPORT_LEVEL.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();
		createSwitches();
		createSheep();
	
		entrance = 8 + WIDTH * 16;
		exit = 0 ;


		return true;
	}
	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		
		
		    SokobanSentinel mob = new SokobanSentinel();
			mob.pos = 21 + WIDTH * 25;
			mobs.add(mob);
			Actor.occupyCell(mob);
		
			SokobanSentinel mob2 = new SokobanSentinel();
			mob2.pos = 42 + WIDTH * 42;
			mobs.add(mob2);
			Actor.occupyCell(mob2);
		
	}
	
	

	protected void createSheep() {
		 for (int i = 0; i < LENGTH; i++) {				
				if (map[i]==Terrain.SOKOBAN_SHEEP){SheepSokoban npc = new SheepSokoban(); mobs.add(npc); npc.pos = i; Actor.occupyCell(npc);}
				else if (map[i]==Terrain.CORNER_SOKOBAN_SHEEP){SheepSokobanCorner npc = new SheepSokobanCorner(); mobs.add(npc); npc.pos = i; Actor.occupyCell(npc);}
				else if (map[i]==Terrain.SWITCH_SOKOBAN_SHEEP){SheepSokobanSwitch npc = new SheepSokobanSwitch(); mobs.add(npc); npc.pos = i; Actor.occupyCell(npc);}
				else if (map[i]==Terrain.BLACK_SOKOBAN_SHEEP){SheepSokobanBlack npc = new SheepSokobanBlack(); mobs.add(npc); npc.pos = i; Actor.occupyCell(npc);}
				else if (map[i]==Terrain.PORT_WELL){
				/*
					Portal portal = new Portal();
				    portal.seed(i, 1);
				   blobs.put(Portal.class, portal);
				*/
				}
				
			}
	}
	
	
	protected void createSwitches(){
		
		//spots where your portals are		
		teleportspots[0] = 4 + WIDTH * 2;	
		teleportspots[1] = 16 + WIDTH * 5;	
		teleportspots[2] = 23 + WIDTH * 5;	
		teleportspots[3] = 32 + WIDTH * 5;	
		teleportspots[4] = 4 + WIDTH * 6;	
		teleportspots[5] = 25 + WIDTH * 7;	
		teleportspots[6] = 42 + WIDTH * 8;	
		teleportspots[7] = 10 + WIDTH * 9;	
		teleportspots[8] = 2 + WIDTH * 10;	
		teleportspots[9] = 3 + WIDTH * 17;	
		teleportspots[10] = 8 + WIDTH * 17;	
		teleportspots[11] = 17 + WIDTH * 17;	
		teleportspots[12] = 37 + WIDTH * 17;	
		teleportspots[13] = 40 + WIDTH * 17;	
		teleportspots[14] = 37 + WIDTH * 20;	
		teleportspots[15] = 40 + WIDTH * 20;	
		teleportspots[16] = 21 + WIDTH * 21;	
		teleportspots[17] = 25 + WIDTH * 22;	
		teleportspots[18] = 15 + WIDTH * 24;	
		teleportspots[19] = 6 + WIDTH * 44;	
		teleportspots[20] = 23 + WIDTH * 44;	
		teleportspots[21] = 44 + WIDTH * 28;	

				
				
		//spots where your portal switches are		
		portswitchspots[0] = 30 + WIDTH * 14;	
		portswitchspots[1] = 35 + WIDTH * 16;	
		portswitchspots[2] = 42 + WIDTH * 16;	
		portswitchspots[3] = 35 + WIDTH * 21;	
		portswitchspots[4] = 42 + WIDTH * 21;	
		portswitchspots[5] = 30 + WIDTH * 36;	
		portswitchspots[6] = 32 + WIDTH * 36;	
		portswitchspots[7] = 35 + WIDTH * 36;	
		portswitchspots[8] = 37 + WIDTH * 36;	
		portswitchspots[9] = 27 + WIDTH * 41;	
	
				
				
		//assign each switch to a portal		
		teleportassign[0] = 8 + WIDTH * 17;	
		teleportassign[1] = 44 + WIDTH * 28;	
		teleportassign[2] = 40 + WIDTH * 17;	
		teleportassign[3] = 40 + WIDTH * 17;	
		teleportassign[4] = 8 + WIDTH * 17;	
		teleportassign[5] = 40 + WIDTH * 20;	
		teleportassign[6] = 15 + WIDTH * 24;	
		teleportassign[7] = 8 + WIDTH * 17;	
		teleportassign[8] = 23 + WIDTH * 44;	
		teleportassign[9] = 6 + WIDTH * 44;	
			
				
		//assign each switch to a destination spot	
		destinationassign[0] = 9 + WIDTH * 9;	
		destinationassign[1] = 25 + WIDTH * 44;	
		destinationassign[2] = 36 + WIDTH * 20;	
		destinationassign[3] = 41 + WIDTH * 20;	
		destinationassign[4] = 2 + WIDTH * 17;	
		destinationassign[5] = 22 + WIDTH * 21;	
		destinationassign[6] = 16 + WIDTH * 17;	
		destinationassign[7] = 13 + WIDTH * 24;	
		destinationassign[8] = 0;	
		destinationassign[9] = 42 + WIDTH * 7;	
			
		//set the original destination of portals	
		destinationspots[0] = 2 + WIDTH * 9;	
		destinationspots[1] = 24 + WIDTH * 8;	
		destinationspots[2] = 8 + WIDTH * 44;	
		destinationspots[3] = 37 + WIDTH * 16;	
		destinationspots[4] = 31 + WIDTH * 5;	
		destinationspots[5] = 9 + WIDTH * 16;	
		destinationspots[6] = 9 + WIDTH * 16;	
		destinationspots[7] = 5 + WIDTH * 2;	
		destinationspots[8] = 9 + WIDTH * 16;	
		destinationspots[9] = 9 + WIDTH * 16;	
		destinationspots[10] = 0;	
		destinationspots[11] = 9 + WIDTH * 16;	
		destinationspots[12] = 40 + WIDTH * 16;	
		destinationspots[13] = 36 + WIDTH * 20;	
		destinationspots[14] = 36 + WIDTH * 17;	
		destinationspots[15] = 24 + WIDTH * 23;	
		destinationspots[16] = 18 + WIDTH * 5;	
		destinationspots[17] = 9 + WIDTH * 16;	
		destinationspots[18] = 9 + WIDTH * 16;	
		destinationspots[19] = 9 + WIDTH * 16;	
		destinationspots[20] = 9 + WIDTH * 16;	
		destinationspots[21] = 0;	


				
	}
	

	@Override
	protected void createItems() {
		int goldmin=300; int goldmax=500;
		if (first){
			goldmin=300; goldmax=500;
		}
		 for (int i = 0; i < LENGTH; i++) {				
				if (map[i]==Terrain.SOKOBAN_HEAP){
					if (first && Random.Int(5)==0){drop(new ScrollOfUpgrade(), i).type = Heap.Type.CHEST;}
					else {drop(new Gold(Random.Int(goldmin, goldmax)), i).type = Heap.Type.CHEST;}
				}
			}	
		 
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 0, 8 + WIDTH * 18);
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 1, 8 + WIDTH * 18);
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 2, 8 + WIDTH * 18);
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 3, 8 + WIDTH * 18);
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 4, 8 + WIDTH * 18);
		 addItemToGen(new IronKey(Dungeon.dungeondepth) , 5, 8 + WIDTH * 18);
		 addItemToGen(new AutoPotion(), 6, 36 + WIDTH * 28);

		 if (first){
			 addItemToGen(new ReNepenth.Seed(), 7, 36 + WIDTH * 28);
			 addItemToGen(new Starflower.Seed(), 8, 36 + WIDTH * 28);

			 addItemToGen(new ScrollOfMagicalInfusion(), 9, 36 + WIDTH * 28);
			 addItemToGen(new ScrollOfMagicalInfusion(), 10, 36 + WIDTH * 28);
			 addItemToGen(new ScrollOfRegrowth(), 11, 36 + WIDTH * 28);
			 addItemToGen(new Starflower.Seed(), 12, 36 + WIDTH * 28);
		 }
		 
		 //drop(new PotionOfLiquidFlame(), 9 + WIDTH * 24).type = Heap.Type.CHEST;
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
	


}