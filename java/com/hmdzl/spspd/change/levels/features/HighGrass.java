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
package com.hmdzl.spspd.change.levels.features;

import com.hmdzl.spspd.change.Challenges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Invisibility;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.hero.HeroSubClass;
import com.hmdzl.spspd.change.effects.CellEmitter;
import com.hmdzl.spspd.change.effects.particles.LeafParticle;
import com.hmdzl.spspd.change.items.Dewdrop;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.RedDewdrop;
import com.hmdzl.spspd.change.items.VioletDewdrop;
import com.hmdzl.spspd.change.items.YellowDewdrop;
import com.hmdzl.spspd.change.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.change.items.food.Nut;
import com.hmdzl.spspd.change.items.food.vegetable.Vegetable;
import com.hmdzl.spspd.change.items.food.fruit.Blackberry;
import com.hmdzl.spspd.change.items.food.fruit.Blueberry;
import com.hmdzl.spspd.change.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.change.items.food.fruit.Moonberry;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.plants.BlandfruitBush;
import com.hmdzl.spspd.change.plants.Flytrap;
import com.hmdzl.spspd.change.plants.NutPlant;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.watabou.utils.Random;

public class HighGrass {

	public static void trample(Level level, int pos, Char ch) {

		Level.set(pos, Terrain.GRASS);
		GameScene.updateMap(pos);

		//if (!Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
			int naturalismLevel = 0;

			if (ch != null) {
				SandalsOfNature.Naturalism naturalism = ch
						.buff(SandalsOfNature.Naturalism.class);
				if (naturalism != null) {
					if (!naturalism.isCursed()) {
						naturalismLevel = naturalism.level() + 1;
						naturalism.charge();
					} else {
						naturalismLevel = -1;
					}
				}
			}

			if (naturalismLevel >= 0) {
				// Seed
				if (Random.Int(20 - ((int) (naturalismLevel * 3.34))) == 0) {
					Item seed = Generator.random(Generator.Category.SEED);
					level.drop(seed, pos).sprite.drop();
				}
				
				// Vegetable
				//if (Dungeon.growLevel(Dungeon.depth) && Random.Int(40 - ((int) (naturalismLevel * 3.34))) == 0) {
					//level.drop(new NutVegetable(), pos).sprite.drop();
				//}

				if (Dungeon.growLevel(Dungeon.depth) && Random.Int(15 - ((int) (naturalismLevel * 3.34))) == 0) {
					level.drop(new NutPlant.Seed(), pos).sprite.drop();
				}

				// Dew
				if (Random.Int(3 - naturalismLevel) == 0) {
					if (Random.Int(30 - naturalismLevel) == 0 && naturalismLevel>0) {
						level.drop(new YellowDewdrop(), pos).sprite.drop();
					} else if (Random.Int(50 - naturalismLevel) == 0 && naturalismLevel>2) {
						level.drop(new RedDewdrop(), pos).sprite.drop();
					} else if (Random.Int(100 - naturalismLevel) == 0 && naturalismLevel>4){
						level.drop(new VioletDewdrop(), pos).sprite.drop();
					} else {
					   level.drop(new Dewdrop(), pos).sprite.drop();
					}
				}
			}
	//	}

		int leaves = 4;

		// Barkskin
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN) {
			//Buff.affect(ch, Barkskin.class).level(ch.HT / 3);
			Buff.affect(ch, Invisibility.class,2f);
			//leaves = 8;
		}

		CellEmitter.get(pos).burst(LeafParticle.LEVEL_SPECIFIC, leaves);
		Dungeon.observe();
	}
}
