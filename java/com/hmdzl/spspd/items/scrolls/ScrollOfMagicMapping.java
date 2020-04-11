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
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.SpellSprite;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfMagicMapping extends Scroll {

	{
		//name = "Scroll of Magic Mapping";
		consumedValue = 10;
		initials = 4;
	}

	@Override
	public void doRead() {

		int length = Level.LENGTH;
		//int[] map = Dungeon.level.map;
		boolean[] mapped = Dungeon.level.mapped;
		boolean[] discoverable = Level.discoverable;

		//boolean noticed = false;

		for (int i = 0; i < length; i++) {

			//int terr = map[i];

			if (discoverable[i]) {

				mapped[i] = true;
				//if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

					//Level.set(i, Terrain.discover(terr));
					//GameScene.updateMap(i);
					//Dungeon.level.discover( i );

					//if (Dungeon.visible[i]) {
						//GameScene.discoverTile(i, terr);
						//discover(i);

						//noticed = true;
					//}
				//}
			}
		}
		Dungeon.observe();

		GLog.i(Messages.get(this, "layout"));
		//if (noticed) {
			//Sample.INSTANCE.play(Assets.SND_SECRET);
		//}

		//SpellSprite.show(curUser, SpellSprite.MAP);
		//Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		setKnown();

        readAnimation();
	}
	
	@Override
	public void empoweredRead() {
		doRead();
		int length = Level.LENGTH;
		int[] map = Dungeon.level.map;
		boolean[] mapped = Dungeon.level.mapped;
		boolean[] discoverable = Level.discoverable;

		boolean noticed = false;

		for (int i = 0; i < length; i++) {

			int terr = map[i];

			if (discoverable[i]) {

				mapped[i] = true;
				if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

					//Level.set(i, Terrain.discover(terr));
					//GameScene.updateMap(i);
					Dungeon.level.discover( i );

					if (Dungeon.visible[i]) {
						GameScene.discoverTile(i, terr);
						discover(i);

						noticed = true;
					}
				}
			}
		}
		Dungeon.observe();

		GLog.i(Messages.get(this, "layout"));
		if (noticed) {
			Sample.INSTANCE.play(Assets.SND_SECRET);
		}

		SpellSprite.show(curUser, SpellSprite.MAP);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		setKnown();

		curUser.spendAndNext(TIME_TO_READ);
		Dungeon.observe();
	}	

	@Override
	public int price() {
		return isKnown() ? 50 * quantity : super.price();
	}

	public static void discover(int cell) {
		CellEmitter.get(cell).start(Speck.factory(Speck.DISCOVER), 0.1f, 4);
	}
}
