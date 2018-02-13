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

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.food.Meat;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.SpectralRatSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SpectralRat extends Mob  implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_SHADOWBOLT_KILLED = "%s's shadow bolt killed you...";	

	private static final float SPAWN_DELAY = 6f;

	{
		spriteClass = SpectralRatSprite.class;
		baseSpeed = 1.5f;
		viewDistance = 4;
		HP = HT = 80+(Dungeon.depth*Random.NormalIntRange(2, 5));
		defenseSkill = 2;
		
		EXP = 20;
		
		loot = Generator.Category.SCROLL;
		lootChance = 0.15f;
		
		properties.add(Property.DEMONIC);
		properties.add(Property.ELEMENT);

	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.depth/2, Dungeon.depth);
	}

	@Override
	public int attackSkill(Char target) {
		return 50;
	}

	@Override
	public int dr() {
		return 10;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((SpectralRatSprite) sprite).zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	private void zap() {
		spend(TIME_TO_ZAP);

		if (hit(this, enemy, true)) {
			if (enemy == Dungeon.hero && Random.Int(5) == 0) {
				Buff.prolong(enemy, Weakness.class, Weakness.duration(enemy));
			}

			int dmg = Random.Int(20, 45);
			enemy.damage(dmg, this);

			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail( Messages.format(ResultDescriptions.MOB));
				//GLog.n(Messages.get(this, "kill"));
			}
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}
	
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}
	
	public static void spawnAroundChance(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null && Random.Float() < 0.75f) {
				spawnAt(cell);
			}
		}
	}
	
	public static SpectralRat spawnAt(int pos) {
		
        SpectralRat b = new SpectralRat();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
}
