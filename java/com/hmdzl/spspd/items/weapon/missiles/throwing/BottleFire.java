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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.FireFollower;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;

import static com.hmdzl.spspd.Dungeon.hero;

public class BottleFire extends TossWeapon {
	

	{
		//name = "";
		image = ItemSpriteSheet.BOTTLE_FIRE;

		STR = 10;

		MIN = 1;
		MAX = 1;
		
	}


	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if (enemy == null) {
			for (int offset : Level.NEIGHBOURS9) {
				if (Level.flamable[cell + offset]
						|| Actor.findChar(cell + offset) != null
						|| Dungeon.level.heaps.get(cell + offset) != null) {
					GameScene.add(Blob.seed(cell + offset, 5, Fire.class));
				}
			}
			Buff.affect(hero,FireFollower.class).set(30f);
		} else
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		Buff.affect(defender,Burning.class).set(10f);
        Buff.affect(attacker,FireFollower.class).set(30f);
		super.proc(attacker, defender, damage);
	}


}
