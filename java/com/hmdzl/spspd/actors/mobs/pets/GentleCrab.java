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
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.potions.PotionOfShield;
import com.hmdzl.spspd.sprites.GentleCrabSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class GentleCrab extends PET {
	
	{
		//name = "GentleCrab";
		spriteClass = GentleCrabSprite.class;
        //flying=true;
		state = HUNTING;
		type = 102;
		cooldown=50;
		oldcooldown=30;
		baseSpeed = 3/2;
		properties.add(Property.FISHER);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Vegetable;
	}


	@Override
	public void updateStats()  {
		evadeSkill = hero.petLevel;
		HT = 150 + 2*hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((5+hero.petLevel), (5+hero.petLevel*2));
	}

	@Override
	public Item SupercreateLoot(){
		return new PotionOfShield();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(0,hero.petLevel);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 10;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			damage = (int)(damage*1.5);
		}
		if (cooldown == 0 && enemy.isAlive()) {
			Buff.prolong(enemy, ArmorBreak.class,5f).level(10+hero.petLevel);
			cooldown = Math.max(5,25 - hero.petLevel);
		}
        if (cooldown > 0) {
		   cooldown--;
		} 

		return damage;
	}	
}