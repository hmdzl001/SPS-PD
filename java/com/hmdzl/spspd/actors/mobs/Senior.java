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
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DBurning;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.HornOfPlenty;
import com.hmdzl.spspd.sprites.SeniorSprite;
import com.watabou.utils.Random;

public class Senior extends Monk {

	{
		spriteClass = SeniorSprite.class;
		
		properties.add(Property.DWARF);
	}

	@Override
	public boolean act() {

		if( 1 > 2 * HP / HT && !skilluse ) {
			skilluse = true;
			Buff.affect(this,AttackUp.class,10f).level(50);
			Buff.affect(this,DefenceUp.class,10f).level(75);
			HP = HT;
			//Buff.affect(this,AttackUp.class,10f).level(50);
			return true;
		}

		return super.act();
	}


	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( Generator.random(Generator.Category.HIGHFOOD) ,new HornOfPlenty());
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(32, 56+adj(0));
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		Buff.affect(enemy, DBurning.class).set(2f);
		return super.attackProc(enemy, damage);
	}
	
	@Override
	public int defenseProc(Char enemy, int damage) {


		return super.defenseProc(enemy, damage);
	}

	@Override
	public void damage(int dmg, Object src, int type) {
		if (dmg> HT/6) {
			dmg =(int)Math.max(HT/6,1);
		}

		super.damage(dmg, src,type);

	}
	

	@Override
	public void die(Object cause) {
		super.die(cause);
		
		Badges.validateRare(this);
	}
}
