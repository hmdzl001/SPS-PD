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
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.CompleteFood;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.staplefood.NormalRation;
import com.hmdzl.spspd.sprites.DwarfBoySprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class DwarfBoy extends PET {
	
	{
		//name = "dwarfboy";
		spriteClass = DwarfBoySprite.class;
		//flying=false;
		state = HUNTING;

		type = 206;
		cooldown=50;
		oldcooldown=30;
		properties.add(Property.DWARF);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof CompleteFood;
	}

	@Override
	public void updateStats()  {
		evadeSkill = (int)(hero.petLevel*1.5);
		HT = 150 + 2*hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}

	@Override
	public Item SupercreateLoot(){
		return new NormalRation();
	}

	@Override
	public int drRoll(){
		return Random.IntRange(hero.petLevel,(int)(hero.petLevel*1.5));
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 5;
	}
	
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5)==0) {
			Buff.affect(enemy,Vertigo.class,6f);
		}
       
		return damage;
	}
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		enemy.damage(hero.petLevel, this);
		cooldown--;
		if (cooldown == 0) {
			enemy.damage(hero.petLevel*2, this);
			cooldown = Math.max(10,30 - hero.petLevel);
		} 

		return damage;
	}	

}