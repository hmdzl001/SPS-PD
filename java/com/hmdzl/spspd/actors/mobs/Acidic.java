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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.AcidicSprite;
import com.watabou.utils.Random;

public class Acidic extends Scorpio {

	{
		spriteClass = AcidicSprite.class;
		
		properties.add(Property.BEAST);
		properties.add(Property.DEMONIC);
	}

	@Override
	public boolean act() {
		GameScene.add(Blob.seed(pos, 30, StenchGas.class));
		return super.act();
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage/2);
		if (dmg > 0) {
			enemy.damage(dmg, this);
		}

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}
	
	{
		immunities.add(StenchGas.class);
	}

	@Override
	public Item SupercreateLoot(){
		return new WandOfAcid();
	}
}
