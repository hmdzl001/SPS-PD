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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Locked;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.GlassTotem;
import com.hmdzl.spspd.items.weapon.melee.Club;
import com.hmdzl.spspd.items.weapon.missiles.EscapeKnive;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.GnollSprite;
import com.watabou.utils.Random;

public class Gnoll extends Mob {

	{
		spriteClass = GnollSprite.class;

		HP = HT = 70+(adj(0)*Random.NormalIntRange(3, 7));
		evadeSkill = 9+adj(1);


		EXP = 10;
		maxLvl = 18;

		loot = Gold.class;
		lootChance = 0.5f;

		lootOther = Generator.random(Generator.Category.RANGEWEAPON);
		lootChanceOther = 0.5f; // by default, see die()
		
		properties.add(Property.ORC);
	}


	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new EscapeKnive(2) ,new Club(),new GlassTotem());
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10+adj(0), 20+adj(0));
	}

	
	@Override
	protected boolean canAttack(Char enemy) {if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return Level.distance( pos, enemy.pos ) <= 2 ;
	}	
	
	@Override
	public int hitSkill(Char target) {
		return 12+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 8);
	}
	
}
