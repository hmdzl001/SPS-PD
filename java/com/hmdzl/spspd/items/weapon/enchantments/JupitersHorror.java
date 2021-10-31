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
package com.hmdzl.spspd.items.weapon.enchantments;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Paralysis;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.noosa.Camera;
import com.watabou.utils.Random;

public class JupitersHorror extends Weapon.Enchantment {

	private static ItemSprite.Glowing GREY = new ItemSprite.Glowing(0x222222);

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 20%
		// lvl 1 - 33%
		// lvl 2 - 43%
		int level = Math.max(0, weapon.level);

		if (Random.Int(level + 5) >= 4) {

			if (defender == Dungeon.hero) {
				Buff.affect(defender, Vertigo.class,10f);
			} else {
				Buff.affect(defender, Terror.class, Terror.DURATION).object = attacker
						.id();
				if (Random.Int(level + 11) >= 10){
					//doExplode(defender.pos);
				}
			}

			return true;
		} else {
			return false;
		}
	}
	
	   public void doExplode(int cell) {
			
			Camera.main.shake(3, 0.7f);
			
					if (Dungeon.visible[cell] && Level.passable[cell]) {
						CellEmitter.center(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
					}
					
					Char ch = Actor.findChar(cell);
					if (ch != null && ch!=Dungeon.hero) {
						// those not at the center of the blast take damage less
						// consistently.
						int minDamage = Dungeon.depth + 5;
						int maxDamage = 10 + Dungeon.depth * 3;
						                    
						
						int dmg = Random.NormalIntRange(minDamage, maxDamage) - Math.max(ch.drRoll(),0);
						
						
						if (dmg > 0) {
							ch.damage(dmg, this);
							if(Random.Int(15)==1){Buff.prolong(ch, Paralysis.class, 1);}
						}
												
	     			}

		}	


	@Override
	public Glowing glowing() {
		return GREY;
	}
}
