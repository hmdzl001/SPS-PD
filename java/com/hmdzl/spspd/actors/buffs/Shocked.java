/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
package com.hmdzl.spspd.actors.buffs;

import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.ui.BuffIndicator;

public class Shocked extends FlavourBuff {

    //private static final String TXT_DISARMED = "Sudden shock have made you drop your %s on the ground!";
	 public static final float DURATION = 5f;
	
	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.SHOCKED;
	}


	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}

    /*@Override
    public boolean act(){

	    Wet wet =  target.buff(Wet.class);

        
        if( target.isAlive()){
        if( !target.flying && Level.water[ target.pos ] || wet!= null){
            discharge();
           } else spend(TICK);
		}

        if (Level.flamable[target.pos] && !target.flying) {
            detach();
        }
        return super.act();
    }

    public void discharge() {

        target.damage(Random.IntRange(target.HT/4,target.HT/6),this);

//        target.sprite.showStatus( CharSprite.NEGATIVE, "ZAP!");

        if( target instanceof Hero ) {

            Camera.main.shake( 2, 0.3f );

            Hero hero = (Hero)target;
            EquipableItem weapon = hero.belongings.weapon;

            if( weapon != null && !(weapon instanceof Knuckles || weapon instanceof FightGloves)
                    && !weapon.cursed) {
                GLog.w(Messages.get(this, "knock",weapon.name()));
                weapon.doDrop(hero);

            }

        } else {
            Buff.prolong(target,Paralysis.class,2f );
        }

        /*if (target.sprite.visible) {
            target.sprite.centerEmitter().burst( SparkParticle.FACTORY, (int)Math.ceil( duration ) + 1 );
        }

        detach();
    };

    @Override
    public void onDeath() {

        //Badges.validateDeathFromFire();

        Dungeon.fail(Messages.format(ResultDescriptions.POISON));
        //GLog.n(TXT_BURNED_TO_DEATH);
    }*/

}
