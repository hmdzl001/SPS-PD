/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.Pushing;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.scrolls.ScrollOfPsionicBlast;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.MimicSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Mimic extends Mob {

	private int level;

	{
		spriteClass = MimicSprite.class;
		
		properties.add(Property.UNKNOW);
	}

	public ArrayList<Item> items;

	private static final String LEVEL = "level";
	private static final String ITEMS = "items";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ITEMS, items);
		bundle.put(LEVEL, level);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		items = new ArrayList<Item>(
				(Collection<Item>) ((Collection<?>) bundle.getCollection(ITEMS)));
		adjustStats(bundle.getInt(LEVEL));
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(HT/20, HT/10);
	}

	@Override
	public int attackSkill(Char target) {
		return 9 + level;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		return super.attackProc(enemy, damage);
	}

	public void adjustStats(int level) {
		this.level = level;

		HT = (30 + level) * 4;
		EXP = 2 + 2 * (level - 1) / 5;
		defenseSkill = attackSkill(null) / 2;

		enemySeen = true;
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		if (items != null) {
			for (Item item : items) {
				Dungeon.level.drop(item, pos).sprite.drop();
			}
		}
	}

	@Override
	public boolean reset() {
		state = WANDERING;
		return true;
	}

	public static Mimic spawnAt(int pos, List<Item> items) {
		Char ch = Actor.findChar(pos);
		if (ch != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int n : Level.NEIGHBOURS8) {
				int cell = pos + n;
				if ((Level.passable[cell] || Level.avoid[cell])
						&& Actor.findChar(cell) == null) {
					candidates.add(cell);
				}
			}
			if (candidates.size() > 0) {
				int newPos = Random.element(candidates);
				Actor.addDelayed(new Pushing(ch, ch.pos, newPos), -1);

				ch.pos = newPos;
				// FIXME
				if (ch instanceof Mob) {
					Dungeon.level.mobPress((Mob) ch);
				} else {
					Dungeon.level.press(newPos, ch);
				}
			} else {
				return null;
			}
		}

		Mimic m = new Mimic();
		m.items = new ArrayList<Item>(items);
		m.adjustStats(Dungeon.depth);
		m.HP = m.HT;
		m.pos = pos;
		m.state = m.HUNTING;
		GameScene.add(m, 1);

		m.sprite.turnTo(pos, Dungeon.hero.pos);

		if (Dungeon.visible[m.pos]) {
			CellEmitter.get(pos).burst(Speck.factory(Speck.STAR), 10);
			Sample.INSTANCE.play(Assets.SND_MIMIC);
		}

		return m;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
