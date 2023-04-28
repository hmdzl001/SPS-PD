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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Burning;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.completefood.PetFood;
import com.hmdzl.spspd.items.potions.PotionOfLiquidFlame;
import com.hmdzl.spspd.items.scrolls.ScrollOfRage;
import com.hmdzl.spspd.items.wands.WandOfFirebolt;
import com.hmdzl.spspd.items.wands.WandOfMeteorite;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.sprites.RedDragonSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.Dungeon.hero;
import static com.hmdzl.spspd.actors.damagetype.DamageType.FIRE_DAMAGE;

public class RedDragon extends PET {
	
	{
		//name = "red dragon";
		spriteClass = RedDragonSprite.class;       
		//flying=true;
		state = HUNTING;
		type = 504;
		cooldown = 50;
		oldcooldown=30;

		properties.add(Property.DRAGON);

	}

	@Override
	public boolean lovefood(Item item) {
		return item instanceof PetFood ||
				item instanceof PotionOfLiquidFlame ||
				item instanceof ScrollOfRage;
	}


	@Override
	public void updateStats()  {
		HT = 150 + Dungeon.hero.petLevel*5;
		evadeSkill = Dungeon.hero.petLevel;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange((6+hero.petLevel), (6+hero.petLevel*4));
	}
	
	@Override
	public Item SupercreateLoot(){
		return Random.oneOf( new WandOfFirebolt(), new WandOfMeteorite());
	}

	@Override
	public int drRoll(){
		return Random.IntRange(5+Dungeon.hero.petLevel,10+Dungeon.hero.petLevel*3);
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.petLevel + 10;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Level.distance( pos, enemy.pos ) <= 2 ;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		enemy.damage(damageRoll()/2, FIRE_DAMAGE);
		damage = damage/2;
		if (cooldown > 0) cooldown --;
		if (cooldown==0 && enemy.isAlive()) {
			Buff.affect(enemy,Burning.class).set(10);
			cooldown = Math.max(9,30 - Dungeon.hero.petLevel);
		}
		return damage;
	}

	{
		immunities.add(DamageType.FireDamage.class);
	}

}