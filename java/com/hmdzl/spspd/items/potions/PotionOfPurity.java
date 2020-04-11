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
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.ConfusionGas;
import com.hmdzl.spspd.actors.blobs.ParalyticGas;
import com.hmdzl.spspd.actors.blobs.StenchGas;
import com.hmdzl.spspd.actors.blobs.ToxicGas;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.GasesImmunity;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.utils.BArray;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfPurity extends Potion {

	private static final int DISTANCE = 5;

	{
		//name = "Potion of Purification";
		initials = 12;
	}

	@Override
	public void shatter(int cell) {

		PathFinder.buildDistanceMap(cell, BArray.not(Level.losBlockLow, null),
				DISTANCE);

		boolean procd = false;

		Blob[] blobs = { Dungeon.level.blobs.get(ToxicGas.class),
				Dungeon.level.blobs.get(ParalyticGas.class),
				Dungeon.level.blobs.get(ConfusionGas.class),
				Dungeon.level.blobs.get(StenchGas.class) };

		for (int j = 0; j < blobs.length; j++) {

			Blob blob = blobs[j];
			if (blob == null) {
				continue;
			}

			for (int i = 0; i < Level.getLength(); i++) {
				if (PathFinder.distance[i] < Integer.MAX_VALUE) {

					int value = blob.cur[i];
					if (value > 0) {

						blob.cur[i] = 0;
						blob.volume -= value;
						procd = true;

						if (Dungeon.visible[i]) {
							CellEmitter.get(i).burst(
									Speck.factory(Speck.DISCOVER), 1);
						}
					}

				}
			}
		}

		boolean heroAffected = PathFinder.distance[Dungeon.hero.pos] < Integer.MAX_VALUE;

		if (procd) {

			if (Dungeon.visible[cell]) {
				splash(cell);
				Sample.INSTANCE.play(Assets.SND_SHATTER);
			}

			setKnown();

			if (heroAffected) {
				GLog.p(Messages.get(this, "freshness"));
			}

		} else {

			super.shatter(cell);

			if (heroAffected) {
				GLog.i(Messages.get(this, "freshness") );
				setKnown();
			}

		}
	}

	@Override
	public void apply(Hero hero) {
		GLog.w(Messages.get(this, "no_smell"));
		Buff.prolong(hero, GasesImmunity.class, GasesImmunity.DURATION);
		Buff.prolong(hero, HighLight.class, 10f);
		setKnown();
	}

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}
}
