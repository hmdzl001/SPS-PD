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

import java.util.HashSet;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.BoxStar;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.hmdzl.spspd.actors.buffs.Chill;
import com.hmdzl.spspd.actors.buffs.Frost;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.StoneIce;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.eggs.EasterEgg;
import com.hmdzl.spspd.items.scrolls.ScrollOfMagicMapping;
import com.hmdzl.spspd.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.items.weapon.melee.special.Handcannon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.traps.SpearTrap;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.hmdzl.spspd.sprites.IceRabbitSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Slow;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.levels.Terrain;


public class UIcecorps extends Mob {

	protected static final float SPAWN_DELAY = 2f;

	{
		spriteClass = IceRabbitSprite.class;
		baseSpeed = 0.75f;

		HP = HT = 1500;
		EXP = 20;
		evadeSkill = 5;

		loot = new EasterEgg();
		lootChance = 1f;

		properties.add(Property.ORC);
		properties.add(Property.BOSS);
	}

	private int breaks = 0;
    private int timeToIce = 0;
	
	
	public void spawnfires() {
		FireRabbit fr1 = new FireRabbit();

		fr1.pos = Dungeon.level.randomRespawnCellMob();
	
		GameScene.add(fr1);

	}			
	
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
	protected boolean canAttack(Char enemy) {
		return Dungeon.level.distance( pos, enemy.pos ) <= 2 ;
	}		
	

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, StoneIce.class).level(3);
		}

		return damage;
	}	
	
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
    public boolean act() {

        if( HP < 100 ) {
            if (breaks == 0) {
			breaks++;
			HP = HT;

            return false;
            }
        } 
		

		timeToIce++;
		if (timeToIce > 20){
           spawnfires();
		   timeToIce=0;
		}
		
        return super.act();
    }	
	
	@Override
	public void damage(int dmg, Object src) {
        if (buff(BoxStar.class) != null && !(src instanceof StoneIce) )
			dmg = 0;
        if (src instanceof StoneIce)
        	dmg = 10;
		if (dmg > 40)
		dmg = Random.Int(10, 40);
		if(HP-dmg<100 && breaks ==0){
			Buff.affect(this,BoxStar.class,10f);
			Buff.affect(this,StoneIce.class).level(100);
		}
		
		super.damage(dmg, src);
	}		
	
	@Override
	public void move(int step) {
		super.move(step);
        if ( timeToIce > 0 ){
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
				Dungeon.level.setTrap( new SpearTrap().reveal(), cell );
				Level.set(cell, Terrain.TRAP);
				GameScene.updateMap(cell);
				ScrollOfMagicMapping.discover(cell);
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set(cell, Terrain.WATER);
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
	
	public static UIcecorps spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			UIcecorps w = new UIcecorps();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			return w;
  			
		} else {
			return null;
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
		IMMUNITIES.add(Chill.class);
		IMMUNITIES.add(Frost.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
		IMMUNITIES.add(Paralysis.class);
	    IMMUNITIES.add(Bleeding.class);
		IMMUNITIES.add(CorruptGas.class);
		
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}	