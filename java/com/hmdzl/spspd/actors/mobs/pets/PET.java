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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.NmGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.blobs.VenomGas;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.HasteBuff;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.SpeedUp;
import com.hmdzl.spspd.actors.buffs.WatchOut;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndPetInfo;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

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
		oldcooldown = 10;

		properties.add(Property.MINIBOSS);
		properties.add(Property.IMMOVABLE);
	}

    public PET() {
        super();
        updateStats();
    }

    public void updateStats(){

    }
    public void spawn() {
        updateStats();
    }

	@Override
	public int drRoll(){
		return 0;
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 10;
	}
	
	public int type;
	public int cooldown;
	public int oldcooldown;
	public boolean callback = false;
	public boolean stay = false;
	
	private static final String TYPE = "type";
	private static final String COOLDOWN = "cooldown";
	private static final String CALLBACK = "callback";
	private static final String STAY = "stay";


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TYPE, type);
		bundle.put(COOLDOWN, cooldown);
		bundle.put(CALLBACK, callback);
		bundle.put(STAY, stay);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		type = bundle.getInt(TYPE);
		cooldown = bundle.getInt(COOLDOWN);
		callback = bundle.getBoolean(CALLBACK);
		stay = bundle.getBoolean(STAY);
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
	
	@Override
	protected boolean act() {
		
		if (Dungeon.depth != 50)
		{ assignPet(this); }
		
		if ( HP<HT){HP+=Dungeon.hero.petLevel;}
		
		return super.act();
	}

	public boolean lovefood(Item item) {
		return item instanceof PetFood;
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
		Dungeon.hero.petType = 1;
		//Statistics.petDies++;
	    GLog.n(Messages.get(this,"pet_died"));
	}

	@Override
	protected Char chooseEnemy() {
			if (enemy == null
					|| !enemy.isAlive()
					|| !Dungeon.level.mobs.contains(enemy)
					|| Level.distance(enemy.pos, Dungeon.hero.pos) > 8
					|| state == WANDERING) {
				
				HashSet<Mob> enemies = new HashSet<>();
				for (Mob mob : Dungeon.level.mobs) {
					if (mob.hostile
							&& Level.fieldOfView[mob.pos]
							&& Level.distance(mob.pos, Dungeon.hero.pos) <= 8
							&& mob.state != mob.PASSIVE) {
						enemies.add(mob);
					}
				}
				
				//go for closest enemy
				Char closest = null;
				for (Char curr : enemies){
					if (closest == null
							|| Level.distance(pos, curr.pos) < Level.distance(pos, closest.pos)){
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

	@Override
	public void aggro(Char ch) {		
	}
	
	@Override
	public void beckon(int cell) {
	}


	private void assignPet(PET pet){
		
		  Dungeon.hero.petType=pet.type;

		  Dungeon.hero.petHP=pet.HP;

		  Dungeon.hero.petCooldown=pet.cooldown;		
	}

	@Override
	public void add( Buff buff ) {
		if (buff instanceof AttackUp ||
				buff instanceof DefenceUp ||
				buff instanceof ShieldArmor ||
				buff instanceof MagicArmor ||
				buff instanceof HasteBuff ||
				buff instanceof SpeedUp ||
				buff instanceof HasteBuff||
				buff instanceof HiddenShadow ||
				buff instanceof WatchOut) {
			super.add(buff);
		} else {

		}
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
		if (!Level.passable[pos]){
			return true;
		}
		if (state == SLEEPING) {
			state = HUNTING;
		}
		GameScene.show(new WndPetInfo( this));
		return true;
	}

	public boolean changeplace(){
		int curPos = pos;

		moveSprite( pos, Dungeon.hero.pos );
		move( Dungeon.hero.pos );

		Dungeon.hero.sprite.move( Dungeon.hero.pos, curPos );
		Dungeon.hero.move( curPos );

		Dungeon.hero.spend( 1 / Dungeon.hero.speed() );
		Dungeon.hero.busy();
		return true;

	}

	public void dropreward(){
		if (cooldown < 4) {
			Item loot = this.SupercreateLoot();
			Dungeon.level.drop(loot,pos).sprite.drop();
			cooldown = this.oldcooldown;
		} else GLog.n(Messages.get(this,"pet_not_ready"));;
	}
	
	{
		immunities.add( ToxicGas.class );
		immunities.add( VenomGas.class );
		immunities.add( Burning.class );
		immunities.add( ScrollOfPsionicBlast.class );
		immunities.add( CorruptGas.class );
		immunities.add( NmGas.class );
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