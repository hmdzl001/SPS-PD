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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Bestiary;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.food.meatfood.SmallMeat;
import com.hmdzl.spspd.items.keys.GoldenKey;
import com.hmdzl.spspd.items.misc.LuckyBadge;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.levels.Room.Type;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.levels.painters.ShopPainter;
import com.hmdzl.spspd.levels.traps.Trap;
import com.hmdzl.spspd.levels.traps.WornTrap;
import com.hmdzl.spspd.levels.traps.damagetrap.FireDamageTrap;
import com.watabou.utils.Bundle;
import com.watabou.utils.Graph;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static com.hmdzl.spspd.items.Heap.Type.HEAP;

public abstract class  RegularLevel extends Level {

	protected HashSet<Room> rooms;

	protected Room roomEntrance;
	protected Room roomExit;

	protected ArrayList<Room.Type> specials;
	protected ArrayList<Room.Type> secrets;
	protected ArrayList<Room.Type> powerspecial;
	protected ArrayList<Room.Type> wisdomspecial;

	public int secretDoors;

	@Override
	protected boolean build() {

		if (!initRooms()) {
			return false;
		}

		int distance;
		int retry = 0;
		int minDistance = (int) Math.sqrt(rooms.size());
		do {
			do {
				roomEntrance = Random.element(rooms);
			} while (roomEntrance.width() < 5 || roomEntrance.height() < 5);

			do {
				roomExit = Random.element(rooms);
			} while (roomExit == roomEntrance || roomExit.width() < 5
					|| roomExit.height() < 5);

			Graph.buildDistanceMap(rooms, roomExit);
			distance = roomEntrance.distance();

			if (retry++ > 15) {
				return false;
			}

		} while (distance < minDistance );

		roomEntrance.type = Type.ENTRANCE;
		roomExit.type = Type.EXIT;

		HashSet<Room> connected = new HashSet<Room>();
		connected.add(roomEntrance);

		Graph.buildDistanceMap(rooms, roomExit);
		List<Room> path = Graph.buildPath(rooms, roomEntrance, roomExit);

		Room room = roomEntrance;
		for (Room next : path) {
			room.connect(next);
			room = next;
			connected.add(room);
		}

		Graph.setPrice(path, roomEntrance.distance);

		Graph.buildDistanceMap(rooms, roomExit);
		path = Graph.buildPath(rooms, roomEntrance, roomExit);

		room = roomEntrance;
		for (Room next : path) {
			room.connect(next);
			room = next;
			connected.add(room);
		}

		int nConnected = (int) (rooms.size()* Random.Float(0.5f, 0.7f));
		while (connected.size() < nConnected) {

			Room cr = Random.element(connected);
			Room or = Random.element(cr.neigbours);
			if (!connected.contains(or)) {

				cr.connect(or);
				connected.add(or);
			}
		}

		if (Dungeon.shopOnLevel()) {
			Room shop = null;
			for (Room r : roomEntrance.connected.keySet()) {
				if (r.connected.size() == 1
						&& ((r.width() - 1) * (r.height() - 1) >= ShopPainter
								.spaceNeeded())) {
					shop = r;
					break;
				}
			}

			if (shop == null) {
				return false;
			} else {
				shop.type = Room.Type.SHOP;
			}

		}		
		
		specials = new ArrayList<Room.Type>(Room.SPECIALS);
		secrets = new ArrayList<Room.Type>(Room.HIDE);
		
		//if (Dungeon.skins == 5) {
			// no sense in giving an armor reward room on a run with no armor.
		    //specials.remove(Room.Type.CRYPT);
		//}
		//if (Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
			// sorry warden, no lucky sungrass or blandfruit seeds for you!
			//specials.remove(Room.Type.GARDEN);
		//}
		
		if (Dungeon.depth > 50 && Dungeon.depth < 100) {
			specials.remove(Room.Type.RUIN_ROOM);
			specials.remove(Room.Type.MATERIAL);
			specials.remove(Room.Type.CRYPT);
			specials.remove(Room.Type.GARDEN);
			specials.remove(Room.Type.COOKING);
			specials.remove(Room.Type.LIBRARY);
			specials.remove(Room.Type.MAGIC_WELL);
			specials.remove(Room.Type.POOL);
			specials.remove(Room.Type.STATUE);
			specials.remove(Room.Type.STORAGE);
			specials.remove(Room.Type.TRAPS);
			specials.remove(Room.Type.JUNGLE);
			specials.remove(Room.Type.VAULT);
	}
		
		
		if (!assignRoomType())
			return false;

		paint();
		paintWater();
		paintGrass();
		placeTraps();

		return true;
	}

