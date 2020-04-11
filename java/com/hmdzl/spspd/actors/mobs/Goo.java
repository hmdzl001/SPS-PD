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

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.blobs.Web;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.UpgradeBlobViolet;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentFire;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Badges.Badge;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.GooWarn;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.items.summon.ActiveMrDestructo;
import com.hmdzl.spspd.items.journalpages.Sokoban1;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.SewerBossLevel;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.GooSprite;
import com.hmdzl.spspd.sprites.PoisonGooSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Goo extends Mob {
	{
		HP = HT = 350; 
		EXP = 20;
		evadeSkill = 12;
		spriteClass = GooSprite.class;

		loot = new UpgradeBlobViolet();
		lootChance = 0.2f;
		
		lootOther = new ActiveMrDestructo();
		lootChanceOther = 1f;

		properties.add(Property.ELEMENT);
		properties.add(Property.BOSS);
	}

	private int pumpedUp = 0;
	private int goosAlive = 0;

	@Override
	public int damageRoll() {
		if (pumpedUp > 0) {
			pumpedUp = 0;
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					CellEmitter.get(j).burst(ElmoParticle.FACTORY, 10);
			}
			Sample.INSTANCE.play(Assets.SND_BURNING);
			return Random.NormalIntRange(5, 30);
		} else {
			return Random.NormalIntRange(2, 12);
		}
	}

	@Override
	public int hitSkill(Char target) {
		return (pumpedUp > 0) ? 30 : 15;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP++;
		}

		return super.act();
	}

	
	@Override
	protected boolean canAttack(Char enemy) {
		return (pumpedUp > 0) ? distance(enemy) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Ooze.class);
			enemy.sprite.burst(0x000000, 5);
		}

		if (pumpedUp > 0) {
			Camera.main.shake(3, 0.2f);
		}
				
		return damage;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		if (pumpedUp == 1) {
			((GooSprite) sprite).pumpUp();
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					GameScene.add(Blob.seed(j, 2, GooWarn.class));
			}
			pumpedUp++;

			spend(attackDelay());

			return true;
		} else if (pumpedUp >= 2 || Random.Int( (HP*2 <= HT) ? 2 : 5 ) > 0) {

			boolean visible = Dungeon.visible[pos];

			if (visible) {
				if (pumpedUp >= 2) {
					((GooSprite) sprite).pumpAttack();
				} else
					sprite.attack(enemy.pos);
			} else {
				attack(enemy);
			}

			spend(attackDelay());

			return !visible;

		} else {

			pumpedUp++;

			((GooSprite) sprite).pumpUp();

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				int j = pos + Level.NEIGHBOURS9[i];
				GameScene.add(Blob.seed(j, 2, GooWarn.class));

			}

			if (Dungeon.visible[pos]) {
				sprite.showStatus(CharSprite.NEGATIVE, "!!!");
				GLog.n(Messages.get(this,"atk"));
			}

			spend(attackDelay());

			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {
		boolean result = super.attack(enemy);
		pumpedUp = 0;
		return result;
	}

	@Override
	protected boolean getCloser(int target) {
		pumpedUp = 0;
		return super.getCloser(target);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof Goo || mob instanceof PoisonGoo){
				   goosAlive++;
				 }
			
			}
			
			 if(goosAlive==0){
			
			((SewerBossLevel) Dungeon.level).unseal();

			GameScene.bossSlain();
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();	
			Badges.validateBossSlain();
		}
			 
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
				}
				
	
		Dungeon.level.drop(new Sokoban1(), pos).sprite.drop();
		

		yell("glurp... glurp...");
	}
  
	protected boolean spawnedMini = false;
	
	@Override
	public void notice() {
		super.notice();
		yell("GLURP-GLURP!");
		((SewerBossLevel) Dungeon.level).seal();
		if (!spawnedMini){
	    PoisonGoo.spawnAround(pos);
	    spawnedMini = true;
		}
	}

	private final String PUMPEDUP = "pumpedup";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(PUMPEDUP, pumpedUp);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pumpedUp = bundle.getInt(PUMPEDUP);
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(EnchantmentDark.class);
		

		IMMUNITIES.add(Roots.class);

		WEAKNESS.add(Burning.class);
		WEAKNESS.add(WandOfFirebolt.class);
		WEAKNESS.add(EnchantmentFire.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	@Override
	public HashSet<Class<?>> weakness() {
		return WEAKNESS;
	}

	public static class PoisonGoo extends Mob {

		protected static final float SPAWN_DELAY = 2f;

		private boolean gooSplit = false;

		private int gooGeneration = 0;
		private int goosAlive = 0;

		private static final String GOOGENERATION = "gooGeneration";

		{
			HP = HT = 100;
			EXP = 1;
			evadeSkill = 12;
			spriteClass = PoisonGooSprite.class;
			baseSpeed = 1.5f;

			loot = new StoneOre();
			lootChance = 0.25f;
			FLEEING = new Fleeing();

			properties.add(Property.ELEMENT);
			properties.add(Property.MINIBOSS);
		}

		private static final float SPLIT_DELAY = 1f;

		@Override
		protected boolean act() {
			boolean result = super.act();

			if (state == FLEEING && buff(Terror.class) == null && enemy != null
					&& enemySeen && enemy.buff(Poison.class) == null) {
				state = HUNTING;
			}
			if (Level.water[pos] && HP < HT) {
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				HP++;
			} else if(Level.water[pos] && HP == HT && HT < 100){
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				HT=HT+5;
				HP=HT;
			}
			return result;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(1) == 0) {
				Buff.affect(enemy, Poison.class).set(
						Random.Int(7, 10) * Poison.durationFactor(enemy));
				state = FLEEING;
			}

			return damage;
		}

		@Override
		public void move(int step) {
			if (state == FLEEING) {
				GameScene.add(Blob.seed(pos, Random.Int(7, 10), Web.class));
			}
			super.move(step);
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(1, 10);
		}

		@Override
		public int hitSkill(Char target) {
			return 5;
		}

		@Override
		public int drRoll() {
			return 0;
		}

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(GOOGENERATION, gooGeneration);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			gooGeneration = bundle.getInt(GOOGENERATION);
		}

		@Override
		public int defenseProc(Char enemy, int damage) {
			gooSplit = false;
			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof Goo) {
					gooSplit = true;
				}
			}
			if (HP >= damage + 2 && gooSplit) {
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
					PoisonGoo clone = split();
					clone.HP = (HP - damage) / 2;
					clone.pos = Random.element(candidates);
					clone.state = clone.HUNTING;

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


		private PoisonGoo split() {
			PoisonGoo clone = new PoisonGoo();
			clone.gooGeneration = gooGeneration + 1;
			if (buff(Burning.class) != null) {
				Buff.affect(clone, Burning.class).reignite(clone);
			}
			if (buff(Poison.class) != null) {
				Buff.affect(clone, Poison.class).set(2);
			}
			return clone;
		}




		@Override
		public void die(Object cause) {

			if (gooGeneration > 0){
				lootChance = 0;
			}

			super.die(cause);

			for (Mob mob : Dungeon.level.mobs) {

				if (mob instanceof Goo || mob instanceof PoisonGoo){
					goosAlive++;
				}

			}

			if(goosAlive==0){
				((SewerBossLevel) Dungeon.level).unseal();

				GameScene.bossSlain();
				Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();

				//Dungeon.level.drop(new Gold(Random.Int(900, 2000)), pos).sprite.drop();

				Badges.validateBossSlain();
			} else {

				//Dungeon.level.drop(new Gold(Random.Int(100, 200)), pos).sprite.drop();
			}

			yell("glurp... glurp...");
		}

		@Override
		public void notice() {
			super.notice();
			yell("GLURP-GLURP!");
		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add(ToxicGas.class);
			RESISTANCES.add(EnchantmentDark.class);

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


		public static void spawnAround(int pos) {
			for (int n : Level.NEIGHBOURS4) {
				int cell = pos + n;
				if (Level.passable[cell] && Actor.findChar(cell) == null) {
					spawnAt(cell);
				}
			}
		}

		public static PoisonGoo spawnAt(int pos) {

			PoisonGoo b = new PoisonGoo();

			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;

		}


	}

}
