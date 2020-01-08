package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.watabou.noosa.Game;
import com.watabou.noosa.TextureFilm;

public class LichDancerSprite extends MobSprite {

	public LichDancerSprite() {
		super();

		texture(Assets.LICH_DANCER);

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
    public void update() {
        super.update();
        if (flashTime <= 0 ){
            float interval = (Game.timeTotal % 9 ) /3f;
            tint(interval > 2 ? interval - 2 : Math.max(0, 1 - interval),
                    interval > 1 ? Math.max(0, 2-interval): interval,
                    interval > 2 ? Math.max(0, 3-interval): interval-1, 0.5f);
        }
    }
}