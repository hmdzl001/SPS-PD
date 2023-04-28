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
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.sprites.LitDemonSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class LitDemon extends PET {
	{
		spriteClass = LitDemonSprite.class;
        //flying=true;
		state = HUNTING;
		type = 105;
		cooldown=50;
		oldcooldown=30;
	
		properties.add(Property.DEMONIC);
	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof PotionOfMending;
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
		return new ScrollOfRage();
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
		
		enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
		
		if (cooldown == 0 && enemy.isAlive()) {
			enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
			enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
			enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
			enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
			enemy.damage(Random.Int(1,(int)(damage/5)),Item.class);
			cooldown = Math.max(6,26 - hero.petLevel);
		}
        if (cooldown > 0) {
		   cooldown--;
		} 

		return damage;
	}	
}