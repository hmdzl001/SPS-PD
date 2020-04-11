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
package com.hmdzl.spspd.windows;

import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;

public class WndMessage extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;
	private static final int MARGIN = 4;

	public WndMessage( String text ) {

		super();

		RenderedTextMultiline info = PixelScene.renderMultiline( text, 6 );
		info.maxWidth((ShatteredPixelDungeon.landscape() ? WIDTH_L : WIDTH_P) - MARGIN * 2);
		info.setPos(MARGIN, MARGIN);
		add( info );

		resize(
				(int)info.width() + MARGIN * 2,
				(int)info.height() + MARGIN * 2 );
	}
}
