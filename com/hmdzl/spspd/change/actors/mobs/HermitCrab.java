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
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.keys.GoldenSkeletonKey;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.HermitCrabSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class HermitCrab extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";
	private static final String TXT_SHELL_ABSORB = "Hermit Crab absorbed the attack in its shell.";
	private static final String TXT_SHELL_CHARGE = "Lightning Shell charged by %s.";

	{
		spriteClass = HermitCrabSprite.class;

		HP = HT = 200;
		defenseSkill = 22;

		EXP = 6;

		loot = Generator.Category.BERRY;
		lootChance = 0.33f;

		properties.add(Property.BEAST);
		properties.add(Property.BOSS);

	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(25, 50);
	}

	@Override
	public int attackSkill(Char target) {
		return 25;
	}

	@Override
	public int dr() {
		return 4;
	}

	@Override
	public void damage(int dmg, Object src) {
		
		if (dmg>HT/4 && src != LightningTrap.LIGHTNING){
            for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Shell && mob.isAlive()){
				Dungeon.shellCharge+=dmg;
				GLog.n(Messages.get(this, "absorb"));
				GLog.n(Messages.get(this, "charge", dmg));
				dmg=1;
				
			    }			
			}
		}			
		super.damage(dmg, src);
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
				((HermitCrabSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(15, 30);
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
	public void call() {
		next();
	}

	@Override
	public void die(Object cause) {
		super.die(cause);  
		if(Random.Int(1)==0){
		Dungeon.level.drop(new GoldenSkeletonKey(0), pos).sprite.drop();	
		}
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
