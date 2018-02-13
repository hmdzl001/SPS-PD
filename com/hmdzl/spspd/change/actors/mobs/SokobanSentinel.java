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
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Journal;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.HallsKey;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.Weapon.Enchantment;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.items.weapon.enchantments.Leech;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.SentinelSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SokobanSentinel extends Mob {	

	{
		spriteClass = SentinelSprite.class;
		
		baseSpeed = 0.5f;

		EXP = 18;
		state = PASSIVE;
		
		properties.add(Property.MECH);
	}

	private Weapon weapon;

	public SokobanSentinel() {
		super();

		do {
			weapon = (Weapon) Generator.random(Generator.Category.WEAPON);
		} while (!(weapon instanceof MeleeWeapon) || weapon.level < 3);

		weapon.identify();
		weapon.enchant(Enchantment.random());
		weapon.upgrade();
		weapon.upgrade();
		weapon.upgrade();
		weapon.upgrade();
		weapon.upgrade();
		
		
		HP = HT = 500;
		defenseSkill = 40;
	}

	private static final String WEAPON = "weapon";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(WEAPON, weapon);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		weapon = (Weapon) bundle.get(WEAPON);
	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(weapon.MIN, weapon.MAX);
	}

	@Override
	public int attackSkill(Char target) {
		return 40;
	}

	@Override
	protected float attackDelay() {
		return weapon.DLY;
	}

	@Override
	public int dr() {
		return Dungeon.depth;
	}

	@Override
	public void damage(int dmg, Object src) {

		if (state == PASSIVE) {
			state = HUNTING;
		}

		super.damage(dmg, src);
	}
	
	@Override
	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Level.passable,
				Level.fieldOfView);
		if (step != -1 && !Level.avoid[step]) {
			move(step);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected boolean act() {
		
				
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
			} else if (state == this.PASSIVE
					&& !(enemy.isAlive() && Level.fieldOfView[enemy.pos] && enemy.invisible <= 0)){
				    state = this.HUNTING;				
			}

			return super.act();
		
	}		
	@Override
	public int attackProc(Char enemy, int damage) {
		weapon.proc(this, enemy, damage);
		return damage;
	}

	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public void die(Object cause) {
		//Dungeon.level.drop(weapon, pos).sprite.drop();		
		super.die(cause);
	}

	@Override
	public String description() {
		return Messages.get(this, "desc", weapon.name());
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		IMMUNITIES.add(Leech.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
