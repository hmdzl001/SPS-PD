/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.summon;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bombs.BuildBomb;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CoconutSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class CallCoconut extends Item {

	{
		//name = "call coconut";
		image = ItemSpriteSheet.ANCIENTKEY;
		defaultAction = AC_ACTIVE;
		stackable = true;
	}


	private static boolean activate = false;

	private static final String AC_ACTIVE = "ACTIVE";


	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ACTIVE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_ACTIVE)) {
			activate = true;
			action = AC_THROW;
		} else
			activate = false;

		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {

		if (Actor.findChar(cell) != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Level.NEIGHBOURS8)
				if (Level.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);

			if (!Level.pit[newCell] && activate) {
				if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
					EXcococat.spawnAt(newCell);
				} else Scococat.spawnAt(newCell);
			} else {
				Dungeon.level.drop(this, newCell).sprite.drop(cell);
			}

		} else if (!Level.pit[cell] && activate) {
			if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
				EXcococat.spawnAt(cell);
			} else
				Scococat.spawnAt(cell);
		} else {

			super.onThrow(cell);
		}

	}


	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 100 * quantity;
	}

	public static class Scococat extends Mob {

		{
			spriteClass = CoconutSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 200;
			evadeSkill = 0;
			ally=true;
			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		protected boolean act() {
			damage(1, this);

			return super.act();
		}

		@Override
		protected Char chooseEnemy() {

			if (enemy == null || !enemy.isAlive()) {
				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]) {
						enemies.add(mob);
					}
				}

				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}

			return enemy;
		}


		public static Scococat spawnAt(int pos) {

			Scococat b = new Scococat();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		@Override
		protected boolean canAttack(Char enemy) {

			return Dungeon.level.distance(pos, enemy.pos) <= 4;

		}

		@Override
		public int hitSkill(Char target) {
			return 40 + (Dungeon.depth);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(Dungeon.depth + 10, Dungeon.depth + 16);
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(10) == 1) {
				BuildBomb bomb = new BuildBomb();
				bomb.explode(enemy.pos);
			}

			return damage;
		}

		@Override
		public void add(Buff buff) {
			//in other words, can't be directly affected by buffs/debuffs.
		}

	}

	public static class EXcococat extends Mob {

		{
			spriteClass = CoconutSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 400;
			evadeSkill = 20;
			ally=true;

			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		protected boolean act() {
			damage(1, this);

			return super.act();
		}

		@Override
		protected Char chooseEnemy() {

			if (enemy == null || !enemy.isAlive()) {
				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]) {
						enemies.add(mob);
					}
				}

				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}

			return enemy;
		}


		public static EXcococat spawnAt(int pos) {

			EXcococat b = new EXcococat();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		@Override
		protected boolean canAttack(Char enemy) {

			return Dungeon.level.distance(pos, enemy.pos) <= 4;

		}

		@Override
		public int hitSkill(Char target) {
			return 60 + (Dungeon.depth);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(Dungeon.depth + 20, Dungeon.depth + 32);
		}


		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(5) == 1) {
				BuildBomb bomb = new BuildBomb();
				bomb.explode(enemy.pos);
			}

			return damage;
		}

		@Override
		public void add(Buff buff) {
			//in other words, can't be directly affected by buffs/debuffs.
		}
	}
}
