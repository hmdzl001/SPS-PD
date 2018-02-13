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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.sprites.BanditSprite;
import com.watabou.utils.Random;

public class Bandit extends Thief {

	public Item item;

	{
		spriteClass = BanditSprite.class;

		// 1 in 30 chance to be a crazy bandit, equates to overall 1/90 chance.
		lootChance = 0.10f;
		
		properties.add(Property.ELF);
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		Buff.prolong(enemy, Blindness.class, Random.Int(5, 12));
		Buff.affect(enemy, Poison.class).set(
				Random.Int(5, 7) * Poison.durationFactor(enemy));
		Buff.prolong(enemy, Cripple.class, Cripple.DURATION);
			Dungeon.observe();

		return damage;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}
}
