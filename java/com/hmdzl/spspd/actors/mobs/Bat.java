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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.sprites.BatSprite;
import com.watabou.utils.Random;

public class Bat extends Mob {

	{
		spriteClass = BatSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);
		baseSpeed = 2f;

		EXP = 9;
		maxLvl = 25;

		flying = true;

		loot = Generator.Category.SEED;
		lootChance = 0.15f; // by default, see die()

		lootOther = new Meat();
		lootChanceOther = 0.3f; // by default, see die()
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.MUSHROOM);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 22+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
		return adj(0);
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		int reg = Math.min(damage, HT - HP);

		if (reg > 0) {
			HP += reg;
			sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
		}

		return damage;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		lootChance = 1f / 6 ;
		
	}

	@Override
	protected Item createLoot() {
		return super.createLoot();
	}

}
