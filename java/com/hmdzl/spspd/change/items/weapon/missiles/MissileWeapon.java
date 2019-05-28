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
package com.hmdzl.spspd.change.items.weapon.missiles;

import java.util.ArrayList;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.misc.MissileShield;
import com.hmdzl.spspd.change.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.change.items.weapon.Weapon;
import com.hmdzl.spspd.change.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.utils.Random;

public class MissileWeapon extends Weapon {

	{
		levelKnown = true;
		defaultAction = AC_THROW;
		usesTargeting = true;
		stackable = true;
		bones = false; 
	}

	protected boolean sticky = true;
	
	protected static final float MAX_DURABILITY = 100;
	protected float durability = MAX_DURABILITY;

	//used to reduce durability from the source weapon stack, rather than the one being thrown.
	protected MissileWeapon parent;	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
		actions.remove(AC_UNEQUIP);		
		return actions;
	}

    @Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if (enemy == null || enemy == curUser) {
			if (this instanceof Boomerang )
				super.onThrow(cell);
			else
				miss(cell);
		} else {
			if (!curUser.shoot(enemy, this)) {
				miss(cell);
			} else if (this instanceof  MiniMoai){
				Dungeon.level.drop( this, enemy.pos).sprite.drop();
			} else if (!(this instanceof Boomerang )) {
				int bonus = 0;
				for (Buff buff : curUser.buffs(RingOfSharpshooting.Aim.class))
					bonus += ((RingOfSharpshooting.Aim) buff).level;

				if (curUser.heroClass == HeroClass.HUNTRESS)
					bonus += 3;
			}
		}
	}

	protected void miss(int cell) {
		int bonus = 0;
		for (Buff buff : curUser.buffs(RingOfSharpshooting.Aim.class)) {
			bonus += ((RingOfSharpshooting.Aim) buff).level;
		}

		// degraded ring of sharpshooting will even make missed shots break.
		if (Random.Float() < Math.pow(0.6, -bonus))
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		super.proc(attacker, defender, damage);

		Hero hero = (Hero) attacker;
		if (hero.rangedWeapon == null && stackable) {
			if (quantity == 1) {
				doUnequip(hero, false, false);
			} else {
				detach(null);
			}
		}
	}

	@Override
	public Item random() {
		return this;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public String info() {

		String info = desc();
		
		info += "\n\n" + Messages.get( Weapon.class, "avg_dmg",MIN,MAX);

		if (STR > Dungeon.hero.STR()) {
			info += Messages.get(Weapon.class, "too_heavy");
		} else if (Dungeon.hero.heroClass == HeroClass.HUNTRESS){
			info += " " + Messages.get(MeleeWeapon.class, "excess_str", 3*(Dungeon.hero.STR() - STR));
		} else {
			info += " " + Messages.get(MeleeWeapon.class, "excess_str", Dungeon.hero.STR() - STR);
		}

		if (enchantment != null) {
			info += "\n" + Messages.get(MeleeWeapon.class, "enchanted", enchantment.desc());
		}
		
		return info;
	}
}
