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

import com.hmdzl.spspd.Badges;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.MagicArmor;
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.WoodenArmor;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.weapon.melee.Handaxe;
import com.hmdzl.spspd.mechanics.Ballistica;
import com.hmdzl.spspd.sprites.ShieldedSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Shielded extends Brute {

	private int breaks=0;
	{
		spriteClass = ShieldedSprite.class;

		evadeSkill = 20+adj(0);
		
		properties.add(Property.ORC);
	}

	private static final String BREAKS	= "breaks";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( BREAKS, breaks );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		breaks = bundle.getInt( BREAKS );
	}

	@Override
	public boolean act() {

		if( 2 - breaks > 3 * HP / HT ) {
			breaks++;
			Buff.affect(this,ShieldArmor.class).level(Dungeon.depth*3);
			Buff.affect(this,MagicArmor.class).level(Dungeon.depth*3);
			return true;
		}

		return super.act();
	}


	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(3)== 0) {
			int oppositeDefender = enemy.pos + (enemy.pos - pos);
			Ballistica trajectory = new Ballistica(enemy.pos, oppositeDefender, Ballistica.MAGIC_BOLT);
			WandOfFlow.throwChar(enemy, trajectory, 2);
			Buff.prolong(enemy, Vertigo.class,3f);
		}

		return damage;
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new WoodenArmor(),new Handaxe(),new CapeOfThorns());
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(10, 30);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		if (this.HP > damage && Random.Int(2) == 0){
			this.sprite.attack(enemy.pos);
			attack(enemy);

		}
		return super.defenseProc(enemy, damage);
	}

	public void damage(int dmg, Object src) {
		if (dmg> HT/6) {
			dmg =(int)Math.max(HT/6,1);
		}
		super.damage(dmg,src);

	}


	@Override
	public void die(Object cause) {
		super.die(cause);
		Badges.validateRare(this);
	}
}
