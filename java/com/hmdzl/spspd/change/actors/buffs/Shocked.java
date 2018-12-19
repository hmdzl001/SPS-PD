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
package com.hmdzl.spspd.change.actors.buffs;

import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.particles.SparkParticle;
import com.hmdzl.spspd.change.items.EquipableItem;
import com.hmdzl.spspd.change.items.rings.RingOfElements;
import com.hmdzl.spspd.change.items.weapon.melee.FightGloves;
import com.hmdzl.spspd.change.items.weapon.melee.Knuckles;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.BuffIndicator;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.change.Statistics.duration;

public class Shocked extends Buff implements Hero.Doom {

    //private static final String TXT_DISARMED = "Sudden shock have made you drop your %s on the ground!";
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
		return Messages.get(this, "desc");
	}

    @Override
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
        }*/

        detach();
    };

    @Override
    public void onDeath() {

        //Badges.validateDeathFromFire();

        Dungeon.fail(Messages.format(ResultDescriptions.POISON));
        //GLog.n(TXT_BURNED_TO_DEATH);
    }

}
