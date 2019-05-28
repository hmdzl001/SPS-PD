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
package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.mobs.Warlock;
import com.hmdzl.spspd.change.actors.mobs.pets.BugDragon;
import com.hmdzl.spspd.change.actors.mobs.pets.GoldDragon;
import com.hmdzl.spspd.change.effects.Beam;
import com.hmdzl.spspd.change.effects.Lightning;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class BugDragonSprite extends MobSprite {
	
	//Frames 1-4 are idle, 5-8 are moving, 9-12 are attack and the last are for death RBVG
	
	private int[] points = new int[2];

	public BugDragonSprite() {
		super();

		texture(Assets.PETDRAGON);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 112, 113, 114, 115);

		run = new Animation(8, true);
		run.frames(frames, 116, 117, 118, 119);

		attack = new Animation(8, false);
		attack.frames(frames, 120, 121, 122, 123);

		zap = attack.clone();
		
		die = new Animation(8, false);
		die.frames(frames, 124, 125, 126, 127);

		play(idle);
	}
	@Override
	public void zap(int cell) {

		turnTo(ch.pos, cell);
		play(zap);

		MagicMissile.force(parent, ch.pos, cell, new Callback() {
			@Override
			public void call() {
				((BugDragon) ch).onZapComplete();
			}
		});
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}
}