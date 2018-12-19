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
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.actors.blobs.ElectriShock;
import com.hmdzl.spspd.change.items.bags.HeartOfScarecrow;
import com.hmdzl.spspd.change.items.wands.WandOfLightning;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentShock;
import com.hmdzl.spspd.change.items.weapon.enchantments.EnchantmentShock2;
import com.hmdzl.spspd.change.mechanics.Ballistica;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.sprites.ScarecrowSprite;
import com.watabou.utils.Bundle;

import java.util.HashSet;

public class TestMob2 extends Mob {
	

	private boolean skill = false;

	{
		spriteClass = ScarecrowSprite.class;

		HP = HT = 100000;
		evadeSkill = 0;

		state = PASSIVE;

		properties.add(Property.MECH);
	}


	@Override
    public boolean act() {
        //if( HP < HT && skill == false ) {
			//Buff.affect(this,AttackUp.class,5f).level(20);
			//Buff.affect(this,DefenceUp.class,5f).level(80);
           // skill = true;
       // }
        return super.act();
    }

	@Override
	public void damage(int dmg, Object src) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		super.damage(dmg, src);
		//Buff.prolong(this,ArmorBreak.class,10f).level(50);
	}

	@Override
	public int damageRoll() {
		return 5;
	}

	@Override
	public int hitSkill(Char target) {
		return 100;
	}

	@Override
	public int drRoll() {
		return 0;
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}	

	@Override
	public void die(Object cause) {
		super.die(cause);
		
		if (!Dungeon.limitedDrops.heartScarecrow.dropped()) {
			Dungeon.limitedDrops.heartScarecrow.drop();
			Dungeon.level.drop(new HeartOfScarecrow(), pos).sprite.drop();
			explodeDew(pos);
		}
		
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> WEAKNESS = new HashSet<Class<?>>();
	static {
		WEAKNESS.add(ElectriShock.class);
		WEAKNESS.add(WandOfLightning.class);
		WEAKNESS.add(EnchantmentShock.class);
		WEAKNESS.add(EnchantmentShock2.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> weakness() {
		return WEAKNESS;
	}


	private final String SKILL = "skill";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SKILL, skill);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		skill = bundle.getBoolean(SKILL);
	}	

}
