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
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ManySkeletonSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ManySkeleton extends Mob {

	{
		spriteClass = ManySkeletonSprite.class;

		HP = HT = 100;
		evadeSkill = 5;

		EXP = 10;
		maxLvl = 30;

		flying = true;

		properties.add(Property.UNDEAD);
	}

	private static final float SPLIT_DELAY = 1f;


	@Override
	public int damageRoll() {
		return Random.NormalIntRange(4, 7);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		if (HP >= damage + 2) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			int[] neighbours = { pos + 1, pos - 1, pos + Level.getWidth(),
					pos - Level.getWidth() };
			for (int n : neighbours) {
				if (passable[n] && Actor.findChar(n) == null) {
					candidates.add(n);
				}
			}

			if (candidates.size() > 0) {

				SommonSkeleton clone = new SommonSkeleton();
				clone.HP = (HP - damage) / 2;
				clone.pos = Random.element(candidates);
				clone.state = clone.HUNTING;
				clone.sumcopy = true;

				if (Dungeon.level.map[clone.pos] == Terrain.DOOR) {
					Door.enter(clone.pos);
				}

				GameScene.add(clone, SPLIT_DELAY);
				Actor.addDelayed(new Pushing(clone, pos, clone.pos), -1);

				HP -= clone.HP;
			}
		}

		return damage;
	}

	@Override
	public int hitSkill(Char target) {
		return 30+adj(01);
	}

	@Override
	public void die(Object cause) {
		SommonSkeleton.spawnAround(this.pos);
		super.die(cause);
	}

	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static ManySkeleton spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			ManySkeleton w = new ManySkeleton();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, 1f);
			return w;
  			
		} else {
			return null;
		}
	}	

}
