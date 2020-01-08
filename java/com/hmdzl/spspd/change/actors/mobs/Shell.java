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

import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ShellSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Shell extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	{
		spriteClass = ShellSprite.class;

		HP = HT = 500;
		evadeSkill = 0;

		EXP = 50;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
		lootChance = 1f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}

	private int shellCharge=0;

	private static final String SHELLCHARGE	= "shellCharge";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( SHELLCHARGE, shellCharge );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		shellCharge = bundle.getInt( SHELLCHARGE );
	}


	@Override
	public void beckon(int cell) {
		// Do nothing
	}

	@Override
	public int damageRoll() {
		return 0;
	}

	@Override
	public void damage(int dmg, Object src) {
		if(shellCharge>0){zapAround(1);}
		super.damage(dmg, src);
	}
	
	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	protected boolean act() {

		if(Random.Int(shellCharge)>20 && Dungeon.hero.isAlive()){
			zapAll(1);
		}
		return super.act();
	}
	
	@Override
	public void call() {
		next();
	}
	

	@Override
	protected boolean canAttack(Char enemy) {		if (buff(Silent.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
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
				((ShellSprite) sprite).zap(enemy.pos);
			}			
			zapAll(10);
			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(Math.round(shellCharge/4), Math.round(shellCharge/2));
				shellCharge-=dmg;
				
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, this);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Messages.format(ResultDescriptions.MOB));
						//GLog.n(Messages.get(this, "kill"));
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}


	public void zapAll(int dmg){
		
		GLog.n(Messages.get(this, "zap"));
		
		int heroDmg=0;
		int mobDmg=Random.Int(1, 2+Math.round(dmg/4));
		
		for (Mob mob : Dungeon.level.mobs) {
				
			
		  if (Level.distance(pos, mob.pos) > 1 && mob.isAlive()){
			  boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[mob.pos];
			
			
			  if (visible) {
				((ShellSprite) sprite).zap(mob.pos);
			  }

			  mob.damage(mobDmg, this);

			  mob.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			  mob.sprite.flash();
			
			}
		}
		
		
		if (Dungeon.hero.isAlive()){
			
		       Char hero=Dungeon.hero;
		
		if (Level.distance(pos, hero.pos) > 1){
		
		boolean visibleHero = Level.fieldOfView[pos]
				|| Level.fieldOfView[hero.pos];
		if (visibleHero && Random.Int(4) > 2 ) {
			((ShellSprite) sprite).zap(hero.pos);
		}
		
		heroDmg = Random.Int(Math.round(shellCharge/4), Math.round(shellCharge/2));
		shellCharge-=heroDmg;

		hero.damage(heroDmg, this);

		hero.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
		hero.sprite.flash();
		
		}
		}
		

	}
	
public void zapAround(int dmg){
		
		GLog.n(Messages.get(this, "zap"));
		
		int heroDmg=0;
		int mobDmg=Random.Int(1, 2+Math.round(dmg/4));
		
		for (int n : Level.NEIGHBOURS8) {
			int c = pos + n;
			
			Char ch = Actor.findChar(c);
			if (ch != null && ch != Dungeon.hero && ch.isAlive()) {
				
					 boolean visible = Level.fieldOfView[pos]
							|| Level.fieldOfView[ch.pos];
					  
					  if (visible) {
						((ShellSprite) sprite).zap(ch.pos);
					  }
					
			  if (Level.water[ch.pos] && !ch.flying) {
				  mobDmg *= 1.5f;
			  }
			  ch.damage(mobDmg, this);

			  ch.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
			  ch.sprite.flash();
			
			  
			}  else if (ch != null && ch == Dungeon.hero && ch.isAlive()){
				
				heroDmg = Random.Int(shellCharge/2, shellCharge);
				
				if(dmg<shellCharge){
				    shellCharge-=dmg;
				} else {
					shellCharge=0;
				}
				
				
				boolean visible = Level.fieldOfView[pos]
						|| Level.fieldOfView[ch.pos];
				  
				  if (visible) {
					((ShellSprite) sprite).zap(ch.pos);
				  }

				ch.damage(heroDmg, this);

				ch.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				ch.sprite.flash();
				
			}
						
		}		

	}
	
	@Override
	public void add(Buff buff) {
	}
	
	@Override
	public void die(Object cause) {
		super.die(cause);
		shellCharge=0;
		
		
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(EnchantmentDark.class);
		
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Terror.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
