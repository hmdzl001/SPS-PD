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
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.sellitem.MiniBunny;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.sprites.BunnySprite;
import com.watabou.utils.Random;

public class Bunny extends PET{
	
	{
		//name = "bunny";
		spriteClass = BunnySprite.class;       
		state = HUNTING;
		type = 401;
		cooldown=40;
        oldcooldown = 10;
		properties.add(Property.BEAST);
	}


	@Override
	public void updateStats()  {

		HT = 150 + Dungeon.hero.petLevel*3;
		evadeSkill = 8 + Dungeon.hero.petLevel;
	}

	@Override
	public void move(int step) {
		super.move(step);

		if (Dungeon.depth.map[step] == Terrain.HIGH_GRASS ||
				Dungeon.depth.map[step] == Terrain.OLD_HIGH_GRASS ||
				Dungeon.depth.map[step] == Terrain.GRASS ) {
			if (cooldown > 0) {
				cooldown--;
			}
		}

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Vegetable ||
				item instanceof Fruit;
	}

	@Override
	public Item SupercreateLoot(){
		return new MiniBunny();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(1,Dungeon.hero.petLevel*2);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 8;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((5+Dungeon.hero.petLevel)*5, (5+Dungeon.hero.petLevel*3)*4) ;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown==0) {
			Dungeon.depth.drop(Generator.random(Random.oneOf(Generator.Category.SEED,
					Generator.Category.BERRY,Generator.Category.MUSHROOM)),pos).sprite.drop();
			cooldown=Math.max(4,40-Dungeon.hero.petLevel);
		}
		return damage;
	}

}