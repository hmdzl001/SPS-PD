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

import com.hmdzl.spspd.change.items.DewVial;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
 

public class WndDewDrawInfo extends Window {
	
	//if people don't get it after this, I quit. I just quit.

	private static final String TXT_MESSAGE = "Drawing out dew makes it so that mobs on special levels drop dew to fill your vial. "
            +"Additionally, your character is buffed with dew charge at the start of each normal level. "
            +"As long as you are dew charged, enemies drop dew to fill your vial. ";
	
	private static final String TXT_MESSAGE2 = "Each level dew charges you for a set amount of moves. "
            +"Each level also has a move goal for killing all regular generated enemies. (Not special enemies like statues and piranha) "
            +"Killing all regular enemies that were generated with the level clears that level. ";
	
	private static final String TXT_MESSAGE3 = "If you clear a level in less moves than the goal, the additional moves are added to your dew charge for the next level. "
            +"You will need to clear the levels as fast as you can to get dew upgrades. ";

	private static final String TXT_MESSAGE4 = "The dew vial will also allow you to choose which item you apply upgrades to when blessing. ";
	
	private static final String TXT_WATER = "Okay! Thanks for that!";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndDewDrawInfo(final Item item) {

		super();
		
		Item dewvial = new DewVial();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(dewvial.image(), null));
		titlebar.label(Messages.titleCase(dewvial.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene.renderMultiline(Messages.get(this, "info1"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);

		RenderedTextMultiline message2 = PixelScene.renderMultiline(Messages.get(this, "info2"), 6);
		message2.maxWidth(WIDTH);
		message2.setPos(0,message.top() + message.height() + GAP);
		add(message2);

		RenderedTextMultiline message3 = PixelScene.renderMultiline(Messages.get(this, "info3"), 6);
		message3.maxWidth(WIDTH);
		message3.setPos(0, message2.top() + message2.height() + GAP);
		add(message3);
		
		RenderedTextMultiline message4 = PixelScene.renderMultiline(Messages.get(this, "info4"), 6);
		message4.maxWidth(WIDTH);
		message4.setPos(0,message3.top() + message3.height() + GAP);
		add(message4);
		
		
		RedButton btnBattle = new RedButton(Messages.get(this, "ok")) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		btnBattle.setRect(0, message4.top() + message4.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		

		resize(WIDTH, (int) btnBattle.bottom());
	}

	
}
