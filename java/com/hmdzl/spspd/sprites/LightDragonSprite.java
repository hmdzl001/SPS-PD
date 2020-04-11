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
import com.hmdzl.spspd.effects.Beam;
import com.watabou.noosa.TextureFilm;

public class LightDragonSprite extends MobSprite {
	private int attackPos;
	//Frames 1-4 are idle, 5-8 are moving, 9-12 are attack and the last are for death RBVG
	
	private int[] points = new int[2];

	public LightDragonSprite() {
		super();

		texture(Assets.PETDRAGON);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 96, 97, 98, 99);

		run = new Animation(8, true);
		run.frames(frames, 100, 101, 102, 103);

		attack = new Animation(8, false);
		attack.frames(frames, 104, 105, 106, 107);

		zap = attack.clone();
		
		die = new Animation(8, false);
		die.frames(frames, 108, 109, 110, 111);

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
                parent.add(new Beam.LightRay(center(), DungeonTilemap
                        .tileCenterToWorld(attackPos)));
            }
        }
    }
	
	@Override
	public int blood() {
		return 0xFFcdcdb7;
	}
}
