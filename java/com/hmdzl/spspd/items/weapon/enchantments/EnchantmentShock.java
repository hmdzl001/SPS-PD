/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.Lightning;
import com.hmdzl.spspd.effects.particles.SparkParticle;
import com.hmdzl.spspd.items.misc.FourClover;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.relic.RelicMeleeWeapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

import static com.hmdzl.spspd.actors.damagetype.DamageType.SHOCK_DAMAGE;

public class EnchantmentShock extends Weapon.Enchantment {

    private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing( 0x00FF00 );
	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}

	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 25%
		// lvl 1 - 40%
		// lvl 2 - 50%
		FourClover.FourCloverBless fcb = attacker.buff(FourClover.FourCloverBless.class);
		int level = Math.min(20, attacker.HT/10);
		int maxdmg = level + weapon.level;

		if (Random.Int(level + 4) >= 3) {

			points[0] = attacker.pos;
			nPoints = 1;

			affected.clear();
			affected.add(attacker);

			hit(defender, (int)(Random.Int(level,maxdmg)*0.75));
			
			if(fcb != null){
			   defender.damage((int)(Random.Int(level,maxdmg)*0.50), SHOCK_DAMAGE,2);
		    }

			attacker.sprite.parent.add(new Lightning( attacker.pos, defender.pos, null ));

			return true;

		} else {

			return false;

		}
	}

	private ArrayList<Char> affected = new ArrayList<Char>();

	private int[] points = new int[20];
	private int nPoints;

	private void hit(Char ch, int damage) {

		if (damage < 1) {
			return;
		}

		affected.add(ch);
		ch.damage(Floor.water[ch.pos] && !ch.flying ? damage * 2
				: damage, SHOCK_DAMAGE,2);

		ch.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
		ch.sprite.flash();

		points[nPoints++] = ch.pos;

		HashSet<Char> ns = new HashSet<Char>();
		for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			Char n = Actor.findChar(ch.pos + Floor.NEIGHBOURS8[i]);
			if (n != null && !affected.contains(n)) {
				ns.add(n);
			}
		}

		if (ns.size() > 0) {
			hit(Random.element(ns), Random.Int(damage / 2, damage));
		}
	}
	
	@Override
	public Glowing glowing() {
		return GREEN;
	}
}

