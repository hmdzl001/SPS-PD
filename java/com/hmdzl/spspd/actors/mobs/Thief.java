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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.vegetable.NutVegetable;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ThiefSprite;
import com.watabou.utils.Random;

public class Thief extends Mob {

	public Item item;

	{
		spriteClass = ThiefSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 8+adj(0);

		EXP = 5;
        maxLvl = 20;
		
		loot =  new NutVegetable();
		lootChance = 0.1f;
		
		lootOther = Generator.Category.BERRY;
		lootChanceOther = 0.05f; // by default, see die()

		FLEEING = new Fleeing();
		
		properties.add(Property.GOBLIN);
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.RANGEWEAPON);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 7+adj(0));
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Locked.class) == null) {
			state = HUNTING;
		}
		return result;
	}	
	
	@Override
	public void die(Object cause) {
		super.die(cause);
	}

	@Override
	protected Item createLoot() {
			return super.createLoot();
	}

	@Override
	public int hitSkill(Char target) {
		return 12;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 3);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Locked.class,20f);
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (state == FLEEING) {
			Dungeon.level.drop(new Gold(), pos).sprite.drop();
		}

		return damage;
	}
	
	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Mob.class, "rage"));
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
