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
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.change.sprites.CellmobSprite;
import com.hmdzl.spspd.change.sprites.CrabSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.watabou.utils.Random;

public class NormalCell extends Mob {

	{
		spriteClass = CellmobSprite.class;

		HP = HT = 1;
		evadeSkill = 0;
		baseSpeed = 0.5f;
		
		properties.add(Property.BOSS);
		properties.add(Property.MINIBOSS);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.HT/20, Dungeon.hero.HT/10);
	}

	@Override
	public int hitSkill(Char target) {
		return 99;
	}

}
