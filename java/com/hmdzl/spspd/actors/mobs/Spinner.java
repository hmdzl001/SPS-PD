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

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Web;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.Roots;
import com.hmdzl.spspd.actors.buffs.Terror;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.food.meatfood.MysteryMeat;
import com.hmdzl.spspd.items.weapon.melee.Whip;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.SpinnerSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

public class Spinner extends Mob {

	{
		spriteClass = SpinnerSprite.class;

		HP = HT = 120+(adj(0)*Random.NormalIntRange(5, 7));
		evadeSkill = 14+adj(1);

		EXP = 9;
		maxLvl = 25;

		loot = new MysteryMeat();
		lootChance = 0.15f;

		FLEEING = new Fleeing();
		
		properties.add(Property.BEAST);
	}

	@Override
	public Item SupercreateLoot(){
		return new Whip();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12, 26+adj(0));
	}

	@Override
	public int hitSkill(Char target) {
		return 20+adj(0);
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(6, 10);
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Poison.class) == null) {
			state = HUNTING;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Poison.class).set(
					Random.Int(7, 9));
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public void move(int step) {
		if (state == FLEEING) {
			GameScene.add(Blob.seed(pos, Random.Int(5, 7), Web.class));
		}
		super.move(step);
	}

	{
		resistances.add(Poison.class);
		immunities.add(Roots.class);
	}

	public static void spawnAround(int pos) {
		for (int n : Floor.NEIGHBOURS4) {
			int cell = pos + n;
			if (Floor.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static Spinner spawnAt(int pos) {
		if (Floor.passable[pos] && Actor.findChar(pos) == null) {

			Spinner w = new Spinner();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, 1f);

			w.sprite.alpha(0);
			w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

			w.sprite.emitter().burst(ShadowParticle.CURSE, 5);

			return w;

		} else {
			return null;
		}
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
