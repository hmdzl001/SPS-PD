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
import com.hmdzl.spspd.actors.buffs.Shieldblock;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.CompleteFood;
import com.hmdzl.spspd.items.scrolls.ScrollOfRecharging;
import com.hmdzl.spspd.sprites.HaroSprite;
import com.watabou.utils.Random;

public class Haro extends PET {
	
	{
		//name = "haro";
		spriteClass = HaroSprite.class;
        //flying=true;
		state = HUNTING;

		type = 403;

		oldcooldown = 10;
		properties.add(Property.MECH);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof CompleteFood;
	}


	@Override
	public void updateStats()  {

		evadeSkill = Dungeon.hero.petLevel;
		HT = 70 + Dungeon.hero.petLevel*5;
	}


	@Override
	public Item SupercreateLoot(){
		return new ScrollOfRecharging();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(8+Dungeon.hero.petLevel,10+Dungeon.hero.petLevel);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 20;
	}


	@Override
	public int damageRoll() {
		return Random.NormalIntRange(5, (10+Dungeon.hero.petLevel*4));
	}

	@Override
	public void move(int step) {
		super.move(step);
			if (cooldown > 0) {
				cooldown--;
			}
	}
	@Override
	public int attackProc(Char enemy, int damage) {
		Dungeon.hero.belongings.relord();
		Buff.affect(Dungeon.hero,Recharging.class,2f);
		return damage;
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {
		if (cooldown == 0) {
			Buff.affect(enemy,Shieldblock.class,5f);
			cooldown = Math.max(10,50-Dungeon.hero.petLevel);
		}
		return damage;
	}	
}	
	