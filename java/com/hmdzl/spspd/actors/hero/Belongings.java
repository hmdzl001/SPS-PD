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
package com.hmdzl.spspd.actors.hero;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.KindofMisc;
import com.hmdzl.spspd.items.bags.Bag;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.items.keys.Key;
import com.hmdzl.spspd.items.scrolls.ScrollOfRemoveCurse;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.guns.GunWeapon;
import com.hmdzl.spspd.items.weapon.missiles.ShootGun;
import com.hmdzl.spspd.items.weapon.rockcode.RockCode;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Iterator;

public class Belongings implements Iterable<Item> {

	public static final int BACKPACK_SIZE = 34;

	private Hero owner;

	public Bag backpack;

	public KindOfWeapon weapon = null;
	public KindOfArmor armor = null;
	public KindofMisc misc1 = null;
	public KindofMisc misc2 = null;
	public KindofMisc misc3 = null;

	public Belongings(Hero owner) {
		this.owner = owner;

		backpack = new Bag() {
			{
				name = Messages.get(Bag.class, "name");
				size = BACKPACK_SIZE;
			}
		};
		backpack.owner = owner;
	}

	private static final String WEAPON = "weapon";
	private static final String ARMOR = "armor";
	private static final String MISC1 = "misc1";
	private static final String MISC2 = "misc2";
	private static final String MISC3 = "misc3";

	public void storeInBundle(Bundle bundle) {

		backpack.storeInBundle(bundle);

		bundle.put(WEAPON, weapon);
		bundle.put(ARMOR, armor);
		bundle.put(MISC1, misc1);
		bundle.put(MISC2, misc2);
		bundle.put(MISC3, misc3);
	}

	public void restoreFromBundle(Bundle bundle) {

		backpack.clear();
		backpack.restoreFromBundle(bundle);

		weapon = (KindOfWeapon) bundle.get(WEAPON);
		if (weapon != null) {
			weapon.activate(owner);
		}

	    armor = (KindOfArmor) bundle.get(ARMOR);
		if (armor != null) {
			armor.activate(owner);
		}
		//armor = (Armor) bundle.get(ARMOR);

		misc1 = (KindofMisc) bundle.get(MISC1);
		if (misc1 != null) {
			misc1.activate(owner);
		}

		misc2 = (KindofMisc) bundle.get(MISC2);
		if (misc2 != null) {
			misc2.activate(owner);
		}
		
		misc3 = (KindofMisc) bundle.get(MISC3);
		if (misc3 != null) {
			misc3.activate(owner);
		}		
		
	}

