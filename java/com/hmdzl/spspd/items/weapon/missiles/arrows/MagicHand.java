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
package com.hmdzl.spspd.items.weapon.missiles.arrows;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class MagicHand extends Arrows {

	{
		image = ItemSpriteSheet.MAGIC_HAND;
	}

	public MagicHand() {
		this(1);
	}

	public MagicHand(int number) {
		super(1,5);
		quantity = number;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		if (enchant != null)
		     enchant.proc(this, attacker, defender, damage);
		super.proc(attacker, defender, damage);
			Item loot = ((Mob) defender).SupercreateLoot();
			Dungeon.depth.drop(loot, attacker.pos).sprite.drop();
			((Mob) defender).firstitem = false;

	}	
	
	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return quantity * 100;
	}
}
