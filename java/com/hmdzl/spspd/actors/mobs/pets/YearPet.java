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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.weapon.missiles.MoneyPack;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.BeastYearSprite;
import com.watabou.utils.Random;

public class YearPet extends PET {
	
	{
		//name = "year";
		spriteClass = BeastYearSprite.class;
        //flying=true;
		state = HUNTING;

		baseSpeed = 0.5f;
		type = 666;
		
		properties.add(Property.BEAST);
		properties.add(Property.UNKNOW);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof MoneyPack;
	}


	@Override
	public void updateStats()  {

		evadeSkill = 0;
		HT = 500 + Dungeon.hero.petLevel*10;
	}
	@Override
	public Item SupercreateLoot(){
		return new MoneyPack(5);
	}

	@Override
	public int drRoll(){
		return Random.IntRange(Dungeon.hero.petLevel,2*Dungeon.hero.petLevel);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 20;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((10+Dungeon.hero.petLevel*2), (10+Dungeon.hero.petLevel*3));
	}

	@Override
	protected boolean canAttack(Char enemy) {

		return Level.distance( pos, enemy.pos ) <= 2 ;

	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown > 0) cooldown --;
		return damage;
	}



	
}