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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Alchemy;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.Foliage;
import com.hmdzl.spspd.change.actors.blobs.Portal;
import com.hmdzl.spspd.change.actors.blobs.WaterOfHealth;
import com.hmdzl.spspd.change.actors.blobs.WaterOfTransmutation;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Bestiary;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.SokobanSentinel;
import com.hmdzl.spspd.change.actors.mobs.npcs.Blacksmith;
import com.hmdzl.spspd.change.actors.mobs.npcs.MagicSheep;
import com.hmdzl.spspd.change.actors.mobs.npcs.Sheep;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanBlack;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.change.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.eggs.Egg;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.Towel;
import com.hmdzl.spspd.change.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.change.items.keys.IronKey;
import com.hmdzl.spspd.change.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfUpgrade;

import com.hmdzl.spspd.change.levels.features.Chasm;
import com.hmdzl.spspd.change.levels.features.Door;
import com.hmdzl.spspd.change.levels.features.HighGrass;
import com.hmdzl.spspd.change.levels.painters.Painter;
import com.hmdzl.spspd.change.levels.traps.ActivatePortalTrap;
import com.hmdzl.spspd.change.levels.traps.AlarmTrap;
import com.hmdzl.spspd.change.levels.traps.ChangeSheepTrap;
import com.hmdzl.spspd.change.levels.traps.FireTrap;
import com.hmdzl.spspd.change.levels.traps.FleecingTrap;
import com.hmdzl.spspd.change.levels.traps.GrippingTrap;
import com.hmdzl.spspd.change.levels.traps.HeapGenTrap;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.levels.traps.ParalyticTrap;
import com.hmdzl.spspd.change.levels.traps.PoisonTrap;
import com.hmdzl.spspd.change.levels.traps.SokobanPortalTrap;
import com.hmdzl.spspd.change.levels.traps.SummoningTrap;
import com.hmdzl.spspd.change.levels.traps.ToxicTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Dewcatcher;
import com.hmdzl.spspd.change.plants.Flytrap;
import com.hmdzl.spspd.change.plants.Phaseshift;
import com.hmdzl.spspd.change.plants.Plant;
import com.hmdzl.spspd.change.plants.Starflower;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

public class SokobanIntroLevel extends Level {


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
	   heapgenspots = new int[10];
	   teleportspots = new int[10];
	   portswitchspots = new int[10];
	   destinationspots = new int[10];
	   destinationassign = new int[10];
	   teleportassign = new int[10];
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
		boolean interrupt = false;
		
		switch (map[cell]) {

			case Terrain.FLEECING_TRAP:			
			
			if (ch==null){	
			  interrupt=true;	
			}
			
			if (ch != null && ch==Dungeon.hero){
				trap = true;
				FleecingTrap.trigger(cell, ch);
			}
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner || ch instanceof Sheep || ch instanceof MagicSheep){
				trap = true;
				ChangeSheepTrap.trigger(cell, ch);
			}						
			break;
			
		case Terrain.WOOL_RUG:
			
		break;
		
		case Terrain.SOKOBAN_PORT_SWITCH:
			trap=false;
			if (ch!=null){
			   ActivatePortalTrap.trigger(cell, ch);
				
				/*	
				int arraypos = -1; //position in array of teleport switch
				int portpos = -1; //position on map of teleporter
				int portarraypos = -1; //position in array of teleporter
				int destpos = -1; //destination position assigned to switch
				
				for(int i = 0; i < portswitchspots.length; i++) {
				  if(portswitchspots[i] == cell) {
				     arraypos = i;
				     break;
				  }
				}
				
				portpos = teleportassign[arraypos];
				destpos = destinationassign[arraypos];
				
				// Stepping on switch deactivates the portal 
				destpos = -1;
				
				for(int i = 0; i < teleportspots.length; i++) {
					  if(teleportspots[i] == portpos) {
						     portarraypos = i;
						     break;
						  }
				}
				
				if (map[portpos] == Terrain.PORT_WELL){
					destinationspots[portarraypos]=destpos;	
					GLog.i("Portal Deactivated!");
				}
				
				*/
			}				
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
				
				if(portarray != -1) {
					destinationspot=destinationspots[portarray];
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
		
		if (interrupt){

			Dungeon.hero.interrupt();
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
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep){
				fleece=true;
			}
			FleecingTrap.trigger(cell, mob);
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof Sheep){
				trap=true;
				ChangeSheepTrap.trigger(cell, mob);
			}						
			break;
			
