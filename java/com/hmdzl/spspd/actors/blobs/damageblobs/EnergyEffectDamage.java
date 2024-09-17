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
package com.hmdzl.spspd.actors.blobs.damageblobs;

import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;

import static com.hmdzl.spspd.actors.damagetype.DamageType.ENERGY_DAMAGE;

public class EnergyEffectDamage extends Blob {

	@Override
	protected void evolve() {

		int from = WIDTH + 1;
		int to = Floor.getLength() - WIDTH - 1;


		for (int pos = from; pos < to; pos++) {

			int hit;
			boolean shelf = false;
			
			if (cur[pos] > 0) {

				energyhit(pos);

				hit = cur[pos] - 1;
				
                if (hit <= 0){   }
			} else {
				hit = 0;
			}
 
            volume += (off[pos] = hit);
		}

	}

	private void energyhit(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null && !ch.isImmune(this.getClass()) ) {
			ch.damage( Math.max( 1, Random.Int( ch.HP / 100, ch.HP / 50 ) ), ENERGY_DAMAGE,2 );
		}
	}

	@Override
	public void seed(int cell, int amount) {
		if (cur[cell] == 0) {
			volume += amount;
			cur[cell] = amount;
		/*} else {
			volume += amount - cur[cell];
			cur[cell] = amount;*/
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.pour(Speck.factory(Speck.CONFUSION), 0.6f);
	}
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}

	//public void onDeath() {
		//Dungeon.fail(Messages.format(ResultDescriptions.LOSE));
	//}	
}
