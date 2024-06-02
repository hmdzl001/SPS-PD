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
package com.hmdzl.spspd.items.bags;

import com.hmdzl.spspd.items.AncientCoin;
import com.hmdzl.spspd.items.Bone;
import com.hmdzl.spspd.items.ChallengeBook;
import com.hmdzl.spspd.items.ConchShell;
import com.hmdzl.spspd.items.DolyaSlate;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PotKey;
import com.hmdzl.spspd.items.TenguKey;
import com.hmdzl.spspd.items.TreasureMap;
import com.hmdzl.spspd.items.TriForce;
import com.hmdzl.spspd.items.keys.Key;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class KeyRing extends Bag {

	{
		//name = "key ring";
		image = ItemSpriteSheet.KEYRING;

		size = 34;
	}

	@Override
	public boolean grab(Item item) {
        return item instanceof Key
                || item instanceof TenguKey
                || item instanceof PotKey
                || item instanceof AncientCoin
                || item instanceof ConchShell
                || item instanceof Bone
                || item instanceof TriForce
                || item instanceof DolyaSlate
                || item instanceof Ring
                || item instanceof TreasureMap
                || item instanceof ChallengeBook;
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	//@Override
	//public boolean doPickUp( Hero hero ) {

	//	return hero.belongings.getItem( KeyRing.class ) == null && super.doPickUp( hero ) ;

	//}

}

