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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.actors.mobs.YearBeast;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.SmokeParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class FireCracker extends MeleeWeapon {

	{
		//name = "FireCracker";
		image = ItemSpriteSheet.FIRECRACKER;
		 
		usesTargeting = true;
	}

	public FireCracker() {
		super(1, 1f, 1f, 2);
		MIN = 1;
		MAX = 5;
	}
	

	@Override
	public Item upgrade(boolean enchant) {
		
		return super.upgrade(enchant);
    }
	
    @Override
    public void proc(Char attacker, Char defender, int damage) {

		if (defender instanceof YearBeast) {
             defender.damage(1,this);
		}

		if (Random.Int(100)> 75) {
            for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
                mob.beckon(attacker.pos);
            }
		}

		if (Random.Int(100)> 50 ){
			for (int n : Level.NEIGHBOURS9) {
				int c = defender.pos + n;
				if (c >= 0 && c < Level.getLength()) {
					if (Dungeon.visible[c]) {
						CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
					}
					Char ch = Actor.findChar(c);
					if (ch != null) {
						// those not at the center of the blast take damage less
						// consistently.
						int minDamage = ch.HT/40;
						int maxDamage = ch.HT/20;

						int dmg = Random.NormalIntRange(minDamage, maxDamage)
								- Math.max(ch.drRoll(),0);
						if (dmg > 0) {
							ch.damage(dmg, this);
						}
					}
				}
			}
		}

		if (Random.Int(100)> 70) {
            Buff.affect(defender,Terror.class,5f).object = attacker.id();
		}

		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
    }		
}
