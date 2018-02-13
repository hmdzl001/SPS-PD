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

import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Silent;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.SkeletonSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Skeleton extends Mob {

	private static final String TXT_HERO_KILLED = "You were killed by the explosion of bones...";

	{
		spriteClass = SkeletonSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		defenseSkill = 15+adj(0);
		baseSpeed = 0.8f;


		EXP = 5;
		maxLvl = 10;

		loot = Generator.Category.WEAPON;
		lootChance = 0.15f;
		
		properties.add(Property.UNDEAD);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 22+adj(0));
	}

		
	@Override
	public void die(Object cause) {

		super.die(cause);

		boolean heroKilled = false;
		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				int damage = Math.max(0,
						Random.NormalIntRange(3, 8) - Random.IntRange(0, ch.dr() / 2));
				ch.damage(damage, this);
				Buff.affect(ch,Silent.class,10f);
				if (ch == Dungeon.hero && !ch.isAlive()) {
					heroKilled = true;
				}
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BONES);
		}

		if (heroKilled) {
			Dungeon.fail( Messages.format(ResultDescriptions.MOB));
			//GLog.n(Messages.get(this, "kill"));
		}
	}

	@Override
	protected Item createLoot() {
		Item loot = Generator.random(Generator.Category.WEAPON);
		for (int i = 0; i < 2; i++) {
			Item l = Generator.random(Generator.Category.WEAPON);
			if (l.level < loot.level) {
				loot = l;
			}
		}
		return loot;
	}

	@Override
	public int attackSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int dr() {
		return 4+adj(0);
	}


	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
