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
package com.hmdzl.spspd.items.weapon.missiles;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Splash;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.summon.Honeypot;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class HoneyArrow extends MissileWeapon {


	{
		//name = "HoneyArrow";
		image = ItemSpriteSheet.HONEY_ARROW;

		STR = 10;

		MIN = 1;
		MAX = 1;
	}

	public HoneyArrow() {
		this(2);
	}

	public HoneyArrow(int number) {
		super();
		quantity = number;
	}

	@Override
	protected void onThrow(int cell) {
		Char enemy = Actor.findChar(cell);
		if (enemy == null) {
			shatter(null, cell);
		} else
			super.onThrow(cell);
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		for (int n : Level.NEIGHBOURS4) {
			int cell = defender.pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				shatter(null, cell);
			}
		}


		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(1, 2);
		return this;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

	public void shatter(Char owner, int pos) {

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_SHATTER);
			Splash.at(pos, 0xffd500, 5);
		}

		int newPos = pos;
		if (Actor.findChar(pos) != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			for (int n : Level.NEIGHBOURS4) {
				int c = pos + n;
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
		}

		if (newPos != -1) {
			if (Dungeon.hero.subClass == HeroSubClass.LEADER) {
				Honeypot.SteelBee bee = new Honeypot.SteelBee();
				bee.spawn(Dungeon.depth);
				bee.HP = bee.HT;
				bee.pos = newPos;

				GameScene.add(bee);
				Actor.addDelayed(new Pushing(bee, pos, newPos), -1f);

				bee.sprite.alpha(0);
				bee.sprite.parent.add(new AlphaTweener(bee.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);

			} else {
				Honeypot.Bee bee = new Honeypot.Bee();
				bee.spawn(Dungeon.depth);
				bee.setPotInfo(pos, owner);
				bee.HP = bee.HT;
				bee.pos = newPos;

				GameScene.add(bee);
				Actor.addDelayed(new Pushing(bee, pos, newPos), -1f);

				bee.sprite.alpha(0);
				bee.sprite.parent.add(new AlphaTweener(bee.sprite, 1, 0.15f));

				Sample.INSTANCE.play(Assets.SND_BEE);
			}

		}

	}
}
