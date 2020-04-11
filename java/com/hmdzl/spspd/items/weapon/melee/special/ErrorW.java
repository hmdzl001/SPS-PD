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
package com.hmdzl.spspd.items.weapon.melee.special;

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.Bleeding;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Charm;
import com.watabou.utils.Random;

public class ErrorW extends MeleeWeapon {

    {
        //name = "Error Weapon";
        image = ItemSpriteSheet.ERROR_WEAPON;

        STR = 0;
        MIN = 0;
        MAX = 0;
    }

    public ErrorW() {
        super(0, 1f, 1f, 1);
    }

    @Override
    public Item upgrade(boolean enchant) {
        MIN --;
		
		if (Random.Int(10) > 4 || ACU < 0.5f ){
			ACU += 0.1f;
		} else{
			ACU -= 0.1f;
		}
		
		if (Random.Int(10) > 4 || DLY < 0.5f){
			DLY += 0.05f;
		} else {
			DLY -= 0.05f;
		}
		
        return super.upgrade(enchant);
    }

    @Override
    public void proc(Char attacker, Char defender, int damage) {

        switch (Random.Int (10)) {
		case 0 :
			if (defender.properties().contains(Char.Property.BOSS) || defender.properties().contains(Char.Property.MINIBOSS)){
            defender.damage(Random.Int(defender.HT/8, defender.HT/4), this);}
			else defender.damage(Random.Int(defender.HT, defender.HT * 2), this);
			defender.sprite.emitter().burst(ShadowParticle.UP, 5);
			if (!defender.isAlive() && attacker instanceof Hero) {
				Badges.validateGrimWeapon();
			}
			break;
        case 1 :
            Buff.affect(defender, Cripple.class, 3);
			break;
        case 2 :
            Buff.affect(defender, Bleeding.class).set(5);
			break;
	    case 3 :
            Buff.affect(defender, Vertigo.class, Vertigo.duration(defender));
			Buff.affect(defender, Terror.class, Terror.DURATION).object = attacker.id();
			break;
		case 4 :
            Buff.affect(defender, Paralysis.class, 3);
			break;
		case 5 :
            Buff.affect(defender, Roots.class, 3);
		    break;
		case 6 :
            if (attacker.HP < attacker.HT){
			attacker.HP += (int)((attacker.HT)/10);
			attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);}
			break;
		case 7 :
            Buff.affect(defender, Ooze.class);
			break;
		case 8 :
            Buff.affect(defender, Charm.class, 3 ).object = attacker.id();
			break;		
		default:
			break;
        }
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }
}