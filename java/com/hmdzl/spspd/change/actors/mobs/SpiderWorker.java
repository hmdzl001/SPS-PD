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

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Web;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.SpiderNormalSprite;
import com.hmdzl.spspd.change.sprites.SpinnerSprite;
import com.watabou.utils.Random;

public class SpiderWorker extends Mob {

	{
		spriteClass = SpiderNormalSprite.class;

		HP = HT = 150;
		evadeSkill = 10;

		EXP = 9;
		maxLvl = 16;

		loot = new MysteryMeat();
		lootChance = 0.15f;
		
		properties.add(Property.BEAST);
	}
	
	private static final float SPAWN_DELAY = 1f;

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12, 26+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 20+adj(0);
	}

	@Override
	public int drRoll() {
		return 6+adj(0);
	}


	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

	static {
		RESISTANCES.add(Poison.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
    public static SpiderWorker spawnAt(int pos) {
		
    	SpiderWorker b = new SpiderWorker();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
    }
}
