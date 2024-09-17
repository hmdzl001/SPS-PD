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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.SelfDenial;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

public class FiveFlower extends MeleeWeapon {

	{
		//name = "goei";
		image = ItemSpriteSheet.FIVE_FLOWER;
		 
	}
	public FiveFlower() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 1;
	}

	@Override
	public int damageRoll(Hero hero) {
		return 0;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		Buff.prolong(defender, Amok.class, 3f);
		Buff.prolong(defender, Charm.class, 3f).object = attacker.id();

		Buff.affect(defender, SelfDenial.class).set(20);

	}

}
