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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.effectblobs.ElectriShock;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HiddenShadow;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.bags.HeartOfScarecrow;
import com.hmdzl.spspd.items.wands.WandOfLightning;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentShock2;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ScarecrowSprite;
import com.watabou.utils.Bundle;

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
	public void damage(int dmg, Object src, int type) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		super.damage(dmg, src,type);
		//Buff.prolong(this,Levitation.class,10f);
		//Buff.prolong(this,ArmorBreak.class,10f).level(50);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		//if(enemy instanceof Hero){
		//	BugMeat bugfood = new BugMeat();
		//	if (!bugfood.collect()) {
		//		Dungeon.level.drop( bugfood, Dungeon.hero.pos ).sprite.drop();
		//	} else if (enemy.buff(BugMeat.BugSlow.class) == null)
		//		Buff.affect(enemy,BugMeat.BugSlow.class);
		//}
		//Buff.affect(this,Terror.class,6f).object = enemy.id();
		Buff.prolong(this,HiddenShadow.class,2f);
		return damage;
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
				if (buff(Locked.class) != null){
			return Floor.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		
		Dungeon.depth.drop(new HeartOfScarecrow(), pos).sprite.drop();
		explodeDew(pos);
			
	}

	{
		weakness.add(DamageType.ShockDamage.class);
		weakness.add(ElectriShock.class);
		weakness.add(WandOfLightning.class);
		weakness.add(EnchantmentShock.class);
		weakness.add(EnchantmentShock2.class);
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
