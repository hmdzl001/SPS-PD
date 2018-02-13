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

import com.hmdzl.spspd.change.Rankings;
import com.hmdzl.spspd.change.Statistics;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Ankh;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.InterlevelScene;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.watabou.noosa.Game;

public class WndResurrect extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public static WndResurrect instance;
	public static Object causeOfDeath;

	public WndResurrect(final Ankh ankh, Object causeOfDeath) {

		super();

		instance = this;
		WndResurrect.causeOfDeath = causeOfDeath;

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(ankh.image(), null));
		titlebar.label(ankh.name());
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(this, "message"), 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnYes = new RedButton( Messages.get(this, "yes")) {
			@Override
			protected void onClick() {
				hide();

				Statistics.ankhsUsed++;

				InterlevelScene.mode = InterlevelScene.Mode.RESURRECT;
				Game.switchScene(InterlevelScene.class);
			}
		};
		btnYes.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add(btnYes);

		RedButton btnNo = new RedButton( Messages.get(this, "no")) {
			@Override
			protected void onClick() {
				hide();

				Rankings.INSTANCE.submit(false);
				Hero.reallyDie(WndResurrect.causeOfDeath);
			}
		};
		btnNo.setRect(0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNo);

		resize(WIDTH, (int) btnNo.bottom());
	}

	@Override
	public void destroy() {
		super.destroy();
		instance = null;
	}

	@Override
	public void onBackPressed() {
	}
}
