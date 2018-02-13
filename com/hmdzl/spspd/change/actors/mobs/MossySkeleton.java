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

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.Bone;
import com.hmdzl.spspd.change.items.PrisonKey;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.YellowDewdrop;
import com.hmdzl.spspd.change.items.weapon.enchantments.Death;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.sprites.MossySkeletonSprite;
import com.hmdzl.spspd.change.utils.GLog;
 
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MossySkeleton extends Mob {

	private static final String TXT_HERO_KILLED = "You were killed by the explosion of bones...";
	private static final String TXT_KILLCOUNT = "Mossy Skeleton Kill Count: %s";

	{
		spriteClass = MossySkeletonSprite.class;

		HP = HT = 90+(10*Random.NormalIntRange(7, 10));
		defenseSkill = 20;

		EXP = 1;
		maxLvl = 10;
		
		baseSpeed = 0.5f+(Math.min(1.5f, Statistics.skeletonsKilled/50));

		loot = new YellowDewdrop();
		lootChance = 0.5f; // by default, see die()
			
		lootThird= new RedDewdrop();
		lootChanceThird = 0.1f; // by default, see die()
		
		properties.add(Property.UNDEAD);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20+Math.round(Statistics.skeletonsKilled/10), 45+Math.round(Statistics.skeletonsKilled/5));
		
	}

	@Override
	protected float attackDelay() {
		return 2f-(Math.min(1.75f, Statistics.skeletonsKilled/50));
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		Statistics.skeletonsKilled++;
		GLog.w(Messages.get(Mob.class,"killcount", Statistics.skeletonsKilled));
		
		if (!Dungeon.limitedDrops.prisonkey.dropped() && Dungeon.depth<27) {
			Dungeon.limitedDrops.prisonkey.drop();
			Dungeon.level.drop(new PrisonKey(), pos).sprite.drop();
			explodeDew(pos);				
		} else {
			explodeDew(pos);
		}
		
		if(!Dungeon.limitedDrops.bone.dropped() && Statistics.skeletonsKilled > 50 && Random.Int(10)==0) {
			Dungeon.limitedDrops.bone.drop();
			Dungeon.level.drop(new Bone(), pos).sprite.drop();
		}
		
		if(!Dungeon.limitedDrops.bone.dropped() && Statistics.skeletonsKilled > 100) {
			Dungeon.limitedDrops.bone.drop();
			Dungeon.level.drop(new Bone(), pos).sprite.drop();
		}

		boolean heroKilled = false;
		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				int damage = Math.max(0,
						Random.NormalIntRange(3, 8) - Random.IntRange(0, ch.dr() / 2));
				ch.damage(damage, this);
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
	public int attackSkill(Char target) {
		return 28;
	}

	@Override
	public int dr() {
		return 27;
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
