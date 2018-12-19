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
package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Generator.Category;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.Stylus;
import com.hmdzl.spspd.change.items.UpgradeBlobRed;
import com.hmdzl.spspd.change.items.UpgradeBlobViolet;
import com.hmdzl.spspd.change.items.UpgradeBlobYellow;
import com.hmdzl.spspd.change.items.potions.Potion;
import com.hmdzl.spspd.change.items.scrolls.Scroll;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.plants.Plant;
import com.watabou.utils.Random;

public class WaterOfUpgradeEating extends WellWater {

	@Override
	protected Item affectItem(Item item) {

		if (item.isUpgradable()) {
			item = eatUpgradable((Item) item);
		} else if (item instanceof Scroll
				    || item instanceof Potion
				    || item instanceof Stylus) {
			item = eatStandard((Item) item);
		} else {
			item = null;
		}
		
		return item;

	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(Speck.factory(Speck.CHANGE), 0.2f, 0);
	}

	private Item eatUpgradable(Item w) {

		int ups = w.level;
		
		Item n = null;

		if (Random.Float()<(ups/10)){
			
			n = new UpgradeBlobViolet();
			
		} else if (Random.Float()<(ups/5)) {
			
			n =  new UpgradeBlobRed();
			
        } else if (Random.Float()<(ups/3)) {
			
			n =  new UpgradeBlobYellow();
		
		} else {
			
			n = (Plant.Seed) Generator.random(Category.SEEDRICH);
		}
		
		return n;
	}
	
	private Item eatStandard(Item w) {

		Item n = null;
        
		if (Random.Float()<0.1f){
			n = new UpgradeBlobYellow();
		} else {
			n = (Plant.Seed) Generator.random(Category.SEEDRICH);
		}
		
		return n;
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
