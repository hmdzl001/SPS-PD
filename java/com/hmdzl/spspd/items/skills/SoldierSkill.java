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
package com.hmdzl.spspd.items.skills;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Weakness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.MirrorImage;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.sprites.SeekingBombSprite;
import com.hmdzl.spspd.sprites.SeekingClusterBombSprite;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.watabou.utils.Random;
import com.watabou.noosa.audio.Sample;

public class SoldierSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		image = ItemSpriteSheet.ARMOR_SOLDIER;
	}

	@Override
	public void doSpecial() {

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				respawnPoints.add(p);
			}
		}

		int nImages = 4;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index(respawnPoints);

			MirrorImage mob = new MirrorImage();
			mob.duplicate(curUser);
			GameScene.add(mob);
			ScrollOfTeleportation.appear(mob, respawnPoints.get(index));

			respawnPoints.remove(index);
			nImages--;
		}
		SoldierSkill.charge += 12;
	    Buff.detach(curUser, Poison.class);
		Buff.detach(curUser, Cripple.class);
		Buff.detach(curUser, Weakness.class);
		Buff.detach(curUser, Burning.class);
		Buff.detach(curUser, Ooze.class);
		Buff.detach(curUser, Chill.class);
		Buff.affect(curUser, Bless.class, 5f);
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		}
		
	@Override
	public void doSpecial2() {
		SoldierSkill.charge += 25;
		Buff.affect(curUser, MechArmor.class).level(250);
		Buff.affect(curUser, ShieldArmor.class).level(Dungeon.hero.lvl*3);
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		}
	@Override
	public void doSpecial3() {

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				respawnPoints.add(p);
			}
		}

		int nImages = 2;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index(respawnPoints);
			SeekingBomb mob = new SeekingBomb();
			SeekingHugeBomb mob2 = new SeekingHugeBomb();
			if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
				GameScene.add(mob2);
				ScrollOfTeleportation.appear(mob2, respawnPoints.get(index));
				respawnPoints.remove(index);
				nImages--;
			}else {
				GameScene.add(mob);
				ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
				respawnPoints.remove(index);
				nImages--;
			}
		}
		SoldierSkill.charge += 5;
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public void doSpecial4() {
		SoldierSkill.charge += 20;

		int cell = Dungeon.level.randomRespawnCell();

		if (cell != -1) {
			//Dungeon.level.spdrop(Generator.random(Generator.Category.HIGHFOOD), cell);
			Dungeon.level.spdrop(Generator.random(Generator.Category.RANGEWEAPON), cell);
			if (Dungeon.hero.isHungry() || Dungeon.hero.isStarving())
                Dungeon.level.spdrop(Generator.random(Generator.Category.HIGHFOOD), cell);
			else Dungeon.level.spdrop(Generator.random(Generator.Category.FOOD), cell);
		} else {
			//Dungeon.level.spdrop(Generator.random(Generator.Category.HIGHFOOD), curUser.pos);
			Dungeon.level.spdrop(Generator.random(Generator.Category.RANGEWEAPON), Dungeon.hero.pos);
            if (Dungeon.hero.isHungry() || Dungeon.hero.isStarving())
                Dungeon.level.spdrop(Generator.random(Generator.Category.HIGHFOOD), Dungeon.hero.pos);
            else Dungeon.level.spdrop(Generator.random(Generator.Category.FOOD), Dungeon.hero.pos);
		}
        Buff.affect( curUser, Awareness.class, 2f);
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	public static class SeekingBomb extends Mob {
		{
			spriteClass = SeekingBombSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 1;
			evadeSkill = 20;
			ally=true;

			properties.add(Property.MECH);
		}
		@Override
		public int attackProc(Char enemy, int damage) {
			int dmg = super.attackProc(enemy, damage);
			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			yell("KA-BOOM!!!");
			destroy();
			sprite.die();
			return dmg;
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

		public static SeekingBomb spawnAt(int pos) {

			SeekingBomb b = new SeekingBomb();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, 1f);

			return b;

		}


		@Override
		public void die(Object cause) {
			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			super.die(cause);
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	public static class SeekingHugeBomb extends Mob {
		{
			spriteClass = SeekingClusterBombSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 1;
			evadeSkill = 20;
			ally=true;

			properties.add(Property.MECH);
		}
		@Override
		public int attackProc(Char enemy, int damage) {
			int dmg = super.attackProc(enemy, damage);
			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			bomb.explode(pos);
			yell("KA-BOOM!!!");
			destroy();
			sprite.die();
			return dmg;
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

		public static SeekingHugeBomb spawnAt(int pos) {

			SeekingHugeBomb b = new SeekingHugeBomb();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, 1f);

			return b;

		}


		@Override
		public void die(Object cause) {
			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			bomb.explode(pos);
			super.die(cause);
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
}

