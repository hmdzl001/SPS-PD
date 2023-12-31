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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Elevator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.PuddingCup;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.BurningFistSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.sprites.InfectingFistSprite;
import com.hmdzl.spspd.sprites.LarvaSprite;
import com.hmdzl.spspd.sprites.PinningFistSprite;
import com.hmdzl.spspd.sprites.RottingFistSprite;
import com.hmdzl.spspd.sprites.YogSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Yog extends Mob {

	{
		spriteClass = YogSprite.class;

		HP = HT = 2000;

		EXP = 50;

		state = PASSIVE;

		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}
	
	private static final int REGENERATION = 50;
	private int breaks=0;

	public boolean checkYear() {

		int yearAlive = 0;
		if (Dungeon.depth.mobs != null) {
			for (Mob mob : Dungeon.depth.mobs) {
				if (mob instanceof YearBeast) {
					yearAlive++;
				}
			}
		}
        return yearAlive++ > 0;
	}

	@Override
	public boolean act() {

		if( 4 - breaks > 5 * HP / HT ) {
			breaks++;
				int newPos = -1;
				for (int i = 0; i < 20; i++) {
					newPos = Dungeon.depth.randomRespawnCellMob();
					if (newPos != -1) {
						break;
					}
				}
				if (newPos != -1) {
					Actor.freeCell(pos);
					CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
					pos = newPos;
					sprite.place(pos);
					sprite.visible = Dungeon.visible[pos];
					GLog.n(Messages.get(this, "blink"));
				}

			if (breaks == 4 && !checkYear()){
				int newPos2;
				do {
					newPos2 = Random.Int(Floor.getLength());
				} while (!Floor.passable[newPos2]
						|| Floor.adjacent(newPos2, Dungeon.hero.pos)
						|| Actor.findChar(newPos2) != null);
				YearBeast.spawnAt(newPos2);
			}
			return true;
		}

		return super.act();
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
	private static int fistsCount = 0;

	public Yog() {
		super();
	}

	public void spawnFists() {
		RottingFist fist1 = new RottingFist();
		BurningFist fist2 = new BurningFist();
		PinningFist fist3 = new PinningFist();
		InfectingFist fist4 = new InfectingFist();

		
			fist1.pos = Dungeon.depth.randomRespawnCellMob();
			fist2.pos = Dungeon.depth.randomRespawnCellMob();
			fist3.pos = Dungeon.depth.randomRespawnCellMob();
			fist4.pos = Dungeon.depth.randomRespawnCellMob();
	
		GameScene.add(fist1);
		GameScene.add(fist2);
		GameScene.add(fist3);
		GameScene.add(fist4);
	}

	@Override
	public int drRoll() {
		
		int checkFists = 0;
		
		for (Mob mob : Dungeon.depth.mobs) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof PinningFist || mob instanceof InfectingFist) {
			checkFists++;	
			}
		}
		
		return 0+(30*checkFists);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(54, 96);
	}
	
	@Override
	public void damage(int dmg, Object src) {
		if (dmg > HP && (src instanceof Hero)) {
			dmg = 1;
		}
		super.damage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			int p = pos + Floor.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Floor.passable[p] || Floor.avoid[p])) {
				spawnPoints.add(p);
			}
		}

		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element(spawnPoints);

			GameScene.add(larva);
			Actor.addDelayed(new Pushing(larva, pos, larva.pos), -1);
		}

		for (Mob mob : Dungeon.depth.mobs) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof InfectingFist || mob instanceof PinningFist
					|| mob instanceof Larva) {
				mob.aggro(enemy);
			}
		}
		if (fistsCount<1){
			spawnFists();
			sprite.emitter().burst(ShadowParticle.UP, 2);
			this.damage(REGENERATION,this);
		}
		return super.defenseProc(enemy, damage);
	}

	@Override
	public void beckon(int cell) {
	}
	
	@Override
	public Item SupercreateLoot(){
			return new PuddingCup();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {

	    Dungeon.depth.unseal();
	
		for (Mob mob : (Iterable<Mob>) Dungeon.depth.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof Eye || mob instanceof PinningFist || mob instanceof InfectingFist) {
				mob.die(cause);
			}
		}
		Dungeon.depth.drop(new Elevator(), pos).sprite.drop();
		//Dungeon.level.drop(new Vault(), pos).sprite.drop();
		GameScene.bossSlain();
		Dungeon.depth.drop(new SkeletonKey(Dungeon.dungeondepth), pos).sprite.drop();
		//Dungeon.level.drop(new Gold(Random.Int(6000, 8000)), pos).sprite.drop();
		super.die(cause);

		yell(Messages.get(this, "die"));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}

    {

		resistances.add(EnchantmentDark.class);
		resistances.add(Terror.class);
		resistances.add(Amok.class);
		resistances.add(Charm.class);
		resistances.add(Sleep.class);
		resistances.add(Burning.class);
		resistances.add(ToxicGas.class);
		resistances.add(ScrollOfPsionicBlast.class);
		resistances.add(Vertigo.class);
	}

	public static class RottingFist extends Mob {

		private static final int REGENERATION = 50;

		{
			//name = "rotting fist";
			spriteClass = RottingFistSprite.class;

			HP = HT = 1500;
			evadeSkill = 25;

			EXP = 0;

			state = WANDERING;
			properties.add(Property.ELEMENT);
			properties.add(Property.BOSS);
		}

		public RottingFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(44, 76);
		}

		@Override
		public int drRoll() {
			return 35;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(3) == 0) {
				Buff.affect(enemy, Ooze.class).set(10f);
				enemy.sprite.burst(0xFF000000, 5);
			}

			return damage;
		}

		@Override
		public boolean act() {

			if (Floor.water[pos] && HP < HT) {
				sprite.emitter().burst(ShadowParticle.UP, 2);
				HP += REGENERATION;
			}

			return super.act();
		}

		{
			resistances.add(ToxicGas.class);
			resistances.add(EnchantmentDark.class);	
			resistances.add(Amok.class);
			resistances.add(Sleep.class);
			resistances.add(Terror.class);
			resistances.add(Poison.class);
			resistances.add(Vertigo.class);
			resistances.add(ToxicGas.class);
		}
	}

	public static class BurningFist extends Mob {

		{
			spriteClass = BurningFistSprite.class;

			HP = HT = 1000;
			evadeSkill = 25;

			EXP = 0;

			state = WANDERING;
			properties.add(Property.ELEMENT);
			properties.add(Property.BOSS);
		}

		public BurningFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(40, 52);
		}

		@Override
		public int drRoll() {
			return 25;
		}

		@Override
		protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}

		@Override
		public boolean attack(Char enemy) {

			if (!Floor.adjacent(pos, enemy.pos)) {
				spend(attackDelay());

				if (hit(this, enemy, true)) {

					int dmg = damageRoll();
					enemy.damage(dmg, this);

					enemy.sprite.bloodBurstA(sprite.center(), dmg);
					enemy.sprite.flash();

					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
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

			for (int i = 0; i < Floor.NEIGHBOURS9.length; i++) {
				GameScene.add(Blob.seed(pos + Floor.NEIGHBOURS9[i], 2,
						Fire.class));
			}

			return super.act();
		}

		{
			resistances.add(EnchantmentDark.class);
			resistances.add(Amok.class);
			resistances.add(Sleep.class);
			resistances.add(Terror.class);
			resistances.add(Burning.class);
			resistances.add(Fire.class);
			resistances.add(ScrollOfPsionicBlast.class);
			resistances.add(Vertigo.class);
			resistances.add(ToxicGas.class);
		}
	}
	
	public static class InfectingFist extends Mob {

		{
			spriteClass = InfectingFistSprite.class;

			HP = HT = 1500;
			evadeSkill = 25;

			EXP = 0;

			state = WANDERING;
			properties.add(Property.ELEMENT);
			properties.add(Property.BOSS);
		}

		public InfectingFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(44, 86);
		}

		@Override
		public int drRoll() {
			return 35;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(2) == 0) {
				Buff.affect(enemy, Poison.class).set(Random.Int(7, 9));
				state = FLEEING;
			}		

			return damage;
		}

		@Override
		public boolean act() {
					
			GameScene.add(Blob.seed(pos, 30, ToxicGas.class));

			return super.act();
		}

        {
			resistances.add(ToxicGas.class);
			resistances.add(EnchantmentDark.class);
			resistances.add(Amok.class);
			resistances.add(Sleep.class);
			resistances.add(Terror.class);
			resistances.add(Poison.class);
			resistances.add(Vertigo.class);
			resistances.add(ToxicGas.class);
		}

	}

	
	public static class PinningFist extends Mob {

		{
			spriteClass = PinningFistSprite.class;

			HP = HT = 1000;
			evadeSkill = 25;

			EXP = 0;

			state = WANDERING;
			properties.add(Property.ELEMENT);
			properties.add(Property.BOSS);
		}

		public PinningFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int hitSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(30, 42);
		}

		@Override
		public int drRoll() {
			return 25;
		}

		@Override
		protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
			return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
		}
		
		@Override
		protected boolean getCloser(int target) {
			if (state == HUNTING) {
				return enemySeen && getFurther(target);
			} else {
				return super.getCloser(target);
			}
		}

		@Override
		public boolean attack(Char enemy) {

			if (!Floor.adjacent(pos, enemy.pos)) {
				spend(attackDelay());

				if (hit(this, enemy, true)) {

					int dmg = damageRoll();
					enemy.damage(dmg, this);
					
					if(Random.Int(10)==0){
						Buff.prolong(enemy, Roots.class, 20);
			  		}

					enemy.sprite.bloodBurstA(sprite.center(), dmg);
					enemy.sprite.flash();

					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
						GLog.n(TXT_KILL, name);
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


		{
			resistances.add(ToxicGas.class);
			resistances.add(EnchantmentDark.class);
			resistances.add(Amok.class);
			resistances.add(Sleep.class);
			resistances.add(Terror.class);
			resistances.add(Burning.class);
			resistances.add(ScrollOfPsionicBlast.class);
			resistances.add(Vertigo.class);
		}

	}

	public static class Larva extends Mob {

		{
			spriteClass = LarvaSprite.class;

			HP = HT = 25;
			evadeSkill = 20;

			EXP = 0;

			state = HUNTING;
			properties.add(Property.UNKNOW);
		}

		@Override
		public int hitSkill(Char target) {
			return 30;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(15, 20);
		}

		@Override
		public int drRoll() {
			return 8;
		}

	}
}
