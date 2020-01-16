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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.FrostGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.ShamanSprite;
import com.hmdzl.spspd.change.sprites.UGooSprite;
import com.hmdzl.spspd.change.utils.GLog;

import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.items.weapon.melee.special.Handcannon;


public class UGoo extends Mob {

	protected static final float SPAWN_DELAY = 2f;
	private static final String TXT_UNKNOW = "??? I know nothing about it ???";

	{
		spriteClass = UGooSprite.class;
		baseSpeed = 0.25f;

		HP = HT = 1000;
		EXP = 20;
		evadeSkill = 5;
		
		loot = new Handcannon();
		lootChance = 1f;

		properties.add(Property.ELEMENT);
		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}

	private int breaks=0;
	private static int GoosCount = 0;
	
	public void spawnGoos() {
		EarthGoo goo1 = new EarthGoo();
		FireGoo goo2 = new FireGoo();
		ShockGoo goo3 = new ShockGoo();
		IceGoo goo4 = new IceGoo();

		
			goo1.pos = Dungeon.level.randomRespawnCellMob();
			goo2.pos = Dungeon.level.randomRespawnCellMob();
			goo3.pos = Dungeon.level.randomRespawnCellMob();
			goo4.pos = Dungeon.level.randomRespawnCellMob();
	
		GameScene.add(goo1);
		GameScene.add(goo2);
		GameScene.add(goo3);
		GameScene.add(goo4);
	}		
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
	}

	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}
	
	@Override
	public float speed() {
		if (breaks == 3) return 6*super.speed();
		else return super.speed();
	}

    private static final String BREAKS	= "breaks";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( BREAKS, breaks );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        breaks = bundle.getInt( BREAKS );
    }	
	
	@Override
    public boolean act() {

        if( 3 - breaks > 4 * HP / HT ) {
            
			if (GoosCount==0){
				spawnGoos();
			}
			breaks++;
			
            return true;
        } 
		
	    if (breaks == 1){
		   
		}
		
		if (breaks == 2){
			
		}
		
		if (breaks > 0){

		}
        return super.act();
    }	
	
	@Override
	public void damage(int dmg, Object src) {
	
		if (GoosCount > 0){
			dmg = Random.Int(0,1);
		} else dmg = Random.Int(10,20);
		if (dmg > 15){
			GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
		}
		super.damage(dmg, src);
	}		
	
	@Override
	public void die(Object cause) {
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof FireGoo || mob instanceof EarthGoo || mob instanceof Eye || mob instanceof ShockGoo || mob instanceof IceGoo) {
				mob.die(cause);
			}
		}
		
		GameScene.bossSlain();
		super.die(cause);
		UTengu.spawnAt(pos);
	}	
	
	public static UGoo spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			UGoo w = new UGoo();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);
			GoosCount = 0;

			return w;
  			
		} else {
			return null;
		}
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		IMMUNITIES.add(EnchantmentDark.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Paralysis.class);
	    IMMUNITIES.add(Bleeding.class);
		IMMUNITIES.add(CorruptGas.class);
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	
	public static class EarthGoo extends Mob {

		{
			spriteClass =UGooSprite.EarthSpawnSprite.class;

			HP = HT = 10;
			evadeSkill = 5;
			baseSpeed = 0.75f;

			EXP = 0;

			state = WANDERING;

			properties.add(Property.ELEMENT);
			properties.add(Property.UNKNOW);
			properties.add(Property.MINIBOSS);
		}

		public EarthGoo() {
			super();
			GoosCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			GoosCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 10;
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
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(5) == 0) {
				Buff.affect(enemy, Ooze.class);
				enemy.sprite.burst(0xFF000000, 5);
			}
			if (Random.Int(5) == 0) {
				Buff.prolong(enemy, Roots.class,2f);
				enemy.sprite.burst(0xFF000000, 5);
			}

			return damage;
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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class FireGoo extends Mob {

		{
			spriteClass = UGooSprite.FireSpawnSprite.class;

			HP = HT = 10;
			evadeSkill = 5;
			baseSpeed = 0.75f;

			EXP = 0;

			state = WANDERING;

			properties.add(Property.ELEMENT);
			properties.add(Property.UNKNOW);
			properties.add(Property.MINIBOSS);
		}

		public FireGoo() {
			super();
			GoosCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			GoosCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 10;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(0, 1);
		}

		@Override
		public int drRoll() {
			return 2;
		}

		@Override
		protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}

		@Override
		public boolean attack(Char enemy) {

			if (!Level.adjacent(pos, enemy.pos)) {
				spend(attackDelay());

				if (hit(this, enemy, true)) {

					int dmg = damageRoll();
					enemy.damage(dmg, this);

					enemy.sprite.bloodBurstA(sprite.center(), dmg);
					enemy.sprite.flash();

					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail(Messages.format(ResultDescriptions.MOB));
						//GLog.n(TXT_KILL, name);
					}
					return true;

				} else {

					enemy.sprite.showStatus(CharSprite.NEUTRAL,
							enemy.defenseVerb());
					return false;
				}
			} else {
				return super.attack(enemy);
			}
		}

		@Override
		public boolean act() {

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				GameScene.add(Blob.seed(pos + Level.NEIGHBOURS9[i], 2,
						Fire.class));
			}

			return super.act();
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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(ScrollOfPsionicBlast.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class IceGoo extends Mob {	

		{
			spriteClass = UGooSprite.IceSpawnSprite.class;

			HP = HT = 10;
			evadeSkill = 5;
			baseSpeed = 0.75f;

			EXP = 0;

			state = FLEEING;

			properties.add(Property.ELEMENT);
			properties.add(Property.UNKNOW);
			properties.add(Property.MINIBOSS);
		}

		public IceGoo() {
			super();
			GoosCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			GoosCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 10;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(0, 1);
		}

		@Override
		public int drRoll() {
			return 2;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			return damage;
		}

		@Override
		public boolean act() {

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
					GameScene.add(Blob.seed(pos + Level.NEIGHBOURS9[i], 2,
							FrostGas.class));
				}

				return super.act();
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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
			IMMUNITIES.add(FrostGas.class);

		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	
	public static class ShockGoo extends Mob {
		private static final float TIME_TO_ZAP = 2f;
		{
			spriteClass = UGooSprite.ShockSpawnSprite.class;

			HP = HT = 10;
			evadeSkill = 5;
			baseSpeed = 0.75f;

			EXP = 0;

			state = WANDERING;

			properties.add(Property.ELEMENT);
			properties.add(Property.UNKNOW);
			properties.add(Property.MINIBOSS);
		}

		public ShockGoo() {
			super();
			GoosCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			GoosCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 10;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(0, 1);
		}

		@Override
		public int drRoll() {
			return 2;
		}

		@Override
		protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}

		@Override
		public void damage(int dmg, Object src) {
			GameScene.add(Blob.seed(pos, 5, ElectriShock.class));
			super.damage(dmg, src);
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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(ScrollOfPsionicBlast.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ElectriShock.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
}	