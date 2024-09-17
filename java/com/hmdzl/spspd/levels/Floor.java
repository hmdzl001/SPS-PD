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
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Alchemy;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.TorchLight;
import com.hmdzl.spspd.actors.blobs.WellWater;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.MindVision;
import com.hmdzl.spspd.actors.buffs.Shadows;
import com.hmdzl.spspd.actors.buffs.TentSleep;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokoban;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanCorner;
import com.hmdzl.spspd.actors.mobs.npcs.SheepSokobanSwitch;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.particles.FlowParticle;
import com.hmdzl.spspd.effects.particles.WindParticle;
import com.hmdzl.spspd.items.DewVial;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PocketBallFull;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.StrBottle;
import com.hmdzl.spspd.items.Stylus;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.Weightstone;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.artifacts.DriedRose;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.food.fruit.Blueberry;
import com.hmdzl.spspd.items.misc.LuckyBadge;
import com.hmdzl.spspd.items.potions.PotionOfOverHealing;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicalInfusion;
import com.hmdzl.spspd.items.scrolls.ScrollOfUpgrade;
import com.hmdzl.spspd.levels.features.Chasm;
import com.hmdzl.spspd.levels.features.DewBlessPlace;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.levels.features.HighGrass;
import com.hmdzl.spspd.levels.features.OldHighGrass;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.levels.traps.ChangeSheepTrap;
import com.hmdzl.spspd.levels.traps.FleecingTrap;
import com.hmdzl.spspd.levels.traps.HeapGenTrap;
import com.hmdzl.spspd.levels.traps.Trap;
import com.hmdzl.spspd.mechanics.ShadowCaster;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.CustomTileVisual;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Floor implements Bundlable {

    public enum Feeling {
		NONE, CHASM, WATER, GRASS, DARK, TRAP, SPECIAL_FLOOR
	}



    /*  -W-1 -W  -W+1
	 *  -1    P  +1
	 *  W-1   W  W+1
	 * 
	 */
	
	public static int WIDTH = 48;
	public static int HEIGHT = 48;
	public static int LENGTH = WIDTH * HEIGHT;
	
	//public static int WIDTH = 50;
	//public static int HEIGHT = 50;
	//public static int LENGTH = (WIDTH-2) * (HEIGHT-2);	

	public static final int[] NEIGHBOURS4 = { -getWidth(), +1, +getWidth(), -1 };

	public static final int[] NEIGHBOURS4OUT = { +1 + getWidth(), +1 - getWidth(), -1 + getWidth(), -1 - getWidth() };	
	
	public static final int[] NEIGHBOURS8 = { +1, -1, +getWidth(), -getWidth(),
			+1 + getWidth(), +1 - getWidth(), -1 + getWidth(), -1 - getWidth() };
	public static final int[] NEIGHBOURS9 = { 0, +1, -1, +getWidth(), -getWidth(),
			+1 + getWidth(), +1 - getWidth(), -1 + getWidth(), -1 - getWidth() };

	public static final int[] dir = new int[]{-1, +1, -getWidth(), +getWidth(), -getWidth()-1, -getWidth()+1, +getWidth()-1, +getWidth()+1};
	public static final int[] dirLR = new int[]{-1-getWidth(), -1, -1+getWidth(), -getWidth(), +getWidth(), +1-getWidth(), +1, +1+getWidth()};

	//逆时针
	public static final int[] CIRCLE4 = new int[]{-getWidth(), -1, +1, +getWidth()};
	public static final int[] CIRCLE8 = new int[]{-getWidth()-1, -getWidth(), -getWidth()+1, -1, +1, +getWidth()-1, +getWidth(), +getWidth()+1};
	public static final int[] CIRCLE9 = new int[]{ -getWidth()-1, -getWidth(), -getWidth()+1, -1, 0, +1, +getWidth()-1, +getWidth(), +getWidth()+1};

	// Note that use of these without checking values is unsafe, mobs can be
	// within 2 tiles of the
	// edge of the map, unsafe use in that case will cause an array out of
	// bounds exception.
/*	public static final int[] NEIGHBOURS8DIST2 = { +2 + 2 * getWidth(),
			+1 + 2 * getWidth(), 2 * getWidth(), -1 + 2 * getWidth(), -2 + 2 * getWidth(),
			+2 + getWidth(), +1 + getWidth(), +getWidth(), -1 + getWidth(), -2 + getWidth(), +2, +1, -1,
			-2, +2 - getWidth(), +1 - getWidth(), -getWidth(), -1 - getWidth(), -2 - getWidth(),
			+2 - 2 * getWidth(), +1 - 2 * getWidth(), -2 * getWidth(), -1 - 2 * getWidth(),
			-2 - 2 * getWidth() };
	public static final int[] NEIGHBOURS9DIST2 = { +2 + 2 * getWidth(),
			+1 + 2 * getWidth(), 2 * getWidth(), -1 + 2 * getWidth(), -2 + 2 * getWidth(),
			+2 + getWidth(), +1 + getWidth(), +getWidth(), -1 + getWidth(), -2 + getWidth(), +2, +1, 0,
			-1, -2, +2 - getWidth(), +1 - getWidth(), -getWidth(), -1 - getWidth(), -2 - getWidth(),
			+2 - 2 * getWidth(), +1 - 2 * getWidth(), -2 * getWidth(), -1 - 2 * getWidth(),
			-2 - 2 * getWidth() };
			
	public static final int[] NEIGHBOURS8OUT2 = { +2 + 2 * getWidth(),
			+1 + 2 * getWidth(), 2 * getWidth(), -1 + 2 * getWidth(), -2 + 2 * getWidth(),
			+2 + getWidth(), -2 + getWidth(), +2, -2, +2 - getWidth(), -2 - getWidth(),
			+2 - 2 * getWidth(), +1 - 2 * getWidth(), -2 * getWidth(), -1 - 2 * getWidth(),
			-2 - 2 * getWidth() };

	public static final int[] NEIGHBOURS8OUT = { +2 + 2 * getWidth(), 2 * getWidth(), -2 + 2 * getWidth(),
		 +2, -2, +2 - 2 * getWidth(),  -2 * getWidth(),-2 - 2 * getWidth() };
*/
	protected static final float TIME_TO_RESPAWN = 50;
	protected static final int REGROW_TIMER = 10;
	protected static final int DROP_TIMER = 10;
	protected static final int PET_TICK = 1;
		
	private static final String TXT_HIDDEN_PLATE_CLICKS = "A hidden pressure plate clicks!";

	public static boolean resizingNeeded;
	public static boolean first;
	public static int loadedMapSize;
	
	public int[] map;
	public boolean[] visited;
	public boolean[] mapped;
	
	public int movepar=400;
	public float currentmoves=0;
	public boolean genpetnext = false;

	//private int newvd;
	public int viewDistance = /*Dungeon.isChallenged(Challenges.DARKNESS) ? 5 :*/ 8;

	public static boolean[] fieldOfView = new boolean[getLength()];

	public static boolean[] passable = new boolean[getLength()];
	//public static boolean[] losBlocking = new boolean[getLength()];
	public static boolean[] losBlockHigh = new boolean[getLength()];
	public static boolean[] losBlockLow	 = new boolean[getLength()];	
	public static boolean[] flamable = new boolean[getLength()];
	//public static boolean[] shockable = new boolean[getLength()];
	public static boolean[] secret = new boolean[getLength()];
	public static boolean[] solid = new boolean[getLength()];
	public static boolean[] avoid = new boolean[getLength()];
	public static boolean[] water = new boolean[getLength()];
	public static boolean[] pit = new boolean[getLength()];
	public static boolean[] lightpass = new boolean[getLength()];

	public static boolean[] discoverable = new boolean[getLength()];
	
	public Feeling feeling = Feeling.NONE;


	public int entrance;
	public int exit;

	//public int pitSign;
	public int tent;

	// when a boss level has become locked.
	public boolean locked = false;
	public boolean special = false;
	public boolean cleared = false;
	public boolean forcedone = false;
	//public boolean sealedlevel = false;

	public HashSet<Mob> mobs;
	public SparseArray<Heap> heaps;
	public HashMap<Class<? extends Blob>, Blob> blobs;
	public SparseArray<Plant> plants;
	public SparseArray<Trap> traps;
	public HashSet<CustomTileVisual> customTiles;

	protected ArrayList<Item> itemsToSpawn = new ArrayList<Item>();
	protected ArrayList<Item> itemsSpecialToSpawn = new ArrayList<Item>();

	public int color1 = 0x004400;
	public int color2 = 0x88CC44;

	public boolean reset = false;

	private static final String MAP = "map";
	private static final String VISITED = "visited";
	private static final String MAPPED = "mapped";
	private static final String ENTRANCE = "entrance";
	private static final String EXIT = "exit";
	private static final String LOCKED = "locked";
	private static final String HEAPS = "heaps";
	private static final String PLANTS = "plants";
	private static final String TRAPS       = "traps";
    private static final String CUSTOM_TILES= "customTiles";
	private static final String MOBS = "mobs";
	private static final String BLOBS = "blobs";
	private static final String FEELING = "feeling";
	private static final String PITSIGN = "pitSign";
	private static final String TENT = "tent";
	private static final String MOVES = "currentmoves";
	private static final String CLEARED = "cleared";
	private static final String RESET = "reset";
	private static final String FORCEDONE = "forcedone";
	private static final String GENPETNEXT = "genpetnext";
	//private static final String SEALEDLEVEL = "sealedlevel";

	
	public void create() {

		resizingNeeded = false;

		if (!Dungeon.bossLevel()) {
			addItemToSpawn(Generator.random(Generator.Category.FOOD));
			addItemToSpawn(Generator.random(Generator.Category.FOOD));

			addItemToSpawn(new ScrollOfUpgrade());

			if (Random.Int(3) != 0) {
				addItemToSpawn(new Stylus());
				addItemToSpawn(new Weightstone());
			} else {
				addItemToSpawn(new ScrollOfMagicalInfusion());
				addItemToSpawn(new PotionOfOverHealing());
			}

			if (Dungeon.posNeeded() && !Dungeon.shopOnLevel()) {
				addSpecialItemToSpawn(new StrBottle());
				Dungeon.LimitedDrops.strengthPotions.count++;
			}

			DriedRose rose = Dungeon.hero.belongings.getItem(DriedRose.class);
			if (rose != null && !rose.cursed) {
				// this way if a rose is dropped later in the game, player still
				// has a chance to max it out.
				int petalsNeeded = (int) Math
						.ceil((float) ((Dungeon.dungeondepth / 2) - rose.droppedPetals) / 3);

				for (int i = 1; i <= petalsNeeded; i++) {
					// the player may miss a single petal and still max their
					// rose.
					if (rose.droppedPetals < 12) {
						addItemToSpawn(new DriedRose.Petal());
						rose.droppedPetals++;
					}
				}
			}

			if (Dungeon.dungeondepth > 3 && Dungeon.dungeondepth < 21) {
				switch (Random.Int(8)) {
					case 0:
						feeling = Feeling.CHASM;
						break;
					case 1:
						feeling = Feeling.WATER;
						break;
					case 2:
						feeling = Feeling.GRASS;
						break;
					case 3:
						feeling = Feeling.DARK;
						addItemToSpawn(new Blueberry());
						//addItemToSpawn(new Torch());
						//addItemToSpawn(new Torch());
						//viewDistance = (int) Math.ceil(viewDistance / 3f);
						break;
					case 4:
						feeling = Feeling.SPECIAL_FLOOR;
						break;
					default:
						break;

				}
			} else if (Dungeon.dungeondepth > 20 && Dungeon.dungeondepth < 26) {
				switch (Random.Int(8)) {
					case 0: case 4: case 5:
						feeling = Feeling.DARK;
						addItemToSpawn(new Blueberry());
						//addItemToSpawn(new Torch());
						//addItemToSpawn(new Torch());
						//viewDistance = (int) Math.ceil(viewDistance / 3f);
						break;
					case 1:
						feeling = Feeling.WATER;
						break;
					case 2:
						feeling = Feeling.GRASS;
						break;
					case 3:
						feeling = Feeling.SPECIAL_FLOOR;
						break;
					default:
						break;

				}
			} else if (Dungeon.dungeondepth ==31) {
				feeling = Feeling.DARK;
				viewDistance = (int) Math.ceil(viewDistance / 3f);
			} else if (Dungeon.dungeondepth ==32) {
				//feeling = Feeling.WATER;
			} else if (Dungeon.dungeondepth ==33) {
				feeling = Feeling.TRAP;
			}else if (Dungeon.dungeondepth >55) {
				addItemToSpawn(new StoneOre());
			}
		}
		
		map = new int[getLength()];
		visited = new boolean[getLength()];
		Arrays.fill(visited, false);
		mapped = new boolean[getLength()];
		Arrays.fill(mapped, false);

		mobs = new HashSet<Mob>();
		heaps = new SparseArray<Heap>();
		blobs = new HashMap<Class<? extends Blob>, Blob>();
		plants = new SparseArray<Plant>();
		traps = new SparseArray<>();
		customTiles = new HashSet<>();

		do {
			Arrays.fill(map, feeling == Feeling.CHASM ? Terrain.CHASM
					: (feeling == Feeling.TRAP ? Terrain.CHASM : Terrain.WALL));
					//: (feeling == Feeling.SPECIAL_FLOOR ? Terrain.GLASS_WALL : Terrain.WALL)));
		} while (!build());

		decorate();

		buildFlagMaps();
		cleanWalls();

		createMobs();
		createItems();
	}

	public void reset() {

		for (Mob mob : mobs.toArray(new Mob[0])) {
			if (!mob.reset()) {
				mobs.remove(mob);
			}
		}
		createMobs();
		reset=true;
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		mobs = new HashSet<Mob>();
		heaps = new SparseArray<Heap>();
		blobs = new HashMap<Class<? extends Blob>, Blob>();
		plants = new SparseArray<Plant>();
		traps = new SparseArray<>();
		customTiles = new HashSet<>();

		map = bundle.getIntArray(MAP);
		visited = bundle.getBooleanArray(VISITED);
		mapped = bundle.getBooleanArray(MAPPED);

		entrance = bundle.getInt(ENTRANCE);
		exit = bundle.getInt(EXIT);
		//pitSign = bundle.getInt(PITSIGN);
		tent = bundle.getInt(TENT);
		currentmoves = bundle.getInt(MOVES);

		locked = bundle.getBoolean(LOCKED);
		
		cleared = bundle.getBoolean(CLEARED);
		reset = bundle.getBoolean(RESET);
		forcedone = bundle.getBoolean(FORCEDONE);
		genpetnext = bundle.getBoolean(GENPETNEXT);
		//sealedlevel = bundle.getBoolean(SEALEDLEVEL);

		adjustMapSize();

		Collection<Bundlable> collection = bundle.getCollection(HEAPS);
		for (Bundlable h : collection) {
			Heap heap = (Heap) h;
			if (resizingNeeded) {
				heap.pos = adjustPos(heap.pos);
			}
			if (!heap.isEmpty())
				heaps.put(heap.pos, heap);
		}

		collection = bundle.getCollection(PLANTS);
		for (Bundlable p : collection) {
			Plant plant = (Plant) p;
			if (resizingNeeded) {
				plant.pos = adjustPos(plant.pos);
			}
			plants.put(plant.pos, plant);
		}
		
		collection = bundle.getCollection( TRAPS );
		for (Bundlable p : collection) {
			Trap trap = (Trap)p;
			if (resizingNeeded) {
				trap.pos = adjustPos( trap.pos );
			}
			traps.put( trap.pos, trap );
		}		

		collection = bundle.getCollection(MOBS);
		for (Bundlable m : collection) {
			Mob mob = (Mob) m;
			if (mob != null) {
				if (resizingNeeded) {
					mob.pos = adjustPos(mob.pos);
				}
				mobs.add(mob);
			}
		}

		collection = bundle.getCollection(BLOBS);
		for (Bundlable b : collection) {
			Blob blob = (Blob) b;
			blobs.put(blob.getClass(), blob);
		}
		
		collection = bundle.getCollection( CUSTOM_TILES );
		for (Bundlable p : collection) {
			CustomTileVisual vis = (CustomTileVisual)p;
			if (resizingNeeded) {
				//TODO: add proper resizing logic here
			}
			customTiles.add( vis );
		}		

		feeling = bundle.getEnum(FEELING, Feeling.class);
		if (feeling == Feeling.DARK)
			viewDistance = (int) Math.ceil(viewDistance / 3f);

		buildFlagMaps();
		cleanWalls();
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(MAP, map);
		bundle.put(VISITED, visited);
		bundle.put(MAPPED, mapped);
		bundle.put(ENTRANCE, entrance);
		bundle.put(EXIT, exit);
		bundle.put(LOCKED, locked);
		bundle.put(HEAPS, heaps.values());
		bundle.put(PLANTS, plants.values());
		bundle.put( TRAPS, traps.values() );
		bundle.put( CUSTOM_TILES, customTiles );
		bundle.put(MOBS, mobs);
		bundle.put(BLOBS, blobs.values());
		bundle.put(FEELING, feeling);
		//bundle.put(PITSIGN, pitSign);
		bundle.put(TENT, tent);
		bundle.put(MOVES, currentmoves);
		bundle.put(CLEARED, cleared);
		bundle.put(RESET, reset);
		bundle.put(FORCEDONE, forcedone);
		bundle.put(GENPETNEXT, genpetnext);
		//bundle.put(SEALEDLEVEL, sealedlevel);
	}

	public int tunnelTile() {
		return feeling == Feeling.CHASM ? Terrain.EMPTY_SP : (feeling == Feeling.TRAP  ? Terrain.EMPTY_SP : Terrain.EMPTY );
	}

	private void adjustMapSize() {
		// For levels saved before 1.6.3
		// Seeing as shattered started on 1.7.1 this is never used, but the code
		// may be resused in future.
		if (map.length < getLength()) {

			resizingNeeded = true;
			loadedMapSize = (int) Math.sqrt(map.length);

			int[] map = new int[getLength()];
			Arrays.fill(map, Terrain.WALL);

			boolean[] visited = new boolean[getLength()];
			Arrays.fill(visited, false);

			boolean[] mapped = new boolean[getLength()];
			Arrays.fill(mapped, false);

			for (int i = 0; i < loadedMapSize; i++) {
				System.arraycopy(this.map, i * loadedMapSize, map, i * getWidth(),
						loadedMapSize);
				System.arraycopy(this.visited, i * loadedMapSize, visited, i
						* getWidth(), loadedMapSize);
				System.arraycopy(this.mapped, i * loadedMapSize, mapped, i
						* getWidth(), loadedMapSize);
			}

			this.map = map;
			this.visited = visited;
			this.mapped = mapped;

			entrance = adjustPos(entrance);
			exit = adjustPos(exit);
		} else {
			resizingNeeded = false;
		}
	}

	public int adjustPos(int pos) {
		return (pos / loadedMapSize) * getWidth() + (pos % loadedMapSize);
	}

	public String tilesTex() {
		return null;
	}

	public String waterTex() {
		return null;
	}

	abstract protected boolean build();

	abstract protected void decorate();

	abstract protected void createMobs();

	abstract protected void createItems();


	public void seal(){
		if (!locked) {
			locked = true;
			//Buff.affect(Dungeon.hero, LockedFloor.class);
		}
	}

	public void unseal(){
		if (locked) {
			locked = false;
		}
	}

	public void addVisuals(Scene scene) {
		for (int i = 0; i < getLength(); i++) {
			if (pit[i]) {
				scene.add(new WindParticle.Wind(i));
				if (i >= getWidth() && water[i - getWidth()]) {
					scene.add(new FlowParticle.Flow(i - getWidth()));
				}
			}
		}
	}

	public int nMobs() {
		return 0;
	}
	
	public Mob findMob( int pos ){
		for (Mob mob : mobs){
			if (mob.pos == pos){
				return mob;
			}
		}
		return null;
	}
	
	public int movepar(){
		return movepar;
	}


	public Actor respawner() {
		return new Actor() {
			@Override
			protected boolean act() {
				if (((Dungeon.dewDraw || Dungeon.dewWater)&& Dungeon.depth.cleared && mobs.size() < nMobs())
						|| (!(Dungeon.dewDraw || Dungeon.dewWater) && mobs.size() < nMobs())) 
				{
					Mob mob = Bestiary.mutable(Dungeon.dungeondepth);
					mob.state = mob.WANDERING;
					mob.pos = randomRespawnCell();
					if (Dungeon.hero.isAlive() && mob.pos != -1) {
						GameScene.add(mob);
					}
				}
				spend(TIME_TO_RESPAWN );
				return true;
			}
		};
	}
	
	public Actor respawnerPet() {
		return new Actor() {
			@Override
			protected boolean act() {
				int petpos = -1;
				int heropos = Dungeon.hero.pos;
				if (Actor.findChar(heropos) != null) {
					ArrayList<Integer> candidates = new ArrayList<Integer>();
					boolean[] passable = Floor.passable;
					for (int n : Floor.NEIGHBOURS8) {
						int c = heropos + n;
						if (passable[c] && Actor.findChar(c) == null) {
							candidates.add(c);
						}
					}
					petpos = candidates.size() > 0 ? Random.element(candidates) : -1;
				}
				if (petpos != -1 && Dungeon.hero.haspet && checkpet() == null) {
					PocketBallFull.teleportPet(Dungeon.hero);
				}

				spend(PET_TICK);
				return true;
			}
		};
	}


	private PET checkpet(){
		for (Mob mob : Dungeon.depth.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
	public void spawnPet(PET pet, Integer petpos, Integer heropos){
		pet.spawn();
		pet.HP = Dungeon.hero.petHP;
		pet.pos = petpos;
		pet.state = pet.HUNTING;
		pet.cooldown = Dungeon.hero.petCooldown;

		GameScene.add(pet);
		Actor.addDelayed(new Pushing(pet, heropos, petpos), -1f);
	}
	
	public boolean checkOriginalGenMobs (){
		for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			if (mob.originalgen){return true;}
		 }	
		return false;
	}
	
	public Actor regrower() {
		return new Actor() {
			@Override
			protected boolean act() {
				
				int growPos = -1;
				for (int i = 0; i < 20; i++) {
					growPos = randomRegrowthCell();
					
				if (growPos != -1) {
					break;
				}
			} 
			if (growPos != -1) {
				  if (map[growPos] == Terrain.GRASS){
				  Floor.set(growPos, Terrain.HIGH_GRASS);
				  } 
				  GameScene.updateMap();
				  Dungeon.observe();
			}		
				
			
				spend(REGROW_TIMER);
				return true;
			}
		};
	}
	
	public Actor waterer() {
		return new Actor() {
			@Override
			protected boolean act() {
				
				int growPos = -1;
				for (int i = 0; i < 20; i++) {
					growPos = randomRegrowthCell();
					
				if (growPos != -1) {
					break;
				}
			} 
			if (growPos != -1) {
				  if (map[growPos] == Terrain.GRASS || map[growPos] == Terrain.EMBERS){
				  Floor.set(growPos, Terrain.WATER);
				  } 
				  GameScene.updateMap();
				  Dungeon.observe();
			}		
				
			
				spend(REGROW_TIMER);
				return true;
			}
		};
	}	
	
	public int randomRegrowthCell() {
		int cell;
		int count = 1;
		do {
			cell = Random.Int(getLength());
			count++;
		} while (map[cell] != Terrain.GRASS && count < 100);
		     return cell;
	}
	
	public Actor floordropper() {
		return new Actor() {
			@Override
			protected boolean act() {
				
			int dropPos = -1;
			for (int i = 0; i < 20; i++) {
				dropPos = randomChasmCell();
				
				if (dropPos != -1) {
					//GLog.i("one ");
					break;
				}
			}		
			
			if (dropPos != -1) {
				
				//GLog.i("two %s",dropPos);
				  if (map[dropPos] == Terrain.EMPTY && Actor.findChar(dropPos) == null){
					  
					  //GLog.i("three ");
					  //if the tile above is not chasm then set to chasm floor. If is chasm then set to chasm
					 
					  if  (map[dropPos-getWidth()]==Terrain.WALL ||
							  map[dropPos-getWidth()]==Terrain.WALL_DECO){
						  
							  set(dropPos, Terrain.CHASM_WALL); 
							  //GLog.i("four ");
						   }
					  else if (map[dropPos-getWidth()]!=Terrain.CHASM && 
						  map[dropPos-getWidth()]!=Terrain.CHASM_FLOOR &&
						  map[dropPos-getWidth()]!=Terrain.CHASM_WALL)
					        {
								  set(dropPos, Terrain.CHASM_FLOOR); 
					         }  
						 else {
						   set(dropPos, Terrain.CHASM);
						  // GLog.i("five ");
					   }
					  	
					  if (map[dropPos+getWidth()]==Terrain.CHASM_FLOOR){
						  set(dropPos+getWidth(), Terrain.CHASM);
						 // GLog.i("six ");
					  }
					  
				
				    } 
				  GameScene.updateMap(dropPos);
				  GameScene.updateMap(dropPos-getWidth());
				  GameScene.updateMap(dropPos+getWidth());
				  Dungeon.observe();				  
			}				
			
				spend(DROP_TIMER);
				return true;
			}
		};
	}
	
	public int randomChasmCell() {
		int cell;
		int count = 1;
		do {
			cell = Random.Int(getWidth()+1, getLength()-(getWidth()+1));
			count++;
		} while (map[cell] != Terrain.EMPTY && count < 100);
		     return cell;
	}
	
	public int randomRespawnCell() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell] || Dungeon.visible[cell]
				|| Actor.findChar(cell) != null);
		return cell;
	}
	
	public int randomFallRespawnCell() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell]);
		return cell;
	}

	public int randomRespawnCellMob() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell] || Dungeon.visible[cell]
				|| Actor.findChar(cell) != null);
		return cell;
	}
	
	public int dropRespawnCell() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while ((map[cell]!=Terrain.EMPTY &&
				map[cell]!=Terrain.EMPTY_DECO &&
				map[cell]!=Terrain.EMPTY_SP &&
				map[cell]!=Terrain.GRASS &&
				map[cell]!=Terrain.WATER)
				|| !passable[cell]);
		return cell;
	}
	
	public int randomCell() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell]);
		return cell;
	}


	public int wellRespawnCellMob() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell] || map[cell]!=Terrain.EMPTY_WELL || Dungeon.visible[cell]
				|| Actor.findChar(cell) != null);
		return cell;
	}

	public int randomRespawnCellSheep(int start, int dist) {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!avoid[cell] || Actor.findChar(cell) != null || map[cell]!=Terrain.FLEECING_TRAP
				  || distance(start, cell) > dist);
		return cell;
	}
	
	public int countFleeceTraps(int start, int dist) {
		int count=0;
		for (int cell = 0; cell < getLength(); cell++) {
		  if(avoid[cell] && Actor.findChar(cell) == null && map[cell]==Terrain.FLEECING_TRAP && distance(start, cell) < dist){
			  count++;
		  }
		}
		return count;		
	}
	
	
	public int randomRespawnCellFish() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell] || Actor.findChar(cell) != null || (map[cell]!=Terrain.EMPTY  && map[cell]!=Terrain.EMPTY_SP && map[cell]!=Terrain.GRASS && map[cell]!=Terrain.HIGH_GRASS) );
		return cell;
	}

	public int randomDestination() {
		int cell;
		do {
			cell = Random.Int(getLength());
		} while (!passable[cell]);
		return cell;
	}

	public void addItemToSpawn(Item item) {
		if (item != null) {
			itemsToSpawn.add(item);
		}
	}
		
	public void addSpecialItemToSpawn(Item item) {
		if (item != null) {
			itemsSpecialToSpawn.add(item);
		}
	}
		
	public Item findPrizeItem() {
		return findPrizeItem(null);
	}

	public Item findPrizeItem(Class<? extends Item> match) {
		if (itemsToSpawn.size() == 0)
			return null;

		if (match == null) {
			Item item = Random.element(itemsToSpawn);
			itemsToSpawn.remove(item);
			return item;
		}

		for (Item item : itemsToSpawn) {
			if (match.isInstance(item)) {
				itemsToSpawn.remove(item);
				return item;
			}
		}

		return null;
	}

	protected void buildFlagMaps() {

		for (int i = 0; i < getLength(); i++) {
			int flags = Terrain.flags[map[i]];
			passable[i] = (flags & Terrain.PASSABLE) != 0;
			//losBlocking[i] = (flags & Terrain.LOS_BLOCKING) != 0;
            losBlockLow[i]	= (flags & Terrain.LOS_BLOCKING) != 0;
            losBlockHigh[i]	= losBlockLow[i] && (flags & Terrain.WALLS) != 0 ;			
			flamable[i] = (flags & Terrain.FLAMABLE) != 0;
			secret[i] = (flags & Terrain.SECRET) != 0;
			solid[i] = (flags & Terrain.SOLID) != 0;
			avoid[i] = (flags & Terrain.AVOID) != 0;
			water[i] = (flags & Terrain.LIQUID) != 0;
			pit[i] = (flags & Terrain.PIT) != 0;
			lightpass[i] = (flags & Terrain.LIGHTPASS) != 0;
		}

		int lastRow = getLength() - getWidth();
		for (int i = 0; i < getWidth(); i++) {
			passable[i] = avoid[i] = false;
			passable[lastRow + i] = avoid[lastRow + i] = false;
		}
		for (int i = getWidth(); i < lastRow; i += getWidth()) {
			passable[i] = avoid[i] = false;
			passable[i + getWidth() - 1] = avoid[i + getWidth() - 1] = false;
		}

		for (int i = getWidth(); i < getLength() - getWidth(); i++) {

			if (water[i]) {
				int t = Terrain.WATER_TILES;
				for (int j = 0; j < NEIGHBOURS4.length; j++) {
					if ((Terrain.flags[map[i + NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
						t += 1 << j;
					}
				}
				map[i] = t;
			}

			if (pit[i]) {
				if (!pit[i - getWidth()]) {
					int c = map[i - getWidth()];
					if (c == Terrain.EMPTY_SP || c == Terrain.STATUE_SP) {
						map[i] = Terrain.CHASM_FLOOR_SP;
					} else if (water[i - getWidth()]) {
						map[i] = Terrain.CHASM_WATER;
					} else if ((Terrain.flags[c] & Terrain.UNSTITCHABLE) != 0) {
						map[i] = Terrain.CHASM_WALL;
					} else {
						map[i] = Terrain.CHASM_FLOOR;
					}
				}
			}
		}
	}
	
	private int getWaterTile( int pos ) {
		int t = Terrain.WATER_TILES;
		for (int j=0; j < NEIGHBOURS4.length; j++) {
			if ((Terrain.flags[map[pos + NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
				t += 1 << j;
			}
		}
		return t;
	}

	public void destroy( int pos ) {
		if ((Terrain.flags[map[pos]] & Terrain.UNSTITCHABLE) == 0) {

			set( pos, Terrain.EMBERS );

		} else {
			boolean flood = false;
			for (int j=0; j < NEIGHBOURS4.length; j++) {
				if (water[pos + NEIGHBOURS4[j]]) {
					flood = true;
					break;
				}
			}
			if (flood) {
				set( pos, getWaterTile( pos ) );
			} else {
				set( pos, Terrain.EMBERS );
			}
		}
	}	

	protected void cleanWalls() {
		for (int i = 0; i < getLength(); i++) {

			boolean d = false;

			for (int j = 0; j < NEIGHBOURS9.length; j++) {
				int n = i + NEIGHBOURS9[j];
				if (n >= 0 && n < getLength() && map[n] != Terrain.WALL
						&& map[n] != Terrain.WALL_DECO) {
					d = true;
					break;
				}
			}

			if (d) {
				d = false;

				for (int j = 0; j < NEIGHBOURS9.length; j++) {
					int n = i + NEIGHBOURS9[j];
					if (n >= 0 && n < getLength() && !pit[n]) {
						d = true;
						break;
					}
				}
			}

			discoverable[i] = d;
		}
	}

	public static void set(int cell, int terrain) {
		Painter.set(Dungeon.depth, cell, terrain);

		int flags = Terrain.flags[terrain];
		passable[cell] = (flags & Terrain.PASSABLE) != 0;
		//losBlocking[cell] = (flags & Terrain.LOS_BLOCKING) != 0;
		losBlockLow[cell]	= (flags & Terrain.LOS_BLOCKING) != 0;
		losBlockHigh[cell]	= losBlockLow[cell] && (flags & Terrain.WALLS) != 0;		
		flamable[cell] = (flags & Terrain.FLAMABLE) != 0;
		secret[cell] = (flags & Terrain.SECRET) != 0;
		solid[cell] = (flags & Terrain.SOLID) != 0;
		avoid[cell] = (flags & Terrain.AVOID) != 0;
		pit[cell] = (flags & Terrain.PIT) != 0;
		lightpass[cell] = (flags & Terrain.LIGHTPASS) != 0;
		water[cell] = terrain == Terrain.WATER	|| (terrain >= Terrain.WATER_TILES && terrain<Terrain.WOOL_RUG);
	}

	public int checkdew(){
		int dewdrops=0;
		for (int i = 0; i < LENGTH; i++) {
			Heap heap = heaps.get(i);
			if (heap != null)
				dewdrops += heap.dewdrops();
		 }
		return dewdrops;
	}
	
	
	public Heap drop(Item item, int cell) {

		if ((Hero.skins==5 && item instanceof Armor) || item == null ){

		
			//create a dummy heap, give it a dummy sprite, don't add it to the game, and return it.
			//effectively nullifies whatever the logic calling this wants to do, including dropping items.
			Heap heap = new Heap();
			ItemSprite sprite = heap.sprite = new ItemSprite();
			sprite.link(heap);
			return heap;

		}	
	
		Heap heap = heaps.get(cell);
		if (heap == null) {

			heap = new Heap();
			heap.seen = Dungeon.visible[cell];
			heap.pos = cell;
			heap.drop(item);
			if (map[cell] == Terrain.CHASM
					|| (Dungeon.depth != null && pit[cell])) {
				Dungeon.dropToChasm(item);
				GameScene.discard(heap);
			} else {
				heaps.put(cell, heap);
				GameScene.add(heap);
			}

		} else if (heap.type == Heap.Type.LOCKED_CHEST
				|| heap.type == Heap.Type.CRYSTAL_CHEST
				|| heap.type == Heap.Type.G_MIMIC
				) {

			int n;
			do {
				n = cell + Floor.NEIGHBOURS8[Random.Int(8)];
			} while (!Floor.passable[n] && !Floor.avoid[n] && n == cell);
			return drop(item, n);

		} else {
			heap.drop(item);
		}

		if (Dungeon.depth != null) {
			press(cell, null);
		}

		return heap;
	}

	public Plant plant(Plant.Seed seed, int pos) {

		Plant plant = plants.get(pos);
		if (plant != null) {
			plant.wither();
		}

		if (map[pos] == Terrain.HIGH_GRASS || 
		    map[pos] == Terrain.OLD_HIGH_GRASS || 
		    map[pos] == Terrain.EMPTY  || 
		    map[pos] == Terrain.EMBERS || 
			map[pos] == Terrain.EMPTY_DECO) {
			map[pos] = Terrain.GRASS;
			GameScene.updateMap(pos);
		}

		plant = seed.couch(pos);
		plants.put(pos, plant);

		GameScene.add(plant);

		return plant;
	}
	
	public Plant explant(Plant.Seed seed, int pos) {

		Plant plant = plants.get(pos);
		if (plant != null) {
			plant.wither();
		}

		if (map[pos] == Terrain.HIGH_GRASS || 
		    map[pos] == Terrain.OLD_HIGH_GRASS || 
		    map[pos] == Terrain.EMPTY  || 
		    map[pos] == Terrain.EMBERS || 
			map[pos] == Terrain.EMPTY_DECO) {
			map[pos] = Terrain.GRASS;
			GameScene.updateMap(pos);
		}

		plant = seed.excouch(pos);
		plants.put(pos, plant);

		GameScene.add(plant);

		return plant;
	}	

	public void uproot(int pos) {
		plants.remove(pos);
	}

	public Trap setTrap( Trap trap, int pos ){
		Trap existingTrap = traps.get(pos);
		if (existingTrap != null){
			traps.remove( pos );
			if(existingTrap.sprite != null) existingTrap.sprite.kill();
		}
		trap.set( pos );
		traps.put( pos, trap );
		GameScene.add(trap);
		return trap;
	}

	public void disarmTrap( int pos ) {
		set(pos, Terrain.INACTIVE_TRAP);
		GameScene.updateMap(pos);
	}

	public void discover( int cell ) {
		set( cell, Terrain.discover( map[cell] ) );
		Trap trap = traps.get( cell );
		if (trap != null)
			trap.reveal();
		GameScene.updateMap( cell );
	}
	
	public int pitCell() {
		return randomRespawnCell();
	}

	public void press(int cell, Char ch) {

		if (ch != null && pit[cell] && !ch.flying) {
			if (ch == Dungeon.hero) {
				Chasm.heroFall(cell);
			} else if (ch instanceof Mob) {
				Chasm.mobFall( (Mob)ch );
			}
			return;
		}

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean frozen = timeFreeze != null;

		Trap trap = null;
		boolean fleece = false;
		boolean sheep = false;

		switch (map[cell]) {

		case Terrain.SECRET_TRAP:
			GLog.i( Messages.get(Floor.class, "hidden_plate") );
		case Terrain.TRAP:
			trap = traps.get( cell );
			break;		
		
		case Terrain.FLEECING_TRAP:
			trap = null;
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner){
				fleece=true;
			}			
			if (ch != null)
				FleecingTrap.trigger(cell, ch);
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			trap = null;
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner){
				sheep=true;
				ChangeSheepTrap.trigger(cell, ch);
			}						
			break;
			
		case Terrain.HIGH_GRASS:
			HighGrass.trample(this, cell, ch);
			break;
		case Terrain.OLD_HIGH_GRASS:
			OldHighGrass.trample(this, cell, ch);
			break;
		case Terrain.DEW_BLESS:
			DewBlessPlace.trample(this, cell, ch);
			break;
		//case Terrain.SIGN:
		//	Sign.read(cell);
		//	break;
		case Terrain.WELL:
			WellWater.affectCell(cell);
			break;

		case Terrain.ALCHEMY:
			/*if (ch == null) {
				Alchemy.transmute(cell);
			}*/
			    Alchemy alchemy = new Alchemy();
                blobs.put( Alchemy.class, alchemy );
			break;

		case Terrain.DOOR:
			Door.enter(cell);
			break;
		}

		if (trap != null && !frozen) {

			if (ch == Dungeon.hero)
				Dungeon.hero.interrupt();

			trap.activate(ch);

		} else if (trap != null && frozen){

			Sample.INSTANCE.play(Assets.SND_TRAP);

			discover(cell);

			timeFreeze.setDelayedPress( cell );

		}		
		
		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(ch);
		}
	}

	public void mobPress(Mob mob) {

		int cell = mob.pos;

		if (pit[cell] && !mob.flying) {
			Chasm.mobFall(mob);
			return;
		}

		Trap trap = null;
		boolean fleece = false;
		boolean sheep = false;
		switch (map[cell]) {
			
		case Terrain.FLEECING_TRAP:
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner){
				fleece=true;
			}
			FleecingTrap.trigger(cell, mob);
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner){
				sheep=true;
				ChangeSheepTrap.trigger(cell, mob);
			}						
			break;
			
		case Terrain.SOKOBAN_ITEM_REVEAL:
			trap = null;
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner){
				HeapGenTrap.trigger(cell, mob);
				sheep=true;
			}						
			break;

		/*case Terrain.POISON_TRAP:
			PoisonTrap.trigger(cell, mob);
			break;

		case Terrain.ALARM_TRAP:
			AlarmTrap.trigger(cell, mob);
			break;

		case Terrain.LIGHTNING_TRAP:
			LightningTrap.trigger(cell, mob);
			break;

		case Terrain.GRIPPING_TRAP:
			GrippingTrap.trigger(cell, mob);
			break;

		case Terrain.SUMMONING_TRAP:
			SummoningTrap.trigger(cell, mob);
			break;*/
		case Terrain.TRAP:
			trap = traps.get( cell );
			break;
		case Terrain.DOOR:
			Door.enter(cell);
            break;
		}

		if (trap != null) {
			trap.activate(mob);
		}		
		
		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(mob);
		}
	}

	public boolean[] updateFieldOfView(Char c) {

		int cx = c.pos % getWidth();
		int cy = c.pos / getWidth();

		boolean sighted = c.buff(Blindness.class) == null
				&& c.buff(Shadows.class) == null
				&& c.buff(TimekeepersHourglass.timeStasis.class) == null
				&& c.buff(TentSleep.class) == null
				&& c.isAlive();
		if (sighted) {
			ShadowCaster.castShadow(cx, cy, fieldOfView, c.viewDistance, c.flying);
		} else {
			Arrays.fill(fieldOfView, false);
		}

		int sense = 1;
		if (c.isAlive()) {
			for (Buff b : c.buffs(MindVision.class)) {
				sense = Math.max(((MindVision) b).distance, sense);
			}
		}

		if (!sighted || sense > 1) {

			int ax = Math.max(0, cx - sense);
			int bx = Math.min(cx + sense, getWidth() - 1);
			int ay = Math.max(0, cy - sense);
			int by = Math.min(cy + sense, HEIGHT - 1);

			int len = bx - ax + 1;
			int pos = ax + ay * getWidth();
			for (int y = ay; y <= by; y++, pos += getWidth()) {
				Arrays.fill(fieldOfView, pos, pos + len, true);
			}

			for (int i = 0; i < getLength(); i++) {
				fieldOfView[i] &= discoverable[i];
			}
		}

		if (c.isAlive() && c == Dungeon.hero) {
			if (c.buff(MindVision.class) != null) {
				for (Mob mob : mobs) {
					int p = mob.pos;
					fieldOfView[p] = true;
					fieldOfView[p + 1] = true;
					fieldOfView[p - 1] = true;
					fieldOfView[p + getWidth() + 1] = true;
					fieldOfView[p + getWidth() - 1] = true;
					fieldOfView[p - getWidth() + 1] = true;
					fieldOfView[p - getWidth() - 1] = true;
					fieldOfView[p + getWidth()] = true;
					fieldOfView[p - getWidth()] = true;
				}
			} else if (((Hero) c).heroClass == HeroClass.HUNTRESS) {
				for (Mob mob : mobs) {
					int p = mob.pos;
					if (distance(c.pos, p) == 2) {
						fieldOfView[p] = true;
						fieldOfView[p + 1] = true;
						fieldOfView[p - 1] = true;
						fieldOfView[p + getWidth() + 1] = true;
						fieldOfView[p + getWidth() - 1] = true;
						fieldOfView[p - getWidth() + 1] = true;
						fieldOfView[p - getWidth() - 1] = true;
						fieldOfView[p + getWidth()] = true;
						fieldOfView[p - getWidth()] = true;
					}
				}
		    } 
			if (c.buff(Awareness.class) != null) {
				for (Heap heap : heaps.values()) {
					int p = heap.pos;
					fieldOfView[p] = true;
					fieldOfView[p + 1] = true;
					fieldOfView[p - 1] = true;
					fieldOfView[p + getWidth() + 1] = true;
					fieldOfView[p + getWidth() - 1] = true;
					fieldOfView[p - getWidth() + 1] = true;
					fieldOfView[p - getWidth() - 1] = true;
					fieldOfView[p + getWidth()] = true;
					fieldOfView[p - getWidth()] = true;
				}
			}

			for (Mob mob : mobs) {
				if (mob.isAlive() && mob.buff(WatchOut.class) != null) {
					int p = mob.pos;
					fieldOfView[p] = true;
					fieldOfView[p + 1] = true;
					fieldOfView[p - 1] = true;
					fieldOfView[p + getWidth() + 1] = true;
					fieldOfView[p + getWidth() - 1] = true;
					fieldOfView[p - getWidth() + 1] = true;
					fieldOfView[p - getWidth() - 1] = true;
					fieldOfView[p + getWidth()] = true;
					fieldOfView[p - getWidth()] = true;
				}
				if (mob.isAlive() && mob.buff(HiddenShadow.class) != null) {
					int p = mob.pos;
					fieldOfView[p] = false;
				}
			}
			for (int i = 0; i <  Floor.getLength(); i++)  {
				for (Blob blob : Dungeon.depth.blobs.values()) {
					if (blob.cur[i] > 0 && blob instanceof TorchLight) {
						if (distance(c.pos, i) < 6) {
							fieldOfView[i] = true;
							fieldOfView[i + 1] = true;
							fieldOfView[i + 2] = true;
							fieldOfView[i - 1] = true;
							fieldOfView[i - 2] = true;
							fieldOfView[i + getWidth() + 1] = true;
							fieldOfView[i + getWidth() - 1] = true;
							fieldOfView[i - getWidth() + 1] = true;
							fieldOfView[i - getWidth() - 1] = true;
							fieldOfView[i + getWidth()] = true;
							fieldOfView[i - getWidth()] = true;
							fieldOfView[i + 2 * getWidth()] = true;
							fieldOfView[i - 2 * getWidth()] = true;
						}
						if (c.buff(DewVial.DewLight.class) != null){
							fieldOfView[i] = true;
							fieldOfView[i + 1] = true;
							fieldOfView[i - 1] = true;
							fieldOfView[i + getWidth() + 1] = true;
							fieldOfView[i + getWidth() - 1] = true;
							fieldOfView[i - getWidth() + 1] = true;
							fieldOfView[i - getWidth() - 1] = true;
							fieldOfView[i + getWidth()] = true;
							fieldOfView[i - getWidth()] = true;
						}
					//} else if (blob.cur[i] > 0 && blob instanceof DarkGas) {
					//	fieldOfView[i] = false;
					}
				}
			}
		}
		

		for (Heap heap : heaps.values())
				if (!heap.seen && fieldOfView[heap.pos])
						heap.seen = true;
		
		return fieldOfView;
	}

	public static int distance(int a, int b) {
		int ax = a % getWidth();
		int ay = a / getWidth();
		int bx = b % getWidth();
		int by = b / getWidth();
		return Math.max(Math.abs(ax - bx), Math.abs(ay - by));
	}

	public static boolean adjacent(int a, int b) {
		int diff = Math.abs(a - b);
		return diff == 1 || diff == getWidth() || diff == getWidth() + 1
				|| diff == getWidth() - 1;
	}

	public static boolean insideMap( int tile ){
				//outside map array
	    return !((tile <= -1 || tile >= LENGTH) ||
				//top and bottom row
				 (tile <= 47 || tile >= LENGTH - WIDTH) ||
				//left and right column
				(tile % WIDTH == 0 || tile % WIDTH == 47));
			
	}

	public String tileName(int tile) {

		if (tile >= Terrain.WATER_TILES) {
			return tileName(Terrain.WATER);
		}
		// && tile<Terrain.WOOL_RUG

		if (tile != Terrain.CHASM && (Terrain.flags[tile] & Terrain.PIT) != 0) {
			return tileName(Terrain.CHASM);
		}

		switch (tile) {
		case Terrain.CHASM:
			return Messages.get(Floor.class, "chasm_name");
		case Terrain.EMPTY:
		case Terrain.EMPTY_SP:
		case Terrain.EMPTY_DECO:
		//case Terrain.SECRET_TOXIC_TRAP:
		//case Terrain.SECRET_FIRE_TRAP:
		//case Terrain.SECRET_PARALYTIC_TRAP:
		//case Terrain.SECRET_POISON_TRAP:
		//case Terrain.SECRET_ALARM_TRAP:
		//case Terrain.SECRET_LIGHTNING_TRAP:
		case Terrain.SECRET_TRAP:
			return Messages.get(Floor.class, "floor_name");
		case Terrain.GRASS:
			return Messages.get(Floor.class, "grass_name");
		case Terrain.WATER:
			return Messages.get(Floor.class, "water_name");
		case Terrain.WALL:
		case Terrain.WALL_DECO:
		case Terrain.SECRET_DOOR:
			case Terrain.WALL_GROUND:
			case Terrain.WALL_LIVER:
			return Messages.get(Floor.class, "wall_name");
		case Terrain.DOOR:
			return Messages.get(Floor.class, "closed_door_name");
		case Terrain.OPEN_DOOR:
			return Messages.get(Floor.class, "open_door_name");
		case Terrain.ENTRANCE:
			return Messages.get(Floor.class, "entrace_name");
		case Terrain.EXIT:
			return Messages.get(Floor.class, "exit_name");
		case Terrain.EMBERS:
			return Messages.get(Floor.class, "embers_name");
		case Terrain.LOCKED_DOOR:
			return Messages.get(Floor.class, "locked_door_name");
		case Terrain.PEDESTAL:
			return Messages.get(Floor.class, "pedestal_name");
		case Terrain.BARRICADE:
			return Messages.get(Floor.class, "barricade_name");
		case Terrain.HIGH_GRASS:
			return Messages.get(Floor.class, "high_grass_name");
		case Terrain.OLD_HIGH_GRASS:
			return Messages.get(Floor.class, "old_high_grass_name");
		case Terrain.LOCKED_EXIT:
			return Messages.get(Floor.class, "locked_exit_name");
		case Terrain.UNLOCKED_EXIT:
			return Messages.get(Floor.class, "unlocked_exit_name");
		case Terrain.SIGN:
			return Messages.get(Floor.class, "sign_name");
		case Terrain.WELL:
			return Messages.get(Floor.class, "well_name");
		case Terrain.EMPTY_WELL:
			return Messages.get(Floor.class, "empty_well_name");
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return Messages.get(Floor.class, "statue_name");
		case Terrain.BROKEN_DOOR:
			return Messages.get(Floor.class, "broken_door_name");
		case Terrain.TENT:
			return Messages.get(Floor.class, "tent_name");
		case Terrain.BED:
			return Messages.get(Floor.class, "bed_name");
		case Terrain.DEW_BLESS:
			return Messages.get(Floor.class, "dew_bless_name");
		case Terrain.IRON_MAKER:
			return Messages.get(Floor.class, "iron_maker_name");
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
		//case Terrain.POISON_TRAP:
			//return "Poison dart trap";
		//case Terrain.ALARM_TRAP:
			//return "Alarm trap";
		//case Terrain.LIGHTNING_TRAP:
			//return "Lightning trap";
		//case Terrain.GRIPPING_TRAP:
			//return "Gripping trap";
		//case Terrain.SUMMONING_TRAP:
			//return "Summoning trap"

		case Terrain.INACTIVE_TRAP:
			return Messages.get(Floor.class, "inactive_trap_name");
		case Terrain.BOOKSHELF:
			return Messages.get(Floor.class, "bookshelf_name");
		case Terrain.ALCHEMY:
			return Messages.get(Floor.class, "alchemy_name");
		case Terrain.SHRUB:
			return Messages.get(Floor.class, "shrub_name");
		case Terrain.GLASS_WALL:
			return Messages.get(Floor.class, "glass_name");
		case Terrain.FLOWER_POT:
				return Messages.get(Floor.class, "flower_pot_name");
		default:
			return Messages.get(Floor.class, "default_name");
		}
	}

	public String tileDesc(int tile) {

		switch (tile) {
			case Terrain.CHASM:
				return Messages.get(Floor.class, "chasm_desc");
			case Terrain.WATER:
				return Messages.get(Floor.class, "water_desc");
			case Terrain.ENTRANCE:
				return Messages.get(Floor.class, "entrance_desc");
			case Terrain.EXIT:
			case Terrain.UNLOCKED_EXIT:
				return Messages.get(Floor.class, "exit_desc");
			case Terrain.EMBERS:
				return Messages.get(Floor.class, "embers_desc");
			case Terrain.HIGH_GRASS:
				return Messages.get(Floor.class, "high_grass_desc");
			case Terrain.OLD_HIGH_GRASS:
				return Messages.get(Floor.class, "old_high_grass_desc");
		case Terrain.SHRUB:
			return Messages.get(Floor.class, "shrub_desc");
			case Terrain.LOCKED_DOOR:
				return Messages.get(Floor.class, "locked_door_desc");
			case Terrain.LOCKED_EXIT:
				return Messages.get(Floor.class, "locked_exit_desc");
			case Terrain.BARRICADE:
				return Messages.get(Floor.class, "barricade_desc");
			case Terrain.SIGN:
				return Messages.get(Floor.class, "sign_desc");
		case Terrain.TENT:
			return Messages.get(Floor.class, "tent_desc");
		case Terrain.BED:
			return Messages.get(Floor.class, "bed_desc");
		case Terrain.DEW_BLESS:
			return Messages.get(Floor.class, "dew_bless_desc");
		case Terrain.IRON_MAKER:
			return Messages.get(Floor.class, "iron_maker_desc");
		//case Terrain.PARALYTIC_TRAP:
		//case Terrain.POISON_TRAP:
		//case Terrain.ALARM_TRAP:
		//case Terrain.LIGHTNING_TRAP:
		//case Terrain.GRIPPING_TRAP:

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
		case Terrain.INACTIVE_TRAP:
			return Messages.get(Floor.class, "inactive_trap_desc");
		case Terrain.STATUE:
		case Terrain.STATUE_SP:
			return Messages.get(Floor.class, "statue_desc");
		case Terrain.BROKEN_DOOR:
			return Messages.get(Floor.class, "broken_door_desc");
		case Terrain.ALCHEMY:
			return Messages.get(Floor.class, "alchemy_desc");
		case Terrain.EMPTY_WELL:
			return Messages.get(Floor.class, "empty_well_desc");
		case Terrain.GLASS_WALL:
			return Messages.get(Floor.class, "glass_desc");
			case Terrain.FLOWER_POT:
				return Messages.get(Floor.class, "flower_pot_desc");
	 case Terrain.WOOL_RUG:
			return Messages.get(Floor.class, "wool_rug_desc");
		default:	
			if (tile >= Terrain.WATER_TILES) {
				return tileDesc(Terrain.WATER);
			}
			if ((Terrain.flags[tile] & Terrain.PIT) != 0) {
				return tileDesc(Terrain.CHASM);
			}
			return Messages.get(Floor.class, "default_desc");
		}
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
	public boolean darkness(){
		return (Dungeon.isChallenged(Challenges.DARKNESS) || Dungeon.depth.feeling == Feeling.DARK);
	}

	public boolean glasstown(){
		return  Dungeon.depth.feeling == Feeling.SPECIAL_FLOOR;
	}	
	
	public static int getWidth() {
		return WIDTH;
	}

	public static int getLength() {
		return LENGTH;
	}
}
