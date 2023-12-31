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
package com.hmdzl.spspd.items.scrolls;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Water;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Dewcharge;
import com.hmdzl.spspd.actors.buffs.GrowSeed;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.plants.Earthroot;
import com.hmdzl.spspd.plants.Plant;
import com.hmdzl.spspd.plants.Starflower;
import com.hmdzl.spspd.plants.Sungrass;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfRegrowth extends Scroll {

	{
		//name = "Scroll of Regrowth";
		consumedValue = 15;
		initials = 9;
	}

	@Override
	public void doRead() {

		ArrayList<Integer> plantCandidates = new ArrayList<>();
		
		PathFinder.buildDistanceMap( Dungeon.hero.pos, BArray.not(Floor.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Char ch = Actor.findChar(i);
                  if ( Dungeon.depth.map[i] == Terrain.EMPTY ||
							Dungeon.depth.map[i] == Terrain.EMBERS ||
							Dungeon.depth.map[i] == Terrain.EMPTY_DECO ||
							Dungeon.depth.map[i] == Terrain.GRASS ||
						    Dungeon.depth.map[i] == Terrain.OLD_HIGH_GRASS ||
							Dungeon.depth.map[i] == Terrain.HIGH_GRASS){
					
					plantCandidates.add(i);
				}
				GameScene.add( Blob.seed( i, 10, Water.class ) );
			}
		}

		int plants = Random.chances(new float[]{0, 6, 3, 1});

		for (int i = 0; i < plants; i++) {
			Integer plantPos = Random.element(plantCandidates);
			if (plantPos != null) {
				Dungeon.depth.plant((Plant.Seed) Generator.random(Generator.Category.SEED), plantPos);
				plantCandidates.remove(plantPos);
			}
		}
		
		Integer plantPos = Random.element(plantCandidates);
		if (plantPos != null){
			Plant.Seed plant;
			switch (Random.chances(new float[]{0, 3, 2, 1})){
				case 1: default:
					plant = new Sungrass.Seed();
					break;
				case 2:
					plant = new Earthroot.Seed();
					break;
				case 3:
					plant = new Starflower.Seed();
					break;
			}
			Dungeon.depth.plant( plant, plantPos);
		}

		for (Mob mob : Dungeon.depth.mobs.toArray( new Mob[0] )) {
			if (Floor.fieldOfView[mob.pos]) {
				Buff.affect(mob,GrowSeed.class).set(6f);
			}
		}

	
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		setKnown();

		readAnimation();
	}
	
	@Override
	public void empoweredRead() {
        doRead();
        Buff.affect(curUser, Dewcharge.class,50f);
	}	

	@Override
	public int price() {
		return isKnown() ? 20 * quantity : super.price();
	}

}
