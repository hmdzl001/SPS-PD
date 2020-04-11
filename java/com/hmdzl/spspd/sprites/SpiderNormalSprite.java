package com.hmdzl.spspd.sprites;

import com.hmdzl.spspd.Assets;
import com.watabou.noosa.TextureFilm;

public class SpiderNormalSprite extends MobSprite {

	public SpiderNormalSprite() {
		super();

		texture(Assets.SPIDERWORKER);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(10, true);
		idle.frames(frames, 0, 0, 0, 0, 1, 2, 0, 1);

		run = new Animation(15, true);
		run.frames(frames, 2, 3, 4, 5);

		attack = new Animation(12, false);
		attack.frames(frames, 6, 7, 8, 9);

		die = new Animation(12, false);
		die.frames(frames, 10, 11, 12, 13);

		play(idle);
	}

	@Override
	public int blood() {
		return 0xFFBFE5B8;
	}
}