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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.CorruptGas;
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.particles.EnergyParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.potions.PotionOfMending;
import com.hmdzl.spspd.items.weapon.guns.GunD;
import com.hmdzl.spspd.items.weapon.missiles.arrows.GlassFruit;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.DemonRabbitSprite;
import com.watabou.utils.Random;

public class DemonRabbit extends Mob {

	{
		spriteClass = DemonRabbitSprite.class;

		HP = HT = 200+(adj(0)*Random.NormalIntRange(3, 5));
		evadeSkill = 30+adj(1);

		EXP = 12;
		maxLvl = 30;

        baseSpeed = 0.4f;

		loot = new GlassFruit();
		lootChance = 0.2f;
		
		lootOther = new PotionOfMending();
		lootChanceOther = 0.1f; // by default, see die()
		
		properties.add(Property.DEMONIC);
        properties.add(Property.ORC);
	}

    @Override
    public Item SupercreateLoot(){
        return new GunD();
    }

    @Override
    public boolean act() {

        if( !enemySeen )
            skilluse = false;

        return super.act();

    }


	
    @Override
    protected boolean doAttack( Char enemy ) {
        //Ballistica dis = new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE);
        if( enemySeen && state != SLEEPING && paralysed == 0 && !skilluse ) {

            skilluse = true;

            if( Dungeon.visible[ pos ] ) {
                sprite.centerEmitter().burst(EnergyParticle.FACTORY, 15);
            }

            spend( attackDelay() );

            return true;

        } else {

            skilluse = false;

            return super.doAttack( enemy );
        }
    }	
	
    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos == enemy.pos;
    }

    @Override
    public void onAttackComplete() {
        //attack(enemy);
        int oldpos = pos;
        if (getFurther(enemy.pos)){
            moveSprite(oldpos,pos);
        }
        spend(attackDelay());
        super.onAttackComplete();
    }

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(5, 10);
	}

	@Override
	public int hitSkill(Char target) {
		return 25+adj(01);
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(3, 5);
	}

    @Override
    public int attackProc(Char enemy, int damage) {
        GameScene.add(Blob.seed(enemy.pos, 8, CorruptGas.class));
        skilluse = false;
        return damage;
    }

	{
		immunities.add(Amok.class);
		immunities.add(Terror.class);
        immunities.add(Bleeding.class);
	}
}
