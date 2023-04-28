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
package com.hmdzl.spspd.scenes;

import android.content.Intent;
import android.net.Uri;

import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.effects.Flare;
import com.hmdzl.spspd.ui.Archs;
import com.hmdzl.spspd.ui.ExitButton;
import com.hmdzl.spspd.ui.Icons;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;

public class AboutScene extends PixelScene {

	private static final String TTL_SHPX = "SPecial Surprise Pixel Dungeon";

	private static final String TXT_SHPX = "Modified from lots of Pixel Dungeon Mods";

	private static final String TXT_CODE = "Game based code";

	private static final String LNK_CODE = "github.com/hmdzl001/SPS-PD";

	private static final String TXT_FOR_DEV = "Support developer";

	private static final String LNK_FOR_DEV = "afdian.net/a/Hmdzl001SPS";

	private static final String LNK_FOR_DEV2 = "PayPal.Me/Hmdzl001";

	private static final String TXT_FOR_CHILD = "Support this mod";

	private static final String LNK_FOR_CHILD = "unicef.org";


	private static final String TTL_WATA = "Original Pixel Dungeon";

	private static final String TXT_WATA = "Code & Graphics: Watabou\n" + "Music: Cube_Code";

	private static final String LNK_WATA = "pixeldungeon.watabou.ru";

	@Override
	public void create() {
		super.create();

		final float colWidth = Camera.main.width
				/ (ShatteredPixelDungeon.landscape() ? 2 : 1);
		final float colTop = (Camera.main.height / 2)
				- (ShatteredPixelDungeon.landscape() ? 60 : 120);
		final float wataOffset = ShatteredPixelDungeon.landscape() ? colWidth
				: 0;

		Image shpx = Icons.SHPX.get();
		shpx.x = align((colWidth - shpx.width()) / 2);
		shpx.y = align(colTop);
		align(shpx);
		add(shpx);

		new Flare(7, 64).color(0x225511, true).show(shpx, 0).angularSpeed = +20;

		RenderedText shpxtitle = renderText( TTL_SHPX, 8 );
		shpxtitle.hardlight( Window.SHPX_COLOR );
		add( shpxtitle );

		shpxtitle.x = (colWidth - shpxtitle.width()) / 2;
		shpxtitle.y = shpx.y + shpx.height + 3;
		align(shpxtitle);

		RenderedTextMultiline shpxtext = renderMultiline( TXT_SHPX, 8 );
		shpxtext.maxWidth((int)Math.min(colWidth, 120));
		add( shpxtext );

		shpxtext.setPos((colWidth - shpxtext.width()) / 2, shpxtitle.y + shpxtitle.height() + 4);
		align(shpxtext);

		RenderedTextMultiline codetext = renderMultiline( TXT_CODE, 8 );
		codetext.maxWidth((int)Math.min(colWidth, 120));
		add( codetext );

		codetext.setPos((colWidth - codetext.width()) / 2, shpxtext.bottom() );
		align(codetext);

		RenderedTextMultiline shpxlink = renderMultiline(LNK_CODE, 8 );
		shpxlink.maxWidth(shpxtext.maxWidth());
		shpxlink.hardlight( Window.SHPX_COLOR );
		add( shpxlink );

		shpxlink.setPos((colWidth - shpxlink.width()) / 2, codetext.bottom());
		align(shpxlink);
		TouchArea shpxhotArea = new TouchArea( shpxlink.left(), shpxlink.top(), shpxlink.width(), shpxlink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://" + LNK_CODE) );
				Game.instance.startActivity( intent );
			}
		};
		add(shpxhotArea);

		RenderedTextMultiline supdtext = renderMultiline( TXT_FOR_DEV, 8 );
		supdtext.maxWidth((int)Math.min(colWidth, 120));
		add( supdtext );
		supdtext.setPos((colWidth - supdtext.width()) / 2, shpxlink.bottom() );
		align(supdtext);

		RenderedTextMultiline supdlink = renderMultiline(LNK_FOR_DEV, 8 );
		supdlink.maxWidth(supdtext.maxWidth());
		supdlink.hardlight( Window.SHPX_COLOR );
		add( supdlink );

