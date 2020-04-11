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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.Item;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DemoScroll extends Item {
	
    public static final String AC_READ = "READ";
	{
		//name = "DemoScroll";
		image = ItemSpriteSheet.DEMO_SCROLL;

		stackable = false;
		charge = 0;
		//defaultAction = AC_READ;
	}

	public static int charge;
	private static final String CHARGE = "charge";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (charge < hero.lvl && hero.HT >  hero.lvl) actions.add(AC_READ);
		return actions;
	}
	
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
	public void execute(Hero hero, String action) {

		if (action.equals(AC_READ)) {
            
	    switch (Random.Int (4)) {
		case 0 :
            hero.hitSkill++;
			GLog.w(Messages.get(this, "hitup"));
			break;
        case 1 :
		    hero.evadeSkill++;
			GLog.w(Messages.get(this, "evaup"));
			break;
        case 2 :
            hero.magicSkill++;
			GLog.w(Messages.get(this, "migup"));
			break;
	    case 3 :
            hero.STR++;
			GLog.w(Messages.get(this, "strup"));
			break;
		default:
		
			break;
        }
		 charge ++;
		 hero.sprite.operate(hero.pos);
		hero.busy();
		hero.spend(2f);
		 int dmg = Random.Int(6,Dungeon.hero.lvl);
          hero.damage(dmg,this);
		 if (!hero.isAlive()) {
				Dungeon.fail(Messages.format(ResultDescriptions.ITEM));
				//GLog.n("The Chalice sucks your life essence dry...");
			}
		 Dungeon.hero.HT-= Math.min(dmg,hero.HT-1);
		 GLog.w(Messages.get(this, "htdown"));
            //hero.hitSkill++;
            //hero.evadeSkill++;
            //hero.magicSkill++;
		    
			//detach(hero.belongings.backpack);
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
