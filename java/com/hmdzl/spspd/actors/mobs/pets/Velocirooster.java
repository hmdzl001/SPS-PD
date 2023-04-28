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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.items.weapon.melee.special.SJRBMusic;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.VelociroosterSprite;
import com.watabou.utils.Random;

public class Velocirooster extends PET {
	
	{
		//name = "velocirooster";
		spriteClass = VelociroosterSprite.class;       
		//flying=false;
		state = HUNTING;
		type = 404;
		baseSpeed = 1.5f;
		cooldown=50;
        oldcooldown = 10;
		properties.add(Property.BEAST);
		properties.add(Property.HUMAN);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Plant.Seed ||
				item instanceof Vegetable;
	}


	@Override
	public void updateStats()  {
		HT = 50 + Dungeon.hero.petLevel*10;
		evadeSkill = 10 + Dungeon.hero.petLevel;
	}

	@Override
	public Item SupercreateLoot(){
		return new SJRBMusic();
	}

	@Override
	public int drRoll(){
		return Random.Int(0,Dungeon.hero.petLevel);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 10;
	}


	@Override
	public int damageRoll() {
		
		int dmg=0;
		if (cooldown==0){
			dmg=Random.NormalIntRange((5+Dungeon.hero.petLevel)*5/2, (5+Dungeon.hero.petLevel*3)*2);
		} else {
			dmg=Random.NormalIntRange((5+Dungeon.hero.petLevel), (5+Dungeon.hero.petLevel*3)) ;
		}
		return dmg;
			
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(4) == 0){
			damage *= 1.2;
		}
		if (cooldown > 0) cooldown --;
		if (cooldown==0) {
			this.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "yell1"));
			Buff.affect(enemy,Charm.class,5f).object = this.id();
			cooldown=Math.max(5,30-Dungeon.hero.petLevel);
		}
		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (cooldown > 0) cooldown --;
		if (cooldown == 0) {
			this.sprite.showStatus(CharSprite.NEUTRAL, Messages.get(this, "yell2"));
			Buff.affect(enemy,Charm.class,5f).object = this.id();
			cooldown = 5;
		}
		return damage;
	}
}