	protected void placeSign(){
		while (true) {
			int pos = roomEntrance.random();
			if (pos != entrance && traps.get(pos) == null && findMob(pos) == null) {
				map[pos] = Terrain.SIGN;
				break;
			}
		}
	}
	
	protected boolean initRooms() {

		rooms = new HashSet<Room>();
		split(new Rect(0, 0, getWidth() - 1, HEIGHT - 1));

		if (rooms.size() < 20) {
			return false;
		}

		Room[] ra = rooms.toArray(new Room[0]);
		for (int i = 0; i < ra.length - 1; i++) {
			for (int j = i + 1; j < ra.length; j++) {
				ra[i].addNeigbour(ra[j]);
			}
		}

		return true;
	}

	protected boolean assignRoomType() {

		int specialRooms = 0;
		int hideRooms = 0;

		for (Room r : rooms) {
			if (r.type == Type.NULL && r.connected.size() == 1) {

				if (secrets.size() > 0 && r.width() > 5 && r.height() > 5
						&& hideRooms == 0) {

						int n = secrets.size();
						r.type = secrets.get(Random.Int(n));
						//if (r.type == Type.RUIN_ROOM) {
						//	weakFloorCreated = true;
						//}

					Room.useType(r.type);
					//secrets.remove(r.type);
					hideRooms++;

				} else if (specials.size() > 0 && r.width() > 5 && r.height() > 5
						&& Random.Int(specialRooms * specialRooms + 2) == 0) {

					if (Dungeon.depth % 5 == 2 && specials.contains(Type.COOKING)) {
						r.type = Type.COOKING;

					} else if (Dungeon.depth % 5 == 3 && specials.contains(Type.RUIN_ROOM)) {
						r.type = Type.RUIN_ROOM;

					} else {

						int n = specials.size();
						r.type = specials.get(Math.min(Random.Int(n),
								Random.Int(n)));
						//if (r.type == Type.RUIN_ROOM) {
					//		weakFloorCreated = true;
						//}

					}

					Room.useType(r.type);
					specials.remove(r.type);
					specialRooms++;

				} else if (Random.Int(2) == 0) {

					HashSet<Room> neigbours = new HashSet<Room>();
					for (Room n : r.neigbours) {
						if (!r.connected.containsKey(n)
								&& !Room.SPECIALS.contains(n.type)&& !Room.HIDE.contains(n.type)) {

							neigbours.add(n);
						}
					}
					if (neigbours.size() > 1) {
						r.connect(Random.element(neigbours));
					}
				}
			}
		}

		int count = 0;
		for (Room r : rooms) {
			if (r.type == Type.NULL) {
				int connections = r.connected.size();
				if (connections == 0) {

				} else if (Random.Int(connections * connections) == 0) {
					r.type = Type.STANDARD;
					count++;
				} else {
					r.type = Type.TUNNEL;
				}
			}
		}

		while (count < 4) {
			Room r = randomRoom(Type.TUNNEL, 1);
			if (r != null) {
				r.type = Type.STANDARD;
				count++;
			}
		}

		return true;
	}

	protected void paintWater() {
		boolean[] lake = water();
		//for (int i = 0; i < getLength(); i++) {
		//	if (map[i] == Terrain.EMPTY && lake[i]) {
		//		map[i] = Terrain.WATER;
		//	}
		//}
		for (int i = 0; i < getLength(); i++) {
			if (map[i] == Terrain.EMPTY && lake[i]) {
				map[i] = (Random.Int(25) < 1) ? Terrain.OLD_HIGH_GRASS
						: Terrain.WATER;
			}
		}
	}

	protected void paintGrass() {
		boolean[] grass = grass();

		if (feeling == Feeling.GRASS) {

			for (Room room : rooms) {
				if (room.type != Type.NULL && room.type != Type.PASSAGE
						&& room.type != Type.TUNNEL) {
					grass[(room.left + 1) + (room.top + 1) * getWidth()] = true;
					grass[(room.right - 1) + (room.top + 1) * getWidth()] = true;
					grass[(room.left + 1) + (room.bottom - 1) * getWidth()] = true;
					grass[(room.right - 1) + (room.bottom - 1) * getWidth()] = true;
				}
			}
		}

		for (int i = getWidth() + 1; i < getLength() - getWidth() - 1; i++) {
			if (map[i] == Terrain.EMPTY && grass[i]) {
				int count = 1;
				for (int n : NEIGHBOURS8) {
					if (grass[i + n]) {
						count++;
					}
				}
				map[i] = (Random.Float() < count / 12f) ? Terrain.HIGH_GRASS
						: Terrain.GRASS;
			}
			if (map[i] == Terrain.EMPTY) {
				map[i] = (Random.Int(40) < 1) ? Terrain.OLD_HIGH_GRASS
							: Terrain.EMPTY;
			}
		}
	}

