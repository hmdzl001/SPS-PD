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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.FrostIce;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.wands.WandOfFlow;
import com.hmdzl.spspd.items.wands.WandOfFreeze;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentIce2;
import com.hmdzl.spspd.plants.Icecap;
import com.hmdzl.spspd.sprites.IceBugSprite;
import com.watabou.utils.Random;

public class IceBug extends Mob {

	{
		spriteClass = IceBugSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);
		baseSpeed = 1.5f;

		EXP = 9;
		maxLvl = 25;
		
		loot = new StoneOre();
		lootChance = 0.3f;		
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return new Icecap.Seed();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 20+adj(0));
	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, FrostIce.class).level(5);
		}

		return damage;
	}	

	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
	    return Random.NormalIntRange(2, 5);
	}

	{
		resistances.add(DamageType.IceDamage.class);
		resistances.add(WandOfFlow.class);
		resistances.add(WandOfFreeze.class);

		immunities.add(FrostIce.class);
		
		immunities.add(EnchantmentIce.class);
		immunities.add(EnchantmentIce2.class);
	}


			
}