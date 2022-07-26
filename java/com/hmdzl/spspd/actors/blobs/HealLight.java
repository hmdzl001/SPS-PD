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
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.Speck;
import com.hmdzl.spspd.effects.particles.ShaftParticle;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;

public class HealLight extends Blob implements Hero.Doom{
	
	@Override
	protected void evolve() {
		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;
		for (int pos = from; pos < to; pos++) {
			int light;
			if (cur[pos] > 0) {
				light(pos);
				light = cur[pos] - 1;
				if (light <= 0){ }
			} else {
				light = 0;
			}
			volume += (off[pos] = light);

		}

	}

	private void light(int pos) {
		Char ch = Actor.findChar( pos );
		if (ch != null && !ch.isImmune(this.getClass())) {
			if (ch.HP < ch.HT) {
				ch.HP += ch.HT / 25;
				ch.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 4);
			}
		}

		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null) heap.lighthit();
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use(emitter);
		emitter.start(ShaftParticle.FACTORY, 1.0f, 0);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}

    @Override
    public void onDeath() {

    }
}