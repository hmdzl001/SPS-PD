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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.sprites.HaroSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Haro extends PET {
	
	{
		//name = "haro";
		spriteClass = HaroSprite.class;
        //flying=true;
		state = HUNTING;
		level = 1;
		type = 29;

		properties.add(Property.MECH);
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
		HT = 100 + level*5;
	}
	



	@Override
	public int damageRoll() {
		return Random.NormalIntRange(5, (10+level*2));
	}

	@Override
	protected boolean act() {		
		

		return super.act();
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		Dungeon.hero.belongings.relord();
		Buff.affect(Dungeon.hero,Recharging.class,2f);
		return damage;
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		return damage;
	}	
}	
	