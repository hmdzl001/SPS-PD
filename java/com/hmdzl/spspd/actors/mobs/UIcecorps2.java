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
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.SelfDestroy;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.IceRabbit2Sprite;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ICE_DAMAGE;


public class UIcecorps2 extends Mob {

	protected static final float SPAWN_DELAY = 2f;

	{
		spriteClass = IceRabbit2Sprite.class;
		baseSpeed = 1.5f;

		HP = HT = 500;
		EXP = 20;
		evadeSkill = 5;

		loot = new EasterEgg();
		lootChance = 1f;

		properties.add(Property.ORC);
		properties.add(Property.BOSS);
	}

    private int timeToIce = 0;
	
	public void spawnfires() {

		FireRabbit fr1 = new FireRabbit();

		fr1.pos = Dungeon.depth.randomRespawnCellMob();
	
		GameScene.add(fr1);

	}			
	
	@Override
	public int damageRoll() {
		//return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
		return 0;
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
	protected boolean canAttack(Char enemy) {
		return Floor.distance( pos, enemy.pos ) <= 2 ;
	}		
	

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, StoneIce.class).level(3);
		}
		
		enemy.damage(damageRoll()*3/4, ICE_DAMAGE,2);
		damage = damage/4;


		return damage;
	}	

	@Override
    public boolean act() {
		beckon(Dungeon.hero.pos);
		//if (buffs(SelfDestroy.class)){
		Buff.affect(this, SelfDestroy.class);
		//}
		timeToIce++;
		if (timeToIce > 20){
           spawnfires();
		   timeToIce=0;
		}
		
        return super.act();
    }	
	
	@Override
	public void damage(int dmg, Object src, int type) {
        if (!(src instanceof SelfDestroy) ) dmg = 0;
		super.damage(dmg, src,type);
	}		
	
	@Override
	public void move(int step) {
		super.move(step);
        if ( timeToIce > 0 ){
		int[] cells = { step - 1, step + 1, step - Floor.getWidth(),
				step + Floor.getWidth(), step - 1 - Floor.getWidth(),
				step - 1 + Floor.getWidth(), step + 1 - Floor.getWidth(),
				step + 1 + Floor.getWidth() };
		int cell = cells[Random.Int(cells.length)];

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);

			if (Floor.water[cell]) {
				Dungeon.depth.setTrap( new SpearTrap().reveal(), cell );
				Floor.set(cell, Terrain.TRAP);
				GameScene.updateMap(cell);
				ScrollOfMagicMapping.discover(cell);
			} else if (Dungeon.depth.map[cell] == Terrain.EMPTY) {
				Floor.set(cell, Terrain.WATER);
				GameScene.updateMap(cell);
			}
		}
		Char ch = Actor.findChar(cell);
		if (ch != null && ch != this) {
			Buff.prolong(ch, Slow.class, 5);
		    }
		}
	}	
	
	@Override
	public void die(Object cause) {
	
		GameScene.bossSlain();
		super.die(cause);
		UYog.spawnAt(pos);
	}	
	
	public static UIcecorps2 spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			UIcecorps2 w = new UIcecorps2();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			return w;
  			
		} else {
			return null;
		}
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		//resistances.add(EnchantmentDark.class);
		//immunities.add(EnchantmentDark.class);
		immunities.add(Terror.class);
		immunities.add(Amok.class);
		immunities.add(Charm.class);
		immunities.add(Sleep.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(Chill.class);
		immunities.add(Frost.class);
		immunities.add(ScrollOfPsionicBlast.class);
		immunities.add(Vertigo.class);
		immunities.add(Paralysis.class);
	    immunities.add(Bleeding.class);
		immunities.add(CorruptGas.class);
		
	}
	

}	