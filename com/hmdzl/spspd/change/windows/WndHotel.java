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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.actors.mobs.Mob;
import com.hmdzl.spspd.change.actors.mobs.npcs.G2159687;
import com.hmdzl.spspd.change.actors.mobs.npcs.Imp;
import com.hmdzl.spspd.change.actors.mobs.npcs.OldNewStwist;
import com.hmdzl.spspd.change.actors.mobs.npcs.StormAndRain;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.keys.IronKey;
import com.hmdzl.spspd.change.items.quest.DwarfToken;
import com.hmdzl.spspd.change.items.quest.GnollClothes;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.scenes.PixelScene;
import com.hmdzl.spspd.change.sprites.ItemSprite;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.ui.RedButton;
import com.hmdzl.spspd.change.ui.RenderedTextMultiline;
import com.hmdzl.spspd.change.ui.Window;
import com.hmdzl.spspd.change.utils.GLog;
 

public class WndHotel extends Window {

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	public WndHotel() {

		super();

		IronKey key = new IronKey(Dungeon.depth);


		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(key.image(), null));
		titlebar.label(Messages.titleCase(key.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(Messages.get(this, "message"), 6);
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add(message);

		RedButton btnReward = new RedButton(Messages.get(this, "buy")) {
			@Override
			protected void onClick() {
                if (Dungeon.gold > 100){
					Dungeon.gold-=100;
					IronKey key = new IronKey(Dungeon.depth);
					key.doPickUp(Dungeon.hero);
					hide();
				} else {
					GLog.w(Messages.get(this,"more_gold"));
				    hide();
				}
			}
		};
		btnReward.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnReward);

		resize(WIDTH, (int) btnReward.bottom());
	}
}