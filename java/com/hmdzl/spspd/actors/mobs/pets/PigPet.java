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
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.Honeymeat;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.meatfood.SmallMeat;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.sprites.PigPetSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class PigPet extends PET {
	
	{
		//name = "PigPet";
		spriteClass = PigPetSprite.class;
        //flying=true;
		state = HUNTING;
		type = 303;
        cooldown=50;
		oldcooldown=30;
		properties.add(Property.BEAST);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Plant.Seed ||
				item instanceof Vegetable ||
				item instanceof Fruit;
	}


	@Override
	public void updateStats()  {
		evadeSkill =   hero.petLevel;
		HT = 150 + 2*  hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}

	@Override
	public Item SupercreateLoot(){
		return new Honeymeat();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(hero.petLevel,hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 5;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
        cooldown--;
        if(cooldown == 0){
			Dungeon.depth.drop(Generator.random(Generator.Category.MUSHROOM), enemy.pos).sprite.drop();
			cooldown =  Math.max(25,45 - hero.petLevel);
		}
		return damage;
	}	
	
	@Override
	public int defenseProc(Char enemy, int damage) {
		if (Random.Int(15) == 0) {
			Dungeon.depth.drop(new SmallMeat(), pos).sprite.drop();
		}

		return damage;
	}	
}	
	