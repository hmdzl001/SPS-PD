/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.GamesInProgress;
import com.hmdzl.spspd.ShatteredPixelDungeon;
import com.hmdzl.spspd.actors.hero.HeroSubClass;
import com.hmdzl.spspd.messages.Messages;
import com.hmdzl.spspd.scenes.InterlevelScene;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.scenes.StartScene;
import com.hmdzl.spspd.sprites.HeroSprite;
import com.hmdzl.spspd.ui.ActionIndicator;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.Window;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.utils.FileUtils;

import java.util.Locale;

public class WndGameInProgress extends Window {
	private static final int WIDTH    = 120;
	private static final int HEIGHT   = 160;

	private int GAP	  = 5;

	private float pos;

	public WndGameInProgress(final int slot){

		final GamesInProgress.Info info = GamesInProgress.check(slot);

		String className = null;
		if (info.subClass != HeroSubClass.NONE){
			className = info.subClass.title();
		} else {
			className = info.heroClass.title();
	    }

		IconTitle title = new IconTitle();
		title.icon( HeroSprite.avatar(info.heroClass, info.skins) );
		//title.label((Messages.get(this, "title", info.level, className)).toUpperCase(Locale.ENGLISH));
		title.label((Messages.get(this, "title", info.level, className)).toUpperCase(Locale.ENGLISH));

		title.color(Window.SHPX_COLOR);
		title.setRect( 0, 0, WIDTH, 0 );
		add(title);

		if (info.challenges > 0) GAP -= 2;

		pos = title.bottom() + GAP;

		if (info.challenges > 0) {
			RedButton btnChallenges = new RedButton( Messages.get(this, "challenges") ) {
				@Override
				protected void onClick() {
					Game.scene().add( new WndChallenges( info.challenges, false ) );
				}
			};
			float btnW = btnChallenges.reqWidth() + 2;
			btnChallenges.setRect( (WIDTH - btnW)/2, pos, btnW , btnChallenges.reqHeight());
			add( btnChallenges );

			pos = btnChallenges.bottom() + GAP;
		}

		pos += GAP;

		statSlot( Messages.get(this, "depth"), info.depth );

		statSlot( Messages.get(this, "oldslot"), info.oldslot );

		pos += GAP;

		statSlot( Messages.get(this, "skin"), Messages.get(this,"id_"+ info.skins) );
		statSlot( Messages.get(this, "str"), info.str );
		statSlot( Messages.get(this, "health"), (info.hp) + "/" + info.ht );

		pos += GAP;
		statSlot( Messages.get(this, "gold"), info.goldCollected );
		statSlot( Messages.get(this, "maxdepth"), info.maxDepth );
		statSlot( Messages.get(this, "verison"), info.version );

		pos += GAP;

		RedButton cont = new RedButton(Messages.get(this, "continue")){
			@Override
			protected void onClick() {
				super.onClick();

				GamesInProgress.curSlot = slot;
				Dungeon.hero = null;
				ActionIndicator.action = null;
				InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
				ShatteredPixelDungeon.switchScene(InterlevelScene.class);
			}
		};

		RedButton erase = new RedButton( Messages.get(this, "erase")){
			@Override
			protected void onClick() {
				super.onClick();

				ShatteredPixelDungeon.scene().add(new WndOptions(
						Messages.get(WndGameInProgress.class, "erase_warn_title"),
						Messages.get(WndGameInProgress.class, "erase_warn_body"),
						Messages.get(WndGameInProgress.class, "erase_warn_yes"),
						Messages.get(WndGameInProgress.class, "erase_warn_no") ) {
					@Override
					protected void onSelect( int index ) {
						if (index == 0) {
							FileUtils.deleteDir(GamesInProgress.gameFolder(slot));
							GamesInProgress.setUnknown(slot);
							ShatteredPixelDungeon.switchNoFade(StartScene.class);
						}
					}
				} );
			}
		};


		cont.setRect(0, HEIGHT - 20, WIDTH / 2 - 1, 20);
		add(cont);


		erase.setRect(WIDTH/2 + 1, HEIGHT-20, WIDTH/2 - 1, 20);
		add(erase);

		resize(WIDTH, HEIGHT);
	}

	private void statSlot( String label, String value ) {

		RenderedText txt = PixelScene.renderText( label, 8 );
		txt.y = pos;
		add( txt );

		txt = PixelScene.renderText( value, 8 );
		txt.x = WIDTH * 0.6f;
		txt.y = pos;
		PixelScene.align(txt);
		add( txt );

		pos += GAP + txt.baseLine();
	}

	private void statSlot( String label, int value ) {
		statSlot( label, Integer.toString( value ) );
	}
}
