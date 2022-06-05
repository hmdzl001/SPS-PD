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
import com.hmdzl.spspd.actors.buffs.Amok;
import com.hmdzl.spspd.actors.buffs.BeCorrupt;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GlassShield;
import com.hmdzl.spspd.actors.buffs.Sleep;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.artifacts.UnstableSpellbook;
import com.hmdzl.spspd.sprites.SuffererSprite;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.DARK_DAMAGE;

 public class Sufferer extends Mob {

	{
		spriteClass = SuffererSprite.class;

		HP = HT = 180+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 16+adj(1);

		EXP = 16;
		maxLvl = 35;
		
		loot = Generator.Category.SCROLL;
		lootChance = 0.35f;
		
		properties.add(Property.DEMONIC);
		properties.add(Property.MAGICER);
		properties.add(Property.HUMAN);
	}

	 @Override
	 public Item SupercreateLoot(){
		 return new UnstableSpellbook();
	 }

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(7+adj(0), 10+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return 34+adj(1);
	}


	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 10);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, BeCorrupt.class).level(20);
		}
		
		enemy.damage(damage, DARK_DAMAGE);
		damage = 0;

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		int dmg = damage;
		if (dmg > 50 && buff(GlassShield.class) == null) {
			Buff.affect(this,GlassShield.class).turns(4);
		}

		return super.defenseProc(enemy, damage);
	}	


	{
		immunities.add(Amok.class);
		immunities.add(Terror.class);
		immunities.add(Sleep.class);
	}

}