		case Terrain.SOKOBAN_ITEM_REVEAL:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep){
				HeapGenTrap.trigger(cell, mob);
				drop(genPrizeItem(IronKey.class),heapgenspots[prizeNo]);
				prizeNo++;
				sheep=true;
				trap=true;
			}						
			break;
			
		case Terrain.SOKOBAN_PORT_SWITCH:
			trap=false;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep){
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
				     break;
				  }
				}
				
				portpos = teleportassign[arraypos];
				destpos = destinationassign[arraypos];
				
				for(int i = 0; i < teleportspots.length; i++) {
					  if(teleportspots[i] == portpos) {
						     portarray = i;
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
		
		map = SokobanLayouts.SOKOBAN_INTRO_LEVEL.clone();
		decorate();

		buildFlagMaps();
		cleanWalls();
		createSwitches();
		createSheep();
	
		entrance = 7 + WIDTH * 3;
		exit = 0 ;


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
	
	
/*
	public static final int FLEECING_TRAP = 65;
	public static final int WOOL_RUG = 66;
	public static final int SOKOBAN_SHEEP = 67;
	public static final int CORNER_SOKOBAN_SHEEP = 68;
	public static final int SWITCH_SOKOBAN_SHEEP = 69;
	public static final int CHANGE_SHEEP_TRAP = 70;
	public static final int SOKOBAN_ITEM_REVEAL = 71;
	public static final int SOKOBAN_HEAP = 72;
	public static final int BLACK_SOKOBAN_SHEEP = 73;
	public static final int SOKOBAN_PORT_SWITCH = 75;
	public static final int PORT_WELL = 74;	
	
*/	

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
		teleportspots[0] = 7 + WIDTH * 21;	
		
		//spots where your portal switches are	
		portswitchspots[0] = 8 + WIDTH * 27;	
			
		//assign each switch to a portal	
		teleportassign[0] = 7 + WIDTH * 21;	
			
		//assign each switch to a destination spot	
		destinationassign[0] = 34 + WIDTH * 8;	
			
		//set the original destination of portals	
		destinationspots[0] = 0;				
	}


	@Override
	public String tileName(int tile) {
		switch (tile) {
			case Terrain.SOKOBAN_SHEEP:
			case Terrain.SWITCH_SOKOBAN_SHEEP:
			case Terrain.CORNER_SOKOBAN_SHEEP:
			case Terrain.BLACK_SOKOBAN_SHEEP:
				return Messages.get(Level.class, "floor_name");
			case Terrain.WATER:
				return Messages.get(Level.class, "water_name");
			case Terrain.WOOL_RUG:
				return Messages.get(Level.class, "wool_rug_name");
			case Terrain.FLEECING_TRAP:
				return Messages.get(Level.class, "fleecing_trap_name");
			case Terrain.CHANGE_SHEEP_TRAP:
				return Messages.get(Level.class, "change_sheep_trap_name");
			case Terrain.SOKOBAN_ITEM_REVEAL:
				return Messages.get(Level.class, "sokoban_item_reveal_name");
			case Terrain.SOKOBAN_PORT_SWITCH:
				return Messages.get(Level.class, "sokoban_port_switch_name");
			case Terrain.PORT_WELL:
				return Messages.get(Level.class, "port_well_name");
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
				return Messages.get(Level.class, "default_desc");
			case Terrain.EMPTY_DECO:
				return Messages.get(PrisonLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(PrisonLevel.class, "bookshelf_desc");
			case Terrain.WOOL_RUG:
				return Messages.get(Level.class, "wool_rug_desc");
			case Terrain.FLEECING_TRAP:
				return Messages.get(Level.class, "fleecing_trap_desc");
			case Terrain.CHANGE_SHEEP_TRAP:
				return Messages.get(Level.class, "change_sheep_trap_desc");
			case Terrain.SOKOBAN_ITEM_REVEAL:
				return Messages.get(Level.class, "sokoban_item_reveal_desc");
			case Terrain.SOKOBAN_PORT_SWITCH:
				return Messages.get(Level.class, "sokoban_port_switch_desc");
			case Terrain.PORT_WELL:
				return Messages.get(Level.class, "port_well_desc");
			default:
				return super.tileDesc(tile);
		}
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
		 
		 addItemToGen(new IronKey(Dungeon.depth) , 0, 24 + WIDTH * 21);
		 

		 if (first){
			 addItemToGen(new Towel() , 1, 37 + WIDTH * 21);
		 } else {
			 addItemToGen(new Gold(1) , 1, 37 + WIDTH * 21);
		 }
		 
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
	
	

}
