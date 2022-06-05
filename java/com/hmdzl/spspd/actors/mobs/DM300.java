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
import com.hmdzl.spspd.Badges.Badge;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.BlastParticle;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.TomeOfMastery;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.journalpages.Sokoban3;
import com.hmdzl.spspd.items.keys.SkeletonKey;
import com.hmdzl.spspd.items.misc.HorseTotem;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.LightningTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DM300Sprite;
import com.hmdzl.spspd.sprites.TowerSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class DM300 extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	{
		spriteClass = DM300Sprite.class;

		HP = HT = 800;
		EXP = 50;
		evadeSkill = 24;

		loot = new CapeOfThorns().identify();
		lootChance = 0.2f;
		
		lootOther = Generator.Category.NORNSTONE;
		lootChanceOther = 1f;		
		
		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}

	private int bossAlive = 0;
	private int towerAlive = 0;
	
	@Override
	public int damageRoll() {
		
		return Random.NormalIntRange(15, 19)*towerAlive;
	}

	@Override
	public int hitSkill(Char target) {
		return 35;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 10+(4*towerAlive));
	}

	public void spawnTower() {
		Tower a = new Tower();  
	    Tower b = new Tower(); 	

		do {
			a.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			b.pos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
		} while (!Level.passable[a.pos] || !Level.passable[b.pos] || a.pos == b.pos);
		
		GameScene.add(a);
		GameScene.add(b);	
	}	
	
	@Override
	public boolean act() {
		
		if (towerAlive < 1){
			spawnTower();
			towerAlive++;
		}

		GameScene.add(Blob.seed(pos, 30, ToxicGas.class));

		return super.act();
	}
	
	@Override
	public void move(int step) {
		super.move(step);

		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP && HP < HT) {

			HP += Random.Int(1, HT - HP);
			sprite.emitter().burst(ElmoParticle.FACTORY, 5);

			if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
				GLog.n(Messages.get(this,"heal"));
			}
		}

		int[] cells = { step - 1, step + 1, step - Level.getWidth(),
				step + Level.getWidth(), step - 1 - Level.getWidth(),
				step - 1 + Level.getWidth(), step + 1 - Level.getWidth(),
				step + 1 + Level.getWidth() };
		int cell = cells[Random.Int(cells.length)];

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);

			if (Level.water[cell]) {
				GameScene.ripple(cell);
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set(cell, Terrain.EMPTY_DECO);
				GameScene.updateMap(cell);
			}
		}

		Char ch = Actor.findChar(cell);
		if (ch != null && ch != this ) {
			Buff.prolong(ch, Paralysis.class, 2);
		}
	}

	@Override
	public Item SupercreateLoot(){
			return new HorseTotem().identify();
	}	
	
	@Override
	public void die(Object cause) {

		super.die(cause);

           for (Mob mob : Dungeon.level.mobs) {
			
			  if (mob instanceof Tower){
				   bossAlive++;
				   }
			
			}
			
			 if(bossAlive==0){
				 
					GameScene.bossSlain();
					Dungeon.level.unseal();
					Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
					Badges.validateBossSlain();
			 }

			 Badges.Badge badgeToCheck = null;
				switch (Dungeon.hero.heroClass) {
				case WARRIOR:
					badgeToCheck = Badge.MASTERY_WARRIOR;
					break;
				case MAGE:
					badgeToCheck = Badge.MASTERY_MAGE;
					break;
				case ROGUE:
					badgeToCheck = Badge.MASTERY_ROGUE;
					break;
				case HUNTRESS:
					badgeToCheck = Badge.MASTERY_HUNTRESS;
					break;
				case PERFORMER:
					badgeToCheck = Badge.MASTERY_PERFORMER;
					break;	
				case SOLDIER:
					badgeToCheck = Badge.MASTERY_SOLDIER;
					break;	
				case FOLLOWER:
					badgeToCheck = Badge.MASTERY_FOLLOWER;
					break;	
			case ASCETIC:
					badgeToCheck = Badge.MASTERY_ASCETIC;
					break;					
				}
				
				
				Dungeon.level.drop(new Sokoban3(), pos).sprite.drop();
		       Dungeon.level.drop(new TomeOfMastery(), pos).sprite.drop();

		yell(Messages.get(this,"die"));
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this, "notice"));
	}
	
	@Override
	public void call() {
		next();
	}
	
	{
		immunities.add(ToxicGas.class);
		immunities.add(Terror.class);
	}


public static class Tower extends Mob implements Callback {

	{
		spriteClass = TowerSprite.class;

		HP = HT = 500+(Dungeon.depth*Random.NormalIntRange(2, 5));
		evadeSkill = 0;

		EXP = 25;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new StoneOre();
		lootChance = 1f;

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}
	
	@Override
	public void beckon(int cell) {
		// Do nothing
	}
	
	private int bossAlive = 0;

	@Override
	public int damageRoll() {
		return 0;
	}

	@Override
	public void damage(int dmg, Object src) {

		for (Mob mob : Dungeon.level.mobs) {
			mob.beckon(Dungeon.hero.pos);
		}

		GLog.w(Messages.get(this,"alert"));
		CellEmitter.center(pos).start(
				Speck.factory(Speck.SCREAM), 0.3f, 3);
		Sample.INSTANCE.play(Assets.SND_CHALLENGE);

		super.damage(dmg, src);
	}
	
	@Override
	public int hitSkill(Char target) {
		return 0;
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	protected boolean act() {
		
		switch (Random.Int(4)) {
		case 1:
		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Tower && mob != this) {
				mob.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				mob.sprite.flash();
			}
		}
		break;
		case 2:
			if (Dungeon.level.mobs.size()<10){
		 BrokenRobot.spawnAround(pos);
		 GLog.n(Messages.get(this,"robots"));
			}
		break;
		}
		
		return super.act();
	}
	
	@Override
	public void call() {
		next();
	}
	
	public void explode(int cell) {
		// We're blowing up, so no need for a fuse anymore.
	
		Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

		boolean terrainAffected = false;
		for (int n : Level.NEIGHBOURS8) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Level.flamable[c]) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				Char ch = Actor.findChar(c);
				if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = c == cell ? Dungeon.depth + 5 : 1;
					int maxDamage = 10 + Dungeon.depth * 2;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Math.max(ch.drRoll(),0);
					if (dmg > 0) {
						ch.damage(dmg, this);
					}
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
	}
	
	
	@Override
	public void add(Buff buff) {
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		explode(pos);

		for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof Tower || mob instanceof DM300){
				   bossAlive++;
				 }
			
			}
			
			 if(bossAlive==0){
				 
					GameScene.bossSlain();
					Dungeon.level.unseal();
					Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
					Dungeon.level.drop(new Gold(Random.Int(3000, 6000)), pos).sprite.drop();

					Badges.validateBossSlain();
			 }
			 explodeDew(pos);
	}

	{
		resistances.add(LightningTrap.Electricity.class);

		immunities.add(ToxicGas.class);
		immunities.add(Terror.class);
		immunities.add(ConfusionGas.class);
	}

	
}
	
}
