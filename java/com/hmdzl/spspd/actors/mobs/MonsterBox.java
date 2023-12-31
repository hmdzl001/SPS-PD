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
package com.hmdzl.spspd.actors.mobs;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Pushing;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.KindOfArmor;
import com.hmdzl.spspd.items.KindOfWeapon;
import com.hmdzl.spspd.items.KindofMisc;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.MonsterBoxSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

import static com.hmdzl.spspd.Dungeon.hero;

public class MonsterBox extends Mob {

	{
		//name = "monster box";
		spriteClass = MonsterBoxSprite.class;
		//HT = hero.HT;
		HT = hero.HT;
		EXP = 1;
		evadeSkill = hero.evadeSkill;
		//enemySeen = true;
		properties.add(Property.UNKNOW);
	}

	@Override
	public int damageRoll() {
		return Dungeon.hero.damageRoll();
	}

	@Override
	public int hitSkill(Char target) {
		return Dungeon.hero.hitSkill;
	}


	@Override
	public boolean reset() {
		state = WANDERING;
		return true;
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		switch (Random.Int(5)){
			case 0:
				if (hero.belongings.weapon != null) {
					Class<? extends KindOfWeapon> weapon = hero.belongings.weapon.getClass();
					Dungeon.depth.drop(Item.copy(weapon), pos);
				}else break;
			break;
			case 1:
				if (hero.belongings.armor != null) {
					Class<? extends KindOfArmor> armor = hero.belongings.armor.getClass();
					Dungeon.depth.drop(Item.copy(armor), pos);
				} else break;
			break;
			case 2:
				if (hero.belongings.misc1 != null) {
					Class<? extends KindofMisc> misc1 = hero.belongings.misc1.getClass();
					Dungeon.depth.drop(Item.copy(misc1), pos);
				}else break;
			break;
			case 3:
				if (hero.belongings.misc2 != null) {
					Class<? extends KindofMisc> misc2 = hero.belongings.misc2.getClass();
					Dungeon.depth.drop(Item.copy(misc2), pos);
				}else break;
			break;
			case 4:
				if (hero.belongings.misc3 != null) {
					Class<? extends KindofMisc> misc3 = hero.belongings.misc3.getClass();
					Dungeon.depth.drop(Item.copy(misc3), pos);
				}else break;
			break;
		}
	}

	public static MonsterBox spawnAt(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int n : Floor.NEIGHBOURS8) {
				int cell = pos + n;
				if ((Floor.passable[cell] || Floor.avoid[cell])
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
					Dungeon.depth.mobPress((Mob) ch);
				} else {
					Dungeon.depth.press(newPos, ch);
				}
			} else {
				return null;
			}
		}

		MonsterBox m = new MonsterBox();
		m.HP = m.HT;
		m.pos = pos;
		m.state = m.HUNTING;
		GameScene.add(m, 1);

		m.sprite.turnTo(pos, hero.pos);
		//Buff.affect(m, Roots.class, 1000);

		if (Dungeon.visible[m.pos]) {
			CellEmitter.get(pos).burst(Speck.factory(Speck.STAR), 10);
			Sample.INSTANCE.play(Assets.SND_MIMIC);
		}

		return m;
	}

}
