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
package com.hmdzl.spspd.levels.features;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.LeafParticle;
import com.hmdzl.spspd.items.Dewdrop;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.RedDewdrop;
import com.hmdzl.spspd.items.VioletDewdrop;
import com.hmdzl.spspd.items.YellowDewdrop;
import com.hmdzl.spspd.items.artifacts.CapeOfThorns;
import com.hmdzl.spspd.items.artifacts.SandalsOfNature;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.utils.Random;

public class HighGrass {

	public static void trample(Level level, int pos, Char ch) {

		Level.set(pos, Terrain.GRASS);
		GameScene.updateMap(pos);
		Statistics.findseed ++;

		//if (!Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
			int naturalismLevel = 0;
		int thornsLevel = 0;

			if (ch != null) {
				SandalsOfNature.Naturalism naturalism = ch
						.buff(SandalsOfNature.Naturalism.class);
				CapeOfThorns.Thorns thorns = ch.buff(CapeOfThorns.Thorns.class);
				if (naturalism != null) {
					if (!naturalism.isCursed()) {
						naturalismLevel = naturalism.level();
						naturalism.charge();
					} else {
						naturalismLevel = -1;
					}
				}
				if (thorns != null) {
					if (!thorns.isCursed()) {
						thornsLevel = thorns.level();
					} else {
						thornsLevel = -1;
					}
				}

			}

			// Seed
			if (Random.Int(20) == 0) {
					Item seed = Generator.random(Generator.Category.SEED);
					level.drop(seed, pos).sprite.drop();
			}		
			
			// Dew
			if (Random.Int(10) == 0) {
				if (Random.Int(3) == 0 && naturalismLevel>9) {
					level.drop(new VioletDewdrop(), pos).sprite.drop();		
				} else if (Random.Int(3) == 0 && naturalismLevel>6) {
						level.drop(new RedDewdrop(), pos).sprite.drop();
					} else if (Random.Int(3) == 0 && naturalismLevel>3){
						level.drop(new YellowDewdrop(), pos).sprite.drop();
					} else {
					   level.drop(new Dewdrop(), pos).sprite.drop();
					}
			}			
			
			if (naturalismLevel >= 1) {
				// Seed
				if (Statistics.findseed >55 - naturalismLevel*5) {
					Item seed = Generator.random(Generator.Category.SEED);
					level.drop(seed, pos).sprite.drop();
					Statistics.findseed = 0;
				}
				
				// Vegetable
				//if (Dungeon.growLevel(Dungeon.depth) && Random.Int(40 - ((int) (naturalismLevel * 3.34))) == 0) {
					//level.drop(new NutVegetable(), pos).sprite.drop();
				//}


			}
		if (thornsLevel >= 1) {
			// Seed
			if (Random.Int(16 - thornsLevel) == 0) {
				Item seed = Generator.random(Generator.Category.SEED);
				level.drop(seed, pos).sprite.drop();
			}


		}

	//	}

		int leaves = 4;

		// Barkskin
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN) {
			//Buff.affect(ch, Barkskin.class).level(ch.HT / 3);
			Buff.affect(ch, Invisibility.class,2f);
			//leaves = 8;
			if (Random.Int(10) == 0 && Statistics.findseed > 25) {
				level.drop(Generator.random(Generator.Category.BERRY), pos).sprite.drop();
				Statistics.findseed = 0;
			}
		}

		CellEmitter.get(pos).burst(LeafParticle.LEVEL_SPECIFIC, leaves);
		Dungeon.observe();
	}
}
