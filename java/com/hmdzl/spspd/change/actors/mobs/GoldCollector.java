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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.items.food.meatfood.Meat;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.RatSprite;
import com.watabou.utils.Random;

public class GoldCollector extends Mob {

	{
		spriteClass = RatSprite.class;

		HP = HT = Dungeon.gold;
		evadeSkill = Dungeon.gold;
		baseSpeed = 3f;
		flying = true;

		state = WANDERING;
		
		properties.add(Property.BOSS);
	}
	
	@Override
	public int damageRoll() {
		return Dungeon.gold;
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.gold;
	}

	@Override
	public int drRoll() {
		return Dungeon.gold;
	}	
	
	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
}
