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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Badges.Badge;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.SlowWeb;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.BeOld;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.TomeOfMastery;
import com.hmdzl.spspd.items.artifacts.RobotDMT;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.journalpages.Sokoban3;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.items.weapon.missiles.TaurcenBow;
import com.hmdzl.spspd.items.weapon.rockcode.Sweb;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SpiderEggSprite;
import com.hmdzl.spspd.sprites.SpiderGoldSprite;
import com.hmdzl.spspd.sprites.SpiderJumpSprite;
import com.hmdzl.spspd.sprites.SpiderMindSprite;
import com.hmdzl.spspd.sprites.SpiderNormalSprite;
import com.hmdzl.spspd.sprites.SpiderQueenSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class SpiderQueen extends Mob {

	{
		spriteClass = SpiderQueenSprite.class;

		HP = HT = 1000;
		EXP = 50;
		evadeSkill = 35;
		baseSpeed = 0.8f;

		loot = new RobotDMT().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.ARMOR;
		lootChance = 1f;
		
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
					Random.Int(7, 9));
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
	public Item SupercreateLoot(){
			return new TaurcenBow().identify().dounique();
	}	

	@Override
	public void die(Object cause) {

		GameScene.bossSlain();
		Dungeon.level.unseal();
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
				case FOLLOWER:
					badgeToCheck = Badge.MASTERY_FOLLOWER;
					break;
				case ASCETIC:
					badgeToCheck = Badge.MASTERY_ASCETIC;
					break;
		}
	
	    Dungeon.level.drop(new TomeOfMastery(), pos).sprite.drop();
	
	    Dungeon.level.drop(new Sokoban3(), pos).sprite.drop();

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Dungeon.skins == 7)
			Dungeon.level.drop(new Sweb(), Dungeon.hero.pos).sprite.drop();
		yell(Messages.get(this,"die"));
		super.die(cause);
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}
	
    {
		resistances.add(Poison.class);
		immunities.add(Slow.class);
		immunities.add(Roots.class);
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
		public void beckon(int cell) {
			// Do nothing
		}

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

		{
			immunities.add(Amok.class);
			immunities.add(Sleep.class);
			immunities.add(Terror.class);
			immunities.add(Poison.class);
			immunities.add(Vertigo.class);
			immunities.add(ToxicGas.class);
			immunities.add(Slow.class);
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

        {
			resistances.add(Poison.class);

			immunities.add(Roots.class);
			immunities.add(SlowWeb.class);
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
			if (reg > 0 && this.buff(BeOld.class) == null) {
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

		{
			resistances.add(Poison.class);
			immunities.add(Roots.class);
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


		{
			resistances.add(Poison.class);
		
			immunities.add(Roots.class);
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

		{
			resistances.add(Poison.class);
			immunities.add(ConfusionGas.class);
			immunities.add(Roots.class);
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
