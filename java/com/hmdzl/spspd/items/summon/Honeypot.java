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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BeeSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.SteelBeeSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Honeypot extends Item {

	public static final String AC_SHATTER = "SHATTER";

	{
		//name = "honeypot";
		image = ItemSpriteSheet.HONEYPOT;
		defaultAction = AC_THROW;
		stackable = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_SHATTER);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_SHATTER)) {

			hero.sprite.zap(hero.pos);

			detach(hero.belongings.backpack);

			shatter(hero, hero.pos).collect();

			hero.next();

		} else {
			super.execute(hero, action);
		}
	}

	@Override
	protected void onThrow(int cell) {
		if (Level.pit[cell]) {
			super.onThrow(cell);
		} else {
			Dungeon.level.drop(shatter(null, cell), cell);
		}
	}

	public Item shatter(Char owner, int pos) {

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			Splash.at(pos, 0xffd500, 5);
		}

		int newPos = pos;
		if (Actor.findChar(pos) != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS4) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
		}

		if (newPos != -1 ) {
			if (Dungeon.hero.subClass == HeroSubClass.LEADER ){
				SteelBee bee = new SteelBee();
				bee.spawn(Dungeon.depth);
				bee.HP = bee.HT;
				bee.pos = newPos;

				GameScene.add(bee);
				Actor.addDelayed(new Pushing(bee, pos, newPos), -1f);

				bee.sprite.alpha(0);
				bee.sprite.parent.add(new AlphaTweener(bee.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);
				return new ShatteredPot();
			} else {
				Bee bee = new Bee();
				bee.spawn(Dungeon.depth);
				bee.setPotInfo(pos, owner);
				bee.HP = bee.HT;
				bee.pos = newPos;

				GameScene.add(bee);
				Actor.addDelayed(new Pushing(bee, pos, newPos), -1f);

				bee.sprite.alpha(0);
				bee.sprite.parent.add(new AlphaTweener(bee.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);
				return new ShatteredPot().setBee(bee);
			}
		} else {
			return this;
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
		return 30 * quantity;
	}

	// The bee's broken 'home', all this item does is let its bee know where it
	// is, and who owns it (if anyone).
	public static class ShatteredPot extends Item {

		{
			//name = "shattered honeypot";
			image = ItemSpriteSheet.SHATTPOT;
			stackable = false;
		}

		private int myBee;
		private int beeDepth;

		public Item setBee(Char bee) {
			myBee = bee.id();
			beeDepth = Dungeon.depth;
			return this;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			if (super.doPickUp(hero)) {
				setHolder(hero);
				return true;
			} else
				return false;
		}

		@Override
		public void doDrop(Hero hero) {
			super.doDrop(hero);
			updateBee(hero.pos, null);
		}

		@Override
		protected void onThrow(int cell) {
			super.onThrow(cell);
			updateBee(cell, null);
		}

		public void setHolder(Char holder) {
			updateBee(holder.pos, holder);
		}

		public void goAway() {
			updateBee(-1, null);
		}

		private void updateBee(int cell, Char holder) {
			// important, as ids are not unique between depths.
			if (Dungeon.depth != beeDepth)
				return;

			Bee bee = (Bee) Actor.findById(myBee);
			if (bee != null)
				bee.setPotInfo(cell, holder);
		}

		@Override
		public boolean isUpgradable() {
			return false;
		}

		@Override
		public boolean isIdentified() {
			return true;
		}

		private static final String MYBEE = "mybee";
		private static final String BEEDEPTH = "beedepth";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(MYBEE, myBee);
			bundle.put(BEEDEPTH, beeDepth);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			myBee = bundle.getInt(MYBEE);
			beeDepth = bundle.getInt(BEEDEPTH);
		}
	}
	public static class Bee extends Mob {

		{
			spriteClass = BeeSprite.class;

			viewDistance = 4;

			flying = true;
			state = WANDERING;

			properties.add(Property.BEAST);
		}

		private int level;

		// -1 refers to a pot that has gone missing.
		private int potPos;
		// -1 for no owner
		private int potHolder;

		private static final String LEVEL = "level";
		private static final String POTPOS = "potpos";
		private static final String POTHOLDER = "potholder";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(LEVEL, level);
			bundle.put(POTPOS, potPos);
			bundle.put(POTHOLDER, potHolder);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawn(bundle.getInt(LEVEL));
			potPos = bundle.getInt(POTPOS);
			potHolder = bundle.getInt(POTHOLDER);
		}

		public void spawn(int level) {
			this.level = Math.min(level,Statistics.deepestFloor);

			HT = (20 + level) * 4;
			evadeSkill = 9 + level;
		}

		public void setPotInfo(int potPos, Char potHolder) {
			this.potPos = potPos;
			if (potHolder == null)
				this.potHolder = -1;
			else
				this.potHolder = potHolder.id();
		}

		@Override
		public int hitSkill(Char target) {
			return evadeSkill;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(HT / 10, HT / 4);
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (enemy instanceof Mob) {
				((Mob) enemy).aggro(this);
			}
			return damage;
		}

		@Override
		protected Char chooseEnemy() {
			// if the pot is no longer present, target the hero
			if (potHolder == -1 && potPos == -1)
				return Dungeon.hero;

				// if something is holding the pot, target that
			else if (Actor.findById(potHolder) != null)
				return (Char) Actor.findById(potHolder);

				// if the pot is on the ground
			else {

				// if already targeting something, and that thing is still alive and
				// near the pot, keeping targeting it.
				if (enemy != null && enemy.isAlive()
						&& Level.distance(enemy.pos, potPos) <= 3)
					return enemy;

				// find all mobs near the pot
				HashSet<Char> enemies = new HashSet<Char>();
				for (Mob mob : Dungeon.level.mobs)
					if (!(mob instanceof Bee)
							&& Level.distance(mob.pos, potPos) <= 3
							&& (mob.hostile || mob.ally))
						enemies.add(mob);

				// pick one, if there are none, check if the hero is near the pot,
				// go for them, otherwise go for nothing.
				if (enemies.size() > 0)
					return Random.element(enemies);
				else
					return (Level.distance(Dungeon.hero.pos, potPos) <= 3) ? Dungeon.hero
							: null;
			}
		}

		@Override
		protected boolean getCloser(int target) {
			if (enemy != null && Actor.findById(potHolder) == enemy) {
				target = enemy.pos;
			} else if (potPos != -1
					&& (state == WANDERING || Level.distance(target, potPos) > 3))
				this.target = target = potPos;
			return super.getCloser(target);
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Amok.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	public static class SteelBee extends NPC {

		{
			//name = "golden bee";
			spriteClass = SteelBeeSprite.class;

			viewDistance = 6;
			ally=true;
			flying = true;
			state = WANDERING;
		}

		private int level;

		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(LEVEL, level);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			spawn(bundle.getInt(LEVEL));
		}

		public void spawn(int level) {
			this.level = Math.min(level,Statistics.deepestFloor);
			HT = (50 + level) * 4;
			evadeSkill = 15 + level;
		}
		@Override
		public int hitSkill(Char target) {
			return evadeSkill*2;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(HT / 8, HT / 2);
		}

		@Override
		protected boolean canAttack(Char enemy) {
			return super.canAttack(enemy);
		}

		@Override
		protected boolean getCloser(int target) {
			if (state == WANDERING
					|| Level.distance(target, Dungeon.hero.pos) > 6)
				this.target = target = Dungeon.hero.pos;
			return super.getCloser(target);
		}
		@Override
		protected Char chooseEnemy() {
			if (enemy == null || !enemy.isAlive() || state == WANDERING) {

				HashSet<Mob> enemies = new HashSet<Mob>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile && Level.fieldOfView[mob.pos]
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				enemy = enemies.size() > 0 ? Random.element(enemies) : null;
			}
			return enemy;
		}

		@Override
		public boolean interact() {
			if (Dungeon.level.passable[pos] || Dungeon.hero.flying) {
			int curPos = pos;

			moveSprite(pos, Dungeon.hero.pos);
			move(Dungeon.hero.pos);

			Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
			Dungeon.hero.move(curPos);

			Dungeon.hero.spend(1 / Dungeon.hero.speed());
			Dungeon.hero.busy();
			return true;
			} else {
				return false;
			}
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

	}
}
