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
package com.hmdzl.spspd.items.skills;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.SpeedImbue;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.HashMap;


public class AsceticSkill extends ClassSkill {
	private static int SKILL_TIME = 1;

	{
		//name = "Ascetic suit of armor";
		image = ItemSpriteSheet.SKILL_ASCETIC;
	}

	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();

	@Override
	public void doSpecial() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		Buff.affect(curUser, SpeedImbue.class).level(30);
		AsceticSkill.charge += 10;
	}

	@Override
	public void doSpecial2() {
		curUser.spend(SKILL_TIME);
		GameScene.selectItem(itemSelector, WndBag.Mode.ENCHANTABLE, Messages.get(AsceticSkill.class, "prompt"));
	}

	@Override
	public void doSpecial3() {
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);

		Dungeon.hero.hitSkill--;
		Dungeon.hero.magicSkill++;

		Dungeon.hero.evadeSkill--;
		Dungeon.hero.magicSkill++;

		AsceticSkill.charge += 30;
	}

	@Override
	public void doSpecial4() {
		for (int n : Level.NEIGHBOURS8DIST2) {
			int c = curUser.pos + n;

			if (c >= 0 && c < Level.getLength()) {
				if ((Dungeon.level.map[c] == Terrain.WALL || Dungeon.level.map[c] == Terrain.GLASS_WALL || Dungeon.level.map[c] == Terrain.WALL_DECO)&& Level.insideMap(c)) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					Dungeon.observe();
				}
			}
		}

		for (int n : Level.NEIGHBOURS8OUT) {
			int d = curUser.pos + n;

			if (d >= 0 && d < Level.getLength()) {
				if (Dungeon.level.map[d] != Terrain.ENTRANCE && Dungeon.level.map[d] != Terrain.EXIT
						&& Dungeon.level.map[d] != Terrain.LOCKED_EXIT
						&& Dungeon.level.map[d] != Terrain.UNLOCKED_EXIT && Level.insideMap(d)) {
					Level.set(d, Terrain.DOOR);
					GameScene.updateMap(d);
					Dungeon.observe();
				}
			}
		}

		for (int m : Level.NEIGHBOURS8DIST2) {
			int c = curUser.pos + m;
			if (c >= 0 && c < Level.getLength()) {

				Char ch2 = Actor.findChar(c);
				if (ch2 != null) {
					int dmg = (int) (Dungeon.depth * (1 + 0.1 * Dungeon.hero.magicSkill()));
					Buff.affect(ch2, Vertigo.class, 3f);
					if (dmg > 0) {
						ch2.damage(dmg, this);
					}
				}
			}
		}
		curUser.spend(SKILL_TIME);
		curUser.sprite.operate(curUser.pos);
		curUser.busy();
		curUser.sprite.centerEmitter().start(ElmoParticle.FACTORY, 0.15f, 4);
		Sample.INSTANCE.play(Assets.SND_READ);
		charge+=20;

	}
	
	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			curUser = Dungeon.hero;
			if (item != null) {
				if (item instanceof Weapon) {
					apply((Weapon) item);
				} else if (item instanceof Armor) {
					inscribe((Armor) item);
				}
				//item.detach(Dungeon.hero.belongings.backpack);
				//Dungeon.level.drop(result, Dungeon.hero.pos).sprite.drop();
				AsceticSkill.charge += 20;
			}
		}
	};
	private void apply(Weapon weapon) {
		weapon.enchant();
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
		Sample.INSTANCE.play(Assets.SND_MISS);
	}
	private void inscribe(Armor armor) {
		armor.hasglyph();
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
		Sample.INSTANCE.play(Assets.SND_MISS);
	}
}