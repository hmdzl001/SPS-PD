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
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.Water;
import com.hmdzl.spspd.actors.buffs.Invisibility;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.audio.Sample;

public class ScrollOfRegrowth extends Scroll {

	{
		//name = "Scroll of Regrowth";
		consumedValue = 15;
		initials = 9;
	}

	@Override
	public void doRead() {
		int length = Level.getLength();

		for (int i = 0; i < length; i++) {
			
			GameScene.add(Blob.seed(i, (2) * 20, Water.class));

		}

		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		setKnown();

		readAnimation();
	}
	
	@Override
	public void empoweredRead() {
		//does nothing for now, this should never happen.
	}	

	@Override
	public int price() {
		return isKnown() ? 100 * quantity : super.price();
	}

}
