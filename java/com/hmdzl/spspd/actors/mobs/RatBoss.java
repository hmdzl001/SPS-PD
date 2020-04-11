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


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.sprites.RatBossSprite;
import com.watabou.utils.Random;

public class RatBoss extends Mob {

	{
		spriteClass = RatBossSprite.class;

		HP = HT = 50+(Dungeon.depth*Random.NormalIntRange(2, 5));
		evadeSkill = 5+(Dungeon.depth/4);
		
		EXP = 10;
		
		loot = Generator.Category.BERRY;
		lootChance = 0.5f;
		
		lootOther = new ScrollOfRegrowth();
		lootChanceOther = 0.1f;

		properties.add(Property.BEAST);
		properties.add(Property.BOSS);
	}

	private boolean spawnedRats = false;
			
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(2+Dungeon.depth/2, 8+(Dungeon.depth));
	}

	@Override
	public int hitSkill(Char target) {
		return 11+Dungeon.depth;
	}

	@Override
	public int drRoll() {
		return Dungeon.depth/2;
	}

	@Override
	public void notice() {
		super.notice();
		if (!spawnedRats){
	    Rat.spawnAround(pos);
	    spawnedRats = true;
		}
	}
}