	protected abstract boolean[] water();

	protected abstract boolean[] grass();

	protected void placeTraps() {
		
		int nTraps = nTraps();
		float[] trapChances = trapChances();
		Class<?>[] trapClasses = trapClasses();

		LinkedList<Integer> validCells = new LinkedList<Integer>();

		for (int i = 0; i < LENGTH; i ++) {
			if (map[i] == Terrain.EMPTY || map[i] == Terrain.WATER || map[i] == Terrain.HIGH_GRASS ){

				if(Dungeon.depth == 1){
					//extra check to prevent annoying inactive traps in hallways on floor 1
					Room r = room(i);
					if (r != null && r.type != Type.TUNNEL){
						validCells.add(i);
					}
				} else
					validCells.add(i);
			}
		}

		//no more than one trap every 5 valid tiles.
		nTraps = Math.min(nTraps, validCells.size()/3);

		Collections.shuffle(validCells);

		for (int i = 0; i < nTraps; i++) {
			
			int trapPos = validCells.removeFirst();

			try {
				Trap trap = ((Trap)trapClasses[Random.chances( trapChances )].newInstance()).hide();
				setTrap( trap, trapPos );
				//some traps will not be hidden
				map[trapPos] = (trap.visible ||  Random.Int(2) == 0 ) ? Terrain.TRAP : Terrain.SECRET_TRAP;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	protected int nTraps() {
		return Random.NormalIntRange( 13, 20+(Dungeon.depth/2) );
	}
	
	protected Class<?>[] trapClasses(){
		return new Class<?>[]{WornTrap.class};
	}

	protected float[] trapChances() {
		return new float[]{1};
	}

	protected int minRoomSize = 8;
	protected int maxRoomSize = 10;

	protected void split(Rect rect) {

		int w = rect.width();
		int h = rect.height();


		if (w > maxRoomSize && h < minRoomSize) {

			int vw = Random.Int(rect.left + 4, rect.right - 3);
			split(new Rect(rect.left, rect.top, vw, rect.bottom));
			split(new Rect(vw, rect.top, rect.right, rect.bottom));

		} else if (h > maxRoomSize && w < minRoomSize) {

			int vh = Random.Int(rect.top + 4, rect.bottom - 3);
			split(new Rect(rect.left, rect.top, rect.right, vh));
			split(new Rect(rect.left, vh, rect.right, rect.bottom));

		} else if ((Math.random() <= (minRoomSize * minRoomSize / rect.square())
				&& w <= maxRoomSize && h <= maxRoomSize && w>5 && h >5 )
				|| w < minRoomSize || h < minRoomSize ) {

			rooms.add((Room) new Room().set(rect));

		} else {

			if (Random.Float() < (float) (w - 2) / (w + h - 4)) {
				int vw = Random.Int(rect.left + 4, rect.right - 3);
				split(new Rect(rect.left, rect.top, vw, rect.bottom));
				split(new Rect(vw, rect.top, rect.right, rect.bottom));
			} else {
				int vh = Random.Int(rect.top + 4, rect.bottom - 3);
				split(new Rect(rect.left, rect.top, rect.right, vh));
				split(new Rect(rect.left, vh, rect.right, rect.bottom));
			}

		}
	}

	protected void paint() {

		for (Room r : rooms) {
			if (r.type != Type.NULL) {
				placeDoors(r);
				r.type.paint(this, r);
			} else {
				if (feeling == Feeling.CHASM && Random.Int(2) == 0) {
					Painter.fill(this, r, Terrain.WALL);
				}
			}
		}

		for (Room r : rooms) {
			paintDoors(r);
		}
	}

	private void placeDoors(Room r) {
		for (Room n : r.connected.keySet()) {
			Room.Door door = r.connected.get(n);
			if (door == null) {

				Rect i = r.intersect(n);
				if (i.width() == 0) {
					door = new Room.Door(i.left,
							Random.Int(i.top + 1, i.bottom));
				} else {
					door = new Room.Door(Random.Int(i.left + 1, i.right), i.top);
				}

				r.connected.put(n, door);
				n.connected.put(r, door);
			}
		}
	}

	protected void paintDoors(Room r) {
		for (Room n : r.connected.keySet()) {

			if (joinRooms(r, n)) {
				continue;
			}

			Room.Door d = r.connected.get(n);
			int door = d.x + d.y * getWidth();

			switch (d.type) {
			case EMPTY:
				map[door] = Terrain.EMPTY;
				break;
			case TUNNEL:
				map[door] = tunnelTile();
				break;
			case REGULAR:
				if (Dungeon.depth <= 1) {
					map[door] = Terrain.DOOR;
				} else {
					boolean secret = (Dungeon.depth < 6 ? Random
							.Int(12 - Dungeon.depth) : Random.Int(6)) == 0;
					map[door] = secret ? Terrain.SECRET_DOOR : Terrain.DOOR;
					if (secret) {
						secretDoors++;
					}
				}
				break;
			case UNLOCKED:
				map[door] = Terrain.DOOR;
				break;
			case HIDDEN:
				map[door] = Terrain.SECRET_DOOR;
				break;
			case BARRICADE:
				map[door] = Random.Int(3) == 0 ? Terrain.BOOKSHELF
						: Terrain.BARRICADE;
				break;
			case LOCKED:
				map[door] = Terrain.LOCKED_DOOR;
				break;
			case ONEWAY:
				map[door] = Terrain.BROKEN_DOOR;
				break;
			}
		}
	}

	protected boolean joinRooms(Room r, Room n) {

		if (r.type != Room.Type.STANDARD || n.type != Room.Type.STANDARD) {
			return false;
		}

		Rect w = r.intersect(n);
		if (w.left == w.right) {

			if (w.bottom - w.top < 3) {
				return false;
			}

			if (w.height() == Math.max(r.height(), n.height())) {
				return false;
			}

			if (r.width() + n.width() > maxRoomSize) {
				return false;
			}

			w.top += 1;
			w.bottom -= 0;

			w.right++;

			Painter.fill(this, w.left, w.top, 1, w.height(), Terrain.EMPTY);

		} else {

			if (w.right - w.left < 3) {
				return false;
			}

			if (w.width() == Math.max(r.width(), n.width())) {
				return false;
			}

			if (r.height() + n.height() > maxRoomSize) {
				return false;
			}

			w.left += 1;
			w.right -= 0;

			w.bottom++;

			Painter.fill(this, w.left, w.top, w.width(), 1, Terrain.EMPTY);
		}

		return true;
	}
	
	protected void setPar(){
		Dungeon.pars[Dungeon.depth] = 800;
	}

	@Override
	public int nMobs() {
		if (Dungeon.depth < 5 && !Statistics.amuletObtained){
		 return 10 + Dungeon.depth + Random.Int(3);
		} else if(!Statistics.amuletObtained) {
		 return 15 + Dungeon.depth % 3 + Random.Int(3);
		} else {
		 return 10 + (5 - Dungeon.depth % 5) + Random.Int(3);
		}
	}

	@Override
	protected void createMobs() {
		int nMobs = nMobs();
		for (int i = 0; i < nMobs; i++) {
			Mob mob = Bestiary.mob(Dungeon.depth);
			do {
				mob.pos = randomRespawnCell();
				mob.originalgen=true;
			} while (mob.pos == -1);
			mobs.add(mob);
			Actor.occupyCell(mob);
		}
	}

	@Override
	public int randomRespawnCell() {
						
		int count = 0;
		int cell = -1;

		while (true) {

			if (++count > 30) {
				return -1;
			}

			Room room = randomRoom(Room.Type.STANDARD, 10);
			if (room == null) {
				continue;
			}

			cell = room.random();
			if (!Dungeon.visible[cell] && Actor.findChar(cell) == null
					&& Level.passable[cell]) {
				return cell;
			}

		 }
		
	}

	@Override
	public int randomDestination() {

		int cell = -1;

		while (true) {

			Room room = Random.element(rooms);
			if (room == null) {
				continue;
			}

			cell = room.random();
			if (Level.passable[cell]) {
				return cell;
			}

		}
	}

	@Override
	protected void createItems() {

		int nItems = 3;
		int bonus = 0;
		for (Buff buff : Dungeon.hero.buffs(LuckyBadge.GreatLucky.class)) {
			bonus += ((LuckyBadge.GreatLucky) buff).level;
		}
		if (Dungeon.hero.heroClass == HeroClass.SOLDIER)
			bonus += 5;
		if (Dungeon.hero.subClass == HeroSubClass.SUPERSTAR) {
			bonus += 3;
		}
		for (Buff buff : Dungeon.hero.buffs(AflyBless.class)) {
			bonus += 3;
		}	
		// just incase someone gets a ridiculous ring, cap this at 80%
		bonus = Math.min(bonus, 10);
		while (Random.Float() < (0.3f + bonus * 0.05f)) {
			nItems++;
		}

		for (int i = 0; i < nItems; i++) {
			Heap.Type type = null;
			switch (Random.Int(20)) {
			case 0:
				type = Heap.Type.SKELETON;
				break;
			case 1:
			case 2:
			case 3:
			case 4:
				type = Heap.Type.CHEST;
				break;
			case 5:
				type = Dungeon.depth > 1 ? Heap.Type.MIMIC : Heap.Type.CHEST;
				break;
			default:
				type = HEAP;
			}
			drop(Generator.random(), randomDropCell()).type = type;
		}
		
		for (int r = 0; r < 10; r++) {
			Heap.Type type = Heap.Type.E_DUST;
			if (Random.Int(5)==0)
			   drop(Generator.random(), randomDropCell()).type = type;
			else drop(new YellowDewdrop(), randomDropCell()).type = type;
		}

		for (int r = 0; r < 3; r++) {
			Heap.Type type = Heap.Type.M_WEB;
			if (Random.Int(3)==0)
			   drop(Generator.random(Random.oneOf(Generator.Category.ARMOR,
					   Generator.Category.MELEEWEAPON,
					   Generator.Category.ARTIFACT,
					   Generator.Category.RING)), randomDropCell()).type = type;
			else drop(new SmallMeat(), randomDropCell()).type = type;
		}		
		
//for (int x = 0; x < 20; x++) {
		if (Random.Int(5)>0){
			Heap.Type type = Heap.Type.LOCKED_CHEST;
			drop(new GoldenKey(Dungeon.depth),randomDropCell()).type = HEAP;
			switch (Random.Int(15)) {
				case 0:
				case 1:
					drop(Generator.random(Generator.Category.GOLD), randomDropCell()).type = type;
					break;
				case 2:	
				case 3:
				case 4:
				case 5:
					drop(Generator.random(Generator.Category.HIGHFOOD), randomDropCell()).type = type;
					break;
				case 6:
				case 7:
				case 8:
					drop(Generator.random(Generator.Category.NORNSTONE), randomDropCell()).type = type;
					break;
				case 9:case 10:case 11: 
					drop(Generator.random(Generator.Category.PILL), randomDropCell()).type = type;
					break;
				case 12:case 13:
					drop(Generator.random(Generator.Category.SUMMONED), randomDropCell()).type = type;
					break;
				case 14:
					drop(Generator.random(Generator.Category.EGGS), randomDropCell()).type = type;
					break;
				
				default:
			}

		} else {
		    Heap.Type type = Heap.Type.G_MIMIC;
			switch (Random.Int(5)) {
				case 0:
                    drop(Generator.random(Generator.Category.HIGHFOOD), randomDropCell()).type = type;
					break;				
                case 1:
					drop(Generator.random(Generator.Category.NORNSTONE), randomDropCell()).type = type;
					break;
			    case 2:	
					drop(Generator.random(Generator.Category.PILL), randomDropCell()).type = type;
					break;
				case 3:	
					drop(Generator.random(Generator.Category.SUMMONED), randomDropCell()).type = type;
					break;
		        case 4:
					drop(Generator.random(Generator.Category.EGGS), randomDropCell()).type = type;
					break;
				
				default:
			}
		}
//}
		for (Item item : itemsToSpawn) {
			int cell = randomDropCell();
			if (item instanceof Scroll) {
				while (map[cell] == Terrain.TRAP
						|| map[cell] == Terrain.SECRET_TRAP
						&& traps.get( cell ) instanceof FireDamageTrap) {
					cell = randomDropCell();
				}
			}
			drop(item, cell).type = HEAP;
		}


	}

	protected Room randomRoom(Room.Type type, int tries) {
		for (int i = 0; i < tries; i++) {
			Room room = Random.element(rooms);
			if (room.type == type) {
				return room;
			}
		}
		return null;
	}

	public Room room(int pos) {
		for (Room room : rooms) {
			if (room.type != Type.NULL && room.inside(pos)) {
				return room;
			}
		}

		return null;
	}

	protected int randomDropCell() {
		while (true) {
			Room room = randomRoom(Room.Type.STANDARD, 1);
			if (room != null) {
				int pos = room.random();
				if (heaps.get(pos) != null) {
					do {
						pos = room.random();
					} while (heaps.get(pos) != null);
				}
				if (passable[pos]) {
					return pos;
				}
			}
		}
	}

	@Override
	public int pitCell() {
		return super.pitCell();
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put("rooms", rooms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		rooms = new HashSet<Room>(
				(Collection<Room>) ((Collection<?>) bundle
						.getCollection("rooms")));

	}

}
