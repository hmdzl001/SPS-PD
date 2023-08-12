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
package com.hmdzl.spspd.items;

import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.TorchLight;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.HighLight;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.effects.particles.FlameParticle;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.sprites.ItemSpriteSheet;
import com.watabou.noosa.particles.Emitter;

import java.util.ArrayList;

public class Torch extends Item {

	public static final String AC_LIGHT = "LIGHT";
	public static final String AC_SET = "SET";

	public static final float TIME_TO_LIGHT = 1;

	{
		//name = "torch";
		image = ItemSpriteSheet.TORCH;

		stackable = true;

		defaultAction = AC_SET;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_LIGHT);
		actions.add(AC_SET);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_LIGHT)) {

			hero.spend(TIME_TO_LIGHT);
			hero.busy();

			hero.sprite.operate(hero.pos);

			detach(hero.belongings.backpack);
			Buff.affect(hero, HighLight.class, 300);

			Emitter emitter = hero.sprite.centerEmitter();
			emitter.start(FlameParticle.FACTORY, 0.2f, 3);

		} else if (action.equals(AC_SET)) {

			hero.spend(TIME_TO_LIGHT);
			hero.busy();
			hero.sprite.operate(hero.pos);
			detach(hero.belongings.backpack);
			GameScene.add(Blob.seed(hero.pos, 1, TorchLight.class));
			Emitter emitter = hero.sprite.centerEmitter();
			emitter.start(FlameParticle.FACTORY, 0.2f, 3);

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 10 * quantity;
	}
}
