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
package com.hmdzl.spspd.items.weapon.missiles.throwing;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.buffs.Shieldblock;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class MoneyBook extends TossWeapon {
	
    public static final String AC_CAST	= "CAST";
	{
		//name = "";
		image = ItemSpriteSheet.JOURNAL_PAGE;

		STR = 10;

		MIN = 3;
		MAX = 6;
		
	}

	public MoneyBook() {
		this(1);
	}

	public MoneyBook(int number) {
		super();
		quantity = number;
	}



	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	

	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(AC_CAST);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		 if (action.equals(AC_CAST)) {
			curUser = hero;
			Buff.affect(hero,Invisibility.class,10f);
			for (int m : Level.NEIGHBOURS8DIST2) {
			int c = hero.pos + m;
			if (c >= 0 && c < Level.getLength()) {

				Char ch2 = Actor.findChar(c);
				if (ch2 != null) {
					Buff.affect(ch2, Shieldblock.class, 5f);
				}
			}
		}
		detach(curUser.belongings.backpack);
		}
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender,Shieldblock.class,10f);
		super.proc(attacker, defender, damage);
	}


}
