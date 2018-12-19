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
package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.mobs.SewerHeart;
import com.hmdzl.spspd.change.effects.Beam;
import com.hmdzl.spspd.change.effects.MagicMissile;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.effects.particles.EnergyParticle;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;

public class SewerHeartSprite extends MobSprite {

	private int zapPos;

	private Emitter cloud;
	private Animation charging;
	private Emitter chargeParticles;

	public SewerHeartSprite(){
		super();

		texture( Assets.SEWER_HEART );

		TextureFilm frames = new TextureFilm( texture, 16, 16 );

		idle = new MovieClip.Animation( 1, true );
		idle.frames( frames, 0);

		run = new MovieClip.Animation( 1, true );
		run.frames( frames, 0 );

		attack = new MovieClip.Animation( 10, false );
		attack.frames( frames, 0,1 );
		zap = attack.clone();

		chargeParticles = centerEmitter();
		chargeParticles.autoKill = false;
		chargeParticles.pour( EnergyParticle.FACTORY, 0.05f);
		chargeParticles.on = false;

		die = new MovieClip.Animation( 8, false );
		die.frames( frames, 1, 2, 3, 4, 5, 6, 7, 7, 7 );

		play( idle );
	}

	@Override
	public void link( Char ch ) {
		super.link( ch );
		if (((SewerHeart)ch).beamCharged) play(charging);
		if (cloud == null) {
			cloud = emitter();
			cloud.pour( Speck.factory(Speck.TOXIC), 0.7f );
		}
	}

	@Override
	public void turnTo(int from, int to) {
		//do nothing
	}

	@Override
	public void update() {

		super.update();
		chargeParticles.pos(center());
		chargeParticles.visible = visible;
		if (cloud != null) {
			cloud.visible = visible;
		}
	}

	@Override
	public void die() {
		super.die();

		if (cloud != null) {
			cloud.on = false;
		}
	}

	public void charge( int pos ){
		turnTo(ch.pos, pos);
		play(charging);
	}

	@Override
	public void play(Animation anim) {
		chargeParticles.on = anim == charging;
		super.play(anim);
	}

	@Override
	public void zap( int pos ) {
		zapPos = pos;
		super.zap( pos );
	}

	@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );

		if (anim == zap) {
			idle();
			if (Actor.findChar(zapPos) != null){
				parent.add(new Beam.LightRay(center(), Actor.findChar(zapPos).sprite.center()));
			} else {
				parent.add(new Beam.LightRay(center(), DungeonTilemap.raisedTileCenterToWorld(zapPos)));
			}
			((SewerHeart)ch).deathGaze();
			ch.next();
		} else if (anim == die){
			chargeParticles.killAndErase();
		}
	}
}
