/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.mindbuff.AmokMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.CrazyMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.LoseMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.TerrorMind;
import com.hmdzl.spspd.actors.buffs.mindbuff.WeakMind;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.missiles.MindArrow;
import com.hmdzl.spspd.messages.Messages;import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PPC extends Item {

	public static final String AC_TRY = "TRY";
	public static final String AC_HEAL = "HEAL";
	public static final String AC_MIND = "MIND";


	{
		//name = "PPC";
		image = ItemSpriteSheet.PPC;
		defaultAction = AC_TRY;
		unique = true;
		 
	}
	
	public int charge = 0;
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
	public ArrayList<String> actions(Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
	
		actions.add(AC_TRY);
		if (charge > 20)
		actions.add(AC_HEAL);
	    if (charge > 5)
		actions.add(AC_MIND);
		
        actions.remove( AC_THROW );
        actions.remove( AC_DROP );
		return actions;
	}

	@Override
	public void execute( final Hero hero, String action ) {
		if (action.equals(AC_TRY)) {
			if (charge < 1) {
				GLog.p(Messages.get(PPC.class, "need_charge"));
			} else {
				charge--;
				hero.sprite.operate(hero.pos);
				hero.busy();
				Dungeon.hero.spp += Random.Int(10);

				Buff.affect(Dungeon.hero, Bless.class, 10f);

			}
		}
		 if (action.equals(AC_HEAL)) {

				hero.sprite.operate(hero.pos);
				hero.busy();
				charge -= 20;
			 if (Dungeon.hero.buff(CrazyMind.class) != null) {
				 Buff.detach(Dungeon.hero, CrazyMind.class);
			 } else if (Dungeon.hero.buff(WeakMind.class) != null) {
				 Buff.detach(Dungeon.hero, WeakMind.class);
			 } else if (Dungeon.hero.buff(AmokMind.class) != null) {
				 Buff.detach(Dungeon.hero, AmokMind.class);
			 } else if (Dungeon.hero.buff(TerrorMind.class) != null) {
				 Buff.detach(Dungeon.hero, TerrorMind.class);
			 } else if (Dungeon.hero.buff(LoseMind.class) != null) {
				 Buff.detach(Dungeon.hero, LoseMind.class);
			 }

				Dungeon.hero.HP += Dungeon.hero.HT / 5;
				Dungeon.hero.spp = 0;
			}
			if (action.equals(AC_MIND)) {
				hero.sprite.operate(hero.pos);
				hero.busy();
				charge -= 2;
                Dungeon.level.drop(new MindArrow(5), hero.pos).sprite.drop();
			} else {
				super.execute(hero, action);

		}

	}

	@Override
	public String status() {
		return Messages.format("%d", charge);
	}
	
	@Override
	public String info() {
		String info = desc();
		info += "\n\n" + Messages.get(PPC.class, "charge",charge);
		return info;	
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
