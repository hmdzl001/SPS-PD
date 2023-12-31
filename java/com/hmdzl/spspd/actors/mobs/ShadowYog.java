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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.OrbOfZot;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.SummoningTrap;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ShadowYogSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
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
	private int breaks=0;
	private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		breaks = bundle.getInt( BREAKS );
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(43, 83);
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
		return 25;
	}

	@Override
	public boolean act() {

		if (5 - breaks > 6 * HP / HT) {
			breaks++;
			int newPos = -1;
			for (int i = 0; i < 20; i++) {
				newPos = Dungeon.depth.randomRespawnCellMob();
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
			return true;
		}
		return super.act();
	}

	@Override
	public void damage(int dmg, Object src) {

			//for (Mob mob : Dungeon.level.mobs) {
			 //	mob.beckon(pos);
			//	}
			
			for (int i = 0; i < 4; i++) {
				int trapPos;
				do {
					trapPos = Random.Int(Floor.getLength());
				} while (!Floor.fieldOfView[trapPos] || !Floor.passable[trapPos]);

				if (Dungeon.depth.map[trapPos] == Terrain.INACTIVE_TRAP) {
					Dungeon.depth.setTrap( new SummoningTrap().reveal(), trapPos );
					Floor.set(trapPos,Terrain.TRAP);
					GameScene.updateMap(trapPos);
				}
			}
		if (Dungeon.depth.mobs.size()<Dungeon.hero.lvl*2){
			Fiend.spawnAroundChance(pos);
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
		
		//Statistics.shadowYogsKilled++;

      for (Mob mob : Dungeon.depth.mobs) {
			
			if (mob instanceof ShadowYog){
				   yogsAlive++;
				 }
			}
			
		 if(yogsAlive==0){
			GameScene.bossSlain();
			Dungeon.shadowyogkilled=true;
			
			Dungeon.depth.drop(new OrbOfZot(), pos).sprite.drop();
			
			for (Mob mob : (Iterable<Mob>) Dungeon.depth.mobs.clone()) {
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
	
	{

		immunities.add(EnchantmentDark.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Paralysis.class);
		immunities.add(Vertigo.class);
	}



	}
