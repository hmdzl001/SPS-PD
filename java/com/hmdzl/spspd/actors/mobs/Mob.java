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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Corruption;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.Feed;
import com.hmdzl.spspd.actors.buffs.Hunger;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.SoulMark;
import com.hmdzl.spspd.actors.buffs.SpAttack;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Surprise;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.misc.LuckyBadge;
import com.hmdzl.spspd.items.misc.PPC;
import com.hmdzl.spspd.items.misc.Shovel;
import com.hmdzl.spspd.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Level.Feeling;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.utils.GLog;

import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public abstract class Mob extends Char {
	
	{
		name = Messages.get(this, "name");
	}
	
	private static final String TXT_DIED = "You hear something died in the distance";

	protected static final String TXT_NOTICE1 = "?!";
	protected static final String TXT_RAGE = "#$%^";
	protected static final String TXT_EXP = "%+dEXP";

	public AiState SLEEPING = new Sleeping();
	public AiState HUNTING = new Hunting();
	public AiState WANDERING = new Wandering();
	public AiState FLEEING = new Fleeing();
	public AiState PASSIVE = new Passive();
	public AiState state = SLEEPING;

	public Class<? extends CharSprite> spriteClass;

	protected int target = -1;

	protected int evadeSkill = 0;

	//public int EXP = 1;
	protected int EXP = 1;
	//public int maxLvl = 100;
	protected int maxLvl = 100;
	protected int dewLvl = 1;

	protected Char enemy;
	protected boolean enemySeen;
	protected boolean alerted = false;

	protected static final float TIME_TO_WAKE_UP = 1f;

	public boolean hostile = true;
	public boolean ally = false;
	public boolean originalgen = false;

	private static final String STATE = "state";
	private static final String SEEN = "seen";
	private static final String TARGET = "target";
	private static final String ORIGINAL = "originalgen";
	
	public int getExp(){
		return EXP;
	}
	
	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		if (state == SLEEPING) {
			bundle.put(STATE, Sleeping.TAG);
		} else if (state == WANDERING) {
			bundle.put(STATE, Wandering.TAG);
		} else if (state == HUNTING) {
			bundle.put(STATE, Hunting.TAG);
		} else if (state == FLEEING) {
			bundle.put(STATE, Fleeing.TAG);
		} else if (state == PASSIVE) {
			bundle.put(STATE, Passive.TAG);
		}
		bundle.put(SEEN, enemySeen);
		bundle.put(TARGET, target);
		bundle.put(ORIGINAL, originalgen);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		String state = bundle.getString(STATE);
		if (state.equals(Sleeping.TAG)) {
			this.state = SLEEPING;
		} else if (state.equals(Wandering.TAG)) {
			this.state = WANDERING;
		} else if (state.equals(Hunting.TAG)) {
			this.state = HUNTING;
		} else if (state.equals(Fleeing.TAG)) {
			this.state = FLEEING;
		} else if (state.equals(Passive.TAG)) {
			this.state = PASSIVE;
		}

		enemySeen = bundle.getBoolean(SEEN);

		target = bundle.getInt(TARGET);
		
		originalgen = bundle.getBoolean(ORIGINAL);
	}

	public CharSprite sprite() {
		CharSprite sprite = null;
		try {
			sprite = spriteClass.newInstance();
		} catch (Exception e) {
		}
		return sprite;
	}

	@Override
	protected boolean act() {

		super.act();

		boolean justAlerted = alerted;
		alerted = false;

		//sprite.hideAlert();
		if (justAlerted){
			sprite.showAlert();
		} else {
			sprite.hideAlert();
			sprite.hideLost();
		}		
		

		if (paralysed > 0) {
			enemySeen = false;
			spend(TICK);
			return true;
		}

		enemy = chooseEnemy();

		if (!(this instanceof NPC)){
			if (enemy != null) {
				ArrayList<Integer> candidates = new ArrayList<Integer>();
				for (int n : Level.NEIGHBOURS8) {
					int cell = enemy.pos + n;
					if ((Level.passable[cell] || Level.avoid[cell])) {
						candidates.add(cell);
					}
				}
				if (candidates.size() > 0 && this.pos == enemy.pos) {
					int newPos = Random.element(candidates);
					Actor.addDelayed(new Pushing(enemy, enemy.pos, newPos), -1);
					enemy.pos = newPos;
				}
			}
		}
		boolean enemyInFOV = enemy != null && enemy.isAlive()
				&& Level.fieldOfView[enemy.pos] && enemy.invisible<=0 ;

		return state.act(enemyInFOV, justAlerted);

	}

	protected Char chooseEnemy() {

		Terror terror = buff( Terror.class );
		if (terror != null) {
			Char source = (Char)Actor.findById( terror.object );
			if (source != null) {
				return source;
			}
		}

		//find a new enemy if..
		boolean newEnemy = false;
		//we have no enemy, or the current one is dead
		if ( enemy == null || !enemy.isAlive() || state == WANDERING)
			newEnemy = true;
			//We are corrupted, and current enemy is either the hero or another corrupted character.
		else if (buff(Corruption.class) != null && (enemy == Dungeon.hero || enemy.buff(Corruption.class) != null))
			newEnemy = true;
			//We are amoked and current enemy is the hero
		else if (buff( Amok.class ) != null && enemy == Dungeon.hero)
			newEnemy = true;
	
		if ( newEnemy ) {

			HashSet<Char> enemies = new HashSet<>();

			//if the mob is corrupted...
			if ( buff(Corruption.class) != null) {

				//look for enemy mobs to attack, which are also not corrupted
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.hostile && mob.buff(Corruption.class) == null)
						enemies.add(mob);
				if (enemies.size() > 0) return Random.element(enemies);

				//otherwise go for nothing
				return this;

				//if the mob is amoked...
			} else if ( buff(Amok.class) != null) {

				//try to find an enemy mob to attack first.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.hostile)
						enemies.add(mob);
				if (enemies.size() > 0) return Random.element(enemies);

				//try to find ally mobs to attack second.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.ally)
						enemies.add(mob);
				if (enemies.size() > 0) return Random.element(enemies);

					//if there is nothing, go for the hero
				else return Dungeon.hero;

			} else {

				//try to find ally mobs to attack.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.ally)
						enemies.add(mob);

				//and add the hero to the list of targets.
				enemies.add(Dungeon.hero);

				//go after the closest enemy, preferring the hero if two are equidistant
			//	Char closest = null;
				//for (Char curr : enemies){
					//if (closest == null
						//	|| Dungeon.level.distance(pos, curr.pos) < Dungeon.level.distance(pos, closest.pos)
						//	|| Dungeon.level.distance(pos, curr.pos) == Dungeon.level.distance(pos, closest.pos) && curr == Dungeon.hero){
						//closest = curr;
					//}
				//}
				//return closest;
				return Random.element(enemies);

			}

		} else
			return enemy;
		
		// resets target if: the target is dead, the target has been lost
		// (wandering)
		// or if the mob is amoked and targeting the hero (will try to target
		// something else)
		/*if (enemy != null && !enemy.isAlive() || state == WANDERING
				|| (buff(Amok.class) != null && enemy == Dungeon.hero) 
				|| (buff(Corruption.class) != null && enemy == Dungeon.hero))
			enemy = null;
		// if there is no current target, find a new one.
		if (enemy == null) {
			HashSet<Char> enemies = new HashSet<Char>();
			// if the mob is amoked...
			if (buff(Amok.class) != null) {
				// try to find an enemy mob to attack first.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos]
							&& mob.hostile)
						enemies.add(mob);
				if (enemies.size() > 0)
					return Random.element(enemies);
				// try to find ally mobs to attack second.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.ally)
						enemies.add(mob);
				if (enemies.size() > 0)
					return Random.element(enemies);

				// if there is nothing, go for the hero.
				return Dungeon.hero;

				// if the mob is Corruption...
			} else if (buff(Corruption.class) != null) {
				// try to find an enemy mob to attack first.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos]
							&& mob.hostile)
						enemies.add(mob);
				if (enemies.size() > 0)
					return Random.element(enemies);

				// try to find ally mobs to attack second.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && !mob.ally)
						enemies.add(mob);
				if (enemies.size() > 0)
					return Random.element(enemies);

				// if there is nothing, go for the itself.
				return null;
				// if the mob is not amoked...
			} else {
				// try to find ally mobs to attack.
				for (Mob mob : Dungeon.level.mobs)
					if (mob != this && Level.fieldOfView[mob.pos] && mob.ally)
						enemies.add(mob);
				// and add the hero to the list of targets.
				enemies.add(Dungeon.hero);
				// target one at random.
				return Random.element(enemies);

			}

		} else
			return enemy;		
		*/
		
	}

	protected boolean moveSprite(int from, int to) {

		if (sprite.isVisible()
				&& (Dungeon.visible[from] || Dungeon.visible[to])) {
			sprite.move(from, to);
			return true;
		} else {
			sprite.place(to);
			return true;
		}
	}

	@Override
	public void add(Buff buff) {
		super.add(buff);
		if (buff instanceof Amok) {
			if (sprite != null) {
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(this, "rage"));
			}
			state = HUNTING;
		} else if (buff instanceof Terror) {
			state = FLEEING;
		} else if (buff instanceof Sleep) {
			state = SLEEPING;
			this.sprite().showSleep();
			postpone(Sleep.SWS);
		}
	}

	@Override
	public void remove(Buff buff) {
		super.remove(buff);
		if (buff instanceof Terror) {
			sprite.showStatus(CharSprite.NEGATIVE,Messages.get(this, "rage"));
			state = HUNTING;
		}
	}

	protected boolean canAttack(Char enemy) {
        return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy) && (buff(Disarm.class) == null));
	}

	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Level.passable,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Level.passable,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void updateSpriteState() {
		super.updateSpriteState();
		if (Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class) != null)
			sprite.add(CharSprite.State.PARALYSED);
	}

	@Override
	public void move(int step) {
		super.move(step);

		if (!flying) {
			Dungeon.level.mobPress(this);
		}
	}

	protected float attackDelay() {
		return 1f;
	}

	protected boolean doAttack(Char enemy) {

		boolean visible = Dungeon.visible[pos];

		if (visible) {
			sprite.attack(enemy.pos);
		} else {
			attack(enemy);
		}

		spend(attackDelay());

		return !visible;
	}

	@Override
	public void onAttackComplete() {
		attack(enemy);
		super.onAttackComplete();
	}

	@Override
	public int evadeSkill(Char enemy) {
		if (enemySeen && (paralysed == 0)) {
			int evadeSkill = this.evadeSkill;
			int penalty = 0;
			for (Buff buff : enemy.buffs(RingOfAccuracy.Accuracy.class)) {
				penalty += ((RingOfAccuracy.Accuracy) buff).level;
			}
			if (penalty != 0 && enemy == Dungeon.hero)
				evadeSkill *= Math.pow(0.75, penalty);
			return evadeSkill;
		} else {
			return 0;
		}
	}

	@Override
	public int drRoll() {
		return 0;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (!enemySeen && enemy == Dungeon.hero) {
			if (((Hero)enemy).subClass == HeroSubClass.ASSASSIN) {
				damage *= 1.5f;
				Wound.hit(this);
			} else {
				Surprise.hit(this);
			}
		} 

		SpAttack spatk = enemy.buff(SpAttack.class);

		if (HP == HT && spatk != null) {
				damage *= 3f;
		} 

		if (HP < HT/4 && spatk != null) {
			damage *= 1.5f;
		}

		if (buff(SoulMark.class) != null) {
			int restoration = Math.max(damage, HP);
			//Dungeon.hero.buff(Hunger.class).satisfy(restoration*0.5f);
			Dungeon.hero.HP = (int)Math.ceil(Math.min(Dungeon.hero.HT, Dungeon.hero.HP+(restoration*0.15f)));
			Dungeon.hero.sprite.emitter().burst( Speck.factory(Speck.HEALING), 1 );
			if (Dungeon.hero.petHP < Dungeon.hero.HT)
			Dungeon.hero.petHP += (int)Math.ceil(restoration*0.10f);
		}
		
		return damage;
	}

	public void aggro(Char ch) {
		enemy = ch;
		if (state != PASSIVE){
			state = HUNTING;
		}		
	}
	
	public int adj(int type){
		
		int adjustment;
				
	  if (type == 0){
		adjustment = Dungeon.depth;
	  } else if (type == 1){
		adjustment = (int) Dungeon.depth/2;
	  } else if (type == 2){
		 adjustment = (int) Dungeon.depth/4;
	  } else if (type == 3){
		 adjustment = (int) Dungeon.depth*2;
	  } else adjustment = 1;

		return adjustment;
	}

	@Override
	public void damage(int dmg, Object src) {

		Terror.recover(this);

		if (state == SLEEPING) {
			state = WANDERING;
		}
		
		if (state != HUNTING) {
			alerted = true;
		}
		//alerted = true;

		super.damage(dmg, src);
	}

	@Override
	public void destroy() {

		super.destroy();

		Dungeon.level.mobs.remove(this);

		if (Dungeon.hero.isAlive()) {

			if (hostile) {
				Statistics.enemiesSlain++;
				Badges.validateMonstersSlain();
				Statistics.qualifiedForNoKilling = false;

				if (Dungeon.level.feeling == Feeling.DARK) {
					Statistics.nightHunt++;
				} else {
					Statistics.nightHunt = 0;
				}
				Badges.validateNightHunter();
				
			}
			
			if(Dungeon.hero.heroClass == HeroClass.PERFORMER){
			Buff.affect(Dungeon.hero,Rhythm.class,10);
			
		   Shovel shovel = Dungeon.hero.belongings.getItem(Shovel.class);
		   if (shovel!=null && shovel.charge<shovel.fullCharge) {shovel.charge+=5;}
			//Buff.affect(Dungeon.hero,GlassShield.class).turns(3)
			}
		if(Dungeon.hero.subClass == HeroSubClass.SUPERSTAR){
			//Buff.affect(Dungeon.hero,Rhythm.class,50);
			Buff.affect(Dungeon.hero,Rhythm2.class,10);
			
		}
		if(Dungeon.hero.buff(Feed.class)!=null){
			Dungeon.hero.HT++;
			//Buff.affect(Dungeon.hero,GlassShield.class).turns(3)
		}
	
		if (Dungeon.hero.lvl <= maxLvl && EXP > 0) {
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "exp", EXP));
			Dungeon.hero.earnExp(EXP);
		} else if (EXP > 0) {
			EXP = 1;
			Dungeon.hero.sprite.showStatus(CharSprite.POSITIVE, Messages.get(this, "exp", EXP));
			Dungeon.hero.earnExp(EXP);
		   }
		}
	}
	
	public boolean checkOriginalGenMobs (){
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (mob.originalgen){return true;}
		 }	
		return false;
	}

	@Override
	public void die(Object cause) {

		super.die(cause);
		
		int generation=0;
		
		if(this instanceof Swarm){
			Swarm swarm = (Swarm) this;
			generation=swarm.generation;
		}
		
		if(this instanceof Virus){
			Virus virus = (Virus) this;
			generation=virus.generation;
		}

		if(this instanceof SandMob.MiniSand){
			SandMob.MiniSand mnsand = (SandMob.MiniSand) this;
			generation=mnsand.generation;
		}
		
		if (Dungeon.hero.buff(Dewcharge.class) != null && generation==0) {
			explodeDewHigh(pos);
		}
		
		if (!Dungeon.level.cleared && originalgen && !checkOriginalGenMobs() && Dungeon.depth>2 
		&& Dungeon.depth<25 && !Dungeon.bossLevel(Dungeon.depth) && (Dungeon.dewDraw || Dungeon.dewWater)){
			Dungeon.level.cleared=true;
			GameScene.levelCleared();		
			if(Dungeon.depth>0){Statistics.prevfloormoves=Math.max(Dungeon.pars[Dungeon.depth]-Dungeon.level.currentmoves,0);
			   if (Statistics.prevfloormoves>1){
			     GLog.h(Messages.get(this, "clear1"), Statistics.prevfloormoves);
			   } else if (Statistics.prevfloormoves==1){
			     GLog.h(Messages.get(this, "clear2")); 
			   } else if (Statistics.prevfloormoves==0){
				 GLog.h(Messages.get(this, "clear3"));
			   }
			} 
		}

		float lootChance = this.lootChance;
		float lootChanceOther = this.lootChanceOther;
		int bonus = 0;
		for (Buff buff : Dungeon.hero.buffs(LuckyBadge.GreatLucky.class)) {
			bonus += ((LuckyBadge.GreatLucky) buff).level;
		}
		if (Dungeon.hero.heroClass == HeroClass.SOLDIER)
			bonus += 5;
		if (Dungeon.hero.subClass == HeroSubClass.SUPERSTAR) {
			bonus += 3;
		}
		if (Dungeon.hero.buff(AflyBless.class)!=null) {
			bonus += 3;
		}		
		
		lootChance += 0.02*bonus;
		lootChanceOther += 0.02*bonus;

		if (Random.Float() < lootChance && Dungeon.hero.lvl <= maxLvl + 800) {
			Item loot = createLoot();
			if (loot != null)
				Dungeon.level.drop(loot, pos).sprite.drop();

		} else if (Random.Float() < lootChanceOther
				&& Dungeon.hero.lvl <= maxLvl + 800) {
			Item lootOther = createLootOther();
			if (lootOther != null)
				Dungeon.level.drop(lootOther, pos).sprite.drop();
		}

		if (Dungeon.hero.isAlive() && !Dungeon.visible[pos]) {
			GLog.i(Messages.get(this, "died"));
		}
		
		PPC ppc = Dungeon.hero.belongings.getItem(PPC.class);
		if (ppc!=null) {ppc.charge++;}
		
		if (Dungeon.isChallenged(Challenges.NIGHTMARE_VIRUS) && !(this instanceof Virus)) {
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

				Virus virus = new Virus();
				virus.pos = Random.element(candidates);
				virus.state = virus.HUNTING;

				if (Dungeon.level.map[virus.pos] == Terrain.DOOR) {
					Door.enter(virus.pos);
				}

				GameScene.add(virus, 1f);
				Actor.addDelayed(new Pushing(virus, pos, virus.pos), -1);
			}			
			
		}
		
	}

	protected Object loot = null;
	protected Object lootOther = null;
	protected float lootChance = 0;
	protected float lootChanceOther = 0;

	@SuppressWarnings("unchecked")
	protected Item createLoot() {
		Item item;
		if (loot instanceof Generator.Category) {

			item = Generator.random((Generator.Category) loot);

		} else if (loot instanceof Class<?>) {

			item = Generator.random((Class<? extends Item>) loot);

		} else {

			item = (Item) loot;

		}
		return item;
	}

	@SuppressWarnings("unchecked")
	protected Item createLootOther() {
		Item item;
		if (lootOther instanceof Generator.Category) {

			item = Generator.random((Generator.Category) lootOther);

		} else if (lootOther instanceof Class<?>) {

			item = Generator.random((Class<? extends Item>) lootOther);

		} else {

			item = (Item) lootOther;

		}
		return item;
	}
	
	public void explodeDew(int cell) {
		
		if (Dungeon.dewDraw || Dungeon.dewWater){
		  Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		  if (Dungeon.isChallenged(Challenges.DEW_REJECTION)) {
			  for (int n : Level.NEIGHBOURS4) {
				  int c = cell + n;
				  if (c >= 0 && c < Level.getLength() && Level.passable[c]) {

					  if (Random.Int(20) == 1) {
						  Dungeon.level.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(8) == 1) {
						  Dungeon.level.drop(new RedDewdrop(), c).sprite.drop();
					  }
				  }
			  }
		  } else {
			  for (int n : Level.NEIGHBOURS9) {
				  int c = cell + n;
				  if (c >= 0 && c < Level.getLength() && Level.passable[c]) {

					  if (Random.Int(20) == 1) {
						  Dungeon.level.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(8) == 1) {
						  Dungeon.level.drop(new RedDewdrop(), c).sprite.drop();
					  }
				  }
			  }
		  }
		}
	}
	
    public void explodeDewHigh(int cell) {
		
		if (Dungeon.dewDraw || Dungeon.dewWater){
		  Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		  if (Dungeon.isChallenged(Challenges.DEW_REJECTION)) {
			  for (int n : Level.NEIGHBOURS4) {
				  int c = cell + n;
				  if (c >= 0 && c < Level.getLength() && Level.passable[c]) {

					  if (Random.Int(80) == 1) {
						  Dungeon.level.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(10) == 1) {
						  Dungeon.level.drop(new RedDewdrop(), c).sprite.drop();
					  } else if (Random.Int(2) == 0) {
						  Dungeon.level.drop(new YellowDewdrop(), c).sprite.drop();
					  }
				  }
			  }
		  }else{
			  for (int n : Level.NEIGHBOURS9) {
			 int c = cell + n;
			 if (c >= 0 && c < Level.getLength() && Level.passable[c]) {
						
				if (Random.Int(80)==1){Dungeon.level.drop(new VioletDewdrop(), c).sprite.drop();}
				else if (Random.Int(10)==1){Dungeon.level.drop(new RedDewdrop(), c).sprite.drop();}
				else if (Random.Int(2)==0){Dungeon.level.drop(new YellowDewdrop(), c).sprite.drop();}
			}
		  }
		  }
		}
	}
	
	public boolean reset() {
		return false;
	}

	public void beckon(int cell) {

		notice();

		if (state != HUNTING) {
			state = WANDERING;
		}
		target = cell;
	}

	public String description() {
		return Messages.get(this, "desc");
	}

	public void notice() {
		sprite.showAlert();
	}

	public void yell(String str) {
		GLog.n("%s: \"%s\" ", name, str);
	}

	// returns true when a mob sees the hero, and is currently targeting them.
	public boolean focusingHero() {
		return enemySeen && (target == Dungeon.hero.pos);
	}

	public interface AiState {
		public boolean act(boolean enemyInFOV, boolean justAlerted);

		public String status();
	}

	private class Sleeping implements AiState {

		public static final String TAG = "SLEEPING";

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (enemyInFOV
					&& Random.Int(distance(enemy) + enemy.stealth()
							+ (enemy.flying ? 2 : 0)) == 0) {

				enemySeen = true;

				notice();
				state = HUNTING;
				target = enemy.pos;

				spend(TIME_TO_WAKE_UP);

			} else {

				enemySeen = false;

				spend(TICK);

			}
			return true;
		}

		@Override
		public String status() {
			return Messages.get(this, "status", name);
		}
	}

	private class Wandering implements AiState {

		public static final String TAG = "WANDERING";

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (enemyInFOV
					&& (justAlerted || Random.Int(distance(enemy) / 2
							+ enemy.stealth()) == 0)) {

				enemySeen = true;

				notice();
				alerted = true;
				state = HUNTING;
				target = enemy.pos;

			} else {

				enemySeen = false;

				int oldPos = pos;
				if (target != -1 && getCloser(target)) {
					spend(1 / speed());
					return moveSprite(oldPos, pos);
				} else {
					target = Dungeon.level.randomDestination();
					spend(TICK);
				}

			}
			return true;
		}

		@Override
		public String status() {
			return Messages.get(this, "status", name);
		}
	}

	private class Hunting implements AiState {

		public static final String TAG = "HUNTING";

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			enemySeen = enemyInFOV;
			if (enemyInFOV && !isCharmedBy( enemy ) && canAttack(enemy)) {

				return doAttack(enemy);

			} else {

				if (enemyInFOV) {
					target = enemy.pos;
				} else if (enemy == null) {
					state = WANDERING;
					target = Dungeon.level.randomDestination();
					return true;
				}

				int oldPos = pos;
				if (target != -1 && getCloser(target)) {

					spend(1 / speed());
					return moveSprite(oldPos, pos);

				} else {

					spend(TICK);
					if (!enemyInFOV) {
					sprite.showLost();
					state = WANDERING;
					target = Dungeon.level.randomDestination();
					}
					return true;
				}
			}
		}

		@Override
		public String status() {
			return  Messages.get(this, "status", name);
		}
	}

	protected class Fleeing implements AiState {

		public static final String TAG = "FLEEING";

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			enemySeen = enemyInFOV;
			if (enemyInFOV) {
				target = enemy.pos;
			}

			int oldPos = pos;
			if (target != -1 && getFurther(target)) {

				spend(1 / speed());
				return moveSprite(oldPos, pos);

			} else {

				spend(TICK);
				nowhereToRun();

				return true;
			}
		}

		protected void nowhereToRun() {
		}

		@Override
		public String status() {
			return Messages.get(this, "status", name);
		}
	}

	private class Passive implements AiState {

		public static final String TAG = "PASSIVE";

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			enemySeen = false;
			spend(TICK);
			return true;
		}

		@Override
		public String status() {
			return Messages.get(this, "status", name);
		}
	}
	
	
}
