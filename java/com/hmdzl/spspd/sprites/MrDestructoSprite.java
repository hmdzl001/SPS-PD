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
package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.DungeonTilemap;
import com.hmdzl.spspd.effects.Beam.DeathRay;
import com.watabou.noosa.TextureFilm;

public class MrDestructoSprite extends MobSprite {

	private int attackPos;

	public MrDestructoSprite() {
		super();

		texture(Assets.MRDESTRUCTO);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 1, 2, 3, 4);

		run = new Animation(12, true);
		run.frames(frames, 2, 3, 4);

		attack = new Animation(15, false);
		attack.frames(frames, 1, 5);

		die = new Animation(8, false);
		die.frames(frames, 1, 0, 6);

		play(idle);
	}

	@Override
	public void attack(int pos) {
		attackPos = pos;
		super.attack(pos);
	}
	

	@Override
	public void onComplete(Animation anim) {
		super.onComplete(anim);

		if (anim == attack) {
			if (Dungeon.visible[ch.pos] || Dungeon.visible[attackPos]) {
				parent.add(new DeathRay(center(), DungeonTilemap
						.tileCenterToWorld(attackPos)));
			}
		}
	}
}
