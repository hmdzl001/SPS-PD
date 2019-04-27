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
package com.hmdzl.spspd.change.items.bags;

import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.ChallengeBook;
import com.hmdzl.spspd.change.items.DolyaSlate;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.keys.Key;
import com.hmdzl.spspd.change.items.AncientCoin;
import com.hmdzl.spspd.change.items.ConchShell;
import com.hmdzl.spspd.change.items.Bone;
import com.hmdzl.spspd.change.items.TriForce;
import com.hmdzl.spspd.change.items.TreasureMap;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.items.rings.Ring;

public class KeyRing extends Bag {

	{
		//name = "key ring";
		image = ItemSpriteSheet.KEYRING;

		size = 22;
	}

	@Override
	public boolean grab(Item item) {
		if (item instanceof Key
			||  item instanceof TenguKey
			||  item instanceof AncientCoin
			||  item instanceof ConchShell
			||  item instanceof Bone
			||  item instanceof TriForce
			||  item instanceof DolyaSlate
		    ||  item instanceof Ring 
			||  item instanceof TreasureMap
			||  item instanceof ChallengeBook
				){
			return true;
			} else {
			return false;
			}
	}

	@Override
	public int price() {
		return 50 * quantity;
	}

	@Override
	public boolean doPickUp( Hero hero ) {

		return hero.belongings.getItem( KeyRing.class ) == null && super.doPickUp( hero ) ;

	}

}

