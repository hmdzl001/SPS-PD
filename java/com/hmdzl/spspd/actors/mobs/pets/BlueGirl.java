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
import com.hmdzl.spspd.actors.buffs.Shieldblock;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.StoneArmor;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.meatfood.MeatFood;
import com.hmdzl.spspd.sprites.BlueGirlSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class BlueGirl extends PET {
	
	{
		//name = "scorpion";
		spriteClass = BlueGirlSprite.class;
		//flying=false;
		state = HUNTING;

		type = 601;

		cooldown=50;
		oldcooldown=30;
		properties.add(Property.ELF);
	}

	@Override
	public void updateStats()  {
		HT = 150 + hero.petLevel*5;
		evadeSkill = hero.petLevel;
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof MeatFood;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((6+hero.petLevel), (6+hero.petLevel*4));
	}

	@Override
	public Item SupercreateLoot(){
		return new StoneArmor();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(5+hero.petLevel,10+hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 10;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {

		if (cooldown > 0) {
			cooldown--;
		}

		if (cooldown == 0) {
			if (enemy.isAlive()) {
				Buff.affect(enemy, Shieldblock.class, 2f);
				damage += damage;
				cooldown = Math.max(20, 40 - hero.petLevel);
			}
		}
			return damage;
	}

}