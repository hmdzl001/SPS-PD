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

import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.sprites.DM300Sprite;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.blobs.ShockWeb;
import com.hmdzl.spspd.change.actors.blobs.FrostGas;
import com.hmdzl.spspd.change.actors.blobs.TarGas;
import com.hmdzl.spspd.change.actors.blobs.DarkGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.UDM300Sprite;
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.sprites.SeekingBombSprite;

public class UDM300 extends Mob {

	protected static final float SPAWN_DELAY = 2f;
	
	{
		spriteClass = UDM300Sprite.class;
		baseSpeed = 0.75f;

		HP = HT = 1000;
		EXP = 20;
		evadeSkill = 5;
		
		FLEEING = new Fleeing();

		properties.add(Property.MECH);
		properties.add(Property.BOSS);
	}

	private int breaks=0;
	private static int CountBomb = 0;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
	}

	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}
	
	@Override
	public float speed() {
		if (breaks == 3) return 2*super.speed();
		else return super.speed();
	}
	
	public void spawnBomb() {
		SeekBomb bomb1 = new SeekBomb();	
		SeekBomb bomb2 = new SeekBomb();
		SeekBomb bomb3 = new SeekBomb();
		SeekBomb bomb4 = new SeekBomb();	
		
			bomb1.pos = Dungeon.level.randomRespawnCellMob();
			bomb2.pos = Dungeon.level.randomRespawnCellMob();
			bomb3.pos = Dungeon.level.randomRespawnCellMob();
			bomb4.pos = Dungeon.level.randomRespawnCellMob();
	
		GameScene.add(bomb1);
		GameScene.add(bomb2);
		GameScene.add(bomb3);
		GameScene.add(bomb4);
	}		
	
	@Override
    public boolean act() {

        if( 3 - breaks > 4 * HP / HT ) {
            
			breaks++;
			spawnBomb();
            return true;
        } 
		
	    if (breaks == 1){
		   GameScene.add(Blob.seed(pos, 30, FrostGas.class));
		}
		
		if (breaks == 2){
		   GameScene.add(Blob.seed(pos, 60, TarGas.class));
		}
		
		if (breaks == 3){
           GameScene.add(Blob.seed(pos, 100, DarkGas.class));
		}
       
		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && (enemy.buff(Poison.class) == null && enemy.buff(Burning.class) == null && enemy.buff(Tar.class) == null)) {
			state = HUNTING;
		}
		
        return super.act();
		
    }	
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (breaks == 0 ){
		    if (Random.Int(2) == 0) {
			    Buff.affect(enemy, Poison.class).set(Random.Int(7, 9) * Poison.durationFactor(enemy));
		        state = FLEEING;
			}
		}		
		if (breaks == 1 ){
		    if (Random.Int(2) == 0) {
			    Buff.affect(enemy, Tar.class);
		        state = FLEEING;
			}
		}	
		if (breaks == 2 ){
		    if (Random.Int(2) == 0) {
			    Buff.affect(enemy, Burning.class).reignite(enemy);
		        state = FLEEING;
			}
		}	
	

		return damage;
	}
	
	@Override
	public void move(int step) {
		if (state == FLEEING) {
			GameScene.add(Blob.seed(pos, Random.Int(5, 7), ShockWeb.class));
		}
		super.move(step);
	}	
	
	@Override
	public void damage(int dmg, Object src) {
	
        dmg = Random.Int(10,20);
		if (dmg > 15){
			GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
		}
		super.damage(dmg, src);
	}		
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage/3);

		if (dmg > 0 || (Random.Int(3) == 0 )) {
			enemy.damage(dmg, this);
		}
        
		return super.defenseProc(enemy, damage);
	}	
	
	@Override
	public void die(Object cause) {
	
		GameScene.bossSlain();
		super.die(cause);
		UKing.spawnAt(pos);
	}	
	
	public static UDM300 spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			UDM300 w = new UDM300();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			return w;
  			
		} else {
			return null;
		}
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}	
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(EnchantmentDark.class);
		IMMUNITIES.add(EnchantmentDark.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Paralysis.class);
	    IMMUNITIES.add(Bleeding.class);
		IMMUNITIES.add(CorruptGas.class);
		IMMUNITIES.add(FrostGas.class);
		IMMUNITIES.add(TarGas.class);
		IMMUNITIES.add(Tar.class);
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}	

	public static class SeekBomb extends Mob {

		private static final int BOMB_DELAY = 10;
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