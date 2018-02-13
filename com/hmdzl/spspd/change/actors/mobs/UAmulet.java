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

import java.util.ArrayList;
import java.util.HashSet;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.OtilukesJournal;
import com.hmdzl.spspd.change.items.keys.SkeletonKey;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.LarvaSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.utils.Random;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.sprites.GooSprite;
import com.hmdzl.spspd.change.sprites.ErrorSprite;
import com.hmdzl.spspd.change.items.AdamantRing;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.CorruptGas;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Weakness;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Sleep;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.items.Playericon;
import com.hmdzl.spspd.change.levels.Level;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class UAmulet extends Mob {

	protected static final float SPAWN_DELAY = 2f;
	private static final String TXT_UNKNOW = "??? I know nothing about it ???";

	
	{
		spriteClass = ErrorSprite.class;
		baseSpeed = 0.75f;

		HP = HT = 1000;
		EXP = 20;
		defenseSkill = 5;
		
		loot = new Playericon();
		lootChance = 1f;

		properties.add(Property.UNKNOW);
		properties.add(Property.BOSS);
	}

	private int breaks=0;
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.hero.lvl/2, Dungeon.hero.lvl);
	}

	@Override
	public int attackSkill(Char target) {
		return 100;
	}

	@Override
	public int dr() {
		return 2;
	}
	
	@Override
	public float speed() {
		if (breaks == 3) return 2*super.speed();
		else return super.speed();
	}

	@Override
    public boolean act() {

        if( 3 - breaks > 4 * HP / HT ) {
            
			breaks++;
			
            return true;
        } 
		
	    if (breaks == 1){
		   
		}
		
		if (breaks == 2){
			
		}
		
		if (breaks > 0){

		}
        return super.act();
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
	public void die(Object cause) {
	
		GameScene.bossSlain();
		super.die(cause);
		//UYog.spawnAt(pos);
	}	
	
	public static UAmulet spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			UAmulet w = new UAmulet();
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
		RESISTANCES.add(Death.class);
		IMMUNITIES.add(Death.class);
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