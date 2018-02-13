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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.items.CavesKey;
import com.hmdzl.spspd.change.items.food.Meat;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.PiranhaSprite;
import com.watabou.utils.Random;

public class Piranha extends Mob {

	{
		spriteClass = PiranhaSprite.class;

		baseSpeed = 1.5f;

		EXP = 5;

        properties.add(Property.BEAST);
	}

	public Piranha() {
		super();

		HP = HT = 40 + Dungeon.depth * 5;
		defenseSkill = 10 + Dungeon.depth * 2;
	}

	@Override
	protected boolean act() {
		if (!Level.water[pos]) {
			die(null);
			return true;
		} else {
			// this causes pirahna to move away when a door is closed on them.
			Dungeon.level.updateFieldOfView(this);
			enemy = chooseEnemy();
			if (state == this.HUNTING
					&& !(enemy.isAlive() && Level.fieldOfView[enemy.pos] && enemy.invisible <= 0)) {
				state = this.WANDERING;
				int oldPos = pos;
				int i = 0;
				do {
					i++;
					target = Dungeon.level.randomDestination();
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
		return Random.NormalIntRange(Dungeon.depth, 4 + Dungeon.depth * 2);
	}

	@Override
	public int attackSkill(Char target) {
		return 20 + Dungeon.depth * 2;
	}

	@Override
	public int dr() {
		return Dungeon.depth;
	}

	@Override
	public void die(Object cause) {
		Dungeon.level.drop(new Meat(), pos).sprite.drop();
		
		if (!Dungeon.limitedDrops.caveskey.dropped() && Statistics.deepestFloor > 10) {
			Dungeon.limitedDrops.caveskey.drop();
			Dungeon.level.drop(new CavesKey(), pos).sprite.drop();
			explodeDew(pos);				
		}
		
		super.die(cause);

		Statistics.piranhasKilled++;
		Badges.validatePiranhasKilled();
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

		int step = Dungeon.findPath(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Roots.class);
		IMMUNITIES.add(Frost.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
