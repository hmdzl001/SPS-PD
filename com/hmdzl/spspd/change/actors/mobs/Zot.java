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

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.Yog.BurningFist;
import com.hmdzl.spspd.change.actors.mobs.Yog.InfectingFist;
import com.hmdzl.spspd.change.actors.mobs.Yog.Larva;
import com.hmdzl.spspd.change.actors.mobs.Yog.PinningFist;
import com.hmdzl.spspd.change.actors.mobs.Yog.RottingFist;
import com.hmdzl.spspd.change.actors.mobs.npcs.OtilukeNPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.BlastParticle;
import com.hmdzl.spspd.change.effects.particles.SmokeParticle;
import com.hmdzl.spspd.change.items.AdamantArmor;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.misc.AutoPotion.AutoHealPotion;
import com.hmdzl.spspd.change.items.misc.Spectacles.MagicSight;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.items.weapon.enchantments.Leech;
import com.hmdzl.spspd.change.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.change.items.weapon.missiles.JupitersWraith;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ZotSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Zot extends Mob {

	private static final int JUMP_DELAY = 10;

	{
		spriteClass = ZotSprite.class;
		baseSpeed = 0.5f;

		HP = HT = 25000;
		EXP = 20;
		defenseSkill = 40;
		
		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(100, 150);
	}

	@Override
	public int attackSkill(Char target) {
		return 200;
	}

	@Override
	public int dr() {
		return 60;
	}

	@Override
	protected boolean act() {
		
		if (paralysed > 0) {
			yell(Messages.get(this,"pain"));
			
			if(!checkEyes()){
				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

				for (int i = 0; i < Level.NEIGHBOURS4.length; i++) {
					int p = Dungeon.hero.pos + Level.NEIGHBOURS4[i];
					if (Actor.findChar(p) == null
							&& (Level.passable[p] || Level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0 && Random.Int(5) == 0) {
					MagicEye eye = new MagicEye();
					eye.pos = Random.element(spawnPoints);

					GameScene.add(eye);
					Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
				}
			}
			
			if (HP < HT) {
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				HP = HP + 5;
			}
		}

		
		boolean result = super.act();

		int regen = Dungeon.hero.buff(AutoHealPotion.class) != null ? 1 : Random.Int(20,50);
				
		
		if (HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + regen;			
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		
		Dungeon.level.locked=false;
		GameScene.bossSlain();		
		
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof ZotPhase || mob instanceof MagicEye) {
				mob.die(cause);
				mob.destroy();
				mob.sprite.killAndErase();
			}
		}
		
		super.die(cause);
		yell(Messages.get(this,"die"));
		OtilukeNPC.spawnAt(pos);					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 && Level.adjacent(pos, enemy.pos)) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		
		if (!checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				int p = pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				ZotPhase zot = new ZotPhase();
				zot.pos = Random.element(spawnPoints);

				GameScene.add(zot);
				Actor.addDelayed(new Pushing(zot, pos, zot.pos), -1);
			}
		}
		
		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.fieldOfView[newPos] || !Level.passable[newPos]
				|| Level.adjacent(newPos, enemy.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
	}
	
	private boolean checkPhases(){
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.level.mobs) {
			if (mob != null && mob instanceof ZotPhase) {
				phases++;
				if (Dungeon.hero.heroClass!=HeroClass.HUNTRESS && phases>6){
				check=true;
				}else if (phases>10){
				  check=true;
				}
		}			
	  }
		return check;
	}
	
	private boolean checkEyes(){
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.level.mobs) {
			if (mob != null && mob instanceof MagicEye) {
				phases++;
				if (phases>20){
				  check=true;
				}
		}			
	  }
		return check;
	}
	
	@Override
	public void damage(int dmg, Object src) {
		
		if(!checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				int p = Dungeon.hero.pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				MagicEye eye = new MagicEye();
				eye.pos = Random.element(spawnPoints);

				GameScene.add(eye);
				Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
			}
		}
		
		super.damage(dmg, src);
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"notice"));
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		IMMUNITIES.add(Leech.class);
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Paralysis.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
