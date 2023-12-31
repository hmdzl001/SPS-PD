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
package com.hmdzl.spspd.actors.blobs;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.Char;
import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.actors.buffs.ArmorBreak;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.SpeedSlow;
import com.hmdzl.spspd.actors.buffs.StandDown;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;
import com.watabou.utils.Random;


public class SwampGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();
		
		Char ch;
		for (int i = 0; i < LENGTH; i++) {
			if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
				if (!ch.isImmune(this.getClass())){
					Buff.affect(ch,ArmorBreak.class,3f).level(20);
					if (ch.buff(StandDown.class) != null){
						Buff.affect(ch, StandDown.class, 2f);
					} else {
						Buff.affect(ch, SpeedSlow.class, 3f);
						SpeedSlow speedslow = ch.buff(SpeedSlow.class);
						if (speedslow != null && speedslow.cooldown() >= 10f){
							Buff.affect(ch, StandDown.class, 5f);
						}
					}
                }	
			}				
        }
		
        Blob blob = Dungeon.depth.blobs.get( Fire.class );
        if (blob != null) {

            for (int pos=0; pos < LENGTH; pos++) {

                if ( cur[pos] > 0 && blob.cur[ pos ] < 2 ) {

                    int flammability = 0;

                    for (int n : Floor.NEIGHBOURS8) {
                        if ( blob.cur[ pos + n ] > 0 ) {
                            flammability++;
                        }
                    }
                    if( Random.Int( 4 ) < flammability ) {
                        blob.volume += ( blob.cur[ pos ] = 2 );
                        volume -= ( cur[pos] / 2 );
                        cur[pos] -=( cur[pos] / 2 );
                    }
                }
            }
        }			
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(Speck.factory(Speck.TARGAS,true ), 0.6f);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}