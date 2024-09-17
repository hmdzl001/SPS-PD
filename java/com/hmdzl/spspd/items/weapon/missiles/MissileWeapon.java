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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.rings.RingOfSharpshooting;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.items.weapon.melee.MeleeWeapon;
import com.hmdzl.spspd.items.weapon.missiles.meleethrow.MeleeThrowWeapon;
import com.hmdzl.spspd.items.weapon.missiles.throwing.TempestBoomerang;
import com.hmdzl.spspd.items.weapon.ranges.RangeWeapon;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class MissileWeapon extends Weapon {

	{
		//levelKnown = true;
		defaultAction = AC_THROW;
		usesTargeting = true;
		stackable = true;
	}
	
	protected static final float MAX_DURABILITY = 100;
	protected float durability = MAX_DURABILITY;

	;

	//used to reduce durability from the source weapon stack, rather than the one being thrown.
	protected MissileWeapon parent;	
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.remove(AC_EQUIP);
		actions.remove(AC_UNEQUIP);		
		return actions;
	}

	protected static RangeWeapon bow;
	protected static Enchantment enchant;

	public static void updateRangeWeapon(){
		if (Dungeon.hero.belongings.weapon instanceof RangeWeapon){
			bow = (RangeWeapon) Dungeon.hero.belongings.weapon;
			if (bow.isEnchanted()) {
				enchant = bow.enchantment;
			} else {
				enchant = null;
			}
		} else {
			bow = null;
			enchant = null;
		}
	}

    @Override
	protected void onThrow(int cell) {
		//updateRangeWeapon();
		hero.damagetwice = true;

		Char enemy = Actor.findChar(cell);
		//hero.damagetwice = false;
		if (enemy == null || enemy == curUser) {
			//if (this instanceof Boomerang)
			//	super.onThrow(cell);
			//else
				miss(cell);
		} else {
			if (!curUser.shoot(enemy, this)) {
				miss(cell);
			} else if ( this instanceof TempestBoomerang || this instanceof MeleeThrowWeapon){
				if (!isdestory){
				   Dungeon.depth.drop( this, enemy.pos).sprite.drop();
				}
			} else {

				int bonus = 0;
				for (Buff buff : curUser.buffs(RingOfSharpshooting.RingShoot.class))
					bonus += ((RingOfSharpshooting.RingShoot) buff).level;
			}
		}
	}

	protected void miss(int cell) {
		int bonus = 0;
		for (Buff buff : curUser.buffs(RingOfSharpshooting.RingShoot.class)) {
			bonus += ((RingOfSharpshooting.RingShoot) buff).level;
		}
		if (curUser.heroClass == HeroClass.HUNTRESS)
			bonus += 3;
		// degraded ring of sharpshooting will even make missed shots break.
		if (Random.Float() < Math.pow(0.6, -bonus))
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		super.proc(attacker, defender, damage);

		KindOfWeapon wep = hero.belongings.weapon;
		//if (hero.heroClass== HeroClass.HUNTRESS &&  wep !=null && this instanceof MissileWeapon && !( this instanceof ManyKnive.KniveAmmo) && !(this instanceof TaurcenBow.TaurcenBowArrow)&& !(this instanceof GunWeapon.NormalAmmo)) {
		//	defender.damage(Random.Int(wep.MAX,wep.MIN),this,1);
		//}
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
	public String info() {

		String info = desc();

		info += "\n\n" + Messages.get( Weapon.class, "avg_dmg",MIN,MAX);

		if (STR > hero.STR()) {
			info += Messages.get(Weapon.class, "too_heavy");
		} else {
			info += " " + Messages.get(MeleeWeapon.class, "excess_str", hero.STR() - STR);
		}

		if (enchantment != null) {
			info += "\n" + Messages.get(MeleeWeapon.class, "enchanted", enchantment.desc());
		}
		
		return info;
	}
}
