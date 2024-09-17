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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Recharging;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class RustybladeCat extends Item {

	{
		image = ItemSpriteSheet.RUSTY_CAT;
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
			for (int i : Floor.NEIGHBOURS8)
				if (Floor.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);

			if (!Floor.pit[newCell] && activate) {
				if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
					ButterCat2.spawnAt(newCell);
				} else ButterCat.spawnAt(newCell);
			} else {
				Dungeon.depth.drop(this, newCell).sprite.drop(cell);
			}

		} else if (!Floor.pit[cell] && activate) {
			if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
				ButterCat2.spawnAt(cell);
			} else
				ButterCat.spawnAt(cell);
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

	public static class ButterCat extends Mob {

		{
			//spriteClass = ButterCatSprite.class;
			spriteClass = ErrorSprite.class;
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
			damage(1, this,3);
			
		    if (Floor.adjacent(pos, Dungeon.hero.pos)){
			Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f,	1);
            Dungeon.hero.belongings.relord();
		    Buff.prolong(Dungeon.hero,Recharging.class,2f);
		    }
		
			return super.act();
		}

		@Override
		protected boolean getCloser(int target) {
			return super.getCloser(Dungeon.hero.pos);
		}

		@Override
		protected Char chooseEnemy() {
			return null;
		}


		public static ButterCat spawnAt(int pos) {

			ButterCat b = new ButterCat();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		@Override
		public void add(Buff buff) {
			//in other words, can't be directly affected by buffs/debuffs.
		}

	}

	public static class ButterCat2 extends Mob {

		{
			spriteClass = ErrorSprite.class;
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
			damage(1, this,3);
		if (Floor.adjacent(pos, Dungeon.hero.pos)){
			Dungeon.hero.sprite.emitter().start(Speck.factory(Speck.UP), 0.4f,	1);
            Dungeon.hero.belongings.relord();
		    Buff.prolong(Dungeon.hero,Recharging.class,2f);
			Buff.affect(Dungeon.hero,ShieldArmor.class).level(30);
			
		}
			return super.act();
		}

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Floor.distance(target, Dungeon.hero.pos) > 6)
				this.target = target = Dungeon.hero.pos;
			return super.getCloser(target);
		}

		@Override
		protected Char chooseEnemy() {

			if (enemy == null || !enemy.isAlive()) {
				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.depth.mobs) {
					if (mob.hostile && Floor.fieldOfView[mob.pos]) {
						enemies.add(mob);
					}
				}

				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}

			return enemy;
		}


		public static ButterCat2 spawnAt(int pos) {

			ButterCat2 b = new ButterCat2();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		@Override
		protected boolean canAttack(Char enemy) {

			return Floor.distance(pos, enemy.pos) <= 4;

		}

		@Override
		public int hitSkill(Char target) {
			return 60 + (Dungeon.dungeondepth);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(Dungeon.dungeondepth + 12, Dungeon.dungeondepth + 25);
		}
		
		@Override
		public void add(Buff buff) {
			//in other words, can't be directly affected by buffs/debuffs.
		}
	}
}
