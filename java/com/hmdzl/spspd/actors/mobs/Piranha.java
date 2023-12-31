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
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.challengelists.CaveChallenge;
import com.hmdzl.spspd.items.food.meatfood.Meat;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.HugeShuriken;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.PiranhaSprite;
import com.watabou.utils.Random;

public class Piranha extends Mob {

	{
		spriteClass = PiranhaSprite.class;

		baseSpeed = 1.5f;

		EXP = 5;

		loot = new Meat();
		lootChance = 1f;			
		
        properties.add(Property.FISHER);
	}

	public Piranha() {
		super();

		HP = HT = 40 + Dungeon.dungeondepth * 5;
		evadeSkill = 10 + Dungeon.dungeondepth * 2;
	}

	@Override
	public Item SupercreateLoot(){
		return new HugeShuriken();
	}

	@Override
	protected boolean act() {
		if (!Floor.water[pos]) {
			die(null);
			return true;
		} else {
			// this causes pirahna to move away when a door is closed on them.
			Dungeon.depth.updateFieldOfView(this);
			enemy = chooseEnemy();
			if (state == this.HUNTING
					&& !(enemy.isAlive() && Floor.fieldOfView[enemy.pos] && enemy.invisible <= 0)) {
				state = this.WANDERING;
				int oldPos = pos;
				int i = 0;
				do {
					i++;
					target = Dungeon.depth.randomDestination();
					if (i == 100)
						return true;
				} while (!getCloser(target));
				moveSprite(oldPos, pos);
				return true;
			}

			return super.act();
		}
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.dungeondepth, 4 + Dungeon.dungeondepth * 2);
	}

	@Override
	public int hitSkill(Char target) {
		return 20 + Dungeon.dungeondepth * 2;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, Dungeon.dungeondepth);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		
		if (!Dungeon.LimitedDrops.caveskey.dropped() && Statistics.deepestFloor > 10) {
			Dungeon.LimitedDrops.caveskey.drop();
			Dungeon.depth.drop(new CaveChallenge(), pos).sprite.drop();
		}

	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Floor.water,
				Floor.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Floor.water,
				Floor.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	{
		immunities.add(Burning.class);
		immunities.add(Paralysis.class);
		immunities.add(ToxicGas.class);
		immunities.add(Roots.class);
		immunities.add(Frost.class);
	}
	
}
