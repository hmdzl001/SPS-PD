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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Amok;
import com.hmdzl.spspd.change.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.bombs.Bomb;
import com.hmdzl.spspd.change.items.bombs.DungeonBomb;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.sprites.MusketeerSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Musketeer extends Mob {


    private boolean charged = false;

    private static final String CHARGED = "charged";

	{
		spriteClass = MusketeerSprite.class;

		HP = HT = 160+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 30+adj(1);

		EXP = 12;
		maxLvl = 30;

		loot = new StoneOre();
		lootChance = 0.2f;
		
		lootOther = new DungeonBomb();
		lootChanceOther = 0.1f; // by default, see die()
		
		properties.add(Property.DWARF);
	}

    @Override
    public boolean act() {

        if( !enemySeen )
            charged = false;

        return super.act();

    }	
	
    @Override
    protected boolean doAttack( Char enemy ) {
        //Ballistica dis = new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE);
        int dist = Level.distance(pos, enemy.pos);
        if (dist == 1){
            return super.doAttack( enemy );
        } else if( enemySeen && state != SLEEPING && paralysed == 0 && !charged ) {

            charged = true;

            if( Dungeon.visible[ pos ] ) {
                sprite.centerEmitter().burst(EnergyParticle.FACTORY, 15);
            }

            spend( attackDelay() );

            return true;

        } else {

            charged = false;

            return super.doAttack( enemy );
        }
    }	
	
    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
    }

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(35, 60+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 35+adj(01);
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 10);
	}

    @Override
    public int attackProc(Char enemy, int damage) {
        int dist = Level.distance(pos, enemy.pos);
        if (dist > 1 && Random.Int(4)< 1 ){
            Buff.affect(enemy, ArmorBreak.class,5f).level(25);
        }
        return damage;
    }
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Terror.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	@Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put( CHARGED, charged );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);
        charged = bundle.getBoolean( CHARGED );
    }
}
