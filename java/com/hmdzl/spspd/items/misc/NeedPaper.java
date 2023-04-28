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
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.ForeverShadow;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndItem;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class NeedPaper extends Item {

	{
		//name = "NeedPaper";
		image = ItemSpriteSheet.NEED_PAPER;
		unique = true;
		defaultAction = AC_CHOOSE;
	}

	private static final String AC_CHOOSE = "CHOOSE";
	
    public static final String AC_HELP = "HELP";
	public static final String AC_SHOP = "SHOP";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		
		actions.add(AC_HELP);
		actions.add(AC_SHOP);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;
		if (action.equals( AC_CHOOSE )) {

			GameScene.show(new WndItem(null, this, true));

		} else if (action.equals(AC_HELP)) {
		 if (Dungeon.hero.spp < 500) {
				 GLog.p(Messages.get(this, "need_charge"));
		 } else {
			 hero.sprite.operate(hero.pos);
			   hero.HP = hero.HT;
			   Dungeon.hero.spp -= 500;
			   Buff.affect(hero, ForeverShadow.class, 15f);
			   Buff.detach(hero, Poison.class);
			   Buff.detach(hero, Cripple.class);
			   Buff.detach(hero, STRdown.class);
			   Buff.detach(hero, Bleeding.class);
			   Buff.detach(hero, AttackDown.class);
			   Buff.detach(hero, ArmorBreak.class);
		   }
				hero.spendAndNext(1f);
        } else
		 if (action.equals(AC_SHOP)) {
			 if (Dungeon.hero.spp < 3000) {
				 GLog.p(Messages.get(this, "need_charge"));
			 } else {
				 hero.sprite.operate(hero.pos);

				Dungeon.level.drop(Generator.random(Random.oneOf(Generator.Category.WAND,
						Generator.Category.RING, Generator.Category.ARTIFACT,
						Generator.Category.MELEEWEAPON, Generator.Category.ARMOR)), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);

				 Dungeon.hero.spp -= 3000;
				 hero.spendAndNext(1f);
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

	@Override
	public String desc() {
		String info = super.desc();
		return info;
	}	
	@Override
	public int price() {
		return 30 * quantity;
	}
}
