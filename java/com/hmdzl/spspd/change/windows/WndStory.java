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
package com.hmdzl.spspd.change.windows;

import com.hmdzl.spspd.change.Chrome;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.ShatteredPixelDungeon;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;
import com.watabou.utils.SparseArray;

public class WndStory extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;
	private static final int MARGIN = 6;

	private static final float bgR = 0.77f;
	private static final float bgG = 0.73f;
	private static final float bgB = 0.62f;

	public static final int ID_SEWERS = 0;
	public static final int ID_PRISON = 1;
	public static final int ID_CAVES = 2;
	public static final int ID_CITY = 3;
	public static final int ID_HALLS = 4;
	public static final int ID_SOKOBAN1 = 5;
	public static final int ID_SOKOBAN2 = 6;
	public static final int ID_SOKOBAN3 = 7;
	public static final int ID_SOKOBAN4 = 8;
	public static final int ID_SAFELEVEL = 9;
	public static final int ID_TOWN = 10;
	public static final int ID_CHAOS = 11;
	public static final int ID_ZOT = 12;

	private static final SparseArray<String> CHAPTERS = new SparseArray<String>();

	static {
		CHAPTERS.put( ID_SEWERS, "sewers" );
		CHAPTERS.put( ID_PRISON, "prison" );
		CHAPTERS.put( ID_CAVES, "caves" );
		CHAPTERS.put( ID_CITY, "city" );
		CHAPTERS.put( ID_HALLS, "halls" );
        CHAPTERS.put( ID_SOKOBAN1, "sokoban1");
		CHAPTERS.put( ID_SOKOBAN2,"sokoban2");
		CHAPTERS.put( ID_SOKOBAN3,"sokoban3");
		CHAPTERS.put( ID_SOKOBAN4,"sokoban4");
		CHAPTERS.put( ID_SAFELEVEL,"safelevel");
        CHAPTERS.put( ID_TOWN,"town");
		CHAPTERS.put( ID_CHAOS,"chaos");
		CHAPTERS.put( ID_ZOT,"zot");
	}

	private RenderedTextMultiline tf;

	private float delay;

	public WndStory(String text) {
		super(0, 0, Chrome.get(Chrome.Type.SCROLL));

		tf = PixelScene.renderMultiline( text, 7 );
		tf.maxWidth(ShatteredPixelDungeon.landscape() ?
				WIDTH_L - MARGIN * 2:
				WIDTH_P - MARGIN *2);
		tf.invert();
		tf.setPos(MARGIN, 0);
		add( tf );

		add(new TouchArea(chrome) {
			@Override
			protected void onClick(Touch touch) {
				hide();
			}
		});

		resize((int) (tf.width() + MARGIN * 2),
				(int) Math.min(tf.height(), 180));
	}

	@Override
	public void update() {
		super.update();

		if (delay > 0 && (delay -= Game.elapsed) <= 0) {
			shadow.visible = chrome.visible = tf.visible = true;
		}
	}

	public static void showChapter(int id) {

		if (Dungeon.chapters.contains(id)) {
			return;
		}

		String text = Messages.get(WndStory.class, CHAPTERS.get( id ));
		if (text != null) {
			WndStory wnd = new WndStory(text);
			if ((wnd.delay = 0.6f) > 0) {
				wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
			}

			Game.scene().add(wnd);

			Dungeon.chapters.add(id);
		}
	}
	
	public static void showChapter(String text) {
		if (text != null) {
			WndStory wnd = new WndStory( text );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.chrome.visible = wnd.tf.visible = false;
			}

			Game.scene().add( wnd );
		}
	}	
}
