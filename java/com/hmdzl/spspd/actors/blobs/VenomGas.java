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
package com.hmdzl.spspd.actors.blobs;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Venom;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Bundle;

public class VenomGas extends Blob {

	private int strength = 0;

	@Override
	protected void evolve() {
		super.evolve();

		if (volume == 0){
			strength = 0;
		} else {
			Char ch;
			for (int i = 0; i < LENGTH; i++) {
				if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
					if (!ch.isImmune(this.getClass()))
						Buff.affect(ch, Venom.class).set(2f, strength);
				}
			}
		}
	}

	public void setStrength(int str){
		if (str > strength)
			strength = str;
	}

	private static final String STRENGTH = "strength";

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		strength = bundle.getInt( STRENGTH );
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( STRENGTH, strength );
	}

	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );

		emitter.pour( Speck.factory(Speck.VENOM), 0.6f );
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
