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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.potions.PotionOfHealing;
import com.hmdzl.spspd.items.weapon.melee.Dagger;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ScorpioSprite;
import com.watabou.utils.Random;

public class Scorpio extends Mob {

	{
		spriteClass = ScorpioSprite.class;

		HP = HT = 180+(adj(0)*Random.NormalIntRange(1, 3));
		evadeSkill = 24+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 17;
		maxLvl = 35;

		loot = new PotionOfHealing();
		lootChance = 0.2f;
		
		lootOther = new MysteryMeat();
		lootChanceOther = 0.30f; // by default, see die()
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return new Dagger();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 52+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 36+adj(1);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 20);
	}

    @Override
	protected boolean canAttack(Char enemy) {
		if (buff(Locked.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return Floor.distance( pos, enemy.pos ) <= 2 ;
	}
	

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.prolong(enemy, Cripple.class, Cripple.DURATION);
		}

		return damage;
	}

	@Override
	protected boolean getCloser(int target) {
		if (state == HUNTING) {
			return enemySeen && getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected Item createLoot() {
		// 5/count+5 total chance of getting healing, failing the 2nd roll drops
		// mystery meat instead.
		if (Random.Int(5 ) <= 24) {
			return (Item) loot;
		} else {
			return new MysteryMeat();
		}
	}

	{
		
		resistances.add(Poison.class);
	}

}
