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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Awareness;
import com.hmdzl.spspd.actors.buffs.Bless;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.actors.buffs.MechArmor;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.scrolls.ScrollOfTeleportation;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BMirrorSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class SoldierSkill extends ClassSkill {
 private static int SKILL_TIME = 1;
	{
		image = ItemSpriteSheet.SKILL_SOLDIER;
	}

	@Override
	public void doSpecial() {

		ArrayList<Integer> respawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			int p = curUser.pos + Floor.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Floor.passable[p] || Floor.avoid[p])) {
				respawnPoints.add(p);
			}
		}

		int nImages = 2;
		while (nImages > 0 && respawnPoints.size() > 0) {
			int index = Random.index(respawnPoints);



			if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
				SeekingHugeBomb mob = new SeekingHugeBomb();
				mob.duplicates(curUser);
				GameScene.add(mob);
				ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
				respawnPoints.remove(index);
				nImages--;
			}else {
				SeekingBomb mob = new SeekingBomb();
				mob.duplicates(curUser);
				GameScene.add(mob);
				ScrollOfTeleportation.appear(mob, respawnPoints.get(index));
				respawnPoints.remove(index);
				nImages--;
			}
		}

		SoldierSkill.charge += 12;
		if (Dungeon.hero.lvl > 55) {
			Buff.detach(curUser, Poison.class);
			Buff.detach(curUser, Cripple.class);
			Buff.detach(curUser, STRdown.class);
			Buff.detach(curUser, Burning.class);
			Buff.detach(curUser, Ooze.class);
			Buff.detach(curUser, Chill.class);
			Buff.affect(curUser, Bless.class, 5f);
		}
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		}
		
	@Override
	public void doSpecial2() {
		SoldierSkill.charge += 25;
		if (Dungeon.hero.lvl > 55) {
			Buff.affect(curUser, MechArmor.class).level(600);
			Buff.affect(curUser, ShieldArmor.class).level(Dungeon.hero.lvl * 6);
		} else {
			Buff.affect(curUser, MechArmor.class).level(300);
			Buff.affect(curUser, ShieldArmor.class).level(Dungeon.hero.lvl * 3);
		}
        curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();

		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		}
	@Override
	public void doSpecial3() {
		if (Dungeon.hero.lvl > 55 ) {
			Dungeon.depth.drop(Generator.random(Generator.Category.SUMMONED), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
		} else if (Random.Int(2) == 0) {
			Dungeon.depth.drop(Generator.random(Generator.Category.SUMMONED), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
		} else {
			Dungeon.depth.drop(Generator.random(),Dungeon.hero.pos);
		}
		SoldierSkill.charge += 15;
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	@Override
	public void doSpecial4() {
		SoldierSkill.charge += 20;

		for (int n : Floor.NEIGHBOURS4OUT) {
			int cell = curUser.pos + n;
			if (Dungeon.hero.lvl > 55) {
				Dungeon.depth.drop(Generator.random(Generator.Category.RANGEWEAPON), cell);
				Dungeon.depth.drop(Generator.random(Generator.Category.HIGHFOOD), cell);
			} else {
				Dungeon.depth.drop(Generator.random(Generator.Category.RANGEWEAPON), cell);
				Dungeon.depth.drop(Generator.random(Generator.Category.FOOD), cell);
			}
		}
        Buff.affect( curUser, Awareness.class, 5f);
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
	}

	public static class SeekingBomb extends NPC {
		{
			spriteClass = BMirrorSprite.class;
			hostile = false;
			state = HUNTING;
			HP = HT = 100;
			evadeSkill = 20;
			ally=true;

			properties.add(Property.MECH);
		}
		@Override
		public int damageRoll() {
			return Dungeon.hero.damageRoll();
		}

		@Override
		public int hitSkill(Char target) {
			return 1000;
		}

		@Override
		public int drRoll() {
			return Dungeon.hero.drRoll();
		}

		public int skin;
		private static final String SKIN = "skin";
		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(SKIN, skin);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			skin = bundle.getInt(SKIN);
		}

		public void duplicates(Hero hero) {
			skin = hero.useskin();
		}


		public boolean interact() {
			if (!Floor.passable[pos]){
				return true;
			}
			if (this.buff(MagicalSleep.class) != null) {
				Buff.detach(this, MagicalSleep.class);
			}

			if (state == SLEEPING) {
				state = HUNTING;
			}
			if (buff(Paralysis.class) != null) {
				Buff.detach(this, Paralysis.class);
			}

			int curPos = pos;

			moveSprite(pos, Dungeon.hero.pos);
			move(Dungeon.hero.pos);

			Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
			Dungeon.hero.move(curPos);

			Dungeon.hero.spend(1 / Dungeon.hero.speed());
			Dungeon.hero.busy();
			return true;
		}

		@Override
		public CharSprite sprite() {
			CharSprite s = super.sprite();
			((BMirrorSprite) s).updateArmor(skin);
			return s;
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

		public static SeekingBomb spawnAt(int pos) {

			SeekingBomb b = new SeekingBomb();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, 1f);

			return b;

		}

		@Override
		public int attackProc(Char enemy, int damage) {
			int dmg = super.attackProc(enemy, damage);

			if (this.HP < 5) {

				Sample.INSTANCE.play(Assets.SND_BLAST);

				if (Dungeon.visible[pos]) {
					CellEmitter.center(pos).burst(BlastParticle.FACTORY, 30);
				}

				GameScene.add(Blob.seed(pos,5, Fire.class));

				for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
					Char ch = findChar(pos + Floor.NEIGHBOURS8[i]);
					if (ch != null && ch.isAlive() && ch != Dungeon.hero) {
						ch.damage(damage, this,2);
					}
				}

				destroy();
				sprite.die();
			}

			return dmg;
		}

		@Override
		public void damage(int dmg, Object src, int type) {
			if (dmg > this.HP){
				dmg = 0;
				this.HP = 1;
			}

			super.damage(dmg,src,type);
		}


		@Override
		public void die(Object cause) {
			super.die(cause);
		}

        {
			immunities.add(Terror.class);
			immunities.add(ToxicGas.class);
		}

	}
	public static class SeekingHugeBomb extends SeekingBomb {
		{
			//spriteClass = MirrorSprite.class;
			//hostile = false;
			//state = HUNTING;
			//HP = HT = 1;
			//evadeSkill = 20;
			ally=true;

			properties.add(Property.MECH);
		}

		@Override
		public int damageRoll() {
			return 2*Dungeon.hero.damageRoll();
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

		public static SeekingHugeBomb spawnAt(int pos) {

			SeekingHugeBomb b = new SeekingHugeBomb();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, 1f);

			return b;

		}

	    {
			immunities.add(Terror.class);
			immunities.add(ToxicGas.class);
		}

	}
}

