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
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.wands.Wand;
import com.hmdzl.spspd.items.weapon.missiles.Tamahawk;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.BruteSprite;
import com.hmdzl.spspd.sprites.CharSprite;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Brute extends Mob {


	{
		spriteClass = BruteSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(1, 2));
		evadeSkill = 10+adj(0);

		EXP = 8;
		maxLvl = 25;

		loot = Gold.class;
		lootChance = 0.5f;

		lootOther = Generator.random(Generator.Category.RANGEWEAPON);
		lootChanceOther = 0.5f; // by default, see die()
		
		properties.add(Property.ORC);
	}

	private boolean enraged = false;

	@Override
	public Item SupercreateLoot(){
		return new Tamahawk(5);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		enraged = HP < HT / 4;
	}

	@Override
	public int damageRoll() {
		return enraged ? Random.NormalIntRange(25+adj(0), 40+adj(0)) : Random.NormalIntRange(5+adj(0), 25+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 10+adj(1);
	}

	@Override
	protected float attackDelay() {
		return 1.5f;
	}
	@Override
	public int drRoll() {
		return enraged ? 0 :Random.NormalIntRange(0, 10);
	}

	@Override
	public void damage(int dmg, Object src) {
		super.damage(dmg, src);

		if (isAlive() && !enraged && HP < HT / 4) {
			enraged = true;
			Buff.affect(this,DefenceUp.class,3f).level(70);
			spend(TICK);
			if (Dungeon.visible[pos]) {
				GLog.w(Messages.get(this, "enraged"));
				sprite.showStatus(CharSprite.NEGATIVE, "enraged");
			}
		}
	}
	
	/*@Override
	protected boolean canAttack(Char enemy) {if (buff(Locked.class) != null){
			return Level.adjacent(pos, enemy.pos) && (!isCharmedBy(enemy));
		} else
		return Dungeon.level.distance( pos, enemy.pos ) <= 2 ;
	}	*/

	{
		weakness.add(Wand.class);
		immunities.add(Terror.class);
	}

}
