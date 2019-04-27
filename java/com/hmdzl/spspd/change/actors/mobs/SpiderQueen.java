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

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Badges.Badge;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.DarkGas;
import com.hmdzl.spspd.change.actors.blobs.SlowWeb;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.DefenceUp;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Slow;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.artifacts.RobotDMT;
import com.hmdzl.spspd.change.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.change.levels.CavesBossLevel;
import com.hmdzl.spspd.change.levels.features.Door;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.items.journalpages.Sokoban3;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.SpiderEggSprite;
import com.hmdzl.spspd.change.sprites.SpiderGoldSprite;
import com.hmdzl.spspd.change.sprites.SpiderJumpSprite;
import com.hmdzl.spspd.change.sprites.SpiderMindSprite;
import com.hmdzl.spspd.change.sprites.SpiderNormalSprite;
import com.hmdzl.spspd.change.sprites.SpiderQueenSprite;
import com.hmdzl.spspd.change.utils.GLog;

import com.watabou.utils.Random;

public class SpiderQueen extends Mob {

	{
		spriteClass = SpiderQueenSprite.class;

		HP = HT = 1000;
		EXP = 35;
		evadeSkill = 35;
		baseSpeed = 0.8f;

		loot = new RobotDMT().identify();
		lootChance = 0.5f;
		
		lootOther = Generator.Category.ARMOR;
		lootChance = 0.2f;
		
		properties.add(Property.BEAST);
		properties.add(Property.BOSS);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(21, 36);
	}

