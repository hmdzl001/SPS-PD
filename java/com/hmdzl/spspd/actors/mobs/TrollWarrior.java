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
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.AttackUp;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.StoneOre;
import com.hmdzl.spspd.items.weapon.enchantments.EnchantmentDark;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.TrollWarriorSprite;
import com.watabou.utils.Random;

public class TrollWarrior extends Mob {
	
	{
		spriteClass = TrollWarriorSprite.class;
		baseSpeed = 1.2f;

		HP = HT = 80+(5*Random.NormalIntRange(2, 5));
		EXP = 10;
		maxLvl = 20;
		evadeSkill = 15;
		
		loot = new StoneOre();
		lootChance = 0.2f;

		properties.add(Property.TROLL);
		
	}

	@Override
	public Item SupercreateLoot(){
		return Generator.random(Generator.Category.MUSICWEAPON);
	}

	@Override
    public boolean act() {
        if( HP < HT && !skilluse ) {
			Buff.affect(this,AttackUp.class,8f).level(20);
			Buff.affect(this,DefenceUp.class,8f).level(80);
            skilluse = true;
			yell(Messages.get(this,"angry"));}
        return super.act();
    }
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 30);
	}

	@Override
	public int hitSkill(Char target) {
		return 30;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 7);
	}
	@Override
	protected float attackDelay() {
		return 1.2f;
	}

	{
		resistances.add(ToxicGas.class);
		resistances.add(Poison.class);
		resistances.add(EnchantmentDark.class);
	}

}
