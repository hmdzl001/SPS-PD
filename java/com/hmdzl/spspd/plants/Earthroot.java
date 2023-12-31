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
import com.hmdzl.spspd.actors.buffs.ShieldArmor;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.EarthParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.potions.PotionOfParalyticGas;
import com.hmdzl.spspd.items.weapon.missiles.arrows.RootFruit;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.hmdzl.spspd.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Earthroot extends Plant {

	{
		image = 5;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			Buff.affect(ch, ShieldArmor.class).level(ch.HT/4);
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.bottom(pos).start(EarthParticle.FACTORY, 0.05f, 8);
			Camera.main.shake(1, 0.4f);
		}
		
		Heap heap = Dungeon.depth.heaps.get(pos);
		if (heap != null) {
			heap.earthhit();
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_EARTHROOT;

			plantClass = Earthroot.class;
			explantClass = ExEarthroot.class;
			alchemyClass = PotionOfParalyticGas.class;

			 
		}
	}
	
	public static class ExEarthroot extends Plant {
		{
			image = 0;
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
				Dungeon.depth.drop(new RootFruit(), c).sprite.drop(pos);
				candidates.remove(c);
			}
		}
	}	

	public static class MagicPlantArmor extends Buff {

		private static final float STEP = 1f;

		private int pos;
		private int level;
		
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			return super.attachTo(target);
		}

		@Override
		public boolean act() {
			if (target.pos != pos) {
				detach();
			}
			spend(STEP);
			return true;
		}

		public int absorb(int damage) {
			if (level <= damage - damage / 2) {
				detach();
				return damage - level;
			} else {
				level -= damage - damage / 2;
				return damage / 2;
			}
		}

		public void level(int value) {
			if (level < value) {
				level = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public String toString() {
			return Messages.get(this, "name");
		}
		
		@Override
		public String desc() {
			return Messages.get(this, "desc", level);
		}
		

		private static final String POS = "pos";
		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(POS, pos);
			bundle.put(LEVEL, level);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			pos = bundle.getInt(POS);
			level = bundle.getInt(LEVEL);
		}
	}
}
