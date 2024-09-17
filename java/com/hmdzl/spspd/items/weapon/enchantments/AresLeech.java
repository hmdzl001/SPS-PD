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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AresLeech extends Weapon.Enchantment {

	private static ItemSprite.Glowing PURPLE = new ItemSprite.Glowing(0x660066);

	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {

		int level = Math.max(0, weapon.level);
		int distance = 3;
		int maxValue = damage * (level + 2) / (level + 6);
		int effValue = Math.min(Random.IntRange(0, maxValue), attacker.HT - attacker.HP);
		
		for (Mob mob : Dungeon.depth.mobs) {

		if (Floor.distance(attacker.pos, mob.pos) < distance && mob.isAlive() ){
			  if(effValue<mob.HP){
				   weapon.charge++;
				}	
		      }
		}

		if (effValue > 0) {

			HealDamage spbuff = attacker.buff(HealDamage.class);
			if (spbuff == null) {
				spbuff = new HealDamage();
				spbuff.attachTo(attacker);
			}
			spbuff.prolong(damage);
			return true;
		} else {
			return false;
		}
		
		
	}

	@Override
	public Glowing glowing() {
		return PURPLE;
	}

	public static class HealDamage extends Buff {

		protected int damage = 0;

		private static final String DAMAGE = "damage";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(DAMAGE, damage);

		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			damage = bundle.getInt(DAMAGE);
		}

		@Override
		public boolean attachTo(Char target) {
			if (super.attachTo(target)) {
				postpone(TICK);
				return true;
			} else {
				return false;
			}
		}

		public void prolong(int damage) {
			this.damage = damage;
		}

        @Override
		public int icon() {
			return BuffIndicator.DAMAGE_DELAY;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}		
		
		@Override
		public boolean act() {
			if (target.isAlive()) {

				int damageThisTick = Math.max(1, (int)(damage*0.1f))
				;
				if (target.HP<target.HT) {
					target.HP+=damageThisTick;
				}
				
				spend(TICK);
				
				damage -= damageThisTick;
				
				if (--damage <= 0 || target.HP >= target.HT) {
					detach();
				}

			} else {

				detach();

			}

			return true;
		}
		@Override
		public String desc() {
			return Messages.get(this, "desc", damage);
		}		
		
	}
}

	

