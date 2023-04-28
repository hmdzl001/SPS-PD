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
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.sprites.NewSnakeSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class Snake extends PET {
	
	{
		//name = "Snake";
		spriteClass = NewSnakeSprite.class;
        //flying=true;
		state = HUNTING;
        cooldown=50;
		oldcooldown=30;
		type = 104;

		properties.add(Property.BEAST);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof MeatFood;
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
		return new PotionOfToxicGas();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(0,hero.petLevel*2);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 10;
	}
	
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0 && enemy.isAlive()) {
			Buff.affect(enemy, Poison.class).set(Random.Int(5, 7));
		}
		
		if (cooldown == 0 && enemy.isAlive()) {
			enemy.damage(Math.max(1,(int)(enemy.HP/3)),this);
			cooldown = Math.max(5,25 - hero.petLevel);
		}
		
        if (cooldown > 0) {
		   cooldown--;
		} 
		return damage;
	}	
	
}