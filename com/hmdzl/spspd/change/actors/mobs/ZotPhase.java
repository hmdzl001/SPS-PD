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
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.food.Meat;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.JupitersWraith;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ShamanSprite;
import com.hmdzl.spspd.change.sprites.ZotPhaseSprite;
import com.hmdzl.spspd.change.sprites.ZotSprite;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ZotPhase extends Mob implements Callback{

	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		spriteClass = ZotPhaseSprite.class;

		HP = HT = 200;
		defenseSkill = 40;
		baseSpeed = 1.5f;

		EXP = 36;		

		loot = Generator.Category.SCROLL;
		lootChance = 0.33f;		
		
		properties.add(Property.UNKNOW);
		
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(115, 160+adj(1));
	}

	@Override
	public int attackSkill(Char target) {
		return 100+adj(0);
	}
	
	@Override
	protected float attackDelay() {
		return 0.5f;
	}


	@Override
	public int dr() {
		return 4;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((ZotPhaseSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(80+adj(0), 160+adj(3));
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, LightningTrap.LIGHTNING);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Messages.format(ResultDescriptions.MOB));
						//GLog.n(Messages.get(this, "kill"));
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}
	
	@Override
	public void damage(int dmg, Object src) {
		

		
		super.damage(dmg, src);
	}

	@Override
	public void call() {
		next();
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
