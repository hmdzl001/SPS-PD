/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
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
import com.hmdzl.spspd.actors.blobs.CurseWeb;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.sprites.ErrorSprite;
import com.watabou.utils.Random;

public class DemonSummoner extends Mob {

	{
		spriteClass = ErrorSprite.class;

		HP = HT = 300;
		evadeSkill = 0;

		EXP = 10;
		maxLvl = 29;

		loot = Generator.random(Generator.Category.SHOOTWEAPON);
		lootChance = 0.2f;

		properties.add(Property.DEMONIC);

		FLEEING = new Fleeing();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(30, 50);
	}

	@Override
	public int hitSkill(Char target) {
		return 50;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 6);
	}


	@Override
	protected boolean act() {

		return super.act();
	}

	@Override
	public int attackProc(Char enemy, int damage) {


		return super.attackProc(enemy, damage);
	}


	{
		resistances.add(LightShootAttack.class);
	}
	
	{
		immunities.add(CurseWeb.class);
		immunities.add(ShadowCurse.class);
	}

	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
