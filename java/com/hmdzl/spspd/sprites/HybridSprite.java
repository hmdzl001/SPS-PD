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

public class HybridSprite extends MobSprite {
    private int attackPos;
	
	public HybridSprite() {
		super();

		texture( Assets.HYBRID );

        TextureFilm frames = new TextureFilm(texture, 22, 18);

        idle = new Animation(10, true);
        idle.frames(frames, 0, 1);

        run = new Animation(10, true);
        run.frames(frames, 2, 3);

        attack = new Animation(15, false);
        attack.frames(frames, 4, 5, 6,7);

        die = new Animation(4, false);
        die.frames(frames, 0, 8, 9, 10);

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
                parent.add(new Beam.WaterRay(center(), DungeonTilemap
                        .tileCenterToWorld(attackPos)));
            }
        }
    }
}
