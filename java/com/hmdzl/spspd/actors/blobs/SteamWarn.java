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

import com.hmdzl.spspd.actors.blobs.effectblobs.Fire;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;

public class SteamWarn extends Blob {

	@Override
	protected void evolve() {

		for (int i = 0; i < LENGTH; i++) {
			int offv = cur[i] > 0 ? cur[i] - 1 : 0;
			off[i] = offv;

			if (offv > 0) {
				volume += offv;
				if (volume <2) {
					GameScene.add(Blob.seed(i, 5, Fire.class));
				}
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour( Speck.factory( Speck.STEAM ), 0.2f );
	}

	@Override
	public void seed(int cell, int amount) {
		int diff = amount - cur[cell];
		if (diff > 0) {
			cur[cell] = amount;
			volume += diff;
		}
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
