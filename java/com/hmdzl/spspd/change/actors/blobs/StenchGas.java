package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.messages.Messages;

/**
 * Created by debenhame on 08/10/2014.
 */
public class StenchGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		for (int i = 0; i < LENGTH; i++) {
			if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
				if (!ch.immunities().contains(this.getClass()))
					Buff.affect(ch, Ooze.class);
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(Speck.factory(Speck.STENCH), 0.6f);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
