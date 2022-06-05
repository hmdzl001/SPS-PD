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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.weapon.melee.special.Brick;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.RatSprite;
import com.watabou.utils.Random;

public class Rat extends Mob {
	

	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = RatSprite.class;

		HP = HT = 40+(Dungeon.depth*Random.NormalIntRange(1, 3));
		evadeSkill = 3+(Math.round((Dungeon.depth)/2));
		
		EXP = 1;
		maxLvl = 4;
		
		loot = new Meat();
		lootChance = 0.5f;
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return new Brick();
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 5+Dungeon.depth);
	}

	@Override
	public int hitSkill(Char target) {
		return 5+Dungeon.depth;
	}

	@Override
	public int drRoll() {
		return 1;
	}

	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static Rat spawnAt(int pos) {
		
        Rat b = new Rat();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	

	
}
