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
package com.hmdzl.spspd.change.actors.mobs.pets;

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.blobs.VenomGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.MagicalSleep;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.ElmoParticle;
import com.hmdzl.spspd.change.items.Heap;
import com.hmdzl.spspd.change.items.rings.RingOfHaste;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.sprites.HeroSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public abstract class PET extends NPC {

	{
		HP = HT = 1;
		EXP = 0;

		//WANDERING = new Wandering();
		//HUNTING = new Hunting();
		
		
		flying = true;
		hostile = false;
		state = HUNTING;
		enemy = null;
		ally=true;
		
		properties.add(Property.IMMOVABLE);
	}

	@Override
	public int drRoll(){
		return level;
	}

	@Override
	public int hitSkill(Char target) {
		return level + 10;
	}
	
	public int level;
	public int kills;
	public int type;
	public int experience;
	public int cooldown;
	public boolean callback = false;
	public boolean stay = false;
	/*
	 type
	 1 = 
	 2 = bee
	 3 = 
	 4 = 
	 5 = 

	 */
	
	private static final String KILLS = "kills";
	private static final String LEVEL = "level";
	private static final String TYPE = "type";
	private static final String EXPERIENCE = "experience";
	private static final String COOLDOWN = "cooldown";
	private static final String CALLBACK = "callback";
	private static final String STAY = "stay";


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(KILLS, kills);
		bundle.put(LEVEL, level);
		bundle.put(TYPE, type);
		bundle.put(EXPERIENCE, experience);
		bundle.put(COOLDOWN, cooldown);
		bundle.put(CALLBACK, callback);
		bundle.put(STAY, stay);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		kills = bundle.getInt(KILLS);
		level = bundle.getInt(LEVEL);
		type = bundle.getInt(TYPE);
		experience = bundle.getInt(EXPERIENCE);
		cooldown = bundle.getInt(COOLDOWN);
		callback = bundle.getBoolean(CALLBACK);
		stay = bundle.getBoolean(STAY);
		adjustStats(level);
	}
	
	protected void throwItem() {
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			int n;
			do {
				n = pos + Level.NEIGHBOURS8[Random.Int(8)];
			} while (!Level.passable[n] && !Level.avoid[n]);
			Dungeon.level.drop(heap.pickUp(), n).sprite.drop(pos);
		}
	}
	
	public void adjustStats(int level) {
	}
	
	public void spawn(int level) {
		this.level = level;
        adjustStats(level);
	}
	
	@Override
	protected boolean act() {
		
		assignPet(this);
		
		if (experience >= level*(level+1) && level < 30){
			experience-=level*(level+1);
			level++;
			GLog.p(Messages.get(this,"levelup"));
			adjustStats(level);

		}
		
		if ( HP<HT){HP+=1;}
		
		return super.act();
	}
	
	
	@Override
	public void damage(int dmg, Object src) {
		
		if (src instanceof Hero){
			//goaways++;
			//GLog.n(Messages.get(this,"warning",name));
			dmg = 0;
		}
		
		super.damage(dmg,src);
		
	}
	
	@Override
	public void die(Object cause) {
		super.die(cause);	 
		Dungeon.hero.haspet=false;
		Dungeon.hero.petCount++;
	    GLog.n(Messages.get(this,"pet_died"));
	}

	@Override
	protected Char chooseEnemy() {
		if(enemy != null && !enemy.isAlive() && enemy instanceof Mob){
			kills++;
			experience+=((Mob)enemy).getExp();
		}
		
			if (enemy == null
					|| !enemy.isAlive()
					|| !Dungeon.level.mobs.contains(enemy)
					|| Dungeon.level.distance(enemy.pos, Dungeon.hero.pos) > 8
					|| state == WANDERING) {
				
				HashSet<Mob> enemies = new HashSet<>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile
							&& Level.fieldOfView[mob.pos]
							&& Dungeon.level.distance(mob.pos, Dungeon.hero.pos) <= 8
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				
				//go for closest enemy
				Char closest = null;
				for (Char curr : enemies){
					if (closest == null
							|| Dungeon.level.distance(pos, curr.pos) < Dungeon.level.distance(pos, closest.pos)){
						closest = curr;
					}
				}
				return closest;
			}
			return enemy;
}
	
	@Override
	protected boolean getCloser(int target) {
		if (stay) {
			return false;
		} else if (state == WANDERING
			|| Level.distance(target, Dungeon.hero.pos) > 6)
			this.target = target = Dungeon.hero.pos;
		return super.getCloser(target);
	}

	public int wanderLocation(){
		  int newPos = -1;
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS8) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
			
		return newPos;
	}
	
	@Override
	public void aggro(Char ch) {		
	}
	
	@Override
	public void beckon(int cell) {
	}


	private void assignPet(PET pet){
		
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		  Dungeon.hero.petKills=pet.kills;	
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;		
	}

	@Override
	public void add( Buff buff ) {
		//in other words, can't be directly affected by buffs/debuffs.
	}	
	

	@Override
	public boolean interact() {
			//int curPos = pos;
			//moveSprite( pos, Dungeon.hero.pos );
			//move( Dungeon.hero.pos );
			//Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
			//Dungeon.hero.move( curPos );
			//Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
			//Dungeon.hero.busy();



		if (!Dungeon.level.passable[pos]){
			return true;
		}

		if (state == SLEEPING) {
			state = HUNTING;
		}
			int curPos = pos;

			moveSprite( pos, Dungeon.hero.pos );
			move( Dungeon.hero.pos );

			Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
			Dungeon.hero.move( curPos );

			Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
			Dungeon.hero.busy();
			return true;

	}	
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( ToxicGas.class );
		IMMUNITIES.add( VenomGas.class );
		IMMUNITIES.add( Burning.class );
		IMMUNITIES.add( ScrollOfPsionicBlast.class );
		IMMUNITIES.add( CorruptGas.class );
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}	
	
		/*private class Wandering extends Mob.Wandering {
			
			@Override
			public boolean act( boolean enemyInFOV, boolean justAlerted ) {
				if ( enemyInFOV ) {
					
					enemySeen = true;
					
					notice();
					state = HUNTING;
					target = enemy.pos;
					
				} else {
					
					enemySeen = false;
					
					int oldPos = pos;
					//always move towards the hero when wandering
					if (getCloser( target = Dungeon.hero.pos )) {
						//moves 2 tiles at a time when returning to the hero from a distance
						if (!Dungeon.level.adjacent(Dungeon.hero.pos, pos)){
							getCloser( target = Dungeon.hero.pos );
						}
						spend( 1 / speed() );
						return moveSprite( oldPos, pos );
					} else {
						spend( TICK );
					}
					
				}
				return true;
			}
			
		}
		
		private class Hunting extends Mob.Hunting {
			
			@Override
			public boolean act( boolean enemyInFOV, boolean justAlerted ) {
				
				enemySeen = enemyInFOV;
				if (enemyInFOV && !isCharmedBy( enemy ) && canAttack( enemy )) {
					
					return doAttack( enemy );
					
				} else {
					
					if (enemyInFOV) {
						target = enemy.pos;
					}
					
					int oldPos = pos;
					if (enemyInFOV && getCloser( target )) {
						
						spend( 1 / speed() );
						return moveSprite( oldPos,  pos );
						
					} else {
						
						//don't lose a turn due to the transition, immediately act instead
						state = WANDERING;
						return state.act( false, justAlerted );
						
					}
				}
			}
			
		}*/
}