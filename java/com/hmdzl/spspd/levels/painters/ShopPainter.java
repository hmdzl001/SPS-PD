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
package com.hmdzl.spspd.levels.painters;

import java.util.ArrayList;
import java.util.Collections;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Belongings;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.ImpShopkeeper;
import com.hmdzl.spspd.actors.mobs.npcs.Shopkeeper;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.armor.normalarmor.PlateArmor;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.summon.FairyCard;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.Stylus;
import com.hmdzl.spspd.items.Torch;
import com.hmdzl.spspd.items.Weightstone;
import com.hmdzl.spspd.items.challengelists.CourageChallenge;
import com.hmdzl.spspd.items.challengelists.PowerChallenge;
import com.hmdzl.spspd.items.challengelists.WisdomChallenge;
import com.hmdzl.spspd.items.food.staplefood.Pasty;
import com.hmdzl.spspd.items.journalpages.Town;
import com.hmdzl.spspd.items.journalpages.SafeSpotPage;
import com.hmdzl.spspd.items.armor.normalarmor.DiscArmor;
import com.hmdzl.spspd.items.armor.normalarmor.MailArmor;
import com.hmdzl.spspd.items.armor.normalarmor.ScaleArmor;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.bags.PotionBandolier;
import com.hmdzl.spspd.items.bags.ScrollHolder;
import com.hmdzl.spspd.items.bags.SeedPouch;
import com.hmdzl.spspd.items.bags.WandHolster;
import com.hmdzl.spspd.items.potions.Potion;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.scrolls.Scroll;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.summon.Mobile;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.guns.GunA;
import com.hmdzl.spspd.items.weapon.guns.GunB;
import com.hmdzl.spspd.items.weapon.guns.GunC;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.guns.GunE;
import com.hmdzl.spspd.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.items.weapon.melee.special.DragonBoat;
import com.hmdzl.spspd.items.weapon.melee.special.KeyWeapon;
import com.hmdzl.spspd.items.weapon.missiles.PocketBall;
import com.hmdzl.spspd.levels.LastShopLevel;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Plant;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class ShopPainter extends Painter {

	private static int pasWidth;
	private static int pasHeight;

	private static ArrayList<Item> itemsToSpawn;

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		pasWidth = room.width() - 2;
		pasHeight = room.height() - 2;
		int per = pasWidth * 2 + pasHeight * 2;

		if (itemsToSpawn == null)
			generateItems();

		int pos = xy2p(room, room.entrance()) + (per - itemsToSpawn.size()) / 2;
		for (Item item : itemsToSpawn) {

			Point xy = p2xy(room, (pos + per) % per);
			int cell = xy.x + xy.y * Level.getWidth();

			if (level.heaps.get(cell) != null) {
				do {
					cell = room.random();
				} while (level.heaps.get(cell) != null);
			}

			level.drop(item, cell).type = Heap.Type.FOR_SALE;

			pos++;
		}

		placeShopkeeper(level, room);

		for (Room.Door door : room.connected.values()) {
			door.set(Room.Door.Type.REGULAR);
		}

		itemsToSpawn = null;
	}

	private static void generateItems() {

		itemsToSpawn = new ArrayList<Item>();

		switch (Dungeon.depth) {
		case 1:
			itemsToSpawn.add(new GunA().identify());
			//itemsToSpawn.add(new MiniMoai().identify());
			itemsToSpawn.add(new DragonBoat());
		    itemsToSpawn.add(new DolyaSlate().identify());
			itemsToSpawn.add(new Pasty());
			//itemsToSpawn.add(new UnstableSpellbook());
			Dungeon.limitedDrops.journal.drop();
            break;	
			
		case 6:
			itemsToSpawn.add(new SafeSpotPage().identify());
			Dungeon.limitedDrops.safespotpage.drop();
			itemsToSpawn.add(new GunB().identify());
			itemsToSpawn.add(new DiscArmor().identify());
			break;

		case 11:
			itemsToSpawn.add(new Town().identify());
			Dungeon.limitedDrops.town.drop();
			itemsToSpawn.add(new GunC().identify());
			itemsToSpawn.add(new MailArmor().identify());
			break;

		case 16:
			itemsToSpawn.add(new GunD().identify());
			itemsToSpawn.add(new ScaleArmor().identify());
			break;

		case 21:
			//itemsToSpawn.add(Random.Int(2) == 0 ? new Glaive().identify()
			//	: new WarHammer().identify());
			//itemsToSpawn.add(Random.Int(2) == 0 ? new Bola().quantity(Random
			//		.NormalIntRange(4, 7)) : new Tamahawk().quantity(Random
			//		.NormalIntRange(4, 7)));
			//itemsToSpawn.add(new PlateArmor().identify());
			//itemsToSpawn.add(new Torch());
			itemsToSpawn.add(new GunE().identify());
			itemsToSpawn.add(new PlateArmor().identify());
			itemsToSpawn.add(new CourageChallenge());
			itemsToSpawn.add(new PowerChallenge());
			itemsToSpawn.add(new WisdomChallenge());
			break;
		}

		ChooseBag(Dungeon.hero.belongings);

		itemsToSpawn.add(new PotionOfHealing());
		for (int i = 0; i < 2; i++)
		itemsToSpawn.add(Generator.random(Generator.Category.POTION));

		//itemsToSpawn.add(new ScrollOfIdentify());
		//itemsToSpawn.add(new ScrollOfRemoveCurse());
		itemsToSpawn.add(new ScrollOfMagicMapping());
		for (int i = 0; i < 2; i++)
		itemsToSpawn.add(Generator.random(Generator.Category.SCROLL));
		itemsToSpawn.add(new PocketBall());
		itemsToSpawn.add(Generator.random(Generator.Category.BOMBS));
		//for (int i = 0; i < 2; i++)
		itemsToSpawn.add(Random.Int(2) == 0 ?
				Generator.random(Generator.Category.POTION):
		        Generator.random(Generator.Category.SCROLL));
		for (int i = 0; i < 2; i++)
		itemsToSpawn.add(Generator.random(Generator.Category.RANGEWEAPON));
		itemsToSpawn.add(Generator.random(Generator.Category.MELEEWEAPON));
		//itemsToSpawn.add(new DungeonBomb().random());
		switch (Random.Int(8)) {
		case 1:
			itemsToSpawn.add(new ActiveMrDestructo());
			break;
		case 2:
			itemsToSpawn.add(new FairyCard());
			break;
		case 3:
			itemsToSpawn.add(new Mobile());
			break;
		case 4:
			itemsToSpawn.add(new Honeypot());
			break;
		case 5:
		case 6:
		case 7:
			itemsToSpawn.add(new Torch());
			break;
		}

		//if (Dungeon.depth == 6) {
			itemsToSpawn.add(new Ankh());
			itemsToSpawn.add(new Weightstone());
		//} else {
			//itemsToSpawn.add(Random.Int(2) == 0 ? new Ankh()
					//: new Weightstone());
		//}

		TimekeepersHourglass hourglass = Dungeon.hero.belongings
				.getItem(TimekeepersHourglass.class);
		if (hourglass != null) {
			int bags = 0;
			// creates the given float percent of the remaining bags to be
			// dropped.
			// this way players who get the hourglass late can still max it,
			// usually.
			switch (Dungeon.depth) {
			case 6:
				bags = (int) Math.ceil((5 - hourglass.sandBags) * 0.20f);
				break;
			case 11:
				bags = (int) Math.ceil((5 - hourglass.sandBags) * 0.25f);
				break;
			case 16:
				bags = (int) Math.ceil((5 - hourglass.sandBags) * 0.50f);
				break;
			case 21:
				bags = (int) Math.ceil((5 - hourglass.sandBags) * 0.80f);
				break;
			}

			for (int i = 1; i <= bags; i++) {
				itemsToSpawn.add(new TimekeepersHourglass.sandBag());
				hourglass.sandBags++;
			}
		}

		Item rare;
		switch (Random.Int(4)) {
		case 0:
			rare = Generator.random(Generator.Category.WAND);
			rare.level = 0;
			break;
		case 1:
			rare = Generator.random(Generator.Category.RING);
			rare.level = 1;
			break;
		case 2:
			rare = Generator.random(Generator.Category.ARTIFACT).identify();
			break;
		default:
			rare = new Stylus();
		}
		rare.cursed = rare.cursedKnown = false;
		itemsToSpawn.add(rare);

		// this is a hard limit, level gen allows for at most an 8x5 room, can't
		// fit more than 39 items + 1 shopkeeper.
		if (itemsToSpawn.size() > 39)
			throw new RuntimeException(
					"Shop attempted to carry more than 39 items!");

		Collections.shuffle(itemsToSpawn);
	}

	private static void ChooseBag(Belongings pack) {
		// FIXME: this whole method is pretty messy to accomplish a fairly
		// simple logic goal. Should be a better way.

		// there is a bias towards giving certain bags earlier, seen here
		int seeds = 10, scrolls = 1, potions = 1, wands = 0;

		// we specifically only want to look at items in the main bag, none of
		// the sub-bags.
		for (Item item : pack.backpack.items) {
			if (item instanceof Plant.Seed)
				seeds++;
			else if (item instanceof Scroll)
				scrolls++;
			else if (item instanceof Potion)
				potions++;
			else if (item instanceof Wand)
				wands++;
		}
		// kill our counts for bags that have already been dropped.
		if (Dungeon.limitedDrops.seedBag.dropped())
			seeds = 0;
		if (Dungeon.limitedDrops.scrollBag.dropped())
			scrolls = 0;
		if (Dungeon.limitedDrops.potionBag.dropped())
			potions = 0;
		if (Dungeon.limitedDrops.wandBag.dropped())
			wands = 0;

		// then pick whichever valid bag has the most items available to put
		// into it.
		if (seeds >= scrolls && seeds >= potions && seeds >= wands
				&& !Dungeon.limitedDrops.seedBag.dropped()) {
			Dungeon.limitedDrops.seedBag.drop();
			itemsToSpawn.add(new SeedPouch());
		} else if (scrolls >= potions && scrolls >= wands
				&& !Dungeon.limitedDrops.scrollBag.dropped()) {
			Dungeon.limitedDrops.scrollBag.drop();
			itemsToSpawn.add(new ScrollHolder());
		} else if (potions >= wands
				&& !Dungeon.limitedDrops.potionBag.dropped()) {
			Dungeon.limitedDrops.potionBag.drop();
			itemsToSpawn.add(new PotionBandolier());
		} else if (!Dungeon.limitedDrops.wandBag.dropped()) {
			Dungeon.limitedDrops.wandBag.drop();
			itemsToSpawn.add(new WandHolster());
		}
	}

	public static int spaceNeeded() {
		if (itemsToSpawn == null)
			generateItems();

		// plus one for the shopkeeper
		return itemsToSpawn.size() + 1;
	}

	private static void placeShopkeeper(Level level, Room room) {

		int pos;
		do {
			pos = room.random();
		} while (level.heaps.get(pos) != null);

		Mob shopkeeper = level instanceof LastShopLevel ? new ImpShopkeeper()
				: new Shopkeeper();
		shopkeeper.pos = pos;
		level.mobs.add(shopkeeper);

		if (level instanceof LastShopLevel) {
			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				int p = shopkeeper.pos + Level.NEIGHBOURS9[i];
				if (level.map[p] == Terrain.EMPTY_SP) {
					level.map[p] = Terrain.WATER;
				}
			}
		}
	}

	private static int xy2p(Room room, Point xy) {
		if (xy.y == room.top) {

			return (xy.x - room.left - 1);

		} else if (xy.x == room.right) {

			return (xy.y - room.top - 1) + pasWidth;

		} else if (xy.y == room.bottom) {

			return (room.right - xy.x - 1) + pasWidth + pasHeight;

		} else {

			if (xy.y == room.top + 1) {
				return 0;
			} else {
				return (room.bottom - xy.y - 1) + pasWidth * 2 + pasHeight;
			}

		}
	}

	private static Point p2xy(Room room, int p) {
		if (p < pasWidth) {

			return new Point(room.left + 1 + p, room.top + 1);

		} else if (p < pasWidth + pasHeight) {

			return new Point(room.right - 1, room.top + 1 + (p - pasWidth));

		} else if (p < pasWidth * 2 + pasHeight) {

			return new Point(room.right - 1 - (p - (pasWidth + pasHeight)),
					room.bottom - 1);

		} else {

			return new Point(room.left + 1, room.bottom - 1
					- (p - (pasWidth * 2 + pasHeight)));

		}
	}
}
