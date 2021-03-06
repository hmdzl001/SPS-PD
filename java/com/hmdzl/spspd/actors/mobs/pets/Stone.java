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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.sprites.StoneSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Stone extends PET {
	
	{
		//name = "Stone";
		spriteClass = StoneSprite.class;
        //flying=true;
		state = HUNTING;
		level = 1;
		type = 18;

		properties.add(Property.ELEMENT);
	}
	
	

	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		adjustStats(level);
	}

	@Override
	public void adjustStats(int level) {
		this.level = level;
		evadeSkill = 5 + level;
		HT = 90 + level*5;
	}
	



	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, (10+level*2));
	}

	@Override
	protected boolean act() {		
		

		return super.act();
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(20) == 0) {
			Buff.affect(enemy, Paralysis.class, 3f);
		}

		return damage;
	}	
}