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
package com.hmdzl.spspd.change.actors.mobs;

import java.util.HashSet;

import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.ShockWeb;
import com.hmdzl.spspd.change.actors.blobs.Web;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.actors.buffs.Tar;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.items.StoneOre;
import com.hmdzl.spspd.change.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.SpiderBotSprite;
import com.hmdzl.spspd.change.sprites.SpinnerSprite;
import com.watabou.utils.Random;

public class SpiderBot extends Mob {

	{
		spriteClass = SpiderBotSprite.class;

		HP = HT = 150+(adj(0)*Random.NormalIntRange(4, 7));
		evadeSkill = 25+adj(1);

		EXP = 12;
		maxLvl = 22;

		loot = new StoneOre();
		lootChance = 0.3f;

		FLEEING = new Fleeing();
		
		properties.add(Property.MECH);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15+adj(0), 40+adj(1));
	}

	@Override
	public int hitSkill(Char target) {
		return  30+adj(0);
	}

	@Override
	public int drRoll() {
		return 10+adj(0);
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Tar.class) == null) {
			state = HUNTING;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Tar.class);
			state = FLEEING;
		}
		return damage;
	}

	@Override
	public void move(int step) {
		if (state == FLEEING) {
			GameScene.add(Blob.seed(pos, Random.Int(5, 7), ShockWeb.class));
		}
		super.move(step);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();

	static {
		RESISTANCES.add(Bleeding.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
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