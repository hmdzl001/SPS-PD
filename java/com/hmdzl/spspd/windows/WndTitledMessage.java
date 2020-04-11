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
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class WndTitledMessage extends Window {

	protected static final int WIDTH_P    = 120;
	protected static final int WIDTH_L    = 144;
	protected static final int GAP	= 2;

	public WndTitledMessage( Image icon, String title, String message ) {

		this( new IconTitle( icon, title ), message );

	}

	public WndTitledMessage( Component titlebar, String message ) {

		super();

		int width = ShatteredPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

		titlebar.setRect( 0, 0, width, 0 );
		add(titlebar);

		RenderedTextMultiline text = PixelScene.renderMultiline( 6 );
		text.text( message, width );
		text.setPos( titlebar.left(), titlebar.bottom() + GAP );
		add( text );

		resize( width, (int)text.bottom() );
	}
}
