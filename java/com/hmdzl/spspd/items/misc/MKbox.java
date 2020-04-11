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

import java.util.ArrayList;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.buffs.BoxStar;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FireImbue;
import com.hmdzl.spspd.actors.buffs.FrostImbue;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Ankh;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.items.weapon.melee.zero.WoodenHammer;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class MKbox extends Item {

	public static final String AC_USE = "USE";

	public static final float TIME_TO_USE = 1;

	{
		//name = "MKbox";
		image = ItemSpriteSheet.MK_BOX;
		defaultAction = AC_USE;
		unique = true;
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
           if (Dungeon.gold < 100){
			   GLog.p(Messages.get(MKbox.class, "need_gold"));
		   } else {
			   Dungeon.gold -= 100;
			   hero.spend(TIME_TO_USE);
			   hero.busy();

			   hero.sprite.operate(hero.pos);
			   if (Random.Int(50) == 0) {
				   Buff.affect(hero,BoxStar.class,30f);
				   GLog.p(Messages.get(MKbox.class, "star"));
			   } else if (Random.Int(49) == 0) {
				   Dungeon.gold += 500;
				   GLog.p(Messages.get(MKbox.class, "coin"));
				   //GLog.p(Messages.get(MKbox.class,"mbox"));
			   } else if (Random.Int(48) < 3) {
				   Dungeon.level.drop(new Ankh(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				   GLog.p(Messages.get(MKbox.class, "1up"));
			   } else if (Random.Int(45) < 20) {
				   WoodenHammer wh = new WoodenHammer();
				   Dungeon.level.drop(wh, Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				   GLog.p(Messages.get(MKbox.class, "hammer"));
			   } else if (Random.Int(25) < 20) {
				   Dungeon.level.drop(Generator.random(Generator.Category.MUSHROOM), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				   GLog.p(Messages.get(MKbox.class, "mushroom"));
			   } else if (Random.Int(2) == 1) {
			   	   Buff.affect(hero,FireImbue.class).set(FireImbue.DURATION);
				   GLog.p(Messages.get(MKbox.class, "flowerf"));
			   } else {
				   Buff.affect(hero, FrostImbue.class, FrostImbue.DURATION);
				   GLog.p(Messages.get(MKbox.class, "floweri"));
			   }
		   }
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

}
