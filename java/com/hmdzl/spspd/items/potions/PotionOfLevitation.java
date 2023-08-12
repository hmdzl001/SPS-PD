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
package com.hmdzl.spspd.items.potions;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.Actor;
import com.hmdzl.spspd.actors.blobs.Blob;
import com.hmdzl.spspd.actors.blobs.effectblobs.ElectriShock;
import com.hmdzl.spspd.actors.buffs.Buff;
import com.hmdzl.spspd.actors.buffs.DefenceUp;
import com.hmdzl.spspd.actors.buffs.Levitation;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class PotionOfLevitation extends Potion {

	{
		//name = "Potion of Levitation";
		initials = 5;
	}



	@Override
	public void shatter(int cell) {

		if (Dungeon.visible[cell]) {
			setKnown();

			splash(cell);
			Sample.INSTANCE.play(Assets.SND_SHATTER);
		}

			for (int offset : Level.NEIGHBOURS9) {
			if (Actor.findChar(cell + offset) != null
					|| Dungeon.level.heaps.get(cell + offset) != null) {

				GameScene.add(Blob.seed(cell + offset, 5, ElectriShock.class));

			}
		}
	}		
	
	@Override
	public void apply(Hero hero) {
		setKnown();
		Buff.affect(hero, Levitation.class, Levitation.DURATION);
		Buff.affect(hero, DefenceUp.class, 100f).level(20);
		GLog.i(Messages.get(this, "float"));
	}

	@Override
	public int price() {
		return isKnown() ? 35 * quantity : super.price();
	}
}