	@Override
	public int hitSkill(Char target) {
		return 40;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 20);
	}
	
	@Override
	public boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Poison.class) == null) {
			state = HUNTING;
		}
		return result;
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Poison.class).set(
					Random.Int(7, 9) * Poison.durationFactor(enemy));
			state = FLEEING;
		}

		return damage;
	}
	
	@Override
	public void move(int step) {
		super.move(step);

		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP ) {
			sprite.emitter().burst(ElmoParticle.FACTORY, 5);
			
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

				SpiderEgg segg = new SpiderEgg();
				segg.HP = 25;
				segg.pos = Random.element(candidates);
				segg.state = segg.PASSIVE;

				if (Dungeon.level.map[segg.pos] == Terrain.DOOR) {
					Door.enter(segg.pos);
				}

				GameScene.add(segg, 1f);
				Actor.addDelayed(new Pushing(segg, pos, segg.pos), -1);
				damage(1,this);
			}			
			
			if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
				GLog.n(Messages.get(this,"egg"));
			}
		}

	}

	@Override
	public void die(Object cause) {

		GameScene.bossSlain();
		((CavesBossLevel) Dungeon.level).unseal();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Badges.validateBossSlain();

			Badges.Badge badgeToCheck = null;
			switch (Dungeon.hero.heroClass) {
			case WARRIOR:
			badgeToCheck = Badge.MASTERY_WARRIOR;
			break;
			case MAGE:
			badgeToCheck = Badge.MASTERY_MAGE;
			break;
			case ROGUE:
			badgeToCheck = Badge.MASTERY_ROGUE;
			break;
		    case HUNTRESS:
			badgeToCheck = Badge.MASTERY_HUNTRESS;
			break;
		    case PERFORMER:
			badgeToCheck = Badge.MASTERY_PERFORMER;
			break;	
				case SOLDIER:
					badgeToCheck = Badge.MASTERY_SOLDIER;
					break;				
		}
	
	    Dungeon.level.drop(new Sokoban3(), pos).sprite.drop();
		yell(Messages.get(this,"die"));
		super.die(cause);
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

	static {
		RESISTANCES.add(Poison.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Slow.class);
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
	
	public static class SpiderEgg extends Mob {

		{
			spriteClass = SpiderEggSprite.class;

			HP = HT = 25;
			evadeSkill = 0;

			EXP = 0;

			state = PASSIVE;
			properties.add(Property.UNKNOW);
			properties.add(Property.BOSS);
		}

		private int life_p=0;

		@Override
		public void die(Object cause) {
			super.die(cause);
			if (life_p > 20){
				SpiderGold.spawnAt(pos);
			} else if(life_p > 10) {
				SpiderJumper.spawnAt(pos);
			} else if(life_p > 5 ){
				SpiderMind.spawnAt(pos);
			} else SpiderWorker.spawnAt(pos);
		}

		@Override
		public int hitSkill(Char target) {
			return 0;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(0, 1);
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public boolean act() {
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				GameScene.add(Blob.seed(pos + Level.NEIGHBOURS9DIST2[i], 2,
						SlowWeb.class));
			}
			life_p ++;
            damage(1,this);
			return super.act();
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
			IMMUNITIES.add(Slow.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class SpiderWorker extends Mob {

		{
			spriteClass = SpiderNormalSprite.class;

			HP = HT = 150;
			evadeSkill = 10;

			EXP = 9;
			maxLvl = 16;

			loot = new MysteryMeat();
			lootChance = 0.15f;

			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(12, 26+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 20+adj(0);
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(6, 10);
		}


		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

		static {
			RESISTANCES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Roots.class);
			IMMUNITIES.add(SlowWeb.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		public static SpiderWorker spawnAt(int pos) {

			SpiderWorker b = new SpiderWorker();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}
	}

	public static class SpiderMind extends SpiderWorker {

		{
			spriteClass = SpiderMindSprite.class;

			HP = HT = 100;
			evadeSkill = 20;

			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(5, 10+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 20+adj(0);
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			int reg = Random.Int(damage);
			if (reg > 0) {
				HP += reg;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
			return damage;
		}


		@Override
		public boolean act() {
			GameScene.add(Blob.seed(pos, 15, DarkGas.class));
			return super.act();
		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

		static {
			RESISTANCES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Roots.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		public static SpiderMind spawnAt(int pos) {

			SpiderMind b = new SpiderMind();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}
	}
	public static class SpiderJumper extends SpiderWorker{

		private static final int BLINK_DELAY = 3;

		private int delay = 0;

		{
			spriteClass = SpiderJumpSprite.class;

			HP = HT = 150;
			evadeSkill = 10;

			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(12, 26+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 20+adj(0);
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(6, 10);
		}

		@Override
		protected boolean getCloser(int target) {
			if (Level.fieldOfView[target] && Level.distance(pos, target) > 2
					&& delay <= 0) {

				blink(target);
				spend(-1 / speed());
				return true;

			} else {

				delay--;
				return super.getCloser(target);

			}
		}

		private void blink(int target) {

			Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
			int cell = route.collisionPos;

			//can't occupy the same cell as another char, so move back one.
			if (Actor.findChar( cell ) != null && cell != this.pos)
				cell = route.path.get(route.dist-1);

			if (Level.avoid[ cell ]){
				ArrayList<Integer> candidates = new ArrayList<>();
				for (int n : Level.NEIGHBOURS8) {
					cell = route.collisionPos + n;
					if (Level.passable[cell] && Actor.findChar( cell ) == null) {
						candidates.add( cell );
					}
				}
				if (candidates.size() > 0)
					cell = Random.element(candidates);
				else {
					delay = BLINK_DELAY;
					return;
				}
			}

			ScrollOfTeleportation.appear( this, cell );

			delay = BLINK_DELAY;
		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

		static {
			RESISTANCES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Roots.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		public static SpiderJumper spawnAt(int pos) {

			SpiderJumper b = new SpiderJumper();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}
	}
	public static class SpiderGold extends SpiderWorker {

		{
			spriteClass = SpiderGoldSprite.class;

			HP = HT = 300;
			evadeSkill = 5;
			baseSpeed = 0.75f;

			properties.add(Property.BEAST);
		}

		private static final float SPAWN_DELAY = 1f;

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(20, 30+adj(0));
		}

		@Override
		public int hitSkill(Char target) {
			return 40+adj(0);
		}

		@Override
		public int drRoll() {
			return Random.NormalIntRange(0, 10);
		}

		@Override
		public boolean act() {
			GameScene.add(Blob.seed(pos, 15, ConfusionGas.class));
			return super.act();
		}

		@Override
		public int defenseProc(Char enemy, int damage) {

			Buff.affect(this,DefenceUp.class,3f).level(20);

			return super.defenseProc(enemy, damage);
		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

		static {
			RESISTANCES.add(Poison.class);
		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(ConfusionGas.class);
			IMMUNITIES.add(Roots.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}

		public static SpiderGold spawnAt(int pos) {

			SpiderGold b = new SpiderGold();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}
	}


}
