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
package com.hmdzl.spspd.change.actors.blobs;

import com.hmdzl.spspd.change.Assets;
import com.hmdzl.spspd.change.Badges;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.DungeonTilemap;
import com.hmdzl.spspd.change.Journal;
import com.hmdzl.spspd.change.Journal.Feature;
import com.hmdzl.spspd.change.actors.buffs.Awareness;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.effects.BlobEmitter;
import com.hmdzl.spspd.change.effects.Identification;
import com.hmdzl.spspd.change.effects.Speck;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.levels.Terrain;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WaterOfAwareness extends WellWater {

	@Override
	protected boolean affectHero(Hero hero) {

		Sample.INSTANCE.play(Assets.SND_DRINK);
		emitter.parent.add(new Identification(DungeonTilemap
				.tileCenterToWorld(pos)));

		hero.belongings.observe();

		for (int i = 0; i < Level.getLength(); i++) {

			int terr = Dungeon.level.map[i];
			if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

				//Level.set(i, Terrain.discover(terr));
				//GameScene.updateMap(i);
                Dungeon.level.discover( i );
				
				if (Dungeon.visible[i]) {
					GameScene.discoverTile(i, terr);
				}
			}
		}

		Buff.affect(hero, Awareness.class, Awareness.DURATION);
		Dungeon.observe();

		Dungeon.hero.interrupt();

		GLog.p( Messages.get(this, "procced"));

		Journal.remove(Feature.WELL_OF_AWARENESS);

		return true;
	}

	@Override
	protected Item affectItem(Item item) {
		if (item.isIdentified()) {
			return null;
		} else {
			item.identify();
			Badges.validateItemLevelAquired(item);

			emitter.parent.add(new Identification(DungeonTilemap
					.tileCenterToWorld(pos)));

			Journal.remove(Feature.WELL_OF_AWARENESS);

			return item;
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.pour(Speck.factory(Speck.QUESTION), 0.3f);
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
