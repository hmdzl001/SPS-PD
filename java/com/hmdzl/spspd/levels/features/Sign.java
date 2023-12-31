/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.hmdzl.spspd.levels.features;

import com.hmdzl.spspd.Assets;
import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.Statistics;
import com.hmdzl.spspd.effects.CellEmitter;
import com.hmdzl.spspd.effects.particles.ElmoParticle;
import com.hmdzl.spspd.levels.ChaosLevel;
import com.hmdzl.spspd.levels.DeadEndLevel;
import com.hmdzl.spspd.levels.Floor;
import com.hmdzl.spspd.levels.NewRoomLevel;
import com.hmdzl.spspd.levels.Terrain;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.GameScene;
import com.hmdzl.spspd.utils.GLog;
import com.hmdzl.spspd.windows.WndMessage;
import com.watabou.noosa.audio.Sample;

public class Sign {

	private static final String TXT_DEAD_END = "What are you doing here?!";

	private static final String[] TIPS = {
			// hmm.. I wonder what this is?
			"standOfF roW", "fraCtion doWnpOur", "gaffe MaSts" };
	
	
	private static final String PIT = "Note to self: Always leave a teleport scroll in the vault.";
	//private static final String BOOKLVL = "Note to self: Always leave a teleport scroll in the vault.";


	private static final String TXT_BURN = "As you try to read the sign it bursts into greenish flames.";

	public static void read(int pos) {

		if (Dungeon.depth instanceof DeadEndLevel) {

			GameScene.show(new WndMessage(Messages.get(Sign.class, "dead_end")));

		} else if (Dungeon.depth instanceof ChaosLevel) {

			GameScene.show(new WndMessage(Messages.get(Sign.class, "chaos")));

		} else if (Dungeon.depth instanceof NewRoomLevel) {

			GameScene.show(new WndMessage(Messages.get(Sign.class, "new_room_"+ Statistics.roomType)));

		} else {

			int index = Dungeon.dungeondepth - 1;

			if (index < TIPS.length) {
				GameScene.show(new WndMessage(Messages.get(Sign.class, "tip_"+Dungeon.dungeondepth)));

				if (index >= 21) {

					Floor.set(pos, Terrain.EMBERS);
					GameScene.updateMap(pos);
					GameScene.discoverTile(pos, Terrain.SIGN);

					GLog.w(Messages.get(Sign.class, "firehit"));

					CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);
					Sample.INSTANCE.play(Assets.SND_BURNING);
				}

			}
		}
	}
	
	public static void readPit(int pos) {
				GameScene.show(new WndMessage(Messages.get(Sign.class,"pit_message")));			
			}
			
	
}
