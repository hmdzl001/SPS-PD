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
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.food.Nut;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.watabou.utils.Random;

public class CocoCat extends PET {
	
	{
		spriteClass = CoconutSprite.class;       
		//flying=false;
		state = HUNTING;

		type = 402;
		cooldown=50;
		oldcooldown = 10;

		properties.add(Property.BEAST);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Nut;
	}


	@Override
	public void updateStats()  {
		HT =  200+ 2*Dungeon.hero.petLevel;
		evadeSkill = 3 + Dungeon.hero.petLevel;
	}

	@Override
	public Item SupercreateLoot(){
		return new DungeonBomb();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(2+Dungeon.hero.petLevel,5+Dungeon.hero.petLevel);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 5;
	}

	@Override
	public int damageRoll() {		
		return Random.NormalIntRange((2+Dungeon.hero.petLevel), (5+Dungeon.hero.petLevel*3)) ;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {

		return Level.distance( pos, enemy.pos ) <= 4 ;
	
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown > 0) cooldown --;
		if (cooldown==0) {
		BuildBomb bomb = new BuildBomb();
		bomb.explode(enemy.pos);
		cooldown=Math.max(5,50-Dungeon.hero.petLevel);
		}
		return damage;
	}

}