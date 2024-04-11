/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.hmdzl.spspd.levels.traps.damagetrap;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.damageblobs.LightEffectDamage;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.traps.Trap;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.TrapSprite;
import com.hmdzl.spspd.utils.BArray;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class LightDamage2Trap extends Trap {

	{
		color = TrapSprite.WHITE;
		shape = TrapSprite.CROSSHAIR;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		PathFinder.buildDistanceMap( pos, BArray.not( Floor.solid, null ), 2 );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				if (Floor.insideMap(i) && !Floor.solid[i]) {
					GameScene.add(Blob.seed(i, 20, LightEffectDamage.class));
				}
			}
		}
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}
}