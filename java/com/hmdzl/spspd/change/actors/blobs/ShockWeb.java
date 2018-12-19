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
package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Roots;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.particles.ShockWebParticle;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ResultDescriptions;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Random;

public class ShockWeb extends Blob {

	@Override
	protected void evolve() {

		for (int i = 0; i < LENGTH; i++) {

			int offv = cur[i] > 0 ? cur[i] - 1 : 0;
			off[i] = offv;

			if (offv > 0) {

				volume += offv;

				Char ch = Actor.findChar(i);
				if (ch != null) {
					int damage = 5;
				    ch.damage(damage, this);
					Buff.prolong(ch, Roots.class, TICK);
				}
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(ShockWebParticle.FACTORY, 0.4f);
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
