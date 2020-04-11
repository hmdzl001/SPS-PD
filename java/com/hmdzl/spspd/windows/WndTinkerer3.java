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

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.actors.mobs.npcs.Tinkerer3;
import com.hmdzl.spspd.items.Item;
import com.hmdzl.spspd.items.quest.Mushroom;
import com.hmdzl.spspd.scenes.PixelScene;
import com.hmdzl.spspd.sprites.ItemSprite;
import com.hmdzl.spspd.ui.RedButton;
import com.hmdzl.spspd.ui.RenderedTextMultiline;
import com.hmdzl.spspd.ui.Window;
import com.hmdzl.spspd.messages.Messages;
 

public class WndTinkerer3 extends Window {

	private static final String TXT_MESSAGE = "Thanks for the Toadstool Mushroom! "
			                                  +"I can upgrade your dew vial for you. "
			                                  +"I can make it hold more and give you wings when you splash. ";
	private static final String TXT_UPGRADE = "Upgrade my Vial!";
	
	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndTinkerer3(final Tinkerer3 tinkerer, final Item item) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Messages.titleCase(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(this, "info"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnUpgrade = new RedButton(Messages.get(this, "upgrade")) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer);
			}
		};
		btnUpgrade.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnUpgrade);

		
		resize(WIDTH, (int) btnUpgrade.bottom());
	}

	private void selectUpgrade(Tinkerer3 tinkerer) {
		hide();
		
		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);
		
			//Dungeon.dewWater=true;				
			//Dungeon.dewDraw=true;
			Dungeon.wings=true;
	
		
		tinkerer.yell( Messages.get(this, "farewell", Dungeon.hero.givenName()));
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
}