	@SuppressWarnings("unchecked")
	public <T extends Item> T getItem(Class<T> itemClass) {

		for (Item item : this) {
			if (itemClass.isInstance(item)) {
				return (T) item;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Key> T getKey(Class<T> kind, int depth) {

		for (Item item : backpack) {
			if (item.getClass() == kind && ((Key) item).depth == depth) {
				return (T) item;
			}
		}

		return null;
	}

	public void countIronKeys() {

		IronKey.curDepthQuantity = 0;

		for (Item item : backpack) {
			if (item instanceof IronKey
					&& ((IronKey) item).depth == Dungeon.depth) {
				IronKey.curDepthQuantity += item.quantity();
			}
		}
	}

	public void identify() {
		for (Item item : this) {
			item.identify();
		}
	}

	public void observe() {
		if (weapon != null) {
			weapon.identify();
			Badges.validateItemLevelAquired(weapon);
		}
		if (armor != null) {
			armor.identify();
			Badges.validateItemLevelAquired(armor);
		}
		if (misc1 != null) {
			misc1.identify();
			Badges.validateItemLevelAquired(misc1);
		}
		if (misc2 != null) {
			misc2.identify();
			Badges.validateItemLevelAquired(misc2);
		}
		if (misc3 != null) {
			misc3.identify();
			Badges.validateItemLevelAquired(misc3);
		}			
		for (Item item : backpack) {
			item.cursedKnown = true;
		}
	}

	public void observeS() {
		for (Item item : backpack) {
			item.cursedKnown = true;
		}
	}	
	
	public void uncurseEquipped() {
		ScrollOfRemoveCurse.uncurse(owner, armor, weapon, misc1, misc2, misc3);
	}

	public Item randomUnequipped() {
		return Random.element(backpack.items);
	}

	public void resurrect(int depth) {

		for (Item item : backpack.items.toArray( new Item[0])) {
			if (item instanceof Key) {
				if (((Key)item).depth == depth) {
					item.detachAll( backpack );
				}
			} else if (item.unique) {
				item.detachAll(backpack);
				//you keep the bag itself, not its contents.
				if (item instanceof Bag){
					((Bag)item).clear();
				}
				item.collect();
			} else if (!item.isEquipped( owner )) {
				item.detachAll( backpack );
			}
		}	
	
		if (weapon != null) {
			weapon.cursed = false;
			weapon.activate(owner);
		}

		if (armor != null) {
			armor.cursed = false;
			armor.activate(owner);
		}

		if (misc1 != null) {
			misc1.cursed = false;
			misc1.activate(owner);
		}
		if (misc2 != null) {
			misc2.cursed = false;
			misc2.activate(owner);
		}
		if (misc3 != null) {
			misc3.cursed = false;
			misc3.activate(owner);
		}
	}

	public int charge(boolean full) {

		int count = 0;

		for (Item item : this) {
			if (item instanceof Wand) {
				Wand wand = (Wand) item;
				if (wand.curCharges < wand.maxCharges) {
					wand.curCharges = full ? wand.maxCharges
							: wand.curCharges + 1;
					count++;

					wand.updateQuickslot();
				}
			}
		}

		return count;
	}
	
	public int relord() {

		int count = 0;

		for (Item item : this) {
			if (item instanceof GunWeapon) {
				GunWeapon gunweapon = (GunWeapon) item;
				gunweapon.charge =  gunweapon.charge  + 1;
				count++;
				gunweapon.updateQuickslot();
				}
			if (item instanceof ShootGun) {
				ShootGun shootgun = (ShootGun) item;
				shootgun.charge =  Math.min(3,shootgun.charge  + 1);
				count++;
				shootgun.updateQuickslot();
			}

			}
		return count;
	}

	public int recode() {
		int count = 0;
		for (Item item : this) {
			if (item instanceof RockCode) {
				RockCode rockcode = (RockCode) item;
				if (Random.Int(4) == 0 ){
				   rockcode.curEnergy = Math.min(rockcode.curEnergy + 1,rockcode.maxEnergy);
				}
				count++;
				rockcode.updateQuickslot();
			}
		}
		return count;
	}

	public int discharge() {

		int count = 0;

		for (Item item : this) {
			if (item instanceof Wand) {
				Wand wand = (Wand) item;
				if (wand.curCharges > 0) {
					wand.curCharges--;
					count++;

					wand.updateQuickslot();
				}
			}
		}

		return count;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}

	private class ItemIterator implements Iterator<Item> {

		private int index = 0;

		private Iterator<Item> backpackIterator = backpack.iterator();

		private Item[] equipped = { weapon, armor, misc1, misc2 ,misc3 };
		private int backpackIndex = equipped.length;

		@Override
		public boolean hasNext() {

			for (int i = index; i < backpackIndex; i++) {
				if (equipped[i] != null) {
					return true;
				}
			}

			return backpackIterator.hasNext();
		}

		@Override
		public Item next() {

			while (index < backpackIndex) {
				Item item = equipped[index++];
				if (item != null) {
					return item;
				}
			}

			return backpackIterator.next();
		}

		@Override
		public void remove() {
			switch (index) {
			case 0:
				equipped[0] = weapon = null;
				break;
			case 1:
				equipped[1] = armor = null;
				break;
			case 2:
				equipped[2] = misc1 = null;
				break;
			case 3:
				equipped[3] = misc2 = null;
				break;
			case 4:
				equipped[4] = misc3 = null;
				break;				
			default:
				backpackIterator.remove();
			}
		}
	}
}
