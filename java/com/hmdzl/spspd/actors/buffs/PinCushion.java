package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.MissileWeapon;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by debenhame on 06/02/2015.
 */
public class PinCushion extends Buff {

	private ArrayList<MissileWeapon> items = new ArrayList<MissileWeapon>();

	public void stick(MissileWeapon item) {
		items.add(item);
	}

	@Override
	public void detach() {
		for (Item item : items)
			Dungeon.depth.drop(item, target.pos).sprite.drop();
		super.detach();
	}

	private static final String ITEMS = "items";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(ITEMS, items);
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		items = new ArrayList<MissileWeapon>(
				(Collection<MissileWeapon>) ((Collection<?>) bundle
						.getCollection(ITEMS)));
		super.restoreFromBundle(bundle);
	}

}
