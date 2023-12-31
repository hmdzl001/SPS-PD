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
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SkeletonSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SommonSkeleton extends Mob {

	protected static final float SPAWN_DELAY = 2f;
	protected static final String LEVEL = "level";

	protected int level;
	{
		spriteClass = SkeletonSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);
		baseSpeed = 0.8f;
		
		properties.add(Property.UNDEAD);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 22+adj(0));
	}

		
	@Override
	public void die(Object cause) {
		super.die(cause);
	}

	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 4);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt(LEVEL);
		adjustStats(level);
	}

	public void adjustStats(int level) {
		this.level = level;
		evadeSkill = hitSkill(null) * 5;
		enemySeen = true;
	}

	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static SommonSkeleton spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			SommonSkeleton w = new SommonSkeleton();
			w.adjustStats(Dungeon.dungeondepth);
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			w.sprite.alpha(0);
			w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

			w.sprite.emitter().burst(ShadowParticle.CURSE, 5);

			return w;
  			
		} else {
			return null;
		}
	}	


	//{
		//IMMUNITIES.add(EnchantmentDark.class);
	//}
}
