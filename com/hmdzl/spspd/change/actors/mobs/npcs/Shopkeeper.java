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
package com.hmdzl.spspd.change.actors.mobs.npcs;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ShopkeeperSprite;
import com.hmdzl.spspd.change.windows.WndBag;
import com.hmdzl.spspd.change.windows.WndTradeItem;
import com.hmdzl.spspd.change.messages.Messages;

public class Shopkeeper extends NPC {

	{
		//name = "shopkeeper";
		spriteClass = ShopkeeperSprite.class;
		properties.add(Property.HUMAN);
		properties.add(Property.IMMOVABLE);
	}

	@Override
	protected boolean act() {

		throwItem();

		sprite.turnTo(pos, Dungeon.hero.pos);
		spend(TICK);
		return true;
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 1000;
	}
	
	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	public static WndBag sell() {
		return GameScene.selectItem(itemSelector, WndBag.Mode.FOR_SALE,
				Messages.get(Shopkeeper.class, "sell"));
	}

	private static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				
				WndBag parentWnd = sell();
				GameScene.show(new WndTradeItem(item, parentWnd));
			}
		}
	};

	@Override
	public void interact() {
		sell();
	}
}
