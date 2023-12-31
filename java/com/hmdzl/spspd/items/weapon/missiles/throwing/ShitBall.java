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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class ShitBall extends TossWeapon {
	

	{
		//name = "Normal Bomb";
		image = ItemSpriteSheet.SHIT_BOMB;

		STR = 10;

		MIN = 1;
		MAX = 1;
		DLY = 0.5f;
	}

	public ShitBall() {
		this(2);
	}

	public ShitBall(int number) {
		super();
		quantity = number;
	}

	private static ItemSprite.Glowing BROWN = new ItemSprite.Glowing(0xCC6600);

	@Override
	public ItemSprite.Glowing glowing() {
		return BROWN;
	}


	@Override
	protected void onThrow(int cell) {
        Char enemy = Actor.findChar(cell);
       if (enemy == null) {
		   for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			   if (Floor.fieldOfView[mob.pos] && (Floor.distance(cell, mob.pos) <= 5)) {
				   Buff.affect(mob, Blindness.class, 5f);
				   mob.beckon(cell);
			   }
		   }
	   } else
		   super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender, BeOld.class).set(20f);
		Buff.affect(defender, Tar.class);
		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return 5 * quantity;
	}
}
