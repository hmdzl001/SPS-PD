package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.watabou.noosa.TextureFilm;

public class SpiderEggSprite extends MobSprite {

	public SpiderEggSprite() {
		super();

		texture(Assets.SPIDEREGG);

		TextureFilm frames = new TextureFilm(texture, 16, 16);

		idle = new Animation(10, true);
		idle.frames(frames, 0, 0, 0, 0, 0, 1, 0, 1);

		run = new Animation(15, true);
		run.frames(frames, 0, 1, 0, 1);

		attack = new Animation(12, false);
		attack.frames(frames, 0, 1, 0, 0);

		die = new Animation(12, false);
		die.frames(frames, 1, 2, 3, 4);

		play(idle);
	}

	@Override
	public int blood() {
		return 0xFFBFE5B8;
	}
}