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

import com.hmdzl.spspd.change.levels.CavesBossLevel;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.BlastParticle;
import com.hmdzl.spspd.change.effects.particles.SmokeParticle;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.levels.traps.LightningTrap;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.TowerSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Tower extends Mob implements Callback {

	{
		spriteClass = TowerSprite.class;

		HP = HT = 500+(Dungeon.depth*Random.NormalIntRange(2, 5));
		evadeSkill = 0;

		EXP = 25;
		
		hostile = false;
		state = PASSIVE;
		
		loot = new RedDewdrop();
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
		return 10;
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
							- Random.Int(ch.drRoll());
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
					((CavesBossLevel) Dungeon.level).unseal();
					Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
					Dungeon.level.drop(new Gold(Random.Int(3000, 6000)), pos).sprite.drop();

					Badges.validateBossSlain();
			 }
			 explodeDew(pos);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(EnchantmentDark.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
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
		IMMUNITIES.add(ConfusionGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
