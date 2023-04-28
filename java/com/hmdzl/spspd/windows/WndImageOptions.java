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
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.Image;

import static com.hmdzl.spspd.scenes.StartScene.curClass;

public class WndImageOptions extends Window {

	private static final int MARGIN = 2;
	private static final int BUTTON_HEIGHT = 20;

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;

	public WndImageOptions( String title, String message, String... options ) {
		super();

		int width = ShatteredPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

		RenderedTextMultiline tfTitle = PixelScene.renderMultiline( title, 9 );
		tfTitle.hardlight( TITLE_COLOR );
		tfTitle.setPos(MARGIN, MARGIN);
		tfTitle.maxWidth(width - MARGIN * 2);
		add( tfTitle );

		RenderedTextMultiline tfMesage = PixelScene.renderMultiline( 6 );
		tfMesage.text(message, width - MARGIN * 2);
		tfMesage.setPos( MARGIN, tfTitle.top() + tfTitle.height() + MARGIN );
		add( tfMesage );

		float pos = tfMesage.bottom() + MARGIN;

		for (int i=0; i < options.length; i++) {
			final int index = i;
			RedButton btn = new RedButton( curClass.skinsheet(i), options[i] ) {
				@Override
				protected void onClick() {
					hide();
					onSelect( index );
				}
			};
			btn.setRect( MARGIN, pos, width - MARGIN * 2, BUTTON_HEIGHT );
			add( btn );

			pos += BUTTON_HEIGHT + MARGIN;
		}

		resize( width, (int)pos );
	}


	protected boolean enabled( int index ){
		return true;
	}
	
	protected void onSelect( int index ) {}

	protected boolean hasInfo( int index ) {
		return false;
	}

	protected void onInfo( int index ) {}

	protected boolean hasIcon( int index ) {
		return false;
	}

	protected Image getIcon( int index ) {
		return null;
	}
	
}
