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

import com.hmdzl.spspd.change.items.CavesKey;
import com.hmdzl.spspd.change.items.CityKey;
import com.hmdzl.spspd.change.items.HallsKey;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.PrisonKey;
import com.hmdzl.spspd.change.items.SewersKey;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.keys.Key;
import com.hmdzl.spspd.change.items.CourageTrial;
import com.hmdzl.spspd.change.items.PowerTrial;
import com.hmdzl.spspd.change.items.WisdomTrial;
import com.hmdzl.spspd.change.items.AncientCoin;
import com.hmdzl.spspd.change.items.ConchShell;
import com.hmdzl.spspd.change.items.Bone;
import com.hmdzl.spspd.change.items.TriForce;
import com.hmdzl.spspd.change.items.TriforceOfCourage;
import com.hmdzl.spspd.change.items.TriforceOfPower;
import com.hmdzl.spspd.change.items.TriforceOfWisdom;
import com.hmdzl.spspd.change.items.OtilukesJournal;
import com.hmdzl.spspd.change.items.TreasureMap;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.items.AdamantRing;
import com.hmdzl.spspd.change.items.artifacts.RingOfDisintegration;
import com.hmdzl.spspd.change.items.rings.Ring;
import com.hmdzl.spspd.change.messages.Messages;

public class KeyRing extends Bag {

	{
		//name = "key ring";
		image = ItemSpriteSheet.KEYRING;

		size = 22;
	}

	@Override
	public boolean grab(Item item) {
		if (item instanceof Key 
			||  item instanceof CavesKey
			||  item instanceof CityKey
			||  item instanceof TenguKey
			||  item instanceof SewersKey
			||  item instanceof HallsKey
			||  item instanceof PrisonKey
			||  item instanceof CourageTrial
			||  item instanceof PowerTrial
			||  item instanceof WisdomTrial
			||  item instanceof AncientCoin
			||  item instanceof ConchShell
			||  item instanceof Bone
			||  item instanceof TriForce
			||  item instanceof OtilukesJournal
		    ||  item instanceof Ring 
			||  item instanceof TreasureMap
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

}

