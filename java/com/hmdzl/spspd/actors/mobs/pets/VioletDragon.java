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

import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.scrolls.ScrollOfRegrowth;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.VioletDragonSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;

public class VioletDragon extends PET{
	
	{
		//name = "violet dragon";
		spriteClass = VioletDragonSprite.class;       
		//flying=true;
		state = HUNTING;

		type = 506;
		cooldown=50;
		oldcooldown=30;
		properties.add(Property.DRAGON);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof PotionOfToxicGas ||
				item instanceof ScrollOfRegrowth;
	}


	@Override
	public void updateStats()  {
		HT = 150 + hero.petLevel*5;
		evadeSkill = hero.petLevel;
	}
	

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((6+hero.petLevel), (6+hero.petLevel*4));
	}

	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new WandOfSwamp(), new WandOfAcid());
	}

	@Override
	public int drRoll(){
		return Random.IntRange(5+hero.petLevel,10+hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return hero.petLevel + 10;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Floor.distance( pos, enemy.pos ) <= 2 ;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		enemy.damage(damageRoll()/2, EARTH_DAMAGE,2);
		damage = damage/2;
		if (cooldown > 0) cooldown --;
		if (cooldown==0 && enemy.isAlive()) {
			Buff.affect(enemy,Ooze.class).set(hero.petLevel);
			cooldown = Math.max(9,30 - hero.petLevel);
		}
		return damage;
	}

	{
		immunities.add(DamageType.EarthDamage.class);
	}
}