		supdlink.setPos((colWidth - supdlink.width()) / 2, supdtext.bottom());
		align(supdlink);
		TouchArea supdlinkArea = new TouchArea( supdlink.left(), supdlink.top(), supdlink.width(), supdlink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://" + LNK_FOR_DEV) );
				Game.instance.startActivity( intent );
			}
		};
		add(supdlinkArea);

		RenderedTextMultiline supdlink2 = renderMultiline(LNK_FOR_DEV2, 8 );
		supdlink2.maxWidth(supdlink.maxWidth());
		supdlink2.hardlight( Window.SHPX_COLOR );
		add( supdlink2 );

		supdlink2.setPos((colWidth - supdlink2.width()) / 2, supdlink.bottom() );
		align(supdlink2);
		TouchArea supdlink2Area = new TouchArea( supdlink2.left(), supdlink2.top(), supdlink2.width(), supdlink2.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://" + LNK_FOR_DEV2) );
				Game.instance.startActivity( intent );
			}
		};
		add(supdlink2Area);

		RenderedTextMultiline supgtext = renderMultiline( TXT_FOR_CHILD, 8 );
		supgtext.maxWidth((int)Math.min(colWidth, 120));
		add( supgtext );

		supgtext.setPos((colWidth - supgtext.width()) / 2, supdlink2.bottom());
		align(supgtext);

		RenderedTextMultiline supglink = renderMultiline(LNK_FOR_CHILD, 8 );
		supglink.maxWidth(supgtext.maxWidth());
		supglink.hardlight( Window.SHPX_COLOR );
		add( supglink );

		supglink.setPos((colWidth - supglink.width()) / 2, supgtext.bottom() );
		align(supglink);
		TouchArea supglinkArea = new TouchArea( supglink.left(), supglink.top(), supglink.width(), supglink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://" + LNK_FOR_CHILD) );
				Game.instance.startActivity( intent );
			}
		};
		add(supglinkArea);


		Image wata = Icons.WATA.get();
		wata.x = wataOffset + (colWidth - wata.width()) / 2;
		wata.y = ShatteredPixelDungeon.landscape() ?
						colTop:supglink.top() + wata.height + 20;
		align(wata);
		add( wata );

		new Flare(7, 64).color(0x112233, true).show(wata, 0).angularSpeed = +20;

		RenderedText wataTitle = renderText( TTL_WATA, 8 );
		wataTitle.hardlight(Window.TITLE_COLOR);
		add( wataTitle );

		wataTitle.x = wataOffset + (colWidth - wataTitle.width()) / 2;
		wataTitle.y = wata.y + wata.height + 11;
		align(wataTitle);

		RenderedTextMultiline wataText = renderMultiline( TXT_WATA, 8 );
		wataText.maxWidth((int)Math.min(colWidth, 120));
		add( wataText );

		wataText.setPos(wataOffset + (colWidth - wataText.width()) / 2, wataTitle.y + wataTitle.height() + 12);
		align(wataText);
		
		RenderedTextMultiline wataLink = renderMultiline( LNK_WATA, 8 );
		wataLink.maxWidth((int)Math.min(colWidth, 120));
		wataLink.hardlight(Window.TITLE_COLOR);
		add(wataLink);
		
		wataLink.setPos(wataOffset + (colWidth - wataLink.width()) / 2 , wataText.bottom() + 6);
		align(wataLink);
		
		TouchArea hotArea = new TouchArea( wataLink.left(), wataLink.top(), wataLink.width(), wataLink.height() ) {
			@Override
			protected void onClick( Touch touch ) {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://" + LNK_WATA ) );
				Game.instance.startActivity( intent );
			}
		};
		add( hotArea );

		Archs archs = new Archs();
		archs.setSize(Camera.main.width, Camera.main.height);
		addToBack(archs);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos(Camera.main.width - btnExit.width(), 0);
		add(btnExit);

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade(TitleScene.class);
	}
}
