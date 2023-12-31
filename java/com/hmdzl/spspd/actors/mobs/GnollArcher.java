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
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.items.SacrificeBook;
import com.hmdzl.spspd.items.TreasureMap;
import com.hmdzl.spspd.items.challengelists.SewerChallenge;
import com.hmdzl.spspd.items.food.completefood.GoldenNut;
import com.hmdzl.spspd.items.reward.SewerReward;
import com.hmdzl.spspd.items.weapon.missiles.arrows.NutFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.GnollArcherSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

public class GnollArcher extends Mob {
	{
		//name = "gnollarcher";
		spriteClass = GnollArcherSprite.class;

		HP = HT = 25 + Statistics.archersKilled;
		evadeSkill = 5;

		EXP = 1;
			
		baseSpeed = 0.9f;

		state = WANDERING;
		
		properties.add(Property.ORC);

	}
	
	@Override
	public int hitSkill(Char target) {
		return 30;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		if (buff(Locked.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		
		return !Floor.adjacent(pos, enemy.pos) && attack.collisionPos == enemy.pos;
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
		if (Floor.adjacent(pos, enemy.pos)) {
			return getFurther(target);
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if (Dungeon.dungeondepth > 25) {
			Dungeon.depth.drop(new NutFruit(3), pos).sprite.drop();
		}

		Statistics.archersKilled++;
		GLog.w(Messages.get(Mob.class, "killcount", Statistics.archersKilled));


		if ( Dungeon.dungeondepth < 27) {
			Dungeon.depth.drop(new SewerChallenge(), pos).sprite.drop();
			explodeDew(pos);
		} else {
			explodeDew(pos);
		}

		if (Statistics.archersKilled == 25) {
			Dungeon.depth.drop(new TreasureMap(), pos).sprite.drop();
		}

		if (Statistics.archersKilled == 50) {
			Dungeon.depth.drop(new SacrificeBook(), pos).sprite.drop();
		}

		if (Statistics.archersKilled == 100) {
			Dungeon.depth.drop(new SewerReward(), pos).sprite.drop();
		}

		if (Statistics.goldThievesKilled > 99 && Statistics.skeletonsKilled > 99
				&& Statistics.albinoPiranhasKilled > 99 && Statistics.archersKilled == 100 ) {
			Dungeon.depth.drop(new GoldenNut(), pos).sprite.drop();
		}
	}
}