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
package com.hmdzl.spspd.actors.mobs.pets;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.normalarmor.ErrorArmor;
import com.hmdzl.spspd.items.wands.WandOfError;
import com.hmdzl.spspd.items.weapon.melee.special.ErrorW;
import com.hmdzl.spspd.items.weapon.missiles.throwing.ErrorAmmo;
import com.hmdzl.spspd.sprites.BugDragonSprite;
import com.watabou.utils.Random;

public class BugDragon extends PET{
	
	{
		//name = "bug dragon";
		spriteClass = BugDragonSprite.class;
		//flying=true;
		state = HUNTING;
		type = 510;
		cooldown=50;
		oldcooldown=30;
		properties.add(Property.DRAGON);
	}
	
	@Override
	public void updateStats()  {
		HT = 150 + Dungeon.hero.petLevel*10;
		evadeSkill = 20 + Dungeon.hero.petLevel;
	}


	@Override
	public boolean lovefood(Item item) {
		return false;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((15+Dungeon.hero.petLevel), (15+Dungeon.hero.petLevel*5));
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new ErrorAmmo(1), new ErrorArmor(),new ErrorW(),new WandOfError());
	}

	@Override
	public int drRoll(){
		return Random.IntRange(5+Dungeon.hero.petLevel,10+Dungeon.hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return Random.Int(Dungeon.hero.petLevel)+Dungeon.hero.petLevel;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
	
		if (cooldown<3 && enemy.isAlive()) {
			enemy.damage(enemy.HT,Item.class);
		}
		cooldown = Random.Int(100);
		return damage;
	}

}