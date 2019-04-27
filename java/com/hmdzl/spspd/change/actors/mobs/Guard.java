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

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.effects.Chains;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.GuardSprite;

import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;



public class Guard extends Mob {

	private static final String TXT_HERO_KILLED = "You were killed by the explosion of bones...";

	private boolean chainsUsed = false;
	
	{
		spriteClass = GuardSprite.class;

		HP = HT = 75+(adj(0)*Random.NormalIntRange(3, 7));
		evadeSkill = 9+adj(1);

		EXP = 5;
		maxLvl = 10;

		loot = Generator.Category.ARMOR;
		lootChance = 0.2f;
		
		properties.add(Property.HUMAN);
		//HUNTING = new Hunting();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12+adj(0), 20+adj(3));
	}

	@Override
	protected float attackDelay() {
		return 1.2f;
	}

	@Override
	protected boolean act() {
		Dungeon.level.updateFieldOfView( this );

		if (state == HUNTING &&
				paralysed <= 0 &&
				enemy != null &&
				enemy.invisible == 0 &&
				Level.fieldOfView[enemy.pos] &&
				Level.distance( pos, enemy.pos ) < 5 && !Level.adjacent( pos, enemy.pos ) &&
				Random.Int(3) == 0 && (buff(Silent.class) == null) &&

				chain(enemy.pos)) {

			return false;

		} else {
			return super.act();
		}
	}
	private boolean chain(int target){
		if (chainsUsed || enemy.properties().contains(Property.IMMOVABLE))
			return false;

		Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

		if (chain.collisionPos != enemy.pos || Level.pit[chain.path.get(1)])
			return false;
		else {
			int newPos = -1;
			for (int i : chain.subPath(1, chain.dist)){
				if (!Level.solid[i] && Actor.findChar(i) == null){
					newPos = i;
					break;
				}
			}

			if (newPos == -1){
				return false;
			} else {
				final int newPosFinal = newPos;
				yell( Messages.get(this, "scorpion") );
				sprite.parent.add(new Chains(pos, enemy.pos, new Callback() {
					public void call() {
						Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal), -1);
						enemy.pos = newPosFinal;
						Dungeon.level.press(newPosFinal, enemy);
						Cripple.prolong(enemy, Cripple.class, 4f);
						if (enemy == Dungeon.hero) {
							Dungeon.hero.interrupt();
							Dungeon.observe();
						}
						next();
					}
				}));
			}
		}
		chainsUsed = true;
		return true;
	}
	
	
	@Override
	public void die(Object cause) {

		super.die(cause);

		boolean heroKilled = false;
		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				int damage = Math.max(0,
						Random.NormalIntRange(3, 8) - Random.IntRange(0, ch.drRoll() / 2));
				ch.damage(damage, this);
				if (ch == Dungeon.hero && !ch.isAlive()) {
					heroKilled = true;
				}
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BONES);
		}

		if (heroKilled) {
			Dungeon.fail(Messages.format(ResultDescriptions.MOB));
			//GLog.n(Messages.get(this, "kill"));
		}
	}

	@Override
	protected Item createLoot() {
		Item loot = Generator.random(Generator.Category.ARMOR);
		for (int i = 0; i < 2; i++) {
			Item l = Generator.random(Generator.Category.ARMOR);
			if (l.level < loot.level) {
				loot = l;
			}
		}
		return loot;
	}

	@Override
	public int hitSkill(Char target) {
		return 12+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}
	
	private final String CHAINSUSED = "chainsused";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHAINSUSED, chainsUsed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		chainsUsed = bundle.getBoolean(CHAINSUSED);
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(EnchantmentDark.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	/*private class Hunting extends Mob.Hunting{
		@Override
		public boolean act( boolean enemyInFOV, boolean justAlerted ) {
			enemySeen = enemyInFOV;
			
			if (!chainsUsed
					&& enemyInFOV
					&& !isCharmedBy( enemy )
					&& !canAttack( enemy )
					&& Dungeon.level.distance( pos, enemy.pos ) < 5
					&& Random.Int(3) == 0
					
					&& chain(enemy.pos)){
				return false;
			} else {
				return super.act( enemyInFOV, justAlerted );
			}
			
		}
	}	*/
}
