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
package com.hmdzl.spspd.change.items.wands;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Taunt;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.sprites.KeKeSprite;
import com.hmdzl.spspd.change.sprites.TCloudSprite;
import com.hmdzl.spspd.change.utils.BArray;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.HashSet;

public class WandOfTCloud extends Wand {

	{
	    image = ItemSpriteSheet.WAND_TCLOUD;
		collisionProperties = Ballistica.PROJECTILE;
	}

	@Override
	protected void onZap(Ballistica bolt) {

		int level = level();

		int n = 1;

		if (Actor.findChar(bolt.collisionPos) != null && Ballistica.distance > 2) {
			bolt.sourcePos = Ballistica.trace[Ballistica.distance - 2];
		}

		boolean[] passable = BArray.or(Level.passable, Level.avoid, null);
		for (Actor actor : Actor.all()) {
			if (actor instanceof Char) {
				passable[((Char) actor).pos] = false;
			}
		}

		PathFinder.buildDistanceMap(bolt.collisionPos, passable, n);
		int dist = 0;

		if (Actor.findChar(bolt.collisionPos) != null) {
			PathFinder.distance[bolt.collisionPos] = Integer.MAX_VALUE;
			dist = 1;
		}
		
		if ( curCharges > 9 ){
		cloudLabel: for (int i = 0; i < n; i++) {
			do {
				for (int j = 0; j < Level.getLength(); j++) {
					if (PathFinder.distance[j] == dist) {

						if (Dungeon.hero.subClass == HeroSubClass.LEADER ){
							STCloud scloud = new STCloud();
							scloud.pos = j;
							scloud.lvl = level;
							GameScene.add(scloud);
						} else {

							TCloud cloud = new TCloud();
							cloud.pos = j;
							cloud.lvl = level;
							//cloud.lifespan = chargesPerCast();
							GameScene.add(cloud);
						}
						CellEmitter.get(j).burst(Speck.factory(Speck.WOOL), 4);
						PathFinder.distance[j] = Integer.MAX_VALUE;
						continue cloudLabel;
					}
				}
				dist++;
			} while (dist < n);
		}
	} else {
		GLog.w(Messages.get(this, "more_charge"));
		for (int i : Level.NEIGHBOURS9) {
			int c = bolt.collisionPos + i;
			if (c >= 0 && c < Level.getLength()) {
				GameScene.add(Blob.seed(c, curCharges, ElectriShock.class));
				CellEmitter.get(c).burst(EnergyParticle.FACTORY, 5);
			}
		}
		}
	    Heap heap = Dungeon.level.heaps.get(bolt.collisionPos);
		if (heap != null) {heap.lit();}
	}

	@Override
	protected int initialCharges() {
		return 1;
	}	
	
	@Override	
	protected int chargesPerCast() {
		return Math.max(1, curCharges);
	}		
	
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		MagicMissile.wool(curUser.sprite.parent, curUser.pos, bolt.collisionPos, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

    public static class TCloud extends NPC implements Callback {

   private static final float TIME_TO_ZAP = 1f;
   		private static final int BOMB_DELAY = 20;
		private int timeToBomb = BOMB_DELAY;
	
	{
		//name = "TCloud";
		
		spriteClass = TCloudSprite.class;
		HP = HT = 200;
		state = HUNTING;
		flying = true;
		ally=true;

		viewDistance = 6;
		properties.add(Property.ELEMENT);

	}

	public int lvl;
	public int lifespan; 
	
	private static final String LVL = "lvl";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LVL, lvl);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		lvl = bundle.getInt(LVL);
	}
	
	@Override
	protected boolean act() {
		timeToBomb --;
		if (timeToBomb == 0){
			destroy();
			sprite.die();
		}
		
        return super.act();
	}	
	
	@Override
	public void move(int step) {		
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
	
	@Override
	public int hitSkill(Char target) {
		return 500;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10 + lvl, 15 + 3*lvl);
	}	
	
	@Override
	protected float attackDelay() {
		return 0.5f;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}	
	
	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((TCloudSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(6 + lvl, 20 + 3*lvl);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, this);
				//Buff.affect(enemy, Taunt.class,2f).object = id();
				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();
				damage(Random.NormalIntRange(10 + lvl, 15 + 3*lvl), this);
				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail( Messages.format(ResultDescriptions.MOB));
						//GLog.n(Messages.get(this, "zap_kill"));
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}	
	
	@Override
	public void call() {
		next();
	}	
	
	@Override
	public int attackProc(Char enemy, int damage) {
		int dmg = super.attackProc(enemy, damage);
		if(HP < 1){
		destroy();
		sprite.die();
		}
		
		return dmg;
	}	

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public boolean interact() {

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
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
	
}	
	
    public static class STCloud extends NPC implements Callback {

    private static final float TIME_TO_ZAP = 1f;
   		private static final int BOMB_DELAY = 40;
		private int timeToBomb = BOMB_DELAY;
	
	{
		//name = "TCloud";
		
		spriteClass = KeKeSprite.class;
		HP = HT = 100;
		state = HUNTING;
		flying = true;
		ally=true;

		viewDistance = 6;
		properties.add(Property.ELEMENT);

	}

	public int lvl;
	public int lifespan; 
	
	private static final String LVL = "lvl";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LVL, lvl);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		lvl = bundle.getInt(LVL);
	}
	
	@Override
	protected boolean act() {
		timeToBomb --;
		if (timeToBomb == 0){
			destroy();
			sprite.die();
		}
		
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
	
	@Override
	public int hitSkill(Char target) {
		return 500;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20 + lvl, 30 + 5*lvl);
	}	
	
	@Override
	protected float attackDelay() {
		return 0.5f;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}	
	
	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((KeKeSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(4 + lvl, 12 + 3*lvl);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, this);
				//Buff.affect(enemy, Taunt.class,2f).object = id();
				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();
				damage(Random.NormalIntRange(20 + lvl, 30 + 5*lvl), this);
				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail( Messages.format(ResultDescriptions.MOB));
						//GLog.n(Messages.get(this, "zap_kill"));
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}	
	
	@Override
	public void call() {
		next();
	}	

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public boolean interact() {

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
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
	
}	
}
