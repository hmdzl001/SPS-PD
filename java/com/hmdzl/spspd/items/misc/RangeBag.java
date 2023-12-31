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
package com.hmdzl.spspd.items.misc;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;

import java.util.ArrayList;

public class RangeBag extends MiscEquippable {
	
	{
		image = ItemSpriteSheet.RANGE_BAG;	
		unique = true;
		defaultAction = AC_BUY;
	}
	
    public static final String AC_BUY = "BUY";	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
        actions.add(AC_BUY);
		return actions;
	}	

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;

		if (action.equals( AC_BUY )){
			if (Dungeon.gold < 500 ) {
				 GLog.p(Messages.get(this, "need_gold"));
			} else {
				Dungeon.gold -= 500;
				hero.spend(1f);
				hero.busy();

				hero.sprite.operate(hero.pos);
				Dungeon.depth.drop(Generator.random(Generator.Category.LINKDROP), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

			}
		} else {
			super.execute(hero, action);
		}
	}
	
	@Override
	protected MiscBuff buff() {
		return new RangeBagBless();
	}
	@Override
	public int price() {
		return 30 * quantity;
	}
	public class RangeBagBless extends MiscBuff {

	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

}
