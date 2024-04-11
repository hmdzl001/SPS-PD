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
package com.hmdzl.spspd.items.food.fruit;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Notice;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Blueberry extends Fruit {

	{
		//name = "dungeon blue berry";
		image = ItemSpriteSheet.BLUEBERRY;
		energy = 50;
		hornValue = 1;
		 
	}

	public void doEat() {

			if (Random.Float()<0.1f) {
			
				int length = Floor.getLength();
				//int[] map = Dungeon.level.map;
				boolean[] mapped = Dungeon.depth.mapped;
				boolean[] discoverable = Floor.discoverable;
				//boolean noticed = false;
				for (int i = 0; i < length; i++) {
					//int terr = map[i];
					if (discoverable[i]) {
						mapped[i] = true;
					}
				}
				Dungeon.observe();
				Buff.affect(curUser, Notice.class, 50f);
				
			} else {

				Buff.affect(curUser, Notice.class, 50f);

			}

	}	
	
	public static void discover(int cell) {
		CellEmitter.get(cell).start(Speck.factory(Speck.DISCOVER), 0.1f, 4);
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}
	
	public Blueberry() {
		this(1);
	}

	public Blueberry(int value) {
		this.quantity = value;
	}
	
}
