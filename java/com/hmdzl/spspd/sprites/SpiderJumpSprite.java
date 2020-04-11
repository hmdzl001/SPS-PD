package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.watabou.noosa.TextureFilm;

public class SpiderJumpSprite extends MobSprite {

	public SpiderJumpSprite() {
		super();

		texture(Assets.SPIDERWORKER);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(10, true);
		idle.frames(frames, 16, 16, 16, 16, 17, 18, 16, 17);

		run = new Animation(15, true);
		run.frames(frames, 18, 19, 20, 21);

		attack = new Animation(12, false);
		attack.frames(frames, 22, 23, 24, 25);

		die = new Animation(12, false);
		die.frames(frames, 26, 27, 27, 27);

		play(idle);
	}

	@Override
	public int blood() {
		return 0xFFBFE5B8;
	}
}