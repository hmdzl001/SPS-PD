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
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.keys.GoldenSkeletonKey;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.HermitCrabSprite;
import com.hmdzl.spspd.utils.GLog;
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
		evadeSkill = 22;

		EXP = 60;

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
	public int hitSkill(Char target) {
		return 25;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 4);
	}

	@Override
	public void damage(int dmg, Object src) {
		
		if (dmg>HT/4 && src != this){
            for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Shell && mob.isAlive()){
				GLog.n(Messages.get(this, "absorb"));
				dmg=1;
				}
			}
		}			
		super.damage(dmg, src);
	}


	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
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
				sprite.zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(15, 30);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, this);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
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

	{
		resistances.add(LightningTrap.Electricity.class);
	}
}
