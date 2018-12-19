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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.TenguKey;
import com.hmdzl.spspd.change.items.TreasureMap;
import com.hmdzl.spspd.change.items.challengelists.SewerChallenge;
import com.hmdzl.spspd.change.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.change.items.reward.SewerReward;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfSacrifice;
import com.hmdzl.spspd.change.items.weapon.missiles.ForestDart;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.GnollArcherSprite;
import com.hmdzl.spspd.change.utils.GLog;

import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Random;

public class GnollArcher extends Mob {

	private static final String TXT_KILLCOUNT = "Gnoll Archer Kill Count: %s";
	{
		//name = "gnollarcher";
		spriteClass = GnollArcherSprite.class;

		HP = HT = 25 + Statistics.archersKilled;
		evadeSkill = 5;

		EXP = 1;
			
		baseSpeed = 0.9f;

		state = WANDERING;
		
		properties.add(Property.GNOLL);

	}
	
	@Override
	public int hitSkill(Char target) {
		return 30;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return !Level.adjacent(pos, enemy.pos) && attack.collisionPos == enemy.pos;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1+Math.round(Statistics.archersKilled/10), 8+Math.round(Statistics.archersKilled/5));
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	protected boolean getCloser(int target) {
		if (Level.adjacent(pos, enemy.pos)) {
			return getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if (Dungeon.depth > 25) {
			Dungeon.level.drop(new ForestDart(3), pos).sprite.drop();
		}

		Statistics.archersKilled++;
		GLog.w(Messages.get(Mob.class, "killcount", Statistics.archersKilled));


		if (!Dungeon.limitedDrops.sewerkey.dropped() && Dungeon.depth < 27) {
			Dungeon.limitedDrops.sewerkey.drop();
			Dungeon.level.drop(new SewerChallenge(), pos).sprite.drop();
			explodeDew(pos);
		} else {
			explodeDew(pos);
		}

		if (Statistics.archersKilled == 25) {
			Dungeon.limitedDrops.treasuremap.drop();
			Dungeon.level.drop(new TreasureMap(), pos).sprite.drop();
		}

		if (Statistics.archersKilled == 50) {
			Dungeon.level.drop(new ScrollOfSacrifice(), pos).sprite.drop();
		}

		if (Statistics.archersKilled == 100) {
			Dungeon.level.drop(new SewerReward(), pos).sprite.drop();
		}

		if (Statistics.goldThievesKilled > 99 && Statistics.skeletonsKilled > 99
				&& Statistics.albinoPiranhasKilled > 99 && Statistics.archersKilled == 100 ) {
			Dungeon.level.drop(new GoldenNut(), pos).sprite.drop();
		}
	}
}