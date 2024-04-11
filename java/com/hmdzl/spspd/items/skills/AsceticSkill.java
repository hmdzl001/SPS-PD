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
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.SpeedImbue;
import com.hmdzl.spspd.actors.buffs.Vertigo;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.effects.particles.PurpleParticle;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.GreatRune;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.armor.Armor;
import com.hmdzl.spspd.items.weapon.Weapon;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

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
		Buff.affect(curUser, SpeedImbue.class, 40f);
		if (Dungeon.hero.lvl < 56) {
			AsceticSkill.charge += 10;
		}
	}

	@Override
	public void doSpecial2() {
		curUser.spend(SKILL_TIME);
		Dungeon.depth.drop(new GreatRune(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
		if (Dungeon.hero.lvl > 55) {
			Dungeon.depth.drop(Generator.random(), Dungeon.hero.pos);
		}
		AsceticSkill.charge += 20;
		curUser.sprite.operate(curUser.pos);
		curUser.sprite.centerEmitter().start(PurpleParticle.BURST, 0.05f, 10);
		Sample.INSTANCE.play(Assets.SND_MISS);
		//GameScene.selectItem(itemSelector, WndBag.Mode.ENCHANTABLE, Messages.get(AsceticSkill.class, "prompt"));
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

		if (Dungeon.hero.lvl > 55) {
			switch (Random.Int(3)){
				case 0:
					Dungeon.hero.magicSkill++;
					break;
				case 1:
					Dungeon.hero.hitSkill++;
					break;
				case 2:
					Dungeon.hero.evadeSkill++;
					break;
			}
		}
	}

	@Override
	public void doSpecial4() {
		PathFinder.buildDistanceMap( curUser.pos, BArray.not( Floor.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				if (i >= 0 && i < Floor.getLength()) {
					if ((Dungeon.depth.map[i] == Terrain.WALL || Dungeon.depth.map[i] == Terrain.GLASS_WALL || Dungeon.depth.map[i] == Terrain.WALL_DECO) && Floor.insideMap(i)) {
						Floor.set(i, Terrain.EMBERS);
						GameScene.updateMap(i);
						Dungeon.observe();
					}
					Char ch2 = Actor.findChar(i);
					if (ch2 != null) {
						int dmg = (int) (Dungeon.dungeondepth * (1 + 0.1 * Dungeon.hero.magicSkill()));
						if (Dungeon.hero.lvl > 55) {
							Buff.affect(ch2, Vertigo.class, 10f);
							Buff.affect(ch2, Blindness.class, 10f);
						}
						if (dmg > 0) {
							ch2.damage(dmg, this);
						}
					}
				}
			}
		}

		for (int n : Floor.NEIGHBOURS4) {
			int d = curUser.pos + n;
			if (d >= 0 && d < Floor.getLength()) {
				if (Dungeon.depth.map[d] != Terrain.ENTRANCE && Dungeon.depth.map[d] != Terrain.EXIT
						&& Dungeon.depth.map[d] != Terrain.LOCKED_EXIT
						&& Dungeon.depth.map[d] != Terrain.UNLOCKED_EXIT && Floor.insideMap(d)) {
					Floor.set(d, Terrain.DOOR);
					GameScene.updateMap(d);
					Dungeon.observe();
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