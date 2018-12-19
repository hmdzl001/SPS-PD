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
package com.hmdzl.spspd.change.items.weapon.missiles;

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;

public class HugeShuriken extends MissileWeapon {

	{
		//name = "Huge shuriken";
		image = ItemSpriteSheet.HUGESHURIKEN;

		STR = 13;

		MIN = 2;
		MAX = 6;

		DLY = 0.5f;
	}

	public HugeShuriken() {
		this(1);
	}

	public HugeShuriken(int number) {
		super();
		quantity = number;
	}

	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if ((enemy == null || enemy == curUser) && Level.flamable[cell])
			GameScene.add(Blob.seed(cell, 4, Fire.class));
		
		else
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender, Burning.class).reignite(defender);
		super.proc(attacker, defender, damage);
	}
		
}
