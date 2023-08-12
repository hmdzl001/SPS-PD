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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.buffs.Light;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Pumpkin extends MeleeWeapon {

	{
		//name = "Pumpkin Lamp";
		image = ItemSpriteSheet.PUMPKIN;
		 
		usesTargeting = true;
	}

	public Pumpkin() {
		super(1, 1f, 1f, 1);
		MIN = 1;
		MAX = 5;
	}
	

	@Override
	public Item upgrade(boolean enchant) {
		
		return super.upgrade(enchant);
    }
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

		if (Random.Int(100) < 20) {
			Buff.affect(defender, Burning.class).set(5f);
			defender.damage(Random.Int(1, level + 2), this);
			defender.sprite.emitter().burst(FlameParticle.FACTORY, level + 1);
		}
		if (Random.Int(100) < 20) {
			Buff.affect(defender, Terror.class, 3);
		}
		if (Random.Int(100) < 20) {
			if (attacker.HP < attacker.HT){
			attacker.HP += 10;
			attacker.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f,1);}
		}
		Buff.prolong(attacker, Light.class, 50f);
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }		
}
