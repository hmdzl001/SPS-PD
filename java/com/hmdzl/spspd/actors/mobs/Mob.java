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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Challenges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AflyBless;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.Disarm;
import com.hmdzl.spspd.actors.buffs.Feed;
import com.hmdzl.spspd.actors.buffs.LearnSkill;
import com.hmdzl.spspd.actors.buffs.Rhythm;
import com.hmdzl.spspd.actors.buffs.Rhythm2;
import com.hmdzl.spspd.actors.buffs.Shocked;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.SoulMark;
import com.hmdzl.spspd.actors.buffs.SpAttack;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.actors.mobs.npcs.NPC;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.Surprise;
import com.hmdzl.spspd.effects.Wound;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.artifacts.AlienBag;
import com.hmdzl.spspd.items.artifacts.ClownDeck;
import com.hmdzl.spspd.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.items.artifacts.TimekeepersHourglass;
import com.hmdzl.spspd.items.misc.DemoScroll;
import com.hmdzl.spspd.items.misc.LuckyBadge;
import com.hmdzl.spspd.items.misc.PPC;
import com.hmdzl.spspd.items.misc.Shovel;
import com.hmdzl.spspd.items.reward.BoundReward;
import com.hmdzl.spspd.items.rings.Ring;
import com.hmdzl.spspd.items.rings.RingOfAccuracy;
import com.hmdzl.spspd.items.rings.RingOfKnowledge;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.features.Door;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public abstract class Mob extends Char {

    {
		name = Messages.get(this, "name");
	}

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
	public int dewLvl = 1;

	protected Char enemy;
	protected boolean enemySeen;
	protected boolean alerted = false;
	protected boolean skilluse = false;
	protected boolean sumcopy = false;

	protected static final float TIME_TO_WAKE_UP = 1f;

	public boolean hostile = true;
	public boolean ally = false;
	public boolean originalgen = false;
	public boolean firstitem = true;

	private static final String STATE = "state";
	private static final String SEEN = "seen";
	private static final String TARGET = "target";
	private static final String ORIGINAL = "originalgen";
	private static final String FIRSTITEM = "firstitem";
	private static final String SkillUSE = "skilluse";
	
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
		bundle.put(FIRSTITEM, firstitem);
		bundle.put(SkillUSE, skilluse);
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
		firstitem = bundle.getBoolean(FIRSTITEM);
		skilluse = bundle.getBoolean(SkillUSE);
	}

	public CharSprite sprite() {
		CharSprite sprite = null;
		try {
			sprite = spriteClass.newInstance();
		} catch (Exception e) {
			ShatteredPixelDungeon.reportException(e);
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
				for (int n : Floor.NEIGHBOURS8) {
					int cell = enemy.pos + n;
					if ((Floor.passable[cell] || Floor.avoid[cell])) {
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
				&& Floor.fieldOfView[enemy.pos] && enemy.invisible<=0 ;

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
			//We are amoked and current enemy is the hero
		else if (buff( Amok.class ) != null && enemy == Dungeon.hero)
			newEnemy = true;
		if (enemy == Dungeon.hero )
			newEnemy = true;

		if ( newEnemy ) {

			HashSet<Char> enemies = new HashSet<>();

			//if the mob is corrupted...
			if ( buff(Amok.class) != null) {

				//try to find an enemy mob to attack first.
				for (Mob mob : Dungeon.depth.mobs)
					if (mob != this && Floor.fieldOfView[mob.pos] && mob.hostile)
						enemies.add(mob);
				if (enemies.size() > 0) return Random.element(enemies);

				//try to find ally mobs to attack second.
				for (Mob mob : Dungeon.depth.mobs)
					if (mob != this && Floor.fieldOfView[mob.pos] && mob.ally)
						enemies.add(mob);
				if (enemies.size() > 0) return Random.element(enemies);

					//if there is nothing, go for the hero
				else return Dungeon.hero;

			} else {

				//try to find ally mobs to attack.
				for (Mob mob : Dungeon.depth.mobs)
					if (mob != this && Floor.fieldOfView[mob.pos] && (mob.ally || mob.buff(Amok.class) != null))
						enemies.add(mob);

				//and add the hero to the list of targets.
				enemies.add(Dungeon.hero);

				//go after the closest enemy, preferring the hero if two are equidistant
				if (enemies.isEmpty()){
					return null;
				} else {
					//go after the closest potential enemy, preferring the hero if two are equidistant
					Char closest = null;
					for (Char curr : enemies){
						if (closest == null
								|| Dungeon.depth.distance(pos, curr.pos) < Dungeon.depth.distance(pos, closest.pos)
								|| Dungeon.depth.distance(pos, curr.pos) == Dungeon.depth.distance(pos, closest.pos) && curr == Dungeon.hero){
							closest = curr;
						}
					}
					return closest;
				}

				//return Random.element(enemies);

			}

		} else
			return enemy;
		
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
        return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy) && (buff(Disarm.class) == null));
	}

	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Floor.passable,
				Floor.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Floor.passable,
				Floor.fieldOfView);
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
			Dungeon.depth.mobPress(this);
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

	public int attackProc(Char enemy, int damage) {
		if (Dungeon.isChallenged(Challenges.ELE_STOME)){
			enemy.damage( Statistics.deepestFloor/5 , DamageType.ENERGY_DAMAGE,2);
		}

		if (buff(Shocked.class)!=null && !Shocked.first){
			Buff.detach(this,Shocked.class);
			Buff.affect(this, Disarm.class,5f);
			damage(this.HP/10,SHOCK_DAMAGE,2);
		}

		return damage;
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
			for (Buff buff : enemy.buffs(RingOfAccuracy.RingAccuracy.class)) {
				penalty += ((RingOfAccuracy.RingAccuracy) buff).level;
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
			if (Dungeon.hero.petHP < Dungeon.hero.HT) {
				Dungeon.hero.petHP += (int) Math.ceil(restoration * 0.10f);
			}
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
		adjustment = Dungeon.dungeondepth;
	  } else if (type == 1){
		adjustment = Dungeon.dungeondepth /2;
	  } else if (type == 2){
		 adjustment = Dungeon.dungeondepth /4;
	  } else if (type == 3){
		 adjustment = Dungeon.dungeondepth *2;
	  } else adjustment = 1;

		return adjustment;
	}

	@Override
	public void damage(int dmg, Object src, int type) {

		Terror.recover(this);

		if (state == SLEEPING) {
			state = WANDERING;
		}
		
		if (state != HUNTING) {
			alerted = true;
		}

		if (Dungeon.isChallenged(Challenges.ELE_STOME)) {
			if (src instanceof Wand)
				dmg = (int) Math.ceil(dmg * 0.8);
		}
		BeCorrupt beco = buff(BeCorrupt.class);
       if( beco != null)	{
		   dmg = (int) Math.ceil(dmg * 1.2);
	   }
		//alerted = true;

		super.damage(dmg, src,type);
	}

	@Override
	public void destroy() {

		super.destroy();

		Dungeon.depth.mobs.remove(this);

		if (Dungeon.hero.isAlive() && !(this instanceof NPC)) {

			if (hostile) {
				Statistics.enemiesSlain++;
				Badges.validateMonstersSlain();
				Statistics.qualifiedForNoKilling = false;

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
			Dungeon.hero.TRUE_HT++;
			Dungeon.hero.updateHT(true);
			//Buff.affect(Dungeon.hero,GlassShield.class).turns(3)
		}
		
	    if(Dungeon.hero.buff(LearnSkill.class)!=null){
			LearnSkill.left--;
			//Buff.affect(Dungeon.hero,GlassShield.class).turns(3)
		}		
		
		AlienBag.bagRecharge bags = Dungeon.hero.buff(AlienBag.bagRecharge.class);
		if (bags != null) bags.gainExp();

		MasterThievesArmband.Thievery armband = Dungeon.hero.buff(MasterThievesArmband.Thievery.class);
		if (armband != null) armband.gainCharge();

		ClownDeck.deckRecharge clowndeck = Dungeon.hero.buff(ClownDeck.deckRecharge.class);
		if (clowndeck != null) clowndeck.gainCharge();

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Hero.skins == 7)
			Dungeon.hero.belongings.recode();
	
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
		for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
			if (mob.originalgen){return true;}
		 }	
		return false;
	}

	@Override
	public void die(Object cause) {

		super.die(cause);
		
		int generation=0;
		
		if(this.sumcopy){
			generation=1;
		}

		Dewcharge dewing = Dungeon.hero.buff(Dewcharge.class);
		if (Dungeon.hero.buff(Dewcharge.class) != null && dewing.isDewing() && generation==0) {
			explodeDewHigh(pos);
		}
		
		if (!Dungeon.depth.cleared && originalgen && !checkOriginalGenMobs() && Dungeon.dungeondepth >1
		&& Dungeon.dungeondepth <25 && !Dungeon.bossLevel(Dungeon.dungeondepth) && (Dungeon.dewDraw || Dungeon.dewWater)){
			Dungeon.depth.cleared=true;
			GameScene.levelCleared();
			if(Dungeon.dungeondepth >0){
				Statistics.prevfloormoves=Math.max(Dungeon.pars[Dungeon.dungeondepth]-(int)Dungeon.depth.currentmoves,0);
			   if (Statistics.prevfloormoves>1){
			     GLog.h(Messages.get(this, "clear1"), Statistics.prevfloormoves);
				 Dungeon.depth.drop(new BoundReward(), pos).sprite.drop();
			   } else if (Statistics.prevfloormoves==0){
				 GLog.h(Messages.get(this, "clear3"));
			   }
			}

		}

		float lootChance = this.lootChance;
		float lootChanceOther = this.lootChanceOther;
		int bonus = 0;

		LuckyBadge luckyBadge = Dungeon.hero.belongings.getItem(LuckyBadge.class);
	    if(luckyBadge != null){
		    bonus += luckyBadge.level;
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
				Dungeon.depth.drop(loot, pos).sprite.drop();

		} else if (Random.Float() < lootChanceOther
				&& Dungeon.hero.lvl <= maxLvl + 800) {
			Item lootOther = createLootOther();
			if (lootOther != null)
				Dungeon.depth.drop(lootOther, pos).sprite.drop();
		}

		if (!ally && !sumcopy){
			rollToDropLoot();
		}

		if (Dungeon.hero.isAlive() && !Dungeon.visible[pos]) {
			GLog.i(Messages.get(this, "died"));
		}
		
		PPC ppc = Dungeon.hero.belongings.getItem(PPC.class);
		if (ppc!=null) {ppc.charge++;}

		DemoScroll dc = Dungeon.hero.belongings.getItem(DemoScroll.class);
		if (dc!=null) {dc.charge++;}
		
		if (Dungeon.isChallenged(Challenges.NIGHTMARE_VIRUS) && generation == 0) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Floor.passable;
			int[] neighbours = { pos + 1, pos - 1, pos + Floor.getWidth(),
					pos - Floor.getWidth() };
			for (int n : neighbours) {
				if (passable[n] && Actor.findChar(n) == null) {
					candidates.add(n);
				}
			}
			
			if (candidates.size() > 0) {

				Virus virus = new Virus();
				virus.pos = Random.element(candidates);
				virus.state = virus.HUNTING;

				if (Dungeon.depth.map[virus.pos] == Terrain.DOOR) {
					Door.enter(virus.pos);
				}

				GameScene.add(virus, 1f);
				Actor.addDelayed(new Pushing(virus, pos, virus.pos), -1);
			} else {
				Dungeon.depth.drop(new RedDewdrop(), pos).sprite.drop();
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
			  for (int n : Floor.NEIGHBOURS4) {
				  int c = cell + n;
				  if (c >= 0 && c < Floor.getLength() && Floor.passable[c] && Dungeon.depth.map[c] != Terrain.LOCKED_EXIT) {

					  if (Random.Int(20) == 1) {
						  Dungeon.depth.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(8) == 1) {
						  Dungeon.depth.drop(new RedDewdrop(), c).sprite.drop();
					  }
				  }
			  }
		  } else {
			  for (int n : Floor.NEIGHBOURS9) {
				  int c = cell + n;
				  if (c >= 0 && c < Floor.getLength() && Floor.passable[c] && Dungeon.depth.map[c] != Terrain.LOCKED_EXIT) {

					  if (Random.Int(20) == 1) {
						  Dungeon.depth.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(8) == 1) {
						  Dungeon.depth.drop(new RedDewdrop(), c).sprite.drop();
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
			  for (int n : Floor.NEIGHBOURS4) {
				  int c = cell + n;
				  if (c >= 0 && c < Floor.getLength() && Floor.passable[c] && Dungeon.depth.map[c] != Terrain.LOCKED_EXIT) {

					  if (Random.Int(80) == 1) {
						  Dungeon.depth.drop(new VioletDewdrop(), c).sprite.drop();
					  } else if (Random.Int(10) == 1) {
						  Dungeon.depth.drop(new RedDewdrop(), c).sprite.drop();
					  } else if (Random.Int(2) == 0) {
						  Dungeon.depth.drop(new YellowDewdrop(), c).sprite.drop();
					  }
				  }
			  }
		  }else{
			  for (int n : Floor.NEIGHBOURS9) {
			 int c = cell + n;
			 if (c >= 0 && c < Floor.getLength() && Floor.passable[c]) {
						
				if (Random.Int(80)==1){Dungeon.depth.drop(new VioletDewdrop(), c).sprite.drop();}
				else if (Random.Int(10)==1){Dungeon.depth.drop(new RedDewdrop(), c).sprite.drop();}
				else if (Random.Int(2)==0){Dungeon.depth.drop(new YellowDewdrop(), c).sprite.drop();}
			}
		  }
		  }
		}
	}

	public void rollToDropLoot(){
		if (Ring.getBonus(Dungeon.hero, RingOfKnowledge.RingKnowledge.class) > 0) {
			int rolls = 1;
			if (properties.contains(Property.BOSS)) rolls = 10;
			ArrayList<Item> bonus = RingOfKnowledge.tryForBonusDrop(Dungeon.hero, rolls);
			if (bonus != null && !bonus.isEmpty()) {
				for (Item b : bonus) Dungeon.depth.drop(b, pos).sprite.drop();
				if (RingOfKnowledge.latestDropWasRare){
					new Flare(8, 48).color(0xAA00FF, true).show(sprite, 3f);
					RingOfKnowledge.latestDropWasRare = false;
				} else {
					new Flare(8, 24).color(0xFFFFFF, true).show(sprite, 3f);
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

	public Item SupercreateLoot() {
		Item item;
		if (loot instanceof Generator.Category) {
			item = Generator.random( (Generator.Category)loot );
		} else if (loot instanceof Class<?>) {
			item = Generator.random( (Class<? extends Item>)loot );
		} else {
			item = new StoneOre();

		}
		return item;
	}


	public String description() {

		String info = Messages.get(this, "desc");

		if (originalgen) {
			info += "\n" + Messages.get(Mob.class, "originalgen");
		}

		if (ally) {
			info += "\n" + Messages.get(Mob.class, "ally");
		}

		if (sumcopy) {
			info += "\n" + Messages.get(Mob.class, "sumcopy");
		}

		return info;
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
		boolean act(boolean enemyInFOV, boolean justAlerted);

		String status();
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
					target = Dungeon.depth.randomDestination();
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
					target = Dungeon.depth.randomDestination();
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
					target = Dungeon.depth.randomDestination();
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
