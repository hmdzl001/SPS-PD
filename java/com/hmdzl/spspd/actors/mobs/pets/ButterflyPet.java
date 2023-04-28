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
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Garbage;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.food.fruit.Fruit;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ButterflyPetSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class ButterflyPet extends PET {
	
	{
		//name = "Butterfly";
		spriteClass = ButterflyPetSprite.class;
        //flying=true;
		state = HUNTING;
		type = 304;
        cooldown=50;
		oldcooldown=30;
		properties.add(Property.BEAST);
	}
	
	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof Fruit;
	}

	@Override
	public void updateStats()  {
		evadeSkill =  hero.petLevel;
		HT = 150 + 2* hero.petLevel;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange((int)(5+hero.petLevel*0.5), (int)(5+hero.petLevel*1.5));
	}

	@Override
	public Item SupercreateLoot(){
		return new Garbage();
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
	protected boolean act() {		
			
		if (Level.adjacent(pos, hero.pos)){
			cooldown --;
			if (cooldown <= 0){
			hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,	1);
			hero.sprite.showStatus(CharSprite.POSITIVE,Integer.toString(5));
			hero.HP = Math.min(hero.HT, hero.HP+5);
			cooldown = Math.max(15,35-hero.petLevel);
			}

		}
		return super.act();
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (cooldown > 0){
			cooldown--;
		}
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Blindness.class,hero.petLevel*2);
		}

		return damage;
	}	
	
}