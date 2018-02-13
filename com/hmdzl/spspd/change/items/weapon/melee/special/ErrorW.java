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
package com.hmdzl.spspd.change.items.weapon.melee.special;

import com.hmdzl.spspd.change.effects.particles.ShadowParticle;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Cripple;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.buffs.Vertigo;
import com.hmdzl.spspd.change.actors.buffs.Paralysis;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Charm;
import com.watabou.utils.Random;

public class ErrorW extends MeleeWeapon {

    {
        //name = "Error Weapon";
        image = ItemSpriteSheet.NULLWARN;

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
		
		if (Random.Int(10) > 2 && ACU < 2f ){
			ACU += 0.1f;
		} 
		
		if (Random.Int(10) > 2 && DLY > 0.5f){
			DLY -= 0.05f;
		} 
		
		if (Random.Int(10) > 2 && RCH < 3){
			RCH ++;
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