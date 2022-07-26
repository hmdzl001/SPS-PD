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

import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.levels.painters.BlacksmithPainter;
import com.hmdzl.spspd.levels.painters.BossExitPainter;
import com.hmdzl.spspd.levels.painters.CookingPainter;
import com.hmdzl.spspd.levels.painters.CryptPainter;
import com.hmdzl.spspd.levels.painters.EntrancePainter;
import com.hmdzl.spspd.levels.painters.ExitPainter;
import com.hmdzl.spspd.levels.painters.GardenPainter;
import com.hmdzl.spspd.levels.painters.JunglePainter;
import com.hmdzl.spspd.levels.painters.LibraryPainter;
import com.hmdzl.spspd.levels.painters.MagicKeeperPainter;
import com.hmdzl.spspd.levels.painters.MaterialPainter;
import com.hmdzl.spspd.levels.painters.Painter;
import com.hmdzl.spspd.levels.painters.PassagePainter;
import com.hmdzl.spspd.levels.painters.PitPainter;
import com.hmdzl.spspd.levels.painters.PoolPainter;
import com.hmdzl.spspd.levels.painters.RatKingPainter;
import com.hmdzl.spspd.levels.painters.ShopPainter;
import com.hmdzl.spspd.levels.painters.StandardPainter;
import com.hmdzl.spspd.levels.painters.StatuePainter;
import com.hmdzl.spspd.levels.painters.StoragePainter;
import com.hmdzl.spspd.levels.painters.TenguBoxPainter;
import com.hmdzl.spspd.levels.painters.TrapsPainter;
import com.hmdzl.spspd.levels.painters.TunnelPainter;
import com.hmdzl.spspd.levels.painters.VaultPainter;
import com.hmdzl.spspd.levels.painters.WeakFloorPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.BarricadedPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.GlassRoomPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.HidenShopPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.MagicWellPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.MemoryPainter;
import com.hmdzl.spspd.levels.painters.hidenroom.WishPoolPainter;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Graph;
import com.watabou.utils.Point;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class Room extends Rect implements Graph.Node, Bundlable {

	public HashSet<Room> neigbours = new HashSet<Room>();
	public HashMap<Room, Door> connected = new HashMap<Room, Door>();

	public int distance;
	public int price = 1;

	public enum Type {
		NULL(null), 
		STANDARD(StandardPainter.class), 
		ENTRANCE(EntrancePainter.class), 
		EXIT(ExitPainter.class), 
		BOSS_EXIT(BossExitPainter.class),
		TUNNEL(TunnelPainter.class), 
		PASSAGE(PassagePainter.class), 
		SHOP(ShopPainter.class), 
		BLACKSMITH(BlacksmithPainter.class), 
		JUNGLE(JunglePainter.class),
		MATERIAL(MaterialPainter.class),
		LIBRARY(LibraryPainter.class), 
		COOKING(CookingPainter.class),
		VAULT(VaultPainter.class), 
		TRAPS(TrapsPainter.class), 
		STORAGE(StoragePainter.class),
		BARRICADED	(BarricadedPainter.class ),
		MAGIC_WELL(MagicWellPainter.class), 
		MAGIC_KEEPER(MagicKeeperPainter.class),
		GARDEN(GardenPainter.class), 
		CRYPT(CryptPainter.class), 
		STATUE(StatuePainter.class), 
		POOL(PoolPainter.class), 
		RAT_KING(RatKingPainter.class), 
		WEAK_FLOOR(WeakFloorPainter.class), 
		PIT(PitPainter.class), 
		RAT_KING2(TenguBoxPainter.class),
		MEMORY(MemoryPainter.class),
		HIDE_SHOP(HidenShopPainter.class),
		WISH_POOL(WishPoolPainter.class),
		GLASSROOM(GlassRoomPainter.class);


		private Method paint;

		Type(Class<? extends Painter> painter) {
			try {
				paint = painter.getMethod("paint", Level.class, Room.class);
			} catch (Exception e) {
				paint = null;
			}
		}

		public void paint(Level level, Room room) {
			try {
				paint.invoke(null, level, room);
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
			}
		}
	}

    public static final ArrayList<Type> SPECIALS = new ArrayList<Type>(
			Arrays.asList(Type.WEAK_FLOOR, Type.CRYPT, Type.POOL, Type.GARDEN, Type.LIBRARY, Type.MATERIAL,
					Type.JUNGLE, Type.TRAPS, Type.STORAGE, Type.STATUE, Type.COOKING, Type.VAULT));

	public static final ArrayList<Type> HIDE = new ArrayList<Type>(
			Arrays.asList( Type.MAGIC_WELL, Type.MEMORY, Type.BARRICADED,Type.HIDE_SHOP,
					Type.MAGIC_WELL, Type.HIDE_SHOP, Type.WISH_POOL,Type.GLASSROOM,
					Type.MEMORY, Type.BARRICADED, Type.WISH_POOL,Type.GLASSROOM));

	//public static final ArrayList<Type> HIDE = new ArrayList<Type>(
	//		Arrays.asList( Type.WISH_POOL));
	
	public static final ArrayList<Type> SPECIALSFORT = new ArrayList<Type>(
			Arrays.asList(Type.GARDEN, Type.GARDEN, Type.GARDEN, Type.GARDEN,
					      Type.GARDEN, Type.GARDEN, Type.GARDEN, Type.GARDEN,
					      Type.GARDEN, Type.GARDEN));
	
	public static final ArrayList<Type> SPECIALSTRANSCEND = new ArrayList<Type>(
			Arrays.asList(Type.MAGIC_KEEPER, Type.MAGIC_KEEPER, Type.MAGIC_KEEPER, Type.MAGIC_KEEPER,
					      Type.MAGIC_KEEPER, Type.MAGIC_KEEPER, Type.MAGIC_KEEPER, Type.MAGIC_KEEPER,
					       Type.MAGIC_KEEPER,  Type.MAGIC_KEEPER));
	
	public Type type = Type.NULL;

	public int random() {
		return random(0);
	}

	public int random(int m) {
		int x = Random.Int(left + 1 + m, right - m);
		int y = Random.Int(top + 1 + m, bottom - m);
		return x + y * Level.getWidth();
	}

	public void addNeigbour(Room other) {

		Rect i = intersect(other);
		if ((i.width() == 0 && i.height() >= 2)
				|| (i.height() == 0 && i.width() >= 2)) {
			neigbours.add(other);
			other.neigbours.add(this);
		}

	}

	public void connect(Room room) {
		if (!connected.containsKey(room)) {
			connected.put(room, null);
			room.connected.put(this, null);
		}
	}

	public Door entrance() {
		return connected.values().iterator().next();
	}

	public boolean inside(int p) {
		int x = p % Level.getWidth();
		int y = p / Level.getWidth();
		return x > left && y > top && x < right && y < bottom;
	}

	public Point center() {
		return new Point((left + right) / 2
				+ (((right - left) & 1) == 1 ? Random.Int(2) : 0),
				(top + bottom) / 2
						+ (((bottom - top) & 1) == 1 ? Random.Int(2) : 0));
	}

	public HashSet<Integer> getCells(){
		HashSet<Point> points = getPoints();
		HashSet<Integer> cells = new HashSet<>();
		for( Point point : points)
			cells.add(point.x + point.y*Level.WIDTH);
		return cells;
	}	

	// **** Graph.Node interface ****

	@Override
	public int distance() {
		return distance;
	}

	@Override
	public void distance(int value) {
		distance = value;
	}

	@Override
	public int price() {
		return price;
	}

	@Override
	public void price(int value) {
		price = value;
	}

	@Override
	public Collection<Room> edges() {
		return neigbours;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put("left", left);
		bundle.put("top", top);
		bundle.put("right", right);
		bundle.put("bottom", bottom);
		bundle.put("type", type.toString());
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		left = bundle.getInt("left");
		top = bundle.getInt("top");
		right = bundle.getInt("right");
		bottom = bundle.getInt("bottom");
		type = Type.valueOf(bundle.getString("type"));
	}

	public static void shuffleTypes() {
		int size = SPECIALS.size();
		for (int i = 0; i < size - 1; i++) {
			int j = Random.Int(i, size);
			if (j != i) {
				Type t = SPECIALS.get(i);
				SPECIALS.set(i, SPECIALS.get(j));
				SPECIALS.set(j, t);
			}
		}
	}

	public static void useType(Type type) {
		if (SPECIALS.remove(type)) {
			SPECIALS.add(type);
		}
	}

	private static final String ROOMS = "rooms";

	public static void restoreRoomsFromBundle(Bundle bundle) {
		if (bundle.contains(ROOMS)) {
			SPECIALS.clear();
			for (String type : bundle.getStringArray(ROOMS)) {
				SPECIALS.add(Type.valueOf(type));
			}
		} else {
			shuffleTypes();
		}
	}

	public static void storeRoomsInBundle(Bundle bundle) {
		String[] array = new String[SPECIALS.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = SPECIALS.get(i).toString();
		}
		bundle.put(ROOMS, array);
	}

	public static class Door extends Point {

		public enum Type {
			EMPTY, TUNNEL, REGULAR, UNLOCKED, HIDDEN, BARRICADE, LOCKED, ONEWAY
		}

		public Type type = Type.EMPTY;

		public Door(int x, int y) {
			super(x, y);
		}

		public void set(Type type) {
			if (type.compareTo(this.type) > 0) {
				this.type = type;
			}
		}
	}
}