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
package com.hmdzl.spspd.change.items.reward;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.bombs.DumplingBomb;
import com.hmdzl.spspd.change.items.food.fruit.Blackberry;
import com.hmdzl.spspd.change.items.food.fruit.Blueberry;
import com.hmdzl.spspd.change.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.change.items.food.fruit.Moonberry;
import com.hmdzl.spspd.change.items.weapon.missiles.RiceBall;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class CaveReward extends Item {

	public static final String AC_USE = "USE";

	public static final float TIME_TO_USE = 1;

	{
		//name = "reward";
		image = ItemSpriteSheet.PETFOOD;

		stackable = false;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE)) {

			hero.spend(TIME_TO_USE);
			hero.busy();

			hero.sprite.operate(hero.pos);

			Moonberry berry1 = new Moonberry(10);
			berry1.doPickUp(Dungeon.hero);	
			Cloudberry berry2 = new Cloudberry(10);
			berry2.doPickUp(Dungeon.hero);	
			Blueberry berry3 = new Blueberry(10);
			berry3.doPickUp(Dungeon.hero);	
			Blackberry berry4 = new Blackberry(10);
			berry4.doPickUp(Dungeon.hero);
			
			detach(hero.belongings.backpack);

		} else {
			super.execute(hero, action);

		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
}
