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
package com.hmdzl.spspd.change;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.Context;

import com.hmdzl.spspd.change.actors.blobs.ToxicGas;
import com.hmdzl.spspd.change.actors.buffs.Bleeding;
import com.hmdzl.spspd.change.actors.buffs.Burning;
import com.hmdzl.spspd.change.actors.buffs.Hunger;
import com.hmdzl.spspd.change.actors.buffs.Ooze;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.hmdzl.spspd.change.actors.mobs.DM300;
import com.hmdzl.spspd.change.actors.mobs.Goo;
import com.hmdzl.spspd.change.actors.mobs.King;
import com.hmdzl.spspd.change.actors.mobs.Tengu;
import com.hmdzl.spspd.change.actors.mobs.Yog;
import com.hmdzl.spspd.change.items.Amulet;
import com.hmdzl.spspd.change.levels.features.Chasm;
import com.hmdzl.spspd.change.messages.Languages;
import com.hmdzl.spspd.change.messages.Messages;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.SystemTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import com.hmdzl.spspd.change.actors.hero.HeroClass;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.SystemTime;

public enum Rankings {

	INSTANCE;

	public static final int TABLE_SIZE = 11;

	public static final String RANKINGS_FILE = "rankings.dat";
	public static final String DETAILS_FILE = "game_%d.dat";

	public ArrayList<Record> records;
	public int lastRecord;
	public int totalNumber;
	public int wonNumber;

	public void submit(boolean win) {

		load();

		Record rec = new Record();

		rec.info = Dungeon.resultDescription;
		rec.win = win;
		rec.heroClass = Dungeon.hero.heroClass;
		rec.skin = 7-Dungeon.skins;
		rec.herolevel = Dungeon.hero.lvl;
		rec.depth = Dungeon.depth;
		rec.score = score(win);

		String gameFile = Messages.format( DETAILS_FILE, SystemTime.now );
		try {
			Dungeon.saveGame(gameFile);
			rec.gameFile = gameFile;
		} catch (IOException e) {
			rec.gameFile = "";
		}

		records.add(rec);

		Collections.sort(records, scoreComparator);

		lastRecord = records.indexOf(rec);
		int size = records.size();
		while (size > TABLE_SIZE) {

			Record removedGame;
			if (lastRecord == size - 1) {
				removedGame = records.remove(size - 2);
				lastRecord--;
			} else {
				removedGame = records.remove(size - 1);
			}

			if (removedGame.gameFile.length() > 0) {
				Game.instance.deleteFile(removedGame.gameFile);
			}

			size = records.size();
		}

		totalNumber++;
		if (win) {
			wonNumber++;
		}

		Badges.validateGamesPlayed();

		save();
	}

	private int score(boolean win) {
		return (Statistics.goldCollected + Dungeon.hero.lvl
				* (win ? 26 : Dungeon.depth) * 100)
				* (win ? 2 : 1);
	}

	private static final String RECORDS = "records";
	private static final String LATEST = "latest";
	private static final String TOTAL = "total";
	private static final String WON = "won";

	public void save() {
		Bundle bundle = new Bundle();
		bundle.put(RECORDS, records);
		bundle.put(LATEST, lastRecord);
		bundle.put(TOTAL, totalNumber);
		bundle.put(WON, wonNumber);

		try {
			OutputStream output = Game.instance.openFileOutput(RANKINGS_FILE,
					Game.MODE_PRIVATE);
			Bundle.write(bundle, output);
			output.close();
		} catch (IOException e) {
		}
	}

	public void load() {

		if (records != null) {
			return;
		}

		records = new ArrayList<Rankings.Record>();

		try {
			InputStream input = Game.instance.openFileInput(RANKINGS_FILE);
			Bundle bundle = Bundle.read(input);
			input.close();

			for (Bundlable record : bundle.getCollection(RECORDS)) {
				records.add((Record) record);
			}
			lastRecord = bundle.getInt(LATEST);

			totalNumber = bundle.getInt(TOTAL);
			if (totalNumber == 0) {
				totalNumber = records.size();
			}

			wonNumber = bundle.getInt(WON);
			if (wonNumber == 0) {
				for (Record rec : records) {
					if (rec.win) {
						wonNumber++;
					}
				}
			}

		} catch (IOException e) {

		}
	}

	public static class Record implements Bundlable {

		private static final String REASON = "reason";
		private static final String WIN = "win";
		private static final String SCORE = "score";
		private static final String SKIN = "skin";
		private static final String LEVEL = "level";
		private static final String DEPTH = "depth";
		private static final String GAME = "gameFile";

		public String info;
		public boolean win;

		public HeroClass heroClass;
		public int skin;
		public int herolevel;
		public int depth;

		public int score;

		public String gameFile;

		@Override
		public void restoreFromBundle(Bundle bundle) {

			info = bundle.getString(REASON);
			win = bundle.getBoolean(WIN);
			score = bundle.getInt(SCORE);
			heroClass = HeroClass.restoreInBundle(bundle);
			skin = bundle.getInt(SKIN);
         	gameFile = bundle.getString(GAME);
			depth = bundle.getInt(DEPTH);
			herolevel = bundle.getInt(LEVEL);

		}

		@Override
		public void storeInBundle(Bundle bundle) {

			bundle.put(REASON, info);
			bundle.put(WIN, win);
			bundle.put(SCORE, score);

			heroClass.storeInBundle(bundle);
			bundle.put(SKIN, skin);
			bundle.put(LEVEL, herolevel);
			bundle.put(DEPTH, depth);

			bundle.put(GAME, gameFile);
		}
	}

	private static final Comparator<Record> scoreComparator = new Comparator<Rankings.Record>() {
		@Override
		public int compare(Record lhs, Record rhs) {
			return (int) Math.signum(rhs.score - lhs.score);
		}
	};
}
