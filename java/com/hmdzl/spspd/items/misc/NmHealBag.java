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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.ui.ActionIndicator.action;

public class NmHealBag extends Item {

	{
		//name = "healbag";
		image = ItemSpriteSheet.HEAL_BAG;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	private static final String AC_CHOOSE = "CHOOSE";
	
    public static final String AC_HEAL = "HEAL";
	public static final String AC_COOK = "COOK";
	public static final String AC_ADD = "ADD";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		
		actions.add(AC_HEAL);
		actions.add(AC_COOK);
		actions.add(AC_ADD);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_HEAL)) {
           if (Dungeon.hero.spp > 0) {
			   hero.HP = hero.HP + Dungeon.hero.spp;
			   Dungeon.hero.spp = 0;
			   Buff.detach(hero, Poison.class);
			   Buff.detach(hero, Cripple.class);
			   Buff.detach(hero, Weakness.class);
			   Buff.detach(hero, Bleeding.class);
			   Buff.detach(hero, AttackDown.class);
			   Buff.detach(hero, ArmorBreak.class);
		   }
				hero.spendAndNext(1f);
        } else
		 if (action.equals(AC_COOK)) {
			 if (Dungeon.hero.spp < 10) {
				 GLog.p(Messages.get(this, "need_charge"));
			 } else {
				 if (Random.Int(4) == 0) {
					 Dungeon.level.drop(Generator.random(Generator.Category.HIGHFOOD), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				 } else {
					 Dungeon.level.drop(Generator.random(Generator.Category.SUMMONED), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
				 }
				 Dungeon.hero.spp -= 10;
				 hero.spendAndNext(1f);
			 }
		 } else
		 if (action.equals(AC_ADD)) {
        	if (hero.HP > 10) {
				Dungeon.hero.spp += (int)(hero.HP/4);
                hero.HP = 1;
        	}
			hero.spendAndNext(1f);
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
	public String desc() {
		String info = super.desc();
		return info;
	}	

}
