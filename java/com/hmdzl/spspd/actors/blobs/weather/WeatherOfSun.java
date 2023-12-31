package com.hmdzl.spspd.actors.blobs.weather;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.Cold;
import com.hmdzl.spspd.actors.buffs.Hot;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.BlobEmitter;
import com.hmdzl.spspd.effects.particles.ShaftParticle;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.messages.Messages;

public class WeatherOfSun extends Blob {

    protected int pos;

	@Override
	protected void evolve() {
		int from = WIDTH + 1;
		int to = Floor.LENGTH - WIDTH - 1;
		
		int[] map = Dungeon.depth.map;
		
		for (int pos=from; pos < to; pos++) {
			if (cur[pos] > 0) {
				
				off[pos] = cur[pos];
				volume += off[pos];
				
			} else {
				off[pos] = 0;
			}
		}

		Hero hero = Dungeon.hero;
		if (hero.isAlive() && cur[hero.pos] > 0) {
			Buff.prolong( hero, Hot.class, Hot.DURATION );
			Buff.detach(hero,Cold.class);
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(ShaftParticle.FACTORY, 0.9f, 0);
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
	
}