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

import java.util.HashSet;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.items.food.meatfood.Meat;
import com.hmdzl.spspd.change.sprites.AlbinoSprite;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.scenes.GameScene;

public class Albino extends Rat {

	{
		spriteClass = AlbinoSprite.class;

		HP = HT = 10+(Dungeon.depth*Random.NormalIntRange(1, 3));

		loot = new Meat();
		lootChance = 1f;
		
		properties.add(Property.BEAST);
		properties.add(Property.DEMONIC);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}
	
	@Override
	public boolean act() {
		GameScene.add(Blob.seed(pos, 15, CorruptGas.class));
		return super.act();
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(CorruptGas.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
