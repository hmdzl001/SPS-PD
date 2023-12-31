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
import com.hmdzl.spspd.actors.buffs.Blindness;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cripple;
import com.hmdzl.spspd.actors.buffs.LightShootAttack;
import com.hmdzl.spspd.actors.mobs.Mob;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.potions.PotionOfInvisibility;
import com.hmdzl.spspd.items.weapon.missiles.arrows.BlindFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Blindweed extends Plant {

	{
		image = 3;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			int len = Random.Int(5, 10);
			Buff.prolong(ch, Blindness.class, len);
			Buff.prolong(ch, Cripple.class, len);
			Buff.affect(ch, LightShootAttack.class).level(len);
			if (ch instanceof Mob) {
				if (((Mob)ch).state == ((Mob)ch).HUNTING) ((Mob)ch).state = ((Mob)ch).WANDERING;
				((Mob)ch).beckon( Dungeon.depth.randomDestination() );
			}
		}
		
		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null) {
			heap.lighthit();
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_BLINDWEED;

			plantClass = Blindweed.class;
			explantClass = ExBlindweed.class;
			alchemyClass = PotionOfInvisibility.class;
		}
	}
	public static class ExBlindweed extends Plant {
		{
			image = 3;
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
				Dungeon.depth.drop(new BlindFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	
	
}
