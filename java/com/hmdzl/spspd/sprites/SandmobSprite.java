/*
 * Unleashed Pixel Dungeon
 * Copyright (C) 2015  David Mitchell
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
import com.hmdzl.spspd.scenes.GameScene;
import com.watabou.noosa.TextureFilm;

public class SandmobSprite extends MobSprite {

    // private int cellToAttack;

    public SandmobSprite() {
        super();

        texture( Assets.SANDMOB );

        TextureFilm frames = new TextureFilm( texture, 16, 17 );

        idle = new Animation( 8, true );
        idle.frames( frames, 0, 1, 2, 1 );

        run = new Animation( 8, true );
        run.frames( frames, 0, 1, 2, 1 );

        attack = new Animation( 14, false );
        attack.frames( frames, 3, 4, 5, 6, 5, 4, 3 );

        die = new Animation( 6, false );
        die.frames( frames, 7, 8, 9 );

        play( idle );
    }

    /*
    @Override
    public void attack( int cell ) {
        if (!Level.adjacent(cell, ch.pos)) {

            cellToAttack = cell;
            turnTo( ch.pos , cell );
            play( zap );

        } else {

            super.attack( cell );

        }
    }
    */

    @Override
    public void onComplete( Animation anim ) {
        super.onComplete( anim );

        /*
        if (anim == zap) {
            idle();

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset(ch.pos, cellToAttack, new Dart(), new Callback() {
                        @Override
                        public void call() {
                            ch.onAttackComplete();
                        }
                    });
        } else
         */
        if (anim == attack) {
            GameScene.ripple( ch.pos );
        }
    }
}
