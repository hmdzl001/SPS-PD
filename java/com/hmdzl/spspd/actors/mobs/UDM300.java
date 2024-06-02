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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.DarkGas;
import com.hmdzl.spspd.actors.blobs.ShockWeb;
import com.hmdzl.spspd.actors.blobs.SlowGas;
import com.hmdzl.spspd.actors.blobs.TarGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Tar;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.bombs.DungeonBomb;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SeekingBombSprite;
import com.hmdzl.spspd.sprites.UDM300Sprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

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
		
			bomb1.pos = Dungeon.depth.randomRespawnCellMob();
			bomb2.pos = Dungeon.depth.randomRespawnCellMob();
			bomb3.pos = Dungeon.depth.randomRespawnCellMob();
			bomb4.pos = Dungeon.depth.randomRespawnCellMob();
	
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
		   GameScene.add(Blob.seed(pos, 30, SlowGas.class));
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
			    Buff.affect(enemy, Poison.class).set(Random.Int(7, 9));
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
			    Buff.affect(enemy, Burning.class).set(3f);
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

		dmg = Math.min(dmg,20);
		if (dmg > 15){
			GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
		}
		super.damage(dmg, src);
	}		
	
	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = Random.IntRange(0, damage/3) - enemy.drRoll();

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
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
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
	
	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		resistances.add(EnchantmentDark.class);
		immunities.add(EnchantmentDark.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Vertigo.class);
		immunities.add(Paralysis.class);
	    immunities.add(Bleeding.class);
		immunities.add(CorruptGas.class);
		immunities.add(SlowGas.class);
		immunities.add(TarGas.class);
		immunities.add(Tar.class);
	}

	public static class SeekBomb extends Mob {
		{
			spriteClass = SeekingBombSprite.class;

			HP = HT = 100;
			evadeSkill = 0;
			baseSpeed = 1f;
			EXP = 0;

			state = HUNTING;

			properties.add(Property.MECH);
			properties.add(Property.MINIBOSS);
		}

	    @Override
	    public int attackProc(Char enemy, int damage) {
		    Buff.affect(enemy, Slow.class,4f);
			enemy.damage(1, Weapon.class);
			for (Mob mob : Dungeon.depth.mobs.toArray(new Mob[0])) {
				mob.beckon(enemy.pos);
			}
		    return 0;
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

	}	

}	