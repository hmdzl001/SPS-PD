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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.STRdown;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Chains;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.SkillBook;
import com.hmdzl.spspd.items.TenguKey;
import com.hmdzl.spspd.items.artifacts.EtherealChains;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.journalpages.Sokoban2;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.SavageHelmet;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfLight;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentLight;
import com.hmdzl.spspd.items.weapon.rockcode.Ichain;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.plants.Blindweed;
import com.hmdzl.spspd.plants.Firebloom;
import com.hmdzl.spspd.plants.Icecap;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Sorrowmoss;
import com.hmdzl.spspd.plants.Stormvine;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.PrisonWanderSprite;
import com.hmdzl.spspd.sprites.SeekingBombSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;

public class PrisonWander extends Mob {

	private boolean chainsUsed = false;

	{
		spriteClass = PrisonWanderSprite.class;

		HP = HT = 800;
		EXP = 40;
		evadeSkill = 20;
		viewDistance = 7;

		properties.add(Property.HUMAN);
		properties.add(Property.BOSS);

		loot = new EtherealChains();
		lootChance = 0.2f;

		lootOther = new DungeonBomb();
		lootChanceOther = 1f;

	}
	
	private int breaks=0;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12, 21);
	}

	public void spawnBomb() {
		int newPos2;
		do {
			newPos2 = Random.Int(Floor.getLength());
		} while (!Floor.fieldOfView[newPos2] || !Floor.passable[newPos2]
				|| Floor.adjacent(newPos2, hero.pos)
				|| Actor.findChar(newPos2) != null);
		SeekBombP bomb1 = new SeekBombP();
		bomb1.pos = newPos2;
		GameScene.add(bomb1);
	}

	@Override
	protected float attackDelay() {
		return 0.75f;
	}
	
	@Override
	protected boolean act() {
		if( 7 - breaks > 8 * HP / HT && HP > 0) {
			breaks++;
				
			if (breaks < 4) {
				Buff.affect(this, GlassShield.class).turns(1);
				spawnBomb();
				chainsUsed = false;
			}
			return true;
		}
		Dungeon.depth.updateFieldOfView( this );
		if (!chainsUsed && state == HUNTING &&
				paralysed <= 0 &&
				enemy != null &&
				enemy.invisible == 0 &&
				Floor.fieldOfView[enemy.pos] &&
				Floor.distance( pos, enemy.pos ) < 5 && !Floor.adjacent( pos, enemy.pos ) &&
				Random.Int(3) == 0 &&
				chain(enemy.pos) && HP > 0) {
			return false;
		} else	if(Random.Int(10)<1 && breaks < 4 ){
				Plant.Seed seed = (Plant.Seed) Generator.random(Generator.Category.SEED2);
				Dungeon.depth.plant(seed, this.pos);
				return true;
		} else {
			return super.act();
		}
	}
	
	private boolean chain(int target){
		//if (chainsUsed || enemy.properties().contains(Property.IMMOVABLE))
			//return false;
		Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

		if (chain.collisionPos != enemy.pos || Floor.pit[chain.path.get(1)])
			return false;
		else {
			int newPos = -1;
			for (int i : chain.subPath(1, chain.dist)){
				if (!Floor.solid[i] && Actor.findChar(i) == null){
					newPos = i;
					break;
				}
			}

			if (newPos == -1){
				return false;
			} else {
				final int newPosFinal = newPos;
				yell( Messages.get(this, "scorpion") );
				sprite.parent.add(new Chains(pos, enemy.pos, new Callback() {
					public void call() {
						Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal), -1);
						enemy.pos = newPosFinal;
						Dungeon.depth.press(newPosFinal, enemy);
						Cripple.prolong(enemy, Cripple.class, 4f);
						Vertigo.prolong(enemy, Vertigo.class, 8f);
						if (enemy == hero) {
							hero.interrupt();
							Dungeon.observe();
						}
						next();
					}
				}));
			}
		}
		chainsUsed = true;
		return true;
	}	

	@Override
	public int hitSkill(Char target) {
		return 35;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(7, 12);
	}



	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10)<2){
		int oppositeDefender = enemy.pos + (enemy.pos - pos);
		Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
		WandOfFlow.throwChar(enemy, trajectory, 1);
		} else if ((Random.Int(10)<2)) {
			int newPos;
			do {
				newPos = Random.Int(Floor.getLength());
			} while (!Floor.fieldOfView[newPos] || !Floor.passable[newPos]
					|| Floor.adjacent(newPos, hero.pos)
					|| Actor.findChar(newPos) != null);

			Buff.affect(hero, STRdown.class,5f);
			hero.sprite.move(pos, newPos);
			hero.move(newPos);

			if (Dungeon.visible[newPos]) {
				CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
				Sample.INSTANCE.play(Assets.SND_PUFF);
			}

			spend(1f);
		}

		return damage;
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	
	}
	
	@Override
	public Item SupercreateLoot(){
			return new SavageHelmet().dounique();
	}	
	
	@Override
	public void die(Object cause) {

		yell(Messages.get(this,"die"));
		
		GameScene.bossSlain();
		
		Badges.validateBossSlain();
	    
	    Dungeon.depth.unseal();

		Dungeon.depth.drop(new SkillBook(), pos).sprite.drop();

		Dungeon.depth.drop(new Sokoban2(), pos).sprite.drop();
		Dungeon.depth.drop(new SkeletonKey(Dungeon.dungeondepth), pos).sprite.drop();
		Dungeon.depth.drop(new TenguKey(), pos).sprite.drop();

		if (Dungeon.hero.heroClass == HeroClass.PERFORMER && Hero.skins == 7)
			Dungeon.depth.drop(new Ichain(), Dungeon.hero.pos).sprite.drop();
		
	    super.die(cause);
	}
	
	private final String CHAINSUSED = "chainsused";
    private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(CHAINSUSED, chainsUsed);
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		chainsUsed = bundle.getBoolean(CHAINSUSED);
		breaks = bundle.getInt( BREAKS );
	}	

	{
		weakness.add(WandOfLight.class);
		weakness.add(EnchantmentLight.class);

		immunities.add(Burning.class);
		resistances.add(ToxicGas.class);
		immunities.add(Poison.class);
		immunities.add(Chill.class);
		immunities.add(Blindness.class);
		immunities.add(Vertigo.class);
		//resistances.add(EnchantmentDark.class);
		

		immunities.add(Icecap.class);
		immunities.add(Firebloom.class);
		immunities.add(Blindweed.class);
		immunities.add(Stormvine.class);
		immunities.add(Sorrowmoss.class);
	}

	public static class SeekBombP extends Mob {

		private static final int BOMB_DELAY = 8;
		private int timeToBomb = BOMB_DELAY;
		{
			spriteClass = SeekingBombSprite.class;

			HP = HT = 1;
			evadeSkill = 0;
			baseSpeed = 1f;
			timeToBomb = BOMB_DELAY;
			EXP = 0;

			state = HUNTING;

			properties.add(Property.MECH);
			properties.add(Property.MINIBOSS);
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			int dmg = super.attackProc(enemy, damage);

			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			yell("KA-BOOM!!!");

			destroy();
			sprite.die();

			return dmg;
		}

		@Override
		public void die(Object cause) {
			DungeonBomb bomb = new DungeonBomb();
			bomb.explode(pos);
			super.die(cause);

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
		public boolean act() {
			yell(""+timeToBomb+"!");
			if (timeToBomb == 0){
				DungeonBomb bomb = new DungeonBomb();
				bomb.explode(pos);
				yell("KA-BOOM!!!");
				destroy();
				sprite.die();
			}

			return super.act();
		}

		@Override
		public void move(int step) {
			super.move(step);
			timeToBomb --;
		}

	}

}
