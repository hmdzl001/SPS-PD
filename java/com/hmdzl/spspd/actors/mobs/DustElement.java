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
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Ooze;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Wet;
import com.hmdzl.spspd.actors.damagetype.DamageType;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.WandOfAcid;
import com.hmdzl.spspd.items.wands.WandOfSwamp;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.DustElementSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.EARTH_DAMAGE;

public class DustElement extends Mob {

	{
		spriteClass = DustElementSprite.class;

		HP = HT = 35 + (Dungeon.dungeondepth * Random.NormalIntRange(1, 3));
		evadeSkill = 4 + (Math.round((Dungeon.dungeondepth) / 2));

		EXP = 2;
		maxLvl = 8;

		flying = true;

		loot = Generator.random(Generator.Category.SEED);
		lootChance = 0.5f;

		properties.add(Property.ELEMENT);
	}

	@Override
	public Item SupercreateLoot() {
		return Generator.random(Generator.Category.NORNSTONE);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(2, 5 + (Dungeon.dungeondepth));
	}

	@Override
	public int hitSkill(Char target) {
		return 11 + (Dungeon.dungeondepth);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(10) == 0) {
			Buff.prolong(enemy, Blindness.class, Random.Int(3, 10));
			//GLog.w(Messages.get(this, "blind"));
			//Dungeon.observe();
		}

		enemy.damage(damageRoll(), EARTH_DAMAGE,2);
		damage = 0;
		return damage;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}

	@Override
	public void add(Buff buff) {
		if (buff instanceof Roots) {
			if (HP < HT && this.isAlive()) {
				HP+=HT/10;
				sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
			}
		} else if (buff instanceof Wet) {
			if (Floor.water[this.pos])
				damage(Random.NormalIntRange(HT / 2, HT), buff,3);
			else
				damage(Random.NormalIntRange(1, HT * 2 / 3), buff,3);
		} else {
			super.add(buff);
		}
	}

	{
		resistances.add(DamageType.EarthDamage.class);
		resistances.add(WandOfAcid.class);
		resistances.add(Ooze.class);
		resistances.add(WandOfSwamp.class);
	}
}
