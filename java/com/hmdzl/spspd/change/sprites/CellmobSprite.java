package com.hmdzl.spspd.change.sprites;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.effects.DeathRay;
import com.watabou.noosa.TextureFilm;

public class CellmobSprite extends MobSprite {

	private int attackPos;

	public CellmobSprite() {
		super();

		texture(Assets.CELL_MOB);

		TextureFilm frames = new TextureFilm(texture, 16, 18);

		idle = new Animation(2, true);
		idle.frames(frames, 1, 2, 3, 4);

		run = new Animation(12, true);
		run.frames(frames, 2, 3, 4);

		attack = new Animation(8, false);
		attack.frames(frames, 1, 5);

		die = new Animation(8, false);
		die.frames(frames, 1, 0, 6);

		play(idle);
	}

	@Override
	public void attack(int pos) {
		attackPos = pos;
		super.attack(pos);
	}
	

	@Override
	public void onComplete(Animation anim) {
		super.onComplete(anim);

		if (anim == attack) {
			if (Dungeon.visible[ch.pos] || Dungeon.visible[attackPos]) {
				parent.add(new DeathRay(center(), DungeonTilemap
						.tileCenterToWorld(attackPos)));
			}
		}
	}
}