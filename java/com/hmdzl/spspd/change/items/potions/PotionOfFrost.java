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
package com.hmdzl.spspd.change.items.potions;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.blobs.Fire;
import com.hmdzl.spspd.change.actors.blobs.Freezing;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfFrost extends Potion {

	private static final int DISTANCE = 2;

	{
		//name = "Potion of Frost";
		initials = 1;
	}

	@Override
	public void shatter(int cell) {

		PathFinder.buildDistanceMap(cell, BArray.not(Level.losBlockLow, null),
				DISTANCE);

		Fire fire = (Fire) Dungeon.level.blobs.get(Fire.class);

		boolean visible = false;
		for (int i = 0; i < Level.getLength(); i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				visible = Freezing.affect(i, fire) || visible;
			}
		}

		if (visible) {
			splash(cell);
			Sample.INSTANCE.play(Assets.SND_SHATTER);

			setKnown();
		}
	}

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
