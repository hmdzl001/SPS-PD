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

import com.hmdzl.spspd.change.levels.traps.SummoningTrap;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.OrbOfZot;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ShadowYogSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class ShadowYog extends Mob  {
	
	{
		spriteClass = ShadowYogSprite.class;

		HP = HT = 50*Dungeon.hero.lvl;
		
		baseSpeed = 2f;
		evadeSkill = 32;

		EXP = 100;

		state = PASSIVE;

		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}
	
	private int yogsAlive = 0;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(45, 100);
	}

	@Override
	public int hitSkill(Char target) {
		return 50;
	}

	public ShadowYog() {
		super();
	}

	@Override
	public int drRoll() {
		return (Dungeon.level.mobs.size());
	}
	
	@Override
	public void damage(int dmg, Object src) {

			//for (Mob mob : Dungeon.level.mobs) {
			 //	mob.beckon(pos);
			//	}
			
			for (int i = 0; i < 4; i++) {
				int trapPos;
				do {
					trapPos = Random.Int(Level.getLength());
				} while (!Level.fieldOfView[trapPos] || !Level.passable[trapPos]);

				if (Dungeon.level.map[trapPos] == Terrain.INACTIVE_TRAP) {
					Dungeon.level.setTrap( new SummoningTrap().reveal(), trapPos );
					Level.set(trapPos,Terrain.TRAP);
					GameScene.updateMap(trapPos);
				}
			}
			
			if (HP<(HT/8) && Random.Float() < 0.5f){

				int newPos = -1;
					for (int i = 0; i < 20; i++) {
					newPos = Dungeon.level.randomRespawnCellMob();
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
				if (Dungeon.level.mobs.size()<Dungeon.hero.lvl*2){
				Fiend.spawnAroundChance(newPos);
				}
			}
			
			super.damage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
                return super.defenseProc(enemy, damage);
	}
	

	
	@Override
	public void beckon(int cell) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		super.die(cause);
		
		Statistics.shadowYogsKilled++;

      for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof ShadowYog){
				   yogsAlive++;
				 }
			}
			
		 if(yogsAlive==0){
			GameScene.bossSlain();
			Dungeon.shadowyogkilled=true;
			
			Dungeon.level.drop(new OrbOfZot(), pos).sprite.drop();
			
			for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
				if (mob instanceof Rat || mob instanceof GoldOrc || mob instanceof Fiend || mob instanceof Eye) {
					mob.die(cause);
				}
			}
			
			yell(Messages.get(this,"die"));
		 }
	}

	@Override
	public void notice() {
		super.notice();
		yell(Messages.get(this,"illusion"));
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {

		IMMUNITIES.add(EnchantmentDark.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	}
