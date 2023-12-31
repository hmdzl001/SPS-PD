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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.AttackDown;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HealBag extends Item {

	{
		//name = "healbag";
		image = ItemSpriteSheet.HEAL_BAG;
		unique = true;
		defaultAction = AC_HEAL;
	}

	public final int fullCharge = 40;
	public int charge = 0;	

    public static final String AC_HEAL = "HEAL";
	public static final String AC_COOK = "COOK";

	private static final String CHARGE = "charge";	

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHARGE, charge);
	}	
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		charge = bundle.getInt(CHARGE);
	}
		@Override
	public int price() {
		return 30 * quantity;
	}
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_DROP);
		actions.remove(AC_THROW);
		if (charge > 15) {
		actions.add(AC_HEAL);
		}
		if (charge > 40) {
			actions.add(AC_COOK);
		}
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		curUser = Dungeon.hero;

        if (action.equals(AC_HEAL)) {
			if (charge < 15) {
				GLog.p(Messages.get(this, "need_charge"));
			} else {
				for (int n : Floor.NEIGHBOURS9) {
					int c = curUser.pos + n;
					Char mob = Actor.findChar(c);
					if (mob != null && mob.HP < mob.HT * 0.75) {
						mob.HP += mob.HT / 2;
					}
				}
				charge -= 15;

				Buff.detach(hero, Poison.class);
				Buff.detach(hero, Cripple.class);
				Buff.detach(hero, STRdown.class);
				Buff.detach(hero, Bleeding.class);
				Buff.detach(hero, AttackDown.class);
				Buff.detach(hero, ArmorBreak.class);

				hero.spendAndNext(1f);
			}
		} else if (action.equals(AC_COOK)) {

			if (Random.Int(3) > 1) {
		    Dungeon.depth.drop(Generator.random(Generator.Category.POTION), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			} else if (Random.Int(5) == 0) {
				Dungeon.depth.drop(Generator.random(Generator.Category.HIGHFOOD), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			} else if (Random.Int(2) == 0) {
				Dungeon.depth.drop(Generator.random(Generator.Category.MUSHROOM), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			} else {
				Dungeon.depth.drop(Generator.random(Generator.Category.PILL), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			}
			charge -= 40;
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
		info += "\n\n" + Messages.get(AttackShield.class, "charge",charge,fullCharge);
		return info;
	}	
	 @Override
	 public String status() {
			 return Messages.format("%d", charge /40);
	 }	
}
