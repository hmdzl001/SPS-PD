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
package com.hmdzl.spspd.plants;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Poison;
import com.hmdzl.spspd.actors.buffs.ShadowCurse;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.PoisonParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.potions.PotionOfToxicGas;
import com.hmdzl.spspd.items.weapon.missiles.arrows.ToxicFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Sorrowmoss extends Plant {

	{
		image = 2;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			Buff.affect(ch, Poison.class).set(4 + Dungeon.dungeondepth / 2);
			Buff.affect(ch, ShadowCurse.class);
		}
		
		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null) {
			heap.darkhit();
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.center(pos).burst(PoisonParticle.SPLASH, 3);
		}

	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_SORROWMOSS;

			plantClass = Sorrowmoss.class;
			explantClass = ExSorrowmoss.class;
			alchemyClass = PotionOfToxicGas.class;
		}
	}
	public static class ExSorrowmoss extends Plant {
		{
			image = 2;
		}
		@Override
		public void activate(Char ch) {
			super.activate(ch);

			ArrayList<Integer> candidates = new ArrayList<Integer>();
			for (int i : Floor.NEIGHBOURS8){
				if (Floor.passable[pos+i]){
					candidates.add(pos+i);
				}
			}

			for (int i = 0; i < 3 && !candidates.isEmpty(); i++){
				Integer c = Random.element(candidates);
				Dungeon.depth.drop(new ToxicFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
}
