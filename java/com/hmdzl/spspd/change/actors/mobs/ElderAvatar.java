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

import java.util.HashSet;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.actors.blobs.DarkGas;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.AttackDown;
import com.hmdzl.spspd.change.actors.buffs.Blindness;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Chill;
import com.hmdzl.spspd.change.actors.buffs.Disarm;
import com.hmdzl.spspd.change.actors.buffs.Frost;
import com.hmdzl.spspd.change.actors.buffs.GlassShield;
import com.hmdzl.spspd.change.actors.buffs.GrowSeed;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.ArmorKit;
import com.hmdzl.spspd.change.items.DolyaSlate;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.KindOfArmor;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.armor.normalarmor.NormalArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.RubberArmor;
import com.hmdzl.spspd.change.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.change.items.artifacts.AlienBag;
import com.hmdzl.spspd.change.items.bombs.DangerousBomb;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.Flare;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.journalpages.Sokoban4;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.wands.WandOfDisintegration;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.CityBossLevel;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ElderAvatarSprite;
import com.hmdzl.spspd.change.sprites.GolemSprite;
import com.hmdzl.spspd.change.sprites.MonkSprite;
import com.hmdzl.spspd.change.sprites.MusketeerSprite;
import com.hmdzl.spspd.change.sprites.ObeliskSprite;
import com.hmdzl.spspd.change.sprites.UndeadSprite;
import com.hmdzl.spspd.change.sprites.WarlockSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ElderAvatar extends Mob {

	{
		spriteClass = ElderAvatarSprite.class;

		HP = HT = 600;
		EXP = 50;
		evadeSkill = 25;
		baseSpeed = 1f;
		
		loot = new AlienBag().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.GUNWEAPON;
		lootChanceOther = 1f;

		properties.add(Property.ALIEN);
		properties.add(Property.BOSS);
	}

	private int orbAlive = 0;
	private int waves = 0;
	public static int breaks = 0;

	private static final String WAVES	= "waves";
	private static final String BREAKS	= "breaks";
	private static final String ORBALIVE	= "orbAlive";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( WAVES, waves );
		bundle.put( BREAKS, breaks );
		bundle.put( ORBALIVE, orbAlive );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		waves = bundle.getInt( WAVES );
		breaks = bundle.getInt( BREAKS );
		orbAlive  = bundle.getInt( ORBALIVE );
	}


	public void spawnObe() {
		Obelisk a = new Obelisk();

		a.pos = Terrain.EMPTY_WELL;
		do {
			a.pos = Random.Int(Dungeon.level.randomRespawnCellMob());
		} while (Dungeon.level.map[a.pos] != Terrain.EMPTY_WELL
				|| Actor.findChar(a.pos) != null);
		GameScene.add(a);
	}

	@Override
	public int damageRoll() {
		return  Dungeon.isChallenged(Challenges.TEST_TIME) ? Random.NormalIntRange(0, 1) : Random.NormalIntRange(25, 40);
	}

	@Override
	public int hitSkill(Char target) {
		return 58;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(8, 12);
	}

	@Override
	protected boolean canAttack(Char enemy) {
		if (HP > 49) return Dungeon.level.distance(pos, enemy.pos) <= 3;
		else return Dungeon.level.distance(pos, enemy.pos) <= 0;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			new DangerousBomb().explode(enemy.pos);
		}
		if (Random.Int(8) == 0) {
			Buff.affect(enemy, Charm.class, Charm.durationFactor(enemy)
					* Random.IntRange(3, 5)).object = id();
			enemy.sprite.centerEmitter().start(Speck.factory(Speck.HEART),
					0.2f, 5);
			Sample.INSTANCE.play(Assets.SND_CHARMS);
		}
		Hero hero = Dungeon.hero;
		KindOfArmor armor = hero.belongings.armor;
		if (Random.Int(10) == 0) {
			if (armor != null && !(armor instanceof WoodenArmor || armor instanceof RubberArmor || armor instanceof NormalArmor)
					&& !armor.cursed) {
				hero.belongings.armor = null;
				Dungeon.level.drop(armor, hero.pos).sprite.drop();
				GLog.w(Messages.get(this, "disarm"));
			}
		}
		return damage;
	}

    public boolean checkObelisk() {

        int obeliskAlive = 0;
        if (Dungeon.level.mobs != null) {
            for (Mob mob : Dungeon.level.mobs) {
                if (mob instanceof Obelisk && mob.HP > 10) {
                    obeliskAlive++;
                }
            }
        }
        if (obeliskAlive > 0) {
            return true;
        } else {
            return false;
        }
    }

	@Override
	public void damage(int dmg, Object src) {
		if (dmg > this.HP && checkObelisk()) {
			dmg = Random.Int(this.HP);
			//Buff.affect(this,ShieldArmor.class).level(100);
		}
		super.damage(dmg, src);
        }

	@Override
	protected boolean act() {

		if (orbAlive < 1) {
			spawnObe();
			orbAlive++;

			return true;
		}

		if (HP < 50 && waves == 0 && breaks == 0) {
			summonHunter(pos);
			waves++;
			Buff.affect(this,ShieldArmor.class).level(100);
			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			return true;
		}

		if (HP < 50 && waves == 1 && breaks == 1) {
			summonWarlock(pos);
			waves++;
			Buff.affect(this,ShieldArmor.class).level(100);
			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			return true;
		}

		if (HP < 50 && waves == 2 && breaks == 2) {
			summonMonk(pos);
			waves++;
			Buff.affect(this,ShieldArmor.class).level(100);
			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			return true;
		}

		if (HP < 50 && waves == 3 && breaks == 3) {
			summonMech(pos);
			waves++;
			Buff.affect(this,ShieldArmor.class).level(100);
			sprite.centerEmitter().start(Speck.factory(Speck.SCREAM), 0.4f, 2);
			Sample.INSTANCE.play(Assets.SND_CHALLENGE);
			return true;
		}

		return super.act();
	}

	private void summonHunter(int pos) {
		TheHunter.spawnAround(pos);
	}

	private void summonWarlock(int pos) {
		TheWarlock.spawnAround(pos);
	}

	private void summonMonk(int pos) {
		TheMonk.spawnAround(pos);
	}

	private void summonMech(int pos) {
		TheMech.spawnAround(pos);
	}

	@Override
	public void die(Object cause) {

		yell(Messages.get(this, "died"));

		GameScene.bossSlain();
		((CityBossLevel) Dungeon.level).unseal();
		if (!Dungeon.limitedDrops.journal.dropped()) {
			Dungeon.level.drop(new DolyaSlate(), pos).sprite.drop();
			Dungeon.limitedDrops.journal.drop();
		}
		Dungeon.level.drop(new Sokoban4(), pos).sprite.drop();
		
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		Dungeon.level.drop(new Gold(Random.Int(4900, 10000)), pos).sprite.drop();

		Badges.validateBossSlain();

		super.die(cause);
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Amok.class);
		RESISTANCES.add(Vertigo.class);

		RESISTANCES.add(WandOfDisintegration.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(Charm.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	public static class TheHunter extends Mob {

		{
			spriteClass = MusketeerSprite.class;

			HP = HT = 100;
			evadeSkill = 15;
			EXP = 0;
			baseSpeed = 3f;
			viewDistance = 6;
			state = WANDERING;
			properties.add(Property.ALIEN);
			properties.add(Property.BOSS);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(20, 35);
		}

		@Override
		public int hitSkill(Char target) {
			return 60;
		}

		@Override
		protected boolean canAttack(Char enemy) {
			if (buff(Disarm.class) != null){
				return false;
			} else
				return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
             Buff.affect(this, Disarm.class,5f);
             Buff.affect(enemy,ArmorBreak.class,3f).level(30);
			return damage;
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
			if (src instanceof ToxicGas) {
				((ToxicGas) src).clear(pos);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
		}

		@Override
		public int drRoll() {
			return 5;
		}

		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static TheHunter spawnAt(int pos) {

			TheHunter d = new TheHunter();

			d.pos = pos;
			d.state = d.HUNTING;
			GameScene.add(d, 2f);

			return d;

		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Charm.class);
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class TheWarlock extends Mob {
		{
			spriteClass = WarlockSprite.class;

			HP = HT = 150;
			evadeSkill = 15;
			EXP = 0;
			state = WANDERING;
			properties.add(Property.ALIEN);
			properties.add(Property.BOSS);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(25, 45);
		}

		@Override
		public int hitSkill(Char target) {
			return 60;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			Buff.prolong(enemy,Vertigo.class,3f);
			Buff.prolong(enemy,AttackDown.class,3f).level(20);
			return damage;
		}

		@Override
		public void damage(int dmg, Object src) {
			dmg =(int)(dmg/2);
			super.damage(dmg, src);
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
		}

		@Override
		public int drRoll() {
			return 5;
		}

		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static TheWarlock spawnAt(int pos) {

			TheWarlock d = new TheWarlock();

			d.pos = pos;
			d.state = d.HUNTING;
			GameScene.add(d, 2f);

			return d;

		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Charm.class);
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class TheMonk extends Mob {

		{
			spriteClass = MonkSprite.class;

			HP = HT = 100;
			evadeSkill = 30;
			EXP = 0;
			baseSpeed = 2f;
			viewDistance = 5;
			state = WANDERING;
			properties.add(Property.ALIEN);
			properties.add(Property.BOSS);
		}

		@Override
		protected float attackDelay() {
			return 0.5f;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(15, 30);
		}

		@Override
		public int hitSkill(Char target) {
			return 60;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(6) == 0) {
			 Buff.affect(this,GlassShield.class).turns(1);
			}
			Buff.prolong(enemy,Blindness.class,3f);
			return damage;
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
			if (src instanceof ToxicGas) {
				((ToxicGas) src).clear(pos);
			}
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
		}

		@Override
		public int drRoll() {
			return 5;
		}

		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static TheMonk spawnAt(int pos) {

			TheMonk d = new TheMonk();

			d.pos = pos;
			d.state = d.HUNTING;
			GameScene.add(d, 2f);

			return d;

		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Charm.class);
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class TheMech extends Mob {

		{
			spriteClass = GolemSprite.class;

			HP = HT = 200;
			evadeSkill = 15;
			EXP = 0;
			state = WANDERING;
			baseSpeed = 0.5f;
			properties.add(Property.ALIEN);
			properties.add(Property.MECH);
			properties.add(Property.BOSS);
		}
		private int addshield = 0;
		@Override
		public int damageRoll() {
			return Random.NormalIntRange(45, 70);
		}

		@Override
		public int hitSkill(Char target) {
			return 30;
		}

		@Override
		protected boolean act() {

			if (addshield < 1) {
				Buff.affect(this,ShieldArmor.class).level(100);
				addshield++;
				return true;
			}
			return super.act();
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			Buff.affect(enemy, Burning.class).reignite(enemy);
			return damage;
		}

		@Override
		public void damage(int dmg, Object src) {
			super.damage(dmg, src);
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
		}

		@Override
		public int drRoll() {
			return 10;
		}

		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static TheMech spawnAt(int pos) {

			TheMech d = new TheMech();

			d.pos = pos;
			d.state = d.HUNTING;
			GameScene.add(d, 2f);

			return d;

		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

		static {
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(Frost.class);
			IMMUNITIES.add(Chill.class);
			IMMUNITIES.add(ElectriShock.class);
			IMMUNITIES.add(ToxicGas.class);
			IMMUNITIES.add(DarkGas.class);
			IMMUNITIES.add(GrowSeed.class);
			IMMUNITIES.add(Charm.class);
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Paralysis.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class Obelisk extends Mob {

		{
			spriteClass = ObeliskSprite.class;

			HP = HT = 1000;
			evadeSkill = 0;

			EXP = 10;

			hostile = false;
			state = PASSIVE;

			loot = new StoneOre();
			lootChance = 0.05f;

			properties.add(Property.UNKNOW);
			properties.add(Property.BOSS);
		}

		@Override
		public void beckon(int cell) {
			// Do nothing
		}

		@Override
		public void add(Buff buff) {
		}

		@Override
		public int damageRoll() {
			return 0;
		}

		@Override
		public int hitSkill(Char target) {
			return 0;
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		protected boolean act() {

			if (3 - ElderAvatar.breaks > 4 * HP / HT) {
				ElderAvatar.breaks++;
				for (Mob mob : Dungeon.level.mobs) {
					if (mob instanceof ElderAvatar) {
						mob.HP = 600;
					}
				}
				return true;
			}
			return super.act();
		}

	public boolean checkElder() {

		int elderAlive = 0;
		if (Dungeon.level.mobs != null) {
			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof ElderAvatar && mob.HP > 20) {
					elderAlive++;
				}
			}
		}
		if (elderAlive > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void damage(int dmg, Object src) {
		if (checkElder()) {
			yell(Messages.get(this, "impossible"));
		} else  {
			super.damage(dmg, src);
		}
	}
	
  }
}
