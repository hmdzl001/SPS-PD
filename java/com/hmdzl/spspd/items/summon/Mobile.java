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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ExMobileSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.MobileSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Mobile extends Item {

	{
		//name = "mobile key";
		image = ItemSpriteSheet.MOBS;
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
			   	 if (Dungeon.hero.subClass == HeroSubClass.LEADER){
					 EXMobileSatellite.spawnAt(newCell);
				 } else MobileSatellite.spawnAt(newCell);
			   } else {
			   Dungeon.depth.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Floor.pit[cell] && activate) {
			if (Dungeon.hero.subClass == HeroSubClass.LEADER){
				EXMobileSatellite.spawnAt(cell);
			} else MobileSatellite.spawnAt(cell);
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

	public static class MobileSatellite extends Mob {

		{
			spriteClass = MobileSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT= 100;
			evadeSkill = 0;
			baseSpeed=2;
			ally=true;
			properties.add(Property.MECH);
		}

		private static final float SPAWN_DELAY = 1f;

	@Override
	protected float attackDelay() {
		return 0.33f;
	}		
		
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



		public static MobileSatellite spawnAt(int pos) {

			MobileSatellite b = new MobileSatellite();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}


		@Override
		protected boolean canAttack(Char enemy) {
            return Floor.distance( pos, enemy.pos ) <= 6 ;
		}

		@Override
		public int hitSkill(Char target) {
			return 30+(Dungeon.dungeondepth);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(HP/8+5, HP/2+10);
		}
	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}			
	}
	public static class EXMobileSatellite extends Mob {

		{
			spriteClass = ExMobileSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 300;
			evadeSkill = 35;
			baseSpeed=2;
			ally=true;
			properties.add(Property.MECH);
		}

		private Ballistica beam;

		private static final float SPAWN_DELAY = 1f;

		@Override
		protected float attackDelay() {
			return 0.25f;
		}

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


		public static EXMobileSatellite spawnAt(int pos) {

			EXMobileSatellite b = new EXMobileSatellite();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}

		@Override
		protected boolean canAttack(Char enemy) {
			return Floor.distance(pos, enemy.pos) <= 6;
		}

		@Override
		public int hitSkill(Char target) {
			return 60 + (Dungeon.dungeondepth);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(HP / 6 + 10, HP / 2 + 20);
		}

		@Override
		public void add(Buff buff) {
			//in other words, can't be directly affected by buffs/debuffs.
		}

	}
}
