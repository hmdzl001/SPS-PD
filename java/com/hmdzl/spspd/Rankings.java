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
package com.hmdzl.spspd;

import com.hmdzl.spspd.actors.hero.Belongings;
import com.hmdzl.spspd.actors.hero.Hero;
import com.hmdzl.spspd.actors.hero.HeroClass;
import com.hmdzl.spspd.items.Generator;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.bags.Bag;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

public enum Rankings {

	INSTANCE;

	public static final int TABLE_SIZE = 11;

	public static final String RANKINGS_FILE = "rankings.dat";

	public ArrayList<Record> records;
	public int lastRecord;
	public int totalNumber;
	public int wonNumber;

	public void submit(boolean win){

		load();

		Record rec = new Record();

		rec.info = Dungeon.resultDescription;
		rec.win = win;
		rec.heroClass = Dungeon.hero.heroClass;
		rec.skin = Hero.skins;
		rec.herolevel = Dungeon.hero.lvl;
		rec.depth = Dungeon.dungeondepth;
		rec.score = score(win);

		INSTANCE.saveGameData(rec);
		
		rec.gameID = UUID.randomUUID().toString();

        records.add(rec);

		Collections.sort(records, scoreComparator);

		lastRecord = records.indexOf(rec);
		int size = records.size();
		
		while (size > TABLE_SIZE) {

			if (lastRecord == size - 1) {
				records.remove( size - 2 );
				lastRecord--;
			} else {
				records.remove( size - 1 );
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
				* (win ? 100 : Dungeon.dungeondepth) * 100)
				* (win ? 2 : 1);
	}

	
	public static final String HERO = "hero";
	public static final String STATS = "stats";
	public static final String BADGES = "badges";
	public static final String HANDLERS = "handlers";
	public static final String CHALLENGES = "challenges";
	
	public void saveGameData(Record rec){
		rec.gameData = new Bundle();

		Belongings belongings = Dungeon.hero.belongings;

		//save the hero and belongings
		ArrayList<Item> allItems = (ArrayList<Item>) belongings.backpack.items.clone();
		//remove items that won't show up in the rankings screen
		for (Item item : belongings.backpack.items.toArray( new Item[0])) {
			if (item instanceof Bag){
				for (Item bagItem : ((Bag) item).items.toArray( new Item[0])){
					if (Dungeon.quickslot.contains(bagItem)) belongings.backpack.items.add(bagItem);
				}
				belongings.backpack.items.remove(item);
			} else if (!Dungeon.quickslot.contains(item))
				belongings.backpack.items.remove(item);
		}
		rec.gameData.put( HERO, Dungeon.hero );

		//save stats
		Bundle stats = new Bundle();
		Statistics.storeInBundle(stats);
		rec.gameData.put( STATS, stats);

		//save badges
		Bundle badges = new Bundle();
		Badges.saveLocal(badges);
		rec.gameData.put( BADGES, badges);

		//save handler information
		Bundle handler = new Bundle();

		//include worn rings
		if (belongings.misc1 != null) belongings.backpack.items.add(belongings.misc1);
		if (belongings.misc2 != null) belongings.backpack.items.add(belongings.misc2);
		if (belongings.misc3 != null) belongings.backpack.items.add(belongings.misc3);

		rec.gameData.put( HANDLERS, handler);

		//restore items now that we're done saving
		belongings.backpack.items = allItems;
		
		//save challenges
		rec.gameData.put( CHALLENGES, Dungeon.challenges );
	}

	public void loadGameData(Record rec){
		Bundle data = rec.gameData;

		Dungeon.hero = null;
		Dungeon.depth = null;
		Generator.reset();

		Bundle handler = data.getBundle(HANDLERS);

		Badges.loadLocal(data.getBundle(BADGES));

		Dungeon.hero = (Hero)data.get(HERO);

		Statistics.restoreFromBundle(data.getBundle(STATS));
		
		Dungeon.challenges = data.getInt(CHALLENGES);

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
			ShatteredPixelDungeon.reportException(e);
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
		private static final String DATA	= "gameData";
		private static final String ID      = "gameID";

		public String info;
		public boolean win;

		public HeroClass heroClass;
		public int skin;
		public int herolevel;
		public int depth;

		public int score;

		public Bundle gameData;
		public String gameID;

		@Override
		public void restoreFromBundle(Bundle bundle) {

			info = bundle.getString(REASON);
			win = bundle.getBoolean(WIN);
			score = bundle.getInt(SCORE);
			heroClass = HeroClass.restoreInBundle(bundle);
			skin = bundle.getInt(SKIN);

			if (bundle.contains(DATA))  gameData = bundle.getBundle(DATA);
			if (bundle.contains(ID))   gameID = bundle.getString(ID);
			
			if (gameID == null) gameID = UUID.randomUUID().toString();
			
			
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

			if (gameData != null) bundle.put( DATA, gameData );
			bundle.put( ID, gameID );
		}
	}

	private static final Comparator<Record> scoreComparator = new Comparator<Rankings.Record>() {
		@Override
		public int compare(Record lhs, Record rhs) {
			return (int) Math.signum(rhs.score - lhs.score);
		}
	};
}
