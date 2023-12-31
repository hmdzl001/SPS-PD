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

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.ResultDescriptions;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Silent;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.weapon.melee.StoneCross;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SkeletonSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Skeleton extends Mob {

	protected static final float SPAWN_DELAY = 2f;
	protected static final String LEVEL = "level";

	protected int level;
	{
		spriteClass = SkeletonSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(2, 5));
		evadeSkill = 15+adj(0);
		baseSpeed = 0.8f;


		EXP = 5;
		maxLvl = 25;

		loot = Generator.Category.MELEEWEAPON;
		lootChance = 0.15f;
		
		properties.add(Property.UNDEAD);
	}

	@Override
	public Item SupercreateLoot(){
		return new StoneCross();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 22+adj(0));
	}

		
	@Override
	public void die(Object cause) {

		super.die(cause);

		boolean heroKilled = false;
		for (int i = 0; i < Floor.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Floor.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				int damage = Math.max(0,
						Random.NormalIntRange(3, 8) - Random.IntRange(0, ch.drRoll() / 2));
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
			Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
			//GLog.n(Messages.get(this, "kill"));
		}
	}

	@Override
	protected Item createLoot() {
		Item loot = Generator.random(Generator.Category.MELEEWEAPON);
		for (int i = 0; i < 2; i++) {
			Item l = Generator.random(Generator.Category.MELEEWEAPON);
			if (l.level < loot.level) {
				loot = l;
			}
		}
		return loot;
	}

	@Override
	public int hitSkill(Char target) {
		return 16+adj(0);
	}

	@Override
	public int drRoll() {
	    return Random.NormalIntRange(2, 5);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getInt(LEVEL);
		adjustStats(level);
	}

	public void adjustStats(int level) {
		this.level = level;
		evadeSkill = hitSkill(null) * 5;
		enemySeen = true;
	}

	//{
		//IMMUNITIES.add(EnchantmentDark.class);
	//}
	
	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static Skeleton spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {
          
			Skeleton w = new Skeleton();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);
			return w;
  			
		} else {
			return null;
		}
	}			
}
