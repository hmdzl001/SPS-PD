/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.items.weapon.missiles;


import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.mobs.pets.PET;
import com.hmdzl.spspd.items.PocketBallFull;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.effects.particles.ShadowParticle;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;


public class PocketBall extends MissileWeapon {

	{
		//name = "pocket ball";
		image = ItemSpriteSheet.POCKETBALL_EMPTY;

		STR = 10;
		MIN = 1;
		MAX = 10;

		stackable = true;
	}

	public PocketBall() {
		this( 1 );
	}

	public PocketBall(int number) {
		super();
		quantity = number;
	}


    @Override
    protected void onThrow( int cell ) {
        if(Actor.findChar(cell) != null && Actor.findChar(cell) instanceof PET) {
            Actor.findChar(cell).sprite.emitter().burst(ShadowParticle.CURSE, 6);
            Sample.INSTANCE.play( Assets.SND_CURSED );
            PocketBallFull pbf = new PocketBallFull(
            	Dungeon.hero.petType,
				Dungeon.hero.petHP,
				Dungeon.hero.petLevel,
				Dungeon.hero.petExperience

			);
            Dungeon.level.drop( pbf, cell ).sprite.drop();
			((PET) Actor.findChar(cell)).sprite.killAndErase();
			((PET) Actor.findChar(cell)).destroy();
			Dungeon.hero.haspet=false;
			GLog.n(Messages.get(this, "get_pet"));
        } else
		miss(cell);
    }

	@Override
	public int price() {
		return 100 * quantity;
	}
}