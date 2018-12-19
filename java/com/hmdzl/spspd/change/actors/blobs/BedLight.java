package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.effects.particles.MemoryParticle;
import com.hmdzl.spspd.change.effects.particles.ShaftParticle;
import com.hmdzl.spspd.change.levels.TownLevel;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.scenes.LoadSaveScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.Journal;
import com.hmdzl.spspd.change.Journal.Feature;
import com.hmdzl.spspd.change.actors.Actor;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.watabou.utils.Bundle;

import java.io.IOException;

public class BedLight extends Blob {

	protected int pos;

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		for (int i = 0; i < LENGTH; i++) {
			if (cur[i] > 0) {
				pos = i;
				break;
			}
		}
	}

	@Override
	protected void evolve() {
		volume = off[pos] = cur[pos];
		Char ch = Actor.findChar(pos);
		BedLight bl = (BedLight) Dungeon.level.blobs.get(BedLight.class);
		if (ch != null && ch == Dungeon.hero) {
			if (Dungeon.visible[pos]) {
				Sample.INSTANCE.play(Assets.SND_LEVELUP);
				bl.seed( bl.pos, 0 );
				try {
					Dungeon.saveAll();
				} catch (IOException e) {
					//
				}
				Dungeon.oneDay=true;
				InterlevelScene.mode = InterlevelScene.Mode.SLEEP;
				Game.switchScene( InterlevelScene.class );
			}
		}
	}

	@Override
	public void seed(int cell, int amount) {
		cur[pos] = 0;
		pos = cell;
		volume = cur[pos] = amount;
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(ShaftParticle.FACTORY, 0.4f);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}