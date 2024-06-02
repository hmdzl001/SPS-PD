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
package com.hmdzl.spspd.actors.mobs.chartype;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.sprites.ErrorSprite;

public class TrollType extends Mob {
	

	private static final float SPAWN_DELAY = 2f;

	{
		spriteClass = ErrorSprite.class;

		HP = HT = 1;
		evadeSkill = 1;
		
		EXP = 1;
		maxLvl = 1;
		
		properties.add(Property.TROLL);
	}

	@Override
	public int damageRoll() {
		return 1;
	}

	@Override
	public int hitSkill(Char target) {
		return 1;
	}

	@Override
	public int drRoll() {
		return 1;
	}

